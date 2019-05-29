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

public class RawStockController {
    /**
     * Stock
     */
    public static boolean setRstock(String StockTitle, String StockDescription, String StockHSN, String StockCost, String StockPrice, String StockRemain, String StockSold) {
        boolean setdata = db.setdata("INSERT INTO RStock (StockTitle,StockDescription,StockHSN,StockCost,StockPrice,StockRemain,StockSold,updated_on,dated_on,timestamp)"
                + "VALUES('" + StockTitle + "','" + StockDescription + "','" + StockHSN + "','" + StockCost + "','" + StockPrice + "','" + StockRemain + "','" + StockSold + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");
        return setdata ? true : false;
    }

    public static boolean updateRstock(String id, String StockTitle, String StockDescription, String StockHSN, String StockCost, String StockPrice, String StockRemain, String StockSold) {
        boolean setdata = db.setdata("UPDATE RStock SET StockTitle='" + StockTitle + "',StockDescription='" + StockDescription + "',StockHSN='" + StockHSN + "',StockCost='" + StockCost + "',StockPrice='" + StockPrice + "',StockRemain='" + StockRemain + "',StockSold='" + StockSold + "',updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE id='" + id + "'");
        return setdata ? true : false;
    }

    public static ResultSet getRStock() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM RStock order by id desc");
        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(RawStockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RawStockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ResultSet getRStock(String id) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM RStock WHERE id='" + id + "'");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean updatedatatoremainRstock(String id, String Quantity) {
        boolean setdata = db.setdata("UPDATE RSTOCK SET StockRemain='" + Quantity + "' WHERE id = '" + id + "'");

        return setdata ? true : false;
    }

    public static boolean adddatatoremainRstock(String id, String Quantity) {
        boolean setdata = db.setdata("UPDATE RSTOCK SET StockRemain=StockRemain+'" + Quantity + "' WHERE id = '" + id + "'");

        return setdata ? true : false;
    }

    public static boolean updatedatatoSoldRstock(String id, String Quantity) {
        boolean setdata = db.setdata("UPDATE RSTOCK SET StockSold='" + Quantity + "' WHERE id = '" + id + "'");

        return setdata ? true : false;
    }

    public static boolean addSoldRStockOnTranscation(String Stockid, String Quantity) {
        boolean setdata = db.setdata("UPDATE RSTOCK SET StockSold=StockSold+'" + Quantity + "' WHERE id = '" + Stockid + "'");
        boolean setdata2 = db.setdata("UPDATE RSTOCK SET StockRemain=StockRemain-'" + Quantity + "' WHERE id = '" + Stockid + "'");
        return setdata && setdata2;

    }

    public static boolean DeleteSoldRStockOnTranscation(String Stockid, String Quantity) {
        boolean setdata = db.setdata("UPDATE RSTOCK SET StockSold=StockSold-'" + Quantity + "' WHERE id = '" + Stockid + "'");
        boolean setdata2 = db.setdata("UPDATE RSTOCK SET StockRemain=StockRemain+'" + Quantity + "' WHERE id = '" + Stockid + "'");
        return setdata;
    }

    public static ObservableList<RStock> getRStockObservableList() {
        ObservableList<RStock> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getRStock();
            while (data.next()) {
                people.add(new RStock(data.getString("Id"), data.getString("StockTitle"), data.getString("StockDescription"), data.getString("StockHSN"), data.getString("StockCost"), data.getString("StockPrice"), data.getString("StockRemain"), data.getString("StockSold")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(RawStockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }
    
    public static String[] getAllStockName() {
        try {

            ResultSet data = getRStock();
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

    public static String getRStockIdFromName(String StockName) {
        int counter = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as counter from RStock WHERE stockTitle ='" + StockName + "'");
            counter = data.getInt("counter");
            data.close();
            if (counter > 0) {
                int dat2 = 0;
                ResultSet data2 = db.getdata("Select id as stockid from RStock WHERE stockTitle ='" + StockName + "'");
                dat2 = data2.getInt("StockId");
                data2.close();
                return dat2 != 0 ? String.valueOf(dat2) : "0";
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String getRStockHSNFromName(String StockName) {
        int counter = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as counter from RStock WHERE stockTitle ='" + StockName + "'");
            counter = data.getInt("counter");
            data.close();
            if (counter > 0) {
                int dat2 = 0;
                ResultSet data2 = db.getdata("Select StockHSN from RStock WHERE stockTitle ='" + StockName + "'");
                dat2 = data2.getInt("StockHSN");
                data2.close();
                return dat2 != 0 ? String.valueOf(dat2) : "0";
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String getRStockPriceFromName(String StockName) {
        int counter = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as counter from RStock WHERE stockTitle ='" + StockName + "'");
            counter = data.getInt("counter");
            data.close();
            if (counter > 0) {
                int dat2 = 0;
                ResultSet data2 = db.getdata("Select StockPrice from RStock WHERE stockTitle ='" + StockName + "'");
                dat2 = data2.getInt("StockPrice");
                data2.close();
                return dat2 != 0 ? String.valueOf(dat2) : "0";
            }
        } catch (SQLException ex) { 
            Logger.getLogger(RawStockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String getRStockCostFromName(String StockName) {
        int counter = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as counter from RStock WHERE stockTitle ='" + StockName + "'");
            counter = data.getInt("counter");
            data.close();
            if (counter > 0) {
                int dat2 = 0;
                ResultSet data2 = db.getdata("Select stockcost from RStock WHERE stockTitle ='" + StockName + "'");
                dat2 = data2.getInt("stockcost");
                data2.close();
                return dat2 != 0 ? String.valueOf(dat2) : "0";
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public static String getRStockRemainFromName(String StockName) {
        int counter = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as counter from RStock WHERE stockTitle ='" + StockName + "'");
            counter = data.getInt("counter");
            data.close();
            if (counter > 0) {
                int dat2 = 0;
                ResultSet data2 = db.getdata("Select StockRemain from RStock WHERE stockTitle ='" + StockName + "'");
                dat2 = data2.getInt("StockRemain");
                data2.close();
                return dat2 != 0 ? String.valueOf(dat2) : "0";
            }
        } catch (SQLException ex) {
            Logger.getLogger(RawStockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
