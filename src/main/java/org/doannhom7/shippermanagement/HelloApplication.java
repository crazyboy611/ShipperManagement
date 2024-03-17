package org.doannhom7.shippermanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.doannhom7.shippermanagement.Models.Model;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Model.getInstance().getViewFactory().showLoginWindow();
        Model.getInstance().getDatabaseDriver().getOrdersData();
    }

    public static void main(String[] args) {
        launch();
    }
}