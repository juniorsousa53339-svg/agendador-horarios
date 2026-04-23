# Guia Angular como Camada de Apresentação (sem regra de negócio)

Este guia refatora a abordagem do front para respeitar a arquitetura cliente-servidor:

- **Backend (Spring Boot)**: regra de negócio, validações de domínio, persistência e decisões de fluxo.
- **Frontend (Angular)**: interface, validações simples de formulário e consumo de endpoints REST.

## 1) Fronteira obrigatória entre Angular e Spring

No Angular, **não implementar**:
- regras de conflito de horário;
- validações de domínio complexas;
- decisões de persistência (ex.: quando criar cliente + agendamento com regras condicionais);
- simulação de regras já existentes no `service` Java.

No Angular, **implementar apenas**:
- `required`, `minLength`, `maxLength`, máscaras e UX de formulário;
- chamadas HTTP com `HttpClient` para endpoints existentes;
- loading, tratamento de erro para feedback visual e navegação.

## 2) Contratos de API (conforme controllers Spring)

Endpoints principais já existentes no backend:

- `POST /clientes`
- `GET /clientes?idCliente={uuid}&nomeCliente={nome}`
- `POST /proprietarios`
- `GET /proprietarios?nome={nome}&id_proprietario={uuid}&email={email}`
- `POST /agendamentos`
- `GET /agendamentos?data={yyyy-MM-dd}&idCliente={uuid}`
- `PUT /agendamentos?dataHoraAtual={...}&idClienteAtual={...}&dataHoraNova={...}&idClienteNovo={...}`
- `DELETE /agendamentos?dataHora={...}&idCliente={...}`

> Observação: o front deve só enviar os dados e exibir resposta/erro. Regras continuam no backend.

## 3) Implementação Angular recomendada

### 3.1 Modelos tipados (DTOs)

```ts
// src/app/shared/models/agendamento.model.ts
export interface AgendamentoRequestDTO {
  idCliente: string;      // UUID
  idFuncionario: number;
  idServico: string;      // UUID
  dataHora: string;       // ISO-8601
}

export interface AgendamentoResponseDTO {
  idAgendamento: string;
  dataHora: string;
  idCliente: string;
  idFuncionario: number;
  idServico: string;
}
```

### 3.2 Services HTTP (sem regra de negócio)

```ts
// src/app/core/services/clientes-api.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Cliente {
  idCliente?: string;
  nomeCliente: string;
  telefoneCliente: string;
}

@Injectable({ providedIn: 'root' })
export class ClientesApiService {
  private readonly baseUrl = `${environment.apiUrl}/clientes`;

  constructor(private http: HttpClient) {}

  salvar(payload: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(this.baseUrl, payload);
  }

  buscar(idCliente: string, nomeCliente: string): Observable<Cliente[]> {
    const params = new HttpParams()
      .set('idCliente', idCliente)
      .set('nomeCliente', nomeCliente);

    return this.http.get<Cliente[]>(this.baseUrl, { params });
  }
}
```

```ts
// src/app/core/services/proprietarios-api.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Proprietario {
  idProprietario?: string;
  nome: string;
  telefone: string;
  email: string;
}

@Injectable({ providedIn: 'root' })
export class ProprietariosApiService {
  private readonly baseUrl = `${environment.apiUrl}/proprietarios`;

  constructor(private http: HttpClient) {}

  salvar(payload: Proprietario): Observable<Proprietario> {
    return this.http.post<Proprietario>(this.baseUrl, payload);
  }

  buscar(nome: string, idProprietario: string, email: string): Observable<Proprietario[]> {
    const params = new HttpParams()
      .set('nome', nome)
      .set('id_proprietario', idProprietario)
      .set('email', email);

    return this.http.get<Proprietario[]>(this.baseUrl, { params });
  }
}
```

```ts
// src/app/core/services/agendamentos-api.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AgendamentoRequestDTO, AgendamentoResponseDTO } from '../../shared/models/agendamento.model';

@Injectable({ providedIn: 'root' })
export class AgendamentosApiService {
  private readonly baseUrl = `${environment.apiUrl}/agendamentos`;

  constructor(private http: HttpClient) {}

  criar(payload: AgendamentoRequestDTO): Observable<AgendamentoResponseDTO> {
    return this.http.post<AgendamentoResponseDTO>(this.baseUrl, payload);
  }

  listarDoDia(data: string, idCliente: string): Observable<AgendamentoResponseDTO[]> {
    const params = new HttpParams().set('data', data).set('idCliente', idCliente);
    return this.http.get<AgendamentoResponseDTO[]>(this.baseUrl, { params });
  }

  alterar(
    dataHoraAtual: string,
    idClienteAtual: string,
    dataHoraNova: string,
    idClienteNovo: string
  ): Observable<AgendamentoResponseDTO> {
    const params = new HttpParams()
      .set('dataHoraAtual', dataHoraAtual)
      .set('idClienteAtual', idClienteAtual)
      .set('dataHoraNova', dataHoraNova)
      .set('idClienteNovo', idClienteNovo);

    return this.http.put<AgendamentoResponseDTO>(this.baseUrl, null, { params });
  }

  cancelar(dataHora: string, idCliente: string): Observable<void> {
    const params = new HttpParams().set('dataHora', dataHora).set('idCliente', idCliente);
    return this.http.delete<void>(this.baseUrl, { params });
  }
}
```

### 3.3 Component de formulário (somente UI + HTTP)

```ts
// src/app/features/agendamento/pages/agendar/agendar.component.ts
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AgendamentosApiService } from '../../../../core/services/agendamentos-api.service';

@Component({
  selector: 'app-agendar',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './agendar.component.html'
})
export class AgendarComponent {
  loading = false;
  erro = '';

  form = this.fb.group({
    idCliente: ['', Validators.required],
    idFuncionario: [null as number | null, Validators.required],
    idServico: ['', Validators.required],
    dataHora: ['', [Validators.required, Validators.minLength(16)]]
  });

  constructor(private fb: FormBuilder, private agendamentosApi: AgendamentosApiService) {}

  enviar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.erro = '';

    this.agendamentosApi.criar(this.form.getRawValue() as any).subscribe({
      next: () => {
        this.loading = false;
        this.form.reset();
      },
      error: (e) => {
        this.loading = false;
        this.erro = e?.error?.message ?? 'Não foi possível concluir o agendamento.';
      }
    });
  }
}
```

## 4) Checklist de qualidade arquitetural

Antes de aprovar qualquer código Angular:

- [ ] Nenhuma regra de domínio foi implementada no front.
- [ ] Não existe duplicação de validações complexas do backend.
- [ ] Services Angular só encapsulam chamadas REST.
- [ ] Componentes só tratam estado de tela/formulário.
- [ ] Mensagens de erro do backend são exibidas para o usuário.
- [ ] Tipos TypeScript representam DTOs reais da API.

## 5) Resultado esperado

Com essa estrutura:
- backend mantém autoridade total sobre regras de negócio;
- frontend fica simples, testável e desacoplado;
- evolução de regra no Spring não exige retrabalho de regra no Angular.
