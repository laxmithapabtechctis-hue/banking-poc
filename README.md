# Customer Management Rest API 

A Spring Boot-based Banking Proof of Concept (POC) for managing customer information through REST APIs.

The application provides APIs to create, retrieve, search, update, and delete customer records. Customer data is persisted in a MySQL database using Spring Data JPA.

---

## 📌 Project Overview

This project demonstrates a basic backend application built using **Spring Boot**, **Spring Data JPA**, and **MySQL**.

The application follows a layered architecture:

```text
Client / Swagger
       ↓
Controller
       ↓
Service
       ↓
Repository
       ↓
Entity
       ↓
MySQL Database
```

The main functionality currently implemented is **Customer Management**.

---

## 🛠️ Technologies Used

* Java 21
* Spring Boot
* Spring Web MVC
* Spring Data JPA
* Hibernate / JPA
* MySQL
* Maven
* Spring Boot Validation
* Swagger / OpenAPI

---

## 📂 Project Structure

```text
src
└── main
    └── java
        └── com.example.banking_poc
            │
            ├── controller
            │   └── CustomerController.java
            │
            ├── service
            │   └── CustomerService.java
            │
            ├── repository
            │   └── CustomerRepository.java
            │
            ├── entity
            │   └── Customer.java
            │
            ├── dto
            │   ├── CustomerRequest.java
            │   ├── CustomerResponse.java
            │   └── ErrorResponse.java
            │
            └── exception
                ├── CustomerNotFoundException.java
                ├── DuplicateCustomerException.java
                └── GlobalExceptionHandler.java

pom.xml
```

---

## 📦 Package Responsibilities

### Controller

The Controller is the entry point for HTTP requests.

`CustomerController.java` provides REST endpoints for customer management.

### Service

The Service layer contains the application's business logic.

`CustomerService.java` handles:

* Creating customers
* Checking duplicate customer numbers
* Checking duplicate emails
* Retrieving customers
* Searching customers
* Updating customers
* Deleting customers
* Converting entities into response DTOs

### Repository

The Repository layer communicates with the database.

`CustomerRepository.java` extends:

```java
JpaRepository<Customer, Long>
```

This provides standard database operations such as:

* `findAll()`
* `findById()`
* `save()`
* `delete()`

It also contains custom queries for:

* Finding customers by customer number
* Finding customers by email
* Searching by first name or last name

### Entity

`Customer.java` represents the customer database entity.

It contains fields such as:

* ID
* Customer Number
* First Name
* Last Name
* Email
* Phone Number
* Address
* Date of Birth
* Created At
* Updated At

### DTO

DTO stands for **Data Transfer Object**.

The project uses separate DTOs for input, output, and errors.

#### CustomerRequest

Used when receiving customer information from the client.

#### CustomerResponse

Used when returning customer information to the client.

#### ErrorResponse

Provides a standard structure for API errors.

### Exception

This package contains custom exception handling.

#### CustomerNotFoundException

Used when the requested customer does not exist.

#### DuplicateCustomerException

Used when a customer number or email already exists.

#### GlobalExceptionHandler

Handles exceptions centrally and converts them into appropriate HTTP responses.

---

# 🚀 Getting Started

## Prerequisites

Make sure the following are installed:

* Java 21
* Maven
* MySQL
* MySQL Workbench
* IntelliJ IDEA or another Java IDE

---

## 🗄️ Database Setup

Start MySQL and create a database for the application.

For example:

```sql
CREATE DATABASE banking_poc;
```

Select the database:

```sql
USE banking_poc;
```

The application uses JPA/Hibernate to work with the database.

The exact database configuration should match the values in your application's configuration file.

For example, your configuration may contain:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/banking_poc
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

**Do not commit your actual database password to GitHub.**

---

# ▶️ Running the Application

## Using IntelliJ IDEA

1. Open the project.
2. Allow Maven dependencies to download.
3. Make sure MySQL is running.
4. Check your database configuration.
5. Run the main Spring Boot application.
6. Wait for the application to start successfully.

The application normally runs on:

```text
http://localhost:8080
```

