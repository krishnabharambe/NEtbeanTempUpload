/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PDFGeneration;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.db;
import static dbController.ProductionController.getAllInfoOfProduction;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class ProductionPrint {

    Font H16 = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL, BaseColor.BLACK);
    Font H16B = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
    Font H15 = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.NORMAL, BaseColor.BLACK);
    Font H15B = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.BLACK);
    Font H14 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.BLACK);
    Font H14B = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);
    Font H13 = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.NORMAL, BaseColor.BLACK);
    Font H13B = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD, BaseColor.BLACK);
    Font H12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
    Font H12B = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
    Font H11 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL, BaseColor.BLACK);
    Font H11B = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD, BaseColor.BLACK);
    Font H10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
    Font H10B = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.BLACK);
    Font H9 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL, BaseColor.BLACK);
    Font H9B = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD, BaseColor.BLACK);
    Font H8 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
    Font H8B = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD, BaseColor.BLACK);

    public ProductionPrint(String productionid) {
        try {
            final String Destination = getpath(productionid);
            File file = new File(Destination);
            file.getParentFile().mkdirs();
            createPdf(Destination, productionid);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProductionPrint.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(ProductionPrint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getpath(String varcaseid) {
        String desktopPath = System.getProperty("user.home") + "/Documents";
        System.out.print(desktopPath.replace("\\", "/"));
        Instant instant = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        String bpath1 = instant.toString().replace("-", "").replace(":", "");
        Date date66 = new Date(System.currentTimeMillis());
        String datenew = date66.toString().replace("-", "").replace(":", "");
        return desktopPath.replace("\\", "/") + "/CometREP/report/Production/Production " + varcaseid + " " + datenew + " " + bpath1 + ".pdf";
    }

    public static void main(String[] args) {
        new ProductionPrint("1");
    }

    private void createPdf(String destination, String productionid) throws FileNotFoundException, DocumentException {
        try {

            float left = 30;
            float right = 30;
            float top = 20;
            float bottom = 20;
            /**
             * Document Creation
             */
            Document document = new Document(PageSize.A4, left, right, top, bottom);
            PdfWriter.getInstance(document, new FileOutputStream(destination));
            document.open();
            /**
             * setting margin to document
             */
            document.setMargins(left, right, 0, bottom);
            PdfPTable tb1 = new PdfPTable(1);
            tb1.getDefaultCell().setBorder(0);

            /**
             * adding header
             */
            PdfPCell tb1cell1 = new PdfPCell(new Paragraph("PRODUCTION", H12B));
            tb1.setWidthPercentage(100);
            tb1cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            tb1cell1.setVerticalAlignment(Element.ALIGN_TOP);
            tb1cell1.setBorder(0);
            tb1.addCell(tb1cell1);
            document.add(tb1);

            /* sepatotor */
            PdfPTable tbsep = new PdfPTable(1);
            tbsep.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            tbsep.setWidthPercentage(100);
            tbsep.getDefaultCell().setMinimumHeight(5);
            tbsep.addCell("");
            document.add(tbsep);

            ResultSet allInfoOfProduction = getAllInfoOfProduction(productionid);
            float[] columnWidthstb8 = new float[]{20f, 40f, 35f, 35f, 35f, 35f, 35f};

            PdfPTable pTable = new PdfPTable(columnWidthstb8);

            PdfPCell pCellid = new PdfPCell(new Paragraph("Production Batch No : " + allInfoOfProduction.getString("productionid"), H11B));
            pCellid.setColspan(3);
            pTable.addCell(pCellid);

            PdfPCell pCell = new PdfPCell(new Paragraph("Production Date : " + allInfoOfProduction.getString("productiondate"), H11B));
            pCell.setColspan(4);
            pCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pTable.addCell(pCell);

            pTable.addCell(new Paragraph("Sr.No", H11B));
            pTable.addCell(new Paragraph("Material", H11B));
            pTable.addCell(new Paragraph("Wt/1", H11B));
            pTable.addCell(new Paragraph("Wt/2", H11B));
            pTable.addCell(new Paragraph("Wt/3", H11B));
            pTable.addCell(new Paragraph("Wt/4", H11B));
            pTable.addCell(new Paragraph("Total", H11B));

            pTable.addCell(new Paragraph("1", H11));
            pTable.addCell(new Paragraph("Raw", H11));
            pTable.addCell(new Paragraph(allInfoOfProduction.getString("raw1"), H11));
            pTable.addCell(new Paragraph(allInfoOfProduction.getString("raw2"), H11));
            pTable.addCell(new Paragraph(allInfoOfProduction.getString("raw3"), H11));
            pTable.addCell(new Paragraph(allInfoOfProduction.getString("raw4"), H11));
            pTable.addCell(new Paragraph(allInfoOfProduction.getString("rawtotal"), H11));

            pTable.addCell(new Paragraph("2", H11));
            pTable.addCell(new Paragraph("Calcium", H11));
            PdfPCell cellname = new PdfPCell(new Paragraph("Name : " + allInfoOfProduction.getString("name"), H11));
            cellname.setColspan(4);
            pTable.addCell(cellname);
            pTable.addCell(new Paragraph(allInfoOfProduction.getString("calciumtotal"), H11));

            pTable.addCell(new Paragraph("3", H11));
            pTable.addCell(new Paragraph("Titanium", H11));
            PdfPCell cellsize = new PdfPCell(new Paragraph("Size : " + allInfoOfProduction.getString("size"), H11));
            cellsize.setColspan(4);
            pTable.addCell(cellsize);
            pTable.addCell(new Paragraph(allInfoOfProduction.getString("titaniumtotal"), H11));

            pTable.addCell(new Paragraph("4", H11));
            pTable.addCell(new Paragraph("T.B.L.S", H11));
            PdfPCell cellpipes = new PdfPCell(new Paragraph("Pipes : " + allInfoOfProduction.getString("Pipes"), H11));
            cellpipes.setColspan(4);
            pTable.addCell(cellpipes);
            pTable.addCell(new Paragraph(allInfoOfProduction.getString("tblstotal"), H11));

            pTable.addCell(new Paragraph("5", H11));
            pTable.addCell(new Paragraph("Wax", H11));
            PdfPCell cellblank = new PdfPCell(new Paragraph(" ", H11));
            cellblank.setColspan(4);
            pTable.addCell(cellblank);
            pTable.addCell(new Paragraph(allInfoOfProduction.getString("waxtotal"), H11));

            pTable.addCell(new Paragraph("6", H11));
            pTable.addCell(new Paragraph("Nil", H11));
            pTable.addCell(cellblank);
            pTable.addCell(new Paragraph(allInfoOfProduction.getString("liltotal"), H11));

            pTable.addCell(new Paragraph(" ", H11B));
            pTable.addCell(new Paragraph("Total", H11B));
            pTable.addCell(cellblank);
            pTable.addCell(new Paragraph(allInfoOfProduction.getString("fulltotal"), H11B));
            Font font = new Font(H9);
            font.setColor(BaseColor.RED);
            PdfPCell newp = new PdfPCell(new Paragraph("All Measurement are in Kilogram.", font));
            newp.setBorder(0);
            newp.setColspan(7);
            newp.setHorizontalAlignment(Element.ALIGN_CENTER);
            pTable.addCell(newp);
            document.add(pTable);
            document.close();

            /* opening file if exists */
            try {
                if (new File(destination).exists()) {
                    System.out.println(destination);
                    String current;
                    current = new File(".").getCanonicalPath();
                    current = current.replace('\\', '/');
                    System.out.println(current + "/" + destination);
                    Process p99 = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + destination);
                    p99.waitFor();
                    String filepath = current + "/" + destination;
                    String[] filename = destination.split("/");
                    System.out.println(filename[1].trim());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println(e.toString());
            }
        } catch (SQLException ex) {
//            CommonFunctionProviderClass.EXP(ex);
            Logger.getLogger(GSTINV.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadElementException ex) {
//            CommonFunctionProviderClass.EXP(ex);
            Logger.getLogger(GSTINV.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            CommonFunctionProviderClass.EXP(ex);
            Logger.getLogger(GSTINV.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static int countLines(String s) {
        return (s + " ").split("\r?\n").length;
    }

}
