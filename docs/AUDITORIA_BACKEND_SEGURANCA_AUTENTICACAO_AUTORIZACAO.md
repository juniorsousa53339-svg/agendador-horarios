# Auditoria Técnica do Backend (Java/Spring)

> Escopo: autenticação, autorização, segurança da API, arquitetura e bugs de implementação.
> Perfil de revisão: feedback de sênior para desenvolvedor júnior.

---

## 0) Resumo executivo (importante)

### Estado atual
O projeto já tem uma base boa de separação em camadas (`controller`, `service`, `repository`) e preocupação com regras de negócio em alguns pontos (ex.: conflito de horário em agendamento). Porém, do ponto de vista de **segurança de produção**, o sistema está em estado **incompleto e vulnerável**.

### Principais riscos críticos
1. **API totalmente aberta** (`anyRequest().permitAll()`) mesmo com uso de `@PreAuthorize`.
2. **Sem fluxo real de autenticação** (não existe endpoint de login JWT, nem filtro de validação de token).
3. **Configuração de autorização inconsistente** (uso misto de `hasRole('Proprietario')` e `hasRole('PROPRIETARIO')`).
4. **Tratamento de erros com RuntimeException genérica**, prejudicando rastreabilidade e previsibilidade de contratos.
5. **Contratos HTTP inconsistentes** (parâmetros obrigatórios demais, nomenclaturas heterogêneas, retorno de entidade JPA em endpoints públicos).

### Risco de negócio
- Exposição indevida de dados;
- operação administrativa sem autenticação real;
- alto risco de retrabalho ao integrar com front-end;
- segurança “aparente” (código parece seguro, mas não está efetivamente protegido na cadeia HTTP).

---

## 1) 🔐 Auditoria de autenticação

## 1.1 O que está correto
- Existe entidade de usuário com `username`, `password` e `role`.
- Existe `PasswordEncoder` com BCrypt (bom padrão).
- Existe `CustomUserDetailsService` para integração com Spring Security.

## 1.2 Problemas encontrados

### A-01 (CRÍTICO) — Não existe autenticação efetiva na API
**Problema:** o `SecurityFilterChain` libera todas as rotas.

**Por que está errado:** sem exigir autenticação na camada HTTP, você não cria um perímetro de segurança real.

**Risco real:** qualquer cliente consegue chamar endpoints sensíveis sem login.

**Correção recomendada:**
- Definir rotas públicas explícitas (`/auth/login`, `/h2-console` em dev, docs se necessário).
- Exigir autenticação para o restante (`.anyRequest().authenticated()`).
- Configurar política stateless se usar JWT.

### A-02 (CRÍTICO) — Não há endpoint de login/token
**Problema:** existe infraestrutura parcial de user details, mas não existe API de autenticação com emissão de token.

**Por que está errado:** o sistema de roles precisa de identidade autenticada para funcionar corretamente.

**Risco real:** front-end não consegue autenticar de forma segura; tendência a “gambiarras” (liberar tudo no backend).

**Correção recomendada:**
- Criar módulo `auth` com:
  - `POST /auth/login`;
  - geração de access token JWT com expiração curta;
  - refresh token (tabela própria + revogação).

### A-03 (ALTO) — Ausência de validação de token (filtro)
**Problema:** não há filtro JWT no chain (ex.: `OncePerRequestFilter`) para extrair/validar bearer token.

**Por que está errado:** sem filtro, não existe autenticação stateless.

**Risco real:** API não diferencia usuário autenticado de anônimo.

**Correção recomendada:**
- Criar `JwtAuthenticationFilter`;
- validar assinatura, expiração e claims;
- popular `SecurityContext` com principal e authorities.

### A-04 (MÉDIO) — Estratégia de sessão/expiração não definida
**Problema:** não há política formal de expiração de sessão, rotação de token ou logout com revogação.

**Risco real:** sessões longas aumentam janela de ataque em caso de vazamento.

**Correção recomendada:**
- Access token curto (ex.: 15 min).
- Refresh token com rotação e revogação.
- Logout invalidando refresh token no servidor.

### A-05 (MÉDIO) — Registro de usuário não estruturado
**Problema:** não existe fluxo claro de registro/provisionamento de usuários com validação e hashing explícito no caso de uso.

