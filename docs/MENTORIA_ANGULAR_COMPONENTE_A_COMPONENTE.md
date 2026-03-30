# Mentoria Angular — Componente a Componente (Baseado no seu Back-end)

> Objetivo: te ensinar **na prática** a montar o front Angular no VS Code, com telas simples e funcionais para:
> - Cliente agendar sem login;
> - Funcionário visualizar agenda;
> - Proprietário fazer gestão básica.

## ✅ Escopo completo desta mentoria

Esta mentoria cobre, de ponta a ponta:
- [x] criação do projeto Angular no VS Code;
- [x] configuração de ambiente e `HttpClient`;
- [x] criação de **models**;
- [x] criação de **services**;
- [x] criação de **rotas**;
- [x] criação de telas: Login, Agendar (cliente), Agenda (funcionário), Dashboard (proprietário), Gestão de Funcionários e Gestão de Serviços;
- [x] criação de componentes compartilhados: Card, Page Header, Sidebar e Shell;
- [x] estilo base e plano de execução para finalizar no mesmo dia.

---

## 0) Pré-requisitos e visão geral

Você vai construir um front com:
- Angular + TypeScript;
- Roteamento por páginas;
- Componentes reutilizáveis;
- Serviços HTTP para consumir sua API Java/Spring.

### Estrutura final desejada (simples e escalável)
```txt
src/app/
  core/
    services/
      api.service.ts
      auth.service.ts
      cliente-api.service.ts
      funcionario-api.service.ts
      servico-api.service.ts
      agendamento-api.service.ts
    guards/
      auth.guard.ts
      role.guard.ts
    interceptors/
      auth.interceptor.ts
  shared/
    models/
      cliente.model.ts
      funcionario.model.ts
      servico.model.ts
      agendamento.model.ts
    components/
      card/
      page-header/
      loading/
  pages/
    auth/
      login/
    cliente/
      agendar/
    funcionario/
      agenda/
    proprietario/
      dashboard/
      funcionarios/
      servicos/
  app.routes.ts
```

---

## 1) Criar projeto no VS Code (do zero)

## 1.1 Instalar Node, Angular CLI e criar projeto
No terminal do VS Code:

```bash
npm install -g @angular/cli
ng new agendador-front --routing --style=scss
cd agendador-front
code .
```

Quando o Angular perguntar sobre SSR, pode escolher **No** para simplificar.

## 1.2 Rodar e testar projeto base
```bash
ng serve -o
```
- Vai abrir em `http://localhost:4200`.
- Se aparecer a tela padrão do Angular, está tudo certo.

---

## 2) Configurar comunicação com seu back-end

## 2.1 Environment
Arquivo: `src/environments/environment.ts`

```ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
```

## 2.2 Habilitar HttpClient
Se seu projeto for standalone (Angular novo), no `app.config.ts` adicione:

```ts
import { provideHttpClient, withInterceptors } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient()
  ]
};
```

---

## 3) Criar Models (tipagem forte)

Crie pasta `src/app/shared/models`.

## 3.1 `cliente.model.ts`
```ts
export interface Cliente {
  idCliente?: number;
  nomeCliente: string;
  telefoneCliente: string;
}
```

## 3.2 `funcionario.model.ts`
```ts
export interface Funcionario {
  idFuncionario?: number;
  nomeFuncionario: string;
  telefoneFuncionario: string;
  especialidade: string;
}
```

## 3.3 `servico.model.ts`
```ts
export interface Servico {
  idServico?: number;
  nomeServico: string;
  descricaoServico: string;
  precoServico: number;
  duracaoMinutos: number;
}
```

## 3.4 `agendamento.model.ts`
```ts
export interface AgendamentoRequest {
  idCliente: number;
  idFuncionario: number;
  idServico: number;
  dataHoraAgendamento: string;
}

export interface AgendamentoResponse {
  idAgendamento: number;
  idCliente: number;
  nomeCliente: string;
  idFuncionario: number;
  nomeFuncionario: string;
  idServico: number;
  nomeServico: string;
  dataHoraAgendamento: string;
  status: string;
}
```

---

## 4) Criar Services da API

Crie `src/app/core/services`.

## 4.1 Service base opcional (`api.service.ts`)
```ts
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ApiService {
  baseUrl = environment.apiUrl;
}
```

