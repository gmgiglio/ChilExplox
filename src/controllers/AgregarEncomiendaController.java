/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Encomienda;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * FXML Controller class
 *
 * @author alberto
 */
public class AgregarEncomiendaController extends VBox {
    
    @FXML
    private TextField descField,pesoField,volumenField,prioridadField,calleField,numeroField,comunaField,ciudadField;
     @FXML
    private Button crearEncomienda;
    @FXML
    private ComboBox comboBoxEncomiendas;
    @FXML
    private Text idPedido,presupuesto,textoAlertaPeso,textoAlertaVolumen,textoAlertaPrioridad,textPresupuesto;

    
    public void botonApretado(ActionEvent event) {
        boolean error = false;
        try{ getPeso();} 
        catch(Exception e){ textoAlertaPeso.setText("Peso invalido"); error = true;}
        try{ getVolumen();}
        catch(Exception e){ textoAlertaVolumen.setText("Volumen invalido"); error = true;}
        try{ getPrioridad();}
        catch(Exception e){ textoAlertaPrioridad.setText("Prioridad invalida"); error = true;}
        if(!error) {
            limpiar();
            handlerEncomienda.handle(new ActionEvent(this,null));
        }
    }
    
    private void limpiar(){
       descField.setText("");
       pesoField.setText("");   
       volumenField.setText("");
       prioridadField.setText("");
       calleField.setText("");
       numeroField.setText("");
       comunaField.setText("");
       ciudadField.setText(""); 
       
       limpiarAlertas();
    }
    
    private void limpiarAlertas(){
        textoAlertaPeso.setText("");
        textoAlertaVolumen.setText("");
        textoAlertaPrioridad.setText("");
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
    
    public void actualizarPresupuesto(ActionEvent e){
        
        try{ 
            int p = Encomienda.calcularPresupuesto(getPeso(), getVolumen(), getPrioridad());
            textPresupuesto.setText(Integer.toString(p));
        }
        catch(Exception ex){ }
    }
    
}
