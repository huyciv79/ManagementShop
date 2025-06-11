package com.example.managementbook.repository;

import com.example.managementbook.model.entity.ShoppingCart;
import com.example.managementbook.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);

}
