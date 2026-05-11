# Escopo Completo do Frontend Angular – Sistema Mobile de Agendamento Integrado ao Spring Boot

## 1) Objetivo do documento

Este documento define o **escopo funcional e técnico do frontend Angular (mobile-first)** para consumo da API Spring Boot já existente, com foco em:

- Mapa completo de telas e navegação.
- Estrutura de módulos, páginas, componentes e serviços.
- Mapeamento endpoint a endpoint do backend atual para cada tela.
- Estratégia de autenticação visual por perfil (`ROLE_PROPRIETARIO` e `ROLE_FUNCIONARIO`).
- Passo a passo de implementação para você desenvolver com segurança e previsibilidade.

> Princípio arquitetural obrigatório: **o Angular não contém regra de negócio**. Toda validação/decisão crítica permanece no backend.

---

## 2) Diagnóstico técnico do backend atual (base para o frontend)

### 2.1 Entidades principais expostas pela API

- `Agendamento`: vínculo de `cliente`, `funcionario`, `servico` e `dataHoraAgendamento`.
- `Servicos`: `nomeServico`, `descricaoServico`, `precoServico`, `duracaoMinutos`.
- `Funcionario`: `nomeFuncionario`, `telefoneFuncionario`, `especialidade`.
- `Cliente`: `nomeCliente`, `telefoneCliente`.
- `Proprietario`: `nome`, `telefone`, `email`.
- `Barbearia`: `nomeBarbearia`, endereço, telefone e horário de funcionamento.

### 2.2 Segurança/autorização observada

- Existe modelagem de `Role` com `ROLE_PROPRIETARIO` e `ROLE_FUNCIONARIO`.
- Existem `@PreAuthorize` em services, porém o `SecurityConfig` atual permite todas as rotas (`permitAll`) no filtro HTTP.
- Não foi identificado endpoint explícito de autenticação (`/auth/login` ou similar) no backend atual.

### 2.3 Implicações para o escopo Angular

Para suportar login real por perfil no frontend, é recomendado alinhar com backend:

1. Endpoint de login (Basic/JWT/Session).
2. Endpoint “quem sou eu” (`/me`) para obter perfil.
3. Garantia de consistência dos filtros de listagem (alguns GETs atuais exigem múltiplos query params simultâneos).

> **Conclusão prática:** este escopo cobre o frontend completo com o backend atual e já indica ajustes de contrato para produção.

---

## 3) Mapa de telas (com fluxo, ordem e navegação)

## 3.1 Estrutura macro de navegação

### Áreas do app

1. **Público/Cliente** (agendamento guiado)
2. **Funcionário** (agenda operacional)
3. **Proprietário** (dashboard + gestão)

### Shells recomendados

- `PublicShellComponent`: sem menu lateral; fluxo linear de agendamento.
- `EmployeeShellComponent`: menu inferior com 2 itens.
- `OwnerShellComponent`: menu inferior/aba com atalhos administrativos.

---

## 3.2 Fluxo Cliente (mobile-first, wizard)

### Tela C1 — Serviços

**Objetivo:** iniciar agendamento escolhendo serviço.

**UI/UX:**
- Lista de cards vertical (scroll).
- Card: nome, duração, preço, descrição curta, botão “Selecionar”.
- Estado vazio: “Nenhum serviço disponível no momento”.

**Entrada:** catálogo de serviços.
**Saída de estado:** `servicoSelecionado`.
**Ações:** selecionar serviço -> navegar para C2.

---

### Tela C2 — Escolher profissional

**Objetivo:** selecionar profissional para o serviço.

**UI/UX:**
- Cards com avatar/foto fallback, nome, especialidade.
- Indicador visual de selecionado.

**Entrada:** lista de funcionários.
**Saída de estado:** `funcionarioSelecionado`.
**Ações:** avançar para C3.

---

### Tela C3 — Escolher data e horário

**Objetivo:** mostrar somente horários disponíveis.

