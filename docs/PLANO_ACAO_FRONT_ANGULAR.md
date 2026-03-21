# Plano de ação (iniciante) — Angular + Back-end do Agendador

## 1) Visão direta: como seu sistema pode ficar (minimalista e escuro)
Sim, dá para começar agora de forma simples e profissional.

### Conceito visual sugerido (site one-page + área interna)
- **Tema escuro**: fundo grafite/preto, detalhes em dourado/vermelho discreto (estilo barbearia premium).
- **Layout limpo**: poucos elementos por tela, espaçamento generoso e tipografia clara.
- **Logo da barbearia** no topo (navbar e tela de login).
- **Cartões simples** para serviços e agenda do dia.

### Paleta pronta (você pode copiar)
- Fundo principal: `#111315`
- Fundo card: `#1a1d21`
- Borda suave: `#2a2f36`
- Texto principal: `#f2f2f2`
- Texto secundário: `#b5b8bd`
- Destaque (botão): `#d4af37`

---

## 2) O que eu ajustei no back-end para facilitar sua integração

### ✅ Ajustes aplicados
1. Corrigi CORS para aceitar Angular local em `http://localhost:4200` (mantendo `3000`).
2. Corrigi rotas duplicadas do tipo `/clientes/clientes/...` e `/servicos/servicos/...`.
3. Corrigi rotas duplicadas em `barbearias` e `proprietarios`.
4. Corrigi criação de barbearia para usar **um único body** (`BarbeariaRequestDTO`), em vez de dois `@RequestBody`.

> Resultado: a API fica mais previsível para consumo no Angular.

---

## 3) Escopo do sistema Angular (MVP para freelance)

## Telas mínimas (fase inicial)
1. **Login simples** (pode ser mock no início, se necessário).
2. **Dashboard** (resumo rápido: total de clientes, serviços e agendamentos do dia).
3. **Clientes** (listar, cadastrar, editar telefone/nome, excluir).
4. **Funcionários** (listar, cadastrar, editar, excluir).
5. **Serviços** (listar, cadastrar, editar preço/descrição, excluir).
6. **Agendamentos** (listar por data, criar, remarcar, cancelar).

## Telas públicas opcionais (site da barbearia)
1. **Home** com logo, slogan e botão “Agendar”.
2. **Seção Serviços** com preço e duração.
3. **Contato** (telefone/endereço).

Assim você consegue apresentar como site + sistema de gestão.

---

## 4) Funções principais por perfil (versão simples)

### Dono/Administrador
- Gerenciar clientes, funcionários, serviços e agenda.

### Funcionário
- Ver agenda do dia e marcar status do atendimento.

### Cliente (fase 2)
- Visualizar horários disponíveis e solicitar agendamento.

---

## 5) Arquitetura Angular recomendada (iniciante)

```text
src/app/
  core/
    interceptors/
    services/http.service.ts
  shared/
    models/
    components/
  features/
    auth/
    dashboard/
    clientes/
    funcionarios/
    servicos/
    agendamentos/
```

### Regras simples para não se perder
- Cada tela = 1 pasta em `features/`.
- Cada entidade = 1 service (`clientes.service.ts`, etc).
- Todo endpoint em service (não no component).
- `environment.ts` com `apiUrl: 'http://localhost:8080'`.

---

## 6) Cronograma didático (10 dias úteis)

## Semana 1
### Dia 1 — Setup geral
- Instalar Angular CLI
- Criar projeto
- Configurar tema escuro global
- Criar layout base (menu + topo + área de conteúdo)

### Dia 2 — Home + identidade visual
- Inserir logo
- Criar página Home minimalista
- Criar seção Serviços estática (mock)

### Dia 3 — Integração de Clientes
- Model + service + listagem
- Formulário de cadastro
- Excluir e editar nome/telefone

### Dia 4 — Integração de Funcionários
- CRUD básico
- Mensagens de sucesso/erro

### Dia 5 — Integração de Serviços
- CRUD básico
- Máscara de preço e validações

