# 🚀 Guia rápido: subir seu Angular para deploy (e postar no LinkedIn)

Este guia foi reorganizado para você executar **agora** sem travar:
1) validar pasta correta, 2) testar local, 3) gerar build, 4) publicar.

---

## 1) Primeiro: entre na pasta certa do front
No diretório atual deste repositório não existe raiz Angular (`angular.json` + `package.json` de front).

> Sem esses arquivos, comandos como `ng build`, `ng serve` e `ng deploy` falham mesmo.

### Comandos para confirmar
```bash
pwd
ls
```
Você precisa ver:
- `angular.json`
- `package.json`
- pasta `src/`

Se não aparecer, entre na pasta correta do front e repita:
```bash
cd caminho/do/seu/frontend
```

---

## 2) Instalação e validação local (passo obrigatório)
Rode na pasta do Angular:
```bash
npm install
npx ng version
npm run build
npm start
```

Se o `npx ng version` falhar, rode:
```bash
npm install -g @angular/cli
ng version
```

---

## 3) Erros mais comuns e correção rápida
### Erro: `ng: command not found`
Use `npx ng ...` ou instale globalmente `@angular/cli`.

### Erro de dependências (`ERESOLVE`, `peer dependency`)
```bash
rm -rf node_modules package-lock.json
npm install
```

### Erro de build com environment
Confira:
- `src/environments/environment.ts`
- `src/environments/environment.prod.ts`

### App abre, mas rota quebra ao atualizar página
Configurar fallback SPA na hospedagem (`/* -> /index.html`).

---

## 4) Deploy rápido para portfólio (escolha 1)

### Opção A — Netlify (mais simples)
1. Suba código no GitHub.
2. New site from Git.
3. Build command: `npm run build`
4. Publish directory:
   - Angular 17+: `dist/<nome-do-projeto>/browser`
   - versões anteriores: `dist/<nome-do-projeto>`
5. Deploy site.

### Opção B — Vercel
1. Import project (GitHub).
2. Framework: Angular.
3. Build command: `npm run build`
4. Output directory:
   - Angular 17+: `dist/<nome-do-projeto>/browser`
   - caso diferente, use a pasta real gerada no `dist/`
5. Deploy.

---

## 5) Checklist final antes de postar no LinkedIn
- [ ] Login funcionando.
- [ ] Logout funcionando.
- [ ] Rota protegida funcionando.
- [ ] Link do deploy abrindo no celular.
- [ ] README com instrução de execução.

---

## 6) Texto pronto para LinkedIn (copiar/colar)
"Publiquei meu projeto Angular de autenticação (login, logout e proteção de rotas), com deploy em produção. Projeto focado em boas práticas, organização de componentes e integração com API. Feedback é bem-vindo!"

---

## 7) Se ainda der erro, me manda estes 2 outputs
```bash
npx ng version
npm run build
```
Com isso eu te digo exatamente o que falta em poucos minutos.
