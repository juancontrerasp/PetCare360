package edu.dosw.petcare.PetCare360.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Veterinarian {
    private Long id;
    private String name;
    private String specialty;
    private String licenseNumber;
}
