package com.example.managementbook.model.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="quantity", nullable=false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name ="user_id",referencedColumnName ="id")
    private User user;
    @ManyToOne
    @JoinColumn(name ="product_id",referencedColumnName ="id")
    private Product product;
}
