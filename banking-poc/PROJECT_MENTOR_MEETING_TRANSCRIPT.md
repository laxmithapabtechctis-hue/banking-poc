# PROJECT MENTOR MEETING TRANSCRIPT

> **Scope note:** This transcript is based on the files currently available in this project. It is a backend-only REST API. I found no frontend pages, authentication, deployment files, database migration scripts, or Git history in the available project folder. Where a detail cannot be proven from the files, this transcript says so rather than guessing.

---

## Part 1 — Project Introduction

### What I can say

“The project I worked on is called **Banking POC**, which stands for Banking Proof of Concept. It is a Java and Spring Boot REST API for managing banking customer profiles.

The main purpose is to provide one backend service where a user interface, a tester, or another system can create, read, search, update, and delete customer information. Instead of directly changing database records, they call clear API endpoints.

I built this as a focused customer-management foundation. It stores a customer number, name, email, phone number, address, date of birth, and timestamps for when the record was created and last changed.

It is important to say that this is not yet a full banking application. It does not contain accounts, balances, transactions, login, roles, or a browser frontend. It is the customer-management backend that such features could build on later.”

### Big-picture mental model

```text
API client (Swagger UI, Postman, or a future frontend)
                       |
                       | HTTP request
                       v
CustomerController  -> chooses the correct API operation
                       |
                       v
CustomerService     -> applies validation and business rules
                       |
                       v
CustomerRepository  -> communicates with the database through JPA
                       |
                       v
MySQL database      -> stores Customer records
                       |
                       v
JSON HTTP response  -> returned to the caller
```

### Four questions

| Question | Answer |
|---|---|
| What is happening? | The application manages customer records through web API requests. |
| Why? | A banking system needs a consistent place to create and maintain customer information. |
| How? | Spring Boot receives requests; service code applies rules; JPA writes to MySQL. |
| Connection? | The controller, service, repository, entity, and database each perform one part of one request. |

---

## Part 2 — Project Overview

### What a user actually opens

There is **no frontend application or homepage in this repository**. A user currently interacts with the backend using an API client. The included OpenAPI/Swagger dependency is intended to provide interactive API documentation when the application runs. The project does not configure a custom Swagger URL; the common Springdoc default is `/swagger-ui/index.html`, but this exact runtime URL was not confirmed by running the application.

### Natural architecture explanation

“Because this project is backend-only, the starting point is not a webpage. A tester can send a request from Swagger UI or Postman, and a future website or mobile application could send the same requests. The request reaches the customer controller. The controller passes it to the service layer, where the project checks business rules such as whether the email or customer number already exists. The repository then stores or fetches data from MySQL. Finally, the application returns a JSON response with either customer data or a clear error.”

### Main capabilities

- Create a customer.
- Retrieve all customers.
- Search customers by first or last name.
- Retrieve one customer by database ID.
- Update an existing customer.
- Delete a customer.
- Validate required request fields and email format.
- Return structured errors for invalid input, duplicates, and missing customers.
- Describe the API using OpenAPI annotations.

---

## Part 3 — Technologies Used

| Technology | Where I used it | Why I used it |
|---|---|---|
| Java 21 | Entire application | Java is the programming language used to write the backend. |
| Spring Boot 4.1.1 | Application startup and configuration | It sets up a web application quickly and connects the project pieces together. |
| Spring MVC / WebMVC | `CustomerController` | It maps HTTP URLs such as `GET /api/customers` to Java methods. |
| Spring Data JPA / Hibernate | `CustomerRepository`, `Customer` | It lets Java code work with database records using objects instead of manually writing SQL for every operation. |
| MySQL Connector/J | Runtime database connection | It is the driver that allows the Java application to communicate with MySQL. |
| MySQL | Configured in `application.properties` | It stores the customer data persistently. |
| Jakarta Bean Validation | `CustomerRequest` | It rejects incomplete data and invalid email addresses before the service saves it. |
| Springdoc OpenAPI / Swagger UI | `OpenApiConfig` and controller annotations | It documents the API and is intended to give testers an interactive screen for endpoints. |
| Maven + Maven Wrapper | `pom.xml`, `mvnw`, `mvnw.cmd` | Maven downloads dependencies and builds/runs/tests the application consistently. |
| JUnit / Spring Boot test support | `BankingPocApplicationTests` | It provides the current application-context smoke test. |

### If my mentor asks “why Spring Boot?”

“I used Spring Boot because it gives me the web server, request routing, dependency injection, validation, and database integration in one ecosystem. That lets me focus on the customer rules instead of building low-level plumbing myself.”

**Dependency injection** means Spring creates shared objects, such as `CustomerService`, and supplies them where needed. For example, the controller receives a `CustomerService` in its constructor.

### If my mentor asks “why JPA?”

“JPA lets me represent a database row as a Java `Customer` object. The repository then gives standard operations such as save, find, and delete. It keeps this proof of concept simple and avoids repetitive database code.”

---

## Part 4 — Project Structure

```text
banking-poc/
├── pom.xml                         Maven dependencies and build settings
├── mvnw / mvnw.cmd                 Maven wrapper scripts
├── HELP.md                         Generated setup notes
├── .mvn/wrapper/                   Maven-wrapper configuration
├── src/
│   ├── main/
│   │   ├── java/com/example/banking_poc/
│   │   │   ├── BankingPocApplication.java
│   │   │   ├── config/OpenApiConfig.java
│   │   │   ├── controller/CustomerController.java
│   │   │   ├── service/CustomerService.java
│   │   │   ├── repository/CustomerRepository.java
│   │   │   ├── entity/Customer.java
│   │   │   ├── dto/CustomerRequest.java
│   │   │   ├── dto/CustomerResponse.java
│   │   │   ├── dto/ErrorResponse.java
│   │   │   └── exception/
│   │   └── resources/application.properties
│   └── test/java/.../BankingPocApplicationTests.java
└── target/                         Generated build output; not source code
```

