/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbController;

import static Common.CFPC.Fun_Date;
import static Common.CFPC.Fun_Timestamp;
import database.db;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author admin
 */
public class CustomerController {

    public CustomerController() {
    }

    public static boolean addNewCustomer(String Name, String Address, String AddressPinCode, String Contact, String AltContact, String Telephone, String Email, String Website, String GSTIN, String PanTan, String AccountNo, String BankIFSC, String BankBranch) {
        boolean setdata = db.setdata("INSERT INTO Customer(Name,Address,AddressPinCode,Contact,AltContact,Telephone,Email,Website,GSTIN,PanTan,AccountNo,BankIFSC,BankBranch,updated_on,dated_on,timestamp)"
                + "VALUES('" + Name + "','" + Address + "','" + AddressPinCode + "','" + Contact + "','" + AltContact + "','" + Telephone + "','" + Email + "','" + Website + "','" + GSTIN + "','" + PanTan + "','" + AccountNo + "','" + BankIFSC + "','" + BankBranch + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");
        return setdata ? true : false;
    }

    public static boolean updateCustomer(String id, String Name, String Address, String AddressPinCode, String Contact, String AltContact, String Telephone, String Email, String Website, String GSTIN, String PanTan, String AccountNo, String BankIFSC, String BankBranch) {
        boolean setdata = db.setdata("Update Customer SET Name='" + Name + "',Address='" + Address + "',AddressPinCode='" + AddressPinCode + "',Contact='" + Contact + "',AltContact='" + AltContact + "',Telephone='" + Telephone + "',Email='" + Email + "',Website='" + Website + "',GSTIN='" + GSTIN + "',PanTan='" + PanTan + "',AccountNo='" + AccountNo + "',BankIFSC='" + BankIFSC + "',BankBranch='" + BankBranch + "',updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE id='" + id + "'");

        return setdata ? true : false;
    }

    public static ResultSet getCustomer() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM Customer ORDER By id desc");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ResultSet getCustomer(String id) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM Customer WHERE id='" + id + "'");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Customers> getCustomerObservableList() {
        ObservableList<Customers> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getCustomer();
            while (data.next()) {
                people.add(new Customers(data.getString("Id"), data.getString("Name"), data.getString("Address"), data.getString("AddressPincode"), data.getString("Contact"), data.getString("AltContact"), data.getString("Telephone"), data.getString("Email"), data.getString("Website"), data.getString("GSTIN"), data.getString("PanTan"), data.getString("AccountNo"), data.getString("BankIFSC"), data.getString("BankBranch")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static String[] getAllCustomerName() {
        try {

            ResultSet data = getCustomer();
            final List<String> timeStr = new ArrayList<>();
            while (data.next()) {
                // Just get the value of the column, and add it to the list
                timeStr.add(data.getString("Name"));
            }

            // I would return the list here, but let's convert it to an array
            return timeStr.toArray(new String[timeStr.size()]);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getCustomerIdFromName(String CustomerName) {
        int counter = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as counter from Customer WHERE name ='" + CustomerName + "'");
            counter = data.getInt("counter");
            data.close();

            if (counter > 0) {
                int dat2 = 0;

                ResultSet data2 = db.getdata("Select id from Customer WHERE Name ='" + CustomerName + "'");
                dat2 = data2.getInt("id");
                data2.close();
                return dat2 != 0 ? String.valueOf(dat2) : "0";

            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static boolean deleteCustomer(String cid) {
        return db.setdata("Delete from Customer where Id='" + cid + "'");
    }
}
