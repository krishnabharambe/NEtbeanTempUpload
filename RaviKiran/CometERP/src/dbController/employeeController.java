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

public class employeeController {
    public static boolean addNewemployee(String Name, String Address, String Contact, String AltContact, String Email, String UID, String AccountNo, String BankIFSC, String BankBranch) {
        boolean setdata = db.setdata("INSERT INTO employee(Name,Address,Contact,AltContact,Email,uid,AccountNo,BankIFSC,BankBranch,updated_on,dated_on,timestamp)"
                + "VALUES('" + Name + "','" + Address + "','" + Contact + "','" + AltContact + "','" + Email + "','" + UID + "','" + AccountNo + "','" + BankIFSC + "','" + BankBranch + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");
        return setdata ? true : false;
    }

    public static boolean updateemployee(String id, String Name, String Address, String Contact, String AltContact, String Email, String UID, String AccountNo, String BankIFSC, String BankBranch) {
        boolean setdata = db.setdata("Update employee SET Name='" + Name + "',Address='" + Address + "',Contact='" + Contact + "',AltContact='" + AltContact + "',Email='" + Email + "',uid='" + UID + "',AccountNo='" + AccountNo + "',BankIFSC='" + BankIFSC + "',BankBranch='" + BankBranch + "',updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE id='" + id + "'");

        return setdata ? true : false;
    }

    public static ResultSet getemployee() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM employee ORDER By id desc");
        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(employeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(employeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ResultSet getemployee(String id) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM employee WHERE id='" + id + "'");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(employeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(employeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<employee> getemployeeObservableList() {
        ObservableList<employee> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getemployee();
            while (data.next()) {
                people.add(new employee(data.getString("Id"), data.getString("Name"), data.getString("Address"), data.getString("Contact"), data.getString("AltContact"),  data.getString("Email"),  data.getString("UID"),  data.getString("AccountNo"), data.getString("BankIFSC"), data.getString("BankBranch")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(employeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static String[] getAllemployeeName() {
        try {

            ResultSet data = getemployee();
            final List<String> timeStr = new ArrayList<>();
            while (data.next()) {
                // Just get the value of the column, and add it to the list
                timeStr.add(data.getString("Name"));
            }

            // I would return the list here, but let's convert it to an array
            return timeStr.toArray(new String[timeStr.size()]);
        } catch (SQLException ex) {
            Logger.getLogger(employeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getemployeeIdFromName(String employeeName) {
        int counter = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as counter from employee WHERE name ='" + employeeName + "'");
            counter = data.getInt("counter");
            data.close();

            if (counter > 0) {
                int dat2 = 0;

                ResultSet data2 = db.getdata("Select id from employee WHERE Name ='" + employeeName + "'");
                dat2 = data2.getInt("id");
                data2.close();
                return dat2 != 0 ? String.valueOf(dat2) : "0";

            }
        } catch (SQLException ex) {
            Logger.getLogger(employeeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