---

# 📖 Swagger UI

Swagger can be used to view and test the REST APIs.

Open:

```text
http://localhost:8080/swagger-ui/index.html
```

Alternative:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI documentation:

```text
http://localhost:8080/v3/api-docs
```

---

# 🔗 Customer API Endpoints

Base URL:

```text
/api/customers
```

| Method | Endpoint                          | Purpose            |
| ------ | --------------------------------- | ------------------ |
| POST   | `/api/customers`                  | Create a customer  |
| GET    | `/api/customers`                  | Get all customers  |
| GET    | `/api/customers/search?name=John` | Search customers   |
| GET    | `/api/customers/{id}`             | Get customer by ID |
| PUT    | `/api/customers/{id}`             | Update customer    |
| DELETE | `/api/customers/{id}`             | Delete customer    |

---

# ➕ Create Customer

### Request

```http
POST /api/customers
```

Example request body:

```json
{
  "customerNumber": "CUST001",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@gmail.com",
  "phoneNumber": "9876543210",
  "address": "Mumbai",
  "dateOfBirth": "1995-05-15"
}
```

### Expected Result

A successful request returns a customer response containing the generated ID and timestamps.

Expected status:

```text
201 Created
```

---

# 📋 Get All Customers

### Request

```http
GET /api/customers
```

Returns the list of customers stored in the database.

Expected status:

```text
200 OK
```

---

# 🔍 Search Customers

### Request

```http
GET /api/customers/search?name=John
```

The search checks the customer's:

* First name
* Last name

The search is case-insensitive and supports partial matching.

For example:

```text
John
john
JOHN
Joh
```

can match appropriate customer names.

---

# 👤 Get Customer By ID

### Request

```http
GET /api/customers/{id}
```

Example:

```http
GET /api/customers/1
```

If the customer exists, the customer information is returned.

If the customer does not exist, the application returns:

```text
404 Not Found
```

---

# ✏️ Update Customer

### Request

```http
PUT /api/customers/{id}
```

Example:

```http
PUT /api/customers/1
```

Example request body:

```json
{
  "customerNumber": "CUST001",
  "firstName": "John",
  "lastName": "Smith",
  "email": "john.smith@gmail.com",
  "phoneNumber": "9876543210",
  "address": "Pune",
  "dateOfBirth": "1995-05-15"
}
```

The service checks that the updated customer number and email do not conflict with another customer.

Expected status:

```text
200 OK
```

---

# 🗑️ Delete Customer

### Request

```http
DELETE /api/customers/{id}
```

Example:

```http
DELETE /api/customers/1
```

If successful, the API returns:

```text
204 No Content
```

---

# ✅ Validation

Customer requests use Spring Boot validation.

Examples of validation include:

```java
@NotBlank
@Email
@NotNull
```

This prevents invalid input from being processed.

For example, an invalid email:

```json
{
  "customerNumber": "CUST002",
  "firstName": "John",
  "lastName": "Doe",
  "email": "invalid-email",
  "phoneNumber": "9876543210",
  "address": "Mumbai",
  "dateOfBirth": "1995-05-15"
}
```

can result in:

```text
400 Bad Request
```

---

# ⚠️ Exception Handling

The project uses centralized exception handling through:

```java
@RestControllerAdvice
```

The following cases are handled:

### Customer Not Found

```text
CustomerNotFoundException
```

Returns:

```text
404 Not Found
```

### Duplicate Customer

```text
DuplicateCustomerException
```

Returns:

```text
409 Conflict
```

### Validation Error

```text
MethodArgumentNotValidException
```

Returns:

```text
400 Bad Request
```

---

# 🧪 Testing the Database

After creating a customer through Swagger, MySQL Workbench can be used to confirm that the data was actually stored.

Select the project database:

```sql
USE banking_poc;
```

Check available tables:

```sql
SHOW TABLES;
```

Check the customer table structure:

```sql
DESCRIBE customer;
```

Then retrieve the records:

```sql
SELECT * FROM customer;
```

The actual table name should be confirmed using:

