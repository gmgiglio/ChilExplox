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
public class AgregarEncomiendaController implements Initializable {
    
    @FXML
    private TextField descField;
      @FXML
    private TextField pesoField;
      @FXML
    private TextField volumenField;
     @FXML
    private TextField prioridadField;
      @FXML
    private TextField calleField;
      @FXML
    private TextField numeroField;
     @FXML
    private TextField ciudadField;
     @FXML
    private Button crearEncomienda;
    
    private ComboBox comboBoxEncomiendas;
    
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {

            /*Scene scene = ciudadField.getScene();
            comboBoxEncomiendas = (ComboBox)scene.lookup("#cbe");
            
             
            try{        
            Empresa.getInstance().agregarEncomienda(descField.getText());
            comboBoxEncomiendas.getItems().add(descField.getText()+" "+apellidosField.getText());
            comboBoxEncomiendas.setPromptText(descField.getText()+" "+apellidosField.getText());
            }
            catch (Exception exc)
            {
            }*/
    }
     

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
