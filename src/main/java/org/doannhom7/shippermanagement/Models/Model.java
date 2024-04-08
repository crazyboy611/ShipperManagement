package org.doannhom7.shippermanagement.Models;

import org.doannhom7.shippermanagement.Views.ViewFactory;

import java.sql.ResultSet;
import java.time.LocalDate;

public class Model {
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;
    private static Model model;

    // Client data section
    private final Shipper shipper;
    private boolean shipperLoginSuccessFlag;

    private final Admin admin;
    private boolean adminLoginSuccessFlag;
    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();
        this.shipperLoginSuccessFlag = false;
        this.adminLoginSuccessFlag = false;
        this.shipper = new Shipper(0,"","",null,"", "", "", "");
        this.admin = new Admin("");
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

}
