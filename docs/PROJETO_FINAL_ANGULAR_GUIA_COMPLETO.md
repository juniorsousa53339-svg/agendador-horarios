# Projeto Final Angular: Guia Completo de Integração com Backend Spring Boot (Passo a Passo)

> **Objetivo:** construir um frontend Angular completo que **apenas consome** o backend Spring Boot já existente, sem duplicar regras de negócio em TypeScript.

---

## Etapa 0 — Leitura do backend (o que foi analisado)

Antes do Angular, o backend foi mapeado em:

- Controllers REST:
  - `ClienteController`
  - `ProprietarioController`
  - `FuncionarioController`
  - `ServicosController`
  - `BarbeariaController`
  - `AgendamentoController`
- DTOs:
  - `AgendamentoRequestDTO`
  - `AgendamentoResponseDTO`
  - `BarbeariaRequestDTO`
- Entidades principais:
  - `Cliente`, `Proprietario`, `Funcionario`, `Servicos`, `Barbearia`, `Agendamento`

### 0.1 Endpoints disponíveis

#### Clientes (`/clientes`)
- `POST /clientes`
- `GET /clientes?idCliente={uuid}&nomeCliente={nome}`
- `DELETE /clientes?nomeCliente={nome}`
- `PUT /clientes/alterar-nome?atualNomeCliente={x}&novoNomeCliente={y}`
- `PUT /clientes/alterar-telefone?telefoneAtual={x}&TelefoneNovo={y}`

#### Proprietários (`/proprietarios`)
- `POST /proprietarios`
- `GET /proprietarios?nome={nome}&id_proprietario={uuid}&email={email}`
- `DELETE /proprietarios?nome={nome}`
- `PUT /proprietarios/alterar-nome?nomeAtual={x}&novoNome={y}`
- `PUT /proprietarios/alterar-telefone?telefoneAtual={x}&telefoneNovo={y}`
- `PUT /proprietarios/alterar-email?emailAtual={x}&emailNovo={y}`

#### Funcionários (`/funcionarios`)
- `POST /funcionarios`
- `GET /funcionarios?idFuncionario={idLong}&nomeFuncionario={nome}`
- `DELETE /funcionarios?nomeFuncionario={nome}`
- `PUT /funcionarios/alterar-nome?nomeFuncionarioAtual={x}&nomeFuncionarioNovo={y}`
- `PUT /funcionarios/alterar-telefone?telefoneAtual={x}&telefoneNovo={y}`

#### Serviços (`/servicos`)
- `POST /servicos`
- `GET /servicos?idServico={uuid}&nomeServico={nome}&precoServico={decimal}`
- `DELETE /servicos?nomeServico={nome}`
- `PUT /servicos/alterar-nome?nomeServicoAtual={x}&nomeServicoNovo={y}`
- `PUT /servicos/alterar-preco?precoServicoAtual={x}&precoServicoNovo={y}`
- `PUT /servicos/alterar-descricao?descricaoNova={y}` + body (texto descrição atual)
- `PUT /servicos/alterar-duracao?duracaoAtual={x}&duracaoNova={y}`

#### Barbearias (`/barbearias`)
- `POST /barbearias`
- `GET /barbearias?nomeBarbearia={nome}&idBarbearia={uuid}&rua={rua}`
- `DELETE /barbearias?nomeBarbearia={nome}`
- `PUT /barbearias/alterar-nome?...`
- `PUT /barbearias/alterar-horarios-funcionamento?...`
- `PUT /barbearias/alterar-telefone?...`
- `PUT /barbearias/alterar-endereco?...`
- `PUT /barbearias/alterar-proprietario?...`

#### Agendamentos (`/agendamentos`)
- `POST /agendamentos` (usa `AgendamentoRequestDTO`)
- `GET /agendamentos?data={yyyy-MM-dd}&idCliente={uuid}`
- `PUT /agendamentos?dataHoraAtual={...}&idClienteAtual={...}&dataHoraNova={...}&idClienteNovo={...}`
- `DELETE /agendamentos?dataHora={...}&idCliente={uuid}`

