package edu.dosw.petcare.PetCare360.controller.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDTO {
    @NotNull(message = "Pet ID is required")
    private Long petId;

    @NotNull(message = "Veterinarian ID is required")
    private Long veterinarianId;

    @NotNull(message = "Date and time is required")
    @Future(message = "Appointment must be scheduled for a future date")
    private LocalDateTime dateTime;

    @NotBlank(message = "Reason for consultation is required")
    private String reason;

    private String observations;
}