### File priorities for the meeting

| Priority | File | Why it matters | Show it? |
|---|---|---|---|
| 🔴 | `controller/CustomerController.java` | Defines all public API endpoints. | Yes. |
| 🔴 | `service/CustomerService.java` | Contains the main customer rules and CRUD flow. | Yes. |
| 🔴 | `entity/Customer.java` | Defines the stored customer data. | Yes. |
| 🔴 | `repository/CustomerRepository.java` | Is the database access layer. | Yes. |
| 🔴 | `dto/CustomerRequest.java` | Defines and validates client input. | Yes. |
| 🔴 | `exception/GlobalExceptionHandler.java` | Converts errors into useful HTTP responses. | Yes. |
| 🟡 | `application.properties` | Shows the database connection and JPA behavior. | Yes, but do not expose real credentials on a shared screen. |
| 🟡 | `config/OpenApiConfig.java` | Defines API documentation title and metadata. | Optional. |
| 🟡 | `pom.xml` | Shows the technology stack. | Yes, briefly. |
| 🟢 | `dto/CustomerResponse.java` | Defines the data returned after a successful call. | Mention or show if asked. |
| 🟢 | `dto/ErrorResponse.java` | Defines error response shape. | Show alongside error handling if time allows. |
| 🟢 | exception classes | Small named error types. | Mention rather than dwell on them. |
| 🟢 | `BankingPocApplicationTests.java` | Current smoke test only. | Show in testing section. |

### What I can say while showing the folders

“I organized the backend by responsibility. The controller is the API entry point, the service contains the application rules, the repository talks to the database, and the entity defines the stored customer. DTOs are separate request and response objects, so the API contract is not tied directly to the database object. Exceptions and the global handler make errors consistent.”

---

## Part 5 — What I Actually Implemented

### 1. Customer CRUD operations

**What it does:** CRUD means Create, Read, Update, and Delete. This project provides all four operations for customer profiles.

**Why it is needed:** These are the core operations required to maintain customer records.

**How it is implemented:** `CustomerController` receives HTTP requests. `CustomerService` coordinates the work. `CustomerRepository`, which extends `JpaRepository`, persists or retrieves `Customer` objects.

**Important files:** `CustomerController.java`, `CustomerService.java`, `CustomerRepository.java`, `Customer.java`.

**Data flow:**

```text
API client
  ↓ POST /api/customers, GET, PUT, or DELETE
CustomerController
  ↓ calls a service method
CustomerService
  ↓ calls repository methods
CustomerRepository / JPA
  ↓
MySQL
  ↓
CustomerResponse or an error response
```

**What I can say:**

“The main feature is full customer CRUD. I expose REST endpoints for creating, listing, finding, updating, and deleting customers. I separated the API controller from the service logic and the database repository so each layer has one clear responsibility.”

### 2. Customer search

**What it does:** Finds customers whose first name or last name contains the supplied text, ignoring letter case.

**Why it is needed:** A user may not know a customer’s database ID but may know part of a name.

**How it is implemented:** The repository declares `findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase`. Spring Data JPA derives the database query from this method name.

**Important files:** `CustomerController.java`, `CustomerService.java`, `CustomerRepository.java`.

**What I can say:**

“I added a search endpoint that accepts a name query parameter. The repository searches both first and last names and ignores uppercase or lowercase differences. The service converts every matching database record into a response object.”

### 3. Input validation

**What it does:** Rejects missing customer number, first name, last name, email, phone number, and date of birth; it also checks email format.

**Why it is needed:** The database should not receive incomplete or obviously malformed customer data.

**How it is implemented:** `CustomerRequest` uses validation annotations such as `@NotBlank`, `@NotNull`, and `@Email`. The controller uses `@Valid` before passing the request to the service.

**What I can say:**

“Before creating or updating a customer, I validate the request object. This protects the database from missing required values and invalid email syntax. If validation fails, the global exception handler returns a 400 Bad Request with the failed fields.”

### 4. Duplicate protection

**What it does:** Prevents two customers from using the same customer number or email.

**Why it is needed:** A customer number and email are intended to identify a customer uniquely.

**How it is implemented:** The service checks the repository before saving. The database entity also marks both columns as unique, providing a second layer of intent. During an update, the service allows the current customer to keep its own existing value.

**What I can say:**

“I check uniqueness at the service layer before saving. For updates, I compare IDs so the same customer can keep their own email or customer number, but another customer cannot take it. I also marked these entity fields as unique.”

### 5. Consistent errors

**What it does:** Returns a structured response for validation errors, customer-not-found errors, and duplicate-customer errors.

**Why it is needed:** API callers need predictable status codes and messages rather than raw internal errors.

**How it is implemented:** The service throws custom runtime exceptions; `GlobalExceptionHandler` catches them and creates `ErrorResponse` objects.

**What I can say:**

“Instead of handling errors inside every endpoint, I created a global exception handler. That centralizes error formatting. Missing customers return 404, duplicates return 409 Conflict, and invalid input returns 400.”

### 6. API documentation

**What it does:** Adds endpoint descriptions and an OpenAPI configuration.

**Why it is needed:** It makes the backend easier for testers and future frontend developers to understand and try.

**How it is implemented:** Controller methods use `@Operation`, `@ApiResponse`, and parameter annotations; `OpenApiConfig` defines the API title, description, version, contact, and license metadata.

