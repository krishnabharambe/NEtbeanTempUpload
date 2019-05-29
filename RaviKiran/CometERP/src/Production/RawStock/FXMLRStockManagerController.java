/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Production.RawStock;

import dbController.RStock;
import static dbController.RawStockController.adddatatoremainRstock;
import static dbController.RawStockController.getRStock;
import static dbController.RawStockController.getRStockObservableList;
import static dbController.RawStockController.setRstock;
import static dbController.RawStockController.updateRstock;
import static dbController.RawStockController.updatedatatoSoldRstock;
import static dbController.RawStockController.updatedatatoremainRstock;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLRStockManagerController implements Initializable {

    String updating = null;
    @FXML
    private TextField txtStockTitle;
    @FXML
    private TextField txtStockDescription;
    @FXML
    private TextField txtStockHSN;
    @FXML
    private TextField txtStockCost;
    @FXML
    private TextField txtCostPrice;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnAddStock;
    @FXML
    private TextField txtRemainStock;
    @FXML
    private TextField txtSoldStock;
    @FXML
    private TextField txtNewStock;
    @FXML
    private TextField txtSearch;
    @FXML
    private TableView<RStock> tbStock;
    @FXML
    private TableColumn<RStock, String> tbcolStockID;
    @FXML
    private TableColumn<RStock, String> tncolStockTitle;
    @FXML
    private TableColumn<RStock, String> tbcolStockHSN;
    @FXML
    private TableColumn<RStock, String> tbcolStockPrice;
    @FXML
    private TableColumn<RStock, String> tbcolRemainStock;

    ObservableList<RStock> data = FXCollections.observableArrayList(getRStockObservableList());
    ObservableList<RStock> filteredData = FXCollections.observableArrayList();
    @FXML
    private Button btnRemainStock;
    @FXML
    private Button btnSoldStock;
    @FXML
    private Button btnNewStock;
    @FXML
    private AnchorPane Stockmaagerpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadPStockTable();
        
        Stockmaagerpane.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.HOME == event.getCode() || KeyCode.END == event.getCode()|| KeyCode.ESCAPE == event.getCode()) {
                try {
                    javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Dashboard/FXMLDashboard.fxml"));
                    Stockmaagerpane.getChildren().setAll(load3);
                    AnchorPane.setRightAnchor(load3, 0.0);
                    AnchorPane.setLeftAnchor(load3, 0.0);
                    AnchorPane.setTopAnchor(load3, 0.0);
                    AnchorPane.setBottomAnchor(load3, 0.0);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLRStockManagerController.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        });
    }

    public FXMLRStockManagerController() {
        data = FXCollections.observableArrayList(getRStockObservableList());
        filteredData.addAll(data);

        data.addListener(new ListChangeListener<RStock>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends RStock> change) {
                updateFilteredData();
            }
        });

    }

    @FXML
    private void actionStcokReset(ActionEvent event) {
        updating = null;
        ClearFields();
        btnUpdate.setText("Update");
        btnNewStock.setText("Update");
        btnRemainStock.setText("Update");
        btnSoldStock.setText("Update");
    }

    @FXML
    private void ActionUpdateStock(ActionEvent event) {
        if (updating.equals(null)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Select the RStock From Table By Pressing F2 Key??");
            alert.showAndWait();
        } else if (txtStockTitle.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check the RStock Title??");
            alert.showAndWait();

        } else if (txtStockHSN.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check the HSN??");
            alert.showAndWait();

        } else if (Double.valueOf(txtCostPrice.getText()) < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check the Price??");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure Want to Update??");
            alert.setContentText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean updatedatatostock = updateRstock(updating, txtStockTitle.getText().trim(), txtStockDescription.getText().trim(), txtStockHSN.getText().trim(), txtStockCost.getText().trim(), txtCostPrice.getText().trim(), "0", "0");
                if (updatedatatostock) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Success");
                    alert2.setHeaderText(null);
                    actionStcokReset(event);
                    loadPStockTable();
                    alert2.setContentText("RStock Updated Successfully");
                    alert2.showAndWait();
                }
            }

        }
    }

    @FXML
    private void ActionAddStock(ActionEvent event) {
        if (txtStockTitle.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check the RStock Title??");
            alert.showAndWait();

        } else if (txtStockHSN.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check the HSN??");
            alert.showAndWait();

        } else if (Double.valueOf(txtCostPrice.getText()) < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check the Price??");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure Want to Add??");
            alert.setContentText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean setdatatostock =setRstock(txtStockTitle.getText().trim(), txtStockDescription.getText().trim(), txtStockHSN.getText().trim(), txtStockCost.getText().trim(), txtCostPrice.getText().trim(), "0", "0");
                if (setdatatostock) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Success");
                    alert2.setHeaderText(null);
                    actionStcokReset(event);
                    loadPStockTable();
                    alert2.setContentText("RStock Added Successfully");
                    alert2.showAndWait();
                }
            }

        }

    }

    @FXML
    private void actionUpdateSoldStock(ActionEvent event) {
        if (txtSoldStock.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtSoldStock.setStyle("-fx-border-color: null");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure Want to Update Sold RStock");
            alert.setContentText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean updatedatatoSoldstock = updatedatatoSoldRstock(updating, txtSoldStock.getText().trim());
                if (updatedatatoSoldstock) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Success");
                    alert2.setHeaderText(null);
                    actionStcokReset(event);
                     loadPStockTable();
                    alert2.setContentText("Sold RStock Updated Successfully");
                    alert2.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check the Sold RStock??");
            alert.showAndWait();
        }
    }

    @FXML
    private void actionAddNewStockQuantity(ActionEvent event) {
        if (txtNewStock.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtNewStock.setStyle("-fx-border-color: null");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure Want to add new Quantity to RStock");
            alert.setContentText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean adddatatoremainstock =adddatatoremainRstock(updating, txtNewStock.getText().trim());
                if (adddatatoremainstock) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Success");
                    alert2.setHeaderText(null);
                    actionStcokReset(event);
                     loadPStockTable();
                    alert2.setContentText("RStock Quantity Added Successfully");
                    alert2.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check the New RStock??");
            alert.showAndWait();
        }
    }

    @FXML
    private void actiontbStockKeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.F2)) {
            try {
                RStock selectedItem = tbStock.getSelectionModel().getSelectedItem();    
                ResultSet stock = getRStock(selectedItem.getRStockid());
                updating = selectedItem.getRStockid();
                txtStockTitle.setText(stock.getString("StockTitle"));
                txtStockDescription.setText(stock.getString("StockDescription"));
                txtStockHSN.setText(stock.getString("StockHSN"));
                txtStockCost.setText(stock.getString("StockCost"));
                txtCostPrice.setText(stock.getString("StockPrice"));
                txtSoldStock.setText(stock.getString("StockSold"));
                txtNewStock.setText("");
                txtRemainStock.setText(stock.getString("StockRemain"));
                btnUpdate.setText("Update -" + selectedItem.getRStockid());
                btnNewStock.setText("Update -" + selectedItem.getRStockid());
                btnRemainStock.setText("Update -" + selectedItem.getRStockid());
                btnSoldStock.setText("Update -" + selectedItem.getRStockid());
            } catch (SQLException ex) {
                Logger.getLogger(FXMLRStockManagerController.class.getName()).log(Level.SEVERE, null, ex);
            } 

        }
    }

    private void ClearFields() {
        txtStockTitle.setText("");
        txtStockDescription.setText("");
        txtStockHSN.setText("");
        txtStockCost.setText("");
        txtCostPrice.setText("");
        txtSoldStock.setText("");
        txtNewStock.setText("");
        txtRemainStock.setText("");
    }

    private void loadPStockTable() {
        filteredData.removeAll(data);
        data = FXCollections.observableArrayList(getRStockObservableList());
        filteredData.addAll(data);
        tbcolStockID.setCellValueFactory(new PropertyValueFactory<RStock, String>("RStockid"));
        tncolStockTitle.setCellValueFactory(new PropertyValueFactory<RStock, String>("RStockTitle"));
        tbcolStockHSN.setCellValueFactory(new PropertyValueFactory<RStock, String>("RStockHSN"));
        tbcolStockPrice.setCellValueFactory(new PropertyValueFactory<RStock, String>("RStockPrice"));
        tbcolRemainStock.setCellValueFactory(new PropertyValueFactory<RStock, String>("RStockRemain"));
        tbStock.setItems(filteredData);

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
        });
    }

    private void updateFilteredData() {
        filteredData.clear();

        for (RStock p : data) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(RStock p) {
        String filterString = txtSearch.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (p.getRStockid().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getRStockTitle().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getRStockDescription().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getRStockPrice().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<RStock, ?>> sortOrder = new ArrayList<>(tbStock.getSortOrder());
        tbStock.getSortOrder().clear();
        tbStock.getSortOrder().addAll(sortOrder); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void ActionupdateRemainStock(ActionEvent event) {
        if (txtRemainStock.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtRemainStock.setStyle("-fx-border-color: null");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure Want to Update Remain RStock");
            alert.setContentText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean updatedatatoremainstock = updatedatatoremainRstock(updating, txtRemainStock.getText().trim());
                if (updatedatatoremainstock) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Success");
                    alert2.setHeaderText(null);
                    actionStcokReset(event);
                    loadPStockTable();
                    alert2.setContentText("Remain RStock Updated Successfully");
                    alert2.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check the Remain RStock??");
            alert.showAndWait();
        }
    }    
}
