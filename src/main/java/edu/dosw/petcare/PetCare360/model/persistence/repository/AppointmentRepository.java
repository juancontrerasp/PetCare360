package edu.dosw.petcare.PetCare360.model.persistence.repository;

import edu.dosw.petcare.PetCare360.model.entities.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, Long> {
    List<Appointment> findByVeterinarianId(Long veterinarianId);
    List<Appointment> findByPetId(Long petId);
    boolean existsByVeterinarianIdAndDateTime(Long veterinarianId, LocalDateTime dateTime);
}