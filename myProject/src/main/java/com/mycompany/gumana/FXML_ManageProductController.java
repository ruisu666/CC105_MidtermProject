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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class FXML_ManageProductController implements Initializable {

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
    private TableView<ProductModel> viewProduct;
    @FXML
    private TextField textSearch;
    @FXML
    private Button btn_update;
    @FXML
    private Button btn_delete;
    @FXML
    private TableColumn<ProductModel, String> colName;
    @FXML
    private TableColumn<ProductModel, Integer> colPrice;
    @FXML
    private TableColumn<ProductModel, Integer> colQuantity;
    @FXML
    private TableColumn<ProductModel, String> colModel;
    @FXML
    private TableColumn<ProductModel, String> colCat;
    @FXML
    private TableColumn<ProductModel, String> colBrand;

    private final ObservableList<ProductModel> prodList = FXCollections.observableArrayList();

    private ProductModel selectedProd = null;
    private Connection conn = null;
    private PreparedStatement stmnt = null;

    /**
     * Initializes the controller class.
     *
     * @return
     */
    public ProductModel getSelectedProd() {
        return selectedProd;
    }

    public void setSelectedProd(ProductModel selectedProd) {
        this.selectedProd = selectedProd;
    }

    public Connection connectDB() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/myproject_db", "root", "");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void RefreshProdList() throws SQLException {
        conn = connectDB();
        stmnt = conn.prepareStatement("SELECT * FROM `tbl_products`");
        ResultSet result = stmnt.executeQuery();
        prodList.clear();
        while (result.next()) {
            prodList.add(new ProductModel(result.getInt("prodID"), result.getString("productName"), result.getInt("productPrice"), result.getInt("productQuantity"), result.getString("productModel"), result.getString("productBrand"), result.getString("productCategory")));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        username.setText(App.getCurrUser().getUsername());

        try {
            RefreshProdList();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, "Failed to Refresh Product List").show();
        }
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        colModel.setCellValueFactory(new PropertyValueFactory("model"));
        colBrand.setCellValueFactory(new PropertyValueFactory("brand"));
        colCat.setCellValueFactory(new PropertyValueFactory("category"));
        viewProduct.setItems(prodList);

        btn_delete.setDisable(true);
        btn_update.setDisable(true);
        FilteredList<ProductModel> filter = new FilteredList(prodList);
        textSearch.textProperty().addListener((obj, oldVal, newVal) -> {
            filter.setPredicate(prodModel -> {
                if (newVal.isBlank() || newVal.isEmpty() || newVal == null) {
                    return true;
                }
                String keyword = newVal.toLowerCase();

                return prodModel.getName().toLowerCase().contains(keyword)
                        || String.valueOf(prodModel.getPrice()).toLowerCase().contains(keyword)
                        || prodModel.getModel().toLowerCase().contains(keyword)
                        || prodModel.getBrand().toLowerCase().contains(keyword)
                        || prodModel.getCategory().toLowerCase().contains(keyword);
            });
        });
        SortedList prodSort = new SortedList(filter);
        viewProduct.setItems(prodSort);

    }

    @FXML
    private void btn_logout(ActionEvent event) {
        new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?").showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    App.setRoot("FXML_FirstScreen");
                } catch (IOException ex) {
                    Logger.getLogger(FXML_DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    private void homeButton(ActionEvent event) throws IOException {
        App.setRoot("FXML_Dashboard");
    }

    @FXML
    private void addProductButton(ActionEvent event) throws IOException {
        Stage newWindow = new Stage();
        newWindow.setTitle("Add Product");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_AddProduct.fxml"));
        newWindow.setScene(new Scene(loader.load()));
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.initOwner(((Node) event.getSource()).getScene().getWindow());
        newWindow.showAndWait();
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
    private void btn_actionUpdate(ActionEvent event) {
        try {
            Scene newScene = new Scene(FXMLLoader.load(App.class.getResource("FXML_Update.fxml")));
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.showAndWait();
            RefreshProdList();
            selectedProd = null;
            viewProduct.getSelectionModel().clearSelection();
            SelectedProd.getINSTANCE().setSelectedProd(null);
        } catch (IOException | SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }

    @FXML
    private void btn_actionDelete(ActionEvent event) {
        if (selectedProd != null) {
            try {
                conn = connectDB();
                stmnt = conn.prepareStatement("DELETE FROM `tbl_products` WHERE prodID = ?");
                stmnt.setInt(1, selectedProd.getId());
                stmnt.execute();
                selectedProd = null;
                viewProduct.getSelectionModel().clearSelection();
                RefreshProdList();
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
            }
        }

    }

    @FXML
    private void selectProd(MouseEvent event) {
        if (viewProduct.getSelectionModel().getSelectedItem() != null) {
            selectedProd = viewProduct.getSelectionModel().getSelectedItem();
            btn_delete.setDisable(false);
            btn_update.setDisable(false);
            SelectedProd.getINSTANCE().setSelectedProd(selectedProd);
        } else {
            btn_delete.setDisable(true);
            btn_update.setDisable(true);
        }
    }

}
