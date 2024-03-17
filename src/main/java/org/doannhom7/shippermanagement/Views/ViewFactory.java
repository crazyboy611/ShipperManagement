package org.doannhom7.shippermanagement.Views;

import org.doannhom7.shippermanagement.Models.AccountType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ViewFactory {
    private AccountType accountType;
    // Client views
//    private final ObjectProperty<ClientMenuOptions> clientSelectedMenu;
    //Admin Views
//    private final ObjectProperty<AdminMenuOptions> adminSelectedMenu;


    public ViewFactory(){
        this.accountType = AccountType.SHIPPER;
//        this.clientSelectedMenu = new SimpleObjectProperty<>();
//        this.adminSelectedMenu = new SimpleObjectProperty<>();
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


    // Admin View

//    public void showAdminWindow() {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Admin.fxml"));
//        AdminController adminController = new AdminController();
//        loader.setController(adminController);
//        creatStage(loader);
//    }

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