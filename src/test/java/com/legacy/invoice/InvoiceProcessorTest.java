package com.legacy.invoice;

import org.junit.Before;
import org.junit.Test;

import jakarta.xml.bind.JAXBException;
import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Tests for InvoiceProcessor.
 * This class imports javax.xml.bind (legacy), which will fail when migrating to Java 21 until updated to jakarta.xml.bind.
 */
public class InvoiceProcessorTest {

    private String validInvoicePath;
    private String invalidInvoicePath;

    @Before
    public void setUp() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        
        URL validUrl = classLoader.getResource("valid-invoice.xml");
        URL invalidUrl = classLoader.getResource("invalid-invoice.xml");
        
        // Use URI to safely handle paths with spaces and special characters across platforms
        if (validUrl != null) {
            validInvoicePath = new File(validUrl.toURI()).getAbsolutePath();
        } else {
            validInvoicePath = "src/test/resources/valid-invoice.xml";
        }

        if (invalidUrl != null) {
            invalidInvoicePath = new File(invalidUrl.toURI()).getAbsolutePath();
        } else {
            invalidInvoicePath = "src/test/resources/invalid-invoice.xml";
        }
    }

    @Test
    public void testProcessValidInvoice() throws JAXBException {
        Invoice invoice = InvoiceProcessor.processInvoice(validInvoicePath);
        
        assertNotNull("Invoice should not be null", invoice);
        assertEquals("Invoice ID should match", "INV-TEST-001", invoice.getId());
        assertNotNull("Items list should not be null", invoice.getItems());
        assertEquals("Invoice should have 2 items", 2, invoice.getItems().size());
        assertNotNull("Total amount should not be null", invoice.getTotalAmount());
    }

    @Test
    public void testProcessInvalidInvoicePath() {
        String nonExistentPath = "target/non-existent-invoice-" + System.currentTimeMillis() + ".xml";
        
        try {
            InvoiceProcessor.processInvoice(nonExistentPath);
            fail("Should throw JAXBException for non-existent file");
        } catch (JAXBException e) {
            assertNotNull("Exception should have been thrown", e);
        }
    }
}