## Semana 2
### Dia 6 — Agendamentos (listagem por data)
- Filtro por dia
- Tabela/cartões de horários

### Dia 7 — Agendamentos (criação)
- Form com cliente + funcionário + serviço + data/hora
- Tratamento de conflito retornado pela API

### Dia 8 — Agendamentos (edição/cancelamento)
- Remarcação
- Cancelamento

### Dia 9 — Refino visual
- Melhorar responsividade
- Padronizar botões, cards e tipografia

### Dia 10 — Revisão final
- Teste de fluxo completo
- Checklist para demo de freelance

---

## 7) Contrato de endpoints (após correções)

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

## Barbearias
- `POST /barbearias` com body:
```json
{
  "barbearia": {
    "nomeBarbearia": "Barber Pro",
    "rua": "Rua X",
    "numeroRua": 123,
    "telefoneBarbearia": "11999999999",
    "horarioAbertura": "08:00:00",
    "horarioFechamento": "18:00:00"
  },
  "proprietario": {
    "idProprietario": 1,
    "nome": "João",
    "telefone": "11988888888",
    "email": "joao@email.com"
  }
}
```
- `PUT /barbearias/alterar-nome`
- `PUT /barbearias/alterar-horarios-funcionamento`
- `PUT /barbearias/alterar-telefone`
- `PUT /barbearias/alterar-endereco`
- `PUT /barbearias/alterar-proprietario`

## Proprietários
- `POST /proprietarios`
- `GET /proprietarios`
- `DELETE /proprietarios?nome=...`
- `PUT /proprietarios/alterar-nome`
- `PUT /proprietarios/alterar-telefone`
- `PUT /proprietarios/alterar-email`

## Agendamentos
- `POST /agendamentos`
- `GET /agendamentos?data=...&idCliente=...`
- `PUT /agendamentos?...`
- `DELETE /agendamentos?...`

---

## 8) Primeiros comandos (passo a passo iniciante)

```bash
# 1) criar app angular
ng new barbearia-front --routing --style=scss

# 2) entrar no projeto
cd barbearia-front

# 3) subir front
ng serve
```

No back-end:
```bash
cd agendador-horarios
mvn spring-boot:run
```

Depois, no Angular:
1. Criar tela `clientes`.
2. Chamar `GET /clientes`.
3. Renderizar lista em cards/tabela.
4. Criar formulário para `POST /clientes`.

Quando isso funcionar, você já validou a integração real.

---

## 9) Checklist de pronto para apresentar
- [ ] Tema escuro aplicado em todas as telas
- [ ] Logo visível no topo
- [ ] Home com serviços da barbearia
- [ ] CRUD de clientes funcionando
- [ ] CRUD de serviços funcionando
- [ ] Agendamento do dia funcionando
- [ ] Fluxo completo demonstrável em 3–5 minutos

Se você quiser, no próximo passo eu posso te entregar a **estrutura base dos componentes Angular** (arquivos e código inicial) para você já começar colando e testando.

---

## 10) Dúvida importante: duração de serviço fica no back-end?
Sim, **fica no back-end também**.  
No front você só exibe/edita; quem define o contrato da regra de negócio é o back-end.

### Regra prática
- Front-end: mostra campo “Duração (min)” no formulário.
- Back-end: persiste `duracaoMinutos` na entidade `Servicos`.
- Agendamento: usa essa duração para calcular conflitos de horário no futuro.

---

## 11) Mentoria “do zero” para conectar Angular com seu back-end

## Etapa A — Preparar ambiente (uma única vez)
1. Instalar Node LTS.
2. Instalar Angular CLI:
   ```bash
   npm install -g @angular/cli
   ```
3. Confirmar versões:
   ```bash
   node -v
   npm -v
   ng version
   ```

## Etapa B — Subir o back-end
1. Na pasta do backend:
   ```bash
   cd agendador-horarios
   mvn spring-boot:run
   ```
2. Teste rápido (exemplo):
   ```bash
   curl -X GET "http://localhost:8080/servicos?idServico=1&nomeServico=Corte&precoServico=40"
   ```

