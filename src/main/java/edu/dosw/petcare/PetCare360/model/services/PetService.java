package edu.dosw.petcare.PetCare360.model.services;

import edu.dosw.petcare.PetCare360.controller.dto.PetRequestDTO;
import edu.dosw.petcare.PetCare360.controller.dto.PetResponseDTO;
import edu.dosw.petcare.PetCare360.model.entities.Pet;
import edu.dosw.petcare.PetCare360.model.persistence.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    public PetResponseDTO registerPet(PetRequestDTO request) {
        Pet pet = Pet.builder()
                .name(request.getName())
                .type(request.getType())
                .age(request.getAge())
                .ownerName(request.getOwnerName())
                .build();

        Pet savedPet = petRepository.save(pet);
        return mapToResponseDTO(savedPet);
    }

    public PetResponseDTO getPetById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
        return mapToResponseDTO(pet);
    }

    public List<PetResponseDTO> getAllPets() {
        return petRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private PetResponseDTO mapToResponseDTO(Pet pet) {
        return PetResponseDTO.builder()
                .id(pet.getId())
                .name(pet.getName())
                .type(pet.getType())
                .age(pet.getAge())
                .ownerName(pet.getOwnerName())
                .build();
    }
}
