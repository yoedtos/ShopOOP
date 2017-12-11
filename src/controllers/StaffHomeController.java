package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.Staff;
import service.ControllerService;
import service.StageService;

import java.io.IOException;

public class StaffHomeController {

    @FXML
    private Label labelWelcome;

    private Staff staff;
    private StageService stage = new StageService();

    /**
     * Initialize the class.
     *
     * @param staff Passing a {@link Staff} object.
     */
    public void initialize(Staff staff) {
        this.staff = staff;
        //Staff.displayGreeting(labelWelcome, staff);
        labelWelcome.setText(staff.displayGreeting());
    }

    /**
     * Modify Products button action.
     * Opens a new stage {@link ModifyProductController}
     */
    @FXML
    private void modifyProducts(ActionEvent actionEvent) throws IOException {
        stage.loadStage(actionEvent, staff, ControllerService.MODIFY_PRODUCTS);
    }

    /**
     * Log out button action.
     * Opens a new stage {@link StaffLoginController}
     */
    @FXML
    private void logout(ActionEvent actionEvent) throws IOException {
        stage.loadStage(actionEvent, ControllerService.STAFF_LOGIN);

    }
}
