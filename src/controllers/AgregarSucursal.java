/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Sucursal;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author gianfrancogiglio
 */
public class AgregarSucursal extends VBox  {

    @FXML
    TextField nombreField, ciudadField, direccionField;
    
    public AgregarSucursal(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/AgregarSucursal.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    EventHandler handlerAgregarSucursal;
    
    public void setHandlerAgregarSucursal(EventHandler eh){
        handlerAgregarSucursal = eh;
    }
    
    Sucursal sucursal;
    
    public Sucursal getSucursal(){
        return sucursal;
    }
    
    public void botonApretado(ActionEvent event){
        sucursal = new Sucursal(nombreField.getText(),ciudadField.getText(),direccionField.getText());
        handlerAgregarSucursal.handle(event);
        limpiar();
    }
    
    public void limpiar(){
        nombreField.setText("");
        direccionField.setText("");
        ciudadField.setText("");
    }
    
}
