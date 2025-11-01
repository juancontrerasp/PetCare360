package edu.dosw.petcare.PetCare360.model.services;

import edu.dosw.petcare.PetCare360.controller.dto.*;
import edu.dosw.petcare.PetCare360.model.entities.*;
import edu.dosw.petcare.PetCare360.model.components.factories.InvoiceFactory;
import edu.dosw.petcare.PetCare360.model.components.factories.InvoiceFactoryProvider;
import edu.dosw.petcare.PetCare360.model.persistence.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceFactoryProvider factoryProvider;
    private final AppointmentRepository appointmentRepository;
    private final OrderRepository orderRepository;
    private final PetRepository petRepository;

    public InvoiceResponseDTO generateInvoiceForOrder(Order order, String customerName,
                                                      String customerEmail) {
        InvoiceFactory factory = factoryProvider.getFactory(InvoiceType.PRODUCT_PURCHASE);
        Invoice invoice = factory.createInvoice(order);

        invoice.setCustomerName(customerName);
        invoice.setCustomerEmail(customerEmail);

        invoice = invoiceRepository.save(invoice);
        return mapToResponseDTO(invoice);
    }

    public InvoiceResponseDTO generateInvoiceForAppointment(Long appointmentId,
                                                            String customerName,
                                                            String customerEmail) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        InvoiceFactory factory = factoryProvider.getFactory(InvoiceType.MEDICAL_CONSULTATION);
        Invoice invoice = factory.createInvoice(appointment);

        if (customerName == null || customerName.isEmpty()) {
            Pet pet = petRepository.findById(appointment.getPetId())
                    .orElseThrow(() -> new RuntimeException("Pet not found"));
            customerName = pet.getOwnerName();
        }

        invoice.setCustomerName(customerName);
        invoice.setCustomerEmail(customerEmail);

        invoice = invoiceRepository.save(invoice);
        return mapToResponseDTO(invoice);
    }

    public InvoiceResponseDTO getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
        return mapToResponseDTO(invoice);
    }

    public InvoiceResponseDTO getInvoiceByNumber(String invoiceNumber) {
        Invoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new RuntimeException("Invoice not found with number: " + invoiceNumber));
        return mapToResponseDTO(invoice);
    }

    public List<InvoiceResponseDTO> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public InvoiceResponseDTO updateInvoiceStatus(Long id, InvoiceStatus newStatus) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));

        invoice.setStatus(newStatus);
        invoice = invoiceRepository.save(invoice);

        return mapToResponseDTO(invoice);
    }

    private InvoiceResponseDTO mapToResponseDTO(Invoice invoice) {
        List<InvoiceItemResponseDTO> items = invoice.getItems().stream()
                .map(item -> InvoiceItemResponseDTO.builder()
                        .id(item.getId())
                        .description(item.getDescription())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .amount(item.getAmount())
                        .build())
                .collect(Collectors.toList());

        return InvoiceResponseDTO.builder()
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .type(invoice.getType())
                .customerName(invoice.getCustomerName())
                .customerEmail(invoice.getCustomerEmail())
                .items(items)
                .subtotal(invoice.getSubtotal())
                .tax(invoice.getTax())
                .total(invoice.getTotal())
                .issuedAt(invoice.getIssuedAt())
                .status(invoice.getStatus())
                .build();
    }
}
