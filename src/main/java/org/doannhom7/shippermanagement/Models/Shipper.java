package org.doannhom7.shippermanagement.Models;

import javafx.beans.property.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.sql.Blob;
import java.time.LocalDate;

public class Shipper {
    private IntegerProperty shipper_id;
    private StringProperty firstName;
    private StringProperty lastName;
    private ObjectProperty<LocalDate> birth;
    private StringProperty phone;
    private StringProperty email;
    private StringProperty address;
    private StringProperty password;
    public Shipper(){}
    public Shipper(Integer shipper_id,
                   String firstName,
                   String lastName,
                   LocalDate birth,
                   String phone,
                   String email,
                   String address,
                   String password
                   ){
        this.shipper_id = new SimpleIntegerProperty(this, "Shipper Id", shipper_id);
        this.firstName = new SimpleStringProperty(this, "First Name", firstName);
        this.lastName = new SimpleStringProperty(this, "Last Name", lastName);
        this.birth = new SimpleObjectProperty<>(this, "Birth", birth);
        this.phone = new SimpleStringProperty(this, "Phone", phone);
        this.email = new SimpleStringProperty(this, "Email", email);
        this.address = new SimpleStringProperty(this, "Address", address);
        this.password = new SimpleStringProperty(this, "Password", password);
    }
    public Shipper(String firstName,
                   String lastName,
                   LocalDate birth,
                   String phone,
                   String email,
                   String address
    ){
        this.firstName = new SimpleStringProperty(this, "First Name", firstName);
        this.lastName = new SimpleStringProperty(this, "Last Name", lastName);
        this.birth = new SimpleObjectProperty<>(this, "Birth", birth);
        this.phone = new SimpleStringProperty(this, "Phone", phone);
        this.email = new SimpleStringProperty(this, "Email", email);
        this.address = new SimpleStringProperty(this, "Address", address);
    }
    public IntegerProperty shipperIdProperty() {
        return shipper_id;
    }
    public StringProperty firstNameProperty() {
        return firstName;
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }
    public StringProperty phoneProperty() {
        return phone;
    }
    public StringProperty emailProperty() {
        return email;
    }
    public StringProperty addressProperty() {
        return address;
    }
    public ObjectProperty<LocalDate> birthProperty() {
        return birth;
    }
    public StringProperty passwordProperty() {
        return password;
    }
}
