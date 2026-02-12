<h1 align="center">📅 Agendador de Horários – Barbearia</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Status-Em%20Evolução-yellow?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/>
  <img src="https://img.shields.io/badge/H2%20Database-1E90FF?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/JSON-000000?style=for-the-badge&logo=json&logoColor=white"/>
</p>

---

## 📌 Sobre o projeto

Projeto simples de estudo com Spring Boot para praticar um CRUD de:

- **Agendamento**
- **Barbearia**

A proposta é manter uma base fácil de entender e evoluir.

## ⚙️ Funcionalidades atuais

### Agendamento
- ➕ Criar
- 🔍 Buscar por dia
- ✏️ Alterar
- ❌ Deletar

### Barbearia
- ➕ Criar
- 🔍 Buscar por nome
- ✏️ Alterar
- ❌ Deletar

## 🔗 Endpoints principais

### Agendamento
- `POST /agendamentos`
- `GET /agendamentos?data=yyyy-MM-dd`
- `PUT /agendamentos?cliente=...&dataHoraAgendamento=yyyy-MM-ddTHH:mm:ss`
- `DELETE /agendamentos?cliente=...&dataHoraAgendamento=yyyy-MM-ddTHH:mm:ss`

### Barbearia
- `POST /barbearias`
- `GET /barbearias?nomeBarbearia=...`
- `PUT /barbearias?nomeBarbearia=...&rua=...&numeroRua=...`
- `DELETE /barbearias?nomeBarbearia=...`

## 🚧 Status do projeto

Projeto de aprendizado, com foco em clareza do código e evolução gradual.

## 📘 Guia rápido

Veja o passo a passo simples em: `docs/GUIA_PROJETO_E_TESTES.md`.
