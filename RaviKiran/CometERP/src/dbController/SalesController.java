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
public class SalesController {
    /**
     * Sales
     */
    public static boolean setdatatoSales(String SaleDate, String CustomerId) {
        boolean setdata = db.setdata("INSERT INTO Sales(SaleDate,CustomerId,Updated_on,Dated_on,Timestamp)"
                + "VALUES('" + SaleDate + "','" + CustomerId + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");
        return setdata ? true : false;
    }

    public static boolean UpdatedatatoSales(String id, String SaleDate, String CustomerId) {
        boolean setdata = db.setdata("Update Sales SET SaleDate='" + SaleDate + "',CustomerId='" + CustomerId + "',Updated_on='" + Fun_Date() + "',Timestamp='" + Fun_Timestamp() + "'WHERE SaleId='" + id + "'");
        return setdata ? true : false;
    }

    public static ResultSet getSales() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM Sales");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ResultSet getSales(String id) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM Sales WHERE Saleid='" + id + "'");
        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static int checkgetSales(String id) {
        int count = 0;
        try {
            ResultSet executeQuery = db.getconnection().createStatement().executeQuery("SELECT count(*) as counter FROM Sales WHERE Saleid='" + id + "'");
            count = executeQuery.getInt("counter");
            executeQuery.close();
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public static int getMaxSaleId() {
        int maxid = 0;
        try {
            ResultSet data = db.getdata("Select max(Saleid) as maxid From Sales");
            maxid = data.getInt("maxid");
            data.close();
        } catch (SQLException ex) { 
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maxid;
    }

    public static ResultSet getSalesWithCustomerName() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM Sales INNER JOIN Customer ON sales.Customerid = customer.id order by SaleDate desc");
        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Sales> getSalesObservableList() {
        ObservableList<Sales> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getSalesWithCustomerName();
            while (data.next()) {
                people.add(new Sales(data.getString("Saleid"), data.getString("Name"), data.getString("SaleDate")));
            }
            data.close();
        } catch (SQLException ex) { 
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static boolean deleteSales(String Invoiceid) {
        db.setdata("delete from mytranscation where invoiceid = '" + Invoiceid + "'");
        db.setdata("delete from salepayment where saleid = '" + Invoiceid + "'");
        db.setdata("delete from saledelivery where saleid = '" + Invoiceid + "'");
        boolean setdata = db.setdata("Delete FROM Sales WHERE Saleid='" + Invoiceid + "'");
        return setdata;
    }

    public static ResultSet getCustomerNameFromInvoiceId(String Invoiceid) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT Name,sales.customerid as custid,saleDate FROM Sales INNER JOIN Customer on customer.id = Sales.Customerid WHERE Sales.Saleid='" + Invoiceid + "'");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static boolean updateCustomerInSales(String InvoiceId, String newCustomerID) {
        boolean setdata = db.setdata("UPDATE sales SET Customerid='" + newCustomerID + "' WHERE saleid='" + InvoiceId + "'");
        updateCustomerinMytranscation(InvoiceId, newCustomerID);
        updateCustomerinSalePayment(InvoiceId, newCustomerID);
        updateCustomerinSaleDelivery(InvoiceId, newCustomerID);

        return setdata;
    }

    public static boolean updateInvoiceDateInSales(String InvoiceId, String newDate) {
        boolean setdata = db.setdata("UPDATE sales SET SaleDate='" + newDate + "' WHERE saleid='" + InvoiceId + "'");
        updateInvoiceDateinSalePayment(InvoiceId, newDate);
        updateInvoiceDateinSaleDelivery(InvoiceId, newDate);
        return setdata;
    }

    /**
     * mytranscation
     */
    public static boolean setdatatomyTranscation(String StockId, String CustomerId, String InvoiceId, String StockTitle, String StockHSN, String StockCost, String StockPrice, String Discount, String StockCGST, String StockSGST, String StockGST, String Quantity, String Total) {
        boolean setdata = db.setdata("INSERT INTO mytranscation(StockId,CustomerId,InvoiceId,StockTitle,StockHSN,StockCost,StockPrice,Discount,StockCGST,StockSGST,StockGST,Quantity,Total,Updated_on,Dated_on,timestamp)"
                + "VALUES('" + StockId + "','" + CustomerId + "','" + InvoiceId + "','" + StockTitle + "','" + StockHSN + "','" + StockCost + "','" + StockPrice + "','" + Discount + "','" + StockCGST + "','" + StockSGST + "','" + StockGST + "','" + Quantity + "','" + Total + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");
       
        return setdata;
    }

    public static boolean updatedatatomyTranscation(String id, String StockId, String CustomerId, String InvoiceId, String StockTitle, String StockHSN, String StockCost, String StockPrice, String Discount, String StockCGST, String StockSGST, String StockGST, String Quantity, String Total) {
        boolean setdata = db.setdata("UPDATE mytranscation SET StockId='" + StockId + "',CustomerId='" + CustomerId + "',InvoiceId = '" + InvoiceId + "',StockTitle='" + StockTitle + "',StockHSN='" + StockHSN + "',StockCost='" + StockCost + "',StockPrice='" + StockPrice + "',Discount='" + Discount + "',StockCGST='" + StockCGST + "',StockSGST='" + StockSGST + "',StockGST='" + StockGST + "',Quantity='" + Quantity + "',Total='" + Total + "',Updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE transid = '" + id + "'");

        return setdata;
    }

    public static boolean deleteFromTranscation(String TransId) {
        boolean setdata = db.setdata("DELETE FROM myTranscation WHERE transid = '" + TransId + "'");
        return setdata;
    }

    public static ResultSet getmyTranscation() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM myTranscation");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ResultSet getmyTranscation(String id) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM myTranscation WHERE Transid='" + id + "'");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ResultSet getmyTranscationByInvoieid(String id) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM myTranscation WHERE InvoiceId='" + id + "'");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<mytranscation> getmytranscationObservableList() {
        ObservableList<mytranscation> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getmyTranscation();
            while (data.next()) {

                people.add(new mytranscation(data.getString("Transid"), data.getString("StockId"), data.getString("CustomerId"), data.getString("InvoiceId"), data.getString("StockTitle"), data.getString("StockHSN"), data.getString("StockCost"), data.getString("StockPrice"), data.getString("Discount"), data.getString("StockCGST"), data.getString("StockSGST"), data.getString("StockGST"), data.getString("Quantity"), data.getString("Total")));
//               new mytranscation(data.getString("Id"), data.getString("StockTitle"), data.getString("StockDescription"), data.getString("StockHSN"), data.getString("StockCost"), data.getString("StockPrice"), data.getString("StockRemain"), data.getString("StockSold")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static ObservableList<mytranscation> getmytranscationObservableList(String transid) {
        ObservableList<mytranscation> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getmyTranscation(transid);
            while (data.next()) {
                people.add(new mytranscation(data.getString("transid"), data.getString("StockId"), data.getString("CustomerId"), data.getString("InvoiceId"), data.getString("StockTitle"), data.getString("StockHSN"), data.getString("StockCost"), data.getString("StockPrice"), data.getString("Discount"), data.getString("StockCGST"), data.getString("StockSGST"), data.getString("StockGST"), data.getString("Quantity"), data.getString("Total")));
//               new mytranscation(data.getString("Id"), data.getString("StockTitle"), data.getString("StockDescription"), data.getString("StockHSN"), data.getString("StockCost"), data.getString("StockPrice"), data.getString("StockRemain"), data.getString("StockSold")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static ObservableList<mytranscation> getmytranscationByInvoiceIdObservableList(String InvoiceId) {
        ObservableList<mytranscation> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getmyTranscationByInvoieid(InvoiceId);
            while (data.next()) {
                people.add(new mytranscation(data.getString("transid"), data.getString("StockId"), data.getString("CustomerId"), data.getString("InvoiceId"), data.getString("StockTitle"), data.getString("StockHSN"), data.getString("StockCost"), data.getString("StockPrice"), data.getString("Discount"), data.getString("StockCGST"), data.getString("StockSGST"), data.getString("StockGST"), data.getString("Quantity"), data.getString("Total")));
//               new mytranscation(data.getString("Id"), data.getString("StockTitle"), data.getString("StockDescription"), data.getString("StockHSN"), data.getString("StockCost"), data.getString("StockPrice"), data.getString("StockRemain"), data.getString("StockSold")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static boolean updateCustomerinMytranscation(String InvoiceId, String newCustomerID) {
        boolean setdata = db.setdata("Update myTranscation set Customerid='" + newCustomerID + "' Where invoiceid ='" + InvoiceId + "'");
        return setdata;
    }

    /**
     * gst Mytranscations table
     */
    public static ObservableList<tbgsttable> getgstdata(String InvoiceId) {
        ObservableList<tbgsttable> people23 = FXCollections.observableArrayList();
        try {
            ResultSet data = db.getdata("SELECT StockHSN,Stockgst/2 as subgstpercentage,sum(TOTAL) as taxable,SUM((TOTAL/100)*STOCKGST) AS fullGstTotal,SUM((TOTAL/100)*STOCKGST)/2 AS halfgst,Stockgst as gst FROM myTranscation WHERE invoiceid = '" + InvoiceId + "' GROUP BY StockHSN");

            while (data.next()) {
                people23.add(new tbgsttable(data.getString("StockHSN"), data.getString("taxable"), data.getString("subgstpercentage"), data.getString("halfgst"), data.getString("subgstpercentage"), data.getString("halfgst"), data.getString("gst"), data.getString("fullGstTotal")));
            }

            data.close();

//                String HSN,cgstpercentage,cgst,sgstpercentage,sgst,gstpercentage,gst;
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(people23);
        return people23;
    }

    public static ResultSet getFinalCalculationsInvoice(String InvoiceId) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT StockHSN,Stockgst/2 as subgstpercentage,sum(TOTAL) as taxable,SUM((TOTAL/100)*STOCKGST) AS fullGstTotal,SUM((TOTAL/100)*STOCKGST)/2 AS halfgst,Stockgst as gst FROM myTranscation WHERE InvoiceId='" + InvoiceId + "'");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Sales Payment
     */
    public static boolean setdatatoSalePayment(String Saleid, String SaleDate, String CustomerId, String PaymentStatus, String PaymentType, String PaymentRemark, String PaymentDate) {
        boolean setdata = db.setdata("INSERT INTO SalePayment(Saleid,SaleDate,CustomerId,PaymentStatus,PaymentType,PaymentRemark,PaymentDate,Updated_on,Dated_on,timestamp)"
                + "VALUES('" + Saleid + "','" + SaleDate + "','" + CustomerId + "','" + PaymentStatus + "','" + PaymentType + "','" + PaymentRemark + "','" + PaymentDate + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");
       return setdata;
    }

    public static boolean updatedatatoSalePayment(String id, String Saleid, String SaleDate, String CustomerId, String PaymentStatus, String PaymentType, String PaymentRemark, String PaymentDate) {
        boolean setdata = db.setdata("UPDATE SalePayment SET Saleid='" + Saleid + "',SaleDate='" + SaleDate + "',CustomerId='" + CustomerId + "',PaymentStatus='" + PaymentStatus + "',PaymentType='" + PaymentType + "',PaymentRemark='" + PaymentRemark + "',PaymentDate='" + PaymentDate + "',Updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE SalePaymentDeliveryId = '" + id + "'");
        return setdata;
    }

    public static boolean updatedatatoSaleOPaymentUsingInvoiceId(String Saleid, String SaleDate, String CustomerId, String PaymentStatus, String PaymentType, String PaymentRemark, String PaymentDate) {
        boolean setdata = db.setdata("UPDATE SalePayment SET SaleDate='" + SaleDate + "',CustomerId='" + CustomerId + "',PaymentStatus='" + PaymentStatus + "',PaymentType='" + PaymentType + "',PaymentRemark='" + PaymentRemark + "',PaymentDate='" + PaymentDate + "',Updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE Saleid = '" + Saleid + "'");
        return setdata;
    }

    public static int checkSalePayment(String id) {
        int count = 0;
        try {
            ResultSet executeQuery = db.getconnection().createStatement().executeQuery("SELECT count(*) as counter FROM SalePayment WHERE Saleid='" + id + "'");
            count = executeQuery.getInt("counter");
            executeQuery.close();
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public static ResultSet getSalePayment() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM SalePayment");
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ResultSet getSalePayment(String id) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM SalePayment WHERE Saleid='" + id + "'");
        }  catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean updateCustomerinSalePayment(String InvoiceId, String newCustomerID) {
        boolean setdata = db.setdata("Update SalePayment set customerid = '" + newCustomerID + "' WHERE saleid= '" + InvoiceId + "'");
        return setdata;
    }

    public static boolean updateInvoiceDateinSalePayment(String InvoiceId, String newDate) {
        boolean setdata = db.setdata("Update SalePayment set saleDate = '" + newDate + "' WHERE saleid= '" + InvoiceId + "'");
        return setdata;
    }

    /**
     * Delivery
     */
    public static boolean setdatatoSaleDelivery(String Saleid, String SaleDate, String CustomerId, String DeliveryStatus, String DeliveryType, String DeliveryRemark, String DeliveryDate) {
        boolean setdata = db.setdata("INSERT INTO SaleDelivery(Saleid,SaleDate,CustomerId,DeliveryStatus,DeliveryType,DeliveryRemark,DeliveryDate,Updated_on,Dated_on,timestamp)"
                + "VALUES('" + Saleid + "','" + SaleDate + "','" + CustomerId + "','" + DeliveryStatus + "','" + DeliveryType + "','" + DeliveryRemark + "','" + DeliveryDate + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");
        return setdata;
    }

    public static boolean updatedatatoSaleDelivery(String id, String Saleid, String SaleDate, String CustomerId, String DeliveryStatus, String DeliveryType, String DeliveryRemark, String DeliveryDate) {
        boolean setdata = db.setdata("UPDATE SaleDelivery SET Saleid='" + Saleid + "',SaleDate='" + SaleDate + "',CustomerId='" + CustomerId + "',DeliveryStatus='" + DeliveryStatus + "',DeliveryType='" + DeliveryType + "',DeliveryRemark='" + DeliveryRemark + "',DeliveryDate='" + DeliveryDate + "',Updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE SaleDeliveryDeliveryId = '" + id + "'");
        return setdata;
    }

    public static boolean updatedatatoSaleODeliveryUsingInvoiceId(String Saleid, String SaleDate, String CustomerId, String DeliveryStatus, String DeliveryType, String DeliveryRemark, String DeliveryDate) {
        boolean setdata = db.setdata("UPDATE SaleDelivery SET SaleDate='" + SaleDate + "',CustomerId='" + CustomerId + "',DeliveryStatus='" + DeliveryStatus + "',DeliveryType='" + DeliveryType + "',DeliveryRemark='" + DeliveryRemark + "',DeliveryDate='" + DeliveryDate + "',Updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE Saleid = '" + Saleid + "'");
        return setdata;
    }

    public static int checkSaleDelivery(String id) {
        int count = 0;
        try {
            ResultSet executeQuery = db.getconnection().createStatement().executeQuery("SELECT count(*) as counter FROM SaleDelivery WHERE Saleid='" + id + "'");
            count = executeQuery.getInt("counter");
            executeQuery.close();
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public static ResultSet getSaleDelivery() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM SaleDelivery");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ResultSet getSaleDelivery(String id) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM SaleDelivery WHERE Saleid='" + id + "'");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean updateCustomerinSaleDelivery(String InvoiceId, String newCustomerID) {
        boolean setdata = db.setdata("Update SaleDelivery set customerid = '" + newCustomerID + "' WHERE saleid= '" + InvoiceId + "'");
        return setdata;
    }

    public static boolean updateInvoiceDateinSaleDelivery(String InvoiceId, String newDate) {
        boolean setdata = db.setdata("Update SaleDelivery set saleDate = '" + newDate + "' WHERE saleid= '" + InvoiceId + "'");
        return setdata;
    }
}