## Etapa C — Criar Angular
1. Criar projeto:
   ```bash
   ng new barbearia-front --routing --style=scss
   cd barbearia-front
   ```
2. Rodar:
   ```bash
   ng serve
   ```
3. Abrir `http://localhost:4200`.

## Etapa D — Configuração base da API no Angular
1. Em `src/environments/environment.ts`:
   ```ts
   export const environment = {
     production: false,
     apiUrl: 'http://localhost:8080'
   };
   ```
2. Criar `core/services/http.service.ts` com `HttpClient`.
3. Importar `HttpClientModule` no app.

## Etapa E — Primeiro módulo real: Serviços
1. Criar model:
   ```ts
   export interface Servico {
     idServico?: number;
     nomeServico: string;
     descricaoServico: string;
     precoServico: number;
     duracaoMinutos: number;
   }
   ```
2. Criar service:
   - `listar(...)`
   - `criar(servico)`
   - `alterarNome(...)`
   - `alterarPreco(...)`
   - `alterarDescricao(...)`
   - `alterarDuracao(...)`
   - `excluir(...)`
3. Criar tela com:
   - lista dos serviços
   - formulário (nome, descrição, preço, duração)
   - botões de editar/excluir

## Etapa F — Fluxo recomendado de integração
1. Integrar **Serviços**.
2. Integrar **Clientes**.
3. Integrar **Funcionários**.
4. Integrar **Agendamentos**.

Essa ordem reduz complexidade e te dá vitória rápida.

---

## 12) Mentoria mão na massa: Angular + HTML + CSS (tema escuro minimalista)

> Aqui é a parte “copiar e fazer”. A ideia é você conseguir montar a base sozinho.

## 12.1 Criar estrutura inicial

No projeto Angular (`barbearia-front`):
```bash
ng g c layout/shell
ng g c pages/dashboard
ng g c pages/servicos
ng g c pages/clientes
ng g s core/services/servicos
ng g i shared/models/servico
```

## 12.2 Definir rotas principais

`src/app/app.routes.ts`
```ts
import { Routes } from '@angular/router';
import { ShellComponent } from './layout/shell/shell.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ServicosComponent } from './pages/servicos/servicos.component';
import { ClientesComponent } from './pages/clientes/clientes.component';

export const routes: Routes = [
  {
    path: '',
    component: ShellComponent,
    children: [
      { path: '', component: DashboardComponent },
      { path: 'servicos', component: ServicosComponent },
      { path: 'clientes', component: ClientesComponent }
    ]
  }
];
```

## 12.3 Tema global escuro (CSS)

`src/styles.scss`
```scss
:root {
  --bg: #111315;
  --surface: #1a1d21;
  --border: #2a2f36;
  --text: #f2f2f2;
  --muted: #b5b8bd;
  --primary: #d4af37;
  --danger: #d9534f;
}

* {
  box-sizing: border-box;
}

body {
  margin: 0;
  font-family: Inter, Arial, sans-serif;
  background: var(--bg);
  color: var(--text);
}

.card {
  background: var(--surface);
  border: 1px solid var(--border);
  border-radius: 12px;
  padding: 16px;
}

.btn {
  border: none;
  border-radius: 10px;
  padding: 10px 14px;
  cursor: pointer;
}

.btn-primary {
  background: var(--primary);
  color: #111;
  font-weight: 600;
}
```

## 12.4 Layout base (Shell) com logo

`src/app/layout/shell/shell.component.html`
```html
<div class="shell">
  <aside class="sidebar">
    <div class="brand">
      <img src="assets/logo-barbearia.png" alt="Logo Barbearia" />
      <h2>Barber Pro</h2>
    </div>

    <nav>
      <a routerLink="/">Dashboard</a>
      <a routerLink="/servicos">Serviços</a>
      <a routerLink="/clientes">Clientes</a>
    </nav>
  </aside>

  <main class="content">
    <router-outlet></router-outlet>
  </main>
</div>
```

