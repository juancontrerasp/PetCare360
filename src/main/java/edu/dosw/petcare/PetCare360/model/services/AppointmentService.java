package edu.dosw.petcare.PetCare360.model.services;


import edu.dosw.petcare.PetCare360.controller.dto.AppointmentRequestDTO;
import edu.dosw.petcare.PetCare360.controller.dto.AppointmentResponseDTO;
import edu.dosw.petcare.PetCare360.controller.dto.PetResponseDTO;
import edu.dosw.petcare.PetCare360.controller.dto.VeterinarianResponseDTO;
import edu.dosw.petcare.PetCare360.model.entities.Appointment;
import edu.dosw.petcare.PetCare360.model.entities.AppointmentStatus;
import edu.dosw.petcare.PetCare360.model.entities.Pet;
import edu.dosw.petcare.PetCare360.model.entities.Veterinarian;
import edu.dosw.petcare.PetCare360.model.persistence.repository.AppointmentRepository;
import edu.dosw.petcare.PetCare360.model.persistence.repository.PetRepository;
import edu.dosw.petcare.PetCare360.model.persistence.repository.VeterinarianRepository;
import edu.dosw.petcare.PetCare360.model.components.AppointmentValidator;
import edu.dosw.petcare.PetCare360.model.components.ScheduleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final VeterinarianRepository veterinarianRepository;
    private final AppointmentValidator appointmentValidator;
    private final ScheduleValidator scheduleValidator;

    public AppointmentResponseDTO scheduleAppointment(AppointmentRequestDTO request) {
        appointmentValidator.validateAppointmentData(
                request.getPetId(),
                request.getVeterinarianId(),
                request.getDateTime()
        );

        scheduleValidator.validateVeterinarianAvailability(
                request.getVeterinarianId(),
                request.getDateTime()
        );

        Appointment appointment = Appointment.builder()
                .petId(request.getPetId())
                .veterinarianId(request.getVeterinarianId())
                .dateTime(request.getDateTime())
                .reason(request.getReason())
                .observations(request.getObservations())
                .status(AppointmentStatus.SCHEDULED)
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return mapToResponseDTO(savedAppointment);
    }

    public AppointmentResponseDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        return mapToResponseDTO(appointment);
    }

    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    public List<AppointmentResponseDTO> getAppointmentsByVeterinarian(Long veterinarianId) {
        if (!veterinarianRepository.existsById(veterinarianId)) {
            throw new RuntimeException("Veterinarian not found with id: " + veterinarianId);
        }

        return appointmentRepository.findByVeterinarianId(veterinarianId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentResponseDTO> getAppointmentsByPet(Long petId) {
        if (!petRepository.existsById(petId)) {
            throw new RuntimeException("Pet not found with id: " + petId);
        }

        return appointmentRepository.findByPetId(petId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private AppointmentResponseDTO mapToResponseDTO(Appointment appointment) {
        Pet pet = petRepository.findById(appointment.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        Veterinarian vet = veterinarianRepository.findById(appointment.getVeterinarianId())
                .orElseThrow(() -> new RuntimeException("Veterinarian not found"));

        return AppointmentResponseDTO.builder()
                .id(appointment.getId())
                .pet(mapPetToDTO(pet))
                .veterinarian(mapVetToDTO(vet))
                .dateTime(appointment.getDateTime())
                .reason(appointment.getReason())
                .status(appointment.getStatus())
                .observations(appointment.getObservations())
                .build();
    }

    private PetResponseDTO mapPetToDTO(Pet pet) {
        return PetResponseDTO.builder()
                .id(pet.getId())
                .name(pet.getName())
                .type(pet.getType())
                .age(pet.getAge())
                .ownerName(pet.getOwnerName())
                .build();
    }

    private VeterinarianResponseDTO mapVetToDTO(Veterinarian vet) {
        return VeterinarianResponseDTO.builder()
                .id(vet.getId())
                .name(vet.getName())
                .specialty(vet.getSpecialty())
                .licenseNumber(vet.getLicenseNumber())
                .build();
    }
}
