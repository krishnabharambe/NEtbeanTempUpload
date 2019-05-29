/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbController;

/**
 *
 * @author admin
 */
public class myPtranscation {

    String id;
    String CustomerId;
    String InvoiceId;
    String StockId;
    String StockTitle;
    String StockHSN;
    String StockCost;
    String StockPrice;
    String Discount;
    String StockCGST;
    String StockSGST;
    String StockGST;
    String Quantity;
    String Total;

    public myPtranscation(String id, String CustomerId, String InvoiceId, String StockId, String StockTitle, String StockHSN, String StockCost, String StockPrice, String Discount, String StockCGST, String StockSGST, String StockGST, String Quantity, String Total) {
        this.id = id;
        this.CustomerId = CustomerId;
        this.InvoiceId = InvoiceId;
        this.StockId = StockId;
        this.StockTitle = StockTitle;
        this.StockHSN = StockHSN;
        this.StockCost = StockCost;
        this.StockPrice = StockPrice;
        this.Discount = Discount;
        this.StockCGST = StockCGST;
        this.StockSGST = StockSGST;
        this.StockGST = StockGST;
        this.Quantity = Quantity;
        this.Total = Total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String CustomerId) {
        this.CustomerId = CustomerId;
    }

    public String getInvoiceId() {
        return InvoiceId;
    }

    public void setInvoiceId(String InvoiceId) {
        this.InvoiceId = InvoiceId;
    }

    public String getStockId() {
        return StockId;
    }

    public void setStockId(String StockId) {
        this.StockId = StockId;
    }

    public String getStockTitle() {
        return StockTitle;
    }

    public void setStockTitle(String StockTitle) {
        this.StockTitle = StockTitle;
    }

    public String getStockHSN() {
        return StockHSN;
    }

    public void setStockHSN(String StockHSN) {
        this.StockHSN = StockHSN;
    }

    public String getStockCost() {
        return StockCost;
    }

    public void setStockCost(String StockCost) {
        this.StockCost = StockCost;
    }

    public String getStockPrice() {
        return StockPrice;
    }

    public void setStockPrice(String StockPrice) {
        this.StockPrice = StockPrice;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String Discount) {
        this.Discount = Discount;
    }

    public String getStockCGST() {
        return StockCGST;
    }

    public void setStockCGST(String StockCGST) {
        this.StockCGST = StockCGST;
    }

    public String getStockSGST() {
        return StockSGST;
    }

    public void setStockSGST(String StockSGST) {
        this.StockSGST = StockSGST;
    }

    public String getStockGST() {
        return StockGST;
    }

    public void setStockGST(String StockGST) {
        this.StockGST = StockGST;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String Total) {
        this.Total = Total;
    }
    
    
    
}
