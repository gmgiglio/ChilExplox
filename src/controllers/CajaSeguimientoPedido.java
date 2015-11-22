/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Pedido;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author gianfrancogiglio
 */
public class CajaSeguimientoPedido extends HBox {

    private Pedido pedido;
    
    @FXML
    private Text textNombre, textEstado;
    
    public CajaSeguimientoPedido(Pedido pedido){
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/CajaSeguimientoPedido.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        
        
        this.pedido = pedido;
        textNombre.setText(Integer.toHexString(pedido.getIdPedido()));
        textEstado.setText(pedido.getEstado().toString());
    }
    
}
