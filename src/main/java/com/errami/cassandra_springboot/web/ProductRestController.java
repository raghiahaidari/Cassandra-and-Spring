package com.errami.cassandra_springboot.web;

import com.errami.cassandra_springboot.entities.Product;
import com.errami.cassandra_springboot.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductRestController {
    private ProductService productService;

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable UUID id){
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable UUID id) {
        productService.deleteById(id);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam("name") String name) {
        return productService.findByNameContaining(name);
    }
}
