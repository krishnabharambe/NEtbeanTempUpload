/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Purchase;

import static Common.CFPC.setDatePicker;
import PDFGeneration.GSTINV;
import PDFGeneration.PurchaseGSTReceipt;
import dbController.PStock;
import dbController.ProfileController;
import static dbController.PurchaseController.checkPurchaseDelivery;
import static dbController.PurchaseController.checkPurchasePayment;
import static dbController.PurchaseController.checkgetPurchase;
import static dbController.PurchaseController.deleteFromTranscation;
import static dbController.PurchaseController.getFinalCalculationsInvoice;
import static dbController.PurchaseController.getMaxPurchaseId;
import static dbController.PurchaseController.getPurchase;
import static dbController.PurchaseController.getPurchaseDelivery;
import static dbController.PurchaseController.getPurchasePayment;
import static dbController.PurchaseController.getSupplierNameFromInvoiceId;
import static dbController.PurchaseController.getgstdata;
import static dbController.PurchaseController.getmyPtranscationByInvoiceIdObservableList;
import static dbController.PurchaseController.setdatatoPurchase;
import static dbController.PurchaseController.setdatatoPurchaseDelivery;
import static dbController.PurchaseController.setdatatoPurchasePayment;
import static dbController.PurchaseController.setdatatomyPTranscation;
import static dbController.PurchaseController.updateInvoiceDateInPurchase;
import static dbController.PurchaseController.updateSupplierInPurchase;
import static dbController.PurchaseController.updatedatatoPurchaseODeliveryUsingInvoiceId;
import static dbController.PurchaseController.updatedatatoPurchaseOPaymentUsingInvoiceId;
import static dbController.PurchaseStockController.addSoldPStockOnTranscation;
import static dbController.PurchaseStockController.getAllPStockName;
import static dbController.PurchaseStockController.getPStockCostFromName;
import static dbController.PurchaseStockController.getPStockHSNFromName;
import static dbController.PurchaseStockController.getPStockIdFromName;
import static dbController.PurchaseStockController.getPStockObservableList;
import static dbController.PurchaseStockController.getPStockPriceFromName;
import static dbController.PurchaseStockController.getPStockRemainFromName;
import dbController.Supplier;
import dbController.SupplierController;
import static dbController.SupplierController.getAllSupplierName;
import static dbController.SupplierController.getSupplierIdFromName;
import static dbController.SupplierController.getSupplierObservableList;
import dbController.myPtranscation;
import dbController.tbgsttable;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLPurchaseController implements Initializable {

    String StockCost = "0";
    private static DecimalFormat df2 = new DecimalFormat(".##");

    @FXML
    private AnchorPane SalePane;
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
    private TextField txtCiustomerEmail;
    @FXML
    private TextField txtWebsite;
    @FXML
    private TextField txtGSTIN;
    @FXML
    private TextField txtPanTan;
    @FXML
    private TextField txtBankAccountNo;
    @FXML
    private TextField txtBankIFSC;
    @FXML
    private TextField txtBankBranch;
    @FXML
    private TableView<Supplier> tbcustomer;
    @FXML
    private TableColumn<Supplier, String> tbcustomerColId;
    @FXML
    private TableColumn<Supplier, String> tbcustomercolname;

    ObservableList<Supplier> CustomerData = FXCollections.observableArrayList(getSupplierObservableList());
    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtCustomerId;
    @FXML
    private DatePicker txtInvoiceDate;
    @FXML
    private TableView<PStock> tbStock;
    @FXML
    private TableColumn<PStock, String> tbStockcolid;
    @FXML
    private TableColumn<PStock, String> tbstockcolname;
    ObservableList<PStock> StockData = FXCollections.observableArrayList(getPStockObservableList());

    @FXML
    private TextField txtInvoiceId;
    @FXML
    private TitledPane customerTP;
    @FXML
    private TitledPane StockTP;
    @FXML
    private TitledPane NewCustomerTP;
    @FXML
    private Accordion accordion;
    @FXML
    private TextField txtStockName;
    @FXML
    private TextField txtStockid;
    @FXML
    private TextField txtRstock;
    @FXML
    private TextField txtHSN;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtGST;
    @FXML
    private TextField txtdiscount;
    @FXML
    private TextField txtquantity;
    @FXML
    private TextField txttaxable;
    @FXML
    private TextField txtpayable;
    @FXML
    private TableView<myPtranscation> tbtransaction;
    @FXML
    private TableColumn<myPtranscation, String> tbcoltransid;
    @FXML
    private TableColumn<myPtranscation, String> tbcolstaockname;
    @FXML
    private TableColumn<myPtranscation, String> tbcolhsn;
    @FXML
    private TableColumn<myPtranscation, String> tbcolprice;
    @FXML
    private TableColumn<myPtranscation, String> tbcoldiscount;
    @FXML
    private TableColumn<myPtranscation, String> tbcolgst;
    @FXML
    private TableColumn<myPtranscation, String> tbcolqty;
    @FXML
    private TableColumn<myPtranscation, String> tbcoltotal;
    @FXML
    private TableView<tbgsttable> tbgst;
    @FXML
    private TableColumn<tbgsttable, String> tbcolgsthsn;
    @FXML
    private TableColumn<tbgsttable, String> tbcoltaxable;
    @FXML
    private TableColumn<tbgsttable, String> tbcolcgstpercentage;
    @FXML
    private TableColumn<tbgsttable, String> tbcolcgst;
    @FXML
    private TableColumn<tbgsttable, String> tbcolsgstpercentage;
    @FXML
    private TableColumn<tbgsttable, String> tbcolsgst;
    @FXML
    private TableColumn<tbgsttable, String> tbcolgstpercentage;
    @FXML
    private TableColumn<tbgsttable, String> tbcolgstgst;
    @FXML
    private TextField txtfinalsubtotal;
    @FXML
    private TextField txtfinalcgst;
    @FXML
    private TextField txtfinalsgst;
    @FXML
    private TextField txtfinalgst;
    @FXML
    private TextField txtfinaltotal;

    ObservableList<myPtranscation> mystranscation;
    @FXML
    private TextField txtPaymentRemark;
    @FXML
    private DatePicker PaymentDate;
    @FXML
    private ComboBox<String> txtPaymentStatus;
    @FXML
    private TextField txtDeliveryRemark;
    @FXML
    private DatePicker DeliveryDate;
    @FXML
    private ComboBox<String> txtDeliveryStatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        txtInvoiceId.setText(String.valueOf(getMaxPurchaseId() + 1));
        loadcomboboxes();

        setDatePicker(txtInvoiceDate);
        setDatePicker(PaymentDate);
        setDatePicker(DeliveryDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");//        String format = LocalDate.now().format(formatter);
        txtInvoiceDate.setValue(LocalDate.now());

        TextFields.bindAutoCompletion(txtCustomerName, getAllSupplierName());
        TextFields.bindAutoCompletion(txtStockName, getAllPStockName());
        _loadCustomerTable();
        _loadStockTable();
        _settxtGST();

        SalePane.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.HOME == event.getCode() || KeyCode.END == event.getCode() || KeyCode.ESCAPE == event.getCode()) {
                try {
                    javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Dashboard/FXMLDashboard.fxml"));
                    SalePane.getChildren().setAll(load3);
                    AnchorPane.setRightAnchor(load3, 0.0);
                    AnchorPane.setLeftAnchor(load3, 0.0);
                    AnchorPane.setTopAnchor(load3, 0.0);
                    AnchorPane.setBottomAnchor(load3, 0.0);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLPurchaseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        txtCustomerName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                _LoadCustomerId();
            }
        });

        txtStockName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                _LoadStockId();
            }

        });

        txtdiscount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (txtdiscount.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
                    txtdiscount.setStyle("-fx-border-color: null");
                    _calculate();
                } else {
                    txtdiscount.setStyle("-fx-border-color:red");
                }

            }

        });

        txtquantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (txtquantity.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
                    txtquantity.setStyle("-fx-border-color: null");
                    _calculate();
                } else {
                    txtquantity.setStyle("-fx-border-color:red");
                }
            }

        });
    }

    @FXML
    private void ActionReset(ActionEvent event) {
        _clearfields();
    }

    @FXML
    private void ActionReset1(KeyEvent event) {
        _clearfields();
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
    }

    private void _AddCustomer() {
        if (txtName.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, Select Customer Name");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure want To add New Customer??");
            alert.setContentText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean addNewCustomer = SupplierController.addNewSupplier(txtName.getText().trim(), txtAddress.getText().trim(), txtAddressPincode.getText().trim(), txtContact.getText().trim(), txtAltContact.getText().trim(), txtTelephone.getText().trim(), txtCiustomerEmail.getText().trim(), txtWebsite.getText().trim(), txtGSTIN.getText().trim(), txtPanTan.getText().trim(), txtBankAccountNo.getText().trim(), txtBankIFSC.getText().trim(), txtBankBranch.getText().trim());
                if (addNewCustomer) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Success");
                    alert2.setHeaderText(null);
                    alert2.setContentText("New Customer Added Successfully");
                    alert2.showAndWait();
                    _clearfields();
                }
            }

        }
    }

    private void _loadCustomerTable() {
        CustomerData = FXCollections.observableArrayList(getSupplierObservableList());
        tbcustomerColId.setCellValueFactory(new PropertyValueFactory<Supplier, String>("id"));
        tbcustomercolname.setCellValueFactory(new PropertyValueFactory<Supplier, String>("Name"));
        tbcustomer.setItems(CustomerData);
    }

    private void _LoadCustomerId() {
        txtCustomerId.setText(getSupplierIdFromName(txtCustomerName.getText().trim()));
    }

    private void _LoadStockId() {
        txtStockid.setText(getPStockIdFromName(txtStockName.getText().trim()));
        txtRstock.setText(getPStockRemainFromName(txtStockName.getText().trim()));
        txtHSN.setText(getPStockHSNFromName(txtStockName.getText().trim()));
        txtPrice.setText(getPStockPriceFromName(txtStockName.getText().trim()));
        StockCost = getPStockCostFromName(txtStockName.getText().trim());
    }

    @FXML
    private void actionMovetoCustomerSelection(ActionEvent event) {
        if (txtCustomerName.getText().trim().equals("")) {
            accordion.setExpandedPane(customerTP);
            tbcustomer.requestFocus();
        } else {
            txtStockName.requestFocus();
        }
    }

    @FXML
    private void actiontbcustomerkeyreleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            Supplier selectedItem = tbcustomer.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                txtCustomerId.setText(selectedItem.getId());
                txtCustomerName.setText(selectedItem.getName());
                txtStockName.requestFocus();
            }
        }
    }

    private void _loadStockTable() {
        StockData = FXCollections.observableArrayList(getPStockObservableList());
        tbStockcolid.setCellValueFactory(new PropertyValueFactory<PStock, String>("PStockid"));
        tbstockcolname.setCellValueFactory(new PropertyValueFactory<PStock, String>("PStockTitle"));
        tbStock.setItems(StockData);
        
    }

    @FXML
    private void actionMoveToStockSelection(ActionEvent event) {
        if (txtStockName.getText().trim().equals("")) {
            accordion.setExpandedPane(StockTP);
            tbStock.requestFocus();
        } else {
            txtdiscount.requestFocus();
        }
    }

    @FXML
    private void actiontbStockKeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            PStock selectedItem = tbStock.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                txtStockName.setText(selectedItem.getPStockTitle());
                txtdiscount.requestFocus();
            }
        }
    }

    private void _settxtGST() {
        txtGST.setText(ProfileController.getGSTDefault());
    }

    private void _calculate() {
        double Price = 0, GST = 0, Discount = 0, Quantity = 0;
        if (txtPrice.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtPrice.setStyle("-fx-border-color: null");
            Price = Double.valueOf(txtPrice.getText().trim());
        } else {
            txtPrice.setStyle("-fx-border-color:red");
        }

        if (txtGST.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtGST.setStyle("-fx-border-color: null");
            GST = Double.valueOf(txtGST.getText().trim());
        } else {
            txtGST.setStyle("-fx-border-color:red");
        }

        if (txtdiscount.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtdiscount.setStyle("-fx-border-color: null");
            Discount = Double.valueOf(txtdiscount.getText().trim());
        } else {
            txtdiscount.setStyle("-fx-border-color:red");
        }

        if (txtquantity.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtquantity.setStyle("-fx-border-color: null");
            Quantity = Double.valueOf(txtquantity.getText().trim());
        } else {
            txtquantity.setStyle("-fx-border-color:red");
        }

        double rowValueWithoutGSTDiscount = Price - (Price / 100) * Discount;

        double total = rowValueWithoutGSTDiscount + ((GST * rowValueWithoutGSTDiscount) / 100);
        txttaxable.setText(String.valueOf(df2.format(rowValueWithoutGSTDiscount * Quantity)));
        txtpayable.setText(String.valueOf(df2.format(total * Quantity)));

    }

    @FXML
    private void actionAddTranscation(ActionEvent event) {
        _addTranscation();
    }

    @FXML
    private void actionAddTranscation1(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            _addTranscation();
        }
    }

    @FXML
    private void actiontbkey(KeyEvent event) {
         if (event.getCode().equals(KeyCode.DELETE)) {
             myPtranscation Selecteditem = tbtransaction.getSelectionModel().getSelectedItem();
            boolean setdata = deleteFromTranscation(Selecteditem.getId());
            if (setdata) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Deleted Successfully");
                alert.showAndWait();
            }
        }
    }

    private void _addTranscation() {
        if (txtCustomerId.getText().trim().equals("") || txtStockid.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please, Select the Customer/Stock");
            alert.setContentText(null);
            alert.showAndWait();
        } else if (Double.valueOf(txttaxable.getText().trim()) < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please, Check All Calculations");
            alert.setContentText(null);
            alert.showAndWait();
        } else if (Double.valueOf(txtpayable.getText().trim()) < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please, Check All Calculations");
            alert.setContentText(null);
            alert.showAndWait();
        } else {

            int checkgetSales = checkgetPurchase(txtInvoiceId.getText().trim());
            if (checkgetSales == 0) {
                setdatatoPurchase(txtInvoiceDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString(), txtCustomerId.getText());
//                setdatatoSalePayment(txtInvoiceId.getText().trim(), txtInvoiceDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), txtCustomerId.getText(), "", "", "", "");
//                setdatatoSaleDelivery(txtInvoiceId.getText().trim(), txtInvoiceDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), txtCustomerId.getText(), "", "", "", "");

            } else {
//                check for change in customerid and Invoicedate
                ResultSet sales = getPurchase(txtInvoiceId.getText().trim());
                String customerid = null;
                String InvoiceDate = null;
                try {
                    customerid = sales.getString("CustomerId");
                    InvoiceDate = sales.getString("SaleDate");
                    if (!customerid.trim().equals(txtCustomerId.getText().trim())) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText("Sure Want To Change Customer??");
                        alert.setContentText("Are you ok with this?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
//                            updateCustomer
                            boolean updateCustomerInSales = updateSupplierInPurchase(txtInvoiceId.getText().trim(), txtCustomerId.getText().trim());
                            if (updateCustomerInSales) {
                                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                                alert2.setTitle("Success");
                                alert2.setHeaderText(null);
                                alert2.setContentText("Customer Updated Successfully");
                                alert2.showAndWait();
                            }
                        }
                    }

                    if (!txtInvoiceDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString().equals(InvoiceDate)) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText("Sure Want To Change Invoice Date??");
                        alert.setContentText("Are you ok with this?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
//                           Update InvoiceDate
                            boolean updateCustomerInSales = updateInvoiceDateInPurchase(txtInvoiceId.getText().trim(), txtInvoiceDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
                            if (updateCustomerInSales) {
                                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                                alert2.setTitle("Success");
                                alert2.setHeaderText(null);
                                alert2.setContentText("Invoice Date Updated Successfully");
                                alert2.showAndWait();
                            }
                        }
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(FXMLPurchaseController.class.getName()).log(Level.SEVERE, null, ex);
                } 

            }

//            add transcation
            boolean setdatatomyTranscation = setdatatomyPTranscation(txtStockid.getText().trim(), txtCustomerId.getText().trim(), txtInvoiceId.getText().trim(), txtStockName.getText().trim(), txtHSN.getText().trim(), StockCost, txtPrice.getText().trim(), txtdiscount.getText().trim(), "", "", txtGST.getText().trim(), String.valueOf(txtquantity.getText().trim()), txttaxable.getText().trim());
            if (setdatatomyTranscation) {
                addSoldPStockOnTranscation(txtStockid.getText().trim(), txtquantity.getText().trim());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                _loadmystrancationTable(txtInvoiceId.getText().trim());
                clearfields();
                alert.setHeaderText("Successfully Added ");
                alert.setContentText(null);
                alert.showAndWait();
                txtStockName.requestFocus();
            }

        }

    }

    private void _loadmystrancationTable(String trim) {
        mystranscation = FXCollections.observableArrayList(getmyPtranscationByInvoiceIdObservableList(txtInvoiceId.getText().trim()));

        tbcoltransid.setCellValueFactory(new PropertyValueFactory<myPtranscation, String>("id"));
        tbcolstaockname.setCellValueFactory(new PropertyValueFactory<myPtranscation, String>("StockTitle"));
        tbcolhsn.setCellValueFactory(new PropertyValueFactory<myPtranscation, String>("StockHSN"));
        tbcolprice.setCellValueFactory(new PropertyValueFactory<myPtranscation, String>("StockPrice"));
        tbcoldiscount.setCellValueFactory(new PropertyValueFactory<myPtranscation, String>("Discount"));
        tbcolgst.setCellValueFactory(new PropertyValueFactory<myPtranscation, String>("StockGST"));
        tbcolqty.setCellValueFactory(new PropertyValueFactory<myPtranscation, String>("Quantity"));
        tbcoltotal.setCellValueFactory(new PropertyValueFactory<myPtranscation, String>("Total"));

        tbtransaction.setItems(mystranscation);
        loadgsttable();
    }

    private void loadgsttable() {
        ObservableList<tbgsttable> data = FXCollections.observableArrayList(getgstdata(txtInvoiceId.getText().trim()));
        tbcolgsthsn.setCellValueFactory(new PropertyValueFactory<tbgsttable, String>("HSN"));
        tbcoltaxable.setCellValueFactory(new PropertyValueFactory<tbgsttable, String>("taxable"));
        tbcolcgstpercentage.setCellValueFactory(new PropertyValueFactory<tbgsttable, String>("cgstpercentage"));
        tbcolcgst.setCellValueFactory(new PropertyValueFactory<tbgsttable, String>("cgst"));
        tbcolsgstpercentage.setCellValueFactory(new PropertyValueFactory<tbgsttable, String>("sgstpercentage"));
        tbcolsgst.setCellValueFactory(new PropertyValueFactory<tbgsttable, String>("sgst"));
        tbcolgstpercentage.setCellValueFactory(new PropertyValueFactory<tbgsttable, String>("gstpercentage"));
        tbcolgstgst.setCellValueFactory(new PropertyValueFactory<tbgsttable, String>("gst"));
        tbgst.setItems(data);

        LoadFinalCalculations(txtInvoiceId.getText().trim());

    }

    private void LoadFinalCalculations(String InvoiceId) {
        try {
            ResultSet data = getFinalCalculationsInvoice(InvoiceId);
            if (data.next()) {
                if (data.getString("taxable") != null) {
                    txtfinalsubtotal.setText(df2.format(Double.valueOf(data.getString("taxable"))));
                    txtfinalcgst.setText(df2.format(Double.valueOf(data.getString("halfgst"))));
                    txtfinalsgst.setText(df2.format(Double.valueOf(data.getString("halfgst"))));
                    txtfinalgst.setText(df2.format(Double.valueOf(data.getString("fullGstTotal"))));
                    txtfinaltotal.setText(df2.format(Double.valueOf(data.getString("taxable")) + Double.valueOf(data.getString("fullGstTotal"))));
                } else {
                    txtfinalsubtotal.setText("0");
                    txtfinalcgst.setText("0");
                    txtfinalsgst.setText("0");
                    txtfinalgst.setText("0");
                    txtfinaltotal.setText("0");
                }
            } else {
                txtfinalsubtotal.setText("0");
                txtfinalcgst.setText("0");
                txtfinalsgst.setText("0");
                txtfinalgst.setText("0");
                txtfinaltotal.setText("0");
            }

            loadPaymentAndDelivery();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearfields() {
        txtStockName.setText("");
        txtStockid.setText("");
        txtdiscount.setText("");
        txtquantity.setText("");
        StockCost = null;
        txtHSN.setText("");
        txtRstock.setText("");
        txtPrice.setText("");
        txtpayable.setText("");
        txttaxable.setText("");
    }

    @FXML
    private void actionAddUpdatePayment(ActionEvent event) {

        _addupdatepayment();
    }

    private void loadcomboboxes() {

        txtPaymentStatus.getItems().addAll(
                "Pending",
                "In Process",
                "Completed"
        );

        txtDeliveryStatus.getItems().addAll(
                "Pending",
                "In Process",
                "Completed"
        );

    }

    private void loadPaymentAndDelivery() {
        try {
            ResultSet salePayment = getPurchasePayment(txtInvoiceId.getText().trim());
            if (salePayment.next()) {
//                txtPaymentType.setValue(salePayment.getString("PaymentType"));
                txtPaymentRemark.setText(salePayment.getString("PaymentRemark"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String date = salePayment.getString("PaymentDate");
                LocalDate localDate = LocalDate.parse(date, formatter);
                PaymentDate.setValue(localDate);
                txtPaymentStatus.setValue(salePayment.getString("paymentstatus"));
            } else {

            }

            ResultSet saleDelivery = getPurchaseDelivery(txtInvoiceId.getText().trim());
            if (saleDelivery.next()) {
//                txtDeliveyType.setValue(saleDelivery.getString("DeliveryType"));
                txtDeliveryRemark.setText(saleDelivery.getString("DeliveryRemark"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String date = saleDelivery.getString("DeliveryDate");
                LocalDate localDate = LocalDate.parse(date, formatter);
                DeliveryDate.setValue(localDate);
                txtDeliveryStatus.setValue(saleDelivery.getString("Deliverystatus"));
            } else {

            }

        } catch (SQLException ex) {
            Logger.getLogger(FXMLPurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionAddUpdateDelivey(ActionEvent event) {

        _addupdatedelivery();
    }

    void editLoader(String id) {
        try {
            txtInvoiceId.setText(id);
            _loadmystrancationTable(txtInvoiceId.getText().trim());

            ResultSet data = getSupplierNameFromInvoiceId(txtInvoiceId.getText().trim());
            txtCustomerId.setText(data.getString("custid"));
            txtCustomerName.setText(data.getString("Name"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String date = data.getString("PurchaseDate");
            LocalDate localDate = LocalDate.parse(date, formatter);
            txtInvoiceDate.setValue(localDate);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLPurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionAddUpdatePayment1(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            _addupdatepayment();
        }
    }

    @FXML
    private void actionAddUpdateDelivey1(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            _addupdatedelivery();
        }
    }

    private void _addupdatepayment() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int checkSalePayment = checkPurchasePayment(txtInvoiceId.getText().trim());
        if (checkSalePayment == 0) {
            boolean setdatatoSalePayment = setdatatoPurchasePayment(
                    txtInvoiceId.getText().trim(),
                    txtInvoiceDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString(),
                    txtCustomerId.getText().trim(),
                    txtPaymentStatus.getValue().toString(),
                    "",
                    txtPaymentRemark.getText().trim(),
                    PaymentDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
            if (setdatatoSalePayment) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setHeaderText("Updation Successfullyd");
                _loadmystrancationTable(txtInvoiceId.getText().trim());
                LoadFinalCalculations(txtInvoiceId.getText().trim());
                alert2.showAndWait();
            }
        } else {

            boolean updatedatatoSaleOPaymentUsingInvoiceId = updatedatatoPurchaseOPaymentUsingInvoiceId(
                    txtInvoiceId.getText().trim(),
                    txtInvoiceDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString(),
                    txtCustomerId.getText().trim(),
                    txtPaymentStatus.getValue().toString(),
                    "",
                    txtPaymentRemark.getText().trim(),
                    PaymentDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());

            if (updatedatatoSaleOPaymentUsingInvoiceId) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setHeaderText("Updation Successfullyd");
                _loadmystrancationTable(txtInvoiceId.getText().trim());
                LoadFinalCalculations(txtInvoiceId.getText().trim());
                alert2.showAndWait();
            }
        }
    }

    private void _addupdatedelivery() {
        int checkSaleDelivery = checkPurchaseDelivery(txtInvoiceId.getText().trim());
        if (checkSaleDelivery == 0) {
            boolean setdatatoSaleDelivery = setdatatoPurchaseDelivery(
                    txtInvoiceId.getText().trim(),
                    txtInvoiceDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString(),
                    txtCustomerId.getText().trim(),
                    txtDeliveryStatus.getValue().toString(),
                    "",
                    txtDeliveryRemark.getText().trim(),
                    DeliveryDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
            if (setdatatoSaleDelivery) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setHeaderText("Updation Successfullyd");
                _loadmystrancationTable(txtInvoiceId.getText().trim());
                LoadFinalCalculations(txtInvoiceId.getText().trim());
                alert2.showAndWait();
            }
        } else {

            boolean updatedatatoSaleODeliveryUsingInvoiceId = updatedatatoPurchaseODeliveryUsingInvoiceId(
                    txtInvoiceId.getText().trim(),
                    txtInvoiceDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString(),
                    txtCustomerId.getText().trim(),
                    txtDeliveryStatus.getValue().toString(),
                    "",
                    txtDeliveryRemark.getText().trim(),
                    DeliveryDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
            if (updatedatatoSaleODeliveryUsingInvoiceId) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setHeaderText("Updation Successfullyd");
                _loadmystrancationTable(txtInvoiceId.getText().trim());
                LoadFinalCalculations(txtInvoiceId.getText().trim());
                alert2.showAndWait();
            }
        }
    }

    @FXML
    private void actionGeneratePDF(ActionEvent event) {
        _PDF();
    }

    @FXML
    private void ActionGeneratePDF1(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            _PDF();
        }
    }

    private void _PDF() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Sure Want to generate PDF??");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            new PurchaseGSTReceipt(txtInvoiceId.getText().trim());
        }
    }
    
}
