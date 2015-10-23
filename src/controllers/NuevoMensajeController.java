/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Empresa;
import chilexplox.front.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author carlossalame
 */
public class NuevoMensajeController implements Initializable {

        @FXML
    private TextField cajaTitulo;
        @FXML
    private TextArea cajaMensaje;
        @FXML
    private TextField cajaDestinatario;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
   public void handlerBotonEnviar(ActionEvent event){
       System.out.println("boton enviar apretado");
       Main.getUsuarioActual().enviarMensaje(cajaTitulo.getText(), cajaMensaje.getText(),Empresa.getInstance().getSucursal(cajaDestinatario.getText()) );
   }
    
}