---

## Etapa 1 — Criar o projeto Angular do zero

### 1.1 Criar aplicação

```bash
npm install -g @angular/cli
ng new agendador-front --routing --style=scss
cd agendador-front
ng serve -o
```

### 1.2 Estrutura sugerida

```txt
src/app/
  core/
    http/
      api-base.service.ts
      error-handler.service.ts
    interceptors/
      auth.interceptor.ts
  features/
    clientes/
      pages/
        cliente-list/
        cliente-create/
        cliente-edit/
      data/
        cliente.service.ts
      models/
        cliente.model.ts
    proprietarios/
    funcionarios/
    servicos/
    agendamentos/
  shared/
    ui/
    utils/
```

> **Por que essa estrutura?**
> - Separa camada de dados (services) da camada visual (components/pages).
> - Facilita manutenção e crescimento do projeto.

---

## Etapa 2 — Configurar integração HTTP

### 2.1 `environment.ts`

```ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
```

### 2.2 Configurar `HttpClient`

(Standalone Angular)

```ts
import { ApplicationConfig } from '@angular/core';
import { provideHttpClient } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [provideHttpClient()]
};
```

### 2.3 Criar serviço base

```ts
// src/app/core/http/api-base.service.ts
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ApiBaseService {
  readonly baseUrl = environment.apiUrl;
}
```

---

## Etapa 3 — Criar models (baseados em entidade/DTO do backend)

> Aqui você **tipa payload/resposta**, não implementa regra de negócio.

```ts
// features/clientes/models/cliente.model.ts
export interface Cliente {
  idCliente?: string; // UUID
  nomeCliente: string;
  telefoneCliente: string;
}
```

```ts
// features/agendamentos/models/agendamento.model.ts
export interface AgendamentoRequest {
  idCliente: string;
  idFuncionario: string;
  idServico: string;
  dataHoraAgendamento: string; // ISO string
}

export interface AgendamentoResponse {
  idAgendamento: string;
  idCliente: string;
  nomeCliente: string;
  idFuncionario: string;
  nomeFuncionario: string;
  idServico: string;
  nomeServico: string;
  dataHoraAgendamento: string;
  status: string;
}
```

Repita para `Proprietario`, `Funcionario`, `Servico` e `Barbearia` conforme campos das entidades Java.

---

## Etapa 4 — Services por entidade (consumo da API)

## 4.1 Exemplo: ClienteService

```ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiBaseService } from '../../../core/http/api-base.service';
import { Cliente } from '../models/cliente.model';

@Injectable({ providedIn: 'root' })
export class ClienteService {
  private readonly url: string;

  constructor(private http: HttpClient, api: ApiBaseService) {
    this.url = `${api.baseUrl}/clientes`;
  }

  criar(payload: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(this.url, payload);
  }

  listarPorFiltro(idCliente: string, nomeCliente: string): Observable<Cliente[]> {
    const params = new HttpParams()
      .set('idCliente', idCliente)
      .set('nomeCliente', nomeCliente);
    return this.http.get<Cliente[]>(this.url, { params });
  }

  atualizarNome(atualNomeCliente: string, novoNomeCliente: string): Observable<Cliente> {
    const params = new HttpParams()
      .set('atualNomeCliente', atualNomeCliente)
      .set('novoNomeCliente', novoNomeCliente);
    return this.http.put<Cliente>(`${this.url}/alterar-nome`, null, { params });
  }

  atualizarTelefone(telefoneAtual: string, TelefoneNovo: string): Observable<Cliente> {
    const params = new HttpParams()
      .set('telefoneAtual', telefoneAtual)
      .set('TelefoneNovo', TelefoneNovo);
    return this.http.put<Cliente>(`${this.url}/alterar-telefone`, null, { params });
  }

  remover(nomeCliente: string): Observable<void> {
    const params = new HttpParams().set('nomeCliente', nomeCliente);
    return this.http.delete<void>(this.url, { params });
  }
}
```

