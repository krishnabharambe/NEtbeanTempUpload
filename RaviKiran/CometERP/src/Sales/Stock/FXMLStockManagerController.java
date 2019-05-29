/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sales.Stock;

import Sales.Customer.FXMLCustomerManagerController;
import dbController.Stock;
import dbController.StockController;
import static dbController.StockController.adddatatoremainstock;
import static dbController.StockController.getStock;
import static dbController.StockController.getStockObservableList;
import static dbController.StockController.setstock;
import static dbController.StockController.updatedatatoSoldstock;
import static dbController.StockController.updatedatatoremainstock;
import static dbController.StockController.updatestock;
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
import javafx.scene.control.Alert.AlertType;
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
public class FXMLStockManagerController implements Initializable {

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
    private TableView<Stock> tbStock;
    @FXML
    private TableColumn<Stock, String> tbcolStockID;
    @FXML
    private TableColumn<Stock, String> tncolStockTitle;
    @FXML
    private TableColumn<Stock, String> tbcolStockHSN;
    @FXML
    private TableColumn<Stock, String> tbcolStockPrice;
    @FXML
    private TableColumn<Stock, String> tbcolRemainStock;

    ObservableList<Stock> data = FXCollections.observableArrayList(getStockObservableList());
    ObservableList<Stock> filteredData = FXCollections.observableArrayList();
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
        loadStockTable();

        Stockmaagerpane.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.HOME == event.getCode() || KeyCode.END == event.getCode() || KeyCode.ESCAPE == event.getCode()) {
                try {
                    javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Dashboard/FXMLDashboard.fxml"));
                    Stockmaagerpane.getChildren().setAll(load3);
                    AnchorPane.setRightAnchor(load3, 0.0);
                    AnchorPane.setLeftAnchor(load3, 0.0);
                    AnchorPane.setTopAnchor(load3, 0.0);
                    AnchorPane.setBottomAnchor(load3, 0.0);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLCustomerManagerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public FXMLStockManagerController() {
        data = FXCollections.observableArrayList(getStockObservableList());
        filteredData.addAll(data);

        data.addListener(new ListChangeListener<Stock>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Stock> change) {
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
            alert.setContentText("Please, Select the Stock From Table By Pressing F2 Key??");
            alert.showAndWait();
        } else if (txtStockTitle.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check the Stock Title??");
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
                boolean updatedatatostock = updatestock(updating, txtStockTitle.getText().trim(), txtStockDescription.getText().trim(), txtStockHSN.getText().trim(), txtStockCost.getText().trim(), txtCostPrice.getText().trim(), "0", "0");
                if (updatedatatostock) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Success");
                    alert2.setHeaderText(null);
                    actionStcokReset(event);
                    loadStockTable();
                    alert2.setContentText("Stock Updated Successfully");
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
            alert.setContentText("Please, Check the Stock Title??");
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
                boolean setdatatostock = setstock(txtStockTitle.getText().trim(), txtStockDescription.getText().trim(), txtStockHSN.getText().trim(), txtStockCost.getText().trim(), txtCostPrice.getText().trim(), "0", "0");
                if (setdatatostock) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Success");
                    alert2.setHeaderText(null);
                    actionStcokReset(event);
                    loadStockTable();
                    alert2.setContentText("Stock Added Successfully");
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
            alert.setHeaderText("Sure Want to Update Sold Stock");
            alert.setContentText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean updatedatatoSoldstock = updatedatatoSoldstock(updating, txtSoldStock.getText().trim());
                if (updatedatatoSoldstock) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Success");
                    alert2.setHeaderText(null);
                    actionStcokReset(event);
                    loadStockTable();
                    alert2.setContentText("Sold Stock Updated Successfully");
                    alert2.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check the Sold Stock??");
            alert.showAndWait();
        }
    }

    @FXML
    private void actionAddNewStockQuantity(ActionEvent event) {
        if (txtNewStock.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtNewStock.setStyle("-fx-border-color: null");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure Want to add new Quantity to Stock");
            alert.setContentText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean adddatatoremainstock = adddatatoremainstock(updating, txtNewStock.getText().trim());
                if (adddatatoremainstock) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Success");
                    alert2.setHeaderText(null);
                    actionStcokReset(event);
                    loadStockTable();
                    alert2.setContentText("Stock Quantity Added Successfully");
                    alert2.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check the New Stock??");
            alert.showAndWait();
        }
    }

    @FXML
    private void actiontbStockKeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.F2)) {
            try {
                Stock selectedItem = tbStock.getSelectionModel().getSelectedItem();
                ResultSet stock = getStock(selectedItem.getStockid());
                updating = selectedItem.getStockid();
                txtStockTitle.setText(stock.getString("StockTitle"));
                txtStockDescription.setText(stock.getString("StockDescription"));
                txtStockHSN.setText(stock.getString("StockHSN"));
                txtStockCost.setText(stock.getString("StockCost"));
                txtCostPrice.setText(stock.getString("StockPrice"));
                txtSoldStock.setText(stock.getString("StockSold"));
                txtNewStock.setText("");
                txtRemainStock.setText(stock.getString("StockRemain"));
                btnUpdate.setText("Update -" + selectedItem.getStockid());
                btnNewStock.setText("Update -" + selectedItem.getStockid());
                btnRemainStock.setText("Update -" + selectedItem.getStockid());
                btnSoldStock.setText("Update -" + selectedItem.getStockid());
            } catch (SQLException ex) {
                Logger.getLogger(FXMLStockManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (event.getCode().equals(KeyCode.DELETE)) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // ... user chose OK
                Stock selectedItem = tbStock.getSelectionModel().getSelectedItem();
                boolean setdata = StockController.DeleteStock(selectedItem.getStockid());
                if (setdata) {
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("Information");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Deleted Successfully");
                    alert2.showAndWait();
                    loadStockTable();
                }
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

    private void loadStockTable() {
        filteredData.removeAll(data);
        data = FXCollections.observableArrayList(getStockObservableList());
        filteredData.addAll(data);
        tbcolStockID.setCellValueFactory(new PropertyValueFactory<Stock, String>("Stockid"));
        tncolStockTitle.setCellValueFactory(new PropertyValueFactory<Stock, String>("StockTitle"));
        tbcolStockHSN.setCellValueFactory(new PropertyValueFactory<Stock, String>("StockHSN"));
        tbcolStockPrice.setCellValueFactory(new PropertyValueFactory<Stock, String>("StockPrice"));
        tbcolRemainStock.setCellValueFactory(new PropertyValueFactory<Stock, String>("StockRemain"));
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

        for (Stock p : data) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(Stock p) {
        String filterString = txtSearch.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (p.getStockid().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getStockTitle().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getStockDescription().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getStockPrice().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Stock, ?>> sortOrder = new ArrayList<>(tbStock.getSortOrder());
        tbStock.getSortOrder().clear();
        tbStock.getSortOrder().addAll(sortOrder); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void ActionupdateRemainStock(ActionEvent event) {
        if (txtRemainStock.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtRemainStock.setStyle("-fx-border-color: null");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure Want to Update Remain Stock");
            alert.setContentText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean updatedatatoremainstock = updatedatatoremainstock(updating, txtRemainStock.getText().trim());
                if (updatedatatoremainstock) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Success");
                    alert2.setHeaderText(null);
                    actionStcokReset(event);
                    loadStockTable();
                    alert2.setContentText("Remain Stock Updated Successfully");
                    alert2.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check the Remain Stock??");
            alert.showAndWait();
        }

    }

}
