package edu.dosw.petcare.PetCare360.model.persistence.repository;

import edu.dosw.petcare.PetCare360.model.entities.Pet;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PetRepository {
    private final Map<Long, Pet> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Pet save(Pet pet) {
        if (pet.getId() == null) {
            pet.setId(idGenerator.getAndIncrement());
        }
        pets.put(pet.getId(), pet);
        return pet;
    }

    public Optional<Pet> findById(Long id) {
        return Optional.ofNullable(pets.get(id));
    }

    public List<Pet> findAll() {
        return new ArrayList<>(pets.values());
    }

    public boolean existsById(Long id) {
        return pets.containsKey(id);
    }

    public void deleteById(Long id) {
        pets.remove(id);
    }
}