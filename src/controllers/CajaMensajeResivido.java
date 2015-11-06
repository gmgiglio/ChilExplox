/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Mensaje;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author gianfrancogiglio
 */
public class CajaMensajeResivido extends Button {
    
    @FXML
    private Text textoAsunto,textoUsuario;

    private Mensaje mensaje;
            
    private int largoMaxAsunto = 40;

    public CajaMensajeResivido (Mensaje mensaje){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/CajaMensajeResivido.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
         
         this.mensaje = mensaje;
        
        setTextoAsunto(mensaje.getNombre());
        textoUsuario.setText(mensaje.getRemitente().getNombreUsuario());
    } 

    /**
     * @return the mensaje
     */
    public Mensaje getMensaje() {
        return mensaje;
    }
    
    private void setTextoAsunto(String s){
        if (s.length() > largoMaxAsunto){
            textoAsunto.setText(s.substring(0, largoMaxAsunto) + "...");
        }
        else textoAsunto.setText(s);
    }
    
}
