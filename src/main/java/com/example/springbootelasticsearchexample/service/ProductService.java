package com.example.springbootelasticsearchexample.service;

import com.example.springbootelasticsearchexample.entity.Product;
import com.example.springbootelasticsearchexample.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // The service layer sits between the controller layer and the repository layer
public class ProductService {

    @Autowired // used to create an object called productRepo of class ProductRepo
    private ProductRepo  productRepo;

    public Iterable<Product> getProducts() {
        return productRepo.findAll(); // method from ElasticsearchRepository which ProductRepo extends from
    }

    public Product insertProduct(Product product) {
        return productRepo.save(product);
    }

    public Product updateProduct(Product product, int id) {
        Product product1  = productRepo.findById(id).get();
        product1.setPrice(product.getPrice());
        return product1;
    }

    public void deleteProduct(int id ) {
        productRepo.deleteById(id);
    }
}