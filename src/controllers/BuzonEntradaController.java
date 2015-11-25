/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.*;
import chilexplox.front.Main;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author carlossalame
 */
public class BuzonEntradaController extends AnchorPane {

    @FXML
    private VBox listaMensajes;
    @FXML
    private TextFlow textoMensaje;
    @FXML
    private Text de,asunto,fecha;
    @FXML
    private BorderPane ningunoSel;
    
    
    
    private int cantidad=0;
    
    
    public BuzonEntradaController() {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/BuzonEntrada.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        
        List<Mensaje> mensajes = ((Funcionario)Main.getUsuarioActual()).getSucActual().getMensajesEnBuzon();
        Funcionario u = (Funcionario) Main.getUsuarioActual();
        Scene inicio =  null;
        for(Mensaje m : mensajes){
            CajaMensajeResivido cajaM = new CajaMensajeResivido(m);
            cajaM.changeBackgroundOnHover();
            if(cajaM.getMensaje().isLeido()){

                cajaM.enModoNormal();
                
            }else{
                cantidad++;
                cajaM.enModoNoLeido();
                
            }

            listaMensajes.getChildren().add(cajaM);
            cajaM.setOnAction(botonApretado);
            inicio =  cajaM.getScene();
        }
     
    }   
   
    private CajaMensajeResivido cajaMensActual;
    
    EventHandler botonApretado = (EventHandler) (Event event) -> {
        CajaMensajeResivido boton = (CajaMensajeResivido) event.getTarget();
        if (cajaMensActual != null) {
                cajaMensActual.enModoNormal();
       
        } 
        if(!boton.getMensaje().isLeido()){
        Scene inicio =  boton.getScene();
        Pane indicador = (Pane) inicio.lookup("#indicador");
        indicador.setVisible(true);
        Text cantidadText = (Text) inicio.lookup("#cantidad");
        cantidad--;
            if(cantidad<100){
                cantidadText.setText(""+(cantidad));
            }
            else{
                cantidadText.setText("99+");
            }
        
            if(cantidad==0)
            {
                indicador.setVisible(false);
            }
        } else{
            if(cantidad>0){
                
                Scene inicio =  boton.getScene();
                Pane indicador = (Pane) inicio.lookup("#indicador");
                indicador.setVisible(true);
                Text cantidadText = (Text) inicio.lookup("#cantidad");
                if(cantidad<100){
                cantidadText.setText(""+(cantidad));
                }
                else{
                    cantidadText.setText("99+");
                }


            }
                
        }
        boton.getMensaje().setLeido(true);
        
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
    public void actualizarIndicador()
    {
     if(cantidad>0){
                
                Scene inicio =  this.getScene();
                Pane indicador = (Pane) inicio.lookup("#indicador");
                indicador.setVisible(true);
                Text cantidadText = (Text) inicio.lookup("#cantidad");
                if(cantidad<100){
                cantidadText.setText(""+(cantidad));
                }
                else{
                    cantidadText.setText("99+");
                }


            }
    }
    
   
  
    
}
