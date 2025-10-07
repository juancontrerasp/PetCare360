package edu.dosw.petcare.PetCare360.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private Long id;
    private Long petId;
    private Long veterinarianId;
    private LocalDateTime dateTime;
    private String reason;
    private AppointmentStatus status;
    private String observations;
}
