/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Encomienda;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author gianfrancogiglio
 */



public class CajaEncomienda extends HBox{

    
    @FXML
    private Text textDesc;
    @FXML
    private Button botonEliminar;
    
    private Encomienda encomienda;
    
    public CajaEncomienda (Encomienda encomienda){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/CajaEncomienda.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
         
         textDesc.setText(encomienda.getDescripcion());
         
    }
    
    
    public void botonApretado(ActionEvent e){
        handlerEliminar.handle(new ActionEvent(this,null));
    }
    
    private EventHandler handlerEliminar;
    
    public void setHandlerEliminar(EventHandler eh){
        handlerEliminar = eh;
    }
    
    public Encomienda getEncomienda(){
        return encomienda;
    }
    
}