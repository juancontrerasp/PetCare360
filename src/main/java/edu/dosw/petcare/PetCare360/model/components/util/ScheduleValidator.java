package edu.dosw.petcare.PetCare360.model.components.util;

import com.vet.exception.AppointmentConflictException;
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
            throw new AppointmentConflictException(
                    "Veterinarian already has an appointment at this time"
            );
        }
    }
}
