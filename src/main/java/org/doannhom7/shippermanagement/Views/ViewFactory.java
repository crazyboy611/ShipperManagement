package org.doannhom7.shippermanagement.Views;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.AnchorPane;
import org.doannhom7.shippermanagement.Controllers.Admin.AdminController;
import org.doannhom7.shippermanagement.Controllers.Shipper.ShipperController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ViewFactory {
    private AccountType accountType;
    // Shipper views
    private AnchorPane shipperProfile;
    private AnchorPane shipperOrdersView;
    private final ObjectProperty<ShipperMenuOptions> shipperSelectedMenu;
    //Admin Views
    private AnchorPane allShipperCrudView;
    private AnchorPane allOrdersCrudView;
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenu;


    public ViewFactory(){
        this.accountType = AccountType.ADMIN;
        this.shipperSelectedMenu = new SimpleObjectProperty<>();
        this.adminSelectedMenu = new SimpleObjectProperty<>();
    }

    public ObjectProperty<ShipperMenuOptions> getShipperSelectedMenu() {
        return shipperSelectedMenu;
    }

    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenu() {
        return adminSelectedMenu;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }


    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
        creatStage(loader);
    }
    public void showShipperWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Shipper/Shipper.fxml"));
        ShipperController shipperController = new ShipperController();
        loader.setController(shipperController);
        creatStage(loader);
    }
    public AnchorPane getShipperProfileView() {
        try{
            if(shipperProfile == null) {
                shipperProfile = new FXMLLoader(getClass().getResource("/FXML/Shipper/ShipperProfile.fxml")).load();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return shipperProfile;
    }
    public AnchorPane getShipperOrdersView() {
        try{
            if(shipperOrdersView == null) {
                shipperOrdersView = new FXMLLoader(getClass().getResource("/FXML/Shipper/MyOrder.fxml")).load();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return shipperOrdersView;
    }
    // Admin View

    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        creatStage(loader);
    }
    public AnchorPane getAllShipperView() {
        try{
            if(allShipperCrudView == null) {
                allShipperCrudView = new FXMLLoader(getClass().getResource("/FXML/Admin/ShipperCrudView.fxml")).load();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return allShipperCrudView;
    }
    public AnchorPane getAllOrdersCrudView() {
        try{
            if(allOrdersCrudView == null) {
                allOrdersCrudView = new FXMLLoader(getClass().getResource("/FXML/Admin/OrderCrudView.fxml")).load();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return allOrdersCrudView;
    }

    private void creatStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icons8-shipper-64.png"))));
        stage.setTitle("Shipper Management");
        stage.show();
    }
    public void closeStage(Stage stage) {
        stage.close();
    }
}