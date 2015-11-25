/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Cliente;
import chilexplox.Empresa;
import chilexplox.Sucursal;
import chilexplox.front.Main;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author gianfrancogiglio
 */
public class AdministrarSucursales extends HBox {

    
    @FXML
    ListView listSucursales;
    @FXML
    Button botonAgregarSucursal;
    @FXML
    AnchorPane pane;
    
    public AdministrarSucursales(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/AdministrarSucursales.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
         
        recargarSucursales();
         
    }
    
    AgregarSucursal agregarSucursal;
    
    public void agregarSucursalApretado(){
        agregarSucursal = new AgregarSucursal();
        pane.getChildren().setAll(agregarSucursal);
        agregarSucursal.setHandlerAgregarSucursal((EventHandler<ActionEvent>) (ActionEvent e) -> {
            Empresa.agregarSucursal(agregarSucursal.getSucursal());
            recargarSucursales();
            handlerCambiosSucursal.handle(new ActionEvent(this,null));
        });
    }
    
    
    public void recargarSucursales(){
        listSucursales.getItems().clear();
        for(Sucursal sucursal : Empresa.getSucursales()){
            CajaSucursal caja = new CajaSucursal(sucursal);
            listSucursales.getItems().add(caja);
            caja.setEliminarHandler((EventHandler<ActionEvent>) (ActionEvent e) -> {
                CajaSucursal caja1 = (CajaSucursal)e.getSource();
                Sucursal s = caja1.getSucursal();
                eliminarSucursal(s);
            });
            caja.setHandlerEditar((EventHandler<ActionEvent>) (ActionEvent e) -> {
                CajaSucursal caja1 = (CajaSucursal)e.getSource();
                Sucursal s = caja1.getSucursal();
                editarSucursal(s);
            });
        }
    }
    
    public void eliminarSucursal(Sucursal sucursal){
        Empresa.eliminarSucursal(sucursal);
        recargarSucursales();
        handlerCambiosSucursal.handle(new ActionEvent(this,null));
    }
    
    EventHandler handlerCambiosSucursal;
    
    public void setHandlerCambiosSucursal(EventHandler eh){
        handlerCambiosSucursal = eh;
    }
    
    public void editarSucursal(Sucursal sucursal){

        EditarSucursal edSuc = new EditarSucursal(sucursal);
        pane.getChildren().setAll(edSuc);
        
    }
    
}
