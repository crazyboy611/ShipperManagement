package org.doannhom7.shippermanagement.Models;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Date;

public class DeliveryNote {
    private final IntegerProperty note_id;
    private final IntegerProperty order_id;
    private final IntegerProperty number;
    private final StringProperty status;
    private final ObjectProperty<LocalDate> delivery_date;
    public DeliveryNote(int note_id, int order_id, int number, String status, LocalDate delivery_date) {
        this.note_id = new SimpleIntegerProperty(this, "Note Id", note_id);
        this.order_id = new SimpleIntegerProperty(this, "Order Id", order_id);
        this.number = new SimpleIntegerProperty(this, "Number", number);
        this.status = new SimpleStringProperty(this, "Status", status);
        this.delivery_date = new SimpleObjectProperty<>(this, "Delivery Date", delivery_date);
    }
    public IntegerProperty noteIdProperty() {
        return note_id;
    }
    public IntegerProperty orderIdProperty() {
        return order_id;
    }
    public IntegerProperty numberProperty() {
        return number;
    }
    public StringProperty statusProperty() {
        return status;
    }
    public ObjectProperty<LocalDate> deliveryDateProperty() {
        return delivery_date;
    }

}
