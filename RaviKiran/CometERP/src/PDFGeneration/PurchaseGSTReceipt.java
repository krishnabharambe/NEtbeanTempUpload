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

public class PurchaseGSTReceipt {

    String BusinessName, BusinessAddress, BusinessContact, BusinessGST, BusinessPanTan, BusinessEmail, BusinessTelephone;
    String BankName, AcNo, BankIFSC;

    String Taxable, Halfgst, Fullgst, lasttotal;

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

    public PurchaseGSTReceipt(String InvoiceId) {
        try {
            final String Destination = getpath(InvoiceId);
            File file = new File(Destination);
            file.getParentFile().mkdirs();
            createPdf(Destination, InvoiceId);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GSTINV.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(GSTINV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getpath(String varcaseid) {
        String desktopPath = System.getProperty("user.home") + "/Documents";
        System.out.print(desktopPath.replace("\\", "/"));
        Instant instant = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        String bpath1 = instant.toString().replace("-", "").replace(":", "");
        Date date66 = new Date(System.currentTimeMillis());
        String datenew = date66.toString().replace("-", "").replace(":", "");
        return desktopPath.replace("\\", "/") + "/CometREP/report/PurchaseGSTReceipt/Purchase Receipt " + varcaseid + " " + datenew + " " + bpath1 + ".pdf";
    }

    public static void main(String[] args) {
        new GSTINV("4");
    }

    private void createPdf(String destination, String Saleid) throws FileNotFoundException, DocumentException {
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
            PdfPCell tb1cell1 = new PdfPCell(new Paragraph("GOODS PURCHASE RECEIPT", H12B));
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
            /**
             * adding border
             */
            PdfPTable tbBorder = new PdfPTable(1);
            tbBorder.setWidthPercentage(100);
            tbBorder.setExtendLastRow(true);
            PdfPCell tbborderCell = new PdfPCell();
            tbBorder.addCell(tbborderCell);

            float[] tbowner1 = new float[]{30f, 70f};
            PdfPTable tableOwner = new PdfPTable(tbowner1);
            tableOwner.getDefaultCell().setBorder(0);
            tableOwner.getDefaultCell().setMinimumHeight(70);

//            ImageIcon(getClass().getResource("/erp2/PDFGeneration/logo23.png"));
            String current2;
            current2 = new File(".").getCanonicalPath();
            current2 = current2.replace('\\', '/');
            Image image1 = Image.getInstance(current2 + "/" + "logo.png");
            tableOwner.addCell(image1);
            PdfPTable tbownerpart1;

            tbownerpart1 = new PdfPTable(1);
            tbownerpart1.setWidthPercentage(100);
            tbownerpart1.getDefaultCell().setBorder(0);
            int checker2 = 0;
            ResultSet data22 = db.getdata("SELECT count(*) as checker FROM BusinessDetail");
            checker2 = data22.getInt("checker");
            data22.close();
            String businessName, businessAddress, businessEmail, businessContact, businessTelephone, businessGSTIN, businessPANTAN;
            if (checker2 > 0) {
                ResultSet data222 = db.getdata("SELECT * from BusinessDetail WHERE BusinessId='1'");
                if (!data222.getString("BusinessName").equals("")) {
                    businessName = data222.getString("BusinessName");
                    PdfPCell cellbusinessname = new PdfPCell(new Paragraph(businessName.toUpperCase(), H13B));
                    cellbusinessname.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cellbusinessname.setBorder(0);
                    tbownerpart1.addCell(cellbusinessname);

                } else {
                    businessName = "No Name";
                }

                if (!data222.getString("BusinessAddress").equals("")) {
                    businessAddress = data222.getString("BusinessAddress");
                    PdfPCell cellbusinessAddress = new PdfPCell(new Paragraph(businessAddress, H10));
                    cellbusinessAddress.setBorder(0);
                    cellbusinessAddress.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tbownerpart1.addCell(cellbusinessAddress);
                } else {
                    businessAddress = "";
                }

                if (!data222.getString("BusinessEmail").equals("")) {
                    businessEmail = data222.getString("BusinessEmail");
                } else {
                    businessEmail = "";
                }

                if (!data222.getString("BusinessContact").equals("")) {
                    businessContact = data222.getString("BusinessContact");
                    PdfPCell cellBusinessContact = new PdfPCell(new Paragraph(businessContact + " / " + businessEmail, H10));
                    cellBusinessContact.setBorder(0);
                    cellBusinessContact.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tbownerpart1.addCell(cellBusinessContact);
                } else {
                    businessContact = "";
                }

                if (!data222.getString("BusinessGSTIN").equals("")) {
                    businessGSTIN = data222.getString("BusinessGSTIN");
                    PdfPCell cellbusinessGSTIN = new PdfPCell(new Paragraph("GSTIN : " + businessGSTIN, H10));
                    cellbusinessGSTIN.setBorder(0);
                    cellbusinessGSTIN.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tbownerpart1.addCell(cellbusinessGSTIN);
                } else {
                    businessGSTIN = "";
                }
                if (!data222.getString("BusinessPanTan").equals("")) {
                    businessPANTAN = data222.getString("BusinessPanTan");
                    PdfPCell cellbusinessPANTAN = new PdfPCell(new Paragraph(businessPANTAN, H10));
                    cellbusinessPANTAN.setBorder(0);
                    cellbusinessPANTAN.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tbownerpart1.addCell(cellbusinessPANTAN);
                } else {
                    businessPANTAN = "";
                }

                if (!data222.getString("BusinessTelephone").equals("")) {
                    businessTelephone = data222.getString("BusinessTelephone");
                    PdfPCell cellbusinessTelephone = new PdfPCell(new Paragraph(businessTelephone, H10));
                    cellbusinessTelephone.setBorder(0);
                    cellbusinessTelephone.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tbownerpart1.addCell(cellbusinessTelephone);
                } else {
                    businessPANTAN = "";
                }
                data222.close();
            }
            tableOwner.addCell(tbownerpart1);
            tbBorder.addCell(tableOwner);

            //bill to 
            PdfPTable tablebillto = new PdfPTable(2);
            tablebillto.setWidthPercentage(100);
            tablebillto.getDefaultCell().setMinimumHeight(80);
            tablebillto.getDefaultCell().setBorder(0);
            PdfPTable tablecustomerinfo = new PdfPTable(1);
            /////customer info
            String Name, Address, Email, Contact, GSTIN, Telephone, PANTAN;
            ResultSet data222 = db.getdata("SELECT name as name,address as address,Contact as contact,Email as Email,GSTIN as GSTIN,pantan as pantan from Supplier INEER JOIN Purchase ON id = Purchase.supplierid WHERE Purchase.PurchaseId =  '" + Saleid + "'");
            if (data222.getString("Name")!= null) {
                Name = data222.getString("Name");
                PdfPCell cellname = new PdfPCell(new Paragraph(Name.toUpperCase(), H11B));
                cellname.setBorder(0);
                tablecustomerinfo.addCell(cellname);
            } else {
                Name = "No Name";
            }

            if (!data222.getString("Address").equals("")) {
                Address = data222.getString("Address");
                PdfPCell cellAddress = new PdfPCell(new Paragraph(Address, H9));
                cellAddress.setBorder(0);
                tablecustomerinfo.addCell(cellAddress);
            } else {
                Address = "";
            }

            if (data222.getString("Email")!=null) {
                Email = " / " + data222.getString("Email");
            } else {
                Email = "";
            }

            if (!data222.getString("Contact").equals("")) {
                Contact = data222.getString("Contact");
                PdfPCell cellContact = new PdfPCell(new Paragraph(Contact + Email, H9));
                cellContact.setBorder(0);

                tablecustomerinfo.addCell(cellContact);
            } else {
                Contact = "";
            }

            if (!data222.getString("GSTIN").equals("")) {
                GSTIN = data222.getString("GSTIN");
                PdfPCell cellGSTIN = new PdfPCell(new Paragraph("GSTIN : " + GSTIN, H9));
                cellGSTIN.setBorder(0);

                tablecustomerinfo.addCell(cellGSTIN);
            } else {
                GSTIN = "";
            }
            if (!data222.getString("PanTan").equals("")) {
                PANTAN = data222.getString("PanTan");
                PdfPCell cellPANTAN = new PdfPCell(new Paragraph(PANTAN, H9));
                cellPANTAN.setBorder(0);
                tablecustomerinfo.addCell(cellPANTAN);
            } else {
                PANTAN = "";
            }

            data222.close();

            tablebillto.addCell(tablecustomerinfo);

            //InvoiceInfo
            PdfPTable invoiceinfo = new PdfPTable(2);
            invoiceinfo.getDefaultCell().setBorder(0);
            ResultSet data = db.getdata("Select * from Purchase Where Purchaseid = '" + Saleid + "'");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
            String nowyear = LocalDate.now().format(formatter);
            invoiceinfo.addCell(new Paragraph("Purchase Id : RHE/P/" + Saleid, H10));
            invoiceinfo.addCell(new Paragraph("Purchase Date : " + data.getString("Purchasedate").replace("-", "/"), H10));
            data.close();
            int paymentchecker = 0;
            ResultSet data1 = db.getdata("select count(*) as checker FROM PurchasePayment WHERE Purchaseid = '" + Saleid + "'");
            paymentchecker = data1.getInt("checker");
            data1.close();
            if (paymentchecker > 0) {
                String aboutpayment, expecteddate, status;
                ResultSet data2 = db.getdata("Select * from PurchasePayment WHERE Purchaseid = '" + Saleid + "'");
                aboutpayment = data2.getString("paymentremark");
                expecteddate = data2.getString("paymentdate");
                status = data2.getString("paymentstatus");
                invoiceinfo.addCell(new Paragraph("Payment Remark : " + aboutpayment, H9));

                if (status.equals("Completed")) {
                    invoiceinfo.addCell(new Paragraph("Payment Date : " + expecteddate.replace("-", "/"), H10));
                } else {
                    invoiceinfo.addCell(new Paragraph("Expected Date : " + expecteddate.replace("-", "/"), H10));
                }
                data2.close();
            }

            int deliverychecker = 0;
            ResultSet data12 = db.getdata("select count(*) as checker FROM PurchaseDelivery WHERE Purchaseid = '" + Saleid + "'");
            deliverychecker = data12.getInt("checker");
            data12.close();
            if (deliverychecker > 0) {
                String aboutpayment, expecteddate, status;
                ResultSet data2 = db.getdata("Select * from PurchaseDelivery WHERE Purchaseid = '" + Saleid + "'");
                aboutpayment = data2.getString("deliveryremark");
                expecteddate = data2.getString("deliverydate");
                status = data2.getString("deliverystatus");
                invoiceinfo.addCell(new Paragraph("Delivery Remark : " + aboutpayment, H9));
                if (status.equals("Completed")) {
                    invoiceinfo.addCell(new Paragraph("Delivery Date : " + expecteddate.replace("-", "/"), H10));
                } else {
                    invoiceinfo.addCell(new Paragraph("Expected Date : " + expecteddate.replace("-", "/"), H10));
                }
                data2.close();
            }

            tablebillto.addCell(invoiceinfo);
            tbBorder.addCell(tablebillto);

//            transcations
            float[] columnWidthstb8 = new float[]{12f, 150f, 35f, 40f, 25f, 25f, 50f};
//            head tags
//            also calculating gst
//            doingGstoutside(LedgerId, saleBillNo);
            PdfPTable tbtrans = new PdfPTable(columnWidthstb8);
            PdfPCell srno = new PdfPCell(new Paragraph("  "));
            srno.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
            tbtrans.addCell(srno);
            PdfPCell description = new PdfPCell(new Paragraph(" Description Of Goods ", H9B));
            description.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
            tbtrans.addCell(description);
            PdfPCell HSN = new PdfPCell(new Paragraph(" HSN ", H9B));
            HSN.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
            HSN.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbtrans.addCell(HSN);
            PdfPCell Rate = new PdfPCell(new Paragraph(" Rate ", H9B));
            Rate.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
            Rate.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbtrans.addCell(Rate);
            PdfPCell Dis = new PdfPCell(new Paragraph(" Dis% ", H9B));
            Dis.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
            Dis.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbtrans.addCell(Dis);
            PdfPCell Qty = new PdfPCell(new Paragraph(" Qty ", H9B));
            Qty.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
            Qty.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbtrans.addCell(Qty);
            PdfPCell Amount = new PdfPCell(new Paragraph(" Amount ", H9B));
            Amount.setBorder(Rectangle.BOTTOM);
            Amount.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbtrans.addCell(Amount);
//            data tags
            int erbocheck = 1;
            int newlinechecker = 0;
            PdfPCell newsrno, newNameOnInvoice, newHSN, newGST, newQuantity, newRate, newDiscount, newAmount;
            ResultSet data121 = db.getdata("SELECT StockTitle,stockHSN as HSN,stockprice as price,discount,stockgst,quantity,total FROM myPTranscation WHERE invoiceid = '" + Saleid + "'");

            while (data121.next()) {

                newsrno = new PdfPCell(new Paragraph(String.valueOf(erbocheck), H10));
                newsrno.setBorder(Rectangle.RIGHT);
                newsrno.setHorizontalAlignment(Element.ALIGN_CENTER);
                tbtrans.addCell(newsrno);

                newlinechecker += countLines(data121.getString("StockTitle"));

                newNameOnInvoice = new PdfPCell(new Paragraph(data121.getString("StockTitle"), H10));
                newNameOnInvoice.setBorder(Rectangle.RIGHT);
                tbtrans.addCell(newNameOnInvoice);

                newHSN = new PdfPCell(new Paragraph(data121.getString("HSN"), H10));
                newHSN.setBorder(Rectangle.RIGHT);
                newHSN.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tbtrans.addCell(newHSN);

                newRate = new PdfPCell(new Paragraph(String.format("%.2f", data121.getDouble("price") + 0.000), H10));
                newRate.setHorizontalAlignment(Element.ALIGN_RIGHT);
                newRate.setBorder(Rectangle.RIGHT);
                tbtrans.addCell(newRate);

                newDiscount = new PdfPCell(new Paragraph(String.format("%.2f", data121.getDouble("discount") + 0.000), H10));
                newDiscount.setBorder(Rectangle.RIGHT);
                newDiscount.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tbtrans.addCell(newDiscount);

                newQuantity = new PdfPCell(new Paragraph(String.valueOf(data121.getInt("Quantity")), H10));
                newQuantity.setHorizontalAlignment(Element.ALIGN_RIGHT);
                newQuantity.setBorder(Rectangle.RIGHT);
                tbtrans.addCell(newQuantity);

                newAmount = new PdfPCell(new Paragraph(String.format("%.2f", (data121.getDouble("total")) + 0.000), H10));
                newAmount.setHorizontalAlignment(Element.ALIGN_RIGHT);
                newAmount.setBorder(0);
                tbtrans.addCell(newAmount);
                erbocheck++;
            }
            data121.close();
            /**
             * **************************************
             */

            while (newlinechecker <= 21) {

                newsrno = new PdfPCell(new Paragraph(" ", H10));
                newsrno.setBorder(Rectangle.RIGHT);
                newsrno.setHorizontalAlignment(Element.ALIGN_CENTER);
                tbtrans.addCell(newsrno);

                newNameOnInvoice = new PdfPCell(new Paragraph(" ", H10));
                newNameOnInvoice.setBorder(Rectangle.RIGHT);
                tbtrans.addCell(newNameOnInvoice);

                newHSN = new PdfPCell(new Paragraph(" ", H10));
                newHSN.setBorder(Rectangle.RIGHT);
                newHSN.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tbtrans.addCell(newHSN);

                newQuantity = new PdfPCell(new Paragraph(" ", H10));
                newQuantity.setHorizontalAlignment(Element.ALIGN_RIGHT);
                newQuantity.setBorder(Rectangle.RIGHT);
                tbtrans.addCell(newQuantity);

                newRate = new PdfPCell(new Paragraph(" ", H10));
                newRate.setHorizontalAlignment(Element.ALIGN_RIGHT);
                newRate.setBorder(Rectangle.RIGHT);
                tbtrans.addCell(newRate);

                newDiscount = new PdfPCell(new Paragraph(" ", H10));
                newDiscount.setBorder(Rectangle.RIGHT);
                newDiscount.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tbtrans.addCell(newDiscount);

                newAmount = new PdfPCell(new Paragraph(" ", H10));
                newAmount.setHorizontalAlignment(Element.ALIGN_RIGHT);
                newAmount.setBorder(0);
                tbtrans.addCell(newAmount);
                newlinechecker++;

            }

            ResultSet data45 = db.getdata("SELECT stockHSN as HSN,sum(TOTAL) as taxable,stockgst as gst,(stockgst/2) as subgstpercentage,SUM((TOTAL/100)*STOCKGST) as totalgst,SUM((TOTAL/100)*STOCKGST)/2 as halfgst FROM myPTranscation WHERE invoiceid = '" + Saleid + "'");

            //Subtotal
            newsrno = new PdfPCell(new Paragraph(" ", H10));
            newsrno.setBorder(Rectangle.RIGHT);
            newsrno.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbtrans.addCell(newsrno);

            newNameOnInvoice = new PdfPCell(new Paragraph(" ", H10));
            newNameOnInvoice.setBorder(Rectangle.RIGHT);
            tbtrans.addCell(newNameOnInvoice);

            newHSN = new PdfPCell(new Paragraph(" ", H10));
            newHSN.setBorder(Rectangle.RIGHT);
            newHSN.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbtrans.addCell(newHSN);

            newQuantity = new PdfPCell(new Paragraph(" ", H10));
            newQuantity.setHorizontalAlignment(Element.ALIGN_RIGHT);
            newQuantity.setBorder(Rectangle.RIGHT);
            tbtrans.addCell(newQuantity);

            newRate = new PdfPCell(new Paragraph(" ", H10));
            newRate.setHorizontalAlignment(Element.ALIGN_RIGHT);
            newRate.setBorder(Rectangle.RIGHT);
            tbtrans.addCell(newRate);

            newDiscount = new PdfPCell(new Paragraph("Taxable", H10));
            newDiscount.setBorder(Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
            newDiscount.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbtrans.addCell(newDiscount);

            newAmount = new PdfPCell(new Paragraph(String.format("%.2f", data45.getDouble("taxable") + 0.000), H10));
            newAmount.setHorizontalAlignment(Element.ALIGN_RIGHT);
            newAmount.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
            tbtrans.addCell(newAmount);

            //cgst
            newsrno = new PdfPCell(new Paragraph(" ", H10));
            newsrno.setBorder(Rectangle.RIGHT);
            newsrno.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbtrans.addCell(newsrno);

            newNameOnInvoice = new PdfPCell(new Paragraph(" ", H10));
            newNameOnInvoice.setBorder(Rectangle.RIGHT);
            tbtrans.addCell(newNameOnInvoice);

            newHSN = new PdfPCell(new Paragraph(" ", H10));
            newHSN.setBorder(Rectangle.RIGHT);
            newHSN.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbtrans.addCell(newHSN);

            newQuantity = new PdfPCell(new Paragraph(" ", H10));
            newQuantity.setHorizontalAlignment(Element.ALIGN_RIGHT);
            newQuantity.setBorder(Rectangle.RIGHT);
            tbtrans.addCell(newQuantity);

            newRate = new PdfPCell(new Paragraph(" ", H10));
            newRate.setHorizontalAlignment(Element.ALIGN_RIGHT);
            newRate.setBorder(Rectangle.RIGHT);
            tbtrans.addCell(newRate);

            newDiscount = new PdfPCell(new Paragraph("SGST 9%", H8));
            newDiscount.setBorder(Rectangle.RIGHT);
            newDiscount.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbtrans.addCell(newDiscount);

            newAmount = new PdfPCell(new Paragraph(String.format("%.2f", data45.getDouble("halfgst") + 0.00), H10));
            newAmount.setHorizontalAlignment(Element.ALIGN_RIGHT);
            newAmount.setBorder(0);
            tbtrans.addCell(newAmount);

            //SGST
            newsrno = new PdfPCell(new Paragraph(" ", H10));
            newsrno.setBorder(Rectangle.RIGHT);
            newsrno.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbtrans.addCell(newsrno);

            newNameOnInvoice = new PdfPCell(new Paragraph(" ", H10));
            newNameOnInvoice.setBorder(Rectangle.RIGHT);
            tbtrans.addCell(newNameOnInvoice);

            newHSN = new PdfPCell(new Paragraph(" ", H10));
            newHSN.setBorder(Rectangle.RIGHT);
            newHSN.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbtrans.addCell(newHSN);

            newQuantity = new PdfPCell(new Paragraph(" ", H10));
            newQuantity.setHorizontalAlignment(Element.ALIGN_RIGHT);
            newQuantity.setBorder(Rectangle.RIGHT);
            tbtrans.addCell(newQuantity);

            newRate = new PdfPCell(new Paragraph(" ", H10));
            newRate.setHorizontalAlignment(Element.ALIGN_RIGHT);
            newRate.setBorder(Rectangle.RIGHT);
            tbtrans.addCell(newRate);

            newDiscount = new PdfPCell(new Paragraph("CGST 9%", H8));
            newDiscount.setBorder(Rectangle.RIGHT);
            newDiscount.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbtrans.addCell(newDiscount);

            newAmount = new PdfPCell(new Paragraph(String.format("%.2f", data45.getDouble("halfgst") + 0.00), H10));
            newAmount.setHorizontalAlignment(Element.ALIGN_RIGHT);
            newAmount.setBorder(0);
            tbtrans.addCell(newAmount);

            //final total
            newsrno = new PdfPCell(new Paragraph(" ", H10));
            newsrno.setBorder(Rectangle.RIGHT);
            newsrno.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbtrans.addCell(newsrno);

            newNameOnInvoice = new PdfPCell(new Paragraph(" ", H10));
            newNameOnInvoice.setBorder(Rectangle.RIGHT);
            tbtrans.addCell(newNameOnInvoice);

            newHSN = new PdfPCell(new Paragraph(" ", H10));
            newHSN.setBorder(Rectangle.RIGHT);
            newHSN.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbtrans.addCell(newHSN);

            newQuantity = new PdfPCell(new Paragraph(" ", H10));
            newQuantity.setHorizontalAlignment(Element.ALIGN_RIGHT);
            newQuantity.setBorder(Rectangle.RIGHT);
            tbtrans.addCell(newQuantity);

            newRate = new PdfPCell(new Paragraph(" ", H10));
            newRate.setHorizontalAlignment(Element.ALIGN_RIGHT);
            newRate.setBorder(Rectangle.RIGHT);
            tbtrans.addCell(newRate);

            newDiscount = new PdfPCell(new Paragraph("Total", H10B));
            newDiscount.setBorder(Rectangle.RIGHT | Rectangle.TOP);
            newDiscount.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbtrans.addCell(newDiscount);

            newAmount = new PdfPCell(new Paragraph(String.format("%.2f", data45.getDouble("taxable") + data45.getDouble("totalgst") + 0.000), H10B));
            newAmount.setHorizontalAlignment(Element.ALIGN_RIGHT);
            newAmount.setBorder(Rectangle.TOP);
            tbtrans.addCell(newAmount);

            tbBorder.addCell(tbtrans);
//          end o9f transcation
//Total payable in words

            PdfPTable tbpw = new PdfPTable(1);
            tbpw.getDefaultCell().setBorder(0);
            tbpw.setWidthPercentage(100);

//            System.out.println("Payable : " + String.format("%.2f", data45.getDouble("taxable") + data45.getDouble("totalgst")+0.00));
//            tbpw.addCell(new Paragraph("Payable : " + pdfGeneration.EnglishNumberTranslator.convert(dFormat.format(data45.getDouble("taxable") + data45.getDouble("totalgst") + 0.00)), H10));
            tbpw.addCell(new Paragraph("Payable : " + Currency.convertToIndianCurrency(df2.format(data45.getDouble("taxable") + data45.getDouble("totalgst") + 0.00)), H10));

            tbBorder.addCell(tbpw);
            data45.close();
//          start of gst table
            float[] columnWidthstb6 = new float[]{40f, 50f, 30f, 35f, 30f, 35f, 50f, 150f};
            PdfPTable tbGST = new PdfPTable(columnWidthstb6);
            tbGST.setWidthPercentage(100);
            PdfPCell classcell, taxableamount, SGST, CGST, totalGST;

            classcell = new PdfPCell(new Paragraph("HSN", H9B));
            classcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            classcell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
            tbGST.addCell(classcell);

            taxableamount = new PdfPCell(new Paragraph("TAXABLE", H9B));
            taxableamount.setHorizontalAlignment(Element.ALIGN_CENTER);
            taxableamount.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
            tbGST.addCell(taxableamount);

            SGST = new PdfPCell(new Paragraph("SGST%", H9B));
            SGST.setHorizontalAlignment(Element.ALIGN_CENTER);
            SGST.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
            tbGST.addCell(SGST);

            SGST = new PdfPCell(new Paragraph("SGST", H9B));
            SGST.setHorizontalAlignment(Element.ALIGN_CENTER);
            SGST.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
            tbGST.addCell(SGST);

            CGST = new PdfPCell(new Paragraph("CGST%", H9B));
            CGST.setHorizontalAlignment(Element.ALIGN_CENTER);
            CGST.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
            tbGST.addCell(CGST);

            CGST = new PdfPCell(new Paragraph("CGST", H9B));
            CGST.setHorizontalAlignment(Element.ALIGN_CENTER);
            CGST.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
            tbGST.addCell(CGST);

            totalGST = new PdfPCell(new Paragraph("TOTAL GST", H9B));
            totalGST.setHorizontalAlignment(Element.ALIGN_CENTER);
            totalGST.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
            tbGST.addCell(totalGST);

            ResultSet data39 = db.getdata("SELECT * FROM BusinessAccount WHERE id = '1'");
            String bankAccountNo = "", bankIFSC = "", bankBranch = "", UPIID = "", gpay = "";

            if (!data39.getString("businessbankaccount").trim().equals("")) {
                bankAccountNo = "A\\C No. : " + data39.getString("businessbankaccount").trim();
            } else {
                bankAccountNo = "A\\C No. : " + "No Account found";
            }

            if (!data39.getString("businessBankIFSC").trim().equals("")) {
                bankIFSC = "\nIFSC : " + data39.getString("businessBankIFSC").trim();
            } else {
                bankIFSC = "A\\C No. : " + "No bankIFSC found";
            }

            if (!data39.getString("businessbankbranch").trim().equals("")) {
                bankBranch = "\nBank & Branch : " + data39.getString("businessbankbranch").trim();
            } else {
                bankBranch = "\nBank & Branch : " + "No bankBranch found";
            }

            data39.close();
            totalGST = new PdfPCell(new Paragraph("Bank Details \n" + bankAccountNo + bankIFSC + bankBranch, H10B));
            totalGST.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            totalGST.setBorder(0);
            totalGST.setRowspan(100);
            tbGST.addCell(totalGST);
            int countrow = 0;
//            ResultSet data43 = db.getdata("SELECT HSN,sum(((myTranscation.price-(myTranscation.price/100)*discount)*quantity)) as taxable,gst,(gst/2) as subgstpercentage,sum(((((myTranscation.price-(myTranscation.price/100)*discount)/100)*gst)*quantity)) as totalgst,sum(((((myTranscation.price-(myTranscation.price/100)*discount)/100)*gst)*quantity)/2) as halfgst FROM myTranscation INNER JOIN Stock ON myTranscation.StockId = Stock.StockId WHERE myTranscation.SaleId = '" + Saleid + "' GROUP BY HSN");
            ResultSet data43 = db.getdata("SELECT stockHSN as HSN,sum(TOTAL) as taxable,stockgst as gst,(stockgst/2) as subgstpercentage,SUM((TOTAL/100)*STOCKGST) as totalgst,SUM((TOTAL/100)*STOCKGST)/2 as halfgst FROM myPTranscation WHERE invoiceid = '" + Saleid + "' GROUP BY stockhsn");

            while (data43.next()) {
                classcell = new PdfPCell(new Paragraph(data43.getString("HSN"), H9));
                classcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                classcell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
                tbGST.addCell(classcell);

                taxableamount = new PdfPCell(new Paragraph(String.format("%.2f", data43.getDouble("taxable") + 0.00), H9));
                taxableamount.setHorizontalAlignment(Element.ALIGN_RIGHT);
                taxableamount.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
                tbGST.addCell(taxableamount);

                SGST = new PdfPCell(new Paragraph(String.format("%.2f", data43.getDouble("subgstpercentage") + 0.00), H9));
                SGST.setHorizontalAlignment(Element.ALIGN_RIGHT);
                SGST.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
                tbGST.addCell(SGST);

                SGST = new PdfPCell(new Paragraph(String.format("%.2f", data43.getDouble("halfgst") + 0.00), H9));
                SGST.setHorizontalAlignment(Element.ALIGN_RIGHT);
                SGST.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
                tbGST.addCell(SGST);

                CGST = new PdfPCell(new Paragraph(String.format("%.2f", data43.getDouble("subgstpercentage") + 0.00), H9));
                CGST.setHorizontalAlignment(Element.ALIGN_RIGHT);
                CGST.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
                tbGST.addCell(CGST);

                CGST = new PdfPCell(new Paragraph(String.format("%.2f", data43.getDouble("halfgst") + 0.00), H9));
                CGST.setHorizontalAlignment(Element.ALIGN_RIGHT);
                CGST.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
                tbGST.addCell(CGST);

                totalGST = new PdfPCell(new Paragraph(String.format("%.2f", data43.getDouble("totalgst") + 0.00), H9));
                totalGST.setHorizontalAlignment(Element.ALIGN_RIGHT);
                totalGST.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
                tbGST.addCell(totalGST);
                countrow = countrow + 1;
            }
            System.out.println(countrow);
            while (countrow <= 4) {
                tbGST.getDefaultCell().setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
                tbGST.addCell(" ");
                tbGST.addCell(" ");
                tbGST.addCell(" ");
                tbGST.addCell(" ");
                tbGST.addCell(" ");
                tbGST.addCell(" ");
                tbGST.addCell(" ");
                countrow = countrow + 1;
            }
            data43.close();
//            ResultSet data44 = db.getdata("SELECT HSN,sum(((myTranscation.price-(myTranscation.price/100)*discount)*quantity)) as taxable,gst,(gst/2) as subgstpercentage,sum(((((myTranscation.price-(myTranscation.price/100)*discount)/100)*gst)*quantity)) as totalgst,sum(((((myTranscation.price-(myTranscation.price/100)*discount)/100)*gst)*quantity)/2) as halfgst FROM myTranscation INNER JOIN Stock ON myTranscation.StockId = Stock.StockId WHERE myTranscation.SaleId = '" + Saleid + "'");
            ResultSet data44 = db.getdata("SELECT stockHSN as HSN,sum(TOTAL) as taxable,stockgst as gst,(stockgst/2) as subgstpercentage,SUM((TOTAL/100)*STOCKGST) as totalgst,SUM((TOTAL/100)*STOCKGST)/2 as halfgst FROM myPTranscation WHERE invoiceid = '" + Saleid + "'");

            tbGST.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbGST.getDefaultCell().setBorder(Rectangle.RIGHT | Rectangle.TOP);
            tbGST.addCell(new Paragraph("Total(Rs.)", H9B));
            tbGST.addCell(new Paragraph(String.format("%.2f", data44.getDouble("taxable") + 0.00), H9B));
            Taxable = data44.getString("taxable");
            tbGST.addCell(new Paragraph(" ", H9B));
            tbGST.addCell(new Paragraph(String.format("%.2f", data44.getDouble("halfgst") + 0.00), H9B));
            Halfgst = data44.getString("halfgst");
            tbGST.addCell(new Paragraph(" ", H9B));
            tbGST.addCell(new Paragraph(String.format("%.2f", data44.getDouble("halfgst") + 0.00), H9B));
            Fullgst = data44.getString("totalgst");
            tbGST.addCell(new Paragraph(String.format("%.2f", data44.getDouble("totalgst") + 0.00), H9B));

            tbBorder.addCell(tbGST);
//            End of gst table
//            PdfPTable tbpw1 = new PdfPTable(1);
//            tbpw1.getDefaultCell().setBorder(0);
//            tbpw1.setWidthPercentage(100);
//            tbpw1.addCell(new Paragraph("GST : " + EnglishNoTranslator.convert(String.format("%.2f", data44.getDouble("totalgst"))), H9));
//            tbBorder.addCell(tbpw1);

            PdfPTable tbpw1 = new PdfPTable(1);
            tbpw1.getDefaultCell().setBorder(0);
            tbpw1.setWidthPercentage(100);

//            System.out.println("Total GST :" + pdfGeneration.EnglishNumberTranslator.convert(df2.format(data44.getDouble("totalgst"))));
//            tbpw1.addCell(new Paragraph("Total GST : " + pdfGeneration.EnglishNumberTranslator.convert(dFormat.format(data44.getDouble("totalgst"))), H10));
            tbpw1.addCell(new Paragraph("Total GST : " + Currency.convertToIndianCurrency(df2.format(data44.getDouble("totalgst") + 0.00)), H10));

            tbBorder.addCell(tbpw1);
            data44.close();

            PdfPTable termscondition = new PdfPTable(2);
            termscondition.getDefaultCell().setMinimumHeight(50);
            termscondition.getDefaultCell().setBorder(0);

            termscondition.setWidthPercentage(100);
            tbBorder.getDefaultCell().setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
            PdfPTable k = new PdfPTable(1);
            k.getDefaultCell().setBorder(Rectangle.RIGHT);

            PdfPCell cellmaintc = new PdfPCell(new Paragraph("Terms & Condition", H10B));
            cellmaintc.setBorder(Rectangle.RIGHT);
            k.addCell(cellmaintc);
            PdfPCell celltc;
            ResultSet data3 = db.getdata("SELECT * FROM Purchaseterms ORDER BY id");
            while (data3.next()) {
                celltc = new PdfPCell(new Paragraph(data3.getString("description"), H9));
                celltc.setBorder(Rectangle.RIGHT);
                k.addCell(celltc);
            }
            data3.close();

            PdfPCell cellcseal = new PdfPCell(new Paragraph("\nCustomer Seal & Signature", H9B));
            cellcseal.setBorder(Rectangle.RIGHT);
            cellcseal.setHorizontalAlignment(Element.ALIGN_RIGHT);
            k.addCell(cellcseal);
            termscondition.addCell(k);
            PdfPTable lasttb = new PdfPTable(1);
            lasttb.getDefaultCell().setBorder(0);
            lasttb.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            lasttb.addCell(new Paragraph("FOR RAVIKIRAN ENTERPRISES", H9B));
            lasttb.addCell(new Paragraph("\n\nAuthorised Signatury", H9));
            termscondition.addCell(lasttb);
            tbBorder.addCell(termscondition);

            document.add(tbBorder);
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
