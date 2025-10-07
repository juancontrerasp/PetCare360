package edu.dosw.petcare.PetCare360.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeterinarianResponseDTO {
    private Long id;
    private String name;
    private String specialty;
    private String licenseNumber;
}