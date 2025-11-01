package edu.dosw.petcare.PetCare360.controller;

import edu.dosw.petcare.PetCare360.controller.dto.InvoiceResponseDTO;
import edu.dosw.petcare.PetCare360.model.entities.InvoiceStatus;
import edu.dosw.petcare.PetCare360.model.services.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@Tag(name = "Invoices", description = "Invoice management endpoints")
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping("/appointment/{appointmentId}")
    @Operation(summary = "Generate invoice for medical consultation")
    public ResponseEntity<InvoiceResponseDTO> generateInvoiceForAppointment(
            @PathVariable Long appointmentId,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String customerEmail) {
        InvoiceResponseDTO response = invoiceService.generateInvoiceForAppointment(
                appointmentId, customerName, customerEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get invoice by ID")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceById(@PathVariable Long id) {
        InvoiceResponseDTO response = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/number/{invoiceNumber}")
    @Operation(summary = "Get invoice by invoice number")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceByNumber(
            @PathVariable String invoiceNumber) {
        InvoiceResponseDTO response = invoiceService.getInvoiceByNumber(invoiceNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all invoices")
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoices() {
        List<InvoiceResponseDTO> response = invoiceService.getAllInvoices();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update invoice status")
    public ResponseEntity<InvoiceResponseDTO> updateInvoiceStatus(
            @PathVariable Long id,
            @RequestParam InvoiceStatus status) {
        InvoiceResponseDTO response = invoiceService.updateInvoiceStatus(id, status);
        return ResponseEntity.ok(response);
    }
}