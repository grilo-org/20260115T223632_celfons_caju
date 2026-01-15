# Authorizer API

Este projeto é uma API desenvolvida em Kotlin utilizando o Spring Boot, que autoriza transações com base em diferentes critérios. A API oferece três níveis de autorização:

- **Simple Authorizer** (`/v1/authorizer/l1`)
- **Authorizer with Fallback** (`/v1/authorizer/l2`)
- **Merchant Dependent Authorizer** (`/v1/authorizer/l3`)

## Requisitos

- JDK 21+
- Maven
- Spring Boot 3.0+

## Instalação

1. Clone o repositório:
   ```bash
   git clone https://github.com/celfons/caju.git

2. Compile e rode a aplicação
   ```bash 
   ./mvnw spring-boot:run

## Endpoints

### `/v1/authorizer/l1` - Simple Authorizer

Autoriza transações de maneira simples.

**Requisição:**
```json
POST /v1/authorizer/l1
Content-Type: application/json

{
  "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
  "accountId": "123456789",
  "amount": 150.75,
  "merchant": "Supermercado ABC",
  "mcc": "5411"
}
```
### `/v1/authorizer/l2` - Fallback Authorizer

Autoriza transações de maneira simples com fallback.

**Requisição:**
```json
POST /v1/authorizer/l2
Content-Type: application/json

{
  "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
  "accountId": "123456789",
  "amount": 150.75,
  "merchant": "Supermercado ABC",
  "mcc": "5411"
}
```

### `/v1/authorizer/l3` - Merchant Dependent Authorizer

Autoriza transações de maneira simples com fallback e uso do nome do comerciante

**Requisição:**
```json
POST /v1/authorizer/l3
Content-Type: application/json

{
  "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
  "accountId": "123456789",
  "amount": 150.75,
  "merchant": "Supermercado ABC",
  "mcc": "5411"
}
```

**Respostas:**
```json
200 OK - Transação aprovada
{ "code": "00" }

200 OK - Saldo insuficiente
{ "code": "51" }

200 OK - Falha na transação
{ "code": "07" }
```

**Suporte**

- http://localhost:8080/h2-console
- http://localhost:8081/actuator


**Tarefa L4**

#### Proposta
```
- Uso de imdepotency key, a chave para identificar a transação deve ser persistida e verificada a cada requisição
- Implementação de índice para otimizar a busca do identificador da transação
- Camada de cache distribuido para diminuir a latência da resposta de transação duplicadas
```
- Imdepotency Key
```mermaid
sequenceDiagram
    participant Client as Cliente
    participant Server as Servidor
    participant Database as Banco de Dados

    Client->>Server: Solicitação de Transação (ID: 12345)
    Server->>Database: Verificar ID da Transação
    Database-->>Server: ID não encontrado (primeira requisição)
    Server->>Database: Processar Transação
    Database-->>Server: Resultado da Transação
    Server->>Client: Confirmar Transação (Resultado)
    
    Client->>Server: Repetir Solicitação de Transação (ID: 12345)
    Server->>Database: Verificar ID da Transação
    Database-->>Server: ID encontrado (transação já processada)
    Server->>Client: Confirmar Transação (Resultado)

    Note right of Server: A mesma resposta é retornada, evitando duplicação.
