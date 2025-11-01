package edu.dosw.petcare.PetCare360.model.persistence.repository;

import edu.dosw.petcare.PetCare360.model.entities.Veterinarian;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class VeterinarianRepository {
    private final Map<Long, Veterinarian> veterinarians = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public VeterinarianRepository() {
        initializeData();
    }

    private void initializeData() {
        Veterinarian vet1 = Veterinarian.builder()
                .id(1L)
                .name("Dr. Carlos Mendoza")
                .specialty("General")
                .licenseNumber("VET-001")
                .build();

        Veterinarian vet2 = Veterinarian.builder()
                .id(2L)
                .name("Dra. Ana García")
                .specialty("Cirugía")
                .licenseNumber("VET-002")
                .build();

        Veterinarian vet3 = Veterinarian.builder()
                .id(3L)
                .name("Dr. Pedro López")
                .specialty("Dermatología")
                .licenseNumber("VET-003")
                .build();

        veterinarians.put(1L, vet1);
        veterinarians.put(2L, vet2);
        veterinarians.put(3L, vet3);
        idGenerator.set(4L);
    }

    public Veterinarian save(Veterinarian veterinarian) {
        if (veterinarian.getId() == null) {
            veterinarian.setId(idGenerator.getAndIncrement());
        }
        veterinarians.put(veterinarian.getId(), veterinarian);
        return veterinarian;
    }

    public Optional<Veterinarian> findById(Long id) {
        return Optional.ofNullable(veterinarians.get(id));
    }

    public List<Veterinarian> findAll() {
        return new ArrayList<>(veterinarians.values());
    }

    public boolean existsById(Long id) {
        return veterinarians.containsKey(id);
    }

    public void deleteById(Long id) {
        veterinarians.remove(id);
    }
}