**Risco real:** risco de persistir senha sem hash em implementações futuras apressadas.

**Correção recomendada:**
- Use case explícito de criação de usuário com:
  - validação de unicidade de username;
  - hash BCrypt no service;
  - auditoria de criação.

## 1.3 Checklist mínimo de autenticação para produção
- [ ] Login com JWT implementado.
- [ ] Refresh token com rotação.
- [ ] Revogação de sessão/logout.
- [ ] Chaves seguras por ambiente (não hardcoded).
- [ ] `anyRequest().authenticated()` fora de ambientes de laboratório.

---

## 2) 🛡️ Auditoria de autorização

## 2.1 O que está correto
- Há uso de `@EnableMethodSecurity`.
- Serviços usam `@PreAuthorize` em operações sensíveis.
- Existência de enum de roles.

## 2.2 Problemas encontrados

### Z-01 (CRÍTICO) — Autorização declarada, mas inefetiva na prática
**Problema:** com chain liberando tudo, autorização por método pode gerar falsa sensação de segurança.

**Risco real:** time acredita que endpoint está protegido quando não está.

**Correção recomendada:** ativar autenticação real + testes de autorização por perfil.

### Z-02 (ALTO) — Inconsistência no nome das roles
**Problema:** uso misto de `hasRole('Proprietario')` (camelcase) e `hasRole('PROPRIETARIO')` (uppercase).

**Por que está errado:** `hasRole` é sensível ao valor final da authority (`ROLE_*`).

**Risco real:** bloqueios inesperados ou autorizações incorretas após ativar autenticação real.

**Correção recomendada:**
- Padronizar para `hasRole('PROPRIETARIO')` e `hasRole('FUNCIONARIO')`.
- Criar teste automatizado para cada endpoint sensível com usuário de role correta/incorreta.

### Z-03 (MÉDIO) — Escopo de acesso por recurso não está modelado
**Problema:** autorização está baseada apenas em role global.

**Risco real:** funcionário pode acessar dados que não deveria (ex.: dados de outras unidades, se houver multiunidade).

**Correção recomendada:**
- Implementar autorização contextual (ownership/scope):
  - por `idFuncionario` do próprio usuário;
  - por barbearia/unidade associada.

---

## 3) 🧱 Problemas estruturais do backend

## 3.1 Pontos positivos
- Camadas separadas em pacotes coerentes.
- Repositórios Spring Data com nomenclatura clara em boa parte.
- Uso de DTO em agendamento (bom começo para contratos externos).

## 3.2 Fragilidades

### E-01 (ALTO) — Controllers retornando entidades JPA diretamente
**Problema:** vários endpoints retornam entidades (`Cliente`, `Funcionario`, `Servicos`, etc.) direto.

**Risco real:** acoplamento forte entre schema interno e API pública, vazamento acidental de campos, quebra de contrato ao refatorar entidade.

**Solução ideal:**
- DTOs para request/response em **todos** os módulos.
- Mappers dedicados (manual/MapStruct).

### E-02 (MÉDIO) — Regras de negócio e contrato HTTP misturados
**Problema:** excesso de query params obrigatórios em busca/alteração.

**Risco real:** UX ruim no consumo da API e lógica frágil de integração.

**Solução ideal:**
- Filtros opcionais em endpoints de busca.
- `PATCH` com payload para alterações parciais em vez de muitos query params.

### E-03 (MÉDIO) — Exceções de domínio pouco padronizadas
**Problema:** mistura de `RuntimeException` genérica com `ResponseStatusException`.

**Risco real:** respostas inconsistentes, difícil padronização de erro no front.

**Solução ideal:**
- Criar exceções de domínio (`NotFoundException`, `ConflictException`, etc.).
- Handler global traduzindo para payload único de erro.

### E-04 (MÉDIO) — Segurança e autenticação não encapsuladas por módulo
**Problema:** falta módulo `auth` coeso (controller + service + token provider + repos refresh).

**Risco real:** crescimento desorganizado e risco de falhas em manutenção.

**Solução ideal:**
- Estruturar `features/auth` com responsabilidades claras.

---

## 4) ⚠️ Bugs e inconsistências gerais

