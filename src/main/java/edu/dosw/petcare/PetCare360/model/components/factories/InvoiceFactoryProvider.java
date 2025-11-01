package edu.dosw.petcare.PetCare360.model.components.factories;

import edu.dosw.petcare.PetCare360.model.entities.InvoiceType;
import org.springframework.stereotype.Component;

@Component
public class InvoiceFactoryProvider {

    public InvoiceFactory getFactory(InvoiceType type) {
        switch (type) {
            case MEDICAL_CONSULTATION:
                return new MedicalConsultationInvoiceFactory();
            case PRODUCT_PURCHASE:
                return new ProductPurchaseInvoiceFactory();
            default:
                throw new IllegalArgumentException("Unknown invoice type: " + type);
        }
    }
}
