package edu.dosw.petcare.PetCare360.model.services;

import edu.dosw.petcare.PetCare360.controller.dto.*;
import edu.dosw.petcare.PetCare360.model.entities.*;
import edu.dosw.petcare.PetCare360.model.persistence.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository cartRepository;
    private final ProductRepository productRepository;

    public ShoppingCartResponseDTO addToCart(Long userId, AddToCartRequestDTO request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }

        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElse(ShoppingCart.builder()
                        .userId(userId)
                        .items(new ArrayList<>())
                        .createdAt(LocalDateTime.now())
                        .build());

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            existingItem.calculateSubtotal();
        } else {
            CartItem newItem = CartItem.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .unitPrice(product.getPrice())
                    .quantity(request.getQuantity())
                    .build();
            newItem.calculateSubtotal();
            cart.getItems().add(newItem);
        }

        cart.setUpdatedAt(LocalDateTime.now());
        cart.calculateTotal();
        cart = cartRepository.save(cart);

        return mapToResponseDTO(cart);
    }

    public ShoppingCartResponseDTO getCart(Long userId) {
        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
        return mapToResponseDTO(cart);
    }

    public ShoppingCartResponseDTO updateCartItem(Long userId, Long productId,
                                                  UpdateCartItemRequestDTO request) {
        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        CartItem item = cart.getItems().stream()
                .filter(ci -> ci.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        item.setQuantity(request.getQuantity());
        item.calculateSubtotal();

        cart.setUpdatedAt(LocalDateTime.now());
        cart.calculateTotal();
        cart = cartRepository.save(cart);

        return mapToResponseDTO(cart);
    }

    public ShoppingCartResponseDTO removeFromCart(Long userId, Long productId) {
        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        cart.setUpdatedAt(LocalDateTime.now());
        cart.calculateTotal();
        cart = cartRepository.save(cart);

        return mapToResponseDTO(cart);
    }

    public void clearCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }

    private ShoppingCartResponseDTO mapToResponseDTO(ShoppingCart cart) {
        List<CartItemResponseDTO> items = cart.getItems().stream()
                .map(item -> CartItemResponseDTO.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .unitPrice(item.getUnitPrice())
                        .quantity(item.getQuantity())
                        .subtotal(item.getSubtotal())
                        .build())
                .collect(Collectors.toList());

        return ShoppingCartResponseDTO.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .items(items)
                .totalAmount(cart.getTotalAmount())
                .totalItems(cart.getItems().size())
                .build();
    }
}