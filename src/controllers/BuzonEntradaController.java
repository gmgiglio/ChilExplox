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
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
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
    @FXML
    private Text de,asunto,fecha;
    @FXML
    private BorderPane ningunoSel;
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        List<Mensaje> mensajes = ((Funcionario)Main.getUsuarioActual()).getSucActual().getMensajesEnBuzon();
        Funcionario u = (Funcionario) Main.getUsuarioActual();
        for(Mensaje m : mensajes){
            CajaMensajeResivido cajaM = new CajaMensajeResivido(m);
            cajaM.changeBackgroundOnHover();
            listaMensajes.getChildren().add(cajaM);
            cajaM.setOnAction(botonApretado);
        }
        
    }   
    
    private CajaMensajeResivido cajaMensActual;
    
    EventHandler botonApretado = (EventHandler) (Event event) -> {
        CajaMensajeResivido boton = (CajaMensajeResivido) event.getTarget();
        if (cajaMensActual != null) {
            cajaMensActual.enModoNormal();
        } 
        cajaMensActual = boton;
        cajaMensActual.enModoAzul();
        ningunoSel.setVisible(false);

        String s = boton.getMensaje().getTexto();
        Text t = new Text(s);
        
        de.setText(boton.getMensaje().getRemitente().getNombreUsuario());
        asunto.setText(boton.getMensaje().getNombre());
        fecha.setText(boton.getMensaje().getFecha());
        
        textoMensaje.getChildren().clear();
        textoMensaje.getChildren().add(t);
    };
    
    
    public void mostrarMensaje (Mensaje m){
        
    }
    
   
  
    
}
