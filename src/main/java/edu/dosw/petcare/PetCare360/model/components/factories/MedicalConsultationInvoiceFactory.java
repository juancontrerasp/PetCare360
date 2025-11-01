package edu.dosw.petcare.PetCare360.model.components.factories;

import edu.dosw.petcare.PetCare360.model.entities.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MedicalConsultationInvoiceFactory extends InvoiceFactory {

    @Override
    protected Invoice buildInvoice(Object sourceData) {
        Appointment appointment = (Appointment) sourceData;

        List<InvoiceItem> items = new ArrayList<>();
        InvoiceItem consultationItem = InvoiceItem.builder()
                .description("Consulta Veterinaria - " + appointment.getReason())
                .quantity(1)
                .unitPrice(new BigDecimal("50000"))
                .build();
        items.add(consultationItem);

        String invoiceNumber = generateInvoiceNumber("MC");

        return Invoice.builder()
                .invoiceNumber(invoiceNumber)
                .type(InvoiceType.MEDICAL_CONSULTATION)
                .relatedEntityId(appointment.getId())
                .items(items)
                .issuedAt(LocalDateTime.now())
                .status(InvoiceStatus.PENDING)
                .build();
    }

    private String generateInvoiceNumber(String prefix) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return prefix + "-" + timestamp;
    }
}