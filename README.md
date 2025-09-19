# PriceService-BE
This project implements a REST service in **Spring Boot** that allows you to check the final price to be applied to a product on a specific date and in a specific chain.

## Requirement
The service must accept the following input parameters:

- **Application date** (`date`)
- **Product identifier** (`productId`)
- **String identifier** (`brandId`)

And return the following output:

- **Product identifier** (`productId`)
- **String identifier** (`brandId`)
- **Rate to apply** (`priceList`)
- **Application dates** (`startDate`, `endDate`)
- **Final price** (`price`)

---

## Overview

- **Language / Platform**: Java 21, Spring Boot 3.5.5
- **Style**: Reactive controllers (`Mono<>`) with blocking work offloaded to `boundedElastic`  
- **Architecture**: Hexagonal (Ports & Adapters)  
- **Persistence**: H2 (dev), JPA adapter (blocking)  
- **Build/Run**: Maven

## 📂 Project Structure:
<pre>
src/main/java/com/price/be
├── application.port.in
├── application.port.out
├── application.port.service.impl
├── domain.exception
├── domain.model
├── infrastructure.persistence.adapter
├── infrastructure.persistence.convert.mapper
├── infrastructure.persistence.dao
├── infrastructure.persistence.entity
├── infrastructure.serialization
├── infrastructure.web.advice
├── infrastructure.web.controller
├── infrastructure.web.convert.mapper
├── infrastructure.web.request.dto
└── infrastructure.web.response.dto
</pre>



## Design Decisions

### 🏗️ Hexagonal Architecture (Ports & Adapters)

- **Domain**
  - `domain.model` → core entities (e.g., `PriceModel`)
  - `domain.exception` → exceptions (e.g., `NotFoundException`)

- **Application (Ports)**
  - `port.in` → inbound ports (use-cases consumed by web/adapters)
  - `port.out` → outbound ports (e.g., `PriceRepositoryPort`)
  - `service.impl` → implements business logic; wraps blocking ops in `boundedElastic`

- **Infrastructure (Adapters)**
  - **Web adapter**
    - `web.controller` → REST controllers (e.g., `PriceController`)
    - `web.request.dto` / `web.response.dto` → request/response DTOs
    - `web.convert.mapper` → MapStruct DTO mappers
    - `web.advice` → exception handling (`DetailErrorDto`)
- **Persistence adapter**
    - `persistence.adapter` → implements `PriceRepositoryPort`
    - `persistence.dao` → Spring Data repositories
    - `persistence.entity` → DB entities
    - `persistence.convert.mapper` → MapStruct entity mappers

**Why hexagonal?**
- Clear boundaries, easy to test
- Replaceable adapters (JPA → R2DBC → S3, etc.)
- Web controllers stay thin and focus on mapping

---

## API Endpoints

Base path: `http://localhost:8080/back`

### `GET /prices`

#### Request Parameters
- **Product** (`productId`) — *Number*
- **Brand** (`brandId`) — *Number*
- **Application Date** (`date`) — *String* in the format **`yyyy-MM-dd HH:mm:ss`**

#### Responses
- **200 OK**  
  The request was successful. Returns the **applicable price rate**.

- **400 Bad Request**  
  One or more required parameters (`productId`, `brandId`, `date`) were missing or incorrectly formatted.

- **404 Not Found**  
  No applicable price was found for the given parameters.

#### Business Rules
- If **multiple rates** are valid for the same date, the one with the **highest priority** is automatically selected.  
- If **no rate** matches the given parameters, the service responds with **HTTP 404 – Not Found**.  
- If **any required field** is missing, the service responds with **HTTP 400 – Bad Request**.


**Response (200)**:
```json
  {
    "idPrice": 1,
    "productId": 35455,
    "priceList": 1,
    "startDate": "2020-06-14 00:00:00",
    "endDate": "2020-12-31 23:59:59",
    "price": 35.5,
    "brandId": 1
  }
```
**Response (404)**:
```json
  {
    "localDateTime": "2025-09-17 23:04:58",
    "message": "No price was found associated with the entered parameters. Please try again with different values.",
    "code": 404
  }

```

**Response (400)**:
```json
{
    "localDateTime": "2025-09-19 08:18:08",
    "message": "Required parameter 'brandId' is not present.",
    "code": 400
}
```
---

### 📚 Tools & Consoles
**Execute in Localhost**
- H2 Database Console → http://localhost:8080/back/h2-console
  - Datasource Url: jdbc:h2:mem:storeappdb
  - User: test
  - Password: test

# Testing
- Unit tests → run with ./mvnw test
- Reactive flows → verified using reactor-test and StepVerifier
- 
- **Unit tests**  
  Controllers are tested in isolation using **WebTestClient** with **Mockito** to mock the `PriceService`.

- **Integration tests**  
  The full application is tested with **@SpringBootTest** and **WebTestClient**, using an in-memory H2 database (preloaded with sample data).  
  These tests validate the business rules by checking the expected price for the 5 requested scenarios.


## 📦 Postman collections:

**Execute in Localhost**
- 1.- *Get price:* http://localhost:8080/back/prices

















