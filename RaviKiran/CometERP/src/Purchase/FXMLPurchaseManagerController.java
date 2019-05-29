/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Purchase;

import dbController.Purchase;
import static dbController.PurchaseController.getPurchaseObservableList;
import static dbController.PurchaseStockController.deletePurchase;
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
import javafx.scene.Parent;
import javafx.scene.control.Alert;
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
public class FXMLPurchaseManagerController implements Initializable {

    @FXML
    private TextField txtSearch;
    @FXML
    private AnchorPane SalesManagerPane;
    @FXML
    private TableView<Purchase> tbSalesListing;

    ObservableList<Purchase> data = FXCollections.observableArrayList(getPurchaseObservableList());
    ObservableList<Purchase> filteredData = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Purchase, String> txtinvoiceid;
    @FXML
    private TableColumn<Purchase, String> txtinvoicedate;
    @FXML
    private TableColumn<Purchase, String> txtinvoicecustomer;

    public FXMLPurchaseManagerController() {
        data = FXCollections.observableArrayList(getPurchaseObservableList());
        filteredData.addAll(data);

        data.addListener(new ListChangeListener<Purchase>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Purchase> change) {
                updateFilteredData();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadSalesTable();
        // TODO

        SalesManagerPane.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.HOME == event.getCode() || KeyCode.END == event.getCode() || KeyCode.ESCAPE == event.getCode()) {
                try {
                    javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Dashboard/FXMLDashboard.fxml"));
                    SalesManagerPane.getChildren().setAll(load3);
                    AnchorPane.setRightAnchor(load3, 0.0);
                    AnchorPane.setLeftAnchor(load3, 0.0);
                    AnchorPane.setTopAnchor(load3, 0.0);
                    AnchorPane.setBottomAnchor(load3, 0.0);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLPurchaseManagerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    private void actionNewSale(ActionEvent event) {
        _NewSale();
    }

    @FXML
    private void actionNewSale1(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            _NewSale();
        }
    }

    private void _NewSale() {
        try {
            javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Purchase/FXMLPurchase.fxml"));
            SalesManagerPane.getChildren().setAll(load3);
            AnchorPane.setRightAnchor(load3, 0.0);
            AnchorPane.setLeftAnchor(load3, 0.0);
            AnchorPane.setTopAnchor(load3, 0.0);
            AnchorPane.setBottomAnchor(load3, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLPurchaseManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actiontbSalesLsingKeyReleased(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            Purchase selectedItem = tbSalesListing.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPurchase.fxml"));
            Parent load = loader.load();
            FXMLPurchaseController controller = loader.getController();
            controller.editLoader(selectedItem.getId());
            SalesManagerPane.getChildren().setAll(load);
            AnchorPane.setRightAnchor(load, 0.0);
            AnchorPane.setLeftAnchor(load, 0.0);
            AnchorPane.setTopAnchor(load, 0.0);
            AnchorPane.setBottomAnchor(load, 0.0);
        } else if (event.getCode().equals(KeyCode.DELETE)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Want To Delete");
            alert.setContentText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Purchase selectedItem = tbSalesListing.getSelectionModel().getSelectedItem();
                boolean deleteSales = deletePurchase(selectedItem.getId());
                if (deleteSales) {
                    Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                    alert3.setTitle("Information");
                    alert3.setHeaderText(null);
                    alert3.setContentText("Deleted SuccessFully");
                    alert3.showAndWait();
                    loadSalesTable();
                }
            }
        }

    }

    private void loadSalesTable() {
        filteredData.removeAll(data);
        data = FXCollections.observableArrayList(getPurchaseObservableList());
        filteredData.addAll(data);
        txtinvoiceid.setCellValueFactory(new PropertyValueFactory<Purchase, String>("id"));
        txtinvoicecustomer.setCellValueFactory(new PropertyValueFactory<Purchase, String>("name"));
        txtinvoicedate.setCellValueFactory(new PropertyValueFactory<Purchase, String>("Date"));
        tbSalesListing.setItems(filteredData);
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

        for (Purchase p : data) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(Purchase p) {
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
        } else if (p.getDate().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Purchase, ?>> sortOrder = new ArrayList<>(tbSalesListing.getSortOrder());
        tbSalesListing.getSortOrder().clear();
        tbSalesListing.getSortOrder().addAll(sortOrder); //To change body of generated methods, choose Tools | Templates.
    }
}
