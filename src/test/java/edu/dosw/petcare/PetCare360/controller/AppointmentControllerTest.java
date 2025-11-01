package edu.dosw.petcare.PetCare360.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dosw.petcare.PetCare360.controller.dto.AppointmentRequestDTO;
import edu.dosw.petcare.PetCare360.controller.dto.AppointmentResponseDTO;
import edu.dosw.petcare.PetCare360.model.entities.AppointmentStatus;
import edu.dosw.petcare.PetCare360.model.services.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AppointmentService appointmentService;

    @Test
    void scheduleAppointment_Success() throws Exception {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        AppointmentRequestDTO request = AppointmentRequestDTO.builder()
                .petId(1L)
                .veterinarianId(1L)
                .dateTime(futureDate)
                .reason("Vacunación")
                .observations("Primera visita")
                .build();

        AppointmentResponseDTO response = AppointmentResponseDTO.builder()
                .id(1L)
                .dateTime(futureDate)
                .reason("Vacunación")
                .status(AppointmentStatus.SCHEDULED)
                .observations("Primera visita")
                .build();

        when(appointmentService.scheduleAppointment(any(AppointmentRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.reason").value("Vacunación"))
                .andExpect(jsonPath("$.status").value("SCHEDULED"));
    }

    @Test
    void scheduleAppointment_InvalidData_ReturnsBadRequest() throws Exception {
        AppointmentRequestDTO request = AppointmentRequestDTO.builder()
                .build();

        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAppointmentById_Success() throws Exception {
        AppointmentResponseDTO response = AppointmentResponseDTO.builder()
                .id(1L)
                .dateTime(LocalDateTime.now().plusDays(1))
                .reason("Consulta general")
                .status(AppointmentStatus.SCHEDULED)
                .build();

        when(appointmentService.getAppointmentById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/appointments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.reason").value("Consulta general"));
    }

    @Test
    void cancelAppointment_Success() throws Exception {
        doNothing().when(appointmentService).cancelAppointment(1L);

        mockMvc.perform(delete("/api/appointments/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAppointmentsByVeterinarian_Success() throws Exception {
        List<AppointmentResponseDTO> appointments = Arrays.asList(
                AppointmentResponseDTO.builder().id(1L).reason("Consulta 1").build(),
                AppointmentResponseDTO.builder().id(2L).reason("Consulta 2").build()
        );

        when(appointmentService.getAppointmentsByVeterinarian(1L)).thenReturn(appointments);

        mockMvc.perform(get("/api/appointments/veterinarian/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void getAppointmentsByPet_Success() throws Exception {
        List<AppointmentResponseDTO> appointments = Arrays.asList(
                AppointmentResponseDTO.builder().id(1L).reason("Vacunación").build()
        );

        when(appointmentService.getAppointmentsByPet(1L)).thenReturn(appointments);

        mockMvc.perform(get("/api/appointments/pet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].reason").value("Vacunación"));
    }
}