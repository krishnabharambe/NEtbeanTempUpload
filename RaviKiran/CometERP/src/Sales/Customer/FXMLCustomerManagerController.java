/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sales.Customer;

import dbController.CustomerController;
import static dbController.CustomerController.deleteCustomer;
import static dbController.CustomerController.getCustomerObservableList;
import dbController.Customers;
import dbController.Stock;
import dbController.StockController;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextArea;
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
public class FXMLCustomerManagerController implements Initializable {

    String Updating = null;

    @FXML
    private TextField txtCiustomerEmail;
    @FXML
    private TextField txtBankIFSC;
    @FXML
    private TextField txtBankBranch;
    @FXML
    private Button btnUpdate;
    @FXML
    private TextField txtName;
    @FXML
    private TextArea txtAddress;
    @FXML
    private TextField txtAddressPincode;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtAltContact;
    @FXML
    private TextField txtTelephone;
    @FXML
    private TextField txtWebsite;
    @FXML
    private TextField txtGSTIN;
    @FXML
    private TextField txtPanTan;
    @FXML
    private TextField txtBankAccountNo;
    @FXML
    private TextField txtSearch;
    @FXML
    private TableView<Customers> tbCustomer;
    @FXML
    private TableColumn<Customers, String> tbcolid;
    @FXML
    private TableColumn<Customers, String> tbcolname;
    @FXML
    private TableColumn<Customers, String> tbcoladdress;

    ObservableList<Customers> data = FXCollections.observableArrayList(getCustomerObservableList());
    ObservableList<Customers> filteredData = FXCollections.observableArrayList();
    @FXML
    private AnchorPane customermanagerpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        _loadCustomerTable();
        customermanagerpane.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.HOME == event.getCode() || KeyCode.END == event.getCode()|| KeyCode.ESCAPE == event.getCode()) {
                try {
                    javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Dashboard/FXMLDashboard.fxml"));
                    customermanagerpane.getChildren().setAll(load3);
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

    public FXMLCustomerManagerController() {
        data = FXCollections.observableArrayList(getCustomerObservableList());
        filteredData.addAll(data);

        data.addListener(new ListChangeListener<Customers>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Customers> change) {
                _updateFilteredData();
            }
        });

    }

    @FXML
    private void ActionReset(ActionEvent event) {
        Updating = null;
        _clearfields();
    }

    @FXML
    private void ActionReset1(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            Updating = null;
            _clearfields();
        }
    }

    @FXML
    private void actionUpdate(ActionEvent event) {
        _UpdateCustomer();
    }

    @FXML
    private void actionUpdate1(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            _UpdateCustomer();
        }
    }

    @FXML
    private void actionAdd(ActionEvent event) {
        _AddCustomer();
    }

    @FXML
    private void actionAdd1(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            _AddCustomer();
        }
    }

    private void _clearfields() {
        txtCiustomerEmail.setText("");
        txtBankIFSC.setText("");
        txtBankBranch.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtAddressPincode.setText("");
        txtContact.setText("");
        txtAltContact.setText("");
        txtTelephone.setText("");
        txtWebsite.setText("");
        txtGSTIN.setText("");
        txtPanTan.setText("");
        txtBankAccountNo.setText("");
        btnUpdate.setText("Update");
    }

    private void _AddCustomer() {
        if (txtName.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Select Customer Name");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure want To add New Customer??");
            alert.setContentText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean addNewCustomer = CustomerController.addNewCustomer(txtName.getText().trim(), txtAddress.getText().trim(), txtAddressPincode.getText().trim(), txtContact.getText().trim(), txtAltContact.getText().trim(), txtTelephone.getText().trim(), txtCiustomerEmail.getText().trim(), txtWebsite.getText().trim(), txtGSTIN.getText().trim(), txtPanTan.getText().trim(), txtBankAccountNo.getText().trim(), txtBankIFSC.getText().trim(), txtBankBranch.getText().trim());
                if (addNewCustomer) {
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("Success");
                    alert2.setHeaderText(null);
                    alert2.setContentText("New Customer Added Successfully");
                    alert2.showAndWait();
                    _clearfields();
                    _loadCustomerTable();
                }
            }

        }
    }

    private void _loadCustomerTable() {
        filteredData.removeAll(data);
        data = FXCollections.observableArrayList(getCustomerObservableList());
        filteredData.addAll(data);
        tbcolid.setCellValueFactory(new PropertyValueFactory<Customers, String>("id"));
        tbcolname.setCellValueFactory(new PropertyValueFactory<Customers, String>("Name"));
        tbcoladdress.setCellValueFactory(new PropertyValueFactory<Customers, String>("Address"));
        tbCustomer.setItems(filteredData);
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                _updateFilteredData();
            }
        });
    }

    private void _updateFilteredData() {
        filteredData.clear();

        for (Customers p : data) {
            if (_matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        _reapplyTableSortOrder();
    }

    private boolean _matchesFilter(Customers p) {
        String filterString = txtSearch.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (p.getId().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getAddress().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        return false;
    }

    private void _reapplyTableSortOrder() {
        ArrayList<TableColumn<Customers, ?>> sortOrder = new ArrayList<>(tbCustomer.getSortOrder());
        tbCustomer.getSortOrder().clear();
        tbCustomer.getSortOrder().addAll(sortOrder); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void aactiontbKeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.F2)) {
            Customers selectedItem = tbCustomer.getSelectionModel().getSelectedItem();
            Updating = selectedItem.getId();
            txtCiustomerEmail.setText(selectedItem.getEmail());
            txtBankIFSC.setText(selectedItem.getBankIFSC());
            txtBankBranch.setText(selectedItem.getBankBranch());
            txtName.setText(selectedItem.getName());
            txtAddress.setText(selectedItem.getAddress());
            txtAddressPincode.setText(selectedItem.getAddressPinCode());
            txtContact.setText(selectedItem.getContact());
            txtAltContact.setText(selectedItem.getAltContact());
            txtTelephone.setText(selectedItem.getTelephone());
            txtWebsite.setText(selectedItem.getWebsite());
            txtGSTIN.setText(selectedItem.getGSTIN());
            txtPanTan.setText(selectedItem.getPanTan());
            txtBankAccountNo.setText(selectedItem.getAccountNo());
            btnUpdate.setText("Update - " + Updating);
        }else if (event.getCode().equals(KeyCode.DELETE)) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // ... user chose OK
                 Customers selectedItem = tbCustomer.getSelectionModel().getSelectedItem();
                boolean setdata = deleteCustomer(selectedItem.getId());
                if (setdata) {
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("Information");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Deleted Successfully");
                    alert2.showAndWait();
                    _loadCustomerTable();
                }
            }

        }

    }

    private void _UpdateCustomer() {
        if (Updating.trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Select Customer");
            alert.showAndWait();
        } else if (txtName.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Select Customer Name");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure want To add New Customer??");
            alert.setContentText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean addNewCustomer = CustomerController.updateCustomer(Updating, txtName.getText().trim(), txtAddress.getText().trim(), txtAddressPincode.getText().trim(), txtContact.getText().trim(), txtAltContact.getText().trim(), txtTelephone.getText().trim(), txtCiustomerEmail.getText().trim(), txtWebsite.getText().trim(), txtGSTIN.getText().trim(), txtPanTan.getText().trim(), txtBankAccountNo.getText().trim(), txtBankIFSC.getText().trim(), txtBankBranch.getText().trim());
                if (addNewCustomer) {
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("Success");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Customer Updated Successfully");
                    alert2.showAndWait();
                    _clearfields();
                    _loadCustomerTable();
                }
            }

        }
    }

}