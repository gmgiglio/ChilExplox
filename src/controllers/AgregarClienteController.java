/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
        if(!error) handlerEncomienda.handle(new ActionEvent(this,null));
    }
    public void setHandlerEncomienda(EventHandler eh){
        handlerEncomienda = eh;
    }
     
    public AgregarClienteController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/AgregarCliente.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    
    }
    private EventHandler handlerEncomienda;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {

           
    }
    public String getNombre(){
        return nombreField.getText();
    }
    public String getApellidos(){
        return apellidosField.getText();
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
    
     
     
}
