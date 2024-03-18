package org.doannhom7.shippermanagement.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Shipper {
    private IntegerProperty shipper_id;
    private StringProperty firstName;
    private StringProperty lastName;
    private ObjectProperty<LocalDate> birth;
    private StringProperty phone;
    private StringProperty email;
    private StringProperty address;
    public Shipper(){}
    public Shipper(Integer id,
                   String firstName,
                   String lastName,
                   LocalDate birth,
                   String phone,
                   String email,
                   String address
                   ){
        this.shipper_id = new SimpleIntegerProperty(this, "Shipper Id", id);
        this.firstName = new SimpleStringProperty(this, "First Name", firstName);
        this.lastName = new SimpleStringProperty(this, "Last Name", lastName);
        this.birth = new SimpleObjectProperty<>(this, "Birth", birth);
        this.phone = new SimpleStringProperty(this, "Phone", phone);
        this.email = new SimpleStringProperty(this, "Email", email);
        this.address = new SimpleStringProperty(this, "Address", address);
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
    public IntegerProperty shipper_idProperty() {
        return shipper_id;
    }

    public void setShipper_id(int shipper_id) {
        this.shipper_id.set(shipper_id);
    }


    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }


    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }


    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public ObjectProperty<LocalDate> birthProperty() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth.set(birth);
    }
}
