/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Production;

import Sales.Customer.FXMLCustomerManagerController;
import dbController.Production;
import static dbController.ProductionController.getProductionObservableList;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
public class FXMLProductionManagerController implements Initializable {

    @FXML
    private AnchorPane ProductionManagerPane;
    @FXML
    private TextField txtsearch;
    @FXML
    private TableView<Production> tbproduction;
    @FXML
    private TableColumn<Production, String> tbcolbatchdate;
    @FXML
    private TableColumn<Production, String> tbcolbatchno;
    @FXML
    private TableColumn<Production, String> tbcolTotalweight;
    @FXML
    private TableColumn<Production, String> tbcolSize;
    @FXML
    private TableColumn<Production, String> tbvcolpipes;
    @FXML
    private TableColumn<Production, String> tbcolname;

    ObservableList<Production> data = FXCollections.observableArrayList(getProductionObservableList());
    ObservableList<Production> filteredData = FXCollections.observableArrayList();

    public FXMLProductionManagerController() {
        data = FXCollections.observableArrayList(getProductionObservableList());
        filteredData.addAll(data);

        data.addListener(new ListChangeListener<Production>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Production> change) {
                updateFilteredData();
            }
        });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
        ProductionManagerPane.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.HOME == event.getCode() || KeyCode.END == event.getCode() || KeyCode.ESCAPE == event.getCode()) {
                try {
                    javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Dashboard/FXMLDashboard.fxml"));
                    ProductionManagerPane.getChildren().setAll(load3);
                    AnchorPane.setRightAnchor(load3, 0.0);
                    AnchorPane.setLeftAnchor(load3, 0.0);
                    AnchorPane.setTopAnchor(load3, 0.0);
                    AnchorPane.setBottomAnchor(load3, 0.0);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLProductionManagerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

       _loadTbProduction();
    }

    @FXML
    private void LoadProduction(ActionEvent event) {
        _LoadProduction();
    }

    @FXML
    private void LoadProduction1(KeyEvent event) {
        if (KeyCode.ENTER == event.getCode()) {
            _LoadProduction();
        }
    }

    private void _LoadProduction() {
        try {
            javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Production/FXMLProduction.fxml"));
            ProductionManagerPane.getChildren().setAll(load3);
            AnchorPane.setRightAnchor(load3, 0.0);
            AnchorPane.setLeftAnchor(load3, 0.0);
            AnchorPane.setTopAnchor(load3, 0.0);
            AnchorPane.setBottomAnchor(load3, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLProductionManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateFilteredData() {
        filteredData.clear();

        for (Production p : data) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(Production p) {
        String filterString = txtsearch.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (p.getPRODUCTIONID().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getProductiondate().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getSize().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Production, ?>> sortOrder = new ArrayList<>(tbproduction.getSortOrder());
        tbproduction.getSortOrder().clear();
        tbproduction.getSortOrder().addAll(sortOrder); //To change body of generated methods, choose Tools | Templates.
    }

    private void _loadTbProduction() {
        filteredData.removeAll(data);
        data = FXCollections.observableArrayList(getProductionObservableList());
        filteredData.addAll(data);
        tbcolbatchdate.setCellValueFactory(new PropertyValueFactory<Production, String>("productiondate"));
        tbcolbatchno.setCellValueFactory(new PropertyValueFactory<Production, String>("PRODUCTIONID"));
        tbcolTotalweight.setCellValueFactory(new PropertyValueFactory<Production, String>("fulltotal"));
        tbcolSize.setCellValueFactory(new PropertyValueFactory<Production, String>("size"));
        tbvcolpipes.setCellValueFactory(new PropertyValueFactory<Production, String>("pipes"));
        tbcolname.setCellValueFactory(new PropertyValueFactory<Production, String>("name"));

        tbproduction.setItems(filteredData);
        txtsearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
        });
    }

    @FXML
    private void tbProductionKeyReleased(KeyEvent event) {
        if (event.getCode() ==  KeyCode.ENTER) {
            try {
                Production selectedItem = tbproduction.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Production/FXMLProduction.fxml"));
                Parent load = loader.load();
                FXMLProductionController controller = loader.getController();
                controller.editLoader(selectedItem.getPRODUCTIONID());
                ProductionManagerPane.getChildren().setAll(load);
                AnchorPane.setRightAnchor(load, 0.0);
                AnchorPane.setLeftAnchor(load, 0.0);
                AnchorPane.setTopAnchor(load, 0.0);
                AnchorPane.setBottomAnchor(load, 0.0);
            } catch (IOException ex) {
                Logger.getLogger(FXMLProductionManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