---

## Part 6 — Complete User Flow: Create a Customer

This is the most important journey to demonstrate.

### What the user sees

1. A tester opens an API tool, such as the Swagger UI intended by this project, or uses Postman.
2. They send `POST /api/customers` with customer data in JSON.
3. They receive either a `201 Created` response containing the saved customer or an error explaining why it failed.

### What happens behind the scenes

1. Spring maps the HTTP request to `CustomerController.createCustomer`.
2. `@Valid` validates the `CustomerRequest` fields.
3. The controller calls `customerService.createCustomer(request)`.
4. The service checks whether the requested customer number already exists.
5. The service checks whether the email already exists.
6. The service creates a `Customer` entity, copies the requested fields, and sets `createdAt` and `updatedAt` to the current time.
7. The repository saves the entity through JPA/Hibernate into MySQL.
8. The service converts the saved entity into a `CustomerResponse`.
9. The controller sends it back with HTTP status `201 Created`.

```text
POST /api/customers + JSON body
        ↓
CustomerController.createCustomer(@Valid CustomerRequest)
        ↓
CustomerService.createCustomer
        ↓ checks duplicate customer number and email
CustomerRepository.save(Customer)
        ↓
MySQL
        ↓
CustomerResponse + HTTP 201
```

### Example request

```json
{
  "customerNumber": "CUST1001",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "9876543210",
  "address": "123 Main Street, New Delhi",
  "dateOfBirth": "1995-06-15"
}
```

### What I can say while demonstrating it

“I will demonstrate customer creation because it shows the full architecture. I send a JSON request to the create endpoint. The controller receives it, validation checks the required fields, and the service checks that the customer number and email are not already in use. It then saves the new customer through the repository. The response returns the database-generated ID and timestamps, which confirms the record was saved.”

---

## Part 7 — Frontend Explanation

### Confirmed project status

**No frontend was found in the available source tree.** Therefore there are no pages, components, navigation routes, forms, UI state, client-side API calls, loading indicators, or browser-side error handling to explain as implemented features.

### Honest transcript

“This repository currently focuses only on the backend API. I did not find a frontend codebase here, so I would not describe React components or website pages as part of this implementation. At the moment, Swagger UI or a tool such as Postman is the practical way to interact with the API. A future frontend could consume the same endpoints without changing the core customer logic.”

### What to show

- Do **not** claim there is a homepage, login screen, or frontend code.
- If the app is running, show Swagger UI or Postman instead.
- If neither is available, show `CustomerController.java` as the API interface.

---

## Part 8 — Backend Explanation

### The backend layers

```text
Controller  → HTTP/API boundary
Service     → business rules and orchestration
Repository  → database operations
Entity      → database-shaped Java object
DTO         → API input/output-shaped Java object
Exception handler → consistent error responses
```

### Natural transcript

“The backend uses a layered design. The controller layer defines the URLs and HTTP methods. It is deliberately thin: it accepts the request and calls the service.

The service layer contains the important rules, such as checking duplicate customer numbers and emails, assigning timestamps, and deciding when a missing customer should produce an error.

The repository layer is the only layer that directly asks JPA to access customer data. The entity represents a customer as stored in the database. DTOs represent the data contract sent into and out of the API, which keeps the external API separate from the database model.

Finally, the global exception handler converts known problems into clear HTTP responses.”

### Middleware and authentication

I found no custom middleware/filter and no authentication or authorization implementation. Spring itself provides the web request handling, but this project does not define login, tokens, roles, or access restrictions.

---

## Part 9 — Database Explanation

### Confirmed configuration

- Database technology: MySQL.
- Database name in configuration: `banking_poc`.
- Connection target: local machine at port `3306`.
- JPA setting: `spring.jpa.hibernate.ddl-auto=update`.
- SQL logging: enabled with `spring.jpa.show-sql=true`.

### What `ddl-auto=update` means

At application startup, Hibernate attempts to adjust the database schema to match the `Customer` entity without deleting existing data. This is convenient for a proof of concept. It is not a complete production migration strategy.

### Customer data model

The `Customer` entity maps to a database table. The exact generated physical table name was not confirmed from a live database; it is derived from the entity named `Customer` because no explicit `@Table(name = ...)` is present.

| Field | Meaning | Database role |
|---|---|---|
| `id` | Database-generated customer ID | Primary key: uniquely identifies each row. |
| `customerNumber` | Business/customer identifier | Required and unique. |
| `firstName` / `lastName` | Customer name | Searchable fields. |
| `email` | Customer email | Required and unique. |
| `phoneNumber` | Customer phone | Required at API input level. |
| `address` | Residential address | Optional in the request DTO. |
| `dateOfBirth` | Customer birth date | Required at API input level. |
| `createdAt` | Creation timestamp | Set by service code. |
| `updatedAt` | Last-update timestamp | Set by service code. |

### Relationships

No relationships exist in the entity. There are no foreign keys, accounts, transactions, or other linked tables in the available code.

### CRUD in database terms

- **Create:** `customerRepository.save(customer)` inserts a new row.
- **Read:** `findAll`, `findById`, and derived search/unique-lookup methods retrieve rows.
- **Update:** the service fetches the existing row, changes fields, then calls `save`.
- **Delete:** the service fetches the customer, then calls `delete`.

### What I can say

“I use MySQL for persistent storage. The `Customer` entity defines the columns. The ID is the primary key, which means each row has a unique database identity. Customer number and email are marked unique, and there are no relationships yet because this POC only manages standalone customer profiles. JPA and Hibernate translate the repository operations into database queries.”

---

## Part 10 — API Explanation

### Endpoint table

