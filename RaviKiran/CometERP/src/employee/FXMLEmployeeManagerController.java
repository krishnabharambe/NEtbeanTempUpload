package employee;

import dbController.employee;
import dbController.employeeController;
import static dbController.employeeController.getemployeeObservableList;
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

public class FXMLEmployeeManagerController implements Initializable {

    String Updating = null;

    @FXML
    private AnchorPane customermanagerpane;
    @FXML
    private TextField txtSearch;
    @FXML
    private TableView<employee> tbCustomer;
    @FXML
    private TableColumn<employee, String> tbcolid;
    @FXML
    private TableColumn<employee, String> tbcolname;
    @FXML
    private TableColumn<employee, String> tbcoladdress;
    @FXML
    private TextField txtName;
    @FXML
    private TextArea txtAddress;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtAltContact;
    @FXML
    private TextField txtCiustomerEmail;
    @FXML
    private TextField txtGSTIN;
    @FXML
    private TextField txtBankAccountNo;
    @FXML
    private TextField txtBankIFSC;
    @FXML
    private TextField txtBankBranch;
    @FXML
    private Button btnUpdate;

    ObservableList<employee> data = FXCollections.observableArrayList(getemployeeObservableList());
    ObservableList<employee> filteredData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        _loademployeeTable();
        customermanagerpane.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.HOME == event.getCode() || KeyCode.END == event.getCode() || KeyCode.ESCAPE == event.getCode()) {
                try {
                    javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Dashboard/FXMLDashboard.fxml"));
                    customermanagerpane.getChildren().setAll(load3);
                    AnchorPane.setRightAnchor(load3, 0.0);
                    AnchorPane.setLeftAnchor(load3, 0.0);
                    AnchorPane.setTopAnchor(load3, 0.0);
                    AnchorPane.setBottomAnchor(load3, 0.0);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLEmployeeManagerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public FXMLEmployeeManagerController() {
        data = FXCollections.observableArrayList(getemployeeObservableList());
        filteredData.addAll(data);

        data.addListener(new ListChangeListener<employee>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends employee> change) {
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
        _Updateemployee();
    }

    @FXML
    private void actionUpdate1(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            _Updateemployee();
        }
    }

    @FXML
    private void actionAdd(ActionEvent event) {
        _Addemployee();
    }

    @FXML
    private void actionAdd1(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            _Addemployee();
        }
    }

    private void _clearfields() {
        txtCiustomerEmail.setText("");
        txtBankIFSC.setText("");
        txtBankBranch.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtAltContact.setText("");
        txtGSTIN.setText("");
        txtBankAccountNo.setText("");
        btnUpdate.setText("Update");
    }

    private void _Addemployee() {
        if (txtName.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Select employee Name");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure want To add New employee??");
            alert.setContentText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean addNewemployee = employeeController.addNewemployee(txtName.getText().trim(), txtAddress.getText().trim(), txtContact.getText().trim(), txtAltContact.getText().trim(), txtCiustomerEmail.getText().trim(), txtGSTIN.getText().trim(), txtBankAccountNo.getText().trim(), txtBankIFSC.getText().trim(), txtBankBranch.getText().trim());
                if (addNewemployee) {
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("Success");
                    alert2.setHeaderText(null);
                    alert2.setContentText("New employee Added Successfully");
                    alert2.showAndWait();
                    _clearfields();
                    _loademployeeTable();
                }
            }

        }
    }

    private void _loademployeeTable() {
        filteredData.removeAll(data);
        data = FXCollections.observableArrayList(getemployeeObservableList());
        filteredData.addAll(data);
        tbcolid.setCellValueFactory(new PropertyValueFactory<employee, String>("id"));
        tbcolname.setCellValueFactory(new PropertyValueFactory<employee, String>("Name"));
        tbcoladdress.setCellValueFactory(new PropertyValueFactory<employee, String>("Address"));
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

        for (employee p : data) {
            if (_matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        _reapplyTableSortOrder();
    }

    private boolean _matchesFilter(employee p) {
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
        ArrayList<TableColumn<employee, ?>> sortOrder = new ArrayList<>(tbCustomer.getSortOrder());
        tbCustomer.getSortOrder().clear();
        tbCustomer.getSortOrder().addAll(sortOrder); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void aactiontbKeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.F2)) {
            employee selectedItem = tbCustomer.getSelectionModel().getSelectedItem();
            Updating = selectedItem.getId();
            txtCiustomerEmail.setText(selectedItem.getEmail());
            txtBankIFSC.setText(selectedItem.getBankIFSC());
            txtBankBranch.setText(selectedItem.getBankBranch());
            txtName.setText(selectedItem.getName());
            txtAddress.setText(selectedItem.getAddress());
            txtContact.setText(selectedItem.getContact());
            txtAltContact.setText(selectedItem.getAltContact());
            txtGSTIN.setText(selectedItem.getUID());
            txtBankAccountNo.setText(selectedItem.getAccountNo());
            btnUpdate.setText("Update - " + Updating);
        }

    }

    private void _Updateemployee() {
        if (Updating.trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Select employee");
            alert.showAndWait();
        } else if (txtName.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Select employee Name");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure want To add New employee??");
            alert.setContentText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean addNewemployee = employeeController.updateemployee(Updating, txtName.getText().trim(), txtAddress.getText().trim(), txtContact.getText().trim(), txtAltContact.getText().trim(), txtCiustomerEmail.getText().trim(), txtGSTIN.getText().trim(), txtBankAccountNo.getText().trim(), txtBankIFSC.getText().trim(), txtBankBranch.getText().trim());
                if (addNewemployee) {
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("Success");
                    alert2.setHeaderText(null);
                    alert2.setContentText("employee Updated Successfully");
                    alert2.showAndWait();
                    _clearfields();
                    _loademployeeTable();
                }
            }

        }
    }

}
