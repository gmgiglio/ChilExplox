/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
    private TextField numeroField;
     @FXML
    private TextField comunaField;
     @FXML
    private TextField ciudadField;
     @FXML
    private TextField telefonoField;
     @FXML
    private Button agregarCliente;
    
    private ComboBox comboBoxClientes;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {

            Scene scene = telefonoField.getScene();
            comboBoxClientes = (ComboBox)scene.lookup("#cbc");
            
            try{        
            Empresa.agregarCliente(nombreField.getText()+" "+apellidosField.getText(),calleField.getText()+" "+numeroField.getText()+", "+comunaField.getText()+", "+ciudadField.getText(),telefonoField.getText());
            comboBoxClientes.getItems().add(nombreField.getText()+" "+apellidosField.getText());
            comboBoxClientes.setPromptText(nombreField.getText()+" "+apellidosField.getText());
            }
            catch (Exception exc)
            {
            }
    }
     
     
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    } 
    public Scene getScene(){
            return telefonoField.getScene();
    }
    
}
