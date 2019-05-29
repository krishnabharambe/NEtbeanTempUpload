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
public class Production {

    String PRODUCTIONID, productiondate, raw1, raw2, raw3, raw4, rawtotal, calciumtotal, titaniumtotal, tblstotal, waxtotal, liltotal, fulltotal, name, size, pipes;

    public Production(String PRODUCTIONID, String productiondate, String raw1, String raw2, String raw3, String raw4, String rawtotal, String calciumtotal, String titaniumtotal, String tblstotal, String waxtotal, String liltotal, String fulltotal, String name, String size, String pipes) {
        this.PRODUCTIONID = PRODUCTIONID;
        this.productiondate = productiondate;
        this.raw1 = raw1;
        this.raw2 = raw2;
        this.raw3 = raw3;
        this.raw4 = raw4;
        this.rawtotal = rawtotal;
        this.calciumtotal = calciumtotal;
        this.titaniumtotal = titaniumtotal;
        this.tblstotal = tblstotal;
        this.waxtotal = waxtotal;
        this.liltotal = liltotal;
        this.fulltotal = fulltotal;
        this.name = name;
        this.size = size;
        this.pipes = pipes;
    }

    public String getPRODUCTIONID() {
        return PRODUCTIONID;
    }

    public void setPRODUCTIONID(String PRODUCTIONID) {
        this.PRODUCTIONID = PRODUCTIONID;
    }

    public String getProductiondate() {
        return productiondate;
    }

    public void setProductiondate(String productiondate) {
        this.productiondate = productiondate;
    }

    public String getRaw1() {
        return raw1;
    }

    public void setRaw1(String raw1) {
        this.raw1 = raw1;
    }

    public String getRaw2() {
        return raw2;
    }

    public void setRaw2(String raw2) {
        this.raw2 = raw2;
    }

    public String getRaw3() {
        return raw3;
    }

    public void setRaw3(String raw3) {
        this.raw3 = raw3;
    }

    public String getRaw4() {
        return raw4;
    }

    public void setRaw4(String raw4) {
        this.raw4 = raw4;
    }

    public String getRawtotal() {
        return rawtotal;
    }

    public void setRawtotal(String rawtotal) {
        this.rawtotal = rawtotal;
    }

    public String getCalciumtotal() {
        return calciumtotal;
    }

    public void setCalciumtotal(String calciumtotal) {
        this.calciumtotal = calciumtotal;
    }

    public String getTitaniumtotal() {
        return titaniumtotal;
    }

    public void setTitaniumtotal(String titaniumtotal) {
        this.titaniumtotal = titaniumtotal;
    }

    public String getTblstotal() {
        return tblstotal;
    }

    public void setTblstotal(String tblstotal) {
        this.tblstotal = tblstotal;
    }

    public String getWaxtotal() {
        return waxtotal;
    }

    public void setWaxtotal(String waxtotal) {
        this.waxtotal = waxtotal;
    }

    public String getLiltotal() {
        return liltotal;
    }

    public void setLiltotal(String liltotal) {
        this.liltotal = liltotal;
    }

    public String getFulltotal() {
        return fulltotal;
    }

    public void setFulltotal(String fulltotal) {
        this.fulltotal = fulltotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPipes() {
        return pipes;
    }

    public void setPipes(String pipes) {
        this.pipes = pipes;
    }

    
}
