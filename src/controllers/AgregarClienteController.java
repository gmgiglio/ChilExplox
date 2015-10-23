/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Empresa;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author alberto
 */
public class AgregarClienteController implements Initializable {

     @FXML
    private TextField nombreField;
      @FXML
    private TextField apellidosField;
      @FXML
    private TextField calleField;
     @FXML
    private TextField comunaField;
     @FXML
    private TextField ciudadField;
     @FXML
    private TextField telefonoField;
     @FXML
    private Button agregarCliente;
     
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        agregarCliente.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                    
                       try{        

                           
                        
                       }
                       catch (Exception exc)
                      {
                               }
                     
                
                }
            });
    }    
    
}
