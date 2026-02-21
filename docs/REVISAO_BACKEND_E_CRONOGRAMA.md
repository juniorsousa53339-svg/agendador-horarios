# Revisão do Backend + Plano de Execução (hoje até 21:00)

> Hora de referência da revisão: **18:37**.
> Objetivo: fechar o backend hoje para iniciar o front na segunda-feira com segurança.

## 1) Diagnóstico rápido do estado atual

### O que já está bom
- Estrutura em camadas (`controller`, `service`, `repository`, `entity`) está montada.
- Já existem testes unitários para serviços (boa base para evoluir rápido).
- Segurança com Spring Security está parcialmente configurada.

### Principais gaps para “backend pronto para front”
1. **Assinaturas quebradas entre Controller e Service** (não compila em vários fluxos de atualização de proprietário).
2. **Regras de atualização invertidas** em vários serviços (código reaplica valor antigo em vez do novo).
3. **Problemas de modelagem/validação JPA** (uso de `org.antlr...NotNull` em entidades).
4. **Endpoints com contrato difícil para front** (uso de entidade inteira em `@RequestParam`).
5. **Padrão HTTP e erro não padronizados** (muito `202 Accepted` e `RuntimeException` genérica).
6. **Lacunas de segurança e autenticação de usuário** (falta fluxo de cadastro/login/seed de usuário para consumo real).

---

## 2) O que falta implementar (por prioridade)

## P0 — BLOQUEADORES (fazer primeiro)

### 2.1 Corrigir incompatibilidade entre Controller e Service (Proprietário)
**Arquivos:**
- `agendador-horarios/src/main/java/com/Luciano/agendador_horarios/controller/ProprietarioController.java`
- `agendador-horarios/src/main/java/com/Luciano/agendador_horarios/service/ProprietarioService.java`

**Problema:**
- Controller envia `Proprietario` + um campo.
- Service espera `String atual, String novo`.

**Implementar:**
- Definir contrato único para update (recomendado: `PUT /proprietarios/{id}` com body de atualização).
- Ajustar controller para chamar exatamente o método existente no service.
- Atualizar testes unitários de `ProprietarioServiceTest` para o novo contrato.

### 2.2 Corrigir updates que gravam valor antigo
**Arquivos:**
- `ClienteService.java`
- `FuncionarioService.java`
- `ServicosService.java`
- `BarbeariaService.java`

**Problema recorrente:**
- Busca registro por valor atual e depois grava no objeto de entrada o mesmo valor antigo.

**Implementar:**
- Identificar registro por `id` (ou chave estável).
- Receber valor novo explicitamente.
- Atualizar campo com o novo valor.
- Salvar a entidade encontrada no banco (não um objeto “solto” do request).

### 2.3 Corrigir bugs de agendamento
**Arquivos:**
- `AgendamentoService.java`
- `AgendamentoRepository.java`
- `AgendamentoController.java`

**Problemas:**
- Query usa tipo incompatível para serviço (`String` vs entidade).
- Método com typo (`findByCliete`).
- Validações invertidas em busca/deleção (lança erro quando encontra).
- Endpoint recebe `Cliente` via `@RequestParam` (ruim para front).

**Implementar:**
- Usar `servico` como entidade na query.
- Renomear método para `findByCliente`.
- Corrigir condição `if (isNull(...))` onde necessário.
- Trocar parâmetros para IDs (`clienteId`, `servicoId`).

---

## P1 — NECESSÁRIO PARA INTEGRAÇÃO LIMPA COM FRONT

### 2.4 Padronizar DTOs de entrada/saída
**Arquivos novos sugeridos:**
- `.../controller/dto/request/*`
- `.../controller/dto/response/*`

**Implementar:**
- Não expor entidade JPA direto no controller.
- Criar DTOs para create/update/list por recurso.
- Adicionar mapeamento simples no service.

### 2.5 Padronizar tratamento de erros
**Arquivo novo sugerido:**
- `.../controller/advice/GlobalExceptionHandler.java`

**Implementar:**
- Criar exceções de domínio (`RecursoNaoEncontradoException`, `ConflitoNegocioException`, etc.).
- Retornar JSON consistente com `timestamp`, `status`, `erro`, `mensagem`, `path`.

