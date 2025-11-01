package edu.dosw.petcare.PetCare360.model.entities;

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
public class Invoice {
    private Long id;
    private String invoiceNumber;
    private InvoiceType type;
    private Long relatedEntityId;
    private String customerName;
    private String customerEmail;
    private List<InvoiceItem> items;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal total;
    private LocalDateTime issuedAt;
    private InvoiceStatus status;
}