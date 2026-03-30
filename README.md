<div align="center">

   <img width="733" height="397" alt="Gemini_Generated_Image_bv78sxbv78sxbv78" src="https://github.com/user-attachments/assets/8a3d950c-c677-43fd-b535-3796ef5c2120" />


  <br><br>

  <img src="https://skillicons.dev/icons?i=java,spring,maven,hibernate,mysql,postman,git,github,idea,docker" />

  <br><br>

  <table width="100%">
    <tr>
      <td bgcolor="#161b22" align="left" style="padding: 30px; border-radius: 15px; border: 1px solid #30363d;">
        <h2 style="color: #58a6ff; margin-top: 0; font-family: sans-serif;">📅 Barber Pro System</h2>
        <p style="color: #c9d1d9; font-size: 16px; line-height: 1.6; font-family: sans-serif;">
          Sistema <b>Enterprise</b> de agendamento desenvolvido com <b>Java 17</b> e <b>Spring Boot 3</b>. 
          Foco em alta disponibilidade e regras de negócio blindadas por testes unitários.
        </p>
      </td>
    </tr>
  </table>

  <br>

  <table width="100%">
    <tr>
      <td width="50%" bgcolor="#161b22" style="padding: 20px; border-radius: 15px; border: 1px solid #30363d; vertical-align: top;">
        <h4 style="color: #3fb950; font-family: sans-serif;">🛡️ Engineering</h4>
        <ul style="color: #8b949e; text-align: left; font-size: 14px;">
          <li>JUnit 5 & Mockito</li>
          <li>Clean Architecture</li>
          <li>Fail-Fast Validations</li>
        </ul>
      </td>
      <td width="50%" bgcolor="#161b22" style="padding: 20px; border-radius: 15px; border: 1px solid #30363d; vertical-align: top;">
        <h4 style="color: #d29922; font-family: sans-serif;">⚙️ Business</h4>
        <ul style="color: #8b949e; text-align: left; font-size: 14px;">
          <li>Gestão de Vagas</li>
          <li>Multi-tenant</li>
          <li>Filtros Avançados</li>
        </ul>
      </td>
    </tr>
  </table>

</div>


## 📘 Mentoria de fechamento do back-end
- Plano detalhado para concluir hoje até 21h: `docs/RESUMO_FINAL_SISTEMA.md`.


## 🧯 Solução para erro `TypeTag :: UNKNOWN`
Se aparecer erro como `java.lang.ExceptionInInitializerError` + `com.sun.tools.javac.code.TypeTag :: UNKNOWN`, normalmente é incompatibilidade de JDK com o processador do Lombok.

Use JDK 17 (ou 21) para compilar/rodar:
```bash
export JAVA_HOME=/root/.local/share/mise/installs/java/17.0.2
export PATH="$JAVA_HOME/bin:$PATH"
java -version
mvn -version
```

O `pom.xml` foi ajustado para:
- fixar `release` de compilação em Java 17;
- atualizar Lombok para versão compatível;
- falhar cedo com mensagem clara caso a JDK seja fora do range suportado.
