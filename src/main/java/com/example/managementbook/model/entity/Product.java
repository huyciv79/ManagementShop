package com.example.managementbook.model.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(name = "product_name",length = 100,unique = true,nullable = false)
    private String name;
    @Column(name = "product_price",nullable = false)
    private double price;
    @Column(name="product_stock",nullable = false)
    private int stock;
    @Column(name="product_status",nullable = false)
    private Boolean status;


    @ManyToOne
    @JoinColumn(name ="category_id",referencedColumnName ="id")
    private Category category;
}
