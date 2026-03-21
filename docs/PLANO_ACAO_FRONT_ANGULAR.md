# Mentoria completa — Agendador (do escopo ao deploy)

> Objetivo: te ensinar como professor, em ordem, do **planejamento** até o **deploy**, usando seu back-end atual.

---

## 1) Escopo do projeto (o que vamos entregar)

## 1.1 Problema que seu sistema resolve
Microempreendedores (barbearias da região) precisam:
- organizar agenda,
- cadastrar clientes e serviços,
- reduzir conflito de horário,
- ter um painel simples para o dia a dia.

## 1.2 Entrega MVP (versão que você vende)
- Painel administrativo com visual minimalista escuro.
- Gestão de Clientes.
- Gestão de Funcionários.
- Gestão de Serviços (com duração em minutos).
- Gestão de Agendamentos.
- Deploy com URL pública (front + back).

## 1.3 Fora do MVP (depois)
- Notificação WhatsApp.
- Login de cliente final.
- Dashboard com métricas avançadas.

---

## 2) Levantamento de requisitos (back-end + front-end)

## 2.1 O que já existe no seu back-end
Com base no código atual:
- CRUD de clientes.
- CRUD de funcionários.
- CRUD de serviços.
- CRUD de agendamentos.
- Endpoints de proprietários e barbearias.
- CORS local para front.

## 2.2 Entidades principais que o front vai consumir
1. **Cliente**
2. **Funcionario**
3. **Servicos**
4. **Agendamento**
5. **Barbearia**
6. **Proprietario**

## 2.3 Requisitos funcionais do front
- RF01: cadastrar/editar/excluir cliente.
- RF02: cadastrar/editar/excluir funcionário.
- RF03: cadastrar/editar/excluir serviço com `duracaoMinutos`.
- RF04: listar agendamentos por dia.
- RF05: criar/remarcar/cancelar agendamento.
- RF06: exibir tela principal com resumo operacional.

## 2.4 Requisitos não funcionais
- Layout minimalista dark.
- Responsivo para celular e notebook.
- Feedback visual de erro/sucesso.
- Tempo de carregamento baixo.

---

## 3) Como ficará o front (minimalista escuro)

## 3.1 Estilo visual
- Fundo: grafite/preto.
- Cards: cinza escuro com borda suave.
- Cor de destaque: dourado/barber style.

### Paleta sugerida
- `#111315` (fundo)
- `#1a1d21` (surface)
- `#2a2f36` (borda)
- `#f2f2f2` (texto)
- `#d4af37` (destaque)

## 3.2 Telas que fazem sentido para seu sistema
1. **Login** (simples para início).
2. **Dashboard** (resumo do dia).
3. **Clientes**.
4. **Funcionários**.
5. **Serviços**.
6. **Agendamentos**.
7. **Configurações da barbearia** (logo, nome, contato).

---

## 4) Contrato de API (resumo para o Angular)

## Clientes
- `POST /clientes`
- `GET /clientes`
- `DELETE /clientes?nomeCliente=...`
- `PUT /clientes/alterar-nome`
- `PUT /clientes/alterar-telefone`

## Funcionários
- `POST /funcionarios`
- `GET /funcionarios`
- `DELETE /funcionarios?nomeFuncionario=...`
- `PUT /funcionarios/alterar-nome`
- `PUT /funcionarios/alterar-telefone`

## Serviços
- `POST /servicos`
- `GET /servicos`
- `DELETE /servicos?nomeServico=...`
- `PUT /servicos/alterar-nome`
- `PUT /servicos/alterar-preco`
- `PUT /servicos/alterar-descricao`
- `PUT /servicos/alterar-duracao`

## Agendamentos
- `POST /agendamentos`
- `GET /agendamentos?data=...&idCliente=...`
- `PUT /agendamentos?...`
- `DELETE /agendamentos?...`

---

## 5) Mentoria prática — do zero ao Angular conectado

## 5.1 Preparar ambiente
```bash
# Node + Angular CLI
npm install -g @angular/cli

# conferir versões
node -v
npm -v
ng version
```

