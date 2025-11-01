package edu.dosw.petcare.PetCare360.controller.dto;

import edu.dosw.petcare.PetCare360.model.entities.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void appointmentRequestDTO_ValidData_NoViolations() {
        AppointmentRequestDTO dto = AppointmentRequestDTO.builder()
                .petId(1L)
                .veterinarianId(1L)
                .dateTime(LocalDateTime.now().plusDays(1))
                .reason("Vacunación")
                .observations("Primera visita")
                .build();

        Set<ConstraintViolation<AppointmentRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void appointmentRequestDTO_NullPetId_HasViolation() {
        AppointmentRequestDTO dto = AppointmentRequestDTO.builder()
                .veterinarianId(1L)
                .dateTime(LocalDateTime.now().plusDays(1))
                .reason("Vacunación")
                .build();

        Set<ConstraintViolation<AppointmentRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void appointmentRequestDTO_PastDate_HasViolation() {
        AppointmentRequestDTO dto = AppointmentRequestDTO.builder()
                .petId(1L)
                .veterinarianId(1L)
                .dateTime(LocalDateTime.now().minusDays(1))
                .reason("Vacunación")
                .build();

        Set<ConstraintViolation<AppointmentRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void appointmentResponseDTO_BuilderPattern_Success() {
        LocalDateTime now = LocalDateTime.now();
        AppointmentResponseDTO dto = AppointmentResponseDTO.builder()
                .id(1L)
                .dateTime(now)
                .reason("Consulta")
                .status(AppointmentStatus.SCHEDULED)
                .observations("Observaciones")
                .build();

        assertEquals(1L, dto.getId());
        assertEquals(now, dto.getDateTime());
        assertEquals("Consulta", dto.getReason());
        assertEquals(AppointmentStatus.SCHEDULED, dto.getStatus());
    }

    @Test
    void petRequestDTO_ValidData_NoViolations() {
        PetRequestDTO dto = PetRequestDTO.builder()
                .name("Max")
                .type(PetType.DOG)
                .age(3)
                .ownerName("Juan Pérez")
                .build();

        Set<ConstraintViolation<PetRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void petRequestDTO_BlankName_HasViolation() {
        PetRequestDTO dto = PetRequestDTO.builder()
                .name("")
                .type(PetType.DOG)
                .age(3)
                .ownerName("Juan Pérez")
                .build();

        Set<ConstraintViolation<PetRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void petRequestDTO_NegativeAge_HasViolation() {
        PetRequestDTO dto = PetRequestDTO.builder()
                .name("Max")
                .type(PetType.DOG)
                .age(-1)
                .ownerName("Juan Pérez")
                .build();

        Set<ConstraintViolation<PetRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void petResponseDTO_BuilderPattern_Success() {
        PetResponseDTO dto = PetResponseDTO.builder()
                .id(1L)
                .name("Luna")
                .type(PetType.CAT)
                .age(2)
                .ownerName("María López")
                .build();

        assertEquals(1L, dto.getId());
        assertEquals("Luna", dto.getName());
        assertEquals(PetType.CAT, dto.getType());
        assertEquals(2, dto.getAge());
    }

    @Test
    void veterinarianResponseDTO_BuilderPattern_Success() {
        VeterinarianResponseDTO dto = VeterinarianResponseDTO.builder()
                .id(1L)
                .name("Dr. Carlos")
                .specialty("Cirugía")
                .licenseNumber("VET-001")
                .build();

        assertEquals(1L, dto.getId());
        assertEquals("Dr. Carlos", dto.getName());
        assertEquals("Cirugía", dto.getSpecialty());
        assertEquals("VET-001", dto.getLicenseNumber());
    }

    @Test
    void productRequestDTO_ValidData_NoViolations() {
        ProductRequestDTO dto = ProductRequestDTO.builder()
                .name("Comida para perros")
                .description("Alimento balanceado")
                .price(new BigDecimal("50000"))
                .stock(100)
                .category(ProductCategory.FOOD)
                .imageUrl("http://example.com/image.jpg")
                .build();

        Set<ConstraintViolation<ProductRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void productRequestDTO_NegativePrice_HasViolation() {
        ProductRequestDTO dto = ProductRequestDTO.builder()
                .name("Producto")
                .price(new BigDecimal("-100"))
                .stock(10)
                .category(ProductCategory.FOOD)
                .build();

        Set<ConstraintViolation<ProductRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void productResponseDTO_BuilderPattern_Success() {
        ProductResponseDTO dto = ProductResponseDTO.builder()
                .id(1L)
                .name("Juguete")
                .description("Para gatos")
                .price(new BigDecimal("15000"))
                .stock(50)
                .category(ProductCategory.TOYS)
                .active(true)
                .build();

        assertEquals(1L, dto.getId());
        assertEquals("Juguete", dto.getName());
        assertTrue(dto.getActive());
    }

    @Test
    void addToCartRequestDTO_ValidData_NoViolations() {
        AddToCartRequestDTO dto = AddToCartRequestDTO.builder()
                .productId(1L)
                .quantity(2)
                .build();

        Set<ConstraintViolation<AddToCartRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void addToCartRequestDTO_ZeroQuantity_HasViolation() {
        AddToCartRequestDTO dto = AddToCartRequestDTO.builder()
                .productId(1L)
                .quantity(0)
                .build();

        Set<ConstraintViolation<AddToCartRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void updateCartItemRequestDTO_ValidData_NoViolations() {
        UpdateCartItemRequestDTO dto = UpdateCartItemRequestDTO.builder()
                .quantity(5)
                .build();

        Set<ConstraintViolation<UpdateCartItemRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shoppingCartResponseDTO_BuilderPattern_Success() {
        ShoppingCartResponseDTO dto = ShoppingCartResponseDTO.builder()
                .id(1L)
                .userId(1L)
                .totalAmount(new BigDecimal("100000"))
                .totalItems(3)
                .build();

        assertEquals(1L, dto.getId());
        assertEquals(3, dto.getTotalItems());
        assertEquals(new BigDecimal("100000"), dto.getTotalAmount());
    }

    @Test
    void cartItemResponseDTO_BuilderPattern_Success() {
        CartItemResponseDTO dto = CartItemResponseDTO.builder()
                .id(1L)
                .productId(1L)
                .productName("Producto Test")
                .unitPrice(new BigDecimal("10000"))
                .quantity(2)
                .subtotal(new BigDecimal("20000"))
                .build();

        assertEquals(1L, dto.getId());
        assertEquals("Producto Test", dto.getProductName());
        assertEquals(2, dto.getQuantity());
    }

    @Test
    void checkoutRequestDTO_ValidData_NoViolations() {
        CheckoutRequestDTO dto = CheckoutRequestDTO.builder()
                .userId(1L)
                .shippingAddress("Calle 123")
                .paymentMethod("Tarjeta")
                .customerName("Juan")
                .customerEmail("juan@email.com")
                .build();

        Set<ConstraintViolation<CheckoutRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void checkoutRequestDTO_BlankAddress_HasViolation() {
        CheckoutRequestDTO dto = CheckoutRequestDTO.builder()
                .userId(1L)
                .shippingAddress("")
                .paymentMethod("Tarjeta")
                .build();

        Set<ConstraintViolation<CheckoutRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void orderResponseDTO_BuilderPattern_Success() {
        OrderResponseDTO dto = OrderResponseDTO.builder()
                .id(1L)
                .userId(1L)
                .totalAmount(new BigDecimal("150000"))
                .status(OrderStatus.PENDING)
                .shippingAddress("Calle 123")
                .paymentMethod("Efectivo")
                .createdAt(LocalDateTime.now())
                .build();

        assertEquals(1L, dto.getId());
        assertEquals(OrderStatus.PENDING, dto.getStatus());
        assertNotNull(dto.getCreatedAt());
    }

    @Test
    void orderItemResponseDTO_BuilderPattern_Success() {
        OrderItemResponseDTO dto = OrderItemResponseDTO.builder()
                .id(1L)
                .productId(1L)
                .productName("Producto")
                .unitPrice(new BigDecimal("25000"))
                .quantity(3)
                .subtotal(new BigDecimal("75000"))
                .build();

        assertEquals(1L, dto.getId());
        assertEquals(3, dto.getQuantity());
        assertEquals(new BigDecimal("75000"), dto.getSubtotal());
    }

    @Test
    void invoiceResponseDTO_BuilderPattern_Success() {
        InvoiceResponseDTO dto = InvoiceResponseDTO.builder()
                .id(1L)
                .invoiceNumber("INV-001")
                .type(InvoiceType.PRODUCT_PURCHASE)
                .customerName("Cliente Test")
                .customerEmail("cliente@email.com")
                .subtotal(new BigDecimal("100000"))
                .tax(new BigDecimal("19000"))
                .total(new BigDecimal("119000"))
                .issuedAt(LocalDateTime.now())
                .status(InvoiceStatus.PAID)
                .build();

        assertEquals("INV-001", dto.getInvoiceNumber());
        assertEquals(InvoiceType.PRODUCT_PURCHASE, dto.getType());
        assertEquals(InvoiceStatus.PAID, dto.getStatus());
    }

    @Test
    void invoiceItemResponseDTO_BuilderPattern_Success() {
        InvoiceItemResponseDTO dto = InvoiceItemResponseDTO.builder()
                .id(1L)
                .description("Item de factura")
                .quantity(2)
                .unitPrice(new BigDecimal("30000"))
                .amount(new BigDecimal("60000"))
                .build();

        assertEquals("Item de factura", dto.getDescription());
        assertEquals(2, dto.getQuantity());
        assertEquals(new BigDecimal("60000"), dto.getAmount());
    }
}