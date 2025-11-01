package edu.dosw.petcare.PetCare360.controller;

import edu.dosw.petcare.PetCare360.controller.dto.VeterinarianResponseDTO;
import edu.dosw.petcare.PetCare360.model.services.VeterinarianService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VeterinarianController.class)
class VeterinarianControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VeterinarianService veterinarianService;

    @Test
    void getVeterinarianById_Success() throws Exception {
        VeterinarianResponseDTO response = VeterinarianResponseDTO.builder()
                .id(1L)
                .name("Dr. Carlos Mendoza")
                .specialty("General")
                .licenseNumber("VET-001")
                .build();

        when(veterinarianService.getVeterinarianById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/veterinarians/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Dr. Carlos Mendoza"))
                .andExpect(jsonPath("$.specialty").value("General"));
    }

    @Test
    void getAllVeterinarians_Success() throws Exception {
        List<VeterinarianResponseDTO> veterinarians = Arrays.asList(
                VeterinarianResponseDTO.builder()
                        .id(1L)
                        .name("Dr. Carlos Mendoza")
                        .specialty("General")
                        .build(),
                VeterinarianResponseDTO.builder()
                        .id(2L)
                        .name("Dra. Ana García")
                        .specialty("Cirugía")
                        .build()
        );

        when(veterinarianService.getAllVeterinarians()).thenReturn(veterinarians);

        mockMvc.perform(get("/api/veterinarians"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Dr. Carlos Mendoza"))
                .andExpect(jsonPath("$[1].name").value("Dra. Ana García"));
    }
}