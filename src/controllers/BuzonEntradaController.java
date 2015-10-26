/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.*;
import chilexplox.front.Main;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author carlossalame
 */
public class BuzonEntradaController implements Initializable {

    @FXML
    private VBox listaMensajes;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        List<Mensaje> mensajes = Main.getUsuarioActual().getSucursalActual().getMensajesEnBuzon();
        Usuario u = Main.getUsuarioActual();
        for(Mensaje m : mensajes){
            CajaMensajeResivido cajaM = new CajaMensajeResivido(m);
            listaMensajes.getChildren().add(cajaM);
        }
        
    }    
    
}
