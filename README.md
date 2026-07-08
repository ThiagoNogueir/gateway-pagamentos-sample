# Gateway Pagamentos

API REST completa para gestão de clientes, contas, transferências e pagamentos (Pix, Boleto, Cartão de Crédito).

## Sobre o Projeto

Gateway de pagamentos com suporte a múltiplos métodos de pagamento (Pix, Boleto, Cartão de Crédito), transferências entre contas e extrato completo de movimentações.

### Tecnologias

- **Java 25** com **Spring Boot 4.1.0**
- **Maven** para build e gerenciamento de dependências
- **Spring Data JPA / Hibernate** para persistência
- **H2 Database** (file-based) para armazenamento
- **JUnit 5 + Spring Boot Test** para testes

### Estrutura do Projeto

```
src/main/java/com/banco/gatewaypagamentos/
  controller/   # Endpoints REST
  service/      # Lógica de negócio
  repository/   # Acesso a dados (Spring Data JPA)
  model/        # Entidades JPA
  dto/          # Records de request/response
  exception/    # Tratamento global de erros
```

### Como Executar

```bash
./mvnw spring-boot:run
```

A aplicação iniciará em `http://localhost:8080`. O console H2 está disponível em `/h2-console`.

### Collection Postman

Uma collection Postman pronta para testes está disponível no arquivo `postman_collection.json`.

## Índice

