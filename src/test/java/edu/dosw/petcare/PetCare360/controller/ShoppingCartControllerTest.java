package edu.dosw.petcare.PetCare360.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dosw.petcare.PetCare360.controller.dto.*;
import edu.dosw.petcare.PetCare360.model.services.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShoppingCartController.class)
class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ShoppingCartService cartService;

    @Test
    void addToCart_Success() throws Exception {
        AddToCartRequestDTO request = AddToCartRequestDTO.builder()
                .productId(1L)
                .quantity(2)
                .build();

        ShoppingCartResponseDTO response = ShoppingCartResponseDTO.builder()
                .id(1L)
                .userId(1L)
                .items(Arrays.asList())
                .totalAmount(new BigDecimal("100000"))
                .totalItems(1)
                .build();

        when(cartService.addToCart(eq(1L), any(AddToCartRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/cart/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.totalItems").value(1));
    }

    @Test
    void getCart_Success() throws Exception {
        ShoppingCartResponseDTO response = ShoppingCartResponseDTO.builder()
                .id(1L)
                .userId(1L)
                .totalAmount(new BigDecimal("50000"))
                .totalItems(2)
                .build();

        when(cartService.getCart(1L)).thenReturn(response);

        mockMvc.perform(get("/api/cart/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.totalItems").value(2));
    }

    @Test
    void updateCartItem_Success() throws Exception {
        UpdateCartItemRequestDTO request = UpdateCartItemRequestDTO.builder()
                .quantity(5)
                .build();

        ShoppingCartResponseDTO response = ShoppingCartResponseDTO.builder()
                .id(1L)
                .userId(1L)
                .totalAmount(new BigDecimal("150000"))
                .build();

        when(cartService.updateCartItem(eq(1L), eq(1L), any(UpdateCartItemRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/cart/user/1/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    void removeFromCart_Success() throws Exception {
        ShoppingCartResponseDTO response = ShoppingCartResponseDTO.builder()
                .id(1L)
                .userId(1L)
                .totalAmount(BigDecimal.ZERO)
                .totalItems(0)
                .build();

        when(cartService.removeFromCart(1L, 1L)).thenReturn(response);

        mockMvc.perform(delete("/api/cart/user/1/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(0));
    }

    @Test
    void clearCart_Success() throws Exception {
        doNothing().when(cartService).clearCart(1L);

        mockMvc.perform(delete("/api/cart/user/1"))
                .andExpect(status().isNoContent());
    }
}