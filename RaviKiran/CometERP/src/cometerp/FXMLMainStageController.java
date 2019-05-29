/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cometerp;

import dbController.UserController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLMainStageController implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnSignin;
    @FXML
    private Label lblErrors;
    @FXML
    private AnchorPane mainPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {

    }

    @FXML
    private void actionLofin(ActionEvent event) {
        try {
            login();
        } catch (IOException ex) {
            Logger.getLogger(FXMLMainStageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionkeyreleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                login();
            } catch (IOException ex) {
                Logger.getLogger(FXMLMainStageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void login() throws IOException {
        if (txtUsername.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please, Check Username and Password!");
            alert.showAndWait();
        } else if (txtPassword.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please, Check Username and Password!");

            alert.showAndWait();
        } else {
            if (txtUsername.getText().trim().equals("fake")) {
                //forward towards the fake
            } else {
                int performCheck = UserController.performCheck();
                if (performCheck == 0) {
                    boolean addNewUser = UserController.addNewUser("Admin", "Uday@Pass", "Admin");
                    if (addNewUser) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setContentText("New Registration Done.\nPlease, Login Again");
                        alert.showAndWait();
                    }
                } else {
                    String performLogin = UserController.performLogin(txtUsername.getText().trim(), txtPassword.getText().trim());
                    if (performLogin.trim().equals("Admin")) {
                        javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Dashboard/FXMLDashboard.fxml"));
                        mainPane.getChildren().setAll(load3);
                        AnchorPane.setRightAnchor(load3, 0.0);
                        AnchorPane.setLeftAnchor(load3, 0.0);
                        AnchorPane.setTopAnchor(load3, 0.0);
                        AnchorPane.setBottomAnchor(load3, 0.0);
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText(null);
                        alert.setContentText("Please, Check Username and Password!");
                        alert.showAndWait();
                    }
                }
            }
        }
    }
}
