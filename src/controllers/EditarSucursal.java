/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Sucursal;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author gianfrancogiglio
 */
public class EditarSucursal extends VBox  {

    @FXML
    Text textTitulo;
    Sucursal sucursal;
    
    public EditarSucursal(Sucursal sucursal){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/EditarSucursal.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
         
         this.sucursal = sucursal;
         textTitulo.setText("Sucursal: " + sucursal.getNombre());
    }
    
    
    
    
    
}