## 5.2 Criar projeto Angular
```bash
ng new barbearia-front --routing --style=scss
cd barbearia-front
ng serve
```
Acesse: `http://localhost:4200`.

## 5.3 Estrutura recomendada
```text
src/app/
  core/
    services/
    interceptors/
  shared/
    models/
    components/
  layout/
    shell/
  pages/
    dashboard/
    clientes/
    funcionarios/
    servicos/
    agendamentos/
```

## 5.4 Configurar API base
`src/environments/environment.ts`
```ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
```

## 5.5 Tema escuro global
`src/styles.scss`
```scss
:root {
  --bg: #111315;
  --surface: #1a1d21;
  --border: #2a2f36;
  --text: #f2f2f2;
  --primary: #d4af37;
}

body {
  margin: 0;
  background: var(--bg);
  color: var(--text);
  font-family: Inter, Arial, sans-serif;
}
```

## 5.6 Criar layout com logo (passo a passo de iniciante)

> Aqui é o ponto que mais gera dúvida. Vamos fazer **bem prático**, usando a estrutura que você já montou:
> `layout/shell`, `pages/dashboard|clientes|funcionarios|servicos|agendamentos`.

### 5.6.1 Ideia do Shell (casca da aplicação)
- O `Shell` é um componente fixo que aparece em todas as páginas internas.
- Dentro dele você coloca:
  1. **Sidebar** (menu da esquerda),
  2. **Topbar** (barra superior),
  3. **Router outlet** (área onde cada tela abre).

Visualmente:
```text
+----------------------------------------------------------+
| Topbar (nome da barbearia + botão sair)                 |
+----------------------+-----------------------------------+
| Sidebar (links)      | Conteúdo da página atual         |
| Dashboard            | (dashboard/clientes/...)         |
| Clientes             |                                   |
| Funcionários         |                                   |
| Serviços             |                                   |
| Agendamentos         |                                   |
+----------------------+-----------------------------------+
```

### 5.6.2 Ordem exata para implementar
1. Criar componente `layout/shell`.
2. Montar HTML com:
   - `<aside>` para sidebar,
   - `<header>` para topbar,
   - `<main><router-outlet></router-outlet></main>` para conteúdo.
3. Adicionar logo em `src/assets/logo-barbearia.png`.
4. Configurar rotas para que todas as páginas internas sejam filhas do shell.
5. Testar clicando em cada link da sidebar.

### 5.6.3 Regra de roteamento (em português simples)
- Rota pai: `''` usando `Shell`.
- Rotas filhas:
  - `/dashboard`
  - `/clientes`
  - `/funcionarios`
  - `/servicos`
  - `/agendamentos`
- Se abrir `/`, redireciona para `/dashboard`.

### 5.6.4 Erros comuns (e como evitar)
- **Tela em branco:** faltou `<router-outlet>` no shell.
- **Link não troca página:** rota não foi registrada no `app.routes.ts`.
- **Estilo “quebrado”:** CSS global não carregado ou classes com nomes diferentes do HTML.
- **Logo não aparece:** arquivo não está em `src/assets` ou caminho está incorreto.

### 5.6.5 Código pronto (copiar e testar)

> Abaixo está um exemplo completo para você **copiar/colar** e já ver funcionando.
> Depois eu explico linha por linha.

#### `src/app/layout/shell/shell.component.ts`
```ts
import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './shell.component.html',
  styleUrl: './shell.component.scss'
})
export class ShellComponent {}
```

#### `src/app/layout/shell/shell.component.html`
```html
<div class="shell">
  <aside class="sidebar">
    <div class="brand">
      <img src="assets/logo-barbearia.png" alt="Logo barbearia" class="logo" />
      <h2>Barbearia X</h2>
    </div>

    <nav class="menu">
      <a routerLink="/dashboard" routerLinkActive="active">Dashboard</a>
      <a routerLink="/clientes" routerLinkActive="active">Clientes</a>
      <a routerLink="/funcionarios" routerLinkActive="active">Funcionários</a>
      <a routerLink="/servicos" routerLinkActive="active">Serviços</a>
      <a routerLink="/agendamentos" routerLinkActive="active">Agendamentos</a>
    </nav>
  </aside>

  <section class="content">
    <header class="topbar">
      <strong>Painel Administrativo</strong>
      <button type="button">Sair</button>
    </header>

    <main class="page">
      <router-outlet></router-outlet>
    </main>
  </section>
</div>
```

