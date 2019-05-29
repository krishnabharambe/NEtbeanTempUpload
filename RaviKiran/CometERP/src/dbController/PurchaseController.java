/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbController;

import static Common.CFPC.Fun_Date;
import static Common.CFPC.Fun_Timestamp;
import database.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author admin
 */
public class PurchaseController {

    /**
     * Purchase
     */
    /**
     * *
     *
     * @param PurchaseDate
     * @param SupplierId
     * @return
     */
    public static boolean setdatatoPurchase(String PurchaseDate, String SupplierId) {
        boolean setdata = db.setdata("INSERT INTO Purchase(PurchaseDate,SupplierId,Updated_on,Dated_on,Timestamp)"
                + "VALUES('" + PurchaseDate + "','" + SupplierId + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");
        return setdata ? true : false;
    }

    /**
     * *
     *
     * @param id
     * @param PurchaseDate
     * @param SupplierId
     * @return
     */
    public static boolean UpdatedatatoPurchase(String id, String PurchaseDate, String SupplierId) {
        boolean setdata = db.setdata("Update Purchase SET PurchaseDate='" + PurchaseDate + "',SupplierId='" + SupplierId + "',Updated_on='" + Fun_Date() + "',Timestamp='" + Fun_Timestamp() + "'WHERE PurchaseId='" + id + "'");
        return setdata ? true : false;
    }

    /**
     * *
     *
     * @return
     */
    public static ResultSet getPurchase() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM Purchase");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * *
     *
     * @param id
     * @return
     */
    public static ResultSet getPurchase(String id) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM Purchase WHERE Purchaseid='" + id + "'");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /***
     * 
     * @param id
     * @return 
     */
    public static int checkgetPurchase(String id) {
        int count = 0;
        try {
            ResultSet executeQuery = db.getconnection().createStatement().executeQuery("SELECT count(*) as counter FROM Purchase WHERE Purchaseid='" + id + "'");
            count = executeQuery.getInt("counter");
            executeQuery.close();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }
    /***
     * 
     * @return 
     */
    public static int getMaxPurchaseId() {
        int maxid = 0;
        try {
            ResultSet data = db.getdata("Select max(Purchaseid) as maxid From Purchase");
            maxid = data.getInt("maxid");
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maxid;
    }

    public static ResultSet getPurchaseWithSupplierName() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM Purchase INNER JOIN Supplier ON Purchase.Supplierid = Supplier.id order by PurchaseDate desc");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Purchase> getPurchaseObservableList() {
        ObservableList<Purchase> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getPurchaseWithSupplierName();
            while (data.next()) {
                people.add(new Purchase(data.getString("Purchaseid"), data.getString("Name"), data.getString("PurchaseDate")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static boolean deletePurchase(String Invoiceid) {
        db.setdata("delete from mytranscation where invoiceid = '" + Invoiceid + "'");
        db.setdata("delete from Purchasepayment where Purchaseid = '" + Invoiceid + "'");
        db.setdata("delete from Purchasedelivery where Purchaseid = '" + Invoiceid + "'");
        boolean setdata = db.setdata("Delete FROM Purchase WHERE Purchaseid='" + Invoiceid + "'");
        return setdata;
    }

    public static ResultSet getSupplierNameFromInvoiceId(String Invoiceid) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT Name,Purchase.Supplierid as custid,PurchaseDate FROM Purchase INNER JOIN Supplier on Supplier.id = Purchase.Supplierid WHERE Purchase.Purchaseid='" + Invoiceid + "'");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static boolean updateSupplierInPurchase(String InvoiceId, String newSupplierID) {
        boolean setdata = db.setdata("UPDATE Purchase SET Supplierid='" + newSupplierID + "' WHERE Purchaseid='" + InvoiceId + "'");
        updateSupplierinMytranscation(InvoiceId, newSupplierID);
        updateSupplierinPurchasePayment(InvoiceId, newSupplierID);
        updateSupplierinPurchaseDelivery(InvoiceId, newSupplierID);

        return setdata;
    }

    public static boolean updateInvoiceDateInPurchase(String InvoiceId, String newDate) {
        boolean setdata = db.setdata("UPDATE Purchase SET PurchaseDate='" + newDate + "' WHERE Purchaseid='" + InvoiceId + "'");
        updateInvoiceDateinPurchasePayment(InvoiceId, newDate);
        updateInvoiceDateinPurchaseDelivery(InvoiceId, newDate);
        return setdata;
    }

    /**
     * mytranscation
     */
    public static boolean setdatatomyPTranscation(String StockId, String SupplierId, String InvoiceId, String StockTitle, String StockHSN, String StockCost, String StockPrice, String Discount, String StockCGST, String StockSGST, String StockGST, String Quantity, String Total) {
        boolean setdata = db.setdata("INSERT INTO myPtranscation(StockId,SupplierId,InvoiceId,StockTitle,StockHSN,StockCost,StockPrice,Discount,StockCGST,StockSGST,StockGST,Quantity,Total,Updated_on,Dated_on,timestamp)"
                + "VALUES('" + StockId + "','" + SupplierId + "','" + InvoiceId + "','" + StockTitle + "','" + StockHSN + "','" + StockCost + "','" + StockPrice + "','" + Discount + "','" + StockCGST + "','" + StockSGST + "','" + StockGST + "','" + Quantity + "','" + Total + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");

        return setdata;
    }

    public static boolean updatedatatomyPTranscation(String id, String StockId, String SupplierId, String InvoiceId, String StockTitle, String StockHSN, String StockCost, String StockPrice, String Discount, String StockCGST, String StockSGST, String StockGST, String Quantity, String Total) {
        boolean setdata = db.setdata("UPDATE myPtranscation SET StockId='" + StockId + "',SupplierId='" + SupplierId + "',InvoiceId = '" + InvoiceId + "',StockTitle='" + StockTitle + "',StockHSN='" + StockHSN + "',StockCost='" + StockCost + "',StockPrice='" + StockPrice + "',Discount='" + Discount + "',StockCGST='" + StockCGST + "',StockSGST='" + StockSGST + "',StockGST='" + StockGST + "',Quantity='" + Quantity + "',Total='" + Total + "',Updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE transid = '" + id + "'");

        return setdata;
    }

    public static boolean deleteFromTranscation(String TransId) {
        boolean setdata = db.setdata("DELETE FROM myPTranscation WHERE transid = '" + TransId + "'");
        return setdata;
    }

    public static ResultSet getmyPTranscation() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM myPTranscation");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ResultSet getmyPTranscation(String id) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM myPTranscation WHERE Transid='" + id + "'");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ResultSet getmyPTranscationByInvoieid(String id) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM myPTranscation WHERE InvoiceId='" + id + "'");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<mytranscation> getmyPtranscationObservableList() {
        ObservableList<mytranscation> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getmyPTranscation();
            while (data.next()) {

                people.add(new mytranscation(data.getString("Transid"), data.getString("StockId"), data.getString("SupplierId"), data.getString("InvoiceId"), data.getString("StockTitle"), data.getString("StockHSN"), data.getString("StockCost"), data.getString("StockPrice"), data.getString("Discount"), data.getString("StockCGST"), data.getString("StockSGST"), data.getString("StockGST"), data.getString("Quantity"), data.getString("Total")));
//               new mytranscation(data.getString("Id"), data.getString("StockTitle"), data.getString("StockDescription"), data.getString("StockHSN"), data.getString("StockCost"), data.getString("StockPrice"), data.getString("StockRemain"), data.getString("StockSold")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static ObservableList<mytranscation> getmyPtranscationObservableList(String transid) {
        ObservableList<mytranscation> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getmyPTranscation(transid);
            while (data.next()) {
                people.add(new mytranscation(data.getString("transid"), data.getString("StockId"), data.getString("SupplierId"), data.getString("InvoiceId"), data.getString("StockTitle"), data.getString("StockHSN"), data.getString("StockCost"), data.getString("StockPrice"), data.getString("Discount"), data.getString("StockCGST"), data.getString("StockSGST"), data.getString("StockGST"), data.getString("Quantity"), data.getString("Total")));
//               new mytranscation(data.getString("Id"), data.getString("StockTitle"), data.getString("StockDescription"), data.getString("StockHSN"), data.getString("StockCost"), data.getString("StockPrice"), data.getString("StockRemain"), data.getString("StockSold")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static ObservableList<myPtranscation> getmyPtranscationByInvoiceIdObservableList(String InvoiceId) {
        ObservableList<myPtranscation> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getmyPTranscationByInvoieid(InvoiceId);
            while (data.next()) {
                people.add(new myPtranscation(data.getString("transid"), data.getString("StockId"), data.getString("SupplierId"), data.getString("InvoiceId"), data.getString("StockTitle"), data.getString("StockHSN"), data.getString("StockCost"), data.getString("StockPrice"), data.getString("Discount"), data.getString("StockCGST"), data.getString("StockSGST"), data.getString("StockGST"), data.getString("Quantity"), data.getString("Total")));
//               new mytranscation(data.getString("Id"), data.getString("StockTitle"), data.getString("StockDescription"), data.getString("StockHSN"), data.getString("StockCost"), data.getString("StockPrice"), data.getString("StockRemain"), data.getString("StockSold")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static boolean updateSupplierinMytranscation(String InvoiceId, String newSupplierID) {
        boolean setdata = db.setdata("Update myPTranscation set Supplierid='" + newSupplierID + "' Where invoiceid ='" + InvoiceId + "'");
        return setdata;
    }

    /**
     * gst Mytranscations table
     */
    public static ObservableList<tbgsttable> getgstdata(String InvoiceId) {
        ObservableList<tbgsttable> people23 = FXCollections.observableArrayList();
        try {
            ResultSet data = db.getdata("SELECT StockHSN,Stockgst/2 as subgstpercentage,sum(TOTAL) as taxable,SUM((TOTAL/100)*STOCKGST) AS fullGstTotal,SUM((TOTAL/100)*STOCKGST)/2 AS halfgst,Stockgst as gst FROM myPTranscation WHERE invoiceid = '" + InvoiceId + "' GROUP BY StockHSN");

            while (data.next()) {
                people23.add(new tbgsttable(data.getString("StockHSN"), data.getString("taxable"), data.getString("subgstpercentage"), data.getString("halfgst"), data.getString("subgstpercentage"), data.getString("halfgst"), data.getString("gst"), data.getString("fullGstTotal")));
            }

            data.close();

//                String HSN,cgstpercentage,cgst,sgstpercentage,sgst,gstpercentage,gst;
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(people23);
        return people23;
    }

    public static ResultSet getFinalCalculationsInvoice(String InvoiceId) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT StockHSN,Stockgst/2 as subgstpercentage,sum(TOTAL) as taxable,SUM((TOTAL/100)*STOCKGST) AS fullGstTotal,SUM((TOTAL/100)*STOCKGST)/2 AS halfgst,Stockgst as gst FROM myPTranscation WHERE InvoiceId='" + InvoiceId + "'");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Purchase Payment
     */
    public static boolean setdatatoPurchasePayment(String Purchaseid, String PurchaseDate, String SupplierId, String PaymentStatus, String PaymentType, String PaymentRemark, String PaymentDate) {
        boolean setdata = db.setdata("INSERT INTO PurchasePayment(Purchaseid,PurchaseDate,SupplierId,PaymentStatus,PaymentType,PaymentRemark,PaymentDate,Updated_on,Dated_on,timestamp)"
                + "VALUES('" + Purchaseid + "','" + PurchaseDate + "','" + SupplierId + "','" + PaymentStatus + "','" + PaymentType + "','" + PaymentRemark + "','" + PaymentDate + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");
        return setdata;
    }

    public static boolean updatedatatoPurchasePayment(String id, String Purchaseid, String PurchaseDate, String SupplierId, String PaymentStatus, String PaymentType, String PaymentRemark, String PaymentDate) {
        boolean setdata = db.setdata("UPDATE PurchasePayment SET Purchaseid='" + Purchaseid + "',PurchaseDate='" + PurchaseDate + "',SupplierId='" + SupplierId + "',PaymentStatus='" + PaymentStatus + "',PaymentType='" + PaymentType + "',PaymentRemark='" + PaymentRemark + "',PaymentDate='" + PaymentDate + "',Updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE PurchasePaymentDeliveryId = '" + id + "'");
        return setdata;
    }

    public static boolean updatedatatoPurchaseOPaymentUsingInvoiceId(String Purchaseid, String PurchaseDate, String SupplierId, String PaymentStatus, String PaymentType, String PaymentRemark, String PaymentDate) {
        boolean setdata = db.setdata("UPDATE PurchasePayment SET PurchaseDate='" + PurchaseDate + "',SupplierId='" + SupplierId + "',PaymentStatus='" + PaymentStatus + "',PaymentType='" + PaymentType + "',PaymentRemark='" + PaymentRemark + "',PaymentDate='" + PaymentDate + "',Updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE Purchaseid = '" + Purchaseid + "'");
        return setdata;
    }

    public static int checkPurchasePayment(String id) {
        int count = 0;
        try {
            ResultSet executeQuery = db.getconnection().createStatement().executeQuery("SELECT count(*) as counter FROM PurchasePayment WHERE Purchaseid='" + id + "'");
            count = executeQuery.getInt("counter");
            executeQuery.close();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public static ResultSet getPurchasePayment() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM PurchasePayment");
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ResultSet getPurchasePayment(String id) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM PurchasePayment WHERE Purchaseid='" + id + "'");
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean updateSupplierinPurchasePayment(String InvoiceId, String newSupplierID) {
        boolean setdata = db.setdata("Update PurchasePayment set Supplierid = '" + newSupplierID + "' WHERE Purchaseid= '" + InvoiceId + "'");
        return setdata;
    }

    public static boolean updateInvoiceDateinPurchasePayment(String InvoiceId, String newDate) {
        boolean setdata = db.setdata("Update PurchasePayment set PurchaseDate = '" + newDate + "' WHERE Purchaseid= '" + InvoiceId + "'");
        return setdata;
    }

    /**
     * Delivery
     */
    public static boolean setdatatoPurchaseDelivery(String Purchaseid, String PurchaseDate, String SupplierId, String DeliveryStatus, String DeliveryType, String DeliveryRemark, String DeliveryDate) {
        boolean setdata = db.setdata("INSERT INTO PurchaseDelivery(Purchaseid,PurchaseDate,SupplierId,DeliveryStatus,DeliveryType,DeliveryRemark,DeliveryDate,Updated_on,Dated_on,timestamp)"
                + "VALUES('" + Purchaseid + "','" + PurchaseDate + "','" + SupplierId + "','" + DeliveryStatus + "','" + DeliveryType + "','" + DeliveryRemark + "','" + DeliveryDate + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");
        return setdata;
    }

    public static boolean updatedatatoPurchaseDelivery(String id, String Purchaseid, String PurchaseDate, String SupplierId, String DeliveryStatus, String DeliveryType, String DeliveryRemark, String DeliveryDate) {
        boolean setdata = db.setdata("UPDATE PurchaseDelivery SET Purchaseid='" + Purchaseid + "',PurchaseDate='" + PurchaseDate + "',SupplierId='" + SupplierId + "',DeliveryStatus='" + DeliveryStatus + "',DeliveryType='" + DeliveryType + "',DeliveryRemark='" + DeliveryRemark + "',DeliveryDate='" + DeliveryDate + "',Updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE PurchaseDeliveryDeliveryId = '" + id + "'");
        return setdata;
    }

    public static boolean updatedatatoPurchaseODeliveryUsingInvoiceId(String Purchaseid, String PurchaseDate, String SupplierId, String DeliveryStatus, String DeliveryType, String DeliveryRemark, String DeliveryDate) {
        boolean setdata = db.setdata("UPDATE PurchaseDelivery SET PurchaseDate='" + PurchaseDate + "',SupplierId='" + SupplierId + "',DeliveryStatus='" + DeliveryStatus + "',DeliveryType='" + DeliveryType + "',DeliveryRemark='" + DeliveryRemark + "',DeliveryDate='" + DeliveryDate + "',Updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE Purchaseid = '" + Purchaseid + "'");
        return setdata;
    }

    public static int checkPurchaseDelivery(String id) {
        int count = 0;
        try {
            ResultSet executeQuery = db.getconnection().createStatement().executeQuery("SELECT count(*) as counter FROM PurchaseDelivery WHERE Purchaseid='" + id + "'");
            count = executeQuery.getInt("counter");
            executeQuery.close();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public static ResultSet getPurchaseDelivery() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM PurchaseDelivery");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ResultSet getPurchaseDelivery(String id) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM PurchaseDelivery WHERE Purchaseid='" + id + "'");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean updateSupplierinPurchaseDelivery(String InvoiceId, String newSupplierID) {
        boolean setdata = db.setdata("Update PurchaseDelivery set Supplierid = '" + newSupplierID + "' WHERE Purchaseid= '" + InvoiceId + "'");
        return setdata;
    }

    public static boolean updateInvoiceDateinPurchaseDelivery(String InvoiceId, String newDate) {
        boolean setdata = db.setdata("Update PurchaseDelivery set PurchaseDate = '" + newDate + "' WHERE Purchaseid= '" + InvoiceId + "'");
        return setdata;
    }

}
