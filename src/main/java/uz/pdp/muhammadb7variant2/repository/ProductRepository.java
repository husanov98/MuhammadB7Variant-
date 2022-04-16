package uz.pdp.muhammadb7variant2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.muhammadb7variant2.entity.Product;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
