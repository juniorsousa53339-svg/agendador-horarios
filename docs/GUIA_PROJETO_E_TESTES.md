# Guia do Projeto e dos Testes (modo mentoria)

Este documento foi feito para você entender o projeto de ponta a ponta e, principalmente, **compreender como os testes unitários foram pensados** para você conseguir evoluir sozinho depois.

---

## 1) Visão geral da arquitetura atual

Seu projeto está separado de um jeito clássico do Spring Boot:

- **controller**: recebe requisição HTTP e delega para a service.
- **service**: contém as regras de negócio (parte mais importante para testar unitariamente).
- **repository**: comunicação com banco via Spring Data JPA.
- **entity**: mapeamento das tabelas/objetos do domínio.

### Fluxo mental rápido

`Request -> Controller -> Service (regra) -> Repository -> Banco`

A ideia dos testes unitários foi focar exatamente no bloco de regra (**Service**), sem depender do banco real.

---

## 2) O que existe hoje em cada camada

## 2.1 Controllers (API)

### AgendamentoController

Expõe endpoints para:

- salvar agendamento (`POST /agendamentos`)
- deletar (`DELETE /agendamentos`)
- buscar por dia (`GET /agendamentos`)
- alterar (`PUT /agendamentos`)

### BarbeariaController

Expõe endpoints para:

- salvar/deletar/buscar barbearia
- alterar nome
- alterar horários
- alterar telefone
- alterar endereço
- alterar proprietário

> Observação de evolução: os métodos de alteração estão com mapeamentos contendo `"/barbearias/..."` dentro de um controller que já está mapeado como `"/barbearias"`; isso pode acabar gerando rotas duplicadas (`/barbearias/barbearias/...`). Isso é um bom ponto para refatorar depois.

---

## 2.2 Services (regras de negócio)

É aqui que mora a “inteligência” do sistema.

### AgendamentoService

- `salvarAgendamento`: verifica conflito de horário antes de salvar.
- `deletarAgendamento`: remove por data/hora + cliente.
- `buscarAgendamentosDia`: calcula intervalo do dia e busca no repo.
- `alterarAgendamento`: valida se existe agendamento, copia o ID antigo e salva a versão nova.

### BarbeariaService

- `salvarBarbearia`: bloqueia cadastro duplicado (nome + proprietário).
- `deletarBarbearia`, `buscarBarbearia`.
- `alterarNomeBarbearia`, `alterarHorariosFun`, `alterarTelefone`, `alterarEndereco`, `alterarProprietario`.

Cada método desses é uma regra concreta, e por isso são ótimos candidatos a teste unitário.

---

## 2.3 Entidades e relacionamento

Entidades principais:

- `Agendamento`
- `Barbearia`
- `Cliente`
- `Funcionario`
- `Servicos`
- `Proprietario`

No `Agendamento`, há relacionamento `ManyToOne` para serviço e cliente.

---

## 3) Por que os testes foram feitos na camada Service?

Porque o teste unitário ideal valida regra de negócio **isolada**. Ou seja:

- sem banco real,
- sem subir servidor,
- sem depender de controller.

Com Mockito, o repository vira um “dublê” (mock). Assim você testa a lógica pura da service.

---

## 4) Explicação didática dos testes criados

## 4.1 AgendamentoServiceTest

### Teste 1: conflito de horário

`salvarAgendamento_deveLancarExcecaoQuandoHorarioJaPreenchido`

**Objetivo:** confirmar que, se o repository encontrar agendamento no intervalo, a service lança erro.

Raciocínio:
1. Monta um agendamento com data/hora.
2. Simula o repository retornando um agendamento existente.
3. Executa `salvarAgendamento`.
4. Verifica:
   - exceção com mensagem esperada;
   - `save` **não** foi chamado.

### Teste 2: salvar quando não há conflito

`salvarAgendamento_deveSalvarQuandoHorarioDisponivel`

**Objetivo:** garantir o caminho feliz.

Raciocínio:
1. Simula busca de conflito retornando `null`.
2. Simula `save` retornando o próprio objeto.
3. Verifica que o retorno veio e o `save` foi chamado.

### Teste 3: busca diária

`buscarAgendamentosDia_deveBuscarEntreInicioEFimDoDia`

**Objetivo:** validar o cálculo de janela de tempo (`00:00` até `20:59:59`).

Raciocínio:
1. Define uma data.
2. Calcula início/fim esperados.
3. Simula repository com lista.
4. Verifica que a service chamou o repo com os limites certos.

### Teste 4: alteração preservando ID

`alterarAgendamento_deveManterIdDoAgendamentoOriginal`

**Objetivo:** garantir que o ID do registro antigo é mantido ao alterar.

Raciocínio:
1. Simula agendamento já existente (id = 7).
2. Envia um novo objeto de entrada.
3. Executa alteração.
4. Verifica que o objeto recebeu o id `7` antes de salvar.

---

## 4.2 BarbeariaServiceTest

### Teste 1: não permitir cadastro duplicado

`salvarBarbearia_deveLancarExcecaoQuandoJaExisteCadastro`

- Simula `findByNomeBarbeariaAndProprietario` retornando existente.
- Espera exceção.
- Garante que não chamou `save`.

### Teste 2: salvar cadastro novo

`salvarBarbearia_deveSalvarQuandoNaoExisteCadastro`

- Simula busca retornando `null`.
- Simula `save`.
- Confere retorno e chamada ao save.

### Teste 3: alterar nome

`alterarNomeBarbearia_deveAtualizarNomeQuandoEncontrarExistente`

- Simula existência pelo nome.
- Executa alteração.
- Verifica atualização do nome no objeto e persistência.

### Teste 4: alterar horários

`alterarHorariosFun_deveAtualizarHorarioComDadosEncontrados`

- Simula barbearia encontrada com horários esperados.
- Executa alteração.
- Confere que abertura/fechamento foram copiados corretamente.

---

## 5) Método para você escrever testes sozinho (passo a passo prático)

Quando você criar um novo método de service, faça assim:

1. **Nomeie a regra em 1 frase**
   - Ex: “não pode alterar telefone se telefone não existir”.
2. **Crie 2 testes mínimos**
   - cenário de erro
   - cenário de sucesso
3. Use estrutura mental **AAA**
   - Arrange: montar dados + mocks
   - Act: executar método
   - Assert: validar resultado/chamadas
4. Valide também interação
   - `verify(...).save(...)`
   - `verify(..., never()).save(...)` em cenários de bloqueio

Se você fizer isso sempre, seu projeto cresce com segurança.

---

## 6) Próximos passos recomendados (mentoria)

1. Completar testes para métodos restantes da `BarbeariaService`:
   - `alterarTelefone`
   - `alterarEndereco`
   - `alterarProprietario`
2. Adicionar testes de cenário negativo também para:
   - `alterarNomeBarbearia` (quando não encontra)
   - `alterarHorariosFun` (quando não encontra)
3. Refatorar mensagens de exceção para exceções específicas de domínio no futuro.
4. Ajustar o `pom.xml` para uma versão de Spring Boot resolvível no seu ambiente para conseguir rodar `mvn test` normalmente.

---

## 7) Resumo direto (em uma frase)

Os testes adicionados foram um “cinto de segurança” para as regras mais críticas das suas services; o foco foi garantir que o comportamento atual continue correto quando você evoluir o sistema.
