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
public class QuotationTerms {
    String Id,Description;

    public QuotationTerms() {
    }
    

    public QuotationTerms(String Id, String Description) {
        this.Id = Id;
        this.Description = Description;
    }

    public QuotationTerms(String string, String string0, String string1, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, String string11, String string12) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
    
}
