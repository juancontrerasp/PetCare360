package edu.dosw.petcare.PetCare360.controller;

import edu.dosw.petcare.PetCare360.controller.dto.CheckoutRequestDTO;
import edu.dosw.petcare.PetCare360.controller.dto.OrderResponseDTO;
import edu.dosw.petcare.PetCare360.model.services.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
@Tag(name = "Checkout", description = "Checkout process endpoints")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    @Operation(summary = "Process checkout and create order")
    public ResponseEntity<OrderResponseDTO> processCheckout(
            @Valid @RequestBody CheckoutRequestDTO request) {
        OrderResponseDTO response = checkoutService.processCheckout(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}