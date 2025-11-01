package edu.dosw.petcare.PetCare360.controller.dto;

import edu.dosw.petcare.PetCare360.model.entities.InvoiceStatus;
import edu.dosw.petcare.PetCare360.model.entities.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponseDTO {
    private Long id;
    private String invoiceNumber;
    private InvoiceType type;
    private String customerName;
    private String customerEmail;
    private List<InvoiceItemResponseDTO> items;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal total;
    private LocalDateTime issuedAt;
    private InvoiceStatus status;
}