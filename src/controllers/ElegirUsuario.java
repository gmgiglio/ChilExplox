/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Empresa;
import chilexplox.Usuario;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author gianfrancogiglio
 */
public class ElegirUsuario extends AnchorPane{

    @FXML
    private TextField textFieldUsuario;
            
    public ElegirUsuario(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/ElegirUsuario.fxml"));
        URL url = getClass().getResource("/resources/ElegirUsuario.fxml");
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }  
    
    private EventHandler handlerUsuarioElegido;
    
    public void setHandlerUsuarioElegido(EventHandler e){
        handlerUsuarioElegido = e;
    }
    
    public void handlerTextFieldUsuario(ActionEvent event){
        handlerUsuarioElegido.handle(event);
    }
    
    public Usuario getUsuario(){
        System.out.println(textFieldUsuario.getText());
        return Empresa.getInstance().getUsuario(textFieldUsuario.getText());
    }
    
}
