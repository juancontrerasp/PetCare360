package edu.dosw.petcare.PetCare360.model.persistence.repository;

import edu.dosw.petcare.PetCare360.model.entities.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends MongoRepository<Pet, Long> {
    List<Pet> findByOwnerName(String ownerName);
    boolean existsById(Long id);
}