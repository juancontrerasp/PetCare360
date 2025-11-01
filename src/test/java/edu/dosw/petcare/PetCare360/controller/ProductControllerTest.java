package edu.dosw.petcare.PetCare360.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dosw.petcare.PetCare360.controller.dto.ProductRequestDTO;
import edu.dosw.petcare.PetCare360.controller.dto.ProductResponseDTO;
import edu.dosw.petcare.PetCare360.model.entities.ProductCategory;
import edu.dosw.petcare.PetCare360.model.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void createProduct_Success() throws Exception {
        ProductRequestDTO request = ProductRequestDTO.builder()
                .name("Comida para perros")
                .description("Alimento balanceado")
                .price(new BigDecimal("50000"))
                .stock(100)
                .category(ProductCategory.FOOD)
                .build();

        ProductResponseDTO response = ProductResponseDTO.builder()
                .id(1L)
                .name("Comida para perros")
                .description("Alimento balanceado")
                .price(new BigDecimal("50000"))
                .stock(100)
                .category(ProductCategory.FOOD)
                .active(true)
                .build();

        when(productService.createProduct(any(ProductRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Comida para perros"))
                .andExpect(jsonPath("$.category").value("FOOD"));
    }

    @Test
    void getProductById_Success() throws Exception {
        ProductResponseDTO response = ProductResponseDTO.builder()
                .id(1L)
                .name("Juguete para gatos")
                .price(new BigDecimal("15000"))
                .category(ProductCategory.TOYS)
                .build();

        when(productService.getProductById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Juguete para gatos"));
    }

    @Test
    void getAllProducts_Success() throws Exception {
        List<ProductResponseDTO> products = Arrays.asList(
                ProductResponseDTO.builder().id(1L).name("Producto 1").build(),
                ProductResponseDTO.builder().id(2L).name("Producto 2").build()
        );

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getProductsByCategory_Success() throws Exception {
        List<ProductResponseDTO> products = Arrays.asList(
                ProductResponseDTO.builder().id(1L).name("Comida 1").category(ProductCategory.FOOD).build()
        );

        when(productService.getProductsByCategory(ProductCategory.FOOD)).thenReturn(products);

        mockMvc.perform(get("/api/products/category/FOOD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].category").value("FOOD"));
    }

    @Test
    void updateProduct_Success() throws Exception {
        ProductRequestDTO request = ProductRequestDTO.builder()
                .name("Producto actualizado")
                .price(new BigDecimal("60000"))
                .stock(50)
                .category(ProductCategory.FOOD)
                .build();

        ProductResponseDTO response = ProductResponseDTO.builder()
                .id(1L)
                .name("Producto actualizado")
                .price(new BigDecimal("60000"))
                .build();

        when(productService.updateProduct(eq(1L), any(ProductRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Producto actualizado"));
    }

    @Test
    void deleteProduct_Success() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
}