| Method | Endpoint | Purpose | Request/input | Response |
|---|---|---|---|---|
| `POST` | `/api/customers` | Create a customer | Valid `CustomerRequest` JSON | `201` + `CustomerResponse`; `400` or `409` on known errors |
| `GET` | `/api/customers` | List all customers | None | `200` + array of `CustomerResponse` |
| `GET` | `/api/customers/search?name=John` | Search first/last name | Required `name` query parameter | `200` + matching customer array |
| `GET` | `/api/customers/{id}` | Find customer by ID | Path ID | `200` + customer; `404` if missing |
| `PUT` | `/api/customers/{id}` | Replace/update a customer’s fields | Path ID + valid `CustomerRequest` JSON | `200` + updated customer; `400`, `404`, or `409` |
| `DELETE` | `/api/customers/{id}` | Delete a customer | Path ID | `204 No Content`; `404` if missing |

### Endpoint explanation template

“The caller uses the endpoint. The controller maps it to a method. The service applies business rules. The repository accesses MySQL. The controller returns a response object and a relevant HTTP status.”

### Important status codes

- `200 OK`: the request succeeded.
- `201 Created`: a new customer was created.
- `204 No Content`: deletion succeeded and there is no response body.
- `400 Bad Request`: validation failed.
- `404 Not Found`: no customer exists with that ID.
- `409 Conflict`: the requested customer number or email is already used.

### Natural API transcript

“I designed the API around the `/api/customers` resource. I use standard HTTP methods: POST creates, GET reads, PUT updates, and DELETE removes. The controller documents these operations with OpenAPI annotations. Each successful request returns a response DTO, while validation, missing records, and duplicates produce meaningful HTTP status codes.”

---

## Part 11 — Authentication and Security

### Confirmed facts

No authentication or authorization code exists in the available project:

- No registration or login endpoints.
- No password field or password hashing.
- No JWT, session, cookie, OAuth, roles, or permissions.
- No Spring Security dependency is listed.
- No API access control is implemented in this repository.

### Safe answer if asked “How are passwords stored?”

“Passwords are not part of this proof of concept. The project does not implement users or login, so it does not store passwords.”

### Safe answer if asked “How is the API protected?”

“Authentication and authorization have not yet been implemented in the available code. That is a known limitation, and adding Spring Security with an appropriate authentication approach would be a priority before using this API in production.”

### Security concern to acknowledge

The MySQL username and password are present directly in `application.properties`. That is acceptable only as a local learning/POC setup. For real use, move secrets to environment variables or a secrets manager and never commit them.

---

## Part 12 — Important Code Walkthrough

### `BankingPocApplication.java`

**Purpose:** Application entry point.

**Important code:** `SpringApplication.run(BankingPocApplication.class, args);`

**What calls it:** The Java/Maven run command.

**What it calls:** Spring Boot startup, component scanning, web server setup, and configuration loading.

**If removed:** The application would not start.

**Say aloud:** “This is the main entry point. The `@SpringBootApplication` annotation tells Spring Boot to start and discover the controllers, services, repositories, and configuration classes in this package tree.”

### `CustomerController.java`

**Purpose:** Public HTTP interface for customer operations.

**Important code:** `@RequestMapping("/api/customers")` sets a shared base path. `@PostMapping`, `@GetMapping`, `@PutMapping`, and `@DeleteMapping` define the individual operations.

**Input:** Request body, path ID, or query parameter depending on the endpoint.

**Output:** `ResponseEntity` with data and HTTP status.

**Calls:** `CustomerService`.

**Why thin:** It keeps HTTP concerns separate from business rules.

### `CustomerService.java`

**Purpose:** Main business logic.

**Important methods:** `createCustomer`, `getAllCustomers`, `searchCustomers`, `getCustomerById`, `updateCustomer`, `deleteCustomer`, and private `convertToResponse`.

**Calls:** `CustomerRepository`; throws `CustomerNotFoundException` and `DuplicateCustomerException` where appropriate.

**Key detail:** Update checks uniqueness but permits a customer to keep their own existing email/customer number by comparing IDs.

**If removed:** The controller would have no logic to execute and the application’s rules would disappear.

### `Customer.java`

**Purpose:** JPA entity: the Java representation of a stored customer record.

**Important annotations:** `@Entity` makes it persistable; `@Id` marks the primary key; `@GeneratedValue(strategy = GenerationType.IDENTITY)` asks the database to generate IDs; `@Column(unique = true, nullable = false)` defines constraints for customer number and email.

### `CustomerRepository.java`

**Purpose:** Database access interface.

**Important code:** Extending `JpaRepository<Customer, Long>` provides save, find, and delete operations. The declared lookup/search methods are turned into queries by Spring Data JPA.

**If removed:** The service could not access MySQL using its current design.

### `CustomerRequest.java` and `CustomerResponse.java`

**Purpose:** API data transfer objects (DTOs).

**Why separate them from `Customer`:** Request validation belongs on incoming API data, and response data can be shaped independently of future database changes.

### `GlobalExceptionHandler.java`

**Purpose:** One central location for turning known Java exceptions into API errors.

**Important code:** `@RestControllerAdvice` applies across controllers; `@ExceptionHandler` methods map specific exceptions to status codes and `ErrorResponse` objects.

### `OpenApiConfig.java`

**Purpose:** Defines API documentation metadata.

### `application.properties`

**Purpose:** Names the app and configures local MySQL/JPA behavior.

---

## Part 13 — Technical Decisions