**UI/UX:**
- Seletor de data (horizontal ou calendário compacto).
- Grade de horários em chips/blocos.
- Exibir **apenas slots livres** retornados pela API (sem regras locais de conflito).

**Entrada:** disponibilidade da API + serviço/profissional selecionados.
**Saída de estado:** `dataHoraSelecionada`.
**Ações:** avançar para C4.

---

### Tela C4 — Confirmação de dados

**Objetivo:** revisar e confirmar.

**Campos:**
- Nome cliente
- Telefone
- Serviço
- Profissional
- Data/hora

**Ações:**
- Botão “Confirmar agendamento”.
- Em sucesso: C5.
- Em erro API: toast com mensagem do backend.

---

### Tela C5 — Sucesso

**Objetivo:** feedback final.

**Conteúdo:**
- Mensagem de sucesso.
- Resumo do agendamento.
- CTA: “Ver meus agendamentos” e “Novo agendamento”.

---

### Menu Cliente

- `Agendar`
- `Meus agendamentos`

`Meus agendamentos` deve listar cards por data/status e permitir cancelamento (chamando API).

---

## 3.3 Fluxo Funcionário

### Tela F1 — Login

- Formulário: usuário + senha.
- Após login, validar role e redirecionar apenas se `ROLE_FUNCIONARIO`.

### Tela F2 — Minha agenda

- Cards dos agendamentos do dia.
- Dados: cliente, serviço, horário, status (confirmado/agendado/cancelado).
- Ação “Ver detalhes”.

### Tela F3 — Detalhe do agendamento

- Nome cliente, telefone, data, horário, serviço, status.
- Ações contextuais: confirmar/cancelar/reagendar (se permitido pela API).

### Tela F4 — Gerenciar agenda

- Edição operacional do agendamento:
  - confirmar
  - cancelar
  - alterar horário

### Menu Funcionário

- `Minha agenda`
- `Gerenciar agenda`

---

## 3.4 Fluxo Proprietário

### Tela P1 — Login

- Mesmo mecanismo de autenticação, roteamento por role para área owner.

### Tela P2 — Dashboard

Cards:
- Agendamentos de hoje
- Serviços ativos
- Total de funcionários

Atalhos:
- Funcionários
- Serviços
- Agendamentos
- Configurações

### Tela P3 — CRUD Funcionários

- Listagem
- Cadastro
- Edição (nome/telefone)
- Exclusão

### Tela P4 — CRUD Serviços

- Listagem
- Cadastro
- Edição (nome/preço/descrição/duração)
- Exclusão

### Tela P5 — Configurações

- Nome proprietário
- Nome da barbearia
- Endereço
- Telefone
- Horário de funcionamento

### Tela P6 — Agendamentos

- Cards com cliente, data/hora, serviço, duração, telefone.
- Filtros por data (MVP).

---

## 4) Estrutura técnica Angular recomendada

## 4.1 Organização de pastas

```text
src/app/
  core/
    guards/
      auth.guard.ts
      role.guard.ts
    interceptors/
      auth.interceptor.ts
      error.interceptor.ts
    services/
      auth.service.ts
      session.service.ts
    models/
      auth.model.ts
      api-error.model.ts
  shared/
    components/
      app-header/
      bottom-nav/
      loading-overlay/
      empty-state/
      confirm-dialog/
      status-chip/
      schedule-slot/
      service-card/
      professional-card/
      appointment-card/
    pipes/
      currency-brl.pipe.ts
      phone-mask.pipe.ts
      status-label.pipe.ts
    directives/
      role-visible.directive.ts
  features/
    cliente/
      pages/
        servicos-page/
        profissional-page/
        data-horario-page/
        confirmacao-page/
        sucesso-page/
        meus-agendamentos-page/
      components/
        fluxo-stepper/
        cliente-form/
    funcionario/
      pages/
        login-funcionario-page/
        minha-agenda-page/
        detalhe-agendamento-page/
        gerenciar-agenda-page/
    proprietario/
      pages/
        login-proprietario-page/
        dashboard-page/
        funcionarios-page/
        funcionario-form-page/
        servicos-page/
        servico-form-page/
        agendamentos-page/
        configuracoes-page/
  app.routes.ts
```

