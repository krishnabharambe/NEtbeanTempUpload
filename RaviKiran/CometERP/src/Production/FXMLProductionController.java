/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Production;

import static Common.CFPC.setDatePicker;
import PDFGeneration.ProductionPrint;
import dbController.ProductionController;
import static dbController.ProductionController.AllProduction;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLProductionController implements Initializable {

    @FXML
    private AnchorPane ProductionPane;
    @FXML
    private TextField ProductionNo;
    @FXML
    private DatePicker ProductionDate;
    @FXML
    private TextField txtraw1;
    @FXML
    private TextField txtraw2;
    @FXML
    private TextField txtraw3;
    @FXML
    private TextField txtraw4;
    @FXML
    private TextField txtrawtotal;
    @FXML
    private TextField txtcalcium;
    @FXML
    private TextField txttitanium;
    @FXML
    private TextField txttbls;
    @FXML
    private TextField txtwax;
    @FXML
    private TextField txtNIL;
    @FXML
    private TextField txtFinalTotal;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtsize;
    @FXML
    private TextField txtpipes;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btnadd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ProductionNo.setText(String.valueOf(ProductionController.getmaxProductionId() + 1));
        setDatePicker(ProductionDate);
        ProductionDate.setValue(LocalDate.now());

        ProductionPane.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.HOME == event.getCode() || KeyCode.END == event.getCode() || KeyCode.ESCAPE == event.getCode()) {
                try {
                    javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Dashboard/FXMLDashboard.fxml"));
                    ProductionPane.getChildren().setAll(load3);
                    AnchorPane.setRightAnchor(load3, 0.0);
                    AnchorPane.setLeftAnchor(load3, 0.0);
                    AnchorPane.setTopAnchor(load3, 0.0);
                    AnchorPane.setBottomAnchor(load3, 0.0);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLProductionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        _ManageFields();
    }

    private void _ManageFields() {
        txtraw1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                _LoadTotal();
            }
        });
        txtraw2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                _LoadTotal();
            }
        });
        txtraw3.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                _LoadTotal();
            }
        });
        txtraw4.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                _LoadTotal();
            }
        });
        txtrawtotal.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                _LoadTotal();
            }
        });
        txtcalcium.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                _LoadTotal();
            }
        });
        txttitanium.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                _LoadTotal();
            }
        });
        txttbls.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                _LoadTotal();
            }
        });
        txtwax.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                _LoadTotal();
            }
        });
        txtNIL.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                _LoadTotal();
            }
        });

    }

    private void _LoadTotal() {
        double r1, r2, r3, r4;
        if (txtraw1.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtraw1.setStyle("-fx-border-color: null");
            r1 = Double.valueOf(txtraw1.getText().trim());
        } else {
            txtraw1.setStyle("-fx-border-color:red");
            r1 = 0;
        }
        if (txtraw2.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtraw2.setStyle("-fx-border-color: null");
            r2 = Double.valueOf(txtraw2.getText().trim());
        } else {
            txtraw2.setStyle("-fx-border-color:red");
            r2 = 0;
        }
        if (txtraw3.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtraw3.setStyle("-fx-border-color: null");
            r3 = Double.valueOf(txtraw3.getText().trim());
        } else {
            txtraw3.setStyle("-fx-border-color:red");
            r3 = 0;
        }
        if (txtraw4.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtraw4.setStyle("-fx-border-color: null");
            r4 = Double.valueOf(txtraw4.getText().trim());
        } else {
            txtraw4.setStyle("-fx-border-color:red");
            r4 = 0;
        }
        txtrawtotal.setText(String.valueOf(r4 + r3 + r2 + r1));
        if (txtrawtotal.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtrawtotal.setStyle("-fx-border-color: null");
            _LoadFinalSubTotal(r4 + r3 + r2 + r1);
        } else {
            txtrawtotal.setStyle("-fx-border-color:red");

        }

    }

    private void _LoadFinalSubTotal(double rawtotal) {
        double calcium, titanium, tbls, wax, nil, total;
        if (txtcalcium.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtcalcium.setStyle("-fx-border-color: null");
            calcium = Double.valueOf(txtcalcium.getText().trim());
        } else {
            txtcalcium.setStyle("-fx-border-color:red");
            calcium = 0;
        }

        if (txttitanium.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txttitanium.setStyle("-fx-border-color: null");
            titanium = Double.valueOf(txttitanium.getText().trim());
        } else {
            txttitanium.setStyle("-fx-border-color:red");
            titanium = 0;
        }

        if (txttbls.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txttbls.setStyle("-fx-border-color: null");
            tbls = Double.valueOf(txttbls.getText().trim());
        } else {
            txttbls.setStyle("-fx-border-color:red");
            tbls = 0;
        }

        if (txtwax.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtwax.setStyle("-fx-border-color: null");
            wax = Double.valueOf(txtwax.getText().trim());
        } else {
            txtwax.setStyle("-fx-border-color:red");
            wax = 0;
        }

        if (txtNIL.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            txtNIL.setStyle("-fx-border-color: null");
            nil = Double.valueOf(txtNIL.getText().trim());
        } else {
            txtNIL.setStyle("-fx-border-color:red");
            nil = 0;
        }

        txtFinalTotal.setText(String.valueOf(rawtotal + calcium + titanium + tbls + wax + nil));
    }

    @FXML
    private void addProductionTO(ActionEvent event) {
        _addProductionNew();
    }

    void editLoader(String productionid) {
        try {
            //        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            ResultSet AllProduction = AllProduction(productionid);
            ProductionNo.setText(AllProduction.getString("productionid"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String date = AllProduction.getString("productiondate");
            LocalDate localDate = LocalDate.parse(date, formatter);
            ProductionDate.setValue(localDate);

            txtraw1.setText(AllProduction.getString("raw1"));
            txtraw2.setText(AllProduction.getString("raw2"));
            txtraw3.setText(AllProduction.getString("raw3"));
            txtraw4.setText(AllProduction.getString("raw4"));
            txtrawtotal.setText(AllProduction.getString("rawtotal"));

            txtcalcium.setText(AllProduction.getString("calciumtotal"));
            txttitanium.setText(AllProduction.getString("titaniumtotal"));
            txttbls.setText(AllProduction.getString("tblstotal"));
            txtwax.setText(AllProduction.getString("waxtotal"));
            txtNIL.setText(AllProduction.getString("liltotal"));
            txtFinalTotal.setText(AllProduction.getString("fulltotal"));
            txtName.setText(AllProduction.getString("name"));
            txtsize.setText(AllProduction.getString("size"));
            txtpipes.setText(AllProduction.getString("pipes"));

            btnadd.setVisible(false);
            btnupdate.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLProductionController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void actionUpdateProduction(ActionEvent event) {
        _updateProduction();
    }

    @FXML
    private void actionUpdateProduction1(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            _updateProduction();
        }
    }

    private void _updateProduction() {
        boolean addProduction = ProductionController.updateaproduction(ProductionNo.getText().trim(), ProductionDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString(), txtraw1.getText().trim(), txtraw2.getText().trim(), txtraw3.getText().trim(), txtraw4.getText().trim(), txtrawtotal.getText().trim(), txtcalcium.getText().trim(), txttitanium.getText().trim(), txttbls.getText().trim(), txtwax.getText().trim(), txtNIL.getText().trim(), txtFinalTotal.getText().trim(), txtName.getText().trim(), txtsize.getText().trim(), txtpipes.getText().trim());
        if (addProduction) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Production Updated SuccessFully");
            alert.showAndWait();
        }
    }

    @FXML
    private void actionGenerateProductionPrint(ActionEvent event) {
        _printProduction();
    }

    @FXML
    private void actionGenerateProductionPrint1(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            _printProduction();
        }
    }

    private void _printProduction() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Sure Want to Print??");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            new ProductionPrint(ProductionNo.getText().trim());
        }
    }

    @FXML
    private void addProductionTO1(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            _addProductionNew();
        }
    }

    private void _addProductionNew() {
        boolean addProduction = ProductionController.addProduction(ProductionDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString(), txtraw1.getText().trim(), txtraw2.getText().trim(), txtraw3.getText().trim(), txtraw4.getText().trim(), txtrawtotal.getText().trim(), txtcalcium.getText().trim(), txttitanium.getText().trim(), txttbls.getText().trim(), txtwax.getText().trim(), txtNIL.getText().trim(), txtFinalTotal.getText().trim(), txtName.getText().trim(), txtsize.getText().trim(), txtpipes.getText().trim());
        if (addProduction) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Production Added SuccessFully");
            alert.showAndWait();
        }//To change body of generated methods, choose Tools | Templates.
    }
}
