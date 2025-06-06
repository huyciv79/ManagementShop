package com.example.managementbook.controller;
import com.example.managementbook.model.dto.ShoppingCartDTO;
import com.example.managementbook.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/shopping-cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ShoppingCartDTO>> getAllCartsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(shoppingCartService.getAllCartsByUser(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<ShoppingCartDTO> addProductToCart(@RequestBody ShoppingCartDTO dto) {
        return ResponseEntity.ok(shoppingCartService.addProductToCart(dto));
    }

    @PutMapping("/update/{cartId}")
    public ResponseEntity<ShoppingCartDTO> updateQuantity(@PathVariable Long cartId, @RequestParam int quantity) {
        return ResponseEntity.ok(shoppingCartService.updateQuantity(cartId, quantity));
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<Void> deleteProductFromCart(@PathVariable Long cartId) {
        shoppingCartService.deleteProductFromCart(cartId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAll/{userId}")
    public ResponseEntity<Void> deleteAllProductsFromCart(@PathVariable Long userId) {
        shoppingCartService.deleteAllProductsFromCart(userId);
        return ResponseEntity.ok().build();
    }
}