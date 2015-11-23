/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Sucursal;
import java.io.IOException;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author gianfrancogiglio
 */
public class CajaSucursal extends HBox {

    Sucursal sucursal;
    
    @FXML
    private Text textNombre;
    
    public CajaSucursal(Sucursal sucursal){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/CajaSucursal.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        this.sucursal = sucursal;
        textNombre.setText(sucursal.getNombre());
        
    }
    
}
