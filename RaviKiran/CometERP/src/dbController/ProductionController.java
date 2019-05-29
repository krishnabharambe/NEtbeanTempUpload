/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbController;

import Common.CFPC;
import static Common.CFPC.Fun_Date;
import static Common.CFPC.Fun_Timestamp;
import database.db;
import static dbController.SalesController.getmyTranscation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductionController {

    /**
     * *
     *
     * @param productiondate
     * @param raw1
     * @param raw2
     * @param raw3
     * @param raw4
     * @param rawtotal
     * @param calciumtotal
     * @param titaniumtotal
     * @param tblstotal
     * @param waxtotal
     * @param liltotal
     * @param fulltotal
     * @param name
     * @param size
     * @param Pipes
     * @return
     */
    public static boolean addProduction(String productiondate, String raw1, String raw2, String raw3, String raw4, String rawtotal, String calciumtotal, String titaniumtotal, String tblstotal, String waxtotal, String liltotal, String fulltotal, String name, String size, String Pipes) {
        boolean setdata = db.setdata("INSERT INTO PRODUCTION (productiondate,raw1,raw2,raw3,raw4,rawtotal,calciumtotal,titaniumtotal,tblstotal,waxtotal,liltotal,fulltotal,name,size,pipes,updated_on,dated_on,timestamp)"
                + "VALUES('" + productiondate + "','" + raw1 + "','" + raw2 + "','" + raw3 + "','" + raw4 + "','" + rawtotal + "','" + calciumtotal + "','" + titaniumtotal + "','" + tblstotal + "','" + waxtotal + "','" + liltotal + "','" + fulltotal + "','" + name + "','" + size + "','" + Pipes + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");
        return setdata;
    }

    /**
     * *
     *
     * @param Productionid
     * @param productiondate
     * @param raw1
     * @param raw2
     * @param raw3
     * @param raw4
     * @param rawtotal
     * @param calciumtotal
     * @param titaniumtotal
     * @param tblstotal
     * @param waxtotal
     * @param liltotal
     * @param fulltotal
     * @param name
     * @param size
     * @param Pipes
     * @return
     */
    public static boolean updateaproduction(String Productionid, String productiondate, String raw1, String raw2, String raw3, String raw4, String rawtotal, String calciumtotal, String titaniumtotal, String tblstotal, String waxtotal, String liltotal, String fulltotal, String name, String size, String Pipes) {
        return db.setdata("UPDATE production set productiondate='" + productiondate + "',raw1='" + raw1 + "',raw2='" + raw2 + "',raw3='" + raw3 + "',raw4='" + raw4 + "',rawtotal='" + rawtotal + "',calciumtotal='" + calciumtotal + "',titaniumtotal='" + titaniumtotal + "',tblstotal='" + tblstotal + "',waxtotal='" + waxtotal + "',liltotal='" + liltotal + "',fulltotal ='" + fulltotal + "',name ='" + name + "',size='" + size + "',Pipes='" + Pipes + "',updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE Productionid = '" + Productionid + "'");
    }

    /**
     * *
     *
     * @return
     */
    public static ResultSet AllProduction() {
        return db.getdata("Select * FROM Production order by productionid desc");
    }
    
     public static ResultSet AllProduction(String id) {
        return db.getdata("Select * FROM Production WHERE productionid='"+id+"' order by productionid desc");
    }


    public static int getmaxProductionId() {
        int maxid = 0;
        ResultSet data = db.getdata("Select max(Productionid) as maxid from Production");
        try {
            maxid = data.getInt("maxid");
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maxid;
    }

    public static ObservableList<Production> getProductionObservableList() {
        ObservableList<Production> people = FXCollections.observableArrayList();
        try {
            ResultSet data = AllProduction();
            while (data.next()) {

                people.add(new Production(data.getString("PRODUCTIONID"), data.getString("productiondate"), data.getString("raw1"), data.getString("raw2"), data.getString("raw3"), data.getString("raw4"), data.getString("rawtotal"), data.getString("calciumtotal"), data.getString("titaniumtotal"), data.getString("tblstotal"), data.getString("waxtotal"), data.getString("liltotal"), data.getString("fulltotal"), data.getString("name"), data.getString("size"), data.getString("pipes")));
//               new mytranscation(data.getString("Id"), data.getString("StockTitle"), data.getString("StockDescription"), data.getString("StockHSN"), data.getString("StockCost"), data.getString("StockPrice"), data.getString("StockRemain"), data.getString("StockSold")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static ResultSet getAllInfoOfProduction(String ProductionId) {
        return db.getdata("SELECT * FROM Production WHERE productionid ='" + ProductionId + "'");
    }
}
