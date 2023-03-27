/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gumana;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ranillo
 */
public class FXML_UpdateController implements Initializable {

    @FXML
    private TextField txt_newproductName;
    @FXML
    private TextField txt_newproductPrice;
    @FXML
    private TextField txt_newproductModelnum;
    @FXML
    private TextField txt_newproductBrand;
    @FXML
    private ComboBox<String> cb_newcategory;
    @FXML
    private TextField txt_newproductQuantity;
    @FXML
    private Button btnUpdate;

    private Connection conn = null;
    private PreparedStatement statement;

    private ObservableList<String> Category = FXCollections.observableArrayList();
    private ProductModel selectedProd;

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
        cb_newcategory.setItems(Category);

        txt_newproductPrice.setTextFormatter(new TextFormatter<>(change -> {
            if (change.isAdded()) {
                if (change.getControlNewText().matches("[0-9]{1,}")) {
                    return change;
                }
                return null;
            }
            return change;
        }));

        selectedProd = SelectedProd.getINSTANCE().getSelectedProd();
        txt_newproductName.setText(selectedProd.getName());
        txt_newproductPrice.setText(String.valueOf(selectedProd.getPrice()));
        txt_newproductModelnum.setText(selectedProd.getModel());
        txt_newproductBrand.setText(selectedProd.getBrand());
        cb_newcategory.getSelectionModel().select(selectedProd.getCategory());
        txt_newproductQuantity.setText(String.valueOf(selectedProd.getQuantity()));

    }

    public Connection connectDB() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/myproject_db", "root", "");
            return conn;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            System.out.println(e.getMessage());
        }
        return null;
    }

    @FXML
    private void btn_updateProduct(ActionEvent event) {
        Alert alrt = new Alert(Alert.AlertType.NONE, "", new ButtonType("Try Again"));
        final String INVALID_INPUT = "Invalid Input";
        Boolean invalidInp = false;
        if (txt_newproductName.getText().isBlank()) {
            invalidInp = true;
            alrt.setTitle(INVALID_INPUT);
            alrt.setContentText(alrt.getContentText() + "Missing Product Name\n");
        }
        if (txt_newproductPrice.getText().isBlank()) {
            invalidInp = true;
            alrt.setTitle(INVALID_INPUT);
            alrt.setContentText(alrt.getContentText() + "Missing Product Price\n");
        }
        if (txt_newproductQuantity.getText().isBlank()) {
            invalidInp = true;
            alrt.setTitle(INVALID_INPUT);
            alrt.setContentText(alrt.getContentText() + "Missing Product Quantity\n");
        }
        if (txt_newproductModelnum.getText().isBlank()) {
            invalidInp = true;
            alrt.setTitle(INVALID_INPUT);
            alrt.setContentText(alrt.getContentText() + "Missing Product Number\n");
        }
        if (txt_newproductBrand.getText().isBlank()) {
            invalidInp = true;
            alrt.setTitle(INVALID_INPUT);
            alrt.setContentText(alrt.getContentText() + "Missing Product Brand\n");
        }
        if (cb_newcategory.getValue() == null) {
            alrt.setTitle(INVALID_INPUT);
            alrt.setContentText(alrt.getContentText() + "Missing Category\n");
        }
        if (invalidInp) {
            alrt.show();
            return;
        }
        conn = connectDB();
        String productName, productModel, productBrand, productCategory;
        Integer productPrice, productQuantity;
        productName = txt_newproductName.getText();
        productPrice = Integer.valueOf(txt_newproductPrice.getText());
        productQuantity = Integer.valueOf(txt_newproductQuantity.getText());
        productModel = txt_newproductModelnum.getText();
        productBrand = txt_newproductBrand.getText();
        productCategory = cb_newcategory.getValue();

        new Alert(Alert.AlertType.CONFIRMATION, "Do you want to update this product?").showAndWait().ifPresent(new Consumer<ButtonType>() {
            @Override
            public void accept(ButtonType response) {
                if (response == ButtonType.OK) {
                    try {
                        String sql = "UPDATE `tbl_products` SET `productName`=?,`productPrice`=?,`productQuantity`=?,`productModel`=?"
                                + ",`productBrand`=?,`productCategory`=? WHERE `prodID`=?";
                        statement = conn.prepareStatement(sql);
                        statement.setString(1, productName);
                        statement.setInt(2, productPrice);
                        statement.setInt(3, productQuantity);
                        statement.setString(4, productModel);
                        statement.setString(5, productBrand);
                        statement.setString(6, productCategory);
                        statement.setInt(7, selectedProd.getId());
                        statement.executeUpdate();
                        new Alert(Alert.AlertType.INFORMATION, "Succesfully Updated!").showAndWait();
                        
                    } catch (SQLException e) {
                           new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
                           System.out.println(e.getMessage());
                    }
                }
            }
        });
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

}
