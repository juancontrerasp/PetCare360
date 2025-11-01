package edu.dosw.petcare.PetCare360.controller;

import edu.dosw.petcare.PetCare360.controller.dto.*;
import edu.dosw.petcare.PetCare360.model.services.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart", description = "Shopping cart management endpoints")
public class ShoppingCartController {
    private final ShoppingCartService cartService;

    @PostMapping("/user/{userId}")
    @Operation(summary = "Add item to cart")
    public ResponseEntity<ShoppingCartResponseDTO> addToCart(
            @PathVariable Long userId,
            @Valid @RequestBody AddToCartRequestDTO request) {
        ShoppingCartResponseDTO response = cartService.addToCart(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user's cart")
    public ResponseEntity<ShoppingCartResponseDTO> getCart(@PathVariable Long userId) {
        ShoppingCartResponseDTO response = cartService.getCart(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/user/{userId}/product/{productId}")
    @Operation(summary = "Update cart item quantity")
    public ResponseEntity<ShoppingCartResponseDTO> updateCartItem(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemRequestDTO request) {
        ShoppingCartResponseDTO response = cartService.updateCartItem(userId, productId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user/{userId}/product/{productId}")
    @Operation(summary = "Remove item from cart")
    public ResponseEntity<ShoppingCartResponseDTO> removeFromCart(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        ShoppingCartResponseDTO response = cartService.removeFromCart(userId, productId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user/{userId}")
    @Operation(summary = "Clear cart")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}