/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cometerp;

import database.db;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class CometERP extends Application {

    public CometERP() {
        db.setdata("CREATE TABLE IF NOT EXISTS USERS (userid integer primary key autoincrement,"
                + "username text,"
                + "password text,"
                + "Role text,"
                + "updated_on text,"
                + "dated_on text,"
                + "timestamp text)");

        db.setdata("CREATE TABLE IF NOT EXISTS Customer(id integer primary key autoincrement,"
                + "Name text,"
                + "Address Textd,"
                + "AddressPinCode,"
                + "Contact text,"
                + "AltContact text,"
                + "Telephone text,"
                + "Email text,"
                + "Website text,"
                + "GSTIN text,"
                + "PanTan text,"
                + "AccountNo text,"
                + "BankIFSC text,"
                + "BankBranch text,"
                + "updated_on text,"
                + "dated_on text,"
                + "timestamp text)");

        /**
         * Stock
         */
        db.setdata("CREATE TABLE IF NOT EXISTS Stock(id integer primary key autoincrement,"
                + "StockTitle text,"
                + "StockDescription text,"
                + "StockHSN text,"
                + "StockCost text,"
                + "StockPrice text,"
                + "StockRemain text,"
                + "StockSold text,"
                + "updated_on text,"
                + "dated_on text,"
                + "timestamp text)");

        /**
         * Profile
         */
        db.setdata("CREATE TABLE IF NOT EXISTS BusinessDetail"
                + "(BusinessID integer primary key autoincrement,"
                + "BusinessName Text,"
                + "BusinessAddress Text,"
                + "BusinessMailing TEXT,"
                + "BusinessContact TEXT,"
                + "BusinessEmail TEXT,"
                + "BusinessGSTIN TEXT,"
                + "BusinessPanTan TEXT,"
                + "BusinessWebsite TEXT,"
                + "BusinessTelephone TEXT,"
                + "updated_on date,"
                + "dated_on date,"
                + "timestamp TEXT)");

        db.setdata("CREATE TABLE IF NOT EXISTS BusinessAccount(id integer primary key autoincrement,"
                + "BusinessBankAccount text,"
                + "BusinessBankIFSC text,"
                + "BusinessBankBranch text,"
                + "updated_on text,"
                + "dated_on text,"
                + "timestamp text)");

        db.setdata("CREATE TABLE IF NOT EXISTS InvoiceTerms"
                + "(Id integer primary key autoincrement,"
                + "Description TEXT,"
                + "updated_on date,"
                + "dated_on date,"
                + "timestamp TEXT)");

        db.setdata("CREATE TABLE IF NOT EXISTS QuotationTerms"
                + "(Id integer primary key autoincrement,"
                + "Description TEXT,"
                + "updated_on date,"
                + "dated_on date,"
                + "timestamp TEXT)");

        db.setdata("CREATE TABLE IF NOT EXISTS PurchaseTerms"
                + "(Id integer primary key autoincrement,"
                + "Description TEXT,"
                + "updated_on date,"
                + "dated_on date,"
                + "timestamp TEXT)");

        /**
         * GSt percentage
         */
        db.setdata("CREATE TABLE IF NOT EXISTS GSTdefault(GDid integer primary key autoincrement,"
                + "Rate text,"
                + "Updated_on text,"
                + "Dated_on text,"
                + "timestamp text)");

        /**
         * Sales
         */
        db.setdata("CREATE TABLE IF NOT EXISTS Sales(SaleId integer primary key autoincrement,"
                + "SaleDate text,"
                + "CustomerId text,"
                + "Updated_on text,"
                + "Dated_on text,"
                + "timestamp text)");
        /**
         * Mytranscationd
         */
        db.setdata("CREATE TABLE IF NOT EXISTS MyTranscation(transid integer primary key autoincrement,"
                + "CustomerId text,"
                + "InvoiceId text,"
                + "StockId text,"
                + "StockTitle text,"
                + "StockHSN text,"
                + "StockCost text,"
                + "StockPrice text,"
                + "Discount text,"
                + "StockCGST text,"
                + "StockSGST text,"
                + "StockGST text,"
                + "Quantity text,"
                + "Total text,"
                + "Updated_on text,"
                + "Dated_on text,"
                + "timestamp text)");

        /**
         * Sale Payment and delivery
         */
        db.setdata("CREATE TABLE IF NOT EXISTS SalePayment"
                + "(SalePaymentId integer primary key autoincrement,"
                + "Saleid text,"
                + "SaleDate text,"
                + "CustomerId,"
                + "PaymentStatus text,"
                + "PaymentType text,"
                + "PaymentRemark text,"
                + "PaymentDate text,"
                + "Updated_on text,"
                + "Dated_on text,"
                + "timestamp text)");

        db.setdata("CREATE TABLE IF NOT EXISTS SaleDelivery"
                + "(SaleDeliveryId integer primary key autoincrement,"
                + "Saleid text,"
                + "SaleDate text,"
                + "CustomerId,"
                + "DeliveryStatus text,"
                + "DeliveryCost text,"
                + "DeliveryType text,"
                + "DeliveryRemark text,"
                + "DeliveryDate text,"
                + "Updated_on text,"
                + "Dated_on text,"
                + "timestamp text)");

        db.setdata("CREATE TABLE IF NOT EXISTS PRODUCTION "
                + "(PRODUCTIONID integer primary key autoincrement,"
                + "productiondate text,"
                + "raw1 text,"
                + "raw2 text,"
                + "raw3 text,"
                + "raw4 text,"
                + "rawtotal text,"
                + "calciumtotal text,"
                + "titaniumtotal text,"
                + "tblstotal text,"
                + "waxtotal text,"
                + "liltotal text,"
                + "fulltotal text,"
                + "name text,"
                + "size text,"
                + "pipes text,"
                + "updated_on text,"
                + "dated_on text,"
                + "timestamp text)");

        db.setdata("CREATE TABLE IF NOT EXISTS Supplier(id integer primary key autoincrement,"
                + "Name text,"
                + "Address Textd,"
                + "AddressPinCode,"
                + "Contact text,"
                + "AltContact text,"
                + "Telephone text,"
                + "Email text,"
                + "Website text,"
                + "GSTIN text,"
                + "PanTan text,"
                + "AccountNo text,"
                + "BankIFSC text,"
                + "BankBranch text,"
                + "updated_on text,"
                + "dated_on text,"
                + "timestamp text)");

        /**
         * Stock
         */
        db.setdata("CREATE TABLE IF NOT EXISTS PStock(id integer primary key autoincrement,"
                + "StockTitle text,"
                + "StockDescription text,"
                + "StockHSN text,"
                + "StockCost text,"
                + "StockPrice text,"
                + "StockRemain text,"
                + "StockSold text,"
                + "updated_on text,"
                + "dated_on text,"
                + "timestamp text)");

        /**
         * Sales
         */
        db.setdata("CREATE TABLE IF NOT EXISTS Purchase(PurchaseId integer primary key autoincrement,"
                + "PurchaseDate text,"
                + "SupplierId text,"
                + "Updated_on text,"
                + "Dated_on text,"
                + "timestamp text)");
        /**
         * Mytranscationd
         */
        db.setdata("CREATE TABLE IF NOT EXISTS MyPTranscation(transid integer primary key autoincrement,"
                + "SupplierId text,"
                + "InvoiceId text,"
                + "StockId text,"
                + "StockTitle text,"
                + "StockHSN text,"
                + "StockCost text,"
                + "StockPrice text,"
                + "Discount text,"
                + "StockCGST text,"
                + "StockSGST text,"
                + "StockGST text,"
                + "Quantity text,"
                + "Total text,"
                + "Updated_on text,"
                + "Dated_on text,"
                + "timestamp text)");

        /**
         * Sale Payment and delivery
         */
        db.setdata("CREATE TABLE IF NOT EXISTS PurchasePayment"
                + "(PurchasePaymentId integer primary key autoincrement,"
                + "Purchaseid text,"
                + "PurchaseDate text,"
                + "SupplierId,"
                + "PaymentStatus text,"
                + "PaymentType text,"
                + "PaymentRemark text,"
                + "PaymentDate text,"
                + "Updated_on text,"
                + "Dated_on text,"
                + "timestamp text)");

        db.setdata("CREATE TABLE IF NOT EXISTS PurchaseDelivery"
                + "(PurchaseDeliveryId integer primary key autoincrement,"
                + "Purchaseid text,"
                + "PurchaseDate text,"
                + "SupplierId,"
                + "DeliveryStatus text,"
                + "DeliveryCost text,"
                + "DeliveryType text,"
                + "DeliveryRemark text,"
                + "DeliveryDate text,"
                + "Updated_on text,"
                + "Dated_on text,"
                + "timestamp text)");

        /**
         * RStock
         */
        db.setdata("CREATE TABLE IF NOT EXISTS RStock(id integer primary key autoincrement,"
                + "StockTitle text,"
                + "StockDescription text,"
                + "StockHSN text,"
                + "StockCost text,"
                + "StockPrice text,"
                + "StockRemain text,"
                + "StockSold text,"
                + "updated_on text,"
                + "dated_on text,"
                + "timestamp text)");

        db.setdata("CREATE TABLE IF NOT EXISTS employee(id integer primary key autoincrement,"
                + "Name text,"
                + "Address Textd,"
                + "Contact text,"
                + "AltContact text,"
                + "Email text,"
                + "UID text,"
                + "AccountNo text,"
                + "BankIFSC text,"
                + "BankBranch text,"
                + "updated_on text,"
                + "dated_on text,"
                + "timestamp text)");
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMainStage.fxml"));

        Scene scene = new Scene(root);
        stage.setMaximized(true);
        stage.setTitle("Comet ERP");
//        stage.setOpacity(50);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