---

## 4.2 Models (tipagem frontend)

Criar interfaces espelhando a API:

- `AgendamentoRequest`
- `AgendamentoResponse`
- `Agendamento`
- `Servico`
- `Funcionario`
- `Cliente`
- `Proprietario`
- `Barbearia`
- `ApiPageState<T>` (loading/error/data)

> Frontend deve apenas adaptar forma de exibição (ex: formatar data), sem replicar validações de disponibilidade.

---

## 4.3 Estado e comunicação entre telas

- **MVP sem NgRx obrigatório**: usar `signals` ou `BehaviorSubject` por feature.
- `ClienteBookingFlowStore` para manter estado do wizard (serviço/profissional/datahora/dados cliente).
- Limpar store ao concluir ou cancelar fluxo.

---

## 5) Mapeamento de endpoints (backend atual -> telas)

> Base URL sugerida no Angular: `environment.apiBaseUrl`.

## 5.1 Agendamentos

1. `POST /agendamentos`
   - Uso: confirmar novo agendamento (C4).
   - Body: `AgendamentoRequestDTO`.
   - Retorno: `AgendamentoResponseDTO`.

2. `GET /agendamentos?data={LocalDate}&idCliente={UUID}`
   - Uso: “Meus agendamentos” cliente.
   - Retorno: `List<Agendamento>`.

3. `PUT /agendamentos?dataHoraAtual=...&idClienteAtual=...&dataHoraNova=...&idClienteNovo=...`
   - Uso: reagendar em F4/P6.
   - Retorno: `AgendamentoResponseDTO`.

4. `DELETE /agendamentos?dataHora=...&idCliente=...`
   - Uso: cancelamento em C6/F4/P6.
   - Retorno: `204 No Content`.

---

## 5.2 Serviços

1. `POST /servicos`
   - Uso: criar serviço (P4).
   - Retorno: `Servicos`.

2. `GET /servicos?idServico=...&nomeServico=...&precoServico=...`
   - Uso: catálogo C1 e listagem P4.
   - Retorno: `List<Servicos>`.
   - Observação: contrato atual exige os 3 parâmetros; para UX melhor, alinhar endpoint de listagem sem filtros obrigatórios.

3. `PUT /servicos/alterar-nome?nomeServicoAtual=...&nomeServicoNovo=...`
4. `PUT /servicos/alterar-preco?precoServicoAtual=...&precoServicoNovo=...`
5. `PUT /servicos/alterar-descricao?descricaoNova=...` (body contém descrição atual)
6. `PUT /servicos/alterar-duracao?duracaoAtual=...&duracaoNova=...`
   - Uso: edição granular de serviço (P4).

7. `DELETE /servicos?nomeServico=...`
   - Uso: exclusão de serviço (P4).

---

## 5.3 Funcionários

1. `POST /funcionarios`
   - Uso: cadastrar funcionário (P3).

2. `GET /funcionarios?idFuncionario=...&nomeFuncionario=...`
   - Uso: escolher profissional (C2), listagem P3.
   - Observação: alinhar backend para busca por filtros opcionais.

3. `PUT /funcionarios/alterar-nome?...`
4. `PUT /funcionarios/alterar-telefone?...`
   - Uso: edição (P3).

5. `DELETE /funcionarios?nomeFuncionario=...`
   - Uso: exclusão (P3).

---

## 5.4 Clientes

1. `POST /clientes`
   - Uso: cadastro rápido no fluxo C4 (quando necessário).

2. `GET /clientes?idCliente=...&nomeCliente=...`
   - Uso: consulta de dados do cliente para “Meus agendamentos”.

3. `PUT /clientes/alterar-nome?...`
4. `PUT /clientes/alterar-telefone?...`
5. `DELETE /clientes?nomeCliente=...`

---

## 5.5 Proprietários

