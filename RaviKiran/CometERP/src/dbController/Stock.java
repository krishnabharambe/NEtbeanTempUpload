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
public class Stock {

    String Stockid;
    String StockTitle;
    String StockDescription;
    String StockHSN;
    String StockCost;
    String StockPrice;
    String StockRemain;
    String StockSold;

    public Stock(String Stockid, String StockTitle, String StockDescription, String StockHSN, String StockCost, String StockPrice, String StockRemain, String StockSold) {
        this.Stockid = Stockid;
        this.StockTitle = StockTitle;
        this.StockDescription = StockDescription;
        this.StockHSN = StockHSN;
        this.StockCost = StockCost;
        this.StockPrice = StockPrice;
        this.StockRemain = StockRemain;
        this.StockSold = StockSold;
    }

    public String getStockid() {
        return Stockid;
    }

    public void setStockid(String Stockid) {
        this.Stockid = Stockid;
    }

    public String getStockTitle() {
        return StockTitle;
    }

    public void setStockTitle(String StockTitle) {
        this.StockTitle = StockTitle;
    }

    public String getStockDescription() {
        return StockDescription;
    }

    public void setStockDescription(String StockDescription) {
        this.StockDescription = StockDescription;
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

    public String getStockRemain() {
        return StockRemain;
    }

    public void setStockRemain(String StockRemain) {
        this.StockRemain = StockRemain;
    }

    public String getStockSold() {
        return StockSold;
    }

    public void setStockSold(String StockSold) {
        this.StockSold = StockSold;
    }

}
