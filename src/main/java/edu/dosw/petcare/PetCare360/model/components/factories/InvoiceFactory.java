package edu.dosw.petcare.PetCare360.model.components.factories;

import edu.dosw.petcare.PetCare360.model.entities.Invoice;

public abstract class InvoiceFactory {

    public Invoice createInvoice(Object sourceData) {
        Invoice invoice = buildInvoice(sourceData);
        calculateTotals(invoice);
        return invoice;
    }

    protected abstract Invoice buildInvoice(Object sourceData);

    protected void calculateTotals(Invoice invoice) {
        invoice.getItems().forEach(item ->
                item.setAmount(item.getUnitPrice().multiply(
                        java.math.BigDecimal.valueOf(item.getQuantity())))
        );

        invoice.setSubtotal(
                invoice.getItems().stream()
                        .map(item -> item.getAmount())
                        .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add)
        );

        invoice.setTax(invoice.getSubtotal().multiply(
                java.math.BigDecimal.valueOf(0.19)) // 19% tax
        );

        invoice.setTotal(invoice.getSubtotal().add(invoice.getTax()));
    }
}