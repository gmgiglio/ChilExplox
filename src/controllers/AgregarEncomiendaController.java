/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


/**
 * FXML Controller class
 *
 * @author alberto
 */
public class AgregarEncomiendaController extends AnchorPane {
    
    @FXML
    private TextField descField,pesoField,volumenField,prioridadField,calleField,numeroField,comunaField,ciudadField;
     @FXML
    private Button crearEncomienda;
    
    private ComboBox comboBoxEncomiendas;
    
    private Text idPedido,presupuesto;

    /*
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
    
    */
    
    public void botonApretado(ActionEvent event) {
        handlerEncomienda.handle(new ActionEvent(this,null));
    }
    
    private EventHandler handlerEncomienda;
    
    public void setHandlerEncomienda(EventHandler eh){
        handlerEncomienda = eh;
    }
    
    public int getPeso(){
        return Integer.parseInt(pesoField.getText());
    }
    
    public int getVolumen(){
        return Integer.parseInt(volumenField.getText());
    }
    
    public String getDescr(){
        return descField.getText();
    }
    
    public String getDirDestino(){
        return calleField.getText()+" "+numeroField.getText()+", "+comunaField.getText()+", "+ciudadField.getText();
    }
    
    public int getPrioridad(){
        if(prioridadField.getText().equals("")) return 0;
        else return Integer.parseInt(prioridadField.getText());
         
    }
     

    public AgregarEncomiendaController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/AgregarEncomienda.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
}
