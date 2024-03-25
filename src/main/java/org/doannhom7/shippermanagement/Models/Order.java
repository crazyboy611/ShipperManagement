package org.doannhom7.shippermanagement.Models;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Order {
    private ObjectProperty<LocalDate> deliveryDateExpect;
    private IntegerProperty order_id;
    private IntegerProperty shipper_id;
    private StringProperty pickup_location;
    private StringProperty delivery_location;
    private DoubleProperty value;
    private StringProperty other_details;
    public Order(){}
    public Order(Integer order_id, Integer shipper_id, String pickup_location, String delivery_location, Double value, String other_details, LocalDate localDate) {
        this.order_id = new SimpleIntegerProperty(this, "Order Id", order_id);
        this.shipper_id = new SimpleIntegerProperty(this, "Shipper Id", shipper_id);
        this.pickup_location = new SimpleStringProperty(this, "Pickup Location", pickup_location);
        this.delivery_location = new SimpleStringProperty(this, "Delivery Location", delivery_location);
        this.value = new SimpleDoubleProperty(this, "Value", value);
        this.other_details = new SimpleStringProperty(this, "Other Details", other_details);
        this.deliveryDateExpect = new SimpleObjectProperty<>(this, "Delivery Date Expect", localDate);
    }
    public Order(int order_id, String pickup_location, String delivery_location, LocalDate localDate, Double value, String other_details) {
        this.order_id = new SimpleIntegerProperty(this, "Shipper Id", order_id);
        this.pickup_location = new SimpleStringProperty(this, "Pickup Location", pickup_location);
        this.delivery_location = new SimpleStringProperty(this, "Delivery Location", delivery_location);
        this.value = new SimpleDoubleProperty(this, "Value", value);
        this.other_details = new SimpleStringProperty(this, "Other Details", other_details);
        this.deliveryDateExpect = new SimpleObjectProperty<>(this, "Delivery Date Expect", localDate);
    }
    public IntegerProperty orderIdProperty(){
        return order_id;
    }
    public IntegerProperty shipperIdProperty(){
        return shipper_id;
    }
    public StringProperty pickupLocationProperty(){
        return pickup_location;
    }
    public StringProperty deliveryLocationProperty() {
        return delivery_location;
    }
    public DoubleProperty valueProperty() {
        return value;
    }
    public StringProperty otherDetailsProperty() {
        return other_details;
    }
    public ObjectProperty<LocalDate> deliveryDateExpectProperty() {
            return deliveryDateExpect;
        }
}
