# 🛍️ Product and Category Management REST API

RESTful API developed with Spring Boot 3.5.3 for a comprehensive product and category management system with PostgreSQL, featuring advanced error handling and validation capabilities.

![Java Version](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Hibernate](https://img.shields.io/badge/Hibernate-6.4-lightblue)
![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0-success)

## 🌟 Key Features

- **Complete CRUD operations** for products and categories
- **Advanced exception handling** with standardized responses
- **Automatic validation** of required fields and formats
- **Advanced search** with multiple criteria
- **Product state management**
- **DTO pattern** for secure data transfer
- **Integrated Swagger documentation** (OpenAPI 3.0)
- **Sequence-based ID generation** for optimal performance
- **PostgreSQL optimized** schema design
- **Lombok** for cleaner code

## 🛠️ Complete Technology Stack

| Component      | Technology                           |
| -------------- | ------------------------------------ |
| Language       | Java 21                              |
| Framework      | Spring Boot 3.5.0                    |
| Database       | PostgreSQL 16                        |
| ORM            | Hibernate 6.4                        |
| Persistence    | Spring Data JPA                      |
| Mapping        | ModelMapper 3.2.3                    |
| Documentation  | SpringDoc OpenAPI 2.8.9              |
| Validations    | Jakarta Validation + Handler         |
| Error Handling | Custom Exceptions + ControllerAdvice |

### Core Dependencies

| Dependency | Version |
|------------|---------|
| Spring Boot | 3.5.3 |
| Java | 21 |
| PostgreSQL Driver | 42.x |
| Spring Data JPA | 3.5.3 |
| Hibernate | 6.4 |
| Lombok | 1.18.30 |
| ModelMapper | 3.2.3 |
| SpringDoc OpenAPI | 2.8.9 |




## 🚀 Initial Setup

```bash
# 1. Clone repository
git clone https://github.com/szMauro/products_rest_api.git

# 2. Create PostgreSQL database (ensure PostgreSQL is running)
psql -U postgres -c "CREATE DATABASE bd_api_products;"

# 3. Configure credentials
# Edit src/main/resources/application.properties
# Default configuration:
# spring.datasource.username=postgres
# spring.datasource.password=root

# 4. Run application
mvn spring-boot:run
```

## 🗄️ Database Schema

### Category Entity
```java
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "category_seq", sequenceName = "category_seq", allocationSize = 1)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @Column(name = "categoryId")
    private Long id;

    @Column(name = "categoryName", nullable = false, length = 50)
    @NotNull
    private String name;
}

```

### Product Entity
```java
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @Column(name = "productId")
    private Long id;

    @Column(name = "productName", nullable = false)
    @NotNull
    private String name;
    @Column(name = "productDescription")
    @NotNull
    private String description;
    @Column(name = "productPrice", nullable = false)
    @NotNull
    private Double price;
    @Column(name = "productStock", nullable = false)
    @NotNull
    private int stock;
    @Enumerated(EnumType.STRING)
    @Column(name = "productState", nullable = false)
    @NotNull
    private ProductState state;

    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private Category category;
}
```

## 🔌 Core Endpoints

### 📦 Products

| Method | Endpoint                           | Description               | Validations                |
| ------ | ---------------------------------- | ------------------------- | -------------------------- |
| POST   | `/api/products/add/{categoryId}`   | Create new product        | Price > 0, required fields |
| GET    | `/api/products`                    | List all products         |                            |
| GET    | `/api/products/search/name/{name}` | Search product by name    |                            |
| GET    | `/api/products/search/id/{id}`     | Search product by ID      |                            |
| PUT    | `/api/products/update/{id}`        | Update complete product   | Validate existing category |
| DELETE | `/api/products/delete/{id}`        | Delete product            |                            |
| PUT    | `/api/products/state/{id}`         | Change product state      | Validate valid state       |
| GET    | `/api/products/state/{state}`      | List products by state    |                            |
| GET    | `/api/products/category/{catId}`   | List products by category |                            |

### 🗂️ Categories

| Method | Endpoint                      | Description         | Validations            |
| ------ | ----------------------------- | ------------------- | ---------------------- |
| POST   | `/api/categories`             | Create new category | Unique name, not empty |
| GET    | `/api/categories`             | List all categories |                        |
| GET    | `/api/categories/{id}`        | Get category by ID  |                        |
| PUT    | `/api/categories/update/{id}` | Update category     | Unique name            |
| DELETE | `/api/categories/delete/{id}` | Delete category     |                        |

## 📚 API Documentation

Explore interactive documentation:

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## 📋 DTO Structure & Validations

### CategoryDto

```java
{
  "categoryId": Long,               // Auto-generated ID
  "categoryName": String            // Required, max 100 chars
}
```

### Validations

- @NotBlank: Name cannot be empty
- @Size(max=100): Name cannot exceed 100 characters

### ProductDto

```java
{
  "productId": Long,                // Auto-generated ID
  "productName": String,            // Required, max 100 chars
  "description": String,            // Required, max 255 chars
  "price": Double,                  // Required, minimum 1
  "stock": Integer,                 // Required, minimum 0
  "productState": Enum,             // Required (ACTIVE/INACTIVE/etc.)
  "category": {                     // Category object
    "categoryId": Long,
    "categoryName": String
  }
}
```

### Validation Rules

| Field        | Validations               | Error Message                        |
| ------------ | ------------------------- | ------------------------------------ |
| productName  | @NotBlank, @Size(max=100) | "Product name can't be empty"        |
| description  | @NotBlank, @Size(max=255) | "Description can't exceed 255 chars" |
| price        | @NotNull, @Min(1)         | "Price can't be lower than 0"        |
| stock        | @NotNull, @Min(0)         | "Stock can't be negative"            |
| productState | @NotNull                  | "Product state can't be null"        |
| categoryName | @NotBlank, @Size(max=100) | "Category name can't be empty"       |

### Common error statuses:

-  400 Bad Request: Invalid input data

-  404 Not Found: Resource not found

-  500 Internal Server Error: Unexpected server error

## 🔍 Search Capabilities

* By Name: /api/products/search/name/{name}

    - Performs case-sensitive exact match
    - Returns single product or 404

* By Category: /api/products/category/{catId}

    - Returns all products in specified category
    - Empty array if no products found

*  By State: /api/products/state/{state}

    - Valid states: ACTIVE, INACTIVE, DISCONTINUED
    - Returns all products with matching state

## 🛡️ Error Handling System

The API implements a structured exception handling mechanism:

### Exception Types

- `ResourceNotFoundException`: Resource not found (404)
- `BadRequestException`: Invalid request (400)
- `MethodArgumentNotValidException`: Failed validations (400)

## 📌 Additional Business Rules

- Name Uniqueness:

  - Duplicate category names are rejected

  - Service-layer validation with existsByCategoryName

- Price Consistency:

  - Must be positive (greater than 0)

  - Additional service-layer validation

- State Validation:

  - Only accepts values from ProductState enum

## 🛠️ Validation Configuration

The API uses:

- Jakarta Validation for basic validations

- Centralized handling in GlobalExceptionHandler

- Custom messages in annotations

- Service-layer validation for complex rules

## 📚 Technical Documentation

Package Structure

```text
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── api_products/
│   │   │           ├── config/                  # Configuration classes
│   │   │           ├── controllers/             # REST controllers
│   │   │           ├── dtos/                    # Data Transfer Objects
│   │   │           ├── exceptions/              # Custom exceptions
│   │   │           ├── mapper/                  # Mapping utilities (ModelMapper)
│   │   │           ├── models/                  # Entity classes
│   │   │           ├── repositories/            # JPA repositories
│   │   │           ├── services/                # Business logic
│   │   │           │   └── impl/                # Service implementations
│   │   │           └── ApiProductsApplication.java # Main application class
```
