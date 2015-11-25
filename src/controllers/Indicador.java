/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 *
 * @author alberto
 */
public class Indicador extends Pane {
    
    @FXML
    private Text textNumero;
    
    public Indicador(int num){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/IndicadorNuevos.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        setNumero(num);
    }
    
    public void setNumero(int num){
        if (num > 99){ textNumero.setText("99+");}
        else {textNumero.setText(""+num);}
    }
}