> **Importante:** observe que o service só faz transporte HTTP. Não decide regras de negócio.

### 4.2 Exemplo: AgendamentoService

```ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiBaseService } from '../../../core/http/api-base.service';
import { AgendamentoRequest, AgendamentoResponse } from '../models/agendamento.model';

@Injectable({ providedIn: 'root' })
export class AgendamentoService {
  private readonly url: string;

  constructor(private http: HttpClient, api: ApiBaseService) {
    this.url = `${api.baseUrl}/agendamentos`;
  }

  criar(payload: AgendamentoRequest): Observable<AgendamentoResponse> {
    return this.http.post<AgendamentoResponse>(this.url, payload);
  }

  listarDoDia(data: string, idCliente: string): Observable<AgendamentoResponse[]> {
    const params = new HttpParams().set('data', data).set('idCliente', idCliente);
    return this.http.get<AgendamentoResponse[]>(this.url, { params });
  }

  alterar(dataHoraAtual: string, idClienteAtual: string, dataHoraNova: string, idClienteNovo: string) {
    const params = new HttpParams()
      .set('dataHoraAtual', dataHoraAtual)
      .set('idClienteAtual', idClienteAtual)
      .set('dataHoraNova', dataHoraNova)
      .set('idClienteNovo', idClienteNovo);
    return this.http.put<AgendamentoResponse>(this.url, null, { params });
  }

  remover(dataHora: string, idCliente: string) {
    const params = new HttpParams().set('dataHora', dataHora).set('idCliente', idCliente);
    return this.http.delete<void>(this.url, { params });
  }
}
```

---

## Etapa 5 — Componentes (listagem, cadastro, edição)

A mesma receita vale para cada entidade.

## 5.1 Componente de listagem

- Busca dados com o service no `ngOnInit`.
- Mostra tabela/lista.
- Botões para editar/remover.

```ts
// cliente-list.component.ts
export class ClienteListComponent implements OnInit {
  clientes: Cliente[] = [];

  constructor(private clienteService: ClienteService) {}

  ngOnInit(): void {
    this.clienteService.listarPorFiltro('00000000-0000-0000-0000-000000000000', '').subscribe({
      next: (data) => (this.clientes = data),
      error: (err) => console.error(err)
    });
  }
}
```

## 5.2 Componente de cadastro (Reactive Forms)

```ts
export class ClienteCreateComponent {
  form = this.fb.group({
    nomeCliente: ['', [Validators.required, Validators.minLength(2)]],
    telefoneCliente: ['', [Validators.required]]
  });

  constructor(private fb: FormBuilder, private clienteService: ClienteService) {}

  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.clienteService.criar(this.form.getRawValue() as Cliente).subscribe({
      next: () => alert('Cliente cadastrado com sucesso!'),
      error: (err) => console.error(err)
    });
  }
}
```

## 5.3 Componente de edição

- Carrega dado atual.
- Envia alteração por endpoint de update.
- Não recalcula validação de negócio: backend decide.

---

## Etapa 6 — Rotas por feature

```ts
import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: 'clientes', loadComponent: () => import('./features/clientes/pages/cliente-list/cliente-list.component').then(m => m.ClienteListComponent) },
  { path: 'clientes/novo', loadComponent: () => import('./features/clientes/pages/cliente-create/cliente-create.component').then(m => m.ClienteCreateComponent) },
  { path: 'clientes/:id/editar', loadComponent: () => import('./features/clientes/pages/cliente-edit/cliente-edit.component').then(m => m.ClienteEditComponent) },
  { path: '', redirectTo: 'clientes', pathMatch: 'full' }
];
```

Repita o padrão para `proprietarios`, `funcionarios`, `servicos`, `barbearias` e `agendamentos`.

---

## Etapa 7 — Regras para não duplicar negócio no Angular

Checklist obrigatório:

- ✅ Frontend só envia dados e exibe respostas.
- ✅ Validação de front apenas básica (`required`, `minlength`, formato local de data).
- ✅ Erros de regra (ex.: horário inválido, conflito de agenda) vêm da API e são exibidos na tela.
- ❌ Não bloquear horário por conta própria no TypeScript com regras complexas.
- ❌ Não reproduzir regra de disponibilidade no Angular.