#### `src/app/layout/shell/shell.component.scss`
```scss
.shell {
  display: grid;
  grid-template-columns: 250px 1fr;
  min-height: 100vh;
  background: #111315;
  color: #f2f2f2;
}

.sidebar {
  border-right: 1px solid #2a2f36;
  padding: 20px;
  background: #1a1d21;
}

.brand {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 24px;
}

.logo {
  width: 40px;
  height: 40px;
  object-fit: cover;
  border-radius: 8px;
}

.menu {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.menu a {
  color: #f2f2f2;
  text-decoration: none;
  padding: 10px 12px;
  border-radius: 10px;
}

.menu a:hover,
.menu a.active {
  background: #2a2f36;
  color: #d4af37;
}

.content {
  display: flex;
  flex-direction: column;
}

.topbar {
  height: 64px;
  border-bottom: 1px solid #2a2f36;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  background: #1a1d21;
}

.topbar button {
  background: #d4af37;
  color: #111315;
  border: 0;
  border-radius: 8px;
  padding: 8px 12px;
  font-weight: 600;
  cursor: pointer;
}

.page {
  padding: 20px;
}
```

#### `src/app/app.routes.ts` (exemplo de rotas com Shell)
```ts
import { Routes } from '@angular/router';
import { ShellComponent } from './layout/shell/shell.component';

export const routes: Routes = [
  {
    path: '',
    component: ShellComponent,
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
      {
        path: 'dashboard',
        loadComponent: () =>
          import('./pages/dashboard/dashboard.component').then((m) => m.DashboardComponent)
      },
      {
        path: 'clientes',
        loadComponent: () =>
          import('./pages/clientes/clientes.component').then((m) => m.ClientesComponent)
      },
      {
        path: 'funcionarios',
        loadComponent: () =>
          import('./pages/funcionarios/funcionarios.component').then((m) => m.FuncionariosComponent)
      },
      {
        path: 'servicos',
        loadComponent: () =>
          import('./pages/servicos/servicos.component').then((m) => m.ServicosComponent)
      },
      {
        path: 'agendamentos',
        loadComponent: () =>
          import('./pages/agendamentos/agendamentos.component').then((m) => m.AgendamentosComponent)
      }
    ]
  },
  { path: '**', redirectTo: '' }
];
```

### 5.6.6 Explicando para quem nunca viu HTML/CSS

#### HTML (estrutura)
- Pense no HTML como os **blocos da casa**:
  - `<aside>` = menu lateral.
  - `<header>` = barra do topo.
  - `<main>` = área principal da página.
  - `<a>` = link de navegação.
  - `<button>` = botão.

#### CSS (visual)
- Pense no CSS como a **pintura e organização da casa**:
  - `display: grid` divide a tela em colunas (menu + conteúdo).
  - `padding` cria espaço interno.
  - `border` cria linha de separação.
  - `background` define cor de fundo.
  - `color` define cor do texto.

#### Dica de ouro
Você não precisa decorar CSS agora. Comece alterando só 3 coisas e observando:
1. `background`
2. `color`
3. `padding`

### 5.6.7 Passo a passo no terminal (sem pular etapa)
```bash
# 1) criar shell (caso ainda não exista)
ng g c layout/shell --standalone

# 2) criar páginas (se precisar)
ng g c pages/dashboard --standalone
ng g c pages/clientes --standalone
ng g c pages/funcionarios --standalone
ng g c pages/servicos --standalone
ng g c pages/agendamentos --standalone

# 3) rodar projeto
ng serve
```

Depois abra `http://localhost:4200` e confirme:
- menu lateral visível,
- topo visível,
- clique nos links funcionando.

## 5.7 Primeiro módulo para integrar: Serviços (didático)

> Motivo de começar por Serviços: CRUD simples e te ensina o padrão que você vai repetir em Clientes, Funcionários e Agendamentos.