```sql
SHOW TABLES;
```

because the JPA/Hibernate naming strategy determines the database table/column names.

---

# 🔄 Application Flow

The basic request flow is:

```text
Client / Swagger
      ↓
CustomerController
      ↓
CustomerService
      ↓
CustomerRepository
      ↓
Customer Entity
      ↓
MySQL Database
```

For a successful response:

```text
MySQL Database
      ↓
Customer Entity
      ↓
CustomerService
      ↓
CustomerResponse
      ↓
CustomerController
      ↓
Client / Swagger
```

---

# 🔐 Duplicate Customer Flow

When creating or updating a customer:

```text
Request
   ↓
CustomerController
   ↓
CustomerService
   ↓
Check Customer Number
   ↓
Check Email
   ↓
Duplicate?
 ┌───────┴───────┐
Yes              No
 ↓                ↓
Exception         Save
 ↓                ↓
Global Handler    Database
 ↓
409 Conflict
```

---

# ❌ Error Handling Flow

```text
Request
   ↓
Controller
   ↓
Service
   ↓
Exception
   ↓
GlobalExceptionHandler
   ↓
ErrorResponse
   ↓
HTTP Error Response
```

---

# 🧑‍💻 Demo Flow

For demonstrating the project, the recommended order is:

```text
1. Start Spring Boot
        ↓
2. Open Swagger
        ↓
3. POST - Create Customer
        ↓
4. Show successful response
        ↓
5. Open MySQL Workbench
        ↓
6. SELECT * FROM customer
        ↓
7. Show stored customer
        ↓
8. GET - Get All Customers
        ↓
9. GET - Get Customer By ID
        ↓
10. GET - Search Customer
        ↓
11. PUT - Update Customer
        ↓
12. Verify updated data
        ↓
13. DELETE - Delete Customer
        ↓
14. GET by ID → 404
        ↓
15. Demonstrate duplicate → 409
        ↓
16. Demonstrate invalid input → 400
```

---

# 📊 HTTP Status Codes

| Status | Meaning     | Example                          |
| ------ | ----------- | -------------------------------- |
| 200    | OK          | Successful GET/UPDATE            |
| 201    | Created     | Customer successfully created    |
| 204    | No Content  | Customer successfully deleted    |
| 400    | Bad Request | Invalid request/validation error |
| 404    | Not Found   | Customer does not exist          |
| 409    | Conflict    | Duplicate customer number/email  |

---

# 📄 Main Files

| File                              | Purpose                                      |
| --------------------------------- | -------------------------------------------- |
| `CustomerController.java`         | Handles REST API requests                    |
| `CustomerService.java`            | Contains business logic                      |
| `CustomerRepository.java`         | Provides database access                     |
| `Customer.java`                   | JPA entity representing customer data        |
| `CustomerRequest.java`            | Input DTO                                    |
| `CustomerResponse.java`           | Successful response DTO                      |
| `ErrorResponse.java`              | Standard error response                      |
| `CustomerNotFoundException.java`  | Handles missing customers                    |
| `DuplicateCustomerException.java` | Handles duplicate customers                  |
| `GlobalExceptionHandler.java`     | Centralized exception handling               |
| `pom.xml`                         | Maven dependencies and project configuration |

---

# 🎯 Project Objective

The objective of this POC is to demonstrate a clean backend structure for customer management using Spring Boot.

The application separates responsibilities into different layers:

```text
Controller → API handling
Service → Business logic
Repository → Database access
Entity → Database representation
DTO → Data transfer
Exception → Error handling
```

This separation makes the application easier to understand, maintain, test, and extend.

---

# 🚀 Possible Future Enhancements

The current POC focuses on customer management.

Possible future enhancements could include:

* Customer authentication and authorization
* Account management
* Bank account creation
* Deposits and withdrawals
* Fund transfers
* Transaction history
* Improved validation responses
* Unit and integration testing
* Production-ready configuration
* API security

These are **future enhancements and are not part of the current implementation**.

---

## 👩‍💻 Author

**Banking POC**

Built as a Spring Boot backend Proof of Concept.
