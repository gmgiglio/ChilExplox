/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Cliente;
import chilexplox.front.Main;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * FXML Controller class
 *
 * @author gianfrancogiglio
 */
public class MenuPrincipalController implements Initializable {

    @FXML
    private MenuBar menuBar;
    @FXML
    private TabPane tabs;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(Main.getUsuarioActual() instanceof Cliente){
            addTab(new CrearPedido());
        }
    } 
    
    public void addTab(Node contenido){
        Tab tab = new Tab();
        tab.setContent(contenido);
        List<Tab> t = tabs.getTabs();
        t.add(tab);
    }
    
    public List<Tab> getTabs(){
        return tabs.getTabs();
    }
    
    public void borrarTab(Tab tab){
        tabs.getTabs().remove(tab);
    }
    
    
    
}
