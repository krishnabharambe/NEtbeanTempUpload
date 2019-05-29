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

/**
 *
 * @author admin
 */
public class ProfileController {
    
    /**
     * BusinessDetail
     */
    public static boolean setdatatoBusinessDetail(String BusinessName, String BusinessMailing, String BusinessContact, String BusinessAddress, String BusinessEmail, String BusinessGSTIN, String BusinessPanTan, String BusinessWebsite, String BusinessTelephone) {
        boolean setdata = db.setdata("INSERT INTO BusinessDetail(BusinessName,BusinessMailing,BusinessContact,BusinessAddress,BusinessEmail,BusinessGSTIN,BusinessPanTan,BusinessWebsite,BusinessTelephone,updated_on,dated_on,timestamp)"
                + "VALUES('" + BusinessName + "','" + BusinessMailing + "','" + BusinessContact + "','" + BusinessAddress + "','" + BusinessEmail + "','" + BusinessGSTIN + "','" + BusinessPanTan + "','" + BusinessWebsite + "','" + BusinessTelephone + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");
       return setdata ? true : false;
    }

    public static boolean updatedatatoBusinessDetail(String BusinessName, String BusinessMailing, String BusinessContact, String BusinessAddress, String BusinessEmail, String BusinessGSTIN, String BusinessPanTan, String BusinessWebsite, String BusinessTelephone) {
        boolean setdata = db.setdata("UPDATE BusinessDetail SET BusinessName='" + BusinessName + "',BusinessMailing='" + BusinessMailing + "',BusinessContact='" + BusinessContact + "',BusinessAddress='" + BusinessAddress + "',BusinessEmail='" + BusinessEmail + "',BusinessGSTIN='" + BusinessGSTIN + "',BusinessPanTan='" + BusinessPanTan + "',BusinessWebsite='" + BusinessWebsite + "',BusinessTelephone='" + BusinessTelephone + "',updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE BusinessID='1'");

        return setdata ? true : false;
    }

    public static int CheckBusinessDetailForNumberOfrecords() {
        int checker = 0;
        try {
            ResultSet data = db.getdata("SELECT count(*) as checker FROM BusinessDetail");
            checker = data.getInt("checker");
            data.close();
        } catch (SQLException ex) { 
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checker;
    }

    public static ResultSet getBusinessDetail() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM BusinessDetail");
        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*
    * BusinessAccount
     */
    public static boolean setdatatoBusinessAccount(String BusinessBankAccount, String BusinessBankIFSC, String BusinessBankBranch) {
        boolean setdata = db.setdata("INSERT INTO BusinessAccount(BusinessBankAccount,BusinessBankIFSC,BusinessBankBranch,updated_on,dated_on,timestamp)"
                + "VALUES('" + BusinessBankAccount + "','" + BusinessBankIFSC + "','" + BusinessBankBranch + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");
         return setdata ? true : false;
    }

    public static boolean updatedatatoBusinessAccount(String BusinessBankAccount, String BusinessBankIFSC, String BusinessBankBranch) {
        boolean setdata = db.setdata("UPDATE BusinessAccount SET BusinessBankAccount='" + BusinessBankAccount + "',BusinessBankIFSC='" + BusinessBankIFSC + "',BusinessBankBranch='" + BusinessBankBranch + "',updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE id='1'");

        return setdata ? true : false;
    }

    public static int CheckBusinessAccountForNumberOfrecords() {
        int checker = 0;
        try {
            ResultSet data = db.getdata("SELECT count(*) as checker FROM BusinessAccount");
            checker = data.getInt("checker");
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checker;
    }

    public static ResultSet getBusinessAccount() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM BusinessAccount");
        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    /**
     * gstdefault
     */
    public static boolean addGSTdefault(String newGSTRate) {
        boolean setdata = db.setdata("INSERT INTO GSTdefault(rate,Updated_on,Dated_on,timestamp) VALUES('" + newGSTRate + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");
        return setdata;
    }

    public static boolean updateGSTdefault(String newGSTRate) {
        boolean setdata = db.setdata("UPDATE GSTdefault SET rate= '" + newGSTRate + "',Updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE GDid= '1'");
        return setdata;
    }

    public static int countupdateGSTdefault() {
        int checker = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as checker FROM GStDefault");
            checker = data.getInt("checker");
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checker;
    }

    public static String getGSTDefault() {
        String retrunstring = "0";

        try {
            ResultSet data = db.getdata("Select Rate From GSTDefault Where GDid = '1'");

            if (data.next()) {
                retrunstring = data.getString("Rate");
            }
            
            data.close();
        } catch (SQLException ex) { 
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retrunstring;
    }
}
