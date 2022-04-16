package uz.pdp.muhammadb7variant2.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.UUID;

//import static uz.mh.util.MyUtil.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private double price;

//    @CreatedBy
//    private UUID createdBy;
//
//    @LastModifiedBy
//    private UUID updatedBy;
//
//    @CreationTimestamp
//    private Timestamp createdAt;
//
//    @UpdateTimestamp
//    private Timestamp updatedAt;
}
