/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static Common.CFPC.EXP;
import java.awt.List;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class db {
    
    static private Connection c;
    
    public static Connection getconnection() throws ClassNotFoundException, SQLException {
        if (c == null) {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DDM.sqlite");
        }
        return c;
    }
    
    public static boolean setdata(String s) {
        try {
            int executeUpdate = db.getconnection().createStatement().executeUpdate(s);
            System.out.println("output:" + executeUpdate);
            if (executeUpdate == 1) {
                return true; // added sucessfully
            } else {
                return false;
            }
        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static ResultSet getdata(String s) {
        try {
            return db.getconnection().createStatement().executeQuery(s);
        } catch (ClassNotFoundException | SQLException ex) {
            EXP(ex);
        }
        return null;
    }
    
    public static void loadnsdata(String sql, String unitchecker, JComboBox cb) {
        try {
            ResultSet data = getdata(sql);
            while (data.next()) {
                if (((DefaultComboBoxModel) cb.getModel()).getIndexOf(data.getString(unitchecker)) < 0) {
                    cb.addItem(data.getString(unitchecker));
                }
            }
            data.close();
        } catch (SQLException ex) {
            EXP(ex);
        }
    }

    static void getdata() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
