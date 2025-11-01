package edu.dosw.petcare.PetCare360.model.components;


import edu.dosw.petcare.PetCare360.model.persistence.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduleValidator {
    private final AppointmentRepository appointmentRepository;

    public void validateVeterinarianAvailability(Long veterinarianId, LocalDateTime dateTime) {
        if (appointmentRepository.existsByVeterinarianIdAndDateTime(veterinarianId, dateTime)) {
            throw new RuntimeException("Veterinarian already has an appointment at this time");
        }
    }
}
