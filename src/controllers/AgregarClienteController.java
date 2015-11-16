/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.*;
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
public class AgregarClienteController extends VBox {

     @FXML
    private TextField nombreField,telefonoField,ciudadField,comunaField,numeroField,calleField,apellidosField;

     @FXML
    private Button agregarCliente;
      @FXML
    private Text textoAlertaNombre,textoAlertaApellidos,textoAlertaTelefono;
    
    private ComboBox comboBoxClientes;
    
    public void botonApretado(ActionEvent event) {
        boolean error = false;
        if(nombreField.getText().equals("")){
            textoAlertaNombre.setText("Este campo es requerido"); 
            error = true;}
        if(apellidosField.getText().equals("")){
          textoAlertaApellidos.setText("Este campo es requerido"); 
            error = true;
        }
        if(telefonoField.getText().equals("")){
          textoAlertaTelefono.setText("Este campo es requerido"); 
            error = true;
        } 
        if(!(Empresa.getCliente(nombreField.getText()+" "+apellidosField.getText(), telefonoField.getText())==null)){
            textoAlertaTelefono.setText("Ya existe el cliente"); 
            error = true;
         }
                
        if(telefonoField.getText().equals("")){
          textoAlertaTelefono.setText("Este campo es requerido"); 
            error = true;
        } 
        else{
        try{ Integer.parseInt(telefonoField.getText().replace(" ", ""));}
        catch(Exception e){ textoAlertaTelefono.setText("Teléfono inválido"); error = true;}
        }
        if(!error) handlerCliente.handle(new ActionEvent(this,null));
    }
    
    private EventHandler handlerCliente;
    
    public void setHandlerCliente(EventHandler eh){
        handlerCliente = eh;
    }
    
    private List<TextField> fields;
     
    public AgregarClienteController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/AgregarCliente.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
         
         TextField[] f = { nombreField,apellidosField,calleField,numeroField,comunaField, ciudadField,telefonoField };
         fields = Arrays.asList(f);
    
    }
    
    
    public String getNombre(){
        return nombreField.getText();
    }
    public String[] getApellidos(){
        return apellidosField.getText().split(" ");
    }
    public String getCalle(){
        return calleField.getText();
    }
    
    public String getNumero(){
        return numeroField.getText();
    }
    public String getComuna(){
        return comunaField.getText();
    }
    public String getCiudad(){
        return ciudadField.getText();
    }
     public String getTelefono(){
        return telefonoField.getText();
    }
     
    public void handlerEnterTextField(ActionEvent e){
          
        TextField fieldActual = (TextField) e.getSource();
        int index = fields.indexOf(fieldActual) + 1;
        if(index < fields.size()) fields.get(index).requestFocus();
        else agregarCliente.requestFocus();
    }

}