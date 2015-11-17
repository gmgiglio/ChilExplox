/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.RegistroCliente;
import java.io.IOException;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author gianfrancogiglio
 */
public class FichaCliente extends VBox  {

    @FXML
    private Text textNombre, textContrasena,textNomUsuario;
    
    private RegistroCliente registro;
    
    public FichaCliente(RegistroCliente registro){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/FichaCliente.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
         
        this.registro = registro;
        textNombre.setText(registro.cliente.getNombreCompleto());
        textNomUsuario.setText(registro.cliente.getNombreUsuario());
        textContrasena.setText(registro.contrasena);
    }
    
}
