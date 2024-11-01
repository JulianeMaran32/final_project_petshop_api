# PetShop

## Descrição

PetShop é uma API RESTful para gerenciamento de petshops.

## Tecnologias

* Java 21
* Spring Boot
* Maven
* Docker

## Dependências

* Spring Boot Starter Web
* Spring Boot Starter Data JPA
* Spring Boot Starter Security
* Spring Boot Starter Validation
* Spring Boot Starter Test
* Spring Boot Starter Actuator
* Spring Boot Starter DevTools
* Spring Boot Starter Configuration Processor
* Spring Boot Starter Cache
* Spring Boot Starter Mail
* Spring Boot Starter Thymeleaf
* Spring Cloud Starter OpenFeign
* Lombok
* Mapstruct
* MySQL Driver
* Springdoc OpenAPI UI
* Runtime Javadoc
* Java JWT (jjwt)
* h2 Database

## Estrutura

```

```

## Execução

### Docker

```bash
docker compose up
```

### Maven

```bash
mvn spring-boot:run
```

## Documentação

### OpenAPI

A documentação da API está disponível em:

* `http://localhost:8080/swagger-ui.html`.
* [Swagger UI](http://localhost:8080/swagger-ui/index.html)
* [API Docs](http://localhost:8080/v3/api-docs)

### Postman

A coleção de requisições está disponível em:

* `https://www.postman.com/julianemaran/petshop`.

## Endpoints

### Usuários

* **POST /api/v1/users**: Cria um novo usuário.
* **GET /api/v1/users**: Retorna todos os usuários.
* **GET /api/v1/users/{id}**: Retorna um usuário pelo ID.
* **PUT /api/v1/users/{id}**: Atualiza um usuário pelo ID.
* **DELETE /api/v1/users/{id}**: Deleta um usuário pelo ID.
* **POST /api/v1/users/login**: Autentica um usuário.
* **POST /api/v1/users/logout**: Desautentica um usuário.
* **POST /api/v1/users/forgot-password**: Envia um email para redefinir a senha do usuário.
* **POST /api/v1/users/reset-password**: Redefine a senha do usuário.
* **POST /api/v1/users/refresh-token**: Atualiza o token de autenticação do usuário.
* **POST /api/v1/users/verify-email**: Verifica o email do usuário.
* **POST /api/v1/users/verify-token**: Verifica o token do usuário.
* **POST /api/v1/users/verify-password**: Verifica a senha do usuário.

### Pets

* **POST /api/v1/pets**: Cria um novo pet.
* **GET /api/v1/pets**: Retorna todos os pets.
* **GET /api/v1/pets/{id}**: Retorna um pet pelo ID.
* **PUT /api/v1/pets/{id}**: Atualiza um pet pelo ID.
* **DELETE /api/v1/pets/{id}**: Deleta um pet pelo ID.
* **GET /api/v1/pets/{id}/owner**: Retorna o dono de um pet pelo ID.
* **GET /api/v1/pets/{id}/vaccines**: Retorna as vacinas de um pet pelo ID.
* **GET /api/v1/pets/{id}/appointments**: Retorna as consultas de um pet pelo ID.
* **GET /api/v1/pets/{id}/services**: Retorna os serviços de um pet pelo ID.
* **GET /api/v1/pets/{id}/products**: Retorna os produtos de um pet pelo ID.

### Vacinas

* **POST /api/v1/vaccines**: Cria uma nova vacina.
* **GET /api/v1/vaccines**: Retorna todas as vacinas.
* **GET /api/v1/vaccines/{id}**: Retorna uma vacina pelo ID.
* **PUT /api/v1/vaccines/{id}**: Atualiza uma vacina pelo ID.
* **DELETE /api/v1/vaccines/{id}**: Deleta uma vacina pelo ID.

### Agendamento de Consultas

* **GET** `/api/vet-appointments` – Retorna todas as consultas
* **GET** `/api/vet-appointments/{id}` – Retorna uma consulta por ID
* **POST** /api/vet-appointments – Cria uma nova consulta
* **PUT** /api/vet-appointments/{id} – Atualiza uma consulta
* **DELETE** /api/vet-appointments/{id} – Deleta uma consulta

### Serviços

* GET /api/agendamentos — lista todos os agendamentos.
* GET /api/agendamentos/{id} — obtém um agendamento específico.
* POST /api/agendamentos — cria um novo agendamento.
* PUT /api/agendamentos/{id} — atualiza um agendamento existente.
* DELETE /api/agendamentos/{id} — exclui um agendamento.

### Produtos

* **POST /api/v1/products**: Cria um novo produto.
* **GET /api/v1/products**: Retorna todos os produtos.
* **GET /api/v1/products/{id}**: Retorna um produto pelo ID.
* **PUT /api/v1/products/{id}**: Atualiza um produto pelo ID.
* **DELETE /api/v1/products/{id}**: Deleta um produto pelo ID.

### Categorias

* **POST /api/v1/categories**: Cria uma nova categoria.
* **GET /api/v1/categories**: Retorna todas as categorias.
* **GET /api/v1/categories/{id}**: Retorna uma categoria pelo ID.
* **PUT /api/v1/categories/{id}**: Atualiza uma categoria pelo ID.
* **DELETE /api/v1/categories/{id}**: Deleta uma categoria pelo ID.
* **GET /api/v1/categories/{id}/products**: Retorna os produtos de uma categoria pelo ID.

### Endereços

* **POST /api/v1/addresses**: Cria um novo endereço.
* **GET /api/v1/addresses**: Retorna todos os endereços.
* **GET /api/v1/addresses/{id}**: Retorna um endereço pelo ID.
* **PUT /api/v1/addresses/{id}**: Atualiza um endereço pelo ID.
* **DELETE /api/v1/addresses/{id}**: Deleta um endereço pelo ID.
* **GET /api/v1/addresses/{id}/users**: Retorna os usuários de um endereço pelo ID.
* **GET /api/v1/addresses/{id}/pets**: Retorna os pets de um endereço pelo ID.

## Tratamento De Erros

* **400 Bad Request**: Quando há algum erro na solicitação, por exemplo, um dado inválido.
* **401 Unauthorized**: Quando o usuário fornece credenciais inválidas.
* **403 Forbidden**: Quando o usuário tenta acessar um recurso sem permissão.
* **404 Not Found**: Quando o email do usuário não é encontrado.
* **405 Method Not Allowed**: Quando o método HTTP não é suportado.
* **406 Not Acceptable**: Quando o tipo de mídia não é suportado.
* **415 Unsupported Media Type**: Quando o tipo de mídia não é suportado.
* **500 Internal Server Error**: Para qualquer erro inesperado no servidor.

## Testes

### Unitários

```bash
mvn test
```

### Integração

```bash
mvn verify
```

### Cobertura

```bash
mvn jacoco:report
```

## Cache

* **Cache Provider**: Spring Cache
* **Cache Store**: Redis
* **Cache Configuration**: Configurado para cachear respostas de API e dados frequentemente acessados.

## Logs

## Segurança

## Monitoramento

### Actuator

* `http://localhost:9090/actuator`
* [Swagger UI](http://localhost:9090/actuator/swagger-ui/index.html#/)
* [API Docs](http://localhost:9090/actuator/openapi/springdocDefault)

## Deploy

## CI/CD

## Contribuição

## Licença

## Autor

Juliane Maran

## Referências

## Anotações

### Exceções Tratadas

* MethodArgumentNotValidException.class,
* MissingServletRequestParameterException.class,
* HttpMessageNotReadableException.class,
* NoHandlerFoundException.class,
* AuthenticationException.class,
* AccessDeniedException.class,
* HttpRequestMethodNotSupportedException.class,
* HttpMediaTypeNotAcceptableException.class,
* HttpMediaTypeNotSupportedException.class,
* HttpMessageNotWritableException.class,
* Exception.class,
* PetShopBadRequestException.class,
* PetShopUnauthorizedException.class,
* PetShopForbiddenException.class,
* PetShopNotFoundException.class,
* PetShopInternalServerErrorException.class

| Exception                               | HTTP Status                | Message                                           |
|-----------------------------------------|----------------------------|---------------------------------------------------|
| MethodArgumentNotValidException         | 400 Bad Request            | A validação falhou para um ou mais argumentos.    |
| MissingServletRequestParameterException | 400 Bad Request            | Parâmetro de solicitação ausente.                 |
| HttpMessageNotReadableException         | 400 Bad Request            | Mensagem HTTP não legível.                        |
| NoHandlerFoundException                 | 404 Not Found              | Nenhum manipulador encontrado para a solicitação. |
| AuthenticationException                 | 401 Unauthorized           | Usuário não autenticado                           |
| AccessDeniedException                   | 403 Forbidden              | Usuário não possui permissão                      |
| HttpRequestMethodNotSupportedException  | 405 Method Not Allowed     | Método de solicitação HTTP não suportado.         |
| HttpMediaTypeNotAcceptableException     | 406 Not Acceptable         | Tipo de mídia HTTP não aceitável.                 |
| HttpMediaTypeNotSupportedException      | 415 Unsupported Media Type | Tipo de mídia não suportada.                      |
| HttpMessageNotWritableException         | 500 Internal Server Error  | Mensagem HTTP não gravável.                       |
| Exception                               | 500 Internal Server Error  | Erro interno no servidor.                         |
| PetShopBadRequestException              | 400 Bad Request            | (Custom message from exception)                   |
| PetShopUnauthorizedException            | 401 Unauthorized           | (Custom message from exception)                   |
| PetShopForbiddenException               | 403 Forbidden              | (Custom message from exception)                   |
| PetShopNotFoundException                | 404 Not Found              | (Custom message from exception)                   |
| PetShopInternalServerErrorException     | 500 Internal Server Error  | (Custom message from exception)                   |

---

Configurações necessárias para a segurança da aplicação:

* **Configuração de CORS**: Permitir que o frontend Angular faça requisições ao backend Spring.
* **Configuração de CSRF**: Configurar o repositório de tokens CSRF e adicionar o token CSRF às respostas.
* **Configuração de JWT**: Adicionar um filtro de autenticação JWT para proteger as rotas.
