package edu.dosw.petcare.PetCare360.model.services;


import edu.dosw.petcare.PetCare360.controller.dto.VeterinarianResponseDTO;
import edu.dosw.petcare.PetCare360.model.entities.Veterinarian;
import edu.dosw.petcare.PetCare360.model.persistence.repository.VeterinarianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeterinarianService{
    private final VeterinarianRepository veterinarianRepository;

    public VeterinarianResponseDTO getVeterinarianById(Long id) {
        Veterinarian vet = veterinarianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinarian not found with id: " + id));
        return mapToResponseDTO(vet);
    }

    public List<VeterinarianResponseDTO> getAllVeterinarians() {
        return veterinarianRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private VeterinarianResponseDTO mapToResponseDTO(Veterinarian vet) {
        return VeterinarianResponseDTO.builder()
                .id(vet.getId())
                .name(vet.getName())
                .specialty(vet.getSpecialty())
                .licenseNumber(vet.getLicenseNumber())
                .build();
    }
}