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
import javafx.scene.layout.BorderPane;
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
    @FXML
    private Text asunto,receptor,fecha;
    @FXML
    private BorderPane ningunoSel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        List<RegistroMensaje> registroMensajes = ((Funcionario)Main.getUsuarioActual()).getRegistroMensEnviados();
        //Funcionario u = Main.getUsuarioActual();
        for(RegistroMensaje r : registroMensajes){
            CajaMensajeEnviado cajaM = new CajaMensajeEnviado(r);
            cajaM.changeBackgroundOnHover();
            listaMensajes.getChildren().add(cajaM);
            cajaM.setOnAction(botonApretado);
        }
    }    
    
    private CajaMensajeEnviado cajaMensActual;

    
    EventHandler botonApretado = (EventHandler) (Event event) -> {
        CajaMensajeEnviado boton = (CajaMensajeEnviado) event.getTarget();
         if (cajaMensActual != null) {
            cajaMensActual.enModoNormal();
        } 
        cajaMensActual = boton;
        cajaMensActual.enModoAzul();
        ningunoSel.setVisible(false);
        String s = boton.getMensaje().getTexto();
        Text t = new Text(s);
        receptor.setText(boton.getTextoSucursal().getText());
        asunto.setText(boton.getMensaje().getNombre());
        fecha.setText(boton.getMensaje().getFecha());
        textoMensaje.getChildren().clear();
        textoMensaje.getChildren().add(t);
    };  
    
}
