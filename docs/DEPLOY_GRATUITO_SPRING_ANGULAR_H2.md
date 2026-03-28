# Deploy gratuito com URL pública — Spring Boot + Angular + H2

> Objetivo: você ter uma URL para cliente, dono e funcionário acessarem seu sistema sem pagar no começo.

## Resumo direto (o que funciona de verdade)

- **Angular** pode ficar no **GitHub Pages** (grátis).
- **Spring Boot** precisa ficar em um host de backend (Render/Railway/Koyeb etc.).
- **H2 em memória** NÃO serve para produção (perde dados ao reiniciar).
- Se quiser manter H2 por agora, use **H2 em arquivo** com caminho persistente (`/data/agendador`).

---

## 1) Preparar o backend para produção com H2 em arquivo

Este repositório já recebeu um perfil `prod` em:
- `agendador-horarios/src/main/resources/application-prod.properties`

Ele configura:
- `jdbc:h2:file:/data/agendador` (H2 em arquivo);
- `spring.h2.console.enabled=false`;
- `app.cors.allowed-origins` para front publicado.

### 1.1 Como subir local simulando produção
```bash
cd agendador-horarios
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

Se estiver tudo certo, API sobe com banco H2 em arquivo.

---

## 2) CORS pronto para ambiente real

`SecurityConfig` agora lê origens de CORS via propriedade:

- chave: `app.cors.allowed-origins`
- formato: URLs separadas por vírgula

Exemplo:
```properties
app.cors.allowed-origins=https://seuusuario.github.io,http://localhost:4200
```

Assim você não precisa editar código toda vez que mudar domínio.

---

## 3) Publicar Angular no GitHub Pages (grátis)

No projeto Angular:
```bash
npm i -D angular-cli-ghpages
ng build --configuration production --base-href "/NOME-DO-REPO/"
npx angular-cli-ghpages --dir=dist/NOME-DO-PROJETO/browser
```

Depois no GitHub:
- **Settings → Pages**
- branch: `gh-pages`

Sua URL do front será algo como:
- `https://SEU_USUARIO.github.io/NOME-DO-REPO/`

---

## 4) Publicar backend Spring Boot gratuitamente (opção prática)

## Opção recomendada para início: Render/Koyeb/Railway

Você precisa de 3 variáveis mínimas:
- `SPRING_PROFILES_ACTIVE=prod`
- `PORT` (normalmente fornecida automaticamente)
- `APP_CORS_ALLOWED_ORIGINS=https://SEU_USUARIO.github.io`

> Importante: em vários planos gratuitos, o filesystem pode reiniciar/limpar. Nesse caso, H2 em arquivo pode perder dados. Para sistema real de cliente, migre para PostgreSQL o quanto antes.

---

## 5) Conectar Angular ao backend publicado

No Angular (`environment.prod.ts`):
```ts
export const environment = {
  production: true,
  apiUrl: 'https://SEU_BACKEND_PUBLICO.com'
};
```

E nos services use:
```ts
private readonly api = `${environment.apiUrl}/servicos`;
```

---

## 6) Rota mais segura para freelancer (sem dor de cabeça)

Se o objetivo é não perder dado de cliente:
1. Comece com H2 em arquivo para demo rápida.
2. Assim que fechar primeiro cliente, migre para PostgreSQL grátis.
3. Mantenha Angular no GitHub Pages.
4. Deixe backend em host com banco persistente.

---

## 7) Checklist final de deploy (copiar e executar)

- [ ] Front publicado no GitHub Pages.
- [ ] Backend publicado com `SPRING_PROFILES_ACTIVE=prod`.
- [ ] CORS com domínio do front configurado.
- [ ] Endpoint `/servicos` respondendo em produção.
- [ ] Cadastro de cliente persistindo após restart do serviço.
- [ ] URL enviada para dono e funcionário testarem login/agenda.

---

## 8) Próximo passo

Se você quiser, no próximo passo eu te passo um **roteiro 100% mão na massa** com:
- template de Angular login dark;
- interceptor JWT;
- páginas `clientes/servicos/agendamentos` prontas para plugar na sua API.
