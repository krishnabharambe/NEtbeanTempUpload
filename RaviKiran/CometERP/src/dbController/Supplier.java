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
public class Supplier {

    String id, Name, Address, AddressPinCode, Contact, AltContact, Telephone, Email, Website, GSTIN, PanTan, AccountNo, BankIFSC, BankBranch;

    public Supplier(String id, String Name, String Address, String AddressPinCode, String Contact, String AltContact, String Telephone, String Email, String Website, String GSTIN, String PanTan, String AccountNo, String BankIFSC, String BankBranch) {
        this.id = id;
        this.Name = Name;
        this.Address = Address;
        this.AddressPinCode = AddressPinCode;
        this.Contact = Contact;
        this.AltContact = AltContact;
        this.Telephone = Telephone;
        this.Email = Email;
        this.Website = Website;
        this.GSTIN = GSTIN;
        this.PanTan = PanTan;
        this.AccountNo = AccountNo;
        this.BankIFSC = BankIFSC;
        this.BankBranch = BankBranch;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getAddressPinCode() {
        return AddressPinCode;
    }

    public void setAddressPinCode(String AddressPinCode) {
        this.AddressPinCode = AddressPinCode;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public String getAltContact() {
        return AltContact;
    }

    public void setAltContact(String AltContact) {
        this.AltContact = AltContact;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String Website) {
        this.Website = Website;
    }

    public String getGSTIN() {
        return GSTIN;
    }

    public void setGSTIN(String GSTIN) {
        this.GSTIN = GSTIN;
    }

    public String getPanTan() {
        return PanTan;
    }

    public void setPanTan(String PanTan) {
        this.PanTan = PanTan;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String AccountNo) {
        this.AccountNo = AccountNo;
    }

    public String getBankIFSC() {
        return BankIFSC;
    }

    public void setBankIFSC(String BankIFSC) {
        this.BankIFSC = BankIFSC;
    }

    public String getBankBranch() {
        return BankBranch;
    }

    public void setBankBranch(String BankBranch) {
        this.BankBranch = BankBranch;
    }

    
}
