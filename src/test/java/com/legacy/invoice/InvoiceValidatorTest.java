package com.legacy.invoice;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for InvoiceValidator.
 * This class does NOT use JAXB, so it serves as a control test.
 * It must pass unchanged before and after migration.
 */
public class InvoiceValidatorTest {

    @Test
    public void testValidInvoice() {
        List<InvoiceItem> items = new ArrayList<InvoiceItem>();
        items.add(new InvoiceItem("Item 1", new BigDecimal("100.00"), 2, new BigDecimal("200.00")));
        items.add(new InvoiceItem("Item 2", new BigDecimal("50.00"), 3, new BigDecimal("150.00")));
        
        Invoice invoice = new Invoice("INV-001", "2024-01-01", items, new BigDecimal("350.00"));
        
        assertTrue("Valid invoice should pass validation", InvoiceValidator.validate(invoice));
    }

    @Test
    public void testInvalidInvoiceMismatchedTotal() {
        List<InvoiceItem> items = new ArrayList<InvoiceItem>();
        items.add(new InvoiceItem("Item 1", new BigDecimal("100.00"), 2, new BigDecimal("200.00")));
        
        Invoice invoice = new Invoice("INV-002", "2024-01-01", items, new BigDecimal("999.99"));
        
        assertFalse("Invoice with mismatched total should fail validation", InvoiceValidator.validate(invoice));
    }
}
