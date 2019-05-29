/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbController;

import static Common.CFPC.Fun_Date;
import static Common.CFPC.Fun_Timestamp;
import database.db;
import static dbController.CustomerController.getCustomer;
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
public class StockController {

    /**
     * Stock
     */
    public static boolean setstock(String StockTitle, String StockDescription, String StockHSN, String StockCost, String StockPrice, String StockRemain, String StockSold) {
        boolean setdata = db.setdata("INSERT INTO Stock (StockTitle,StockDescription,StockHSN,StockCost,StockPrice,StockRemain,StockSold,updated_on,dated_on,timestamp)"
                + "VALUES('" + StockTitle + "','" + StockDescription + "','" + StockHSN + "','" + StockCost + "','" + StockPrice + "','" + StockRemain + "','" + StockSold + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");
        return setdata ? true : false;
    }

    public static boolean updatestock(String id, String StockTitle, String StockDescription, String StockHSN, String StockCost, String StockPrice, String StockRemain, String StockSold) {
        boolean setdata = db.setdata("UPDATE Stock SET StockTitle='" + StockTitle + "',StockDescription='" + StockDescription + "',StockHSN='" + StockHSN + "',StockCost='" + StockCost + "',StockPrice='" + StockPrice + "',StockRemain='" + StockRemain + "',StockSold='" + StockSold + "',updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE id='" + id + "'");
        return setdata ? true : false;
    }

    public static ResultSet getStock() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM Stock order by id desc");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ResultSet getStock(String id) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM Stock WHERE id='" + id + "'");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean updatedatatoremainstock(String id, String Quantity) {
        boolean setdata = db.setdata("UPDATE STOCK SET StockRemain='" + Quantity + "' WHERE id = '" + id + "'");

        return setdata ? true : false;
    }

    public static boolean adddatatoremainstock(String id, String Quantity) {
        boolean setdata = db.setdata("UPDATE STOCK SET StockRemain=StockRemain+'" + Quantity + "' WHERE id = '" + id + "'");

        return setdata ? true : false;
    }

    public static boolean updatedatatoSoldstock(String id, String Quantity) {
        boolean setdata = db.setdata("UPDATE STOCK SET StockSold='" + Quantity + "' WHERE id = '" + id + "'");

        return setdata ? true : false;
    }

    public static boolean addSoldStockOnTranscation(String Stockid, String Quantity) {
        boolean setdata = db.setdata("UPDATE STOCK SET StockSold=StockSold+'" + Quantity + "' WHERE id = '" + Stockid + "'");
        boolean setdata2 = db.setdata("UPDATE STOCK SET StockRemain=StockRemain-'" + Quantity + "' WHERE id = '" + Stockid + "'");
        return setdata && setdata2;

    }

    public static boolean DeleteStock(String Stockid) {
        return db.setdata("Delete FROM STOCK WHERE id = '" + Stockid + "'");
    }

    public static boolean DeleteSoldStockOnTranscation(String Stockid, String Quantity) {
        boolean setdata = db.setdata("UPDATE STOCK SET StockSold=StockSold-'" + Quantity + "' WHERE id = '" + Stockid + "'");
        boolean setdata2 = db.setdata("UPDATE STOCK SET StockRemain=StockRemain+'" + Quantity + "' WHERE id = '" + Stockid + "'");
        return setdata;
    }

    public static ObservableList<Stock> getStockObservableList() {
        ObservableList<Stock> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getStock();
            while (data.next()) {
                people.add(new Stock(data.getString("Id"), data.getString("StockTitle"), data.getString("StockDescription"), data.getString("StockHSN"), data.getString("StockCost"), data.getString("StockPrice"), data.getString("StockRemain"), data.getString("StockSold")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static String[] getAllStockName() {
        try {

            ResultSet data = getStock();
            final List<String> timeStr = new ArrayList<>();
            while (data.next()) {
                // Just get the value of the column, and add it to the list
                timeStr.add(data.getString("stocktitle"));
            }

            // I would return the list here, but let's convert it to an array
            return timeStr.toArray(new String[timeStr.size()]);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getStockIdFromName(String StockName) {
        int counter = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as counter from Stock WHERE stockTitle ='" + StockName + "'");
            counter = data.getInt("counter");
            data.close();
            if (counter > 0) {
                int dat2 = 0;
                ResultSet data2 = db.getdata("Select id as stockid from Stock WHERE stockTitle ='" + StockName + "'");
                dat2 = data2.getInt("StockId");
                data2.close();
                return dat2 != 0 ? String.valueOf(dat2) : "0";
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getStockHSNFromName(String StockName) {
        int counter = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as counter from Stock WHERE stockTitle ='" + StockName + "'");
            counter = data.getInt("counter");
            data.close();
            if (counter > 0) {
                int dat2 = 0;
                ResultSet data2 = db.getdata("Select StockHSN from Stock WHERE stockTitle ='" + StockName + "'");
                dat2 = data2.getInt("StockHSN");
                data2.close();
                return dat2 != 0 ? String.valueOf(dat2) : "0";
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getStockPriceFromName(String StockName) {
        int counter = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as counter from Stock WHERE stockTitle ='" + StockName + "'");
            counter = data.getInt("counter");
            data.close();
            if (counter > 0) {
                int dat2 = 0;
                ResultSet data2 = db.getdata("Select StockPrice from Stock WHERE stockTitle ='" + StockName + "'");
                dat2 = data2.getInt("StockPrice");
                data2.close();
                return dat2 != 0 ? String.valueOf(dat2) : "0";
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getStockCostFromName(String StockName) {
        int counter = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as counter from Stock WHERE stockTitle ='" + StockName + "'");
            counter = data.getInt("counter");
            data.close();
            if (counter > 0) {
                int dat2 = 0;
                ResultSet data2 = db.getdata("Select stockcost from Stock WHERE stockTitle ='" + StockName + "'");
                dat2 = data2.getInt("stockcost");
                data2.close();
                return dat2 != 0 ? String.valueOf(dat2) : "0";
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getStockRemainFromName(String StockName) {
        int counter = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as counter from Stock WHERE stockTitle ='" + StockName + "'");
            counter = data.getInt("counter");
            data.close();
            if (counter > 0) {
                int dat2 = 0;
                ResultSet data2 = db.getdata("Select StockRemain from Stock WHERE stockTitle ='" + StockName + "'");
                dat2 = data2.getInt("StockRemain");
                data2.close();
                return dat2 != 0 ? String.valueOf(dat2) : "0";
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
