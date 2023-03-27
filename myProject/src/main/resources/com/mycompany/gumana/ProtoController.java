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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author Luis
 */
public class ProtoController implements Initializable {


    @FXML
    private TextField lbl_username;
    @FXML
    private PasswordField lbl_password;
    @FXML
    private Button btnLogn;
    @FXML
    private Button btn_balik;
    @FXML
    private CheckBox cb_showPassword;
    @FXML
    private TextField txt_showPassword;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.actionshowPassword(null);
    }    
    
    //Database Tools
    private Connection conn = null;
    private PreparedStatement statement;
    private ResultSet result;
    
    public Connection connectDB(){
        
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost/myproject_db","root","");
            return conn;
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            System.out.println(e.getMessage());
        }
            return null;
    }

    @FXML
    public void btn_login (ActionEvent event){
        conn = connectDB();
        try{
            String sql = "SELECT * FROM tbl_accounts WHERE username = ? and password = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1,lbl_username.getText());
            statement.setString(2,lbl_password.getText());
            result = statement.executeQuery();
            
            if(result.next()){
                String user = result.getString("userType");
                if (user.contains("Administrator")){
                    new Alert(Alert.AlertType.INFORMATION,"Succesfully logged in!").show();
                    App.setCurrUser(new UserModel(result.getString("firstName")));
                    App.setCurrType(new UserType(result.getString("userType")));
                    App.setRoot("FXML_Dashboard");
                }
                else if (user.contains("User")){
                    new Alert(Alert.AlertType.INFORMATION,"Succesfully logged in!").show();
                    App.setCurrUser(new UserModel(result.getString("firstName")));
                    App.setCurrType(new UserType(result.getString("userType")));
                    App.setRoot("FXML_UserDashboard");
                }
    
            } else {
                new Alert(Alert.AlertType.ERROR,"Invalid Username or Password!").show();
                App.setRoot("FXML_Login");
            }
        }catch (IOException | SQLException e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
                System.out.println(e.getMessage());
                System.out.println("eto nagrun");
                e.printStackTrace();
        }
    }

    @FXML
    private void btn_balik(ActionEvent event) throws IOException {
        App.setRoot("FXML_FirstScreen");
    }

    @FXML
    private void actionshowPassword(ActionEvent event) {
        if (cb_showPassword.isSelected()) {
            txt_showPassword.setText(lbl_password.getText());
            txt_showPassword.setVisible(true);
            lbl_password.setVisible(false);
        } else {
            lbl_password.setText(txt_showPassword.getText());
            lbl_password.setVisible(true);
            txt_showPassword.setVisible(false);
        }
    }
}
