/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.*;
import chilexplox.front.Main;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author alberto
 */
public class AgregarPedidoController extends VBox {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button crearPedido;
     
      @FXML
    private ComboBox comboBoxSucursales;
       @FXML
    private Text textoAlertaSucursal;
       @FXML
    private Button botonCrearPedido;
    
    private Sucursal sucursal;
    
    
    private EventHandler handlerPedido;
    
    public void sethandlerPedido(EventHandler eh){
        handlerPedido = eh;
    }
    
    public void crearPedido(ActionEvent event) {
            
     
            if(!(comboBoxSucursales.getSelectionModel().getSelectedItem()==null)){
                sucursal = Empresa.getSucursal((String)comboBoxSucursales.getSelectionModel().getSelectedItem());
                handlerPedido.handle(event);
            } 
            else { textoAlertaSucursal.setText("Por favor seleccione una sucursal"); }

    }
    
    public Sucursal getSucursal(){
        return sucursal;
    }
    
    
    public AgregarPedidoController(){
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/AgregarPedido.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        cargarNombresSucursales();
    }
  
    public void cargarNombresSucursales(){

            ObservableList nombreSucursales = FXCollections.observableArrayList();
            for (int i=0; i<  Empresa.getSucursales().size();i++)
            {
                if(!Empresa.getSucursales().get(i).getNombre().equals(Main.getUsuarioActual().getSucActual().getNombre()))
                nombreSucursales.add(Empresa.getSucursales().get(i).getNombre());
            }
            
            comboBoxSucursales.getItems().setAll(nombreSucursales);
            comboBoxSucursales.setPromptText("seleccione una sucursal");
    }
    
}
