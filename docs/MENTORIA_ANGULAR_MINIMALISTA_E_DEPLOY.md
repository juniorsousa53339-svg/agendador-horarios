# Mentoria do zero — Angular minimalista (dark), login simples, integração com Spring Boot e deploy

> Documento feito com base no código atual deste repositório para te guiar como um “plano de freelancer”: escopo, execução, integração e publicação.

---

## 1) Diagnóstico rápido do seu backend atual (Spring Boot)

### 1.1 O que já está pronto e aproveitável
- API com entidades principais para o negócio: `Cliente`, `Funcionario`, `Servicos`, `Agendamento`, `Barbearia`, `Proprietario`.
- CRUDs já expostos em controllers para cliente, funcionário, serviço e agendamento.
- Regras de conflito de agenda no `AgendamentoService` (não permite mesmo funcionário no mesmo horário).
- CORS já liberado para front local (`http://localhost:4200` para Angular).

### 1.2 Pontos importantes antes de escalar para produção
- O `SecurityConfig` está com `anyRequest().permitAll()`. Ou seja: **tudo liberado** no momento (MVP), mesmo com `@PreAuthorize` em services.
- Seu `application.properties` usa H2 em memória (`jdbc:h2:mem`). Em produção, você vai precisar banco persistente (PostgreSQL, por exemplo).
- Alguns endpoints de `GET` usam muitos `@RequestParam` obrigatórios; no front, isso dificulta telas de listagem geral. Sugestão: criar endpoints “listar todos” simples.

---

## 2) Escopo comercial (MVP que vende para barbearia)

### 2.1 Oferta inicial (MVP)
1. Login administrativo simples.
2. Dashboard do dia (agendamentos de hoje).
3. CRUD de clientes.
4. CRUD de serviços.
5. CRUD de funcionários.
6. Agenda do dia/semana (listar, criar, editar, cancelar).

### 2.2 Fora do MVP (upgrade para vender depois)
- WhatsApp lembrete.
- Multiunidade (franquia).
- Painel com métricas financeiras.
- Link de autoagendamento para cliente final.

---

## 3) Design system minimalista dark (mobile-first)

## 3.1 Paleta sugerida (barber style)
- Fundo principal: `#0F1115`
- Fundo de card: `#171A21`
- Borda suave: `#252A35`
- Texto principal: `#EDEFF3`
- Texto secundário: `#AAB2C5`
- Destaque premium: `#D4AF37`
- Erro: `#EF4444`
- Sucesso: `#22C55E`

## 3.2 Regras de UI
- Poucos elementos por tela (foco em ação principal).
- Botões grandes (44px+ para mobile).
- Inputs com contraste alto.
- Espaçamento padrão de 8px (8/16/24/32).
- Tipografia simples (`Inter`, `Roboto` ou `system-ui`).

## 3.3 Componentes mínimos
- `AppShell` (topbar + navegação inferior para mobile).
- `CardPadrao`.
- `InputPadrao`.
- `BotaoPrimario` / `BotaoSecundario`.
- `ToastFeedback` (sucesso/erro).
- `ListaVazia` (estado sem dados).

---

## 4) Angular do zero (passo a passo prático)

## 4.1 Criar projeto
```bash
npm install -g @angular/cli
ng new barber-pro-front --routing --style=scss
cd barber-pro-front
ng serve
```

## 4.2 Estrutura recomendada
```text
src/app/
  core/
    guards/
    interceptors/
    services/
  shared/
    components/
    models/
    ui/
  features/
    auth/
    dashboard/
    clientes/
    funcionarios/
    servicos/
    agendamentos/
  layout/
    app-shell/
```

## 4.3 Ambientes
`src/environments/environment.ts`
```ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
```

`src/environments/environment.prod.ts`
```ts
export const environment = {
  production: true,
  apiUrl: 'https://SEU-BACKEND-PUBLICO.com'
};
```

## 4.4 Tema global dark
`src/styles.scss`
```scss
:root {
  --bg: #0F1115;
  --surface: #171A21;
  --border: #252A35;
  --text: #EDEFF3;
  --muted: #AAB2C5;
  --primary: #D4AF37;
  --danger: #EF4444;
  --success: #22C55E;
}

* { box-sizing: border-box; }
body {
  margin: 0;
  background: var(--bg);
  color: var(--text);
  font-family: Inter, system-ui, Arial, sans-serif;
}
```

---

## 5) Login simples (MVP) sem complicar

## 5.1 Fluxo recomendado para começar rápido
1. Tela de login (email + senha).
2. Chama endpoint `/auth/login` no backend.
3. Backend devolve token JWT.
4. Front salva token no `localStorage`.
5. Interceptor envia `Authorization: Bearer <token>` em todas as requisições.

