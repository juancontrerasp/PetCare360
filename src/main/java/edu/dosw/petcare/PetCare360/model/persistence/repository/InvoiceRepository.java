package edu.dosw.petcare.PetCare360.model.persistence.repository;

import edu.dosw.petcare.PetCare360.model.entities.Invoice;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InvoiceRepository {
    private final Map<Long, Invoice> invoices = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Invoice save(Invoice invoice) {
        if (invoice.getId() == null) {
            invoice.setId(idGenerator.getAndIncrement());
        }
        invoices.put(invoice.getId(), invoice);
        return invoice;
    }

    public Optional<Invoice> findById(Long id) {
        return Optional.ofNullable(invoices.get(id));
    }

    public Optional<Invoice> findByInvoiceNumber(String invoiceNumber) {
        return invoices.values().stream()
                .filter(inv -> inv.getInvoiceNumber().equals(invoiceNumber))
                .findFirst();
    }

    public List<Invoice> findAll() {
        return new ArrayList<>(invoices.values());
    }
}