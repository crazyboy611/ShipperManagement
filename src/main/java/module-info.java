module org.doannhom7.shippermanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires AnimateFX;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.desktop;

    opens org.doannhom7.shippermanagement to javafx.fxml;
    exports org.doannhom7.shippermanagement;
    exports org.doannhom7.shippermanagement.Models;
    exports org.doannhom7.shippermanagement.Views;
    exports org.doannhom7.shippermanagement.Controllers;
    exports org.doannhom7.shippermanagement.Controllers.Admin;
    exports org.doannhom7.shippermanagement.Controllers.Shipper;
}