/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.*;
import chilexplox.front.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author carlossalame
 */
public class NuevoMensajeController implements Initializable {

    @FXML
    private TextField cajaTitulo, cajaDestinatario;
    @FXML
    private TextArea cajaMensaje;
    @FXML
    private Text alerta;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }   
    
   public void handlerBotonEnviar(ActionEvent event){
       System.out.println("boton enviar apretado");
       String titulo = cajaTitulo.getText();
       String mensaje = cajaMensaje.getText();
       Sucursal destino = Empresa.getSucursal(cajaDestinatario.getText());
       try{
           Main.getUsuarioActual().enviarMens(titulo, mensaje, destino);
           cajaTitulo.setText("");
           cajaMensaje.setText("");
           cajaDestinatario.setText("");
           alerta.setText("Mensaje enviado");
       }catch(Exception e){
           alerta.setText("Error al enviar mensaje");
       }
       
       System.out.println(Empresa.getSucursal("Maipu").getMensajesEnBuzon().get(0).getTexto());
  
   }

}
