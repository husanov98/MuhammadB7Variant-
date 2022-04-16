package uz.pdp.muhammadb7variant2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
//import uz.pdp.muhammadb7variant2.entitiy.Product;
import uz.pdp.muhammadb7variant2.entity.Product;
import uz.pdp.muhammadb7variant2.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

//import static uz.mh.util.MyUtil.*;
@Service
public class ProductService{
    @Autowired
    ProductRepository productRepository;


    public HttpEntity<?> getAllProducts() {
        var productList = productRepository.findAll();
        return ResponseEntity.ok(productList);
    }

    public HttpEntity<?> addProduct(Product product) {
        Product product1 = new Product();
        product1.setPrice(product.getPrice());
        product1.setName(product.getName());
        productRepository.save(product1);
        return ResponseEntity.ok("successfully added");
    }

    public HttpEntity<?> updateProduct(Product product) {
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        Product product1 = optionalProduct.get();
        product1.setName(product.getName());
        product1.setPrice(product.getPrice());
        productRepository.save(product1);
        return ResponseEntity.ok("successfully updated");
    }

    public HttpEntity<?> deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
        return ResponseEntity.ok("successfully deleted");
    }

}
