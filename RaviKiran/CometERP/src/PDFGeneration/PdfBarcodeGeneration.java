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
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.db;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
public class PdfBarcodeGeneration {

    private static DecimalFormat df2 = new DecimalFormat(".##");
//                tbpw1.addCell(new Paragraph("Total GST :" + pdfGeneration.EnglishNumberTranslator.convert(dFormat.format(data44.getDouble("totalgst"))), H10));
    DecimalFormat dFormat = new DecimalFormat("####,###,###.##");

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

    public PdfBarcodeGeneration(String name, int Noof) {
        try {
            final String Destination = getpath(name);
            File file = new File(Destination);
            file.getParentFile().mkdirs();
            createPdf(Destination, name, Noof);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PdfBarcodeGeneration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfBarcodeGeneration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getpath(String varcaseid) {
        String desktopPath = System.getProperty("user.home") + "/Documents";
        System.out.print(desktopPath.replace("\\", "/"));
        Instant instant = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        String bpath1 = instant.toString().replace("-", "").replace(":", "");
        Date date66 = new Date(System.currentTimeMillis());
        String datenew = date66.toString().replace("-", "").replace(":", "");
        return desktopPath.replace("\\", "/") + "/CometREP/report/Barcode/Barcode " + varcaseid + " " + datenew + " " + bpath1 + ".pdf";
    }

    private void createPdf(String destination, String name, int noof) throws FileNotFoundException, DocumentException {

        Document document = new Document(PageSize.A4);
        PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(destination));
        document.open();

        PdfPTable p = new PdfPTable(6);
        p.getDefaultCell().setBorder(0);
        p.getDefaultCell().setPaddingLeft(5);
        p.setWidthPercentage(100);
        PdfPCell c1 = new PdfPCell();
         PdfContentByte pdfContentByte = instance.getDirectContent();
        for (int i = 1; i <= noof; i++) {

            Barcode128 barcode128 = new Barcode128();
            barcode128.setCode(name);
            barcode128.setCodeType(Barcode128.CODE128);
            Image code128Image = barcode128.createImageWithBarcode(pdfContentByte, null, null);
            code128Image.scalePercent(100);
            p.addCell(code128Image);
        }
        document.add(p);

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
        } catch (IOException ex) {
            Logger.getLogger(PdfBarcodeGeneration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(PdfBarcodeGeneration.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static void main(String[] args) {
        new PdfBarcodeGeneration("Krishna",50);
    }
}
