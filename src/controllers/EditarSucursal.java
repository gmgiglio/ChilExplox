/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Camion;
import chilexplox.Empresa;
import chilexplox.Sucursal;
import chilexplox.Tipo;
import chilexplox.front.Main;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author gianfrancogiglio
 */
public class EditarSucursal extends VBox  {

    @FXML
    Text textTitulo;
    @FXML
    Button botonNormal, botonFrio, botonAnimal, botonRadiactivo, botonBlindado,botonFragil;
            
    @FXML 
    TextField fieldNormal, fieldFrio, fieldAnimal, fieldRadiactivo, fieldBlindado,fieldFragil,
            fieldPatNormal, fieldPatFrio, fieldPatAnimal, fieldPatRadiactivo, fieldPatBlindado,fieldPatFragil;
    @FXML
    Pane paneNumRadiactivo, paneNumNormal, paneNumFrio, paneNumBlindado, paneNumAnimal, paneNumFragil ;
    
    Sucursal sucursal;
    
    Indicador contRadiactivo,contAnimal,contNormal,contBlindado,contFrio, contFragil;
    
    public EditarSucursal(Sucursal sucursal){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/EditarSucursal.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
         
         this.sucursal = sucursal;
         textTitulo.setText("Sucursal: " + sucursal.getNombre());
         
         
        botonNormal.setOnAction((EventHandler) (Event ev) -> {
            try { 
                int cap = Integer.parseInt(fieldNormal.getText()); 
                sucursal.agregarCamion(fieldPatNormal.getText(), cap, Tipo.Normal);
                contNormal.setNumero(camionesDeTipo(Tipo.Normal).size());
            } catch(Exception ex){}
        });
        
        botonFrio.setOnAction((EventHandler) (Event ev) -> {
            try { 
                int cap = Integer.parseInt(fieldFrio.getText()); 
                sucursal.agregarCamion(fieldPatFrio.getText(), cap, Tipo.Refrigerado);
                contFrio.setNumero(camionesDeTipo(Tipo.Refrigerado).size());
            } catch(Exception ex){}
        });
        
        botonAnimal.setOnAction((EventHandler) (Event ev) -> {
            try { 
                int cap = Integer.parseInt(fieldAnimal.getText());
                sucursal.agregarCamion(fieldPatAnimal.getText(), cap, Tipo.Animales);
                contAnimal.setNumero(camionesDeTipo(Tipo.Animales).size());
            } catch(Exception ex){}
        });
        
        botonRadiactivo.setOnAction((EventHandler<ActionEvent>) (ActionEvent ev) -> {
            try { 
                int cap = Integer.parseInt(fieldRadiactivo.getText()); 
                sucursal.agregarCamion(fieldPatRadiactivo.getText(), cap, Tipo.Radioactivo);
                contRadiactivo.setNumero(camionesDeTipo(Tipo.Radioactivo).size());
                
            } catch(Exception ex){}
        });
        
        botonBlindado.setOnAction((EventHandler) (Event ev) -> {
            try { 
                int cap = Integer.parseInt(fieldBlindado.getText()); 
                sucursal.agregarCamion(fieldPatBlindado.getText(), cap, Tipo.Blindado);
                contBlindado.setNumero(camionesDeTipo(Tipo.Blindado).size());
            } catch(Exception ex){}

        });
        
        botonFragil.setOnAction((EventHandler) (Event ev) -> {
            try { 
                int cap = Integer.parseInt(fieldFragil.getText()); 
                sucursal.agregarCamion(fieldPatBlindado.getText(), cap, Tipo.Fragil);
                contFragil.setNumero(camionesDeTipo(Tipo.Fragil).size());
            } catch(Exception ex){}

        });
        
        
     
        
        
        contRadiactivo = new Indicador(camionesDeTipo(Tipo.Radioactivo).size());
        contNormal = new Indicador(camionesDeTipo(Tipo.Normal).size());
        contAnimal = new Indicador(camionesDeTipo(Tipo.Animales).size());
        contFrio = new Indicador(camionesDeTipo(Tipo.Refrigerado).size());
        contBlindado = new Indicador(camionesDeTipo(Tipo.Blindado).size());
        contFragil = new Indicador(camionesDeTipo(Tipo.Fragil).size());
        
        
        contRadiactivo.setVisible(true);
        paneNumRadiactivo.getChildren().setAll(contRadiactivo);
        
        contNormal.setVisible(true);
        contAnimal.setVisible(true);
        contFrio.setVisible(true);
        contBlindado.setVisible(true);
        contFragil.setVisible(true);
        
        paneNumNormal.getChildren().setAll(contNormal);
        paneNumAnimal.getChildren().setAll(contAnimal);
        paneNumFrio.getChildren().setAll(contFrio);
        paneNumBlindado.getChildren().setAll(contBlindado);
        paneNumFragil.getChildren().setAll(contFragil);
        
    }
    
    private List<Camion> getCamiones(){
        LinkedList<Camion> result = new LinkedList();
        result.addAll(sucursal.getCamionesDisponibles());
        result.addAll(sucursal.getCamionesPend());
        return result;
    }
    
    private List<Camion> camionesDeTipo(Tipo t){
        LinkedList<Camion> result = new LinkedList();
        
        for(Camion camion : getCamiones()){
            if(camion.getTipo() == t){ result.add(camion); }
        }
            
        return result;
    }
    
}
