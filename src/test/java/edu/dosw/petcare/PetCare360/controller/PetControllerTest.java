package edu.dosw.petcare.PetCare360.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dosw.petcare.PetCare360.controller.dto.PetRequestDTO;
import edu.dosw.petcare.PetCare360.controller.dto.PetResponseDTO;
import edu.dosw.petcare.PetCare360.model.entities.PetType;
import edu.dosw.petcare.PetCare360.model.services.PetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PetService petService;

    @Test
    void registerPet_Success() throws Exception {
        PetRequestDTO request = PetRequestDTO.builder()
                .name("Max")
                .type(PetType.DOG)
                .age(3)
                .ownerName("Juan Pérez")
                .build();

        PetResponseDTO response = PetResponseDTO.builder()
                .id(1L)
                .name("Max")
                .type(PetType.DOG)
                .age(3)
                .ownerName("Juan Pérez")
                .build();

        when(petService.registerPet(any(PetRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Max"))
                .andExpect(jsonPath("$.type").value("DOG"))
                .andExpect(jsonPath("$.age").value(3));
    }

    @Test
    void registerPet_InvalidData_ReturnsBadRequest() throws Exception {
        PetRequestDTO request = PetRequestDTO.builder()
                .type(PetType.DOG)
                .age(3)
                .build();

        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPetById_Success() throws Exception {
        PetResponseDTO response = PetResponseDTO.builder()
                .id(1L)
                .name("Luna")
                .type(PetType.CAT)
                .age(2)
                .ownerName("María López")
                .build();

        when(petService.getPetById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Luna"))
                .andExpect(jsonPath("$.type").value("CAT"));
    }

    @Test
    void getAllPets_Success() throws Exception {
        List<PetResponseDTO> pets = Arrays.asList(
                PetResponseDTO.builder().id(1L).name("Max").type(PetType.DOG).build(),
                PetResponseDTO.builder().id(2L).name("Luna").type(PetType.CAT).build()
        );

        when(petService.getAllPets()).thenReturn(pets);

        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Max"))
                .andExpect(jsonPath("$[1].name").value("Luna"));
    }
}