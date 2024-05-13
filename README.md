# Project: Managing an Ecommerce Keystore with Spring Boot and Cassandra

## Introduction

The objective of this project is to create a Spring Boot application that manipulates a Cassandra database to manage a keystore named "ecommerce". This keystore contains a table "products" that stores product information. The project includes several elements: a `Product` entity, a `ProductRepository` repository, a service layer, a REST controller, and CRUD operation tests with Postman.

## Steps of Implementation

### 1. Creating the Product Entity

The `Product` entity represents the `products` table in the Cassandra database. It contains the following attributes:
- `id`: UUID
- `name`: String
- `price`: double
- `quantiteStock`: int

```java
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table
public class Product {
    
    @PrimaryKey
    private UUID id;
    private String name;
    private double price;
    private int quantiteStock;
    
    // Getters and Setters
}
```
### 2. Creating the ProductRepository

The ProductRepository allows performing database access operations. It extends the CassandraRepository interface provided by Spring Data Cassandra.
```java
import org.springframework.data.cassandra.repository.CassandraRepository;
import java.util.UUID;

public interface ProductRepository extends CassandraRepository<Product, UUID> {
    List<Product> findByNameContaining(String keyword);
}
```

### 3. Creating the Service Layer

The service layer implements CRUD operations and search by keyword. It uses the ProductRepository to interact with the database.
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(UUID id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        product.setId(UUID.randomUUID());
        return productRepository.save(product);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    public List<Product> searchProductsByName(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }
}

```

### 4. Creating the REST Controller

The REST controller exposes the different functionalities of the application via HTTP endpoints.
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword) {
        return productService.searchProductsByName(keyword);
    }
}

```

### 5. Testing the Operations with Postman

To test the various CRUD operations, we used Postman. Here are some example requests:

    GET /api/products: Retrieves all products
    GET /api/products/{id}: Retrieves a product by its ID
    POST /api/products: Creates a new product
    DELETE /api/products/{id}: Deletes a product by its ID
    GET /api/products/search?keyword=example: Searches for products by keyword