## 4.2 `cliente-api.service.ts`
```ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente } from '../../shared/models/cliente.model';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class ClienteApiService {
  private readonly endpoint: string;

  constructor(private http: HttpClient, api: ApiService) {
    this.endpoint = `${api.baseUrl}/clientes`;
  }

  criar(payload: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(`${this.endpoint}/publico`, payload);
  }

  buscar(idCliente: number, nomeCliente: string): Observable<Cliente[]> {
    const params = new HttpParams()
      .set('idCliente', idCliente)
      .set('nomeCliente', nomeCliente);

    return this.http.get<Cliente[]>(this.endpoint, { params });
  }
}
```

## 4.3 `funcionario-api.service.ts`
```ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Funcionario } from '../../shared/models/funcionario.model';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class FuncionarioApiService {
  private readonly endpoint: string;

  constructor(private http: HttpClient, api: ApiService) {
    this.endpoint = `${api.baseUrl}/funcionarios`;
  }

  buscar(idFuncionario: number, nomeFuncionario: string): Observable<Funcionario[]> {
    if (idFuncionario === 0 && nomeFuncionario === '') {
      return this.http.get<Funcionario[]>(`${this.endpoint}/publico`);
    }

    const params = new HttpParams()
      .set('idFuncionario', idFuncionario)
      .set('nomeFuncionario', nomeFuncionario);

    return this.http.get<Funcionario[]>(this.endpoint, { params });
  }
}
```

## 4.4 `servico-api.service.ts`
```ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Servico } from '../../shared/models/servico.model';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class ServicoApiService {
  private readonly endpoint: string;

  constructor(private http: HttpClient, api: ApiService) {
    this.endpoint = `${api.baseUrl}/servicos`;
  }

  buscar(idServico: number, nomeServico: string, precoServico: number): Observable<Servico[]> {
    if (idServico === 0 && nomeServico === '' && precoServico === 0) {
      return this.http.get<Servico[]>(`${this.endpoint}/publico`);
    }

    const params = new HttpParams()
      .set('idServico', idServico)
      .set('nomeServico', nomeServico)
      .set('precoServico', precoServico);

    return this.http.get<Servico[]>(this.endpoint, { params });
  }
}
```

## 4.5 `agendamento-api.service.ts`
```ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AgendamentoRequest, AgendamentoResponse } from '../../shared/models/agendamento.model';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class AgendamentoApiService {
  private readonly endpoint: string;

  constructor(private http: HttpClient, api: ApiService) {
    this.endpoint = `${api.baseUrl}/agendamentos`;
  }

  criar(payload: AgendamentoRequest): Observable<AgendamentoResponse> {
    return this.http.post<AgendamentoResponse>(`${this.endpoint}/publico`, payload);
  }

  listarDoDia(data: string, idCliente: number): Observable<AgendamentoResponse[]> {
    const params = new HttpParams().set('data', data).set('idCliente', idCliente);
    return this.http.get<AgendamentoResponse[]>(this.endpoint, { params });
  }
}
```

---

## 5) Criar rotas das telas

Arquivo: `src/app/app.routes.ts`

```ts
import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'agendar', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./pages/auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'agendar',
    loadComponent: () => import('./pages/cliente/agendar/agendar.component').then(m => m.AgendarComponent)
  },
  {
    path: 'funcionario/agenda',
    loadComponent: () => import('./pages/funcionario/agenda/agenda-funcionario.component').then(m => m.AgendaFuncionarioComponent)
  },
  {
    path: 'proprietario/dashboard',
    loadComponent: () => import('./pages/proprietario/dashboard/dashboard.component').then(m => m.DashboardComponent)
  },
  {
    path: 'proprietario/funcionarios',
    loadComponent: () => import('./pages/proprietario/funcionarios/funcionarios.component').then(m => m.FuncionariosComponent)
  },
  {
    path: 'proprietario/servicos',
    loadComponent: () => import('./pages/proprietario/servicos/servicos.component').then(m => m.ServicosComponent)
  },
  { path: '**', redirectTo: 'agendar' }
];
```

---

## 6) Criar cada tela + componente (passo a passo)

## 6.1 Tela de Login

### Comando
```bash
ng g c pages/auth/login --standalone
```

### `login.component.ts` (base)
```ts
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  form = this.fb.group({
    usuario: ['', Validators.required],
    senha: ['', Validators.required],
    perfil: ['FUNCIONARIO', Validators.required] // FUNCIONARIO | PROPRIETARIO
  });

  constructor(private fb: FormBuilder) {}

  entrar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    // No início, simule login local e salve role no localStorage
    localStorage.setItem('perfil', this.form.value.perfil!);
  }
}
```