| Decision | Confirmed reason / reasonable interpretation | Alternative | Why it fits this POC |
|---|---|---|---|
| Spring Boot web application | Confirmed by dependencies and annotations; developer intent is not stated. | Plain Java server, another framework. | It provides standard REST API building blocks quickly. |
| Layered controller/service/repository design | Confirmed by package structure. | Put all logic in controllers. | It makes API, rules, and persistence easier to understand and change separately. |
| JPA repository | Confirmed by `JpaRepository`. | Handwritten JDBC/SQL. | It reduces repetitive CRUD code for a simple entity. |
| MySQL | Confirmed by driver and configuration. | PostgreSQL, H2, etc. | It is a common relational database suitable for structured customer fields. |
| Request/response DTOs | Confirmed by separate DTO classes. | Expose entity directly. | They create a clearer API contract and enable validation. |
| Custom errors + central handler | Confirmed by exception package and advice class. | Handle errors in each endpoint. | It creates consistent client-facing errors. |
| Swagger/OpenAPI annotations | Confirmed by dependency/config/annotations. | Separate manual documentation. | It documents and can help test the API close to the code. |

### How to avoid overstating intent

Say: “The code shows this design. A reasonable reason for it is…” rather than saying the original developer definitely made a specific decision unless it is documented.

---

## Part 14 — Challenges and How I Addressed Them

There is no issue tracker, commit history, or written development diary in the available project, so I cannot prove which problems were personally encountered. The following are **likely implementation challenges demonstrated by the code**, not claims about a specific past event.

### Keeping unique customer data

**Likely problem:** Two requests could try to use the same email or customer number.

**Implemented solution:** The service checks for existing values before save; the entity also declares those columns unique; duplicates become `409 Conflict` responses.

**What I can say:** “One important concern was maintaining unique identifiers. I implemented checks for email and customer number, including special update logic so a customer can retain their own values.”

### Returning useful errors

**Likely problem:** Raw framework errors are hard for an API caller to understand.

**Implemented solution:** A global handler creates a standard response with timestamp, status, error label, and message.

### Mapping between API and database data

**Likely problem:** The database entity should not automatically become the public API format.

**Implemented solution:** Separate request and response DTOs; `convertToResponse` explicitly maps the entity to its output object.

---

## Part 15 — Testing

### Automated test found

`BankingPocApplicationTests.java` contains one `@SpringBootTest` test named `contextLoads`. It checks whether the Spring application context can start.

### What it does not prove

It does not exercise individual endpoints, duplicate logic, validation responses, searches, updates, deletes, or MySQL behavior. I did not execute the test in preparing this transcript, so a passing result could not be confirmed from the available files.

### Manual test plan

| Test | Action | Expected result |
|---|---|---|
| Create valid customer | POST valid JSON | `201 Created`, ID and timestamps returned. |
| Create duplicate email | POST same email | `409 Conflict`. |
| Create missing field | POST without first name, for example | `400 Bad Request`. |
| List customers | GET `/api/customers` | `200` and an array. |
| Search | GET search with a name | `200` and matching names, case-insensitive. |
| Read missing customer | GET a nonexistent ID | `404 Not Found`. |
| Update existing customer | PUT valid data | `200` and new `updatedAt`. |
| Update with another customer’s email | PUT duplicate email | `409 Conflict`. |
| Delete existing customer | DELETE ID | `204 No Content`. |
| Delete missing customer | DELETE nonexistent ID | `404 Not Found`. |

### What I can say

“The current automated test is a startup smoke test. For full confidence, I would add controller/service tests for every CRUD path and the error cases. I would also verify the endpoints manually through Swagger UI or Postman against a local MySQL database.”

---

## Part 16 — Current Project Status

### Confirmed implemented

- Customer CRUD API.
- Search by first/last name.
- MySQL/JPA configuration.
- Request validation.
- Duplicate protection.
- Structured errors.
- OpenAPI configuration and annotations.
- One application-context test class.

### Confirmed missing from available files

- Frontend/UI.
- Authentication/authorization.
- Accounts, balances, transactions, and other banking functions.
- Database schema migration scripts.
- Environment-based secret configuration.
- API pagination/sorting.
- Comprehensive automated tests.
- Docker/deployment configuration.

### Limitations / improvements to mention honestly

- Credentials are hardcoded in properties, which should be changed before any real deployment.
- Application timestamps are assigned in service code using the server’s local time.
- Duplicate checks are useful but a real production system also needs to handle concurrent writes robustly at the database/application design level.
- Search has no pagination and listing can grow large.
- Validation reports field errors as a stringified map rather than a structured JSON field map.

---

## Part 17 — Future Improvements

### Short-term

1. Move database credentials to environment variables or a secret manager.
2. Add endpoint tests for success, validation, duplicate, not-found, and deletion cases.
3. Add an API README with setup and sample requests.
4. Improve validation-error JSON so individual field errors are structured.

### Medium-term

1. Add pagination and sorting to customer list/search endpoints.
2. Add database migrations such as Flyway or Liquibase instead of relying only on `ddl-auto=update`.
3. Add a frontend or staff dashboard that calls these APIs.
4. Add logging, request IDs, and monitoring-friendly error details.

### Advanced / production-focused

1. Add Spring Security, passwords only if user accounts are introduced, and role-based authorization.
2. Create banking domain entities such as accounts and transactions with proper relationships.
3. Add audit history, encryption/data privacy controls, rate limiting, and secure deployment configuration.
4. Use a production database strategy, backups, health checks, and CI/CD automation.

---

## Part 18 — Complete Meeting Transcript

### Opening

“Hi, I’ll walk through my Banking POC. I will first explain the goal, then the architecture and project structure, demonstrate the main API flow, and finish with testing, limitations, and next steps.”

### Project introduction

