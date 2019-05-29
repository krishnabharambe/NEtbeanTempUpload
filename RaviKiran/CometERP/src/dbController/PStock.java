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
public class PStock {
    String PStockid;
    String PStockTitle;
    String PStockDescription;
    String PStockHSN;
    String PStockCost;
    String PStockPrice;
    String PStockRemain;
    String PStockSold;

    public PStock(String PStockid, String PStockTitle, String PStockDescription, String PStockHSN, String PStockCost, String PStockPrice, String PStockRemain, String PStockSold) {
        this.PStockid = PStockid;
        this.PStockTitle = PStockTitle;
        this.PStockDescription = PStockDescription;
        this.PStockHSN = PStockHSN;
        this.PStockCost = PStockCost;
        this.PStockPrice = PStockPrice;
        this.PStockRemain = PStockRemain;
        this.PStockSold = PStockSold;
    }

    public String getPStockid() {
        return PStockid;
    }

    public void setPStockid(String PStockid) {
        this.PStockid = PStockid;
    }

    public String getPStockTitle() {
        return PStockTitle;
    }

    public void setPStockTitle(String PStockTitle) {
        this.PStockTitle = PStockTitle;
    }

    public String getPStockDescription() {
        return PStockDescription;
    }

    public void setPStockDescription(String PStockDescription) {
        this.PStockDescription = PStockDescription;
    }

    public String getPStockHSN() {
        return PStockHSN;
    }

    public void setPStockHSN(String PStockHSN) {
        this.PStockHSN = PStockHSN;
    }

    public String getPStockCost() {
        return PStockCost;
    }

    public void setPStockCost(String PStockCost) {
        this.PStockCost = PStockCost;
    }

    public String getPStockPrice() {
        return PStockPrice;
    }

    public void setPStockPrice(String PStockPrice) {
        this.PStockPrice = PStockPrice;
    }

    public String getPStockRemain() {
        return PStockRemain;
    }

    public void setPStockRemain(String PStockRemain) {
        this.PStockRemain = PStockRemain;
    }

    public String getPStockSold() {
        return PStockSold;
    }

    public void setPStockSold(String PStockSold) {
        this.PStockSold = PStockSold;
    }
    
}
