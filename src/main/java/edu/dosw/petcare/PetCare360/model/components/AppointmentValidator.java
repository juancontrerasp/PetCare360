package edu.dosw.petcare.PetCare360.model.components;

import edu.dosw.petcare.PetCare360.model.persistence.repository.PetRepository;
import edu.dosw.petcare.PetCare360.model.persistence.repository.VeterinarianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AppointmentValidator {
    private final PetRepository petRepository;
    private final VeterinarianRepository veterinarianRepository;

    public void validateAppointmentData(Long petId, Long veterinarianId, LocalDateTime dateTime) {
        if (!petRepository.existsById(petId)) {
            throw new RuntimeException("Pet not found with id: " + petId);
        }

        if (!veterinarianRepository.existsById(veterinarianId)) {
            throw new RuntimeException("Veterinarian not found with id: " + veterinarianId);
        }

        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Appointment date must be in the future");
        }
    }
}