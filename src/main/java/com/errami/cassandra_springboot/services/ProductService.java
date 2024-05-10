package com.errami.cassandra_springboot.services;

import com.errami.cassandra_springboot.entities.Product;
import com.errami.cassandra_springboot.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(UUID uuid){
        return productRepository.findById(uuid).get();
    }

    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    public List<Product> findByNameContaining(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }
}