### `login.component.html`
```html
<section class="container">
  <h1>Login</h1>
  <form [formGroup]="form" (ngSubmit)="entrar()">
    <input type="text" placeholder="Usuário" formControlName="usuario" />
    <input type="password" placeholder="Senha" formControlName="senha" />

    <select formControlName="perfil">
      <option value="FUNCIONARIO">Funcionário</option>
      <option value="PROPRIETARIO">Proprietário</option>
    </select>

    <button type="submit">Entrar</button>
  </form>
</section>
```

---

## 6.2 Tela do Cliente (Agendar)

### Comando
```bash
ng g c pages/cliente/agendar --standalone
```

### Responsabilidade desta tela
- Listar serviços;
- Listar funcionários;
- Capturar dados do cliente;
- Criar agendamento.

### `agendar.component.ts`
```ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClienteApiService } from '../../../core/services/cliente-api.service';
import { FuncionarioApiService } from '../../../core/services/funcionario-api.service';
import { ServicoApiService } from '../../../core/services/servico-api.service';
import { AgendamentoApiService } from '../../../core/services/agendamento-api.service';
import { Funcionario } from '../../../shared/models/funcionario.model';
import { Servico } from '../../../shared/models/servico.model';

@Component({
  selector: 'app-agendar',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './agendar.component.html'
})
export class AgendarComponent implements OnInit {
  funcionarios: Funcionario[] = [];
  servicos: Servico[] = [];

  form = this.fb.group({
    nomeCliente: ['', Validators.required],
    telefoneCliente: ['', Validators.required],
    idFuncionario: [null as number | null, Validators.required],
    idServico: [null as number | null, Validators.required],
    dataHoraAgendamento: ['', Validators.required]
  });

  constructor(
    private fb: FormBuilder,
    private clienteApi: ClienteApiService,
    private funcionarioApi: FuncionarioApiService,
    private servicoApi: ServicoApiService,
    private agendamentoApi: AgendamentoApiService
  ) {}

  ngOnInit(): void {
    this.carregarFuncionarios();
    this.carregarServicos();
  }

  carregarFuncionarios(): void {
    this.funcionarioApi.buscar(0, '').subscribe({
      next: lista => (this.funcionarios = lista),
      error: () => (this.funcionarios = [])
    });
  }

  carregarServicos(): void {
    this.servicoApi.buscar(0, '', 0).subscribe({
      next: lista => (this.servicos = lista),
      error: () => (this.servicos = [])
    });
  }

  confirmar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const { nomeCliente, telefoneCliente, idFuncionario, idServico, dataHoraAgendamento } = this.form.getRawValue();

    this.clienteApi.criar({
      nomeCliente: nomeCliente!,
      telefoneCliente: telefoneCliente!
    }).subscribe({
      next: cliente => {
        this.agendamentoApi.criar({
          idCliente: cliente.idCliente!,
          idFuncionario: idFuncionario!,
          idServico: idServico!,
          dataHoraAgendamento: dataHoraAgendamento!
        }).subscribe({
          next: () => alert('Agendamento realizado com sucesso!'),
          error: err => alert(err?.error?.message ?? 'Erro ao criar agendamento')
        });
      },
      error: err => alert(err?.error?.message ?? 'Erro ao criar cliente')
    });
  }
}
```

### `agendar.component.html`
```html
<section class="container">
  <h1>Agendar Horário</h1>

  <form [formGroup]="form" (ngSubmit)="confirmar()">
    <input type="text" formControlName="nomeCliente" placeholder="Seu nome" />
    <input type="text" formControlName="telefoneCliente" placeholder="Seu telefone" />

    <select formControlName="idFuncionario">
      <option [ngValue]="null">Selecione o funcionário</option>
      <option *ngFor="let f of funcionarios" [ngValue]="f.idFuncionario">
        {{ f.nomeFuncionario }} - {{ f.especialidade }}
      </option>
    </select>

    <select formControlName="idServico">
      <option [ngValue]="null">Selecione o serviço</option>
      <option *ngFor="let s of servicos" [ngValue]="s.idServico">
        {{ s.nomeServico }} - {{ s.precoServico | currency:'BRL' }}
      </option>
    </select>

    <input type="datetime-local" formControlName="dataHoraAgendamento" />

    <button type="submit">Confirmar</button>
  </form>
</section>
```

---

## 6.3 Tela do Funcionário (Agenda)

### Comando
```bash
ng g c pages/funcionario/agenda --standalone --type=component --flat=false
```

### Objetivo
- Mostrar agenda do dia (filtro por data + idCliente inicialmente).