1. `POST /proprietarios`
2. `GET /proprietarios?nome=...&id_proprietario=...&email=...`
3. `PUT /proprietarios/alterar-nome?...`
4. `PUT /proprietarios/alterar-telefone?...`
5. `PUT /proprietarios/alterar-email?...`
6. `DELETE /proprietarios?nome=...`

Uso principal: configurações (P5) e dados de conta.

---

## 5.6 Barbearias

1. `POST /barbearias`
   - Body: `BarbeariaRequestDTO` (barbearia + proprietário).

2. `GET /barbearias?nomeBarbearia=...&idBarbearia=...&rua=...`
3. `PUT /barbearias/alterar-nome?...`
4. `PUT /barbearias/alterar-horarios-funcionamento?...`
5. `PUT /barbearias/alterar-telefone?...`
6. `PUT /barbearias/alterar-endereco?...`
7. `PUT /barbearias/alterar-proprietario?...`
8. `DELETE /barbearias?nomeBarbearia=...`

Uso principal: tela P5 (configurações do negócio).

---

## 6) Services Angular necessários

## 6.1 `AuthService`

**Responsabilidades (frontend):**
- Login/logout (conforme contrato final do backend).
- Persistência de sessão (token/cookie/sessionStorage).
- Exposição de role atual para guards.

**Métodos sugeridos:**
- `login(credentials)`
- `logout()`
- `getCurrentUser()`
- `hasRole(role)`
- `isAuthenticated()`

---

## 6.2 `AgendamentoService`

- `criarAgendamento(payload)`
- `listarAgendamentosDoCliente(data, idCliente)`
- `alterarAgendamento(params)`
- `cancelarAgendamento(dataHora, idCliente)`

Uso: fluxo cliente + agenda funcionário/proprietário.

---

## 6.3 `FuncionarioService`

- `listar(...)`
- `criar(funcionario)`
- `alterarNome(...)`
- `alterarTelefone(...)`
- `deletar(nomeFuncionario)`

Uso: C2, P3.

---

## 6.4 `ServicoService`

- `listar(...)`
- `criar(servico)`
- `alterarNome(...)`
- `alterarPreco(...)`
- `alterarDescricao(...)`
- `alterarDuracao(...)`
- `deletar(nomeServico)`

Uso: C1 e P4.

---

## 6.5 `ProprietarioService`

- `buscarProprietario(...)`
- `alterarNome(...)`
- `alterarTelefone(...)`
- `alterarEmail(...)`

Uso: P5.

---

## 6.6 Service adicional recomendado (`BarbeariaService`)

Para completar tela de configurações:
- `buscarBarbearia(...)`
- `alterarNome(...)`
- `alterarHorarios(...)`
- `alterarTelefone(...)`
- `alterarEndereco(...)`

---

## 7) Rotas Angular (proposta)

## 7.1 Rotas públicas/cliente

- `/` -> redireciona para `/cliente/agendar/servicos`
- `/cliente/agendar/servicos`
- `/cliente/agendar/profissional`
- `/cliente/agendar/data-horario`
- `/cliente/agendar/confirmacao`
- `/cliente/agendar/sucesso`
- `/cliente/meus-agendamentos`

## 7.2 Rotas funcionário

- `/funcionario/login`
- `/funcionario/minha-agenda`
- `/funcionario/agendamento/:id`
- `/funcionario/gerenciar`

## 7.3 Rotas proprietário

- `/proprietario/login`
- `/proprietario/dashboard`
- `/proprietario/funcionarios`
- `/proprietario/funcionarios/novo`
- `/proprietario/funcionarios/:id/editar`
- `/proprietario/servicos`
- `/proprietario/servicos/novo`
- `/proprietario/servicos/:id/editar`
- `/proprietario/agendamentos`
- `/proprietario/configuracoes`

## 7.4 Guards

- `AuthGuard`: exige sessão autenticada.
- `RoleGuard`: bloqueia rota por papel.
  - funcionário: `ROLE_FUNCIONARIO`
  - proprietário: `ROLE_PROPRIETARIO`

---

