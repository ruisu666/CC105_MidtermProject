package com.mycompany.gumana;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXML_UserDashboardController implements Initializable{

    
    @FXML
    private Button homeButton;
    @FXML
    private Button addProductButton;
    @FXML
    private Label username;
    @FXML
    private Button btn_logout;
    @FXML
    private Button manageProductsbtn;
    @FXML
    private BarChart<?, ?> homeTotalMonitoredChart;
    @FXML
    private Button viewDetailsbtn;
    @FXML
    private Label accountType;
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        username.setText(App.getCurrUser().getUsername());
        accountType.setText(App.getCurrUser().getType());
        disablebtns();
    }    

    @FXML
    private void addProductButton(ActionEvent event) throws IOException{
        
                Stage newWindow = new Stage();
                newWindow.setTitle("Add Product");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_AddProduct.fxml"));
                newWindow.setScene(new Scene(loader.load()));
                newWindow.initModality(Modality.APPLICATION_MODAL);
                newWindow.initOwner(((Node)event.getSource()).getScene().getWindow());
                newWindow.showAndWait();
    }

    @FXML
    private void btn_logout(ActionEvent event) throws IOException {
        new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to logout?").showAndWait().ifPresent(response->{
            if (response == ButtonType.OK){
                try {
                    App.setRoot("FXML_FirstScreen");
                    App.setCurrUser(null);
                } catch (IOException ex) {
                    new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    @FXML
    private void homeButton(ActionEvent event) throws IOException {
        App.setRoot("FXML_UserDashboard");
    }

    @FXML
    private void actionViewProducts(ActionEvent event) throws IOException {
        App.setRoot("FXML_UserViewProduct");
    }

    @FXML
    private void actionManageProducts(ActionEvent event) throws IOException {
        App.setRoot("FXML_ManageProduct");
    }
    
    private void disablebtns() {
        addProductButton.setDisable(true);
        manageProductsbtn.setDisable(true);
    }
}