- [Clientes](#1-clientes)
- [Contas](#2-contas)
- [Transferências](#3-transferências)
- [Pagamentos](#4-pagamentos)
- [Extrato](#5-extrato)
- [Tratamento de Erros](#tratamento-de-erros)

---

## 1. Clientes

### Criar cliente

```http
POST /clientes
```

**Body:**

```json
{
  "nome": "João Silva",
  "cpfCnpj": "12345678901",
  "email": "joao@email.com",
  "telefone": "11999999999",
  "tipo": "PF"
}
```

**Resposta (201):**

```json
{
  "id": 1,
  "nome": "João Silva",
  "cpfCnpj": "12345678901",
  "email": "joao@email.com",
  "telefone": "11999999999",
  "tipo": "PF",
  "createdt": "2026-07-08T17:00:00"
}
```

### Listar clientes

```http
GET /clientes
```

### Buscar cliente por ID

```http
GET /clientes/{id}
```

### Atualizar cliente

```http
PUT /clientes/{id}
```

**Body:**

```json
{
  "nome": "João Silva Atualizado",
  "cpfCnpj": "12345678901",
  "email": "joao.novo@email.com",
  "telefone": "11988888888",
  "tipo": "PF"
}
```

### Excluir cliente

```http
DELETE /clientes/{id}
```

---

## 2. Contas

### Criar conta

```http
POST /contas
```

> O `clienteId` deve referenciar um cliente existente.

**Body:**

```json
{
  "numero": "12345-6",
  "titular": "João Silva",
  "banco": "Banco do Brasil",
  "agencia": "0001",
  "tipo": "CORRENTE",
  "saldo": 1000.00,
  "status": "ATIVA",
  "clienteId": 1
}
```

**Resposta (201):**

```json
{
  "id": 1,
  "numero": "12345-6",
  "titular": "João Silva",
  "banco": "Banco do Brasil",
  "agencia": "0001",
  "tipo": "CORRENTE",
  "saldo": 1000.00,
  "status": "ATIVA",
  "clienteId": 1,
  "createdt": "2026-07-08T17:00:00"
}
```

### Listar contas

```http
GET /contas
```

### Buscar conta por ID

```http
GET /contas/{id}
```

### Buscar contas por cliente

```http
GET /contas/cliente/{clienteId}
```

### Atualizar conta

```http
PUT /contas/{id}
```

### Excluir conta

```http
DELETE /contas/{id}
```

---

## 3. Transferências

### Realizar transferência

```http
POST /transferencias
```

> As contas de origem e destino devem existir e estar com status `ATIVA`.
> A conta de origem deve ter saldo suficiente.

**Body:**

```json
{
  "contaOrigem": "12345-6",
  "contaDestino": "67890-1",
  "valor": 250.00
}
```

**Resposta (200):**

```json
{
  "idTransacao": "1",
  "status": "CONCLUIDO",
  "mensagem": "Transferencia realizada com sucesso"
}
```

### Consultar saldo

```http
GET /transferencias/saldo/{numeroConta}
```

**Resposta (200):**

```
750.0
```

---

## 4. Pagamentos

### Realizar pagamento

```http
POST /pagamentos
```

> As contas de origem e destino devem existir e estar com status `ATIVA`.
> O método pode ser `PIX`, `BOLETO` ou `CARTAO`.

#### Pix

```json
{
  "contaOrigemId": 1,
  "contaDestinoId": 2,
  "valor": 150.00,
  "metodo": "PIX",
  "descricao": "Pagamento via Pix",
  "chavePix": "joao@email.com",
  "tipoChavePix": "EMAIL"
}
```

#### Boleto

```json
{
  "contaOrigemId": 1,
  "contaDestinoId": 2,
  "valor": 500.00,
  "metodo": "BOLETO",
  "descricao": "Pagamento de boleto",
  "codigoBarrasBoleto": "34191750090000123456789012345678901234567890",
  "dataVencimentoBoleto": "2026-08-07"
}
```

#### Cartão de Crédito

```json
{
  "contaOrigemId": 1,
  "contaDestinoId": 2,
  "valor": 300.00,
  "metodo": "CARTAO",
  "descricao": "Compra no cartao",
  "cartaoCreditoId": 1,
  "parcelas": 3
}
```

**Resposta (200):**

```json
{
  "id": 1,
  "contaOrigemId": 1,
  "contaDestinoId": 2,
  "valor": 150.00,
  "status": "CONCLUIDO",
  "metodo": "PIX",
  "descricao": "Pagamento via Pix",
  "createdt": "2026-07-08T17:00:00"
}
```

### Listar pagamentos

```http
GET /pagamentos
```

### Buscar pagamento por ID

```http
GET /pagamentos/{id}
```

### Buscar pagamentos por conta

```http
GET /pagamentos/conta/{contaId}
```

---

## 5. Extrato

### Extrato por número da conta

```http
GET /extratos/{numeroConta}
```

### Extrato completo por ID da conta

```http
GET /extratos/conta/{contaId}
```

**Resposta (200):**

```json
{
  "numeroConta": "12345-6",
  "titular": "João Silva",
  "saldoAtual": 750.0,
  "movimentacoes": [
    {
      "id": "1",
      "tipo": "DEBITO",
      "descricao": "Transferencia debito - 67890-1",
      "valor": 250.0,
      "dataHora": "2026-07-08T17:00:00",
      "status": "CONCLUIDO"
    }
  ]
}
```

---

## Tratamento de Erros

Todas as respostas de erro seguem o formato:

```json
{
  "erro": "Mensagem descritiva do erro",
  "status": 400,
  "timestamp": "2026-07-08T17:00:00"
}
```

| Código | Quando ocorre                              |
|--------|--------------------------------------------|
| 400    | Validação: valor inválido, saldo insuficiente, método inválido, etc. |
| 404    | Entidade não encontrada (cliente, conta, pagamento, etc.) |
| 500    | Erro interno inesperado                    |

### Exemplos de erros

- `"Conta nao encontrada: 12345-6"` → 404
- `"Conta origem nao esta ativa"` → 400
- `"Saldo insuficiente"` → 400
- `"Valor invalido"` → 400
- `"Cliente ja cadastrado com este CPF/CNPJ"` → 400
- `"Chave Pix obrigatoria"` → 400
- `"Limite do cartao insuficiente"` → 400
