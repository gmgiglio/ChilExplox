/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Encomienda;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
        limpiarAlertas();
        boolean error = false;
        try{ getPeso();} 
        catch(Exception e){ aPeso(); error = true; }
        try{ getVolumen();}
        catch(Exception e){  aVol(); error = true;}
        try{ getPrioridad();}
        catch(Exception e){ aPri(); error = true;}
        if(!error) {
            handlerEncomienda.handle(new ActionEvent(this,null));
            limpiar();
        }
    }
    
    private void aPeso(){ textoAlertaPeso.setText("Peso invalido"); }
    private void aVol(){ textoAlertaVolumen.setText("Volumen invalido"); }
    private void aPri(){ textoAlertaPrioridad.setText("Prioridad invalida");}
    
    private void limpiar(){
       descField.setText("");
       pesoField.setText("");   
       volumenField.setText("");
       prioridadField.setText("");
       calleField.setText("");
       numeroField.setText("");
       comunaField.setText("");
       ciudadField.setText("");
       textPresupuesto.setText("0");
       
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
     
    List<TextField> fields;

    public AgregarEncomiendaController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/AgregarEncomienda.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
         
        TextField[] f = {descField,pesoField,volumenField,prioridadField,calleField,numeroField,comunaField,ciudadField};
        fields = Arrays.asList(f);
    }   
    
    
    public void actualizarPresupuesto(){
        
        try{ 
            int p = Encomienda.calcularPresupuesto(getPeso(), getVolumen(), getPrioridad());
            textPresupuesto.setText(Integer.toString(p));
        }
        catch(Exception ex){ }
    }
    
    
    
    
    public void handlerEnterTextField(ActionEvent e){
        actualizarPresupuesto();
          
        TextField fieldActual = (TextField) e.getSource();
        int index = fields.indexOf(fieldActual) + 1;
        if(index < fields.size()) fields.get(index).requestFocus();
        else crearEncomienda.requestFocus();
    }
    
}
