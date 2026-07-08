# Simples Gateway Pagamentos

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

