/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gumana;

/**
 *
 * @author Ranillo
 */
public class SelectedProd {
    private ProductModel selectedProd;

    private static final SelectedProd INSTANCE = new SelectedProd();
    
    private SelectedProd(){}

    public static SelectedProd getINSTANCE() {
        return INSTANCE;
    }

    
    public ProductModel getSelectedProd() {
        return selectedProd;
    }

    public void setSelectedProd(ProductModel selectedProd) {
        this.selectedProd = selectedProd;
    }
    
}