### `agenda-funcionario.component.ts`
```ts
import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { AgendamentoApiService } from '../../../core/services/agendamento-api.service';
import { AgendamentoResponse } from '../../../shared/models/agendamento.model';

@Component({
  selector: 'app-agenda-funcionario',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './agenda-funcionario.component.html'
})
export class AgendaFuncionarioComponent {
  agenda: AgendamentoResponse[] = [];

  filtro = this.fb.group({
    data: ['', Validators.required],
    idCliente: [0, Validators.required]
  });

  constructor(private fb: FormBuilder, private agendamentoApi: AgendamentoApiService) {}

  buscar(): void {
    if (this.filtro.invalid) {
      this.filtro.markAllAsTouched();
      return;
    }

    const { data, idCliente } = this.filtro.getRawValue();

    this.agendamentoApi.listarDoDia(data!, idCliente!).subscribe({
      next: lista => (this.agenda = lista),
      error: () => (this.agenda = [])
    });
  }
}
```

---

## 6.4 Tela do Proprietário (Dashboard)

### Comando
```bash
ng g c pages/proprietario/dashboard --standalone
```

### Objetivo
- Exibir cards simples:
  - total funcionários;
  - total serviços;
  - atalhos de cadastro.

Você pode chamar serviços existentes e mostrar totais em variáveis.

---

## 6.5 Tela do Proprietário (Gestão de Funcionários)

### Comando
```bash
ng g c pages/proprietario/funcionarios --standalone
```

### Objetivo
- Proprietário listar funcionários;
- Proprietário cadastrar funcionário;
- Proprietário editar nome/telefone.

### `funcionarios.component.ts` (base funcional)
```ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { FuncionarioApiService } from '../../../core/services/funcionario-api.service';
import { Funcionario } from '../../../shared/models/funcionario.model';

@Component({
  selector: 'app-funcionarios',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './funcionarios.component.html'
})
export class FuncionariosComponent implements OnInit {
  lista: Funcionario[] = [];

  form = this.fb.group({
    nomeFuncionario: ['', Validators.required],
    telefoneFuncionario: ['', Validators.required],
    especialidade: ['', Validators.required]
  });

  constructor(private fb: FormBuilder, private funcionarioApi: FuncionarioApiService) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.funcionarioApi.buscar(0, '').subscribe({
      next: data => (this.lista = data),
      error: () => (this.lista = [])
    });
  }

  salvar(): void {
    if (this.form.invalid) return;
    // Dica: crie método POST no funcionario-api.service e chame aqui.
    alert('Implementar POST /funcionarios no service e chamar aqui.');
  }
}
```

### `funcionarios.component.html`
```html
<section class="container">
  <h1>Gestão de Funcionários</h1>

  <form [formGroup]="form" (ngSubmit)="salvar()">
    <input formControlName="nomeFuncionario" placeholder="Nome" />
    <input formControlName="telefoneFuncionario" placeholder="Telefone" />
    <input formControlName="especialidade" placeholder="Especialidade" />
    <button type="submit">Salvar funcionário</button>
  </form>

  <div class="card" *ngFor="let item of lista">
    <strong>{{ item.nomeFuncionario }}</strong>
    <p>{{ item.especialidade }} - {{ item.telefoneFuncionario }}</p>
  </div>
</section>
```

---

## 6.6 Tela do Proprietário (Gestão de Serviços)

### Comando
```bash
ng g c pages/proprietario/servicos --standalone
```

### Objetivo
- Proprietário listar serviços;
- Proprietário cadastrar/editar serviço.

### `servicos.component.ts` (base funcional)
```ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ServicoApiService } from '../../../core/services/servico-api.service';
import { Servico } from '../../../shared/models/servico.model';

@Component({
  selector: 'app-servicos',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './servicos.component.html'
})
export class ServicosComponent implements OnInit {
  lista: Servico[] = [];

  form = this.fb.group({
    nomeServico: ['', Validators.required],
    descricaoServico: ['', Validators.required],
    precoServico: [0, Validators.required],
    duracaoMinutos: [30, Validators.required]
  });

  constructor(private fb: FormBuilder, private servicoApi: ServicoApiService) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.servicoApi.buscar(0, '', 0).subscribe({
      next: data => (this.lista = data),
      error: () => (this.lista = [])
    });
  }
}
```

