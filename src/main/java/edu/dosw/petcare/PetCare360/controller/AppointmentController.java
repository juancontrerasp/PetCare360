package edu.dosw.petcare.PetCare360.controller;

import edu.dosw.petcare.PetCare360.controller.dto.AppointmentRequestDTO;
import edu.dosw.petcare.PetCare360.controller.dto.AppointmentResponseDTO;
import edu.dosw.petcare.PetCare360.model.services.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointments", description = "Appointment management endpoints")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    @Operation(summary = "Schedule a new appointment")
    public ResponseEntity<AppointmentResponseDTO> scheduleAppointment(
            @Valid @RequestBody AppointmentRequestDTO request) {
        AppointmentResponseDTO response = appointmentService.scheduleAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get appointment by ID")
    public ResponseEntity<AppointmentResponseDTO> getAppointmentById(@PathVariable Long id) {
        AppointmentResponseDTO response = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel an appointment")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/veterinarian/{veterinarianId}")
    @Operation(summary = "Get appointments by veterinarian")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByVeterinarian(
            @PathVariable Long veterinarianId) {
        List<AppointmentResponseDTO> response =
                appointmentService.getAppointmentsByVeterinarian(veterinarianId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pet/{petId}")
    @Operation(summary = "Get appointments by pet")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByPet(
            @PathVariable Long petId) {
        List<AppointmentResponseDTO> response = appointmentService.getAppointmentsByPet(petId);
        return ResponseEntity.ok(response);
    }
}