### B-01 (ALTO) — Inconsistência de parâmetros de API
- Exemplo: `TelefoneNovo` com inicial maiúscula em endpoint de cliente.

**Risco:** erro de integração front/back e aumento de bugs de contrato.

**Correção:** padronizar convenção (`camelCase`) em todos os params/campos.

### B-02 (ALTO) — Role inconsistente em `BarbeariaService`
- Uso de `hasRole('Proprietario')` em vários métodos.

**Risco:** autorização falhando quando segurança real estiver ativa.

**Correção:** padronizar role e cobrir com testes.

### B-03 (MÉDIO) — Testes comentados e desalinhados com tipos atuais
- Há testes de agendamento comentados e usando IDs numéricos legados em vez de UUID.

**Risco:** queda de confiança no sistema; regressões passam sem detecção.

**Correção:** reativar testes com UUID e cenários reais de segurança/autorização.

### B-04 (MÉDIO) — Erro semântica HTTP em operações de criação
- Diversos cadastros retornam `202 Accepted` em vez de `201 Created`.

**Risco:** semântica REST inconsistente e confusão para clientes da API.

**Correção:** retornar `201` em criação síncrona e incluir localização quando aplicável.

### B-05 (MÉDIO) — Ausência de validações de entrada em alguns controllers
- Alguns endpoints recebem entidade direta sem `@Valid`.

**Risco:** dados inválidos podem avançar até camadas internas.

**Correção:** `@Valid` + DTO + validações explícitas.

---

## 5) 🔧 Correções sugeridas (prática por prioridade)

## P0 — Segurança mínima obrigatória (fazer primeiro)
1. Implementar login JWT (`/auth/login`) com senha BCrypt.
2. Implementar filtro JWT + `SecurityContext`.
3. Alterar chain para:
   - rotas públicas explícitas;
   - `anyRequest().authenticated()`.
4. Padronizar roles e `@PreAuthorize`.
5. Escrever testes de autorização por perfil.

## P1 — Contrato e robustez
1. Migrar responses para DTO em todos os controllers.
2. Padronizar erros com exceções de domínio.
3. Revisar semântica HTTP (`201`, `200`, `204`, `404`, `409`).
4. Uniformizar parâmetros e naming.

## P2 — Evolução arquitetural
1. Criar módulo auth completo (login/refresh/logout/revogação).
2. Introduzir autorização por escopo de recurso (não só role global).
3. Fortalecer observabilidade (logs estruturados + correlação de request).

---

## 6) 🎓 Mentoria: o que um júnior tende a errar aqui e como evitar

## Erro mental #1 — “Coloquei `@PreAuthorize`, então está seguro”
**Ajuste de pensamento:** segurança é cadeia completa: autenticação + autorização + políticas de rota + testes.

## Erro mental #2 — “Funciona no Postman, então está pronto”
**Ajuste de pensamento:** produção exige previsibilidade de contrato, semântica HTTP correta e padronização de erros.

## Erro mental #3 — “Entidade JPA já serve como resposta da API”
**Ajuste de pensamento:** API pública deve ser desacoplada do modelo de persistência.

## Erro mental #4 — “Depois eu arrumo os testes”
**Ajuste de pensamento:** em segurança, sem testes de autorização você não sabe se está protegido.

## Boas práticas para levar para próximos projetos
1. Comece por **threat model** simples (o que proteger, de quem, como).
2. Defina contrato OpenAPI cedo.
3. Implemente auth de forma coesa desde o início (login, token, expiração, refresh, revogação).
4. Padronize exceções e respostas de erro.
5. Trate segurança como requisito funcional de primeira classe.

---

## 7) Referência de arquitetura recomendada (futuro próximo)

```txt
src/main/java/.../
  auth/
    AuthController
    AuthService
    JwtService
    JwtAuthenticationFilter
    RefreshTokenService
    dto/
  security/
    SecurityConfig
    CustomUserDetailsService
  modules/
    agendamento/
      controller/
      service/
      repository/
      dto/
      mapper/
    cliente/
    funcionario/
    servico/
    proprietario/
    barbearia/
  common/
    exception/
    advice/
    validation/
```

Objetivo dessa estrutura: separar autenticação, domínio e infraestrutura transversal para reduzir acoplamento e facilitar evolução segura.