### 5.7.1 Passo mental do módulo
1. Criar **model** (contrato dos dados).
2. Criar **service** (chamadas HTTP).
3. Criar **página** (formulário + tabela/lista).
4. Ligar botão “Salvar” ao método `criar`.
5. Recarregar lista após salvar.

Se isso funcionar para Serviços, você replica a mesma lógica nos outros módulos.

### 5.7.2 Checklist mínimo de tela
- Campos do formulário:
  - nome
  - descrição
  - preço
  - duração (min)
- Botões:
  - salvar
  - atualizar lista
- Lista exibindo:
  - nome, preço, duração, ações

### 5.7.3 O que testar antes de avançar
1. `GET /servicos` retorna dados na tela.
2. `POST /servicos` cria novo serviço.
3. Ao salvar, a lista atualiza sem recarregar a página.
4. Mensagem de sucesso/erro aparece.

### 5.7.4 Definição de pronto (Done)
Você só passa para Clientes quando:
- consegue cadastrar serviço,
- consegue listar serviços,
- consegue editar pelo menos 1 campo (ex.: duração),
- e não tem erro no console do navegador.

### 5.7.5 Código pronto do módulo de Serviços (versão simples)

#### `src/app/shared/models/servico.model.ts`
```ts
export interface Servico {
  idServico?: number;
  nomeServico: string;
  descricaoServico: string;
  precoServico: number;
  duracaoMinutos: number;
}
```

#### `src/app/core/services/servicos.service.ts`
```ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Servico } from '../../shared/models/servico.model';

@Injectable({ providedIn: 'root' })
export class ServicosService {
  private readonly api = `${environment.apiUrl}/servicos`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Servico[]> {
    return this.http.get<Servico[]>(this.api);
  }

  criar(payload: Servico): Observable<Servico> {
    return this.http.post<Servico>(this.api, payload);
  }
}
```

#### `src/app/pages/servicos/servicos.component.ts`
```ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ServicosService } from '../../core/services/servicos.service';
import { Servico } from '../../shared/models/servico.model';

@Component({
  selector: 'app-servicos',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './servicos.component.html',
  styleUrl: './servicos.component.scss'
})
export class ServicosComponent implements OnInit {
  servicos: Servico[] = [];

  form = this.fb.group({
    nomeServico: ['', [Validators.required]],
    descricaoServico: ['', [Validators.required]],
    precoServico: [0, [Validators.required, Validators.min(1)]],
    duracaoMinutos: [30, [Validators.required, Validators.min(5)]]
  });

  constructor(
    private fb: FormBuilder,
    private servicosService: ServicosService
  ) {}

  ngOnInit(): void {
    this.carregarServicos();
  }

  carregarServicos(): void {
    this.servicosService.listar().subscribe({
      next: (dados) => (this.servicos = dados),
      error: () => alert('Erro ao carregar serviços.')
    });
  }

  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.servicosService.criar(this.form.getRawValue() as Servico).subscribe({
      next: () => {
        alert('Serviço salvo com sucesso!');
        this.form.reset({
          nomeServico: '',
          descricaoServico: '',
          precoServico: 0,
          duracaoMinutos: 30
        });
        this.carregarServicos();
      },
      error: () => alert('Erro ao salvar serviço.')
    });
  }
}
```

#### `src/app/pages/servicos/servicos.component.html`
```html
<h1>Serviços</h1>

<form [formGroup]="form" (ngSubmit)="salvar()" class="form">
  <label>
    Nome
    <input type="text" formControlName="nomeServico" />
  </label>

  <label>
    Descrição
    <input type="text" formControlName="descricaoServico" />
  </label>

  <label>
    Preço
    <input type="number" formControlName="precoServico" />
  </label>

  <label>
    Duração (min)
    <input type="number" formControlName="duracaoMinutos" />
  </label>

  <button type="submit">Salvar</button>
  <button type="button" (click)="carregarServicos()">Atualizar lista</button>
</form>

<hr />

<ul>
  <li *ngFor="let servico of servicos">
    <strong>{{ servico.nomeServico }}</strong>
    - R$ {{ servico.precoServico }}
    - {{ servico.duracaoMinutos }} min
  </li>
</ul>
```

