/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Sucursal;
import chilexplox.Tipo;
import java.io.IOException;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    @FXML
    Button botonNormal, botonFrio, botonAnimal, botonRadiactivo, botonBlindado;
    @FXML 
    TextField fieldNormal, fieldFrio, fieldAnimal, fieldRadiactivo, fieldBlindado;
    
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
         
         
        botonNormal.setOnAction((EventHandler) (Event ev) -> {
            try { 
                int cap = Integer.parseInt(fieldNormal.getText()); 
                sucursal.agregarCamion(null, cap, Tipo.Normal);
            } catch(Exception ex){}
        });
        
        botonFrio.setOnAction((EventHandler) (Event ev) -> {
            try { 
                int cap = Integer.parseInt(fieldFrio.getText()); 
                sucursal.agregarCamion(null, cap, Tipo.Refrigerado);
            } catch(Exception ex){}
        });
        
        botonAnimal.setOnAction((EventHandler) (Event ev) -> {
            try { 
                int cap = Integer.parseInt(fieldAnimal.getText()); 
                sucursal.agregarCamion(null, cap, Tipo.Animales);
            } catch(Exception ex){}
        });
        
        botonRadiactivo.setOnAction((EventHandler) (Event ev) -> {
            try { 
                int cap = Integer.parseInt(fieldRadiactivo.getText()); 
                sucursal.agregarCamion(null, cap, Tipo.Radioactivo);
            } catch(Exception ex){}
        });
        
        botonBlindado.setOnAction((EventHandler) (Event ev) -> {
            try { 
                int cap = Integer.parseInt(fieldBlindado.getText()); 
                sucursal.agregarCamion(null, cap, Tipo.Blindado);
            } catch(Exception ex){}
        });
    }
    
    
    
    
    
}
