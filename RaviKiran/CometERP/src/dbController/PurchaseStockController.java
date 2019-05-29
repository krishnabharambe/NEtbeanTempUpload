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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author admin
 */
public class PurchaseStockController {
    public static boolean setPStock(String PStockTitle, String PStockDescription, String PStockHSN, String PStockCost, String PStockPrice, String PStockRemain, String PStockSold) {
        boolean setdata = db.setdata("INSERT INTO PStock (StockTitle,StockDescription,StockHSN,StockCost,StockPrice,StockRemain,StockSold,updated_on,dated_on,timestamp)"
                + "VALUES('" + PStockTitle + "','" + PStockDescription + "','" + PStockHSN + "','" + PStockCost + "','" + PStockPrice + "','" + PStockRemain + "','" + PStockSold + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");
        return setdata ? true : false;
    }

    public static boolean updatePStock(String id, String PStockTitle, String PStockDescription, String PStockHSN, String PStockCost, String PStockPrice, String PStockRemain, String PStockSold) {
        boolean setdata = db.setdata("UPDATE PStock SET StockTitle='" + PStockTitle + "',StockDescription='" + PStockDescription + "',StockHSN='" + PStockHSN + "',StockCost='" + PStockCost + "',StockPrice='" + PStockPrice + "',StockRemain='" + PStockRemain + "',StockSold='" + PStockSold + "',updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE id='" + id + "'");
        return setdata ? true : false;
    }

    public static ResultSet getPStock() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM PStock order by id desc");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseStockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseStockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ResultSet getPStock(String id) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM PStock WHERE id='" + id + "'");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseStockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseStockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean updatedatatoremainPStock(String id, String Quantity) {
        boolean setdata = db.setdata("UPDATE pSTOCK SET StockRemain='" + Quantity + "' WHERE id = '" + id + "'");

        return setdata ? true : false;
    }

    public static boolean adddatatoremainPStock(String id, String Quantity) {
        boolean setdata = db.setdata("UPDATE pSTOCK SET StockRemain=StockRemain+'" + Quantity + "' WHERE id = '" + id + "'");

        return setdata ? true : false;
    }

    public static boolean updatedatatoSoldPStock(String id, String Quantity) {
        boolean setdata = db.setdata("UPDATE pSTOCK SET StockSold='" + Quantity + "' WHERE id = '" + id + "'");

        return setdata ? true : false;
    }

    public static boolean addSoldPStockOnTranscation(String PStockid, String Quantity) {
        boolean setdata = db.setdata("UPDATE pSTOCK SET StockSold=StockSold+'" + Quantity + "' WHERE id = '" + PStockid + "'");
        boolean setdata2 = db.setdata("UPDATE STOCK SET StockRemain=StockRemain-'" + Quantity + "' WHERE id = '" + PStockid + "'");
        return setdata && setdata2;

    }
    
    public static boolean DeletePStock(String Stockid) {
        return db.setdata("Delete FROM pSTOCK WHERE id = '" + Stockid + "'");
    }

    public static boolean DeleteSoldPStockOnTranscation(String PStockid, String Quantity) {
        boolean setdata = db.setdata("UPDATE PSTOCK SET StockSold=StockSold-'" + Quantity + "' WHERE id = '" + PStockid + "'");
        boolean setdata2 = db.setdata("UPDATE PSTOCK SET StockRemain=StockRemain+'" + Quantity + "' WHERE id = '" + PStockid + "'");
        return setdata;
    }

    public static ObservableList<PStock> getPStockObservableList() {
        ObservableList<PStock> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getPStock();
            while (data.next()) {
                people.add(new PStock(data.getString("Id"), data.getString("StockTitle"), data.getString("StockDescription"), data.getString("StockHSN"), data.getString("StockCost"), data.getString("StockPrice"), data.getString("StockRemain"), data.getString("StockSold")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseStockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }
    
    public static String[] getAllPStockName() {
        try {

            ResultSet data = getPStock();
            final List<String> timeStr = new ArrayList<>();
            while (data.next()) {
                // Just get the value of the column, and add it to the list
                timeStr.add(data.getString("stocktitle"));
            }

            // I would return the list here, but let's convert it to an array
            return timeStr.toArray(new String[timeStr.size()]);
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseStockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getPStockIdFromName(String PStockName) {
        int counter = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as counter from PStock WHERE stockTitle ='" + PStockName + "'");
            counter = data.getInt("counter");
            data.close();
            if (counter > 0) {
                int dat2 = 0;
                ResultSet data2 = db.getdata("Select id as stockid from PStock WHERE stockTitle ='" + PStockName + "'");
                dat2 = data2.getInt("StockId");
                data2.close();
                return dat2 != 0 ? String.valueOf(dat2) : "0";
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseStockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String getPStockHSNFromName(String PStockName) {
        int counter = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as counter from PStock WHERE stockTitle ='" + PStockName + "'");
            counter = data.getInt("counter");
            data.close();
            if (counter > 0) {
                int dat2 = 0;
                ResultSet data2 = db.getdata("Select StockHSN from PStock WHERE stockTitle ='" + PStockName + "'");
                dat2 = data2.getInt("StockHSN");
                data2.close();
                return dat2 != 0 ? String.valueOf(dat2) : "0";
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseStockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String getPStockPriceFromName(String PStockName) {
        int counter = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as counter from PStock WHERE stockTitle ='" + PStockName + "'");
            counter = data.getInt("counter");
            data.close();
            if (counter > 0) {
                int dat2 = 0;
                ResultSet data2 = db.getdata("Select StockPrice from PStock WHERE stockTitle ='" + PStockName + "'");
                dat2 = data2.getInt("StockPrice");
                data2.close();
                return dat2 != 0 ? String.valueOf(dat2) : "0";
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseStockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String getPStockCostFromName(String PStockName) {
        int counter = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as counter from PStock WHERE stockTitle ='" + PStockName + "'");
            counter = data.getInt("counter");
            data.close();
            if (counter > 0) {
                int dat2 = 0;
                ResultSet data2 = db.getdata("Select stockcost from PStock WHERE stockTitle ='" + PStockName + "'");
                dat2 = data2.getInt("stockcost");
                data2.close();
                return dat2 != 0 ? String.valueOf(dat2) : "0";
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseStockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public static String getPStockRemainFromName(String PStockName) {
        int counter = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as counter from PStock WHERE stockTitle ='" + PStockName + "'");
            counter = data.getInt("counter");
            data.close();
            if (counter > 0) {
                int dat2 = 0;
                ResultSet data2 = db.getdata("Select StockRemain from PStock WHERE stockTitle ='" + PStockName + "'");
                dat2 = data2.getInt("StockRemain");
                data2.close();
                return dat2 != 0 ? String.valueOf(dat2) : "0";
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseStockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
     public static boolean deletePurchase(String Invoiceid) {
        db.setdata("delete from myPtranscation where invoiceid = '" + Invoiceid + "'");
        db.setdata("delete from Purchasepayment where Purchaseid = '" + Invoiceid + "'");
        db.setdata("delete from Purchasedelivery where Purchaseid = '" + Invoiceid + "'");
        boolean setdata = db.setdata("Delete FROM Purchase WHERE Purchaseid='" + Invoiceid + "'");
        return setdata;
    }
}
