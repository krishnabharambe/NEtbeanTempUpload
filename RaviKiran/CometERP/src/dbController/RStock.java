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
public class RStock {
    String RStockid;
    String RStockTitle;
    String RStockDescription;
    String RStockHSN;
    String RStockCost;
    String RStockPrice;
    String RStockRemain;
    String RStockSold;

    public RStock(String RStockid, String RStockTitle, String RStockDescription, String RStockHSN, String RStockCost, String RStockPrice, String RStockRemain, String RStockSold) {
        this.RStockid = RStockid;
        this.RStockTitle = RStockTitle;
        this.RStockDescription = RStockDescription;
        this.RStockHSN = RStockHSN;
        this.RStockCost = RStockCost;
        this.RStockPrice = RStockPrice;
        this.RStockRemain = RStockRemain;
        this.RStockSold = RStockSold;
    }

    public String getRStockid() {
        return RStockid;
    }

    public void setRStockid(String RStockid) {
        this.RStockid = RStockid;
    }

    public String getRStockTitle() {
        return RStockTitle;
    }

    public void setRStockTitle(String RStockTitle) {
        this.RStockTitle = RStockTitle;
    }

    public String getRStockDescription() {
        return RStockDescription;
    }

    public void setRStockDescription(String RStockDescription) {
        this.RStockDescription = RStockDescription;
    }

    public String getRStockHSN() {
        return RStockHSN;
    }

    public void setRStockHSN(String RStockHSN) {
        this.RStockHSN = RStockHSN;
    }

    public String getRStockCost() {
        return RStockCost;
    }

    public void setRStockCost(String RStockCost) {
        this.RStockCost = RStockCost;
    }

    public String getRStockPrice() {
        return RStockPrice;
    }

    public void setRStockPrice(String RStockPrice) {
        this.RStockPrice = RStockPrice;
    }

    public String getRStockRemain() {
        return RStockRemain;
    }

    public void setRStockRemain(String RStockRemain) {
        this.RStockRemain = RStockRemain;
    }

    public String getRStockSold() {
        return RStockSold;
    }

    public void setRStockSold(String RStockSold) {
        this.RStockSold = RStockSold;
    }
    
    
}
