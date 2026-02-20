# 💈 Barber Pro System — Guia Final de Projeto, Execução e Apresentação

> **Status:** ✅ Arquitetura finalizada, segurança aplicada, API pronta para integração com front-end.
>
> Este documento foi preparado para servir como material de execução técnica **e** de apresentação para recrutadores.

---

## 1) 📌 Visão Geral do Produto

O **Barber Pro System** é uma API REST para gestão de barbearias, com foco em regras de negócio, segurança e escalabilidade.

### O sistema cobre:
- Cadastro e gestão de **barbearias**.
- Gestão de **proprietários**, **funcionários** e **clientes**.
- Gestão de **serviços** (nome, descrição e preço).
- Controle de **agendamentos** com validação de conflitos de horário.
- Segurança com autenticação/autorização por perfil.

### Diferenciais técnicos
- Arquitetura em camadas (`Controller`, `Service`, `Repository`, `Entity`).
- Regras críticas protegidas com validações e fail-fast.
- Testes automatizados com JUnit 5 + Mockito.
- Banco H2 para desenvolvimento local e iteração rápida.

---

## 2) 🧱 Arquitetura e Organização

### Camadas
| Camada | Responsabilidade | Resultado prático |
| :--- | :--- | :--- |
| **Controller** | Receber requisições HTTP | Endpoints limpos e previsíveis para o front-end |
| **Service** | Regras de negócio | Validação de regras antes de persistir |
| **Repository** | Acesso ao banco | Queries declarativas com Spring Data JPA |
| **Entity** | Modelo de domínio | Representação consistente da base relacional |

### Fluxo padrão da requisição
1. Requisição chega no **Controller**.
2. Controller chama o **Service**.
3. Service valida regra de negócio.
4. Service persiste/busca via **Repository**.
5. Controller devolve resposta HTTP padronizada.

---

## 3) 🔐 Segurança Implementada

A aplicação aplica controle de acesso por perfil (roles):
- `PROPRIETARIO`
- `FUNCIONARIO`

### Estratégia de segurança
- Endpoints protegidos por autorização de perfil.
- Senhas protegidas com `BCryptPasswordEncoder`.
- Regras de acesso aplicadas na camada de serviço.

### Resultado
- Operações sensíveis (alteração, deleção e gestão administrativa) exigem perfil autorizado.
- Redução de risco de uso indevido da API.

---

## 4) 🗂️ Banco de Dados (H2 para desenvolvimento)

Configuração para desenvolvimento local:
- Banco H2 em arquivo local (persistente durante o ciclo de desenvolvimento).
- Console H2 habilitado para inspeção rápida.
- JPA/Hibernate com atualização de schema para agilidade em dev.

### Benefícios práticos
- Facilidade para testar sem depender de infraestrutura externa.
- Produtividade para validar cenários rapidamente com Postman e testes.

---

## 5) ✅ Regras de Negócio Finalizadas

### Agendamentos
- Não permite conflito de horário para o mesmo contexto de atendimento.
- Permite busca de agendamentos por dia.
- Permite alteração mantendo rastreabilidade do registro.

### Barbearia
- Evita cadastro duplicado por combinação de dados críticos.
- Permite atualização de nome, horários, telefone, endereço e proprietário.

### Demais entidades
- Cliente, funcionário, proprietário e serviços com operações de cadastro, busca, atualização e exclusão.

---

## 6) 🧪 Estratégia de Testes

### Stack
- `JUnit 5`
- `Mockito`

### Padrão adotado
1. **Arrange**: preparar contexto e mocks.
2. **Act**: executar método alvo.
3. **Assert**: validar retorno e interações.

### Cobertura de cenários críticos
- Tentativas inválidas que devem lançar exceção.
- Garantia de que `save()` não ocorre em cenários de erro.
- Fluxos de sucesso para operações principais.

---

## 7) 🚀 Como Rodar o Projeto (Passo a Passo para iniciantes)

