package org.doannhom7.shippermanagement.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.doannhom7.shippermanagement.Views.ViewFactory;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Objects;

public class Model {
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;
    private static Model model;

    // Client data section
    private final Shipper shipper;
    private boolean shipperLoginSuccessFlag;

    //Admin data section
    private final Admin admin;
    private boolean adminLoginSuccessFlag;
    private final ObservableList<DeliveryNote> deliveryNotes;
    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();
        this.shipperLoginSuccessFlag = false;
        this.adminLoginSuccessFlag = false;
        this.shipper = new Shipper(0,"","",null,"", "", "", "");
        this.admin = new Admin("");
        this.deliveryNotes = FXCollections.observableArrayList();
    }

    public boolean getShipperLoginSuccessFlag() {
        return shipperLoginSuccessFlag;
    }

    public void setShipperLoginSuccessFlag(boolean shipperLoginSuccessFlag) {
        this.shipperLoginSuccessFlag = shipperLoginSuccessFlag;
    }

    public boolean getAdminLoginSuccessFlag() {
        return adminLoginSuccessFlag;
    }

    public void setAdminLoginSuccessFlag(boolean adminLoginSuccessFlag) {
        this.adminLoginSuccessFlag = adminLoginSuccessFlag;
    }

    public Shipper getShipper() {
        return shipper;
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }
    public void evaluateShipperCred(String phone, String password) {
        ResultSet resultSet = databaseDriver.getShipperData(phone, password);
        try {
            if(resultSet.next()){
                this.shipper.shipperIdProperty().set(resultSet.getInt("shipper_id"));
                this.shipper.firstNameProperty().set(resultSet.getString("firstname"));
                this.shipper.lastNameProperty().set(resultSet.getString("lastname"));
                this.shipper.phoneProperty().set(resultSet.getString("phone"));
                String[] birth = resultSet.getString("birthDay").split("-");
                LocalDate localDate = LocalDate.of(Integer.parseInt(birth[0]), Integer.parseInt(birth[1]), Integer.parseInt(birth[2]));
                this.shipper.birthProperty().set(localDate);
                this.shipper.emailProperty().set(resultSet.getString("email"));
                this.shipper.addressProperty().set(resultSet.getString("address"));
                this.shipperLoginSuccessFlag = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void evaluateAdminCred(String username, String password) {
        ResultSet resultSet = databaseDriver.getAdminData(username, password);
        try {
            if(resultSet.next()){
                this.admin.userNameProperty().set(resultSet.getString("username"));
                this.adminLoginSuccessFlag = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setDeliveryNotes() {
        ResultSet resultSet = databaseDriver.getDeliveryNote();
        try {
            while(resultSet.next()) {
                int note_id = resultSet.getInt("delivery_id");
                int order_id = resultSet.getInt("order_id");
                int number = resultSet.getInt("delivery_number");
                String status = resultSet.getString("delivery_status");
                LocalDate localDate;
                if(resultSet.getString("delivery_date").isEmpty()) {
                    localDate = null;
                }else{
                    String[] date = resultSet.getString("delivery_date").split("-");
                    localDate = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                }

                this.deliveryNotes.add(new DeliveryNote(note_id, order_id, number, status, localDate));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ObservableList<DeliveryNote> getDeliveryNotes() {
        return this.deliveryNotes;
    }

}
