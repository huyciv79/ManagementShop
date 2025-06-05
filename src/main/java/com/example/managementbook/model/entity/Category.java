package com.example.managementbook.model.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="category_name",length=100,unique=true,nullable=false)
    private String name;
    @Column(name="category_status")
    private Boolean status;

}