## 8) Contratos de UI/UX e comportamento (sem regra de negócio)

## 8.1 Estados padrão de tela

Toda página de dados deve implementar:
- `loading`
- `empty`
- `error`
- `success`

## 8.2 Padrões de feedback

- Sucesso: toast curto + navegação clara.
- Erro: mensagem amigável + detalhe técnico opcional em dev.
- Ações destrutivas: diálogo de confirmação.

## 8.3 Formulários

- Validação primária de UX (required/máscara/formato) apenas para usabilidade.
- Validação final sempre no backend.

## 8.4 Mobile-first

- Breakpoint base: 360–430px.
- Botões full-width, cards empilhados, tipografia legível.
- Tap area mínima 44px.

---

## 9) Guia de implementação (ordem ideal)

## Fase 0 — Preparação

1. Criar projeto Angular com routing e SCSS.
2. Configurar `core`, `shared` e `features`.
3. Configurar `HttpClient`, environments e interceptors.

## Fase 1 — Autenticação e base de navegação

1. Implementar `AuthService`, `SessionService`, `AuthGuard`, `RoleGuard`.
2. Criar telas de login funcionário/proprietário.
3. Implementar redirecionamento por role.
4. Entregar shells e menus por perfil.

## Fase 2 — Dashboard proprietário

1. Construir cards de KPI (agendamentos hoje, serviços ativos, funcionários).
2. Consumir endpoints necessários (mesmo com adaptações temporárias).
3. Implementar atalhos para módulos administrativos.

## Fase 3 — Fluxo cliente (wizard completo)

1. C1 serviços.
2. C2 profissional.
3. C3 data/horário (somente disponibilidade da API).
4. C4 confirmação.
5. C5 sucesso.
6. `Meus agendamentos`.

## Fase 4 — CRUDs do proprietário

1. CRUD Funcionários.
2. CRUD Serviços.
3. Configurações (proprietário + barbearia).
4. Lista de agendamentos administrativos.

## Fase 5 — Fluxo funcionário

1. Minha agenda (dia).
2. Detalhe agendamento.
3. Gerenciar agenda (confirmar/cancelar/reagendar).

## Fase 6 — Qualidade e hardening

1. Ajustar tratamento global de erros.
2. Adicionar testes unitários básicos de services/guards.
3. Revisar acessibilidade mobile.
4. Checklist final com backend.

---

## 10) Lacunas identificadas e recomendações de contrato (backend <-> frontend)

Para o frontend ficar simples e sem lógica de negócio local, recomenda-se priorizar no backend:

1. Endpoint de autenticação explícito (login + retorno do perfil).
2. Endpoint de disponibilidade de agenda (slots livres por profissional/data/serviço).
3. Endpoints de listagem com filtros opcionais (hoje alguns GET exigem múltiplos filtros fixos).
4. Endpoints para dashboard já agregados (contadores prontos).
5. Padronização de response de erro para UX previsível.

---

## 11) Checklist de pronto por módulo

### Cliente
- [ ] Fluxo C1 a C5 completo.
- [ ] Meus agendamentos + cancelamento.
- [ ] Sem regras de disponibilidade no frontend.

### Funcionário
- [ ] Login por role.
- [ ] Minha agenda + detalhe.
- [ ] Gerenciar agenda operacional.

### Proprietário
- [ ] Dashboard com KPIs.
- [ ] CRUD de funcionários.
- [ ] CRUD de serviços.
- [ ] Configurações da barbearia.
- [ ] Tela de agendamentos.

### Técnico
- [ ] Guards por autenticação e role.
- [ ] Interceptor de autenticação/erro.
- [ ] Estrutura de pastas modular.
- [ ] Estados de tela padronizados.

---

## 12) Resultado esperado

Com este escopo, você terá um frontend Angular:

- alinhado ao backend Spring Boot existente,
- orientado a mobile-first,
- separado por papéis (cliente, funcionário, proprietário),
- sem regra de negócio duplicada,
- com roadmap claro de implementação incremental.

