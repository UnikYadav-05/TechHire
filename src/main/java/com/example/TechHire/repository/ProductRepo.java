package com.example.TechHire.repository;

import com.example.TechHire.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository<Product, Integer> {

}