“This is a backend REST API for managing customer profiles in a banking context. I focused the POC on customer management rather than trying to build every banking feature at once. The API supports creating, listing, searching, retrieving, updating, and deleting customers.”

### Architecture

“The system has four main layers: the API controller, the service containing rules, the repository that accesses the database, and the MySQL database itself. Since there is no frontend in this repository, Swagger UI or Postman acts as the caller for demonstration.”

### Technology stack

“I used Java 21 and Spring Boot. Spring MVC maps HTTP endpoints to controller methods. Spring Data JPA and Hibernate make database operations easier through Java objects and repositories. MySQL stores the data. Bean Validation checks input, and Springdoc/OpenAPI documents the API.”

### Project structure

“The controller folder defines endpoints, service holds business logic, repository connects to the database, entity defines the database record, DTOs define API request and response shapes, and the exception package provides consistent errors.”

### Feature 1: create customer

“For create customer, I send a POST request with customer JSON. The controller validates it, the service checks uniqueness, then creates and saves the entity. The application returns a 201 response with the saved customer, including its generated ID and timestamps.”

### Feature 2: read, search, update, delete

“I also added a list endpoint, lookup by ID, case-insensitive name search, update, and delete. Update repeats the uniqueness checks but allows the existing customer to retain their own email and customer number.”

### Backend and errors

“The controller stays thin. The service owns business decisions. If a customer is missing, it throws a not-found exception. If data conflicts, it throws a duplicate exception. The global exception handler turns these into 404 and 409 responses, while validation errors become 400.”

### Database

“The MySQL database is configured locally. The Customer entity represents the stored record. The database-generated ID is the primary key. Email and customer number are unique. There are no relations yet because this POC only covers customer profiles.”

### Testing

“The repository currently has a Spring context-load smoke test. I would extend it with endpoint-level tests covering successful paths and errors. I would also manually verify requests through Swagger or Postman.”

### Current status and next steps

“The customer-management API is implemented, but it is still a POC. The next steps are secure configuration, more tests, frontend integration, authentication/authorization, pagination, migrations, and the wider banking domain such as accounts and transactions.”

### Closing

“Overall, this project gave me a clean customer API foundation with a layered backend design. It is intentionally scoped so the core data flow and error handling are clear, and it can be extended safely in future iterations.”

---

## Part 19 — What I Should Share on Screen

### Screen 1 — Running API client, if available

Show Swagger UI or Postman. Do not call it a frontend/homepage.

Say: “Because this repository is API-only, I will demonstrate it through an API client.”

### Screen 2 — Create customer request and response

Show a valid `POST /api/customers` request and the `201` response.

Say: “This demonstrates the complete create flow, from request validation through persistence to the response.”

### Screen 3 — Error examples

Show a duplicate-email `409` or missing-field `400` response if you have a running database.

Say: “The API returns controlled, meaningful errors instead of raw internal exceptions.”

### Screen 4 — Project tree

Open `src/main/java/com/example/banking_poc`.

Say: “This structure separates HTTP handling, business rules, persistence, data models, and errors.”

### Screen 5 — `CustomerController.java`

Show the base mapping and endpoints.

Say: “This is the public API boundary. Each annotation connects a URL and HTTP method to a Java method.”

### Screen 6 — `CustomerService.java`

Show `createCustomer` and `updateCustomer`.

Say: “This is where I keep the important rules, including duplicate checks and timestamps.”

### Screen 7 — `Customer.java` and `CustomerRepository.java`

Say: “The entity defines data stored in MySQL, while the repository performs the database operations through JPA.”

### Screen 8 — `CustomerRequest.java` and `GlobalExceptionHandler.java`

Say: “The request DTO validates client data, and the global handler makes errors consistent.”

### Screen 9 — `pom.xml`

Say: “This file defines the major technologies: Spring web, JPA, validation, MySQL, OpenAPI, and testing.”

### Screen 10 — `application.properties`

**Important:** hide/redact credentials before sharing.

Say: “This config points the project to local MySQL and enables schema updates for the POC. In production I would move credentials out of this file.”

### Screen 11 — Tests and future plan

Show `BankingPocApplicationTests.java`.

Say: “This is the current startup smoke test. The next testing step is endpoint coverage for all workflows and errors.”

---

## Part 20 — Mentor Questions and Answers

### Basic questions

**Mentor:** What did you build?

**Me:** “A Spring Boot REST API that manages banking customer profiles through create, read, search, update, and delete operations.”

**Mentor:** Is this a complete banking system?

**Me:** “No. It is a focused proof of concept for customer management. Accounts, balances, transactions, and login are not implemented here.”

**Mentor:** Who uses it?

**Me:** “At present, an API tester can use it through Swagger or Postman. A future web or mobile frontend could call the same endpoints.”

### Technical questions

**Mentor:** Why did you use Spring Boot?

**Me:** “It provides web routing, application configuration, validation, and database integration so I can build a structured REST API without writing that infrastructure from scratch.”

**Mentor:** Why did you use layers?

**Me:** “They separate responsibilities. Controllers handle HTTP, services contain rules, repositories access data, and entities model storage. This makes the code easier to change and test.”

**Mentor:** What is JPA?

**Me:** “JPA is a Java standard for working with relational databases through objects. Here, it lets a `Customer` Java object represent a customer database row.”

### Code questions

**Mentor:** Why do you have request and response DTOs instead of using the entity everywhere?

**Me:** “They separate the public API contract from the persistence model. The request DTO also holds input validation rules, while the response DTO controls what is returned.”

**Mentor:** What does `@Valid` do?

**Me:** “It tells Spring to check the validation annotations on the request object before the service processes it. Invalid input becomes a 400 response.”

