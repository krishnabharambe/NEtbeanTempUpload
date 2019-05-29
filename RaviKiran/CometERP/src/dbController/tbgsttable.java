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
public class tbgsttable {

    String HSN, taxable, cgstpercentage, cgst, sgstpercentage, sgst, gstpercentage, gst;

    public tbgsttable() {
    }

    public tbgsttable(String HSN, String taxable, String cgstpercentage, String cgst, String sgstpercentage, String sgst, String gstpercentage, String gst) {
        this.HSN = HSN;
        this.taxable = taxable;
        this.cgstpercentage = cgstpercentage;
        this.cgst = cgst;
        this.sgstpercentage = sgstpercentage;
        this.sgst = sgst;
        this.gstpercentage = gstpercentage;
        this.gst = gst;
    }

    public String getHSN() {
        return HSN;
    }

    public void setHSN(String HSN) {
        this.HSN = HSN;
    }

    public String getTaxable() {
        return taxable;
    }

    public void setTaxable(String taxable) {
        this.taxable = taxable;
    }

    public String getCgstpercentage() {
        return cgstpercentage;
    }

    public void setCgstpercentage(String cgstpercentage) {
        this.cgstpercentage = cgstpercentage;
    }

    public String getCgst() {
        return cgst;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }

    public String getSgstpercentage() {
        return sgstpercentage;
    }

    public void setSgstpercentage(String sgstpercentage) {
        this.sgstpercentage = sgstpercentage;
    }

    public String getSgst() {
        return sgst;
    }

    public void setSgst(String sgst) {
        this.sgst = sgst;
    }

    public String getGstpercentage() {
        return gstpercentage;
    }

    public void setGstpercentage(String gstpercentage) {
        this.gstpercentage = gstpercentage;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }
    
}
