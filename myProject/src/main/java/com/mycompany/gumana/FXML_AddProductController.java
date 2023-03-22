/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gumana;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class FXML_AddProductController implements Initializable {

    @FXML
    private TextField txt_productName;
    @FXML
    private TextField txt_productPrice;
    @FXML
    private TextField txt_productModelnum;
    @FXML
    private TextField txt_productBrand;
    @FXML
    private ComboBox<String> cb_category;
    @FXML
    private TextField txt_productQuantity;

    private ObservableList<String> Category = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Category.add("Keyboard");
        Category.add("Mouse");
        Category.add("Headset");
        Category.add("Speaker");
        Category.add("Monitor");
        Category.add("Phone");
        Category.add("Laptop");
        Category.add("Webcam");
        Category.add("Miscellaneous");
        cb_category.setItems(Category);   
    }

    private Connection conn = null;
    private Connection con = null;
    private PreparedStatement statement;
    private ResultSet result;
    
    public Connection connectDB(){  
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost/myproject_db","root","");
            return conn;
        }catch (SQLException e){e.printStackTrace();}
        return null;
    }

    private void addProductButton(ActionEvent event)throws IOException{
        App.setRoot("FXML_AddProduct");
    }

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
    private void btn_addProduct(ActionEvent event) {
        conn = connectDB();
        String productName, productPrice, productQuantity, productModel, productBrand, productCategory;
        productName = txt_productName.getText();
        productPrice = txt_productPrice.getText();
        productQuantity = txt_productQuantity.getText();
        productModel = txt_productModelnum.getText();
        productBrand = txt_productBrand.getText();
        productCategory = cb_category.getValue();
        
        Alert alrt = new Alert(Alert.AlertType.NONE, "", new ButtonType("Try Again"));
        final String INVALID_INPUT = "Invalid Input";
        Boolean invalidInp = false;
        if (txt_productName.getText().isBlank()) {
            invalidInp = true;
            alrt.setTitle(INVALID_INPUT);
            alrt.setContentText(alrt.getContentText() + "Missing Product Name\n");
        }
        if (txt_productPrice.getText().isBlank()) {
            invalidInp = true;
            alrt.setTitle(INVALID_INPUT);
            alrt.setContentText(alrt.getContentText() + "Missing Product Price\n");
        }
        if (txt_productQuantity.getText().isBlank()) {
            invalidInp = true;
            alrt.setTitle(INVALID_INPUT);
            alrt.setContentText(alrt.getContentText() + "Missing Product Quantity\n");
        }
        if (txt_productModelnum.getText().isBlank()) {
            invalidInp = true;
            alrt.setTitle(INVALID_INPUT);
            alrt.setContentText(alrt.getContentText() + "Missing Product Number\n");
        }
        if (txt_productBrand.getText().isBlank()) {
            invalidInp = true;
            alrt.setTitle(INVALID_INPUT);
            alrt.setContentText(alrt.getContentText() + "Missing Product Brand\n");
        }
        if (cb_category.getValue() == null){
            alrt.setTitle(INVALID_INPUT);
            alrt.setContentText(alrt.getContentText() + "Missing Category\n");
        }
        if(invalidInp){
            alrt.show();
            return;
        }
        
        new Alert(Alert.AlertType.CONFIRMATION, "Do you want to add this product?").showAndWait().ifPresent(new Consumer<ButtonType>() {
            @Override
            public void accept(ButtonType response) {
                if (response == ButtonType.OK) {
                    try {
                        String sql = "INSERT INTO `tbl_products`(`productName`,`productPrice`,`productQuantity`,`productModel`, `productBrand`, `productCategory`) VALUES ('"+productName+"','"+productPrice+"','"+productQuantity+"','"+productModel+"','"+productBrand+"','"+productCategory+"')";                  
                        statement = conn.prepareStatement(sql);
                        statement.executeUpdate();
                        App.setRoot("FXML_Dashboard");
                        new Alert(Alert.AlertType.INFORMATION,"Succesfully added!").show();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    } catch (SQLException x){
                        System.out.println(x.getMessage());
                    }
                }
            }
        });
    }

    
}
