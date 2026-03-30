# Resumo Final do Sistema (Back-end + Índice da Mentoria)

Este é o documento principal do projeto e funciona como **mapa rápido**.

## Organização final dos documentos (enxuta)

1. **Este documento (`RESUMO_FINAL_SISTEMA.md`)**
   - visão geral do backend;
   - contrato de API;
   - separação por perfil (cliente, funcionário, proprietário);
   - checklist final técnico.
2. **Mentoria completa (`MENTORIA_ANGULAR_COMPONENTE_A_COMPONENTE.md`)**
   - passo a passo completo do Angular;
   - criação de projeto;
   - criação de componentes;
   - criação de telas;
   - layout, rotas e próximos passos.

> Mentoria detalhada (componente a componente): `docs/MENTORIA_ANGULAR_COMPONENTE_A_COMPONENTE.md`.

## 1) O que foi revisado no back-end

### Correções aplicadas
1. **Exclusão de proprietário corrigida**
   - Ajustado o `if` que estava invertido no `deletarProprietario`.
   - Antes: lançava erro quando encontrava o proprietário.
   - Agora: lança erro apenas quando **não** encontra.

2. **Busca de funcionário usando ID e nome de forma consistente**
   - A busca por funcionário passou a usar o `idFuncionario` quando ele for informado (> 0), junto do filtro por nome.
   - Foi adicionada uma query específica no repositório para isso.

3. **Validação de data futura para agendamento**
   - Em criação e remarcação de agendamento, agora o sistema valida se a data/hora é futura.
   - Se for nula ou no passado, retorna erro 400.

4. **Correções críticas no serviço de barbearia**
   - Ajuste de `@PreAuthorize` com role correta: `PROPRIETARIO`.
   - Correção de validação invertida na exclusão de barbearia.
   - Correção da validação da busca de barbearia (evitando condição sempre verdadeira).

## 2) Como o back-end funciona (visão prática)

### Camadas
- **Controller**: recebe requisição HTTP e devolve resposta.
- **Service**: aplica regra de negócio e validações.
- **Repository**: faz acesso ao banco com Spring Data JPA.
- **Entity/DTO**: define estrutura dos dados salvos e retornados.

### Fluxo geral de um agendamento
1. Front envia payload para `POST /agendamentos`.
2. Service valida:
   - cliente, funcionário e serviço existem;
   - data/hora é futura;
   - funcionário não está ocupado naquele horário.
3. Salva no banco.
4. Retorna DTO com dados do agendamento e status `MARCADO`.

## 3) Contrato de API (resumo para o Angular)

### Endpoints separados por perfil (novo)
- **Cliente (público, sem login)**:
  - `GET /funcionarios/publico`
  - `GET /servicos/publico`
  - `POST /clientes/publico`
  - `POST /agendamentos/publico`
- **Funcionário (com perfil FUNCIONARIO)**:
  - `GET /agendamentos/funcionario`
- **Proprietário (com perfil PROPRIETARIO)**:
  - `GET /agendamentos/proprietario`

### Clientes
- `POST /clientes`
- `GET /clientes?idCliente=...&nomeCliente=...`
- `DELETE /clientes?nomeCliente=...`
- `PUT /clientes/alterar-nome?atualNomeCliente=...&novoNomeCliente=...`
- `PUT /clientes/alterar-telefone?telefoneAtual=...&TelefoneNovo=...`

### Funcionários
- `POST /funcionarios`
- `GET /funcionarios?idFuncionario=...&nomeFuncionario=...`
- `DELETE /funcionarios?nomeFuncionario=...`
- `PUT /funcionarios/alterar-nome?nomeFuncionarioAtual=...&nomeFuncionarioNovo=...`
- `PUT /funcionarios/alterar-telefone?telefoneAtual=...&telefoneNovo=...`

### Serviços
- `POST /servicos`
- `GET /servicos?idServico=...&nomeServico=...&precoServico=...`
- `DELETE /servicos?nomeServico=...`
- `PUT /servicos/alterar-nome`
- `PUT /servicos/alterar-preco`
- `PUT /servicos/alterar-descricao`
- `PUT /servicos/alterar-duracao`

### Agendamentos
- `POST /agendamentos`
- `GET /agendamentos?data=...&idCliente=...`
- `PUT /agendamentos?dataHoraAtual=...&idClienteAtual=...&dataHoraNova=...&idClienteNovo=...`
- `DELETE /agendamentos?dataHora=...&idCliente=...`

## 4) Mentoria Angular (passo a passo com base no seu código)

