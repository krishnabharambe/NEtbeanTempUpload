
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
public class employee {

    String id, Name, Address, Contact, AltContact, Email, UID, AccountNo, BankIFSC, BankBranch;

    public employee(String id, String Name, String Address, String Contact, String AltContact, String Email, String UID, String AccountNo, String BankIFSC, String BankBranch) {
        this.id = id;
        this.Name = Name;
        this.Address = Address;
        this.Contact = Contact;
        this.AltContact = AltContact;
        this.Email = Email;
        this.UID = UID;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
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