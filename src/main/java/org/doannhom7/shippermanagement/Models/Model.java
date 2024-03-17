package org.doannhom7.shippermanagement.Models;

import org.doannhom7.shippermanagement.Views.ViewFactory;

public class Model {
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;
    private static Model model;
//    private final DatabaseDriver databaseDriver;

    // Client data section
//    private final Shipper shipper;
//    private boolean shipperLoginSuccessFlag;
//    private final ObservableList<Client> clients;
//    private final ObservableList<Transaction> allTransaction;
//    private final ObservableList<Transaction> latestTransaction;
    // Admin data section
//    private final Admin admin;
//    private boolean adminLoginSuccessFlag;
    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();

//        this.shipperLoginSuccessFlag = false;
//        this.adminLoginSuccessFlag = false;
//        this.allTransaction = FXCollections.observableArrayList();
//        this.latestTransaction = FXCollections.observableArrayList();
//        this.shipper = new s("","","",null, null, null);
//        this.admin = new Admin("");
//        this.clients = FXCollections.observableArrayList();
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

}
