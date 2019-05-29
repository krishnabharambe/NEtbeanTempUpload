/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sales;

import static Common.CFPC.setDatePicker;
import PDFGeneration.GSTINV;
import Sales.Customer.FXMLCustomerManagerController;
import dbController.CustomerController;
import static dbController.CustomerController.getAllCustomerName;
import static dbController.CustomerController.getCustomerIdFromName;
import static dbController.CustomerController.getCustomerObservableList;
import dbController.Customers;
import dbController.ProfileController;
import dbController.SalesController;
import static dbController.SalesController.checkSaleDelivery;
import static dbController.SalesController.checkSalePayment;
import static dbController.SalesController.checkgetSales;
import static dbController.SalesController.getCustomerNameFromInvoiceId;
import static dbController.SalesController.getFinalCalculationsInvoice;
import static dbController.SalesController.getMaxSaleId;
import static dbController.SalesController.getSaleDelivery;
import static dbController.SalesController.getSalePayment;
import static dbController.SalesController.getSales;
import static dbController.SalesController.getgstdata;
import static dbController.SalesController.getmytranscationByInvoiceIdObservableList;
import static dbController.SalesController.setdatatoSaleDelivery;
import static dbController.SalesController.setdatatoSalePayment;
import static dbController.SalesController.setdatatoSales;
import static dbController.SalesController.setdatatomyTranscation;
import static dbController.SalesController.updateCustomerInSales;
import static dbController.SalesController.updateInvoiceDateInSales;
import static dbController.SalesController.updatedatatoSaleODeliveryUsingInvoiceId;
import static dbController.SalesController.updatedatatoSaleOPaymentUsingInvoiceId;
import dbController.Stock;
import dbController.StockController;
import static dbController.StockController.addSoldStockOnTranscation;
import static dbController.StockController.getAllStockName;
import static dbController.StockController.getStockCostFromName;
import static dbController.StockController.getStockHSNFromName;
import static dbController.StockController.getStockIdFromName;
import static dbController.StockController.getStockObservableList;
import static dbController.StockController.getStockPriceFromName;
import static dbController.StockController.getStockRemainFromName;
import dbController.mytranscation;
import dbController.tbgsttable;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLSalesController implements Initializable {

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
    private TableView<Customers> tbcustomer;
    @FXML
    private TableColumn<Customers, String> tbcustomerColId;
    @FXML
    private TableColumn<Customers, String> tbcustomercolname;

    ObservableList<Customers> CustomerData = FXCollections.observableArrayList(getCustomerObservableList());
    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtCustomerId;
    @FXML
    private DatePicker txtInvoiceDate;
    @FXML
    private TableView<Stock> tbStock;
    @FXML
    private TableColumn<Stock, String> tbStockcolid;
    @FXML
    private TableColumn<Stock, String> tbstockcolname;
    ObservableList<Stock> StockData = FXCollections.observableArrayList(getStockObservableList());

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
    private TableView<mytranscation> tbtransaction;
    @FXML
    private TableColumn<mytranscation, String> tbcoltransid;
    @FXML
    private TableColumn<mytranscation, String> tbcolstaockname;
    @FXML
    private TableColumn<mytranscation, String> tbcolhsn;
    @FXML
    private TableColumn<mytranscation, String> tbcolprice;
    @FXML
    private TableColumn<mytranscation, String> tbcoldiscount;
    @FXML
    private TableColumn<mytranscation, String> tbcolgst;
    @FXML
    private TableColumn<mytranscation, String> tbcolqty;
    @FXML
    private TableColumn<mytranscation, String> tbcoltotal;
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

    ObservableList<mytranscation> mystranscation;
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

        txtInvoiceId.setText(String.valueOf(getMaxSaleId() + 1));
        loadcomboboxes();

        setDatePicker(txtInvoiceDate);
        setDatePicker(PaymentDate);
        setDatePicker(DeliveryDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");//        String format = LocalDate.now().format(formatter);
        txtInvoiceDate.setValue(LocalDate.now());

        TextFields.bindAutoCompletion(txtCustomerName, getAllCustomerName());
        TextFields.bindAutoCompletion(txtStockName, getAllStockName());
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
                    Logger.getLogger(FXMLSalesController.class.getName()).log(Level.SEVERE, null, ex);
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
                boolean addNewCustomer = CustomerController.addNewCustomer(txtName.getText().trim(), txtAddress.getText().trim(), txtAddressPincode.getText().trim(), txtContact.getText().trim(), txtAltContact.getText().trim(), txtTelephone.getText().trim(), txtCiustomerEmail.getText().trim(), txtWebsite.getText().trim(), txtGSTIN.getText().trim(), txtPanTan.getText().trim(), txtBankAccountNo.getText().trim(), txtBankIFSC.getText().trim(), txtBankBranch.getText().trim());
                if (addNewCustomer) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Success");
                    alert2.setHeaderText(null);
                    alert2.setContentText("New Customer Added Successfully");
                    alert2.showAndWait();
                    _clearfields();
                    TextFields.bindAutoCompletion(txtCustomerName, getAllCustomerName());
                }
            }

        }
    }

    private void _loadCustomerTable() {
        CustomerData = FXCollections.observableArrayList(getCustomerObservableList());
        tbcustomerColId.setCellValueFactory(new PropertyValueFactory<Customers, String>("id"));
        tbcustomercolname.setCellValueFactory(new PropertyValueFactory<Customers, String>("Name"));
        tbcustomer.setItems(CustomerData);
    }

    private void _LoadCustomerId() {
        txtCustomerId.setText(getCustomerIdFromName(txtCustomerName.getText().trim()));
    }

    private void _LoadStockId() {
        txtStockid.setText(getStockIdFromName(txtStockName.getText().trim()));
        txtRstock.setText(getStockRemainFromName(txtStockName.getText().trim()));
        txtHSN.setText(getStockHSNFromName(txtStockName.getText().trim()));
        txtPrice.setText(getStockPriceFromName(txtStockName.getText().trim()));
        StockCost = getStockCostFromName(txtStockName.getText().trim());
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
            Customers selectedItem = tbcustomer.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                txtCustomerId.setText(selectedItem.getId());
                txtCustomerName.setText(selectedItem.getName());
                txtStockName.requestFocus();
            }
        }
    }

    private void _loadStockTable() {
        StockData = FXCollections.observableArrayList(getStockObservableList());
        tbStockcolid.setCellValueFactory(new PropertyValueFactory<Stock, String>("Stockid"));
        tbstockcolname.setCellValueFactory(new PropertyValueFactory<Stock, String>("StockTitle"));
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
            Stock selectedItem = tbStock.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                txtStockName.setText(selectedItem.getStockTitle());
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
            mytranscation Selecteditem = tbtransaction.getSelectionModel().getSelectedItem();
            boolean setdata = SalesController.deleteFromTranscation(Selecteditem.getId());
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

            int checkgetSales = checkgetSales(txtInvoiceId.getText().trim());
            if (checkgetSales == 0) {
                setdatatoSales(txtInvoiceDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString(), txtCustomerId.getText());
//                setdatatoSalePayment(txtInvoiceId.getText().trim(), txtInvoiceDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), txtCustomerId.getText(), "", "", "", "");
//                setdatatoSaleDelivery(txtInvoiceId.getText().trim(), txtInvoiceDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), txtCustomerId.getText(), "", "", "", "");

            } else {
//                check for change in customerid and Invoicedate
                ResultSet sales = getSales(txtInvoiceId.getText().trim());
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
                            boolean updateCustomerInSales = updateCustomerInSales(txtInvoiceId.getText().trim(), txtCustomerId.getText().trim());
                            if (updateCustomerInSales) {
                                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                                alert2.setTitle("Success");
                                alert2.setHeaderText(null);
                                alert2.setContentText("Customer Updated Successfu•lly");
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
                            boolean updateCustomerInSales = updateInvoiceDateInSales(txtInvoiceId.getText().trim(), txtInvoiceDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
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
                    Logger.getLogger(FXMLSalesController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

//            add transcation
            boolean setdatatomyTranscation = setdatatomyTranscation(txtStockid.getText().trim(), txtCustomerId.getText().trim(), txtInvoiceId.getText().trim(), txtStockName.getText().trim(), txtHSN.getText().trim(), StockCost, txtPrice.getText().trim(), txtdiscount.getText().trim(), "", "", txtGST.getText().trim(), String.valueOf(txtquantity.getText().trim()), txttaxable.getText().trim());
            if (setdatatomyTranscation) {
                addSoldStockOnTranscation(txtStockid.getText().trim(), txtquantity.getText().trim());
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
        mystranscation = FXCollections.observableArrayList(getmytranscationByInvoiceIdObservableList(txtInvoiceId.getText().trim()));

        tbcoltransid.setCellValueFactory(new PropertyValueFactory<mytranscation, String>("id"));
        tbcolstaockname.setCellValueFactory(new PropertyValueFactory<mytranscation, String>("StockTitle"));
        tbcolhsn.setCellValueFactory(new PropertyValueFactory<mytranscation, String>("StockHSN"));
        tbcolprice.setCellValueFactory(new PropertyValueFactory<mytranscation, String>("StockPrice"));
        tbcoldiscount.setCellValueFactory(new PropertyValueFactory<mytranscation, String>("Discount"));
        tbcolgst.setCellValueFactory(new PropertyValueFactory<mytranscation, String>("StockGST"));
        tbcolqty.setCellValueFactory(new PropertyValueFactory<mytranscation, String>("Quantity"));
        tbcoltotal.setCellValueFactory(new PropertyValueFactory<mytranscation, String>("Total"));

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
            Logger.getLogger(FXMLSalesController.class.getName()).log(Level.SEVERE, null, ex);
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
            ResultSet salePayment = getSalePayment(txtInvoiceId.getText().trim());
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

            ResultSet saleDelivery = getSaleDelivery(txtInvoiceId.getText().trim());
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
            Logger.getLogger(FXMLSalesController.class.getName()).log(Level.SEVERE, null, ex);
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

            ResultSet data = getCustomerNameFromInvoiceId(txtInvoiceId.getText().trim());
            txtCustomerId.setText(data.getString("custid"));
            txtCustomerName.setText(data.getString("Name"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String date = data.getString("saleDate");
            LocalDate localDate = LocalDate.parse(date, formatter);
            txtInvoiceDate.setValue(localDate);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLSalesController.class.getName()).log(Level.SEVERE, null, ex);
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
        int checkSalePayment = checkSalePayment(txtInvoiceId.getText().trim());
        if (checkSalePayment == 0) {
            boolean setdatatoSalePayment = setdatatoSalePayment(
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

            boolean updatedatatoSaleOPaymentUsingInvoiceId = updatedatatoSaleOPaymentUsingInvoiceId(
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
        int checkSaleDelivery = checkSaleDelivery(txtInvoiceId.getText().trim());
        if (checkSaleDelivery == 0) {
            boolean setdatatoSaleDelivery = setdatatoSaleDelivery(
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

            boolean updatedatatoSaleODeliveryUsingInvoiceId = updatedatatoSaleODeliveryUsingInvoiceId(
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
            new GSTINV(txtInvoiceId.getText().trim());
        }
    }

}
