# Plano de Arquitetura Front-end Angular + UX/UI

Este documento traduz o estado atual do backend Java/Spring para um plano executável de produto, UX/UI (Figma) e arquitetura Angular desacoplada.

## 1) Entendimento do sistema atual (backend)

### 1.1 O que a aplicação faz hoje
O sistema é uma API de gestão para barbearia com foco em:
- cadastro e manutenção de clientes, funcionários, proprietários, serviços e barbearias;
- criação/consulta/alteração/cancelamento de agendamentos;
- controle de conflito de horário por funcionário.

### 1.2 Módulos principais no backend
- **Agendamentos**: regras de criação, conflito de horário e remarcação.
- **Cadastros base**: cliente, funcionário, serviço, proprietário e barbearia.
- **Segurança**: estrutura de `UserDetailsService`, roles e CORS, porém sem fluxo real de login/token ativo.
- **Tratamento de erros**: `@RestControllerAdvice` para padronização parcial.

### 1.3 Entidades principais
- `Usuario` (credenciais + role).
- `Role` (PROPRIETARIO/FUNCIONARIO).
- `Cliente`.
- `Funcionario`.
- `Servicos`.
- `Proprietario`.
- `Barbearia`.
- `Agendamento` (relaciona cliente + funcionário + serviço + data/hora).

### 1.4 Diagnóstico técnico importante para o front
- O `SecurityFilterChain` está com `anyRequest().permitAll()`. Ou seja, **o backend ainda não exige autenticação real**, mesmo com `@PreAuthorize` no código.
- Existem `@PreAuthorize` com nomenclaturas inconsistentes (`hasRole('Proprietario')` vs `hasRole('PROPRIETARIO')`), o que pode quebrar autorização quando autenticação estiver ativa.
- Em alguns endpoints, há contratos pouco amigáveis ao front:
  - filtros via múltiplos query params obrigatórios;
  - naming inconsistente de parâmetros (ex.: `TelefoneNovo`);
  - respostas de consulta retornando entidade direta em vez de DTO.

---

## 2) Definição de escopo funcional (produto)

### 2.1 MVP funcional recomendado

#### Área A — Experiência do Cliente (pública)
1. Visualizar lista de serviços.
2. Visualizar lista de profissionais.
3. Selecionar serviço + profissional + data/hora.
4. Informar dados pessoais mínimos (nome + telefone).
5. Confirmar agendamento e receber protocolo.

#### Área B — Funcionário (área logada)
1. Login.
2. Visualizar agenda do dia/semana.
3. Filtrar por data.
4. Ver detalhes do agendamento (cliente, serviço, horário, status).

#### Área C — Proprietário (área logada)
1. Login.
2. Dashboard operacional.
3. CRUD de serviços.
4. CRUD de funcionários.
5. Visão geral da agenda.
6. (Opcional MVP+) Gestão de barbearia (dados do estabelecimento).

#### Área D — Plataforma/infra
1. Autenticação e autorização reais.
2. Tratamento consistente de erros para UX.
3. Observabilidade básica (logs, estados de loading/erro no front).

### 2.2 Itens fora do MVP (roadmap)
- pagamentos;
- notificações WhatsApp/SMS/e-mail;
- fidelidade/cuponagem;
- multiunidade avançado com tenancy robusto;
- relatórios BI.

---

## 3) Planejamento de telas (UX/UI)

## 3.1 Módulo de autenticação
### Tela: Login
- Campos: usuário/e-mail, senha.
- Ações: entrar, recuperar senha (placeholder se não existir backend).
- Estados: erro credencial, carregando.

**Fluxo**
- Login válido + role FUNCIONARIO → Agenda Funcionário.
- Login válido + role PROPRIETARIO → Dashboard Proprietário.

## 3.2 Módulo cliente (público)
### Tela: Agendar horário (wizard em 4 passos)
1. Escolher serviço.
2. Escolher profissional.
3. Escolher data e horário.
4. Confirmar dados do cliente.

### Tela: Confirmação
- Número do agendamento.
- Resumo: serviço, profissional, data/hora, cliente.
- CTA: novo agendamento.

