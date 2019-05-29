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
public class TermsAndConditionsController {
    /**
     * Invoice Terms And Conditions
     */
    public static boolean setdatatoInvoiceTerms(String Description) {
        boolean setdata = db.setdata("INSERT INTO invoiceterms(description,updated_on,dated_on,timestamp)VALUES('" + Description + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");

        return setdata;
    }

    public static boolean updatedatatoInvoiceTerms(String Description) {
        boolean setdata = db.setdata("UPDATE invoiceterms SET description='" + Description + "',updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "'WHERE id= '1'");

        return setdata;
    }

    public static int CheckinvoicetermsForNumberOfrecords() {
        int checker = 0;
        try {
            ResultSet data = db.getdata("SELECT count(*) as checker FROM invoiceterms");
            checker = data.getInt("checker");
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(TermsAndConditionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checker;
    }

    public static ResultSet getinvoiceterms() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM invoiceterms");
        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(TermsAndConditionsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TermsAndConditionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<InvoiceTerms> getinvoicetermsObservableList() {
        ObservableList<InvoiceTerms> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getinvoiceterms();
            while (data.next()) {
                people.add(new InvoiceTerms(data.getString("id"), data.getString("description")));
            }
            data.close();
        } catch (SQLException ex) { 
            Logger.getLogger(TermsAndConditionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static boolean DeleteInvoiceTerms(String id) {
        boolean setdata = db.setdata("DELETE FROM invoiceterms WHERE id ='" + id + "'");

        return setdata ? true : false;
    }

    /**
     * purchase Terms And Conditions
     */
    public static boolean setdatatopurchaseTerms(String Description) {
        boolean setdata = db.setdata("INSERT INTO purchaseterms(description,updated_on,dated_on,timestamp)VALUES('" + Description + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");

        return setdata ? true : false;
    }

    public static boolean updatedatatopurchaseTerms(String Description) {
        boolean setdata = db.setdata("UPDATE purchaseterms SET description='" + Description + "',updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "'WHERE id= '1'");

        return setdata ? true : false;
    }

    public static int CheckpurchasetermsForNumberOfrecords() {
        int checker = 0;
        try {
            ResultSet data = db.getdata("SELECT count(*) as checker FROM purchaseterms");
            checker = data.getInt("checker");
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(TermsAndConditionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checker;
    }

    public static ResultSet getpurchaseterms() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM purchaseterms");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TermsAndConditionsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TermsAndConditionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<purchaseTerms> getpurchasetermsObservableList() {
        ObservableList<purchaseTerms> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getpurchaseterms();
            while (data.next()) {
                people.add(new purchaseTerms(data.getString("id"), data.getString("description")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(TermsAndConditionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static boolean Deletepurchaseterms(String id) {
        boolean setdata = db.setdata("DELETE FROM purchaseterms WHERE id ='" + id + "'");

        return setdata ? true : false;
    }

    /**
     * Quotation Terms And Conditions
     */
    public static boolean setdatatoQuotationTerms(String Description) {
        boolean setdata = db.setdata("INSERT INTO Quotationterms(description,updated_on,dated_on,timestamp)VALUES('" + Description + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");

        return setdata;
    }

    public static boolean updatedatatoQuotationTerms(String Description) {
        boolean setdata = db.setdata("UPDATE Quotationterms SET description='" + Description + "',updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "'WHERE id= '1'");

        return setdata;
    }

    public static int CheckQuotationtermsForNumberOfrecords() {
        int checker = 0;
        try {
            ResultSet data = db.getdata("SELECT count(*) as checker FROM Quotationterms");
            checker = data.getInt("checker");
            data.close();
        } catch (SQLException ex) { 
            Logger.getLogger(TermsAndConditionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checker;
    }

    public static ResultSet getQuotationterms() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM Quotationterms");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TermsAndConditionsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TermsAndConditionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<QuotationTerms> getQuotationtermsObservableList() {
        ObservableList<QuotationTerms> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getQuotationterms();
            while (data.next()) {
                people.add(new QuotationTerms(data.getString("id"), data.getString("description")));
            }
            data.close();
        } catch (SQLException ex) { 
            Logger.getLogger(TermsAndConditionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static boolean DeleteQuotationTerms(String id) {
        boolean setdata = db.setdata("DELETE FROM Quotationterms WHERE id ='" + id + "'");
       
        return setdata;
    }
}
