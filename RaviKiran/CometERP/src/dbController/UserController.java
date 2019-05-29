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
public class UserController {

    public UserController() {

    }

    public static boolean addNewUser(String username, String password, String role) {
        db.setdata("insert into users(username,password,role,updated_on,dated_on,timestamp) "
                + "values('" + username + "','" + password + "','" + role + "','" + Fun_Date() + "','" + Fun_Date() + "','" + Fun_Timestamp() + "')");
        return false;
    }

    public static boolean UpdateUser(String userid, String username, String password, String role) {
        db.setdata("update users SET username='" + username + "',password='" + password + "',role='" + role + "',updated_on='" + Fun_Date() + "',timestamp='" + Fun_Timestamp() + "' WHERE userid='" + userid + "'");
        return false;
    }

    public static int performCheck() {
        int checker = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as checker from users");
            checker = data.getInt("checker");
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checker;
    }

    public static boolean updateAdminPassword(String NewPassword) {
        boolean setdata = db.setdata("Update users SET password = '" + NewPassword + "' WHERE userid='" + 1 + "'");
        return setdata;
    }

    public static String GetAdminPassword() {
        String string = "0";
        try {
            ResultSet data = db.getdata("Select Password from users where userid = '" + 1 + "'");
            string = data.getString("password");
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return string;
    }

    public static String performLogin(String Username, String Password) {
        int checker = 0;
        String Role = null;

        try {
            ResultSet data = db.getdata("Select count(*) as checker,role from users Where username='" + Username + "' AND Password = '" + Password + "'");
            checker = data.getInt("checker");
            Role = data.getString("role");
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checker > 0 ? Role : "NoRole";
    }

    public static ResultSet getUsers() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM users");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Users> getUserOperatorObservableList() {
        ObservableList<Users> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getUsers();
            while (data.next()) {
                people.add(new Users(data.getString("userid"), data.getString("username"), data.getString("password"), data.getString("role")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

}
