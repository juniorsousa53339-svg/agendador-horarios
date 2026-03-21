# Mentoria completa — Agendador (do escopo ao deploy)

> Objetivo: te ensinar como professor, em ordem, do **planejamento** até o **deploy**, usando seu back-end atual.

---

## 1) Escopo do projeto (o que vamos entregar)

## 1.1 Problema que seu sistema resolve
Microempreendedores (barbearias da região) precisam:
- organizar agenda,
- cadastrar clientes e serviços,
- reduzir conflito de horário,
- ter um painel simples para o dia a dia.

## 1.2 Entrega MVP (versão que você vende)
- Painel administrativo com visual minimalista escuro.
- Gestão de Clientes.
- Gestão de Funcionários.
- Gestão de Serviços (com duração em minutos).
- Gestão de Agendamentos.
- Deploy com URL pública (front + back).

## 1.3 Fora do MVP (depois)
- Notificação WhatsApp.
- Login de cliente final.
- Dashboard com métricas avançadas.

---

## 2) Levantamento de requisitos (back-end + front-end)

## 2.1 O que já existe no seu back-end
Com base no código atual:
- CRUD de clientes.
- CRUD de funcionários.
- CRUD de serviços.
- CRUD de agendamentos.
- Endpoints de proprietários e barbearias.
- CORS local para front.

## 2.2 Entidades principais que o front vai consumir
1. **Cliente**
2. **Funcionario**
3. **Servicos**
4. **Agendamento**
5. **Barbearia**
6. **Proprietario**

## 2.3 Requisitos funcionais do front
- RF01: cadastrar/editar/excluir cliente.
- RF02: cadastrar/editar/excluir funcionário.
- RF03: cadastrar/editar/excluir serviço com `duracaoMinutos`.
- RF04: listar agendamentos por dia.
- RF05: criar/remarcar/cancelar agendamento.
- RF06: exibir tela principal com resumo operacional.

## 2.4 Requisitos não funcionais
- Layout minimalista dark.
- Responsivo para celular e notebook.
- Feedback visual de erro/sucesso.
- Tempo de carregamento baixo.

---

## 3) Como ficará o front (minimalista escuro)

## 3.1 Estilo visual
- Fundo: grafite/preto.
- Cards: cinza escuro com borda suave.
- Cor de destaque: dourado/barber style.

### Paleta sugerida
- `#111315` (fundo)
- `#1a1d21` (surface)
- `#2a2f36` (borda)
- `#f2f2f2` (texto)
- `#d4af37` (destaque)

## 3.2 Telas que fazem sentido para seu sistema
1. **Login** (simples para início).
2. **Dashboard** (resumo do dia).
3. **Clientes**.
4. **Funcionários**.
5. **Serviços**.
6. **Agendamentos**.
7. **Configurações da barbearia** (logo, nome, contato).

---

## 4) Contrato de API (resumo para o Angular)

## Clientes
- `POST /clientes`
- `GET /clientes`
- `DELETE /clientes?nomeCliente=...`
- `PUT /clientes/alterar-nome`
- `PUT /clientes/alterar-telefone`

## Funcionários
- `POST /funcionarios`
- `GET /funcionarios`
- `DELETE /funcionarios?nomeFuncionario=...`
- `PUT /funcionarios/alterar-nome`
- `PUT /funcionarios/alterar-telefone`

## Serviços
- `POST /servicos`
- `GET /servicos`
- `DELETE /servicos?nomeServico=...`
- `PUT /servicos/alterar-nome`
- `PUT /servicos/alterar-preco`
- `PUT /servicos/alterar-descricao`
- `PUT /servicos/alterar-duracao`

## Agendamentos
- `POST /agendamentos`
- `GET /agendamentos?data=...&idCliente=...`
- `PUT /agendamentos?...`
- `DELETE /agendamentos?...`

---

## 5) Mentoria prática — do zero ao Angular conectado

## 5.1 Preparar ambiente
```bash
# Node + Angular CLI
npm install -g @angular/cli

# conferir versões
node -v
npm -v
ng version
```

## 5.2 Criar projeto Angular
```bash
ng new barbearia-front --routing --style=scss
cd barbearia-front
ng serve
```
Acesse: `http://localhost:4200`.