**Mentor:** How do you prevent duplicate emails?

**Me:** “The service uses repository lookups before saving, and the entity also marks the email column as unique.”

**Mentor:** Why compare IDs during update?

**Me:** “The existing customer should be allowed to keep their own email or customer number. I only treat it as a conflict when those values belong to a different ID.”

### Architecture questions

**Mentor:** Does the controller access MySQL directly?

**Me:** “No. The controller calls the service, and the service calls the repository. That keeps database access separate from HTTP handling.”

**Mentor:** How does Spring know about the controller and service?

**Me:** “`@SpringBootApplication` starts component scanning from the main package, and annotations such as `@RestController` and `@Service` identify those classes to Spring.”

### Database questions

**Mentor:** What is the primary key?

**Me:** “`id` is the primary key. It is database-generated and uniquely identifies a customer row.”

**Mentor:** Are there relationships or foreign keys?

**Me:** “No. The available model contains only Customer, with no linked account or transaction entities.”

**Mentor:** How is the schema created?

**Me:** “The configured Hibernate setting is `ddl-auto=update`, so Hibernate attempts to align the schema with the entity at startup. There are no migration scripts in this project.”

### API questions

**Mentor:** Why use POST, GET, PUT, and DELETE?

**Me:** “They follow common REST conventions: POST creates, GET reads, PUT updates, and DELETE removes.”

**Mentor:** What happens when a customer ID is missing?

**Me:** “The service throws `CustomerNotFoundException`, and the global handler returns 404 Not Found.”

**Mentor:** What happens on invalid email input?

**Me:** “The request DTO’s `@Email` validation fails, then the global handler returns 400 Bad Request.”

### Security questions

**Mentor:** How are passwords stored?

**Me:** “Passwords are not stored because authentication is not implemented in this POC.”

**Mentor:** How is access protected?

**Me:** “It is not protected by authentication or authorization in the available code. Spring Security and role-based access would be a production-priority next step.”

**Mentor:** Is the database password secure?

**Me:** “No, it is currently configured directly in the local properties file, which is a POC limitation. I would move it to environment variables or a secret manager.”

### Improvement questions

**Mentor:** What would you do next?

**Me:** “I would first secure configuration, add endpoint tests, and add database migrations. Then I would add authentication, pagination, a frontend, and banking domain features such as accounts and transactions.”

**Mentor:** Why not put all logic in the controller?

**Me:** “That would mix request handling and business rules. Keeping rules in the service makes the controller easier to read and supports reuse and testing.”

---

## Part 21 — 2-Minute Version

“My project is a Banking Customer Management proof of concept built as a Java Spring Boot REST API. Its job is to manage customer profiles, including create, list, search, retrieve by ID, update, and delete.

The project is backend-only, so it does not have a website interface yet. Swagger UI or Postman can call the API, and a future frontend can use the same endpoints. The architecture is layered: the controller receives HTTP requests, the service applies rules, the repository accesses the database, and MySQL stores the customer records.

I used Spring MVC for endpoints, Spring Data JPA and Hibernate for persistence, MySQL as the database, Bean Validation for required fields and email checks, and OpenAPI annotations to document the API.

I implemented duplicate checks for email and customer number, timestamps, case-insensitive name search, and centralized error handling. Invalid data returns 400, missing customers return 404, and duplicates return 409.

The current status is a working customer-management API foundation. It still needs production features such as authentication, secure secret configuration, broader automated tests, migrations, a frontend, and additional banking features.”

---

## Part 22 — 5-Minute Version

“My project is called Banking POC. It is a backend REST API for managing banking customer profiles. I intentionally scoped it to the customer domain so I could build a clean foundation before adding wider banking functionality.

The API allows a client to create a customer, list all customers, search by first or last name, retrieve one by ID, update data, and delete a customer. Each customer has a unique customer number and email, as well as personal information and timestamps.

The architecture is layered. An API client sends a request to the controller. The controller is the HTTP layer; it chooses the endpoint method and delegates to the service. The service contains business rules, including validation flow, duplicate checks, timestamp management, and not-found decisions. The repository is the persistence layer and communicates with MySQL using Spring Data JPA. The Customer entity maps Java fields to stored data, while DTOs separate API request and response formats from the database object.

For example, for creating a customer, the caller POSTs JSON to `/api/customers`. `@Valid` checks required fields and email format. The service checks customer number and email for duplicates. If valid, it creates a Customer object, sets creation and update timestamps, and saves it through the repository. The response is a `201 Created` with the saved data. If a duplicate exists, the global exception handler returns `409 Conflict`; if input is invalid, it returns `400 Bad Request`.

I used Java 21 and Spring Boot 4.1.1, Spring MVC for REST endpoints, JPA/Hibernate for object-relational persistence, MySQL as the database, Bean Validation for input checks, and Springdoc/OpenAPI for documentation. Maven handles the build and dependencies.

This project currently has a context-load smoke test. My next steps would be API-level automated tests, safer secret handling, database migrations, authentication and authorization, pagination, a frontend, and banking features such as accounts and transactions.”

---

## Part 23 — 10-Minute Director’s Script

### [OPEN API CLIENT OR SWAGGER UI]

“I will start with the application’s purpose. This is a backend-only Banking Customer Management API. There is no browser frontend in this repository, so this API client demonstrates how a future UI or another system interacts with it.”

### [SHOW CREATE CUSTOMER REQUEST]

“This request creates a customer. I provide the customer number, name, email, phone number, address, and date of birth. The API validates required fields and email format.”

### [SEND REQUEST / SHOW RESPONSE, IF RUNNING]

