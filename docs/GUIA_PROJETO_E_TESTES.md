# 💈 SISTEMA DE AGENDAMENTO PARA BARBEARIA
> **Status do Projeto:** 🚀 Em Desenvolvimento / Estruturado para Produção

---

## 🔵 1. VISÃO GERAL
Este projeto é uma **API REST** robusta desenvolvida com **Spring Boot** para gerenciar o ecossistema completo de uma barbearia moderna.

### 🎯 O sistema permite:
* 📅 **Controle de agendamentos** precisos.
* 💇 **Gestão de serviços** oferecidos.
* 👥 **Gestão de funcionários** e escalas.
* 🧔 **Gestão de proprietários** e perfis.
* 🏢 **Gestão da barbearia** (unidades e dados).

### 🚀 Objetivo do Projeto
- [x] Arquitetura limpa e organizada.
- [x] Regras de negócio rigorosas.
- [x] Testes unitários com alta cobertura.
- [x] Preparação para escalabilidade.

---

## 🧱 2. ARQUITETURA DA APLICAÇÃO
A aplicação segue o padrão de camadas do Spring, garantindo o **Single Responsibility Principle (SRP)**.



| Camada | Responsabilidade | Observação |
| :--- | :--- | :--- |
| **Controller** | Gateway de entrada (HTTP) | Sem lógica de negócio. |
| **Service** | O "Cérebro" do sistema | **Onde a mágica acontece.** |
| **Repository** | Persistência de dados | Abstração via Spring Data JPA. |
| **Entity** | Modelo de Domínio | Mapeamento ORM/Tabelas. |

---

## 📋 3. REGRAS DE NEGÓCIO (Business Rules)
O sistema não é um simples CRUD. Ele possui inteligência para evitar erros operacionais:

### 📅 Agendamento
* **Conflito Zero:** Proibido dois agendamentos no mesmo horário.
* **Rastreabilidade:** Mantém o ID original em alterações.
* **Filtros:** Busca otimizada por data.

### 🏢 Barbearia
* **Unicidade:** Bloqueia cadastros duplicados (Combinação Nome + Proprietário).
* **Flexibilidade:** Edição completa de horários, endereços e contatos.

---

## 🧪 4. TESTES AUTOMATIZADOS
A qualidade do código é garantida por uma suíte de testes na camada de **Service**, utilizando o stack:
`JUnit 5` + `Mockito`.



### 🧠 Estratégia de Testes:
1.  **Arrange:** Preparação dos mocks e dados.
2.  **Act:** Execução do método testado.
3.  **Assert:** Verificação dos resultados e comportamentos.

**Cenários Críticos Validados:**
* Falha ao tentar agendar horário ocupado.
* Garantia de que o método `.save()` **não** é chamado se houver erro de validação.
* Preservação da integridade dos dados em atualizações.

---

## 🔐 5. EVOLUÇÃO PLANEJADA (Roadmap)
* [ ] **Segurança:** Implementação de JWT e Spring Security.
* [ ] **Arquitetura:** Introdução de DTOs e MapStruct.
* [ ] **Banco