package edu.dosw.petcare.PetCare360.controller;

import edu.dosw.petcare.PetCare360.controller.dto.VeterinarianResponseDTO;
import edu.dosw.petcare.PetCare360.model.services.VeterinarianService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarians")
@RequiredArgsConstructor
@Tag(name = "Veterinarians", description = "Veterinarian management endpoints")
public class VeterinarianController {
    private final VeterinarianService veterinarianService;

    @GetMapping("/{id}")
    @Operation(summary = "Get veterinarian by ID")
    public ResponseEntity<VeterinarianResponseDTO> getVeterinarianById(@PathVariable Long id) {
        VeterinarianResponseDTO response = veterinarianService.getVeterinarianById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all veterinarians")
    public ResponseEntity<List<VeterinarianResponseDTO>> getAllVeterinarians() {
        List<VeterinarianResponseDTO> response = veterinarianService.getAllVeterinarians();
        return ResponseEntity.ok(response);
    }
}