### 5.7.6 Traduzindo o TypeScript para linguagem simples
- `servicos: Servico[] = []` → lista que aparece na tela.
- `form = this.fb.group(...)` → formulário com validação.
- `ngOnInit()` → roda automaticamente quando a tela abre.
- `carregarServicos()` → busca dados da API.
- `salvar()` → envia formulário para API.

Se você entender esses 5 pontos, já consegue evoluir no Angular com segurança.

## 5.8 Exemplo guiado de uma rotina de estudo (1h por dia)
- **Dia 1:** Shell + rotas funcionando.
- **Dia 2:** Tela de Serviços com lista mockada.
- **Dia 3:** Conectar Serviços na API real.
- **Dia 4:** Ajustar validações e mensagens.
- **Dia 5:** Copiar padrão para Clientes.

## 5.9 Resumo técnico do módulo de Serviços
### Model
```ts
export interface Servico {
  idServico?: number;
  nomeServico: string;
  descricaoServico: string;
  precoServico: number;
  duracaoMinutos: number;
}
```

### Service
```ts
private readonly api = `${environment.apiUrl}/servicos`;
```
Criar métodos:
- `listar(...)`
- `criar(...)`
- `alterarDuracao(...)`

### Tela
Campos mínimos:
- nome
- descrição
- preço
- duração (min)

Botões:
- salvar
- atualizar lista

## 5.10 Ordem de integração (sem travar)
1. Serviços
2. Clientes
3. Funcionários
4. Agendamentos

---

## 6) Conectando front e back (checklist de integração)

1. Backend rodando:
```bash
cd agendador-horarios
mvn spring-boot:run
```
2. Front rodando:
```bash
cd barbearia-front
ng serve
```
3. Testar chamada no front para endpoint real.
4. Validar se não há erro de CORS.
5. Confirmar CRUD funcionando em cada módulo.

---

## 7) Deploy gratuito (início) na AWS

> Para começar sem custo inicial alto, use a camada gratuita/créditos da AWS e monitore o billing.

## 7.1 Front no Amplify
1. Subir código no GitHub.
2. AWS Amplify → Host web app.
3. Conectar repo + branch.
4. Build: `npm ci && npm run build`.
5. Publicar URL.

## 7.2 Back no Elastic Beanstalk
1. Gerar JAR:
```bash
cd agendador-horarios
mvn clean package -DskipTests
```
2. Elastic Beanstalk → Create application (Java).
3. Upload do JAR.
4. Publicar URL do backend.

## 7.3 Produção
- Ajustar `apiUrl` do front para URL pública do backend.
- Fazer novo deploy do front.
- Testar fluxo real ponta a ponta.

## 7.4 Custos (obrigatório)
- Criar Budget na AWS.
- Alertas em 50%, 80%, 100%.
- Remover recursos não usados.

---

## 8) Cronograma diário (do zero ao deploy)

## Dia 1
- Setup Node, Angular CLI, projeto inicial.

## Dia 2
- Estrutura de pastas + rotas + layout shell.

## Dia 3
- Tema dark + logo + ajustes visuais.

## Dia 4
- Módulo Serviços (model + service + cadastro).

## Dia 5
- Módulo Serviços (lista + edição + exclusão).

## Dia 6
- Módulo Clientes.

## Dia 7
- Módulo Funcionários.

## Dia 8
- Agendamentos (listagem por data).

## Dia 9
- Agendamentos (criação/remarcação/cancelamento).

## Dia 10
- Polimento visual + tratamento de erros.

## Dia 11
- Revisão geral + checklist funcional.

## Dia 12
- Deploy Front (Amplify).

## Dia 13
- Deploy Back (Elastic Beanstalk).

## Dia 14
- Testes finais em produção + entrega para cliente.

---

## 9) Como você vende isso como freelancer

Pacote inicial para microempreendedor:
1. Setup do sistema + logo + tema personalizado.
2. Cadastro inicial de serviços/horários.
3. Treinamento rápido de uso.
4. Suporte mensal opcional.

Esse modelo te permite replicar projeto rápido para vários clientes.
