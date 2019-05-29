/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * 8308942283
 */
package Dashboard;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLDashboardController implements Initializable {

    @FXML
    private AnchorPane dashboardpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void actionLoadCustomerManager(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.SPACE)) {
            _LoadCustomerManager();
        }

    }

    @FXML
    private void actionLoadCustomerManager2(MouseEvent event) {
        _LoadCustomerManager();
    }

    private void _LoadCustomerManager() {
        try {
            javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Sales/Customer/FXMLCustomerManager.fxml"));
            dashboardpane.getChildren().setAll(load3);
            AnchorPane.setRightAnchor(load3, 0.0);
            AnchorPane.setLeftAnchor(load3, 0.0);
            AnchorPane.setTopAnchor(load3, 0.0);
            AnchorPane.setBottomAnchor(load3, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionLoadStockManager1(MouseEvent event) {
        _LoadStockManager();
    }

    @FXML
    private void actionLoadStockManager(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.SPACE)) {
            _LoadStockManager();
        }
    }

    private void _LoadStockManager() {
        try {
            javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Sales/Stock/FXMLStockManager.fxml"));
            dashboardpane.getChildren().setAll(load3);
            AnchorPane.setRightAnchor(load3, 0.0);
            AnchorPane.setLeftAnchor(load3, 0.0);
            AnchorPane.setTopAnchor(load3, 0.0);
            AnchorPane.setBottomAnchor(load3, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionLoadSalesManager(MouseEvent event) {
        _LoadSalesManager();
    }

    @FXML
    private void actionLoadSalesManager1(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.SPACE)) {
            _LoadSalesManager();
        }
    }

    private void _LoadSalesManager() {
        try {
            javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Sales/FXMLSalesManager.fxml"));
            dashboardpane.getChildren().setAll(load3);
            AnchorPane.setRightAnchor(load3, 0.0);
            AnchorPane.setLeftAnchor(load3, 0.0);
            AnchorPane.setTopAnchor(load3, 0.0);
            AnchorPane.setBottomAnchor(load3, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionLoadProfile1(MouseEvent event) {
        _LoadProfileManager();
    }

    @FXML
    private void actionLoadProfile(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.SPACE)) {
            _LoadProfileManager();
        }
    }

    private void _LoadProfileManager() {
        try {
            javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Profile/FXMLProfileManager.fxml"));
            dashboardpane.getChildren().setAll(load3);
            AnchorPane.setRightAnchor(load3, 0.0);
            AnchorPane.setLeftAnchor(load3, 0.0);
            AnchorPane.setTopAnchor(load3, 0.0);
            AnchorPane.setBottomAnchor(load3, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionLoadProductionManager1(MouseEvent event) {
        _LoadProduction();
    }

    @FXML
    private void actionLoadProductionManager(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.SPACE)) {
            _LoadProduction();
        }
    }

    private void _LoadProduction() {
        try {
            javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Production/FXMLProductionManager.fxml"));
            dashboardpane.getChildren().setAll(load3);
            AnchorPane.setRightAnchor(load3, 0.0);
            AnchorPane.setLeftAnchor(load3, 0.0);
            AnchorPane.setTopAnchor(load3, 0.0);
            AnchorPane.setBottomAnchor(load3, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionLogout1(MouseEvent event) {
        _Logout();
    }

    @FXML
    private void actionLogout(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.SPACE)) {

            _Logout();
        }
    }

    private void _Logout() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Sure Want to Exit/Logout??");
        alert.setContentText(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    private void actionLoadPStock(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.SPACE)) {

            _LoadPStock();
        }
    }

    @FXML
    private void actionLoadPStock1(MouseEvent event) {
        _LoadPStock();
    }

    @FXML
    private void actionLoadSupplier1(MouseEvent event) {
        _LoadSupplier();
    }

    @FXML
    private void actionLoadSupplier(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.SPACE)) {

            _LoadSupplier();
        }
    }

    private void _LoadSupplier() {
        try {
            javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Purchase/Supplier/FXMLSupplierManager.fxml"));
            dashboardpane.getChildren().setAll(load3);
            AnchorPane.setRightAnchor(load3, 0.0);
            AnchorPane.setLeftAnchor(load3, 0.0);
            AnchorPane.setTopAnchor(load3, 0.0);
            AnchorPane.setBottomAnchor(load3, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void _LoadPStock() {
        try {
            javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Purchase/PStock/FXMLPStockManager.fxml"));
            dashboardpane.getChildren().setAll(load3);
            AnchorPane.setRightAnchor(load3, 0.0);
            AnchorPane.setLeftAnchor(load3, 0.0);
            AnchorPane.setTopAnchor(load3, 0.0);
            AnchorPane.setBottomAnchor(load3, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionLoadPurchase1(MouseEvent event) {
        _LoadPurchase();
    }

    @FXML
    private void actionLoadPurchase(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.SPACE)) {
            _LoadPurchase();
        }
    }

    private void _LoadPurchase() {
        try {
            javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Purchase/FXMLPurchaseManager.fxml"));
            dashboardpane.getChildren().setAll(load3);
            AnchorPane.setRightAnchor(load3, 0.0);
            AnchorPane.setLeftAnchor(load3, 0.0);
            AnchorPane.setTopAnchor(load3, 0.0);
            AnchorPane.setBottomAnchor(load3, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionLoadRawStock1(MouseEvent event) {
        _LoadRawStock();
    }

    @FXML
    private void actionLoadRawStock(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.SPACE)) {
            _LoadRawStock();
        }
    }

    private void _LoadRawStock() {
        try {
            javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Production/RawStock/FXMLRStockManager.fxml"));
            dashboardpane.getChildren().setAll(load3);
            AnchorPane.setRightAnchor(load3, 0.0);
            AnchorPane.setLeftAnchor(load3, 0.0);
            AnchorPane.setTopAnchor(load3, 0.0);
            AnchorPane.setBottomAnchor(load3, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionLoadDashPane1(MouseEvent event) {
        // _LoadDashPane();
                    Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Our Dashboard fully Custom.\nso Just tell us what you want and we will make it as requirement");

            alert.showAndWait();
    }

    @FXML
    private void actionLoadDashPane(KeyEvent event) {
      
    }

    private void _LoadDashPane() {
        try {
            javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Dashboard/DashPane/FXMLDashPane.fxml"));
            dashboardpane.getChildren().setAll(load3);
            AnchorPane.setRightAnchor(load3, 0.0);
            AnchorPane.setLeftAnchor(load3, 0.0);
            AnchorPane.setTopAnchor(load3, 0.0);
            AnchorPane.setBottomAnchor(load3, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionLoadEmployee1(MouseEvent event) {
        _LoadEmployeeManager();
    }

    @FXML
    private void actionLoadEmployee(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            _LoadEmployeeManager();
        }
    }

    private void _LoadEmployeeManager() {
        try {
            javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/employee/FXMLEmployeeManager.fxml"));
            dashboardpane.getChildren().setAll(load3);
            AnchorPane.setRightAnchor(load3, 0.0);
            AnchorPane.setLeftAnchor(load3, 0.0);
            AnchorPane.setTopAnchor(load3, 0.0);
            AnchorPane.setBottomAnchor(load3, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
