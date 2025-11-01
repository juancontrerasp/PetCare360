package edu.dosw.petcare.PetCare360.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dosw.petcare.PetCare360.controller.dto.CheckoutRequestDTO;
import edu.dosw.petcare.PetCare360.controller.dto.OrderResponseDTO;
import edu.dosw.petcare.PetCare360.model.entities.OrderStatus;
import edu.dosw.petcare.PetCare360.model.services.CheckoutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CheckoutController.class)
class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CheckoutService checkoutService;

    @Test
    void processCheckout_Success() throws Exception {
        CheckoutRequestDTO request = CheckoutRequestDTO.builder()
                .userId(1L)
                .shippingAddress("Calle 123 #45-67")
                .paymentMethod("Tarjeta de crédito")
                .customerName("Juan Pérez")
                .customerEmail("juan@email.com")
                .build();

        OrderResponseDTO response = OrderResponseDTO.builder()
                .id(1L)
                .userId(1L)
                .items(Arrays.asList())
                .totalAmount(new BigDecimal("150000"))
                .status(OrderStatus.PENDING)
                .shippingAddress("Calle 123 #45-67")
                .paymentMethod("Tarjeta de crédito")
                .createdAt(LocalDateTime.now())
                .build();

        when(checkoutService.processCheckout(any(CheckoutRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.shippingAddress").value("Calle 123 #45-67"));
    }

    @Test
    void processCheckout_InvalidData_ReturnsBadRequest() throws Exception {
        CheckoutRequestDTO request = CheckoutRequestDTO.builder()
                .userId(1L)
                .build();

        mockMvc.perform(post("/api/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}