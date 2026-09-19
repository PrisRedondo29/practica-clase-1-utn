package com.legacy.invoice;

import org.apache.log4j.Logger;

import java.math.BigDecimal;

/**
 * Validates invoice business rules.
 * This class does NOT use JAXB and should NOT be modified during migration.
 * It serves as a control to verify that migration does not break business logic.
 */
public class InvoiceValidator {

    private static final Logger logger = Logger.getLogger(InvoiceValidator.class);

    /**
     * Validates that an invoice meets all business rules.
     * @param invoice The invoice to validate
     * @return true if valid, false otherwise
     */
    public static boolean validate(Invoice invoice) {
        if (invoice == null) {
            logger.warn("Validation failed: Invoice is null");
            return false;
        }

        logger.info("Validating invoice: " + invoice.getId());

        // Rule 1: Invoice must have an ID
        if (invoice.getId() == null || invoice.getId().trim().isEmpty()) {
            logger.warn("Invoice has no ID");
            return false;
        }

        // Rule 2: Invoice must have items
        if (invoice.getItems() == null || invoice.getItems().isEmpty()) {
            logger.warn("Invoice has no items");
            return false;
        }

        // Rule 3: Validate total amount against line items
        BigDecimal calculatedTotal = BigDecimal.ZERO;
        for (InvoiceItem item : invoice.getItems()) {
            if (item == null || item.getLineTotal() == null) {
                logger.warn("Item has no line total: " + (item != null ? item.getDescription() : "null item"));
                return false;
            }
            calculatedTotal = calculatedTotal.add(item.getLineTotal());
        }

        if (invoice.getTotalAmount() == null) {
            logger.warn("Invoice has no total amount");
            return false;
        }

        // Compare with tolerance for floating point precision
        BigDecimal tolerance = new BigDecimal("0.01");
        if (calculatedTotal.subtract(invoice.getTotalAmount()).abs().compareTo(tolerance) > 0) {
            logger.warn("Total mismatch. Calculated: " + calculatedTotal + ", Declared: " + invoice.getTotalAmount());
            return false;
        }

        logger.info("Invoice validation PASSED: " + invoice.getId());
        return true;
    }
}
