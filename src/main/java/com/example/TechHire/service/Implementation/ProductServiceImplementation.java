package com.example.TechHire.service.Implementation;

import com.example.TechHire.entity.Product;
import com.example.TechHire.repository.ProductRepo;
import com.example.TechHire.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService {

    private final ProductRepo productRepo;

    public ProductServiceImplementation(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product deleteProduct(int id) {
        Product product = productRepo.findById(id).get();
        productRepo.delete(product);
        return product;
    }

    @Override
    public Product updateProduct(int id, Product product) {
        Product productVar = productRepo.findById(id).get();

        productVar.setName(product.getName());
        productVar.setPrice(product.getPrice());
        productVar.setQuantity(product.getQuantity());

        return productRepo.save(productVar);
    }
}