## Objetivo de telas simples
Você quer 3 perfis visuais:
1. **Cliente (sem login)**
   - vê serviços
   - escolhe funcionário
   - escolhe dia e horário
   - informa nome + telefone
2. **Funcionário (com login)**
   - visualiza agenda dele
3. **Proprietário (com login)**
   - visualiza agenda geral
   - gerencia funcionários/serviços

## Estrutura recomendada do Angular
```txt
src/app/
  core/
    api/
    auth/
    interceptors/
  shared/
    models/
    ui/
  features/
    auth/
    cliente-agendamento/
    funcionario-agenda/
    proprietario-painel/
```

## Passo 1 — criar projeto
```bash
npm install -g @angular/cli
ng new agendador-front --routing --style=scss
cd agendador-front
ng serve
```

## Passo 2 — environment
`src/environments/environment.ts`
```ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
```

## Passo 3 — models (exemplo)
`src/app/shared/models/agendamento.ts`
```ts
export interface AgendamentoRequest {
  idCliente: number;
  idFuncionario: number;
  idServico: number;
  dataHoraAgendamento: string;
}

export interface AgendamentoResponse {
  idAgendamento: number;
  idCliente: number;
  nomeCliente: string;
  idFuncionario: number;
  nomeFuncionario: string;
  idServico: number;
  nomeServico: string;
  dataHoraAgendamento: string;
  status: string;
}
```

## Passo 4 — service HTTP
`src/app/core/api/agendamento-api.service.ts`
```ts
@Injectable({ providedIn: 'root' })
export class AgendamentoApiService {
  private readonly base = `${environment.apiUrl}/agendamentos`;

  constructor(private http: HttpClient) {}

  criar(payload: AgendamentoRequest) {
    return this.http.post<AgendamentoResponse>(this.base, payload);
  }

  listarDoDia(data: string, idCliente: number) {
    return this.http.get<AgendamentoResponse[]>(this.base, {
      params: { data, idCliente }
    });
  }
}
```

## Passo 5 — fluxo da tela do cliente
1. Carrega serviços (`/servicos`) e funcionários (`/funcionarios`).
2. Cliente escolhe serviço.
3. Cliente escolhe funcionário.
4. Cliente escolhe data/hora disponível.
5. Cliente informa nome/telefone.
6. Front valida campos obrigatórios.
7. Front chama endpoint de criar agendamento.

## Passo 6 — fluxo do funcionário
- Login.
- Página de agenda diária (filtro por data).
- Lista de horários marcados (somente leitura para início).

## Passo 7 — fluxo do proprietário
- Login.
- Dashboard simples:
  - total de agendamentos do dia;
  - lista de funcionários;
  - lista de serviços.
- Botões de CRUD para funcionários e serviços.

## Passo 8 — rota e guard
- Rotas públicas: `/agendar`.
- Rotas autenticadas:
  - `/funcionario/agenda`
  - `/proprietario/painel`
- Use `AuthGuard` para bloquear páginas privadas.

## Passo 9 — visual simples e rápido
- Layout em cards.
- Formulário com `ReactiveFormsModule`.
- Cores neutras + destaque em botões principais.
- Comece sem biblioteca de UI para ganhar velocidade (depois pode adicionar Angular Material).

## 5) Plano de execução para hoje (foco em finalizar)

1. **Back-end**
   - Rodar API local.
   - Validar endpoints principais no Postman.
2. **Angular base**
   - Criar projeto + rotas + services.
3. **Tela cliente**
   - Form de agendamento completo.
4. **Tela funcionário**
   - Agenda do dia.
5. **Tela proprietário**
   - Dashboard + CRUD mínimo de serviço/funcionário.
6. **Integração final**
   - Ajustar erros de CORS, payload e datas.

## 6) Próximos ajustes recomendados no back-end

- Padronizar exceções de negócio para `ResponseStatusException` (evitar `RuntimeException` genérica).
- Criar autenticação real (JWT ou sessão) para diferenciar proprietário/funcionário.
- Criar endpoint específico para “agenda por funcionário por data”.
- Padronizar nomes de parâmetros (`TelefoneNovo` -> `telefoneNovo`) para consistência.

## 7) Checklist de revisão final

- [x] Endpoints públicos para cliente separados dos endpoints internos.
- [x] Rotas de agenda separadas por perfil (`FUNCIONARIO` e `PROPRIETARIO`).
- [x] Validação de data futura em agendamento.
- [x] Mentoria Angular completa com projeto + componentes + telas + layout.
- [x] Encoder de senha configurado (`BCrypt`) para autenticação.
