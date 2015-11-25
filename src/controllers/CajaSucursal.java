/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Sucursal;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author gianfrancogiglio
 */
public class CajaSucursal extends VBox {

    Sucursal sucursal;
    
    @FXML
    private Text textNombre;
    @FXML
    private Button botonEliminar,botonMenos;
    
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
        modoNormal();
    }
    
    public Sucursal getSucursal(){
        return sucursal;
    }
    
    EventHandler eliminarHandler;
    
    public void setEliminarHandler(EventHandler eh){
        eliminarHandler = eh;
    }
    
    
    public void botonEliminarApretado(ActionEvent event){
        eliminarHandler.handle(new ActionEvent(this,null));
    }
    
    EventHandler handlerEditar;
    
    public void setHandlerEditar(EventHandler eh){
        handlerEditar = eh;
    }
    
    public void botonEditarApretado(ActionEvent event){
        handlerEditar.handle(new ActionEvent(this,null));
    }
    
    double altoNormal = 29;
    double altoEliminar = 60;
    
    public void modoNormal(){
        botonEliminar.setVisible(false);
        this.setPrefHeight(altoNormal);
        botonMenos.setOnAction((EventHandler) (Event event) -> { modoEliminar();}  );
    }
    
    public void modoEliminar(){
        botonEliminar.setVisible(true);
        this.setPrefHeight(altoEliminar);
        botonMenos.setOnAction((EventHandler) (Event event) -> { modoNormal(); }  );
    }
    
}
