/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Profile;

import Sales.Customer.FXMLCustomerManagerController;
import dbController.InvoiceTerms;
import static dbController.ProfileController.CheckBusinessAccountForNumberOfrecords;
import static dbController.ProfileController.CheckBusinessDetailForNumberOfrecords;
import static dbController.ProfileController.addGSTdefault;
import static dbController.ProfileController.countupdateGSTdefault;
import static dbController.ProfileController.getBusinessAccount;
import static dbController.ProfileController.getBusinessDetail;
import static dbController.ProfileController.getGSTDefault;
import static dbController.ProfileController.setdatatoBusinessAccount;
import static dbController.ProfileController.setdatatoBusinessDetail;
import static dbController.ProfileController.updateGSTdefault;
import static dbController.ProfileController.updatedatatoBusinessAccount;
import static dbController.ProfileController.updatedatatoBusinessDetail;
import dbController.QuotationTerms;
import static dbController.TermsAndConditionsController.DeleteInvoiceTerms;
import static dbController.TermsAndConditionsController.DeleteQuotationTerms;
import static dbController.TermsAndConditionsController.Deletepurchaseterms;
import static dbController.TermsAndConditionsController.getQuotationtermsObservableList;
import static dbController.TermsAndConditionsController.getinvoicetermsObservableList;
import static dbController.TermsAndConditionsController.getpurchasetermsObservableList;
import static dbController.TermsAndConditionsController.setdatatoInvoiceTerms;
import static dbController.TermsAndConditionsController.setdatatoQuotationTerms;
import static dbController.TermsAndConditionsController.setdatatopurchaseTerms;
import static dbController.UserController.GetAdminPassword;
import static dbController.UserController.updateAdminPassword;
import dbController.purchaseTerms;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
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
public class FXMLProfileManagerController implements Initializable {

    @FXML
    private TextField txtBusinessName;
    @FXML
    private TextField txtBusinessMailingName;
    @FXML
    private TextField txtBusinessContact;
    @FXML
    private TextArea txtBusinessAddress;
    @FXML
    private TextField txtBusinessEmail;
    @FXML
    private TextField txtBusinessGSTIN;
    @FXML
    private TextField txtBusinessPanTan;
    @FXML
    private TextField txtBusinessWebsite;
    @FXML
    private TextField txtBusinessTelephone;
    @FXML
    private Button btnAddUpdateBusinessDetails;
    @FXML
    private TextField txtBusinessBankAccountNo;
    @FXML
    private TextField txtBusinessBankIFSC;
    @FXML
    private TextField txtBusinessBranch;
    @FXML
    private Button btnaddupdateAccount;
    @FXML
    private TextField txtInvoicedescription;
    @FXML
    private TableView<InvoiceTerms> tbInvoiceTable;
    @FXML
    private TableColumn<InvoiceTerms, String> tbcolinvocietermsid;
    @FXML
    private TableColumn<InvoiceTerms, String> tbcolinvoicetermsDescription;
    ObservableList<InvoiceTerms> invoicedata = FXCollections.observableArrayList(getinvoicetermsObservableList());
    @FXML
    private TextField txtquotationDescription;
    @FXML
    private TableView<QuotationTerms> tbQuotationTable;
    @FXML
    private TableColumn<QuotationTerms, String> tbcolQuotationId;
    @FXML
    private TableColumn<QuotationTerms, String> tbcolQuotationDescription;
    ObservableList<QuotationTerms> quotationdata = FXCollections.observableArrayList(getQuotationtermsObservableList());
    @FXML
    private TextField txtPurchaseDescription;
    @FXML
    private TableView<purchaseTerms> tbPurchase;
    @FXML
    private TableColumn<purchaseTerms, String> tbcolPurchaseId;
    @FXML
    private TableColumn<purchaseTerms, String> tbcolPurchasedescription;
    ObservableList<purchaseTerms> purchasedata = FXCollections.observableArrayList(getpurchasetermsObservableList());
    @FXML
    private TextField txtnewGSTRate;
    @FXML
    private TextField txtOldPassword;
    @FXML
    private TextField txtNewPassword;
    @FXML
    private TextField txtConfirmNewPassword;
    @FXML
    private AnchorPane profilemanagerpane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (CheckBusinessDetailForNumberOfrecords() > 0) {
            setBusinessDetailsFields();
        } else {
            btnAddUpdateBusinessDetails.setText("Add Details");
        }