`src/app/layout/shell/shell.component.scss`
```scss
.shell {
  display: grid;
  grid-template-columns: 240px 1fr;
  min-height: 100vh;
}

.sidebar {
  background: #0d0f11;
  border-right: 1px solid var(--border);
  padding: 20px;
}

.brand {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 24px;
}

.brand img {
  width: 36px;
  height: 36px;
  object-fit: cover;
  border-radius: 50%;
}

nav {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

nav a {
  text-decoration: none;
  color: var(--text);
  padding: 10px;
  border-radius: 8px;
}

nav a:hover {
  background: #1b1f24;
}

.content {
  padding: 24px;
}
```

## 12.5 Model + Service de Serviços (integração real com backend)

`src/app/shared/models/servico.ts`
```ts
export interface Servico {
  idServico?: number;
  nomeServico: string;
  descricaoServico: string;
  precoServico: number;
  duracaoMinutos: number;
}
```

`src/app/core/services/servicos.service.ts`
```ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Servico } from '../../shared/models/servico';

@Injectable({ providedIn: 'root' })
export class ServicosService {
  private readonly api = `${environment.apiUrl}/servicos`;

  constructor(private http: HttpClient) {}

  listar(idServico: number, nomeServico: string, precoServico: number): Observable<Servico[]> {
    return this.http.get<Servico[]>(
      `${this.api}?idServico=${idServico}&nomeServico=${nomeServico}&precoServico=${precoServico}`
    );
  }

  criar(payload: Servico): Observable<Servico> {
    return this.http.post<Servico>(this.api, payload);
  }

  alterarDuracao(duracaoAtual: number, duracaoNova: number): Observable<Servico> {
    return this.http.put<Servico>(
      `${this.api}/alterar-duracao?duracaoAtual=${duracaoAtual}&duracaoNova=${duracaoNova}`,
      {}
    );
  }
}
```

## 12.6 Tela de Serviços (HTML + CSS + TS)

`src/app/pages/servicos/servicos.component.ts`
```ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ServicosService } from '../../core/services/servicos.service';
import { Servico } from '../../shared/models/servico';

@Component({
  selector: 'app-servicos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './servicos.component.html',
  styleUrls: ['./servicos.component.scss']
})
export class ServicosComponent {
  servicos: Servico[] = [];

  form: Servico = {
    nomeServico: '',
    descricaoServico: '',
    precoServico: 0,
    duracaoMinutos: 30
  };

  constructor(private servicosService: ServicosService) {}

  carregar(): void {
    this.servicosService.listar(1, 'Corte', 40).subscribe({
      next: (data) => (this.servicos = data),
      error: (err) => console.error('Erro ao listar serviços', err)
    });
  }

  salvar(): void {
    this.servicosService.criar(this.form).subscribe({
      next: () => {
        this.form = { nomeServico: '', descricaoServico: '', precoServico: 0, duracaoMinutos: 30 };
        this.carregar();
      },
      error: (err) => console.error('Erro ao criar serviço', err)
    });
  }
}
```

`src/app/pages/servicos/servicos.component.html`
```html
<section class="card">
  <h1>Serviços</h1>
  <p class="muted">Cadastre e gerencie os serviços da barbearia.</p>

  <form (ngSubmit)="salvar()" class="form-grid">
    <input [(ngModel)]="form.nomeServico" name="nomeServico" placeholder="Nome do serviço" required />
    <input [(ngModel)]="form.descricaoServico" name="descricaoServico" placeholder="Descrição" required />
    <input [(ngModel)]="form.precoServico" name="precoServico" type="number" placeholder="Preço" required />
    <input [(ngModel)]="form.duracaoMinutos" name="duracaoMinutos" type="number" placeholder="Duração (min)" required />
    <button class="btn btn-primary" type="submit">Salvar serviço</button>
  </form>
</section>

<section class="card mt">
  <div class="row">
    <h2>Lista de serviços</h2>
    <button class="btn btn-primary" (click)="carregar()">Atualizar</button>
  </div>

  <div class="list" *ngIf="servicos.length; else vazio">
    <article class="item" *ngFor="let s of servicos">
      <h3>{{ s.nomeServico }}</h3>
      <p>{{ s.descricaoServico }}</p>
      <small>R$ {{ s.precoServico }} • {{ s.duracaoMinutos }} min</small>
    </article>
  </div>

  <ng-template #vazio>
    <p class="muted">Nenhum serviço carregado ainda.</p>
  </ng-template>
</section>
```

