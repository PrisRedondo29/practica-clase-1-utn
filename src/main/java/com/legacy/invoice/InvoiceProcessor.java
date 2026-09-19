package com.legacy.invoice;

import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Main processor for reading and parsing invoices from XML files.
 * This class uses JAXB (javax.xml.bind) which will fail in Java 21 unless migrated.
 */
public class InvoiceProcessor {

    private static final Logger logger = Logger.getLogger(InvoiceProcessor.class);

    /**
     * Processes an invoice XML file and returns an Invoice object.
     * @param xmlFilePath Path to the XML file
     * @return Parsed Invoice object
     * @throws JAXBException If parsing fails
     */
    public static Invoice processInvoice(String xmlFilePath) throws JAXBException {
        logger.info("Processing invoice file: " + xmlFilePath);

        try {
            File file = new File(xmlFilePath);
            if (!file.exists()) {
                throw new JAXBException("File not found: " + xmlFilePath);
            }

            JAXBContext context = JAXBContext.newInstance(Invoice.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Invoice invoice = (Invoice) unmarshaller.unmarshal(file);

            logger.info("Invoice processed successfully: " + invoice.getId());
            return invoice;
        } catch (JAXBException e) {
            logger.error("Failed to process invoice: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Failed to process invoice: " + e.getMessage(), e);
            throw new JAXBException("Error processing invoice XML: " + e.getMessage(), e);
        }
    }

    /**
     * Command-line entry point for processing invoices.
     * Usage: java com.legacy.invoice.InvoiceProcessor <xml-file>
     */
    public static void main(String[] args) throws JAXBException {
        if (args == null || args.length == 0) {
            System.err.println("Usage: java com.legacy.invoice.InvoiceProcessor <xml-file>");
            System.exit(1);
        }

        String xmlFile = args[0];
        Invoice invoice = processInvoice(xmlFile);

        System.out.println("\n" + InvoiceReportGenerator.generateReport(invoice));
    }
}
