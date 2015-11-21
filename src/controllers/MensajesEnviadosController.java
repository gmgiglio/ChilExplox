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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author carlossalame
 */
public class MensajesEnviadosController implements Initializable {

    
    @FXML
    private VBox listaMensajes;
    @FXML
    private TextFlow textoMensaje;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        List<RegistroMensaje> registroMensajes = ((Funcionario)Main.getUsuarioActual()).getRegistroMensEnviados();
        //Funcionario u = Main.getUsuarioActual();
        for(RegistroMensaje r : registroMensajes){
            CajaMensajeEnviado cajaM = new CajaMensajeEnviado(r);
            listaMensajes.getChildren().add(cajaM);
            cajaM.setOnAction(botonApretado);
        }
    }    
    
    EventHandler botonApretado = (EventHandler) (Event event) -> {
        CajaMensajeEnviado boton = (CajaMensajeEnviado) event.getTarget();
        String s = boton.getMensaje().getTexto();
        Text t = new Text(s);
        textoMensaje.getChildren().clear();
        textoMensaje.getChildren().add(t);
    };
    
}
