package com.legacy.invoice;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for InvoiceReportGenerator.
 * This class does NOT use JAXB and serves as a control test.
 * It must pass unchanged before and after migration.
 */
public class InvoiceReportGeneratorTest {

    @Test
    public void testGenerateReport() {
        List<InvoiceItem> items = new ArrayList<InvoiceItem>();
        items.add(new InvoiceItem("Test Item", new BigDecimal("100.00"), 2, new BigDecimal("200.00")));
        
        Invoice invoice = new Invoice("INV-REPORT-001", "2024-01-01", items, new BigDecimal("200.00"));
        
        String report = InvoiceReportGenerator.generateReport(invoice);
        
        assertNotNull("Report should not be null", report);
        assertTrue("Report should contain invoice ID", report.contains("INV-REPORT-001"));
        assertTrue("Report should contain date", report.contains("2024-01-01"));
        assertTrue("Report should contain item", report.contains("Test Item"));
        assertTrue("Report should contain total", report.contains("200.00"));
    }

    @Test
    public void testGenerateReportMultipleItems() {
        List<InvoiceItem> items = new ArrayList<InvoiceItem>();
        items.add(new InvoiceItem("Item 1", new BigDecimal("50.00"), 1, new BigDecimal("50.00")));
        items.add(new InvoiceItem("Item 2", new BigDecimal("75.00"), 2, new BigDecimal("150.00")));
        items.add(new InvoiceItem("Item 3", new BigDecimal("100.00"), 1, new BigDecimal("100.00")));
        
        Invoice invoice = new Invoice("INV-MULTI-001", "2024-01-05", items, new BigDecimal("300.00"));
        
        String report = InvoiceReportGenerator.generateReport(invoice);
        
        assertNotNull("Report should not be null", report);
        assertTrue("Report should contain Item 1", report.contains("Item 1"));
        assertTrue("Report should contain Item 2", report.contains("Item 2"));
        assertTrue("Report should contain Item 3", report.contains("Item 3"));
    }
}
