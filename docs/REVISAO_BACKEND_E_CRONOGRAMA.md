# Revisão do Backend — somente pendências + plano do Front (segunda)

> Documento atualizado para manter **somente o que falta fazer**.

## 1) O que já foi feito (não repetir)
- Ajustes de build para reduzir erro de ambiente Java/Lombok.
- Checklist inicial e diagnóstico geral do projeto.

---

## 2) Pendências reais do backend (prioridade)

## P0 — Bloqueadores para compilar e integrar

### 2.1 Alinhar `ProprietarioController` com `ProprietarioService`
**Arquivos:**
- `agendador-horarios/src/main/java/com/Luciano/agendador_horarios/controller/ProprietarioController.java`
- `agendador-horarios/src/main/java/com/Luciano/agendador_horarios/service/ProprietarioService.java`

**Falta fazer:**
- Garantir assinatura igual entre controller e service (update de nome/telefone/email).
- Padronizar nomes de parâmetros (`idProprietario` em vez de `id_proprietario`).
- Remover path duplicado (`/proprietarios/alterar-*` dentro de classe já mapeada em `/proprietarios`).

### 2.2 Corrigir updates que ainda salvam valor antigo
**Arquivos:**
- `ClienteService.java`
- `FuncionarioService.java`
- `ServicosService.java`
- `BarbeariaService.java`

**Falta fazer:**
- Buscar entidade persistida e aplicar valor **novo** nela.
- Evitar salvar objeto "solto" vindo do request quando a intenção é alteração parcial.

### 2.3 Corrigir inconsistências no fluxo de agendamento
**Arquivos:**
- `AgendamentoService.java`
- `AgendamentoRepository.java`
- `AgendamentoController.java`

**Falta fazer:**
- Corrigir assinatura de consulta por serviço (tipo compatível com entidade).
- Corrigir typo de repositório (`findByCliete` -> `findByCliente`).
- Corrigir validações invertidas (`isNull`/`nonNull`).
- Trocar `@RequestParam Cliente` por IDs (`clienteId`, `servicoId`).

---

## P1 — Necessário para o Front consumir sem retrabalho

### 2.4 Criar DTOs mínimos
**Falta fazer:**
- DTO de entrada/saída para pelo menos: `Agendamento`, `Cliente`, `Servicos`.
- Evitar expor entidade JPA diretamente no contrato HTTP.

### 2.5 Padronizar retorno de erro
**Falta fazer:**
- Criar `@RestControllerAdvice` com payload fixo de erro.
- Substituir `RuntimeException` genérica por exceções de domínio.

### 2.6 Padronizar status HTTP
**Falta fazer:**
- `POST` 201, `GET` 200, `PUT/PATCH` 200, `DELETE` 204.

---

## P2 — Qualidade e manutenção

### 2.7 Validação das entidades
**Falta fazer:**
- Trocar `org.antlr.v4.runtime.misc.NotNull` por `jakarta.validation.constraints.*`.

### 2.8 Segurança operacional para front
**Falta fazer:**
- Definir fluxo real de autenticação para o front (usuário inicial documentado ou endpoint auth).

### 2.9 Contrato de API versionável
**Falta fazer:**
- Publicar Postman/Swagger atualizado com payloads finais.

---

## 3) Plano de execução de segunda-feira — Front-end

## Bloco 1 (09:00–10:00) — Setup
- Criar projeto front.
- Configurar `baseURL`, cliente HTTP e variáveis de ambiente.
- Criar camada de API (`authApi`, `clientesApi`, `servicosApi`, `agendamentosApi`).

## Bloco 2 (10:00–12:00) — Autenticação + Layout base
- Tela de login com armazenamento de credenciais/token.
- Guardas de rota.
- Layout padrão (menu lateral/topbar + área principal).

## Bloco 3 (13:30–15:00) — Módulo de Serviços
- Listar serviços.
- Criar/editar/excluir serviço.
- Tratar erros com mensagens amigáveis.

## Bloco 4 (15:00–16:30) — Módulo de Clientes
- Listagem com busca.
- Cadastro e edição.
- Exclusão com confirmação.

## Bloco 5 (16:30–18:00) — Agenda
- Agenda do dia.
- Criar e atualizar agendamento.
- Exibir conflitos de horário vindos do backend.

## Bloco 6 (18:00–19:00) — Polimento e validação
- Estados de loading/erro.
- Validação de formulário.
- Revisão de consistência visual e UX básica.

## Bloco 7 (19:00–20:00) — Entrega técnica
- Checklist final de integração.
- README do front com instruções para rodar.
- Lista de pendências para próxima sprint.

---

## 4) Checklist rápido para começar o Front
- [ ] Backend com contratos mínimos estáveis (`Auth`, `Cliente`, `Servicos`, `Agendamento`).
- [ ] Erro padronizado no backend.
- [ ] Rotas críticas testadas no Postman.
- [ ] Ambiente (`.env`) com URL correta.
