/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gumana;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class FXML_FirstScreenController implements Initializable {

    @FXML
    private Button btn_login;
    @FXML
    private Button btn_signup;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btn_login(ActionEvent event) throws IOException {
        App.setRoot("FXML_Login");
    }

    @FXML
    private void btn_signup(ActionEvent event) throws IOException {
        App.setRoot("FXML_Signup");
    }
    
}
