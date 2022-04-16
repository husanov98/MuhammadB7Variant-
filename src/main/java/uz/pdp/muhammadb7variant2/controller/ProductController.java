package uz.pdp.muhammadb7variant2.controller;

//import static uz.mh.util.MyUtil.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.muhammadb7variant2.entity.Product;
import uz.pdp.muhammadb7variant2.service.ProductService;

import java.util.UUID;
//import uz.pdp.muhammadb7variant2.entitiy.Product;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public HttpEntity<?> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping
    public HttpEntity<?> addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @PutMapping
    public HttpEntity<?> updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }

    @DeleteMapping("/{productId}")
    public HttpEntity<?> deleteProduct(@PathVariable UUID productId){
        return productService.deleteProduct(productId);
    }
}