### Pré-requisitos
- Java 17+
- Maven 3.9+
- IDE (IntelliJ, VS Code ou Eclipse)

### Execução local
1. Abra o projeto.
2. No terminal, entre na pasta do backend:
   ```bash
   cd agendador-horarios
   ```
3. Rode os testes:
   ```bash
   mvn test
   ```
4. Suba a aplicação:
   ```bash
   mvn spring-boot:run
   ```
5. Acesse:
   - API: `http://localhost:8080`
   - Console H2: `http://localhost:8080/h2-console`

### Dica para demonstração
- Deixe uma coleção Postman preparada com os fluxos: cadastro → consulta → atualização → deleção.

---

## 8) 🧭 Cronograma de Execução (Amanhã, 14h–18h)

> Objetivo: fechar backend para integração com front-end no mesmo dia.

### 14:00–14:30 — Preparação e validação inicial
- Atualizar branch local.
- Rodar build e testes.
- Subir API local.
- Validar acesso ao H2 Console.

### 14:30–15:20 — Revisão de contratos da API
- Conferir payloads e respostas dos endpoints principais.
- Garantir consistência de status HTTP.
- Validar parâmetros e filtros mais usados pelo front.

### 15:20–16:10 — Segurança e perfis
- Validar fluxo de autenticação.
- Testar permissões por perfil (`PROPRIETARIO`, `FUNCIONARIO`).
- Verificar endpoints críticos bloqueados para usuário sem autorização.

### 16:10–17:00 — Testes funcionais ponta a ponta (manual)
- Executar roteiro completo no Postman:
  1. Criar proprietário
  2. Criar barbearia
  3. Criar serviço
  4. Criar cliente
  5. Criar agendamento
  6. Atualizar e excluir registros
- Registrar evidências (prints e respostas).

### 17:00–17:40 — Preparação para apresentação
- Revisar README e este guia.
- Organizar storytelling técnico (problema → solução → diferenciais).
- Separar exemplos de regras de negócio relevantes.

### 17:40–18:00 — Checklist final e congelamento
- Rodar testes finais.
- Confirmar que endpoints críticos estão estáveis.
- Fechar pendências e preparar handoff para o front-end.

---

## 9) 🎤 Roteiro de Apresentação para Recrutador (3–5 minutos)

### Estrutura sugerida
1. **Contexto**: “Desenvolvi uma API de agendamento para barbearia com foco em regras de negócio e segurança.”
2. **Arquitetura**: “Usei separação em camadas para manter manutenção e escalabilidade.”
3. **Regras de negócio**: “Implementei validações para evitar conflito de horário e inconsistências.”
4. **Qualidade**: “Cobri cenários críticos com testes unitários usando JUnit e Mockito.”
5. **Próximo passo**: “Backend pronto para integração com front-end e evolução para produção.”

### Pontos que impressionam
- Capacidade de estruturar projeto em camadas.
- Preocupação com regras reais do negócio.
- Segurança por perfil de usuário.
- Testes automatizados e organização técnica.

---

## 10) ✅ Checklist de “Backend Redondo”

- [x] Build configurada com Java/Spring compatíveis.
- [x] Banco de desenvolvimento configurado e acessível.
- [x] Endpoints principais funcionais.
- [x] Segurança com autorização por perfil.
- [x] Regras de negócio críticas implementadas.
- [x] Testes automatizados cobrindo cenários centrais.
- [x] Documentação pronta para integração e apresentação.

---

## 11) Próximas evoluções (pós-entrega)

- Adicionar documentação OpenAPI/Swagger.
- Criar DTOs dedicados para entrada e saída.
- Padronizar payload de erro com `ControllerAdvice`.
- Adicionar testes de integração com banco.
- Criar pipeline CI para validação automática em PR.

---

Se você for apresentar esse projeto, use este documento como guia principal e faça uma demo curta com Postman mostrando um fluxo completo de negócio.
