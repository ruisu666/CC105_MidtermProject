/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gumana;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class FXML_ViewProductController implements Initializable {

    @FXML
    private Label username;
    @FXML
    private Button btn_logout;
    @FXML
    private Button homeButton;
    @FXML
    private Button addProductButton;
    @FXML
    private Button viewDetailsbtn;
    @FXML
    private Button manageProductsbtn;
    @FXML
    private TableView<?> viewProduct;
    @FXML
    private TextField textSearch;
    @FXML
    private Button searchbtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      username.setText(App.getCurrUser().getUsername());
    }    

    @FXML
    private void btn_logout(ActionEvent event) {
        new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to logout?").showAndWait().ifPresent(response->{
            if (response == ButtonType.OK){
                try {
                    App.setRoot("FXML_FirstScreen");
                } catch (IOException ex) {
                    Logger.getLogger(FXML_DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    private void homeButton(ActionEvent event) throws IOException{
        App.setRoot("FXML_Dashboard");
    }

    @FXML
    private void addProductButton(ActionEvent event) throws IOException {
                Stage newWindow = new Stage();
                newWindow.setTitle("Add Product");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_AddProduct.fxml"));
                newWindow.setScene(new Scene(loader.load()));
                newWindow.show();
    }

    @FXML
    private void actionViewProducts(ActionEvent event) throws IOException {
        App.setRoot("FXML_ViewProduct");
    }

    @FXML
    private void actionManageProducts(ActionEvent event) throws IOException {
        App.setRoot("FXML_ManageProduct");
    }

    @FXML
    private void searchbtn(ActionEvent event) {
    }
    
}