---

## Etapa 8 — Ordem de implementação (mentoria prática)

1. Criar projeto e configurar `HttpClient`.
2. Criar models de todas as entidades.
3. Criar services de todas as entidades.
4. Implementar telas de **Cliente** (lista, cadastro, edição).
5. Implementar telas de **Funcionário**.
6. Implementar telas de **Serviço**.
7. Implementar telas de **Agendamento**.
8. Implementar telas de **Proprietário** e **Barbearia**.
9. Padronizar tratamento de erro HTTP.
10. Refinar UX (loading, mensagens, confirmação de exclusão).

---

## Etapa 9 — Próximo passo imediato

Para continuarmos em formato de mentoria real (com código do seu projeto Angular), o próximo passo é:

1. Você rodar os comandos de criação (`ng new ...`),
2. Me enviar a estrutura inicial do front (`src/app`),
3. E eu te guio arquivo por arquivo na implementação dos services + componentes.

> Se você quiser, no próximo passo eu já te entrego a **Etapa 10** com os arquivos iniciais prontos (`models`, `services`, `routes`) para copiar e colar.

---

## Etapa 10 — Fechando o esqueleto funcional hoje (após etapas 1 a 4.2)

Se você já concluiu setup + models + services, execute **agora** os comandos abaixo.

### 10.1 Geração dos componentes (exatamente como pedido)

```bash
# CLIENTES
ng g c features/clientes/pages/cliente-list --standalone --skip-tests
ng g c features/clientes/pages/cliente-create --standalone --skip-tests
ng g c features/clientes/pages/cliente-edit --standalone --skip-tests

# FUNCIONÁRIOS
ng g c features/funcionarios/pages/funcionario-list --standalone --skip-tests
ng g c features/funcionarios/pages/funcionario-create --standalone --skip-tests
ng g c features/funcionarios/pages/funcionario-edit --standalone --skip-tests

# AGENDAMENTOS
ng g c features/agendamentos/pages/agendamento-list --standalone --skip-tests
ng g c features/agendamentos/pages/agendamento-create --standalone --skip-tests
ng g c features/agendamentos/pages/agendamento-edit --standalone --skip-tests
```

> Se seu projeto **não** for standalone, remova `--standalone`.

### 10.2 Mapeamento real de função (`salvar()` -> `ClienteService.criar()` -> `POST /clientes`)

```ts
// cliente-create.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ClienteService } from '../../data/cliente.service';
import { Cliente } from '../../models/cliente.model';

@Component({
  selector: 'app-cliente-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <h2>Novo Cliente</h2>

    <form [formGroup]="form" (ngSubmit)="salvar()">
      <input type="text" formControlName="nomeCliente" placeholder="Nome" />
      <input type="text" formControlName="telefoneCliente" placeholder="Telefone" />
      <button type="submit">Salvar</button>
    </form>

    <p *ngIf="feedback" [style.color]="feedbackType === 'error' ? 'tomato' : 'lightgreen'">
      {{ feedback }}
    </p>
  `
})
export class ClienteCreateComponent {
  feedback = '';
  feedbackType: 'success' | 'error' | '' = '';

