package edu.dosw.petcare.PetCare360.model.persistence.repository;

import edu.dosw.petcare.PetCare360.model.entities.Appointment;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class AppointmentRepository {
    private final Map<Long, Appointment> appointments = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Appointment save(Appointment appointment) {
        if (appointment.getId() == null) {
            appointment.setId(idGenerator.getAndIncrement());
        }
        appointments.put(appointment.getId(), appointment);
        return appointment;
    }

    public Optional<Appointment> findById(Long id) {
        return Optional.ofNullable(appointments.get(id));
    }

    public List<Appointment> findAll() {
        return new ArrayList<>(appointments.values());
    }

    public List<Appointment> findByVeterinarianId(Long veterinarianId) {
        return appointments.values().stream()
                .filter(a -> a.getVeterinarianId().equals(veterinarianId))
                .collect(Collectors.toList());
    }

    public List<Appointment> findByPetId(Long petId) {
        return appointments.values().stream()
                .filter(a -> a.getPetId().equals(petId))
                .collect(Collectors.toList());
    }

    public boolean existsByVeterinarianIdAndDateTime(Long veterinarianId, LocalDateTime dateTime) {
        return appointments.values().stream()
                .anyMatch(a -> a.getVeterinarianId().equals(veterinarianId)
                        && a.getDateTime().equals(dateTime));
    }

    public void deleteById(Long id) {
        appointments.remove(id);
    }

    public boolean existsById(Long id) {
        return appointments.containsKey(id);
    }
}