**Fluxo**
- Landing/Agendar → Passos 1..4 → Confirmação.

## 3.3 Módulo funcionário
### Tela: Agenda do funcionário
- Filtro por data.
- Lista por horários/cards.
- Status visual (confirmado, cancelado, concluído futuro).

### Tela: Detalhe do agendamento (drawer/modal)
- Dados do cliente.
- Serviço e duração.
- Observações (campo futuro).

**Fluxo**
- Login → Agenda → Abrir detalhe.

## 3.4 Módulo proprietário
### Tela: Dashboard
- KPIs: agendamentos do dia, taxa ocupação, top serviços.
- Widget agenda do dia.
- Atalhos para gestão.

### Tela: Gestão de Funcionários (CRUD)
- Listagem + busca.
- Criar/editar/excluir em modal ou página dedicada.

### Tela: Gestão de Serviços (CRUD)
- Listagem com preço e duração.
- Criar/editar/excluir.

### Tela: Agenda Geral
- Filtros por profissional e data.
- Visão em tabela ou timeline.

### Tela: Configuração da Barbearia (opcional no MVP)
- Nome, endereço, telefone, horário funcionamento.

**Fluxo**
- Login → Dashboard → (Funcionários | Serviços | Agenda | Configuração).

---

## 4) Direcionamento para Figma

## 4.1 Organização de páginas/frames
No Figma, separar em páginas:
1. `00_Foundations` (cores, tipografia, espaçamento, grid).
2. `01_Components` (botões, inputs, cards, tabelas, modais).
3. `02_Patterns` (shell com sidebar/topbar, empty states, feedback).
4. `03_Screens_Cliente`.
5. `04_Screens_Funcionario`.
6. `05_Screens_Proprietario`.
7. `06_Prototype_Flows`.

## 4.2 Componentes visuais reutilizáveis
- Botão (primary/secondary/ghost/danger).
- Input (text, phone, select, date/time).
- Card de serviço.
- Card de agendamento.
- Tabela com paginação.
- Modal padrão de confirmação.
- Toast/alerta.
- Badge de status.
- Skeleton/loading.

## 4.3 Layout base
- **Desktop app interno**: sidebar fixa + topbar + conteúdo central.
- **Fluxo público cliente**: layout centrado, progress stepper, CTA forte.
- **Mobile-first** para fluxo de agendamento.

## 4.4 Design system básico
- Escala de espaçamento 4/8/12/16/24/32.
- Tokens de cor:
  - primária (marca),
  - neutras (fundo/texto),
  - semânticas (sucesso/erro/alerta/info).
- Tipografia com hierarquia clara (H1..H6, body, caption).
- Estados de componente (default/hover/focus/disabled/error).

---

## 5) Componentização em Angular

## 5.1 Estrutura de pastas recomendada
```txt
src/app/
  core/
    config/
    http/
    auth/
    guards/
    interceptors/
    layout/
  shared/
    ui/
      button/
      input/
      select/
      modal/
      table/
      badge/
      toast/
      empty-state/
      loading/
    pipes/
    directives/
    utils/
    models/
  features/
    auth/
      pages/login/
      data-access/
      facade/
    cliente-agendamento/
      pages/agendar/
      pages/confirmacao/
      components/steps/
      data-access/
      facade/
    funcionario-agenda/
      pages/agenda/
      components/agendamento-detalhe/
      data-access/
      facade/
    proprietario/
      pages/dashboard/
      pages/funcionarios/
      pages/servicos/
      pages/agenda-geral/
      pages/configuracoes/
      components/
      data-access/
      facade/
```

## 5.2 Componentes principais por domínio
- `AgendarWizardComponent`.
- `ServicoCardListComponent`.
- `FuncionarioSelectComponent`.
- `AgendaTimelineComponent`.
- `KpiCardComponent`.
- `FuncionarioFormComponent`.
- `ServicoFormComponent`.

## 5.3 Componentes compartilhados
- `AppShellComponent`, `SidebarComponent`, `TopbarComponent`.
- `UiButtonComponent`, `UiInputComponent`, `UiSelectComponent`.
- `UiModalComponent`, `UiTableComponent`, `UiToastComponent`.
- `PageHeaderComponent`, `ConfirmDialogComponent`, `LoadingStateComponent`.

