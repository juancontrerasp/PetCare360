package edu.dosw.petcare.PetCare360.model.components.factories;

import edu.dosw.petcare.PetCare360.model.entities.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProductPurchaseInvoiceFactory extends InvoiceFactory {

    @Override
    protected Invoice buildInvoice(Object sourceData) {
        Order order = (Order) sourceData;

        List<InvoiceItem> items = new ArrayList<>();
        order.getItems().forEach(orderItem -> {
            InvoiceItem invoiceItem = InvoiceItem.builder()
                    .description(orderItem.getProductName())
                    .quantity(orderItem.getQuantity())
                    .unitPrice(orderItem.getUnitPrice())
                    .build();
            items.add(invoiceItem);
        });

        String invoiceNumber = generateInvoiceNumber("PP");

        return Invoice.builder()
                .invoiceNumber(invoiceNumber)
                .type(InvoiceType.PRODUCT_PURCHASE)
                .relatedEntityId(order.getId())
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