“A successful call returns 201 Created. The response includes the generated ID plus creation and update timestamps.”

### [SHOW ERROR RESPONSE, IF AVAILABLE]

“If I send invalid data, the API returns 400. If I reuse an email or customer number, it returns 409. If an ID does not exist, it returns 404. This makes the contract predictable for a client.”

### [OPEN PROJECT TREE]

“Now I’ll show the backend structure. It is organized into controller, service, repository, entity, DTO, configuration, and exception packages.”

### [OPEN `CustomerController.java`]

“This controller owns the `/api/customers` endpoints. The annotations map HTTP methods and paths to methods. The controller stays small and hands work to the service.”

### [OPEN `CustomerService.java`, `createCustomer`]

“This is the heart of the create flow. First it checks the customer number; then the email. If either already exists it throws a specific exception. Otherwise it copies fields into a new entity, sets timestamps, saves it, and converts it to a response DTO.”

### [OPEN `CustomerService.java`, `updateCustomer`]

“Update uses the same safety checks. The ID comparison is important: the current customer can retain their own email and number, but they cannot use data that belongs to another customer.”

### [OPEN `Customer.java`]

“This entity is the database representation. The ID is the primary key and is generated by the database. Customer number and email are unique. At this stage there are no account or transaction relationships.”

### [OPEN `CustomerRepository.java`]

“The repository extends JPA’s standard repository. This gives basic CRUD methods. I also declared lookup methods for customer number, email, and case-insensitive name search. Spring Data JPA creates the database queries from these names.”

### [OPEN `CustomerRequest.java`]

“This DTO is the incoming API contract. It has validation annotations so incomplete data or invalid email syntax does not move further into the system.”

### [OPEN `GlobalExceptionHandler.java`]

“This file centralizes error handling. It turns framework validation errors and my custom exceptions into standard JSON errors and correct HTTP status codes.”

### [OPEN `pom.xml`]

“These dependencies show the technology stack: Spring WebMVC, JPA, validation, the MySQL driver, OpenAPI/Swagger, and test support. Maven manages them.”

### [OPEN `application.properties`, REDACT PASSWORD]

“This config connects to a local MySQL database and lets Hibernate update the schema during development. One limitation is that the credentials are in the file, which I would replace with environment variables before deploying.”

### [OPEN TEST FILE]

“The project has a context-load smoke test. My testing plan is to add focused automated tests for each endpoint and error condition.”

### [CLOSE]

“To summarize: I built a clean, layered customer-management API with validation, duplicate protection, database integration, documentation, and error handling. It is a POC foundation, and the next phase is security, stronger testing, a frontend, and broader banking functionality.”

---

## Part 24 — One-Page Cheat Sheet

### Project

**One sentence:** “A Java Spring Boot REST API that manages banking customer profiles in MySQL.”

### Problem

“Customer data needs a single controlled API instead of manual database edits.”

### Solution

“I exposed CRUD and search endpoints with validation, duplicate checks, and consistent errors.”

### Tech stack

Java 21 · Spring Boot · Spring MVC · Spring Data JPA/Hibernate · MySQL · Bean Validation · OpenAPI/Swagger · Maven · JUnit.

### Architecture

```text
API client → Controller → Service → Repository/JPA → MySQL → JSON response
```

### Main features

Create · list · search · get by ID · update · delete · validation · duplicate protection · structured errors · API documentation.

### Important files

`CustomerController.java` · `CustomerService.java` · `Customer.java` · `CustomerRepository.java` · `CustomerRequest.java` · `GlobalExceptionHandler.java` · `pom.xml` · `application.properties`.

### Database

“MySQL stores one Customer entity/table with ID, unique customer number/email, customer details, and timestamps; no relationships yet.”

### API

“All endpoints are under `/api/customers`, using POST, GET, PUT, and DELETE.”

### Authentication

“Not implemented in this POC.”

### Biggest implementation

“The end-to-end customer flow: validated request → duplicate checks → MySQL persistence → response/error.”

### Current status

“A backend-only customer API foundation; it needs security, expanded tests, safe secrets, and broader banking features.”

### Future improvements

Environment-based secrets · endpoint tests · migrations · authentication · pagination · frontend · accounts/transactions.

### Ten questions to prepare for

1. Why Spring Boot?
2. Why JPA and MySQL?
3. What is the controller/service/repository split?
4. Why separate DTOs from entities?
5. How do you validate input?
6. How do you prevent duplicates?
7. What happens for an unknown ID?
8. What is currently tested?
9. Is there authentication?
10. What would you improve next?

---

## Appendix — Running the Project Locally

### Prerequisites confirmed or implied by the files

- Java 21, because `pom.xml` declares Java 21.
- A local MySQL server listening on port 3306.
- A database named `banking_poc`, because the JDBC URL points to it.
- A MySQL user/password matching the local application configuration. Do not share the password publicly.

### Commands supported by the repository

From the project directory on Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Run tests:

```powershell
.\mvnw.cmd test
```

### Common setup problems

| Symptom | Likely cause | What to check |
|---|---|---|
| Database connection fails | MySQL is stopped, database missing, or credentials do not match. | Local MySQL service and `application.properties`. |
| Java version build failure | Java 21 is not active. | `java -version`. |
| Port conflict | Another process uses Spring Boot’s default HTTP port. | The running process/port configuration; no custom server port is present in this project. |
| Swagger page unavailable | Application did not start or the dependency/version/runtime behavior differs. | Startup logs and Springdoc documentation; no custom URL is configured. |

### Important honest note

There is no README that gives a full database creation command, no `.env` template, and no Docker setup. Creating the database/schema and supplying valid MySQL credentials must be done locally before the application can connect.
