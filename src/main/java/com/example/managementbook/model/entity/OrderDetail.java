package com.example.managementbook.model.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="quantity", nullable=false)
    private int quantity;
    @Column(name="unit_price", nullable=false)
    private double unitPrice;
    @ManyToOne
    @JoinColumn(name="product_id",referencedColumnName="id")
    private Product product;
    @ManyToOne
    @JoinColumn(name="order_id",referencedColumnName="id")
    private Order order;
}
