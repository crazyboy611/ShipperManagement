package org.doannhom7.shippermanagement.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.doannhom7.shippermanagement.Models.DeliveryNote;
import org.doannhom7.shippermanagement.Models.Model;
import org.doannhom7.shippermanagement.Views.OrderNoteCellFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderNoteListViewController implements Initializable {
    public ListView<DeliveryNote> order_note_list_view;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        order_note_list_view.setMouseTransparent(false);
        order_note_list_view.setFocusTraversable(false);
        order_note_list_view.setItems(Model.getInstance().getDeliveryNotes());
        order_note_list_view.setCellFactory(orderNoteCell -> new OrderNoteCellFactory());
    }
    public void initData() {
        Model.getInstance().setDeliveryNotes();
    }
}