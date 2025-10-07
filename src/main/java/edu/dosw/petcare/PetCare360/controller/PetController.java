package edu.dosw.petcare.PetCare360.controller;

import edu.dosw.petcare.PetCare360.controller.dto.PetRequestDTO;
import edu.dosw.petcare.PetCare360.controller.dto.PetResponseDTO;
import edu.dosw.petcare.PetCare360.model.services.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
@Tag(name = "Pets", description = "Pet management endpoints")
public class PetController {
    private final PetService petService;

    @PostMapping
    @Operation(summary = "Register a new pet")
    public ResponseEntity<PetResponseDTO> registerPet(@Valid @RequestBody PetRequestDTO request) {
        PetResponseDTO response = petService.registerPet(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get pet by ID")
    public ResponseEntity<PetResponseDTO> getPetById(@PathVariable Long id) {
        PetResponseDTO response = petService.getPetById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all pets")
    public ResponseEntity<List<PetResponseDTO>> getAllPets() {
        List<PetResponseDTO> response = petService.getAllPets();
        return ResponseEntity.ok(response);
    }
}
