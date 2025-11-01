package edu.dosw.petcare.PetCare360.model.persistence.repository;

import edu.dosw.petcare.PetCare360.model.entities.Veterinarian;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeterinarianRepository extends MongoRepository<Veterinarian, Long> {
    List<Veterinarian> findBySpecialty(String specialty);
    boolean existsById(Long id);
}