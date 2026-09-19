package com.legacy.invoice;

import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

/**
 * Generates text-based reports for invoices.
 * This class does NOT use JAXB and is fully compatible with Java 7+.
 */
public class InvoiceReportGenerator {

    private static final Logger logger = Logger.getLogger(InvoiceReportGenerator.class);

    /**
     * Generates a formatted text report of an invoice.
     * @param invoice The invoice to report on
     * @return A formatted text report
     */
    public static String generateReport(Invoice invoice) {
        if (invoice == null) {
            return "NO INVOICE DATA";
        }

        logger.info("Generating report for invoice: " + invoice.getId());

        StringBuilder report = new StringBuilder();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("0.00", symbols);
        String border = repeatChar('=', 60);
        String separator = repeatChar('-', 60);

        report.append(border).append("\n");
        report.append("INVOICE REPORT\n");
        report.append(border).append("\n");
        report.append("ID:          ").append(invoice.getId()).append("\n");
        report.append("Date:        ").append(invoice.getDate()).append("\n");
        report.append(separator).append("\n");
        report.append("Line Items:\n");
        report.append(separator).append("\n");

        if (invoice.getItems() != null) {
            for (int i = 0; i < invoice.getItems().size(); i++) {
                InvoiceItem item = invoice.getItems().get(i);
                if (item != null) {
                    String unitPriceStr = item.getUnitPrice() != null ? df.format(item.getUnitPrice()) : "0.00";
                    String lineTotalStr = item.getLineTotal() != null ? df.format(item.getLineTotal()) : "0.00";
                    int qty = item.getQuantity() != null ? item.getQuantity() : 0;

                    report.append(String.format("%2d. %-40s %s x %d = %s\n",
                            i + 1,
                            truncate(item.getDescription(), 40),
                            unitPriceStr,
                            qty,
                            lineTotalStr));
                }
            }
        }

        report.append(separator).append("\n");
        String totalAmountStr = invoice.getTotalAmount() != null ? df.format(invoice.getTotalAmount()) : "0.00";
        report.append("TOTAL AMOUNT:    ").append(totalAmountStr).append("\n");
        report.append(border).append("\n");

        return report.toString();
    }

    /**
     * Helper: repeat a character N times (Java 7 compatible).
     */
    private static String repeatChar(char c, int count) {
        char[] chars = new char[count];
        Arrays.fill(chars, c);
        return new String(chars);
    }

    /**
     * Helper: truncate string to max length.
     */
    private static String truncate(String s, int maxLength) {
        if (s == null) {
            return "";
        }
        if (s.length() <= maxLength) {
            return s;
        }
        return s.substring(0, maxLength - 3) + "...";
    }
}