## 5.3 Estrutura recomendada
```text
src/app/
  core/
    services/
    interceptors/
  shared/
    models/
    components/
  layout/
    shell/
  pages/
    dashboard/
    clientes/
    funcionarios/
    servicos/
    agendamentos/
```

## 5.4 Configurar API base
`src/environments/environment.ts`
```ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
```

## 5.5 Tema escuro global
`src/styles.scss`
```scss
:root {
  --bg: #111315;
  --surface: #1a1d21;
  --border: #2a2f36;
  --text: #f2f2f2;
  --primary: #d4af37;
}

body {
  margin: 0;
  background: var(--bg);
  color: var(--text);
  font-family: Inter, Arial, sans-serif;
}
```

## 5.6 Criar layout com logo
- Sidebar com links (Dashboard, Clientes, Funcionários, Serviços, Agendamentos).
- Topbar com nome da barbearia.
- Logo em `src/assets/logo-barbearia.png`.

## 5.7 Primeiro módulo para integrar: Serviços
### Model
```ts
export interface Servico {
  idServico?: number;
  nomeServico: string;
  descricaoServico: string;
  precoServico: number;
  duracaoMinutos: number;
}
```

### Service
```ts
private readonly api = `${environment.apiUrl}/servicos`;
```
Criar métodos:
- `listar(...)`
- `criar(...)`
- `alterarDuracao(...)`

### Tela
Campos mínimos:
- nome
- descrição
- preço
- duração (min)

Botões:
- salvar
- atualizar lista

## 5.8 Ordem de integração (sem travar)
1. Serviços
2. Clientes
3. Funcionários
4. Agendamentos

---

## 6) Conectando front e back (checklist de integração)

1. Backend rodando:
```bash
cd agendador-horarios
mvn spring-boot:run
```
2. Front rodando:
```bash
cd barbearia-front
ng serve
```
3. Testar chamada no front para endpoint real.
4. Validar se não há erro de CORS.
5. Confirmar CRUD funcionando em cada módulo.

---

## 7) Deploy gratuito (início) na AWS

> Para começar sem custo inicial alto, use a camada gratuita/créditos da AWS e monitore o billing.

## 7.1 Front no Amplify
1. Subir código no GitHub.
2. AWS Amplify → Host web app.
3. Conectar repo + branch.
4. Build: `npm ci && npm run build`.
5. Publicar URL.

## 7.2 Back no Elastic Beanstalk
1. Gerar JAR:
```bash
cd agendador-horarios
mvn clean package -DskipTests
```
2. Elastic Beanstalk → Create application (Java).
3. Upload do JAR.
4. Publicar URL do backend.

## 7.3 Produção
- Ajustar `apiUrl` do front para URL pública do backend.
- Fazer novo deploy do front.
- Testar fluxo real ponta a ponta.

## 7.4 Custos (obrigatório)
- Criar Budget na AWS.
- Alertas em 50%, 80%, 100%.
- Remover recursos não usados.

---

## 8) Cronograma diário (do zero ao deploy)

## Dia 1
- Setup Node, Angular CLI, projeto inicial.

## Dia 2
- Estrutura de pastas + rotas + layout shell.

## Dia 3
- Tema dark + logo + ajustes visuais.

## Dia 4
- Módulo Serviços (model + service + cadastro).

## Dia 5
- Módulo Serviços (lista + edição + exclusão).

## Dia 6
- Módulo Clientes.

## Dia 7
- Módulo Funcionários.

## Dia 8
- Agendamentos (listagem por data).

## Dia 9
- Agendamentos (criação/remarcação/cancelamento).

## Dia 10
- Polimento visual + tratamento de erros.

## Dia 11
- Revisão geral + checklist funcional.

## Dia 12
- Deploy Front (Amplify).

## Dia 13
- Deploy Back (Elastic Beanstalk).

## Dia 14
- Testes finais em produção + entrega para cliente.

---

## 9) Como você vende isso como freelancer

Pacote inicial para microempreendedor:
1. Setup do sistema + logo + tema personalizado.
2. Cadastro inicial de serviços/horários.
3. Treinamento rápido de uso.
4. Suporte mensal opcional.

Esse modelo te permite replicar projeto rápido para vários clientes.
