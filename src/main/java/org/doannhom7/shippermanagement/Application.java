package org.doannhom7.shippermanagement;

import javafx.stage.Stage;
import org.doannhom7.shippermanagement.Models.Model;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        Model.getInstance().getViewFactory().showLoginWindow();
    }

    public static void main(String[] args) {
        launch();
    }
}