### 2.6 Ajustar status HTTP
**Arquivos:** todos os controllers.

**Implementar padrão:**
- `POST` -> `201 Created`
- `GET` -> `200 OK`
- `PUT/PATCH` -> `200 OK`
- `DELETE` -> `204 No Content`

---

## P2 — QUALIDADE (importante, pode iniciar hoje e continuar segunda)

### 2.7 Corrigir validação de entidades
**Arquivos:** todas as entidades em `infrastructure/entity`.

**Problema:**
- Uso de `org.antlr.v4.runtime.misc.NotNull`.

**Implementar:**
- Trocar por `jakarta.validation.constraints.NotNull`.
- Opcional: usar `@NotBlank` para strings, `@Positive` para campos numéricos.

### 2.8 Segurança completa para uso real
**Arquivos:**
- `SecurityConfig.java`
- criar endpoints de auth (`/auth/register`, `/auth/login`) ou carga inicial de usuário.

**Implementar:**
- Definir como o front obtém credenciais válidas.
- Evitar dependência de usuário manual no banco sem documentação.

### 2.9 Documentar contrato da API
**Implementar:**
- Coleção Postman final ou Swagger.
- Lista de payloads final para o front consumir sem retrabalho.

---

## 3) Cronograma detalhado (18:40 -> 21:00)

## 18:40–19:00 (20 min) — “Ajuste de base e compilação”
- [ ] Corrigir incompatibilidades mais críticas de assinatura em proprietário.
- [ ] Corrigir `AgendamentoRepository` (`findByCliete` -> `findByCliente`).
- [ ] Revisar imports de `NotNull` nas entidades.

**Resultado esperado:** projeto compila com menos erro estrutural.

## 19:00–19:40 (40 min) — “Regras de update corretas”
- [ ] Refatorar update de `ClienteService`.
- [ ] Refatorar update de `FuncionarioService`.
- [ ] Refatorar update de `ServicosService`.
- [ ] Refatorar update de `BarbeariaService`.

**Resultado esperado:** update realmente altera valores novos (não os antigos).

## 19:40–20:10 (30 min) — “Agendamento confiável”
- [ ] Corrigir regra de conflito de horário.
- [ ] Corrigir validações invertidas em `deletar` e `buscar`.
- [ ] Ajustar controller para usar IDs em vez de objeto em query param.

**Resultado esperado:** CRUD de agendamento pronto para consumo no front.

## 20:10–20:35 (25 min) — “Contrato para o front”
- [ ] Definir DTO mínimo para 2 recursos principais (Agendamento + Cliente).
- [ ] Ajustar endpoints mais críticos para payload claro e estável.
- [ ] Documentar payloads no README ou arquivo de API.

**Resultado esperado:** front consegue consumir sem conhecer estrutura interna JPA.

## 20:35–20:55 (20 min) — “Tratamento de erros + HTTP”
- [ ] Adicionar `@RestControllerAdvice` mínimo.
- [ ] Padronizar status em controllers principais.
- [ ] Remover `RuntimeException` genérica dos pontos críticos.

**Resultado esperado:** erros previsíveis para o front tratar.

## 20:55–21:00 (5 min) — “Fechamento”
- [ ] Revisar checklist final abaixo.
- [ ] Commit com mensagem clara.
- [ ] Preparar lista de endpoints para começar o front segunda.

---

## 4) Checklist final de “backend pronto para front”
- [ ] Nenhum endpoint principal recebe entidade completa em `@RequestParam`.
- [ ] Todos os updates alteram valor novo corretamente.
- [ ] Erros de negócio retornam JSON padronizado.
- [ ] Status HTTP consistente.
- [ ] Regras de agendamento validadas.
- [ ] Segurança documentada (como o front autentica).
- [ ] Testes de serviço atualizados para os cenários críticos.

---

## 5) Estratégia de segunda-feira (início do front)
- Começar por telas que dependem de endpoints mais estáveis:
  1. Login
  2. Listagem de serviços
  3. Cadastro/listagem de clientes
  4. Agenda do dia
- Evitar começar por funcionalidades ainda sem DTO/erro padronizado.

