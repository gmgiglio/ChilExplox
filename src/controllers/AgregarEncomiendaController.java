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
    protected TextField descField,pesoField,volumenField,prioridadField,calleField,numeroField,comunaField,ciudadField;
     @FXML
    protected Button crearEncomienda;

    @FXML
    private Text textoAlertaPeso,textoAlertaVolumen,textoAlertaPrioridad;

    
    public void botonApretado(ActionEvent event) {
        
        if(actualizarAlertas()) {
            handlerEncomienda.handle(new ActionEvent(this,null));
            limpiar();
        }
    }
    
    private void aPeso(){ textoAlertaPeso.setText("Peso inválido"); }
    private void aVol(){ textoAlertaVolumen.setText("Volumen inválido"); }
    private void aPri(){ textoAlertaPrioridad.setText("Prioridad inválida");}
    
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
    
    //true si esta todo en orden y false si hay error
    public boolean actualizarAlertas(){
        limpiarAlertas();
        
        boolean error = false;
        int peso = 0, volumen = 0, prioridad = 0;
        
        try{ peso = getPeso();} 
        catch(Exception e){ aPeso(); error = true; }
        try{ volumen = getVolumen();}
        catch(Exception e){  aVol(); error = true;}
        try{ prioridad = getPrioridad();}
        catch(Exception e){ aPri(); error = true;}
        
        return !error;
        
    }
    
    
    
    
    public void handlerEnterTextField(ActionEvent e){

        TextField fieldActual = (TextField) e.getSource();
        int index = fields.indexOf(fieldActual) + 1;
        if(index < fields.size()) fields.get(index).requestFocus();
        else crearEncomienda.requestFocus();
    }
    
    public void handlerEnterPesoField(ActionEvent event){ 
        try{ getPeso();}
        catch(Exception e){ aPeso(); }
        handlerEnterTextField(event);
    }
    
    public void handlerEnterVolumenField(ActionEvent event){ 
        try{ getVolumen();}
        catch(Exception e){ aVol(); }
        handlerEnterTextField(event);
    }
    
    public void handlerEnterDescField(ActionEvent event){
        String desc = getDescr();
        if(desc.length() >= Encomienda.getLargoMaximoDesc()){
            descField.setText(desc.substring(0,Encomienda.getLargoMaximoDesc()));
        }
        handlerEnterTextField(event);
    }
    
}
