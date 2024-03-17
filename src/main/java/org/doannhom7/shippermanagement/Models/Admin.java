package org.doannhom7.shippermanagement.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Admin {
    private final StringProperty userName;
    public Admin(String userName) {
        this.userName = new SimpleStringProperty(this, "User Name", userName);
    }
    public StringProperty userNameProperty() {
        return this.userName;
    }
}
