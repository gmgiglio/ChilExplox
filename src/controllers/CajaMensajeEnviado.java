/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Mensaje;
import chilexplox.RegistroMensaje;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author gianfrancogiglio
 */
public class CajaMensajeEnviado extends Button {

    
    @FXML
    private Text textoAsunto,textoSucursal;
    
    private RegistroMensaje registro;
    
    private int largoMaxAsunto = 40;
    
    public CajaMensajeEnviado(RegistroMensaje registro){
        this.registro = registro;
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/CajaMensajeEnviado.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
         
          setTextoAsunto(registro.getMensaje().getNombre());
          textoSucursal.setText(registro.getDestino().getNombre());
    } 
    
    public Mensaje getMensaje(){
        return registro.getMensaje();
    }
    
     private void setTextoAsunto(String s){
        if (s.length() > largoMaxAsunto){
            textoAsunto.setText(s.substring(0, largoMaxAsunto) + "...");
        }
        else textoAsunto.setText(s);
    }
    
}
