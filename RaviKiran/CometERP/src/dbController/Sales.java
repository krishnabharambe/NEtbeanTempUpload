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
public class Sales {
     String id,name,Date;

    public Sales(String id, String name, String Date) {
        this.id = id;
        this.name = name;
        this.Date = Date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
     
}
