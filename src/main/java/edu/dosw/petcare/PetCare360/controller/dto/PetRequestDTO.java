package edu.dosw.petcare.PetCare360.controller.dto;

import edu.dosw.petcare.PetCare360.model.entities.PetType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetRequestDTO {
    @NotBlank(message = "Pet name is required")
    private String name;

    @NotNull(message = "Pet type is required")
    private PetType type;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be positive")
    private Integer age;

    @NotBlank(message = "Owner name is required")
    private String ownerName;
}