## 5.2 Observação importante no seu backend atual
Hoje o projeto ainda está em “modo aberto” (`permitAll`).
Para login real:
- criar endpoint `POST /auth/login`;
- habilitar autenticação JWT;
- proteger rotas por perfil.

Se quiser velocidade máxima no MVP comercial, você pode:
- primeiro entregar front completo com login “mockado” (1 semana);
- depois ativar JWT no backend (2–3 dias) sem refazer telas.

---

## 6) Conectando Angular + Spring Boot no seu cenário

## 6.1 Ordem de integração que evita travar
1. **Serviços** (mais simples para validar padrão).
2. **Clientes**.
3. **Funcionários**.
4. **Agendamentos** (mais regra de negócio).

## 6.2 Padrão de service no Angular
Exemplo de serviço para `servicos`:
```ts
@Injectable({ providedIn: 'root' })
export class ServicosApiService {
  private readonly api = `${environment.apiUrl}/servicos`;

  constructor(private http: HttpClient) {}

  listar(params: { idServico: number; nomeServico: string; precoServico: number }) {
    return this.http.get<Servico[]>(this.api, { params: { ...params } });
  }

  criar(payload: Servico) {
    return this.http.post<Servico>(this.api, payload);
  }

  alterarDuracao(duracaoAtual: number, duracaoNova: number) {
    return this.http.put<Servico>(`${this.api}/alterar-duracao`, null, {
      params: { duracaoAtual, duracaoNova }
    });
  }
}
```

## 6.3 Checklist de integração local
1. Rodar backend:
```bash
cd agendador-horarios
mvn spring-boot:run
```
2. Rodar front:
```bash
cd barber-pro-front
ng serve
```
3. Confirmar no navegador (DevTools > Network) se as chamadas batem em `localhost:8080`.
4. Tratar erros de API com mensagens amigáveis (toast/snackbar).

---

## 7) Publicação: como subir para GitHub Pages do jeito certo

## 7.1 Regra fundamental
**GitHub Pages só hospeda front estático** (Angular build).
Ele **não hospeda** o backend Java/Spring Boot.

## 7.2 Arquitetura de deploy recomendada
- **Front Angular**: GitHub Pages.
- **Backend Spring Boot**: Render, Railway, Fly.io ou VPS.
- **Banco**: PostgreSQL (Render/Neon/Supabase).

## 7.3 Deploy Angular no GitHub Pages
No projeto Angular:
```bash
ng add angular-cli-ghpages
```

No `angular.json`, configure baseHref correto (nome do repositório):
```bash
ng build --configuration production --base-href "/NOME-DO-REPO/"
```

Publicar:
```bash
npx angular-cli-ghpages --dir=dist/barber-pro-front/browser
```

Depois, no GitHub:
- Settings → Pages → branch `gh-pages`.

## 7.4 Conectar front publicado ao backend
No `environment.prod.ts`, apontar para URL pública do backend.
Exemplo:
```ts
apiUrl: 'https://barber-api.onrender.com'
```

---

## 8) Plano de execução em 4 semanas (formato freelancer)

## Semana 1 — UI/UX + estrutura Angular
- setup de projeto;
- tema dark minimalista;
- layout mobile-first;
- componentes compartilhados.

## Semana 2 — CRUDs principais
- módulos clientes, serviços e funcionários;
- integração real com API;
- validações de formulário e feedback.

## Semana 3 — Agenda + ajustes backend
- módulo agendamentos;
- filtros por dia e profissional;
- melhoria de endpoints para listagem simples;
- início de autenticação JWT.

## Semana 4 — Deploy + apresentação comercial
- front no GitHub Pages;
- backend em nuvem;
- domínio opcional;
- vídeo demo + README comercial.

---

## 9) Checklist de qualidade para você entregar como profissional

- [ ] Interface coerente, rápida e limpa no celular.
- [ ] Login funcionando (ou mock com roadmap claro).
- [ ] CRUDs principais funcionando sem erro 500.
- [ ] Agendamento com validação de conflito funcionando.
- [ ] Front publicado em URL pública.
- [ ] Backend publicado em URL pública.
- [ ] README com instruções de uso para cliente final.

---

## 10) Próximo passo imediato (hoje)

1. Criar o front Angular com tema dark e telas base.
2. Integrar módulo de **Serviços** primeiro.
3. Subir front no GitHub Pages para já ter URL demo.
4. Subir backend em ambiente gratuito.
5. Me mandar suas referências visuais (prints/links) para eu te devolver um layout “copiável” componente por componente.

> Se você quiser, no próximo passo eu já te passo um “kit de início” com estrutura de pastas, componentes base e CSS pronto para copiar e colar.