  form = this.fb.group({
    nomeCliente: ['', [Validators.required, Validators.minLength(2)]],
    telefoneCliente: ['', [Validators.required]]
  });

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService,
    private router: Router
  ) {}

  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const payload = this.form.getRawValue() as Cliente;

    // Angular apenas envia dados e mostra retorno da API
    this.clienteService.criar(payload).subscribe({
      next: () => {
        this.feedbackType = 'success';
        this.feedback = 'Cliente cadastrado com sucesso.';
        this.router.navigate(['/clientes/lista']);
      },
      error: (err) => {
        this.feedbackType = 'error';
        this.feedback = err?.error?.message ?? 'Erro ao salvar cliente.';
      }
    });
  }
}
```

### 10.3 Fluxo de dados sem regra de negócio no Frontend

**Fluxo recomendado:**
1. Componente captura input do usuário;
2. Service envia request para API (`HttpClient`);
3. Backend valida regra de negócio;
4. Backend responde sucesso/erro;
5. Componente apenas exibe feedback.

Exemplo genérico para reaproveitar em qualquer componente:

```ts
this.algumService.operacao(payload).subscribe({
  next: (response) => {
    this.feedback = response?.mensagem ?? 'Operação concluída';
  },
  error: (err) => {
    this.feedback = err?.error?.message ?? 'Falha na operação';
  }
});
```

> Não criar regra de disponibilidade, conflito de horário, política de cancelamento, etc., no Angular.

### 10.4 `app.routes.ts` completo para começar a navegar hoje

```ts
import { Routes } from '@angular/router';

export const routes: Routes = [
  // Clientes
  {
    path: 'clientes/lista',
    loadComponent: () =>
      import('./features/clientes/pages/cliente-list/cliente-list.component').then(
        (m) => m.ClienteListComponent
      )
  },
  {
    path: 'clientes/novo',
    loadComponent: () =>
      import('./features/clientes/pages/cliente-create/cliente-create.component').then(
        (m) => m.ClienteCreateComponent
      )
  },
  {
    path: 'clientes/:id/editar',
    loadComponent: () =>
      import('./features/clientes/pages/cliente-edit/cliente-edit.component').then(
        (m) => m.ClienteEditComponent
      )
  },

  // Funcionários
  {
    path: 'funcionarios/lista',
    loadComponent: () =>
      import('./features/funcionarios/pages/funcionario-list/funcionario-list.component').then(
        (m) => m.FuncionarioListComponent
      )
  },
  {
    path: 'funcionarios/novo',
    loadComponent: () =>
      import('./features/funcionarios/pages/funcionario-create/funcionario-create.component').then(
        (m) => m.FuncionarioCreateComponent
      )
  },
  {
    path: 'funcionarios/:id/editar',
    loadComponent: () =>
      import('./features/funcionarios/pages/funcionario-edit/funcionario-edit.component').then(
        (m) => m.FuncionarioEditComponent
      )
  },

  // Agendamentos
  {
    path: 'agendamentos/lista',
    loadComponent: () =>
      import('./features/agendamentos/pages/agendamento-list/agendamento-list.component').then(
        (m) => m.AgendamentoListComponent
      )
  },
  {
    path: 'agendamentos/novo',
    loadComponent: () =>
      import('./features/agendamentos/pages/agendamento-create/agendamento-create.component').then(
        (m) => m.AgendamentoCreateComponent
      )
  },
  {
    path: 'agendamentos/:id/editar',
    loadComponent: () =>
      import('./features/agendamentos/pages/agendamento-edit/agendamento-edit.component').then(
        (m) => m.AgendamentoEditComponent
      )
  },

  // Home e fallback
  { path: '', redirectTo: 'clientes/lista', pathMatch: 'full' },
  { path: '**', redirectTo: 'clientes/lista' }
];
```

---

## Etapa 11 — Prompt final (para usar na mentoria)

Use este texto pronto quando quiser pedir continuidade ao mentor/IA:

```txt
Olá! Como meu mentor, preciso de ajuda para finalizar o esqueleto funcional do meu projeto hoje.
Já concluí as etapas de 1 a 4.2 e meu objetivo é fechar o dia com a estrutura pronta para focar no visual amanhã.

Ponto mais importante: o Frontend Angular deve ser “burro”, apenas consumindo dados e regras do backend Spring Boot,
sem duplicar lógica de negócio em TypeScript.

Baseado nos meus controllers (/clientes, /funcionarios, /agendamentos), quero:
1) comandos ng g c para listagem/cadastro/edição;
2) conexão de salvar() com service + endpoint real;
3) fluxo de dados mostrando sucesso/erro vindos da API;
4) app.routes.ts completo com rotas de navegação.
```
