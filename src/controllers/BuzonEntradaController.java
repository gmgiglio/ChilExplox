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
public class BuzonEntradaController implements Initializable {

    @FXML
    private VBox listaMensajes;
    @FXML
    private TextFlow textoMensaje;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        List<Mensaje> mensajes = ((Funcionario)Main.getUsuarioActual()).getSucActual().getMensajesEnBuzon();
        Funcionario u = (Funcionario) Main.getUsuarioActual();
        for(Mensaje m : mensajes){
            CajaMensajeResivido cajaM = new CajaMensajeResivido(m);
            listaMensajes.getChildren().add(cajaM);
            cajaM.setOnAction(botonApretado);
        }
        
    }   
    
    EventHandler botonApretado = (EventHandler) (Event event) -> {
        CajaMensajeResivido boton = (CajaMensajeResivido) event.getTarget();
        String s = boton.getMensaje().getTexto();
        Text t = new Text(s);
        textoMensaje.getChildren().clear();
        textoMensaje.getChildren().add(t);
    };
    
    
    public void mostrarMensaje (Mensaje m){
        
    }
    
}
