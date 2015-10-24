/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.*;
import chilexplox.front.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


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
    private TextField comunaField;
     @FXML
    private TextField ciudadField;
     @FXML
    private Button crearEncomienda;
    
    private ComboBox comboBoxEncomiendas;
    
    private Text idPedido;
    
    private Text presupuesto;

    
    @FXML
    private void handleEncomienda(ActionEvent event) {
            Scene scene = crearEncomienda.getScene();
            comboBoxEncomiendas = (ComboBox)scene.lookup("#cbe");
            idPedido = (Text)scene.lookup("#idPedido");
            presupuesto = (Text)scene.lookup("#presupuesto");
            try{        
                Pedido p = Main.getUsuarioActual().getSucursalActual().getPedido(Integer.parseInt(idPedido.getText()));
                Main.getUsuarioActual().agregarEnc(p, Integer.parseInt(pesoField.getText()), Integer.parseInt(volumenField.getText()),calleField.getText()+" "+numeroField.getText()+", "+comunaField.getText()+", "+ciudadField.getText(), descField.getText());
                comboBoxEncomiendas.getItems().add( descField.getText());
                comboBoxEncomiendas.setPromptText( descField.getText());
                presupuesto.setText(""+p.getCostoEnvio());
                    
            }
            catch (Exception exc)
            {
            }    
    }
  
     

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
