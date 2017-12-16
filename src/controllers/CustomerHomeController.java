package controllers;

import data.ShopContract.CustomerEntry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import models.Customer;
import service.ControllerService;
import service.DbManager;
import service.StageService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerHomeController {

    @FXML
    private Label labelWelcome;
    private Customer customer;
    private StageService stage = new StageService();

    /**
     * Initialize the class.
     *
     * @param customer Passing a {@link Customer} object.
     */
    public void initialize(Customer customer) {
        this.customer = customer;
        labelWelcome.setText("Welcome " + customer.getFirstName() + " " + customer.getLastName());
    }

    /**
     * Edit Customer button action.
     * Opens a new stage {@link AddEditCustomerController}
     */
    @FXML
    private void editCustomer(ActionEvent actionEvent) throws IOException {
        stage.loadStage(actionEvent, customer, ControllerService.ADD_EDIT_CUSTOMER);
    }

    @FXML
    private void deleteCustomer(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close Account");
        alert.setHeaderText("Are you sure you want to close account?");
        alert.setContentText("USERNAME: " + customer.getUsername()
                + "\nNAME: " + customer.getFirstName()
                + " " + customer.getLastName());

        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {

            String sql = "DELETE FROM " + CustomerEntry.TABLE_NAME + " WHERE "
                    + CustomerEntry.COLUMN_USERNAME + " = ?";

            try (Connection conn = DbManager.Connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // Set the product ID
                pstmt.setString(1, customer.getUsername());
                // execute the delete statement
                pstmt.executeUpdate();

                // Logout Customer
                logout(actionEvent);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void viewOrders(ActionEvent actionEvent) throws IOException {
        stage.loadStage(actionEvent, customer, ControllerService.VIEW_ORDERS);
    }

    @FXML
    private void viewProducs(ActionEvent actionEvent) throws IOException {
        stage.loadStage(actionEvent, customer, ControllerService.VIEW_PRODUCTS);
    }

    /**
     * Log out button action.
     * Opens a new stage {@link CustomerLoginController}
     */
    @FXML
    private void logout(ActionEvent actionEvent) throws IOException {
        stage.loadStage(actionEvent, ControllerService.CUSTOMER_LOGIN);
    }
}
