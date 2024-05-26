package org.doannhom7.shippermanagement.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import org.doannhom7.shippermanagement.Controllers.Admin.OrderListCellController;
import org.doannhom7.shippermanagement.Models.DeliveryNote;

public class OrderNoteCellFactory extends ListCell<DeliveryNote> {
    @Override
    protected void updateItem(DeliveryNote deliveryNote, boolean empty) {
        super.updateItem(deliveryNote, empty);
        if(empty) {
            setText(null);
            setGraphic(null);
        }else{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Admin/OrderNoteListCell.fxml"));
            OrderListCellController orderListCellController = new OrderListCellController(deliveryNote);
            fxmlLoader.setController(orderListCellController);
            setText(null);
            try {
                setGraphic(fxmlLoader.load());
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
