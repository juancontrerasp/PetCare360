package edu.dosw.petcare.PetCare360.controller;

import edu.dosw.petcare.PetCare360.controller.dto.InvoiceResponseDTO;
import edu.dosw.petcare.PetCare360.model.entities.InvoiceStatus;
import edu.dosw.petcare.PetCare360.model.entities.InvoiceType;
import edu.dosw.petcare.PetCare360.model.services.InvoiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvoiceController.class)
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @Test
    void generateInvoiceForAppointment_Success() throws Exception {
        InvoiceResponseDTO response = InvoiceResponseDTO.builder()
                .id(1L)
                .invoiceNumber("MC-20240101120000")
                .type(InvoiceType.MEDICAL_CONSULTATION)
                .customerName("Juan Pérez")
                .customerEmail("juan@email.com")
                .subtotal(new BigDecimal("50000"))
                .tax(new BigDecimal("9500"))
                .total(new BigDecimal("59500"))
                .issuedAt(LocalDateTime.now())
                .status(InvoiceStatus.PENDING)
                .build();

        when(invoiceService.generateInvoiceForAppointment(eq(1L), anyString(), anyString()))
                .thenReturn(response);

        mockMvc.perform(post("/api/invoices/appointment/1")
                        .param("customerName", "Juan Pérez")
                        .param("customerEmail", "juan@email.com"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("MEDICAL_CONSULTATION"))
                .andExpect(jsonPath("$.invoiceNumber").value("MC-20240101120000"));
    }

    @Test
    void getInvoiceById_Success() throws Exception {
        InvoiceResponseDTO response = InvoiceResponseDTO.builder()
                .id(1L)
                .invoiceNumber("PP-20240101120000")
                .type(InvoiceType.PRODUCT_PURCHASE)
                .total(new BigDecimal("100000"))
                .status(InvoiceStatus.PAID)
                .build();

        when(invoiceService.getInvoiceById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/invoices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("PRODUCT_PURCHASE"));
    }

    @Test
    void getInvoiceByNumber_Success() throws Exception {
        InvoiceResponseDTO response = InvoiceResponseDTO.builder()
                .id(1L)
                .invoiceNumber("MC-20240101120000")
                .type(InvoiceType.MEDICAL_CONSULTATION)
                .build();

        when(invoiceService.getInvoiceByNumber("MC-20240101120000")).thenReturn(response);

        mockMvc.perform(get("/api/invoices/number/MC-20240101120000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceNumber").value("MC-20240101120000"));
    }

    @Test
    void getAllInvoices_Success() throws Exception {
        List<InvoiceResponseDTO> invoices = Arrays.asList(
                InvoiceResponseDTO.builder()
                        .id(1L)
                        .invoiceNumber("MC-001")
                        .type(InvoiceType.MEDICAL_CONSULTATION)
                        .build(),
                InvoiceResponseDTO.builder()
                        .id(2L)
                        .invoiceNumber("PP-001")
                        .type(InvoiceType.PRODUCT_PURCHASE)
                        .build()
        );

        when(invoiceService.getAllInvoices()).thenReturn(invoices);

        mockMvc.perform(get("/api/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].type").value("MEDICAL_CONSULTATION"))
                .andExpect(jsonPath("$[1].type").value("PRODUCT_PURCHASE"));
    }

    @Test
    void updateInvoiceStatus_Success() throws Exception {
        InvoiceResponseDTO response = InvoiceResponseDTO.builder()
                .id(1L)
                .status(InvoiceStatus.PAID)
                .build();

        when(invoiceService.updateInvoiceStatus(1L, InvoiceStatus.PAID)).thenReturn(response);

        mockMvc.perform(patch("/api/invoices/1/status")
                        .param("status", "PAID"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAID"));
    }

    @Test
    void updateInvoiceStatus_Cancelled() throws Exception {
        InvoiceResponseDTO response = InvoiceResponseDTO.builder()
                .id(1L)
                .status(InvoiceStatus.CANCELLED)
                .build();

        when(invoiceService.updateInvoiceStatus(1L, InvoiceStatus.CANCELLED)).thenReturn(response);

        mockMvc.perform(patch("/api/invoices/1/status")
                        .param("status", "CANCELLED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }
}