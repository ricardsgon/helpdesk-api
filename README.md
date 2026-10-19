# Helpdesk API

REST API para um sistema de helpdesk (abertura e acompanhamento de chamados de suporte), desenvolvida com **Java 21** e **Spring Boot**.

O projeto nasceu da minha experiência de 2 anos como estagiário de suporte técnico na UNEMAT, atendendo chamados no dia a dia. A ideia é reproduzir em código o fluxo que eu vivia no atendimento.

## Tecnologias

- Java 21
- Spring Boot (Web MVC, Data JPA, Validation)
- PostgreSQL
- Lombok
- Maven (com Maven Wrapper)
- JUnit / Spring Boot Test

## Pré-requisitos

- JDK 21
- PostgreSQL em execução (local ou Docker)
- Git

## Como rodar

1. Clone o repositório:

```bash
git clone https://github.com/ricardsgon/helpdesk-api.git
cd helpdesk-api
```

2. Crie o banco de dados:

```sql
CREATE DATABASE helpdesk;
```

3. Configure a conexão em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/helpdesk
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update
```

> Não versione usuário e senha reais. Use variáveis de ambiente ou um arquivo local ignorado pelo Git.

4. Execute a aplicação:

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux / macOS (com Maven instalado)
mvn spring-boot:run
```

Por padrão, a API sobe em `http://localhost:8080`.

## Testes

```bash
# Windows
mvnw.cmd test

# Linux / macOS
mvn test
```

## Endpoints

<!-- PREENCHA com os endpoints reais do seu projeto. Exemplo de formato: -->

| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/chamados` | Abre um novo chamado |
| `GET` | `/chamados` | Lista os chamados |
| `GET` | `/chamados/{id}` | Busca um chamado por ID |
| `PUT` | `/chamados/{id}` | Atualiza um chamado |
| `DELETE` | `/chamados/{id}` | Remove um chamado |

### Exemplo de requisição

```bash
curl -X POST http://localhost:8080/chamados \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Computador não liga",
    "descricao": "O computador da sala 3 não liga após queda de energia."
  }'
```

## Estrutura do projeto

```
helpdesk-api
├── .mvn/wrapper
├── src
│   ├── main
│   └── test
└── pom.xml
```

## Próximos passos

- [ ] Autenticação e perfis (usuário / técnico / administrador)
- [ ] Status e prioridade dos chamados
- [ ] Documentação com Swagger / OpenAPI
- [ ] Docker Compose para subir API + banco
- [ ] Integração contínua com GitHub Actions

## Autor

**Ricardo Silva**
Estudante de Ciência da Computação, UNEMAT, Mato Grosso, Brasil

- GitHub: [@ricardsgon](https://github.com/ricardsgon)
- LinkedIn: [ricardssilva](https://www.linkedin.com/in/ricardssilva/)