`src/app/pages/servicos/servicos.component.scss`
```scss
h1, h2, h3 {
  margin: 0 0 8px;
}

.muted {
  color: var(--muted);
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
  margin-top: 12px;
}

input {
  background: #12161a;
  border: 1px solid var(--border);
  color: var(--text);
  border-radius: 10px;
  padding: 10px;
}

.mt {
  margin-top: 16px;
}

.row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 10px;
  margin-top: 12px;
}

.item {
  background: #13181d;
  border: 1px solid var(--border);
  border-radius: 10px;
  padding: 12px;
}
```

## 12.7 Checklist final (para saber que conectou certo)
1. Front abre em `http://localhost:4200`.
2. Back abre em `http://localhost:8080`.
3. Tela de serviços carrega sem erro de CORS.
4. Você consegue salvar um serviço com duração.
5. Você vê o serviço listado com preço + duração.

---

## 13) Deploy gratuito na AWS (guia completo, foco microempreendedor/freelance)

> **Importante (data):** desde **15 de julho de 2025**, contas novas da AWS podem usar até **US$ 200 em créditos** (plano free por até 6 meses, ou até acabar os créditos).  
> Ou seja: o deploy pode sair “gratuito” no começo, mas você precisa monitorar custos para não passar do crédito.

### Estratégia de deploy (simples e prática)
- **Front Angular:** AWS Amplify Hosting (mais fácil para SPA).
- **Back Spring Boot:** AWS Elastic Beanstalk (deploy simples do JAR).
- **Banco:** no início mantenha H2 para prova de conceito; para produção, migrar para RDS (pode consumir crédito).

## 13.1 Pré-checklist antes de subir
1. Projeto backend rodando local.
2. Projeto Angular buildando local (`ng build`).
3. Conta AWS criada e com Free Tier ativo.
4. Budget e alertas de custo configurados.

## 13.2 Deploy do front (Angular) no AWS Amplify

### Passo A — Build do Angular
```bash
cd barbearia-front
npm install
ng build
```
O Angular vai gerar pasta `dist/...`.

### Passo B — Subir no Amplify
1. Console AWS → **Amplify** → **Host web app**.
2. Conecte seu GitHub (recomendado) **ou** faça deploy manual do build.
3. Se usar GitHub:
   - selecione repo + branch;
   - build command: `npm ci && npm run build`;
   - output directory: `dist/barbearia-front/browser` (ou `dist/barbearia-front`, depende da versão Angular).
4. Deploy.

### Passo C — Ajustar variável da API
No Amplify, configure variável de ambiente:
- `API_URL=https://SEU_BACKEND.amazonaws.com`

No Angular, use `environment` para apontar para a URL do backend de produção.

## 13.3 Deploy do backend (Spring Boot) no Elastic Beanstalk

### Passo A — Gerar JAR
```bash
cd agendador-horarios
mvn clean package -DskipTests
```

### Passo B — Criar app no Elastic Beanstalk
1. Console AWS → **Elastic Beanstalk** → **Create application**.
2. Platform: **Java**.
3. Upload do arquivo JAR em `target/`.
4. Environment type: **Single instance** (mais barato para começar).
5. Criar ambiente.

### Passo C — Variáveis de ambiente do backend
No Elastic Beanstalk:
- `SERVER_PORT=5000` (se necessário)
- variáveis de banco quando você migrar do H2 para RDS

### Passo D — Health check
Após deploy, abra URL do Beanstalk e teste endpoint:
```bash
curl "https://SEU-ENDERECO-BEANSTALK/agendamentos?data=2026-03-21&idCliente=1"
```