---

## 6) Arquitetura de front-end

## 6.1 Camadas recomendadas no Angular
- **Page (container)**: orquestra estado/fluxo.
- **Facade**: simplifica caso de uso para a página.
- **Data-access service**: comunicação HTTP com API.
- **UI components (presentational)**: sem regra de negócio.

## 6.2 Serviços centrais
- `AuthService`: login/logout, sessão, role atual.
- `SessionStorageService`: token/usuário.
- `AgendamentoApiService`, `ServicoApiService`, `FuncionarioApiService`, etc.
- `NotificationService`: toasts globais.

## 6.3 Guards e interceptors
- `AuthGuard`: bloqueia rotas privadas.
- `RoleGuard`: separa funcionário/proprietário.
- `AuthInterceptor`: injeta token quando houver autenticação real.
- `ErrorInterceptor`: normaliza mensagens de erro da API.

## 6.4 Separação domínio vs UI
- Modelos de domínio em `shared/models`.
- DTOs de API em `features/*/data-access/dto`.
- Mapper DTO -> Model para não acoplar tela ao contrato bruto da API.

## 6.5 Boas práticas
- Forms reativos com validação e mensagens padronizadas.
- OnPush + trackBy em listas.
- Tipagem estrita (`strict` TS).
- Evitar lógica em template; manter no componente/facade.
- Testes unitários em facades/services críticos.

---

## 7) Integração front-end ↔ backend

## 7.1 Estratégia de consumo de API
1. Criar `ApiClient` base com `environment.apiUrl`.
2. Criar serviços por agregado (`agendamentos`, `servicos`, `funcionarios`, etc.).
3. Padronizar tratamento de erro HTTP (400/401/403/404/409/500).
4. Mapear contratos instáveis para DTOs internos.

## 7.2 Estratégia de autenticação
### Situação atual
- Backend possui peças de segurança (roles, user details), **mas sem autenticação efetiva na cadeia HTTP**.

### Recomendação
- Implementar autenticação JWT no backend (endpoint `/auth/login`, emissão + refresh token).
- Front armazena access token (preferencialmente memória + refresh em cookie httpOnly se possível).
- Guards utilizam claims/role.

## 7.3 Fluxo completo recomendado
1. Usuário faz login.
2. Front recebe token + perfil.
3. Interceptor anexa `Authorization: Bearer ...`.
4. Backend valida token, role e executa endpoint.
5. Front exibe feedback consistente (sucesso/erro), atualiza estado e UI.

## 7.4 Riscos de integração (atenção)
1. **Contrato de API inconsistente** (query params obrigatórios em excesso).
2. **Ausência de endpoint de login/token**.
3. **Tipos potencialmente desalinhados** em alguns trechos históricos (UUID vs numérico em exemplos antigos).
4. **Retorno de entidades JPA diretamente** em vez de DTO em múltiplos endpoints.
5. **Inconsistência de nomenclatura** em campos e parâmetros.

## 7.5 Plano de mitigação
- Definir contrato OpenAPI fechado antes da implementação do front final.
- Criar “camada anti-corrupção” no Angular (mappers + DTOs).
- Priorizar estabilização de autenticação no backend antes da sprint de telas privadas.

---

## 8) Sequência recomendada de execução (prática)
1. **Sprint 0**: alinhar contrato API + autenticação.
2. **Sprint 1**: Design System + telas públicas (cliente).
3. **Sprint 2**: área funcionário (agenda).
4. **Sprint 3**: área proprietário (dashboard + CRUDs).
5. **Sprint 4**: endurecimento (erros, loading, testes, observabilidade).

---

## 9) Checklist para início da implementação
- [ ] Definir endpoints finais de autenticação.
- [ ] Congelar payloads/responses principais.
- [ ] Criar projeto Angular com estrutura por feature.
- [ ] Montar biblioteca de componentes base.
- [ ] Prototipar fluxos no Figma com validação rápida.
- [ ] Implementar integração incremental por módulo.