### `servicos.component.html`
```html
<section class="container">
  <h1>Gestão de Serviços</h1>

  <form [formGroup]="form">
    <input formControlName="nomeServico" placeholder="Nome do serviço" />
    <input formControlName="descricaoServico" placeholder="Descrição" />
    <input formControlName="precoServico" type="number" placeholder="Preço" />
    <input formControlName="duracaoMinutos" type="number" placeholder="Duração (min)" />
    <button type="button">Salvar serviço</button>
  </form>

  <div class="card" *ngFor="let item of lista">
    <strong>{{ item.nomeServico }}</strong>
    <p>{{ item.descricaoServico }}</p>
    <p>{{ item.precoServico | currency:'BRL' }} · {{ item.duracaoMinutos }} min</p>
  </div>
</section>
```

---

## 6.7 Componente de Navegação (menu lateral)

### Comando
```bash
ng g c shared/components/sidebar --standalone
```

### `sidebar.component.html`
```html
<aside class="sidebar">
  <a routerLink="/agendar">Agendar</a>
  <a routerLink="/funcionario/agenda">Agenda Funcionário</a>
  <a routerLink="/proprietario/dashboard">Dashboard</a>
  <a routerLink="/proprietario/funcionarios">Funcionários</a>
  <a routerLink="/proprietario/servicos">Serviços</a>
</aside>
```

---

## 6.8 Componente de Layout Principal (shell)

### Comando
```bash
ng g c layout/shell --standalone
```

### `shell.component.html`
```html
<div class="app-shell">
  <app-sidebar></app-sidebar>
  <main class="app-content">
    <router-outlet></router-outlet>
  </main>
</div>
```

### Dica
- Coloque o `ShellComponent` como layout padrão das rotas privadas.

---

## 7) Componentes reutilizáveis (recomendado)

## 7.1 Card padrão
```bash
ng g c shared/components/card --standalone
```

`card.component.html`
```html
<div class="card">
  <ng-content></ng-content>
</div>
```

`card.component.scss`
```scss
.card {
  background: #1f1f1f;
  color: #fff;
  border-radius: 12px;
  padding: 16px;
  border: 1px solid #333;
}
```

## 7.2 Cabeçalho de página
```bash
ng g c shared/components/page-header --standalone
```

---

## 8) Estilo global simples e bonito

Arquivo `src/styles.scss`:

```scss
:root {
  --bg: #111315;
  --surface: #1d2127;
  --text: #f3f3f3;
  --muted: #9ca3af;
  --primary: #d4af37;
}

* { box-sizing: border-box; }

body {
  margin: 0;
  font-family: Inter, Arial, sans-serif;
  background: var(--bg);
  color: var(--text);
}

.container {
  max-width: 960px;
  margin: 0 auto;
  padding: 24px;
}

input, select, button {
  width: 100%;
  padding: 10px 12px;
  border-radius: 10px;
  border: 1px solid #3a3f47;
  background: var(--surface);
  color: var(--text);
  margin-bottom: 12px;
}

button {
  cursor: pointer;
  background: var(--primary);
  color: #111;
  font-weight: 700;
}
```

---

## 9) Guard de autenticação (próximo passo)

Crie um guard simples:

```bash
ng g guard core/guards/auth --skip-tests
```

Exemplo de regra:
- Se não tiver `perfil` no `localStorage`, redireciona para `/login`.

Depois você cria `role.guard` para bloquear rota de proprietário para funcionário.

---

## 10) Ordem recomendada para você evoluir hoje

1. Criar projeto e deixar rodando.
2. Fazer models.
3. Fazer API services.
4. Fazer tela pública de agendamento (`/agendar`).
5. Fazer login mockado (`/login`).
6. Fazer agenda funcionário.
7. Fazer dashboard proprietário.
8. Fazer gestão de funcionários (proprietário).
9. Fazer gestão de serviços (proprietário).
10. Criar shell + sidebar.
11. Melhorar layout e mensagens de erro.

---

## 11) Erros comuns e como corrigir rápido

1. **CORS bloqueado**
   - Confirme que front está em `http://localhost:4200`.

2. **Data em formato inválido**
   - `datetime-local` precisa virar ISO (`YYYY-MM-DDTHH:mm`).

3. **404 em rota Angular**
   - Verifique se o `loadComponent` aponta para caminho correto.

4. **Template não reconhece `*ngFor`**
   - Em componente standalone, importe `CommonModule` no `imports`.

---

## 12) Próximo nível (quando terminar o básico)

- Substituir login mock por autenticação real.
- Criar componente de calendário.
- Adicionar toasts de sucesso/erro.
- Usar Angular Material somente depois do fluxo principal pronto.

---

Se quiser, no próximo passo eu posso montar para você o **esqueleto completo de arquivos Angular** (com cada arquivo pronto para copiar e colar), começando por `agendar.component` + `agendamento-api.service` + rotas e guard.
