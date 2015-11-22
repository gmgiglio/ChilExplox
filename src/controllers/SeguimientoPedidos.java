/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Cliente;
import chilexplox.Pedido;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author gianfrancogiglio
 */
public class SeguimientoPedidos extends VBox {

    Cliente cliente;
   
    @FXML
    ListView listPedidos;
    
    public SeguimientoPedidos(Cliente cliente){
       
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/SeguimientoPedidos.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        this.cliente = cliente;
       for(Pedido p : cliente.getPedidos()){
           listPedidos.getItems().add(new CajaSeguimientoPedido(p));
       }
   }  
    
}
