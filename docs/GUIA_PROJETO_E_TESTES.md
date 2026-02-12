# Guia simples do projeto (modo iniciante)

Este guia foi simplificado para você estudar com calma e entender o básico de um CRUD com Spring Boot.

---

## 1) Estrutura do projeto

- **Controller**: recebe requisição HTTP.
- **Service**: regra de negócio.
- **Repository**: conversa com o banco.
- **Entity**: representa tabela/objeto.

Fluxo mental:

`Request -> Controller -> Service -> Repository -> Banco`

---

## 2) O que ficou simples agora

## 2.1 Agendamento

Você já tinha um fluxo simples com 4 operações:
- salvar
- buscar por dia
- alterar
- deletar

## 2.2 Barbearia

A Barbearia foi simplificada para o mesmo estilo:
- salvar
- buscar por nome
- alterar (com identificação da barbearia antiga)
- deletar por nome

---

## 3) Endpoints para testar no JSON

## Barbearia

### Criar
`POST /barbearias`

Body exemplo:

```json
{
  "nomeBarbearia": "Barber X",
  "rua": "Rua A",
  "numeroRua": 100,
  "telefoneBarbearia": "11999999999",
  "horarioAbertura": "08:00:00",
  "horarioFechamento": "18:00:00"
}
```

### Buscar
`GET /barbearias?nomeBarbearia=Barber X`

### Alterar
`PUT /barbearias?nomeBarbearia=Barber%20X&rua=Rua%20A&numeroRua=100`

Body exemplo (novos dados):

```json
{
  "nomeBarbearia": "Barber X Atualizada",
  "rua": "Rua B",
  "numeroRua": 200,
  "telefoneBarbearia": "11888888888",
  "horarioAbertura": "09:00:00",
  "horarioFechamento": "19:00:00"
}
```

### Deletar
`DELETE /barbearias?nomeBarbearia=Barber X Atualizada`

---

## 4) Erros comuns de 400 (Bad Request)

Se der 400 no teste JSON, cheque:

1. `numeroRua` é número?
2. Horário está em `HH:mm:ss`?
3. URL está correta?
4. Query params obrigatórios foram enviados?

---

## 5) Próximo passo de aprendizado

Depois que esse CRUD básico estiver firme:
1. adicionar validações com Bean Validation
2. melhorar mensagens de erro
3. criar testes unitários (só quando você quiser)

A ideia agora é primeiro dominar o básico com confiança.
