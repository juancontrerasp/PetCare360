package edu.dosw.petcare.PetCare360.controller;

import edu.dosw.petcare.PetCare360.controller.dto.OrderResponseDTO;
import edu.dosw.petcare.PetCare360.model.entities.OrderStatus;
import edu.dosw.petcare.PetCare360.model.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void getOrderById_Success() throws Exception {
        OrderResponseDTO response = OrderResponseDTO.builder()
                .id(1L)
                .userId(1L)
                .totalAmount(new BigDecimal("100000"))
                .status(OrderStatus.PENDING)
                .shippingAddress("Calle 123")
                .paymentMethod("Tarjeta")
                .createdAt(LocalDateTime.now())
                .build();

        when(orderService.getOrderById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void getAllOrders_Success() throws Exception {
        List<OrderResponseDTO> orders = Arrays.asList(
                OrderResponseDTO.builder().id(1L).status(OrderStatus.PENDING).build(),
                OrderResponseDTO.builder().id(2L).status(OrderStatus.CONFIRMED).build()
        );

        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void getOrdersByUser_Success() throws Exception {
        List<OrderResponseDTO> orders = Arrays.asList(
                OrderResponseDTO.builder()
                        .id(1L)
                        .userId(1L)
                        .status(OrderStatus.DELIVERED)
                        .build()
        );

        when(orderService.getOrdersByUser(1L)).thenReturn(orders);

        mockMvc.perform(get("/api/orders/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userId").value(1));
    }

    @Test
    void updateOrderStatus_Success() throws Exception {
        OrderResponseDTO response = OrderResponseDTO.builder()
                .id(1L)
                .status(OrderStatus.SHIPPED)
                .build();

        when(orderService.updateOrderStatus(1L, OrderStatus.SHIPPED)).thenReturn(response);

        mockMvc.perform(patch("/api/orders/1/status")
                        .param("status", "SHIPPED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SHIPPED"));
    }
}