## 13.4 Conectar front com back (produção)
1. Pegue URL final do Elastic Beanstalk.
2. Atualize `apiUrl` no Angular (ambiente de produção).
3. Faça novo build/deploy no Amplify.
4. Teste fluxo real: cadastro de serviço com duração + listagem.

## 13.5 Segurança mínima para produção (freelance)
1. Habilitar autenticação real (não deixar `permitAll` em produção).
2. Restringir CORS para domínio do Amplify (não usar wildcard).
3. Configurar HTTPS obrigatório.
4. Criar usuário admin inicial com senha forte.

## 13.6 Controle de custos (para não tomar susto)
1. AWS Billing → **Budgets**.
2. Criar orçamento mensal (ex.: US$ 5).
3. Alertas em 50%, 80%, 100%.
4. Desligar/remover ambiente de teste que não estiver usando.

## 13.7 Pipeline freelance (como você vende isso)
1. Cria versão base para barbearia A.
2. Duplica layout + muda logo/cores para barbearia B.
3. Mantém backend padrão e personaliza front.
4. Cobra setup + mensalidade de suporte/hosting.

---

## 14) Cronograma por dias (pronto para começar agora)

> Formato: **1 tarefa principal por bloco** para você não travar.

## Dia 1 — Setup total do ambiente
- Instalar Node LTS + Angular CLI.
- Criar projeto Angular.
- Rodar `ng serve`.
- Confirmar backend local rodando.

## Dia 2 — Estrutura base do Angular
- Criar pastas `core`, `shared`, `pages`, `layout`.
- Configurar rotas base (`Dashboard`, `Serviços`, `Clientes`).
- Criar `Shell` com sidebar.

## Dia 3 — Tema visual minimalista escuro
- Aplicar variáveis globais de cor no `styles.scss`.
- Padronizar botões, cards e inputs.
- Inserir logo da barbearia no layout.

## Dia 4 — Integração de Serviços (parte 1)
- Criar model `Servico` com `duracaoMinutos`.
- Criar `ServicosService` (listar + criar).
- Criar tela com formulário e botão salvar.

## Dia 5 — Integração de Serviços (parte 2)
- Exibir lista de serviços.
- Implementar editar preço/duração.
- Testar com backend real.

## Dia 6 — Integração de Clientes
- Criar model/service de clientes.
- Tela de listagem + cadastro + exclusão.
- Ajustar estados de loading/erro.

## Dia 7 — Integração de Funcionários
- Criar model/service de funcionários.
- Listar, cadastrar e editar.
- Validar fluxo básico ponta a ponta.

## Dia 8 — Agendamentos (listagem)
- Criar model/service de agendamento.
- Tela para listar agenda por data.
- Exibir status e dados de cliente/serviço.

## Dia 9 — Agendamentos (criação/remarcação)
- Criar formulário de novo agendamento.
- Implementar remarcação/cancelamento.
- Tratar erro de conflito de horário.

## Dia 10 — Refino do front (UX)
- Revisar alinhamento, espaçamento e responsividade.
- Melhorar mensagens visuais (sucesso/erro).
- Limpar telas e remover ruído visual.

## Dia 11 — Revisão técnica pré-deploy
- Validar variáveis de ambiente (`apiUrl` local/prod).
- Revisar CORS no backend para domínio do front.
- Rodar checklist funcional completo.

## Dia 12 — Deploy do frontend (AWS Amplify)
- Conectar GitHub no Amplify.
- Configurar build e output.
- Publicar URL pública do front.

## Dia 13 — Deploy do backend (Elastic Beanstalk)
- Gerar JAR com Maven.
- Subir app Java no Elastic Beanstalk.
- Testar endpoint público com `curl`.

## Dia 14 — Go-live + operação freelance
- Conectar front público com backend público.
- Testar fluxo real (cadastro → agendamento).
- Configurar budget/alertas AWS.
- Preparar versão demo para cliente local.
