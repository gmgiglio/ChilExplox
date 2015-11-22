/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Mensaje;
import chilexplox.front.Main;
import java.awt.Desktop.Action;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.net.URL;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author gianfrancogiglio
 */
public class CajaMensajeResivido extends Button {
    
    @FXML
    private Text textoAsunto,textoUsuario;
    
    
    private static final String STANDARD_BUTTON_STYLE = "-fx-background-color: #FFF;";
    private static final String HOVERED_BUTTON_STYLE  = "-fx-background-color: #F1F1F1;";
    private static final String ACTIVE_BUTTON_STYLE  = "-fx-background-color: #1269D9;";

    private boolean modoAzul = false;

    private Mensaje mensaje;
            
    private int largoMaxAsunto = 40;
    
    public void enModoAzul(){
        setStyle(ACTIVE_BUTTON_STYLE);
        textoAsunto.setFill(Color.WHITE);
        textoUsuario.setFill(Color.WHITE);
        modoAzul = true;
        setOnMouseExited(null);
        setOnMouseEntered(null);
    }
    
    public void enModoNormal(){
        setStyle(STANDARD_BUTTON_STYLE);
        textoAsunto.setFill(Color.BLACK);
        textoUsuario.setFill(Color.BLACK);
        modoAzul = false;
        changeBackgroundOnHover();
    }

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
        if(mensaje.getRemitente()!=null){
        textoUsuario.setText(mensaje.getRemitente().getNombreUsuario());
        }
        else
        {
            textoUsuario.setText("");
            
        }
        
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
   
    public void changeBackgroundOnHover(){
    setStyle(STANDARD_BUTTON_STYLE);
    setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        setStyle(HOVERED_BUTTON_STYLE);
        textoAsunto.setFill(Color.BLACK);
        textoUsuario.setFill(Color.BLACK);  
      }
    });
    
    setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        setStyle(STANDARD_BUTTON_STYLE);
        textoAsunto.setFill(Color.BLACK);
        textoUsuario.setFill(Color.BLACK);

      }
    });
    setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override public void handle(MouseEvent mouseEvent) {
                
                setStyle(ACTIVE_BUTTON_STYLE);
                textoAsunto.setFill(Color.WHITE);
                textoUsuario.setFill(Color.WHITE);
            }
    }); 
   
        
    }
    
}
