package edu.dosw.petcare.PetCare360.controller.dto;

import edu.dosw.petcare.PetCare360.model.entities.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDTO {
    private Long id;
    private PetResponseDTO pet;
    private VeterinarianResponseDTO veterinarian;
    private LocalDateTime dateTime;
    private String reason;
    private AppointmentStatus status;
    private String observations;
}