        if (CheckBusinessAccountForNumberOfrecords() > 0) {
            setBusinessAccountFields();
        } else {
            btnaddupdateAccount.setText("Add Account");
        }

        loadInvoiceTermsTable();
        loadQuotationTermsTable();
        loadPurchaseTermsTable();
        loadGSTDafault();

        profilemanagerpane.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.HOME == event.getCode() || KeyCode.END == event.getCode() || KeyCode.ESCAPE == event.getCode()) {
                try {
                    javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Dashboard/FXMLDashboard.fxml"));
                    profilemanagerpane.getChildren().setAll(load3);
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

    @FXML
    private void actionAddUpdateBusinessDetails(ActionEvent event) {

        if (txtBusinessName.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please,Check Name Field.");
            alert.showAndWait();
        } else if (txtBusinessMailingName.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please,Check Mailing Name Field.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure Want to Add/Update???");
            alert.setContentText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                int CheckBusinessDetailForNumberOfrecords = CheckBusinessDetailForNumberOfrecords();
                if (CheckBusinessDetailForNumberOfrecords > 0) {
                    boolean setdatatoBusinessDetail2 = updatedatatoBusinessDetail(txtBusinessName.getText().trim(), txtBusinessMailingName.getText().trim(), txtBusinessContact.getText().trim(), txtBusinessAddress.getText().trim(), txtBusinessEmail.getText().trim(), txtBusinessGSTIN.getText().trim(), txtBusinessPanTan.getText().trim(), txtBusinessWebsite.getText().trim(), txtBusinessTelephone.getText().trim());
                    if (setdatatoBusinessDetail2) {
                        Alert alert2 = new Alert(AlertType.INFORMATION);
                        alert2.setTitle("ERROR");
                        alert2.setHeaderText("Business Details Updated Sucessfully");
                        alert2.setContentText(null);
                        alert2.showAndWait();
                    }
                } else {
                    boolean setdatatoBusinessDetail = setdatatoBusinessDetail(txtBusinessName.getText().trim(), txtBusinessMailingName.getText().trim(), txtBusinessContact.getText().trim(), txtBusinessAddress.getText().trim(), txtBusinessEmail.getText().trim(), txtBusinessGSTIN.getText().trim(), txtBusinessPanTan.getText().trim(), txtBusinessWebsite.getText().trim(), txtBusinessTelephone.getText().trim());
                    if (setdatatoBusinessDetail) {
                        Alert alert2 = new Alert(AlertType.INFORMATION);
                        alert2.setTitle("ERROR");
                        alert2.setHeaderText("Business Details Added Sucessfully");
                        alert2.setContentText(null);
                        alert2.showAndWait();
                    }
                }
            }
        }
    }

    private void setBusinessDetailsFields() {
        try {
            ResultSet businessDetail = getBusinessDetail();
            txtBusinessName.setText(businessDetail.getString("BusinessName"));
            txtBusinessMailingName.setText(businessDetail.getString("BusinessMailing"));
            txtBusinessContact.setText(businessDetail.getString("BusinessContact"));
            txtBusinessAddress.setText(businessDetail.getString("BusinessAddress"));
            txtBusinessEmail.setText(businessDetail.getString("BusinessEmail"));
            txtBusinessGSTIN.setText(businessDetail.getString("BusinessGSTIN"));
            txtBusinessPanTan.setText(businessDetail.getString("BusinessPanTan"));
            txtBusinessWebsite.setText(businessDetail.getString("BusinessWebsite"));
            txtBusinessTelephone.setText(businessDetail.getString("BusinessTelephone"));
        } catch (SQLException ex) {
            Logger.getLogger(FXMLProfileManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setBusinessAccountFields() {
        ResultSet businessAccount = getBusinessAccount();
        txtBusinessBankAccountNo.setText("BusinessBankAccount");
        txtBusinessBankIFSC.setText("BusinessBankIFSC");
        txtBusinessBranch.setText("BusinessBankBranch");
    }

    @FXML
    private void actionAddUpdateBusinessAccount(ActionEvent event) {
        if (txtBusinessBankAccountNo.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please,Check Bank Account NO Field.");
            alert.showAndWait();
        } else if (txtBusinessBankIFSC.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please,Check Bank IFSC Field.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure Want to Add/Update???");
            alert.setContentText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                int CheckBusinessAccountForNumberOfrecords = CheckBusinessAccountForNumberOfrecords();
                if (CheckBusinessAccountForNumberOfrecords > 0) {
                    boolean updatedatatoBusinessAccount = updatedatatoBusinessAccount(txtBusinessBankAccountNo.getText().trim(), txtBusinessBankIFSC.getText().trim(), txtBusinessBranch.getText().trim());
                    if (updatedatatoBusinessAccount) {
                        Alert alert2 = new Alert(AlertType.INFORMATION);
                        alert2.setTitle("ERROR");
                        alert2.setHeaderText("Business Account Updated Sucessfully");
                        alert2.setContentText(null);
                        alert2.showAndWait();
                    }
                } else {
                    boolean setdatatoBusinessAccount = setdatatoBusinessAccount(txtBusinessBankAccountNo.getText().trim(), txtBusinessBankIFSC.getText().trim(), txtBusinessBranch.getText().trim());
                    if (setdatatoBusinessAccount) {
                        Alert alert2 = new Alert(AlertType.INFORMATION);
                        alert2.setTitle("ERROR");
                        alert2.setHeaderText("Business Account Added Sucessfully");
                        alert2.setContentText(null);
                        alert2.showAndWait();
                    }
                }
            }
        }
    }

    @FXML
    private void actionaddInvoiceTerms(ActionEvent event) {
        if (txtInvoicedescription.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please,Check Invoice Description Field.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure Want to Add ???");
            alert.setContentText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean setdatatoInvoiceTerms = setdatatoInvoiceTerms(txtInvoicedescription.getText().trim());
                if (setdatatoInvoiceTerms) {
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("ERROR");
                    alert2.setHeaderText("Business Invoice Term Added Sucessfully");
                    alert2.setContentText(null);
                    txtInvoicedescription.setText("");
                    loadInvoiceTermsTable();
                    alert2.showAndWait();
                }
            }
        }
    }

    private void loadInvoiceTermsTable() {
        invoicedata = FXCollections.observableArrayList(getinvoicetermsObservableList());
        tbcolinvocietermsid.setCellValueFactory(new PropertyValueFactory<InvoiceTerms, String>("Id"));
        tbcolinvoicetermsDescription.setCellValueFactory(new PropertyValueFactory<InvoiceTerms, String>("Description"));
        tbInvoiceTable.setItems(invoicedata);
    }

    @FXML
    private void actionaddQuotationTerms(ActionEvent event) {
        if (txtquotationDescription.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please,Check Invoice Description Field.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure Want to Add ???");
            alert.setContentText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean setdatatoQuotationTerms = setdatatoQuotationTerms(txtquotationDescription.getText().trim());
                if (setdatatoQuotationTerms) {
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("ERROR");
                    alert2.setHeaderText("Business Quotation Term Added Sucessfully");
                    alert2.setContentText(null);
                    txtquotationDescription.setText("");
                    loadQuotationTermsTable();
                    alert2.showAndWait();
                }
            }
        }
    }

    @FXML
    private void actionaddPurchaseTerms(ActionEvent event) {
        if (txtPurchaseDescription.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please,Check Invoice Description Field.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure Want to Add ???");
            alert.setContentText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean setdatatopurchaseTerms = setdatatopurchaseTerms(txtPurchaseDescription.getText().trim());
                if (setdatatopurchaseTerms) {
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("ERROR");
                    alert2.setHeaderText("Business Purchase Term Added Sucessfully");
                    alert2.setContentText(null);
                    txtPurchaseDescription.setText("");
                    loadPurchaseTermsTable();
                    alert2.showAndWait();
                }
            }
        }
    }

    private void loadQuotationTermsTable() {
        quotationdata = FXCollections.observableArrayList(getQuotationtermsObservableList());
        tbcolQuotationId.setCellValueFactory(new PropertyValueFactory<QuotationTerms, String>("Id"));
        tbcolQuotationDescription.setCellValueFactory(new PropertyValueFactory<QuotationTerms, String>("Description"));
        tbQuotationTable.setItems(quotationdata);
    }

    private void loadPurchaseTermsTable() {
        purchasedata = FXCollections.observableArrayList(getpurchasetermsObservableList());
        tbcolPurchaseId.setCellValueFactory(new PropertyValueFactory<purchaseTerms, String>("Id"));
        tbcolPurchasedescription.setCellValueFactory(new PropertyValueFactory<purchaseTerms, String>("Description"));
        tbPurchase.setItems(purchasedata);
    }

    @FXML
    private void actionDeleteInvocieTerms(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DELETE)) {
            Alert alert2 = new Alert(AlertType.CONFIRMATION);
            alert2.setTitle("Confirmation");
            alert2.setHeaderText("Sure Want to Delete ???");
            alert2.setContentText(null);
            Optional<ButtonType> result = alert2.showAndWait();
            if (result.get() == ButtonType.OK) {
                InvoiceTerms selectedItem = tbInvoiceTable.getSelectionModel().getSelectedItem();
                boolean DeleteInvoiceTerms = DeleteInvoiceTerms(selectedItem.getId());
                if (DeleteInvoiceTerms) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    loadInvoiceTermsTable();
                    alert.setContentText("Term Deleted Successfully");
                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    private void actionDeleteQuotationTerms(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DELETE)) {
            Alert alert2 = new Alert(AlertType.CONFIRMATION);
            alert2.setTitle("Confirmation");
            alert2.setHeaderText("Sure Want to Delete ???");
            alert2.setContentText(null);
            Optional<ButtonType> result = alert2.showAndWait();
            if (result.get() == ButtonType.OK) {
                QuotationTerms selectedItem = tbQuotationTable.getSelectionModel().getSelectedItem();
                boolean DeleteQuotationTerms = DeleteQuotationTerms(selectedItem.getId());
                if (DeleteQuotationTerms) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    loadQuotationTermsTable();
                    alert.setContentText("Term Deleted Successfully");
                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    private void actionDeletePurchaseTerms(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DELETE)) {
            Alert alert2 = new Alert(AlertType.CONFIRMATION);
            alert2.setTitle("Confirmation");
            alert2.setHeaderText("Sure Want to Delete ???");
            alert2.setContentText(null);
            Optional<ButtonType> result = alert2.showAndWait();
            if (result.get() == ButtonType.OK) {
                purchaseTerms selectedItem = tbPurchase.getSelectionModel().getSelectedItem();
                boolean Deletepurchaseterms = Deletepurchaseterms(selectedItem.getId());
                if (Deletepurchaseterms) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    loadPurchaseTermsTable();
                    alert.setContentText("Term Deleted Successfully");
                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    private void actionUpdateGstRate(ActionEvent event) {
        if (txtnewGSTRate.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtnewGSTRate.setStyle("-fx-border-color: null");
            int countupdateGSTdefault = countupdateGSTdefault();
            if (countupdateGSTdefault == 0) {
//            ADD
                boolean addGSTdefault = addGSTdefault(txtnewGSTRate.getText().trim());
                if (addGSTdefault) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Updated Sccuessfully");
                    alert.setHeaderText(null);
                    alert.setContentText("Updated Sccuessfully");
                    alert.showAndWait();
                }
            } else {
//uPDATE

                boolean updateGSTdefault = updateGSTdefault(txtnewGSTRate.getText().trim());
                if (updateGSTdefault) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Updated Sccuessfully");
                    alert.setHeaderText(null);
                    alert.setContentText("Updated Sccuessfully");
                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please check GST Percentage");
            alert.showAndWait();
        }

    }

    private void loadGSTDafault() {
        txtnewGSTRate.setText(getGSTDefault());
    }

    @FXML
    private void actionPasswordChange(ActionEvent event) {
        txtNewPassword.getText().trim();
        txtOldPassword.getText().trim();

        if (txtOldPassword.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check all Passwords");

            alert.showAndWait();
        } else if (txtConfirmNewPassword.getText().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check all Passwords");

            alert.showAndWait();
        } else if (txtNewPassword.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check all Passwords");

            alert.showAndWait();
        } else if (!txtNewPassword.getText().trim().equals(txtConfirmNewPassword.getText().trim())) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check all Passwords");

            alert.showAndWait();
        } else if (!txtOldPassword.getText().trim().equals(GetAdminPassword())) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check all Passwords");

            alert.showAndWait();
        } else {
            //update password
            boolean updateAdminPassword = updateAdminPassword(txtNewPassword.getText().trim());
            if (updateAdminPassword) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Password Updated Successfully");
                alert.showAndWait();
            }
        }
    }

}
