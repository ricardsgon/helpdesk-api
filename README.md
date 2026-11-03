# Helpdesk API

REST API para um sistema de helpdesk (abertura e acompanhamento de chamados de suporte), desenvolvida com **Java 21** e **Spring Boot**.

O projeto nasceu da minha experiência de 2 anos como estagiário de suporte técnico na UNEMAT, atendendo chamados no dia a dia. A ideia é reproduzir em código o fluxo que eu vivia no atendimento.

## Tecnologias

- Java 21
- Spring Boot (Web MVC, Data JPA, Validation)
- PostgreSQL
- Lombok
- Maven (com Maven Wrapper)
- JUnit 5 e Mockito

## Pré-requisitos

- JDK 21
- PostgreSQL em execução (local ou Docker)
- Git

## Como rodar

1. Clone o repositório:

```
git clone https://github.com/ricardsgon/helpdesk-api.git
cd helpdesk-api
```

2. Tenha um PostgreSQL com o banco `helpdesk`. Com PostgreSQL local:

```sql
CREATE DATABASE helpdesk;
```

Ou, com Docker, um único comando sobe o banco já criado:

```
docker run --name helpdesk-db -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=helpdesk -p 5432:5432 -d postgres:16
```

3. A conexão é configurada em `src/main/resources/application.yaml` por variáveis de ambiente. Se você não definir nenhuma, valem os padrões abaixo (compatíveis com o comando Docker acima):

| Variável      | Padrão                                     |
|---------------|--------------------------------------------|
| `DB_URL`      | `jdbc:postgresql://localhost:5432/helpdesk` |
| `DB_USER`     | `postgres`                                 |
| `DB_PASSWORD` | `postgres`                                 |

> Não versione usuário e senha reais. Defina as variáveis no seu ambiente ou na configuração de execução da IDE.

As tabelas são criadas automaticamente na primeira execução (`ddl-auto: update`).

4. Execute a aplicação:

```
# Windows
mvnw.cmd spring-boot:run

# Linux / macOS (com Maven instalado)
mvn spring-boot:run
```

Por padrão, a API sobe em `http://localhost:8080`.

## Testes

```
# Windows
mvnw.cmd test

# Linux / macOS
mvn test
```

Os testes do controller e do service usam mocks. O teste `contextLoads` sobe a aplicação completa e precisa do PostgreSQL em execução.

## Endpoints

| Método   | Rota             | Descrição               | Resposta de sucesso |
|----------|------------------|-------------------------|---------------------|
| `POST`   | `/chamados`      | Abre um novo chamado    | `201 Created`       |
| `GET`    | `/chamados`      | Lista os chamados       | `200 OK`            |
| `GET`    | `/chamados/{id}` | Busca um chamado por ID | `200 OK`            |
| `PUT`    | `/chamados/{id}` | Atualiza um chamado     | `200 OK`            |
| `DELETE` | `/chamados/{id}` | Remove um chamado       | `204 No Content`    |

Campos do chamado:

- `titulo` e `descricao`: texto obrigatório
- `prioridade`: `BAIXA`, `MEDIA` ou `ALTA`
- `status`: `ABERTO`, `ANDAMENTO` ou `ENCERRADO`

Erros retornam um JSON com `status` e `message`: `400` para dados inválidos e `404` para chamado inexistente.

### Exemplo de requisição

```
curl -X POST http://localhost:8080/chamados \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Computador não liga",
    "descricao": "O computador da sala 3 não liga após queda de energia.",
    "prioridade": "ALTA",
    "status": "ABERTO"
  }'
```

## Estrutura do projeto

```
helpdesk-api
├── .mvn/wrapper
├── src
│   ├── main
│   │   ├── java/io/github/ricardsgon/helpdeskapi
│   │   │   ├── controller
│   │   │   ├── service
│   │   │   ├── dto
│   │   │   ├── database
│   │   │   │   ├── model
│   │   │   │   └── repository
│   │   │   └── exception
│   │   └── resources
│   └── test
└── pom.xml
```

## Próximos passos

- Autenticação e perfis (usuário / técnico / administrador)
- Documentação com Swagger / OpenAPI
- Docker Compose para subir API + banco
- Migrações de banco com Flyway
- Integração contínua com GitHub Actions

## Autor

**Ricardo Silva**
Estudante de Ciência da Computação, UNEMAT, Mato Grosso, Brasil

- GitHub: [@ricardsgon](https://github.com/ricardsgon)
- LinkedIn: [ricardssilva](https://www.linkedin.com/in/ricardssilva/)