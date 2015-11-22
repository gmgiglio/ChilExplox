/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Cliente;
import chilexplox.Empresa;
import chilexplox.Funcionario;
import chilexplox.Sucursal;
import chilexplox.front.Main;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
    @FXML
    private Menu menuSucursal;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(Main.getUsuarioActual() instanceof Cliente){
            addTab(new CrearPedido(),"Pedido");
        }
        
        
        
        LinkedList<Sucursal> sucursales = Empresa.getSucursales();
        
       
        Menu menuUsuario = new Menu(Main.getUsuarioActual().getNombreUsuario());
        MenuItem itemCerrarSesion = new MenuItem("Cerrar sesión");
        itemCerrarSesion.setOnAction((ActionEvent e) -> {
            Main.cerrarSesion();
        });
        menuUsuario.getItems().add(itemCerrarSesion);
        Main.getUsuarioActual().setSucActual(Empresa.getSucursales().get(0));
        ItemSucursalMenu i = new ItemSucursalMenu(Main.getUsuarioActual().getSucActual());
        menuSucursal = new Menu(Main.getUsuarioActual().getSucActual().getNombre());
        
        //agregar sucursales al menu de sucursales
        LinkedList<Sucursal> sucEnLista = new LinkedList(sucursales);
        sucEnLista.remove(Main.getUsuarioActual().getSucActual());
        for(Sucursal s : sucEnLista){
                ItemSucursalMenu item = new ItemSucursalMenu(s);
                menuSucursal.getItems().add(item); 
                item.setOnAction(eventoSucursal);  
                
        }
        menuBar.getMenus().addAll(menuUsuario,menuSucursal);  
        
    }
    
    private EventHandler eventoSucursal = new EventHandler() {

        public void handle(Event e) {
            ItemSucursalMenu item1 = (ItemSucursalMenu)e.getSource();
            Sucursal suc = item1.getSucursal();
            menuSucursal.setText(suc.getNombre());
            ItemSucursalMenu item2 = new ItemSucursalMenu(Main.getUsuarioActual().getSucActual());
            item2.setOnAction(eventoSucursal);
            ((Funcionario)Main.getUsuarioActual()).setSucActual(suc);
            menuSucursal.getItems().remove(item1);
            menuSucursal.getItems().add(item2);
            //actualizarPestanaAdm();
        }
     };
    
    
    
    public void addTab(Node contenido,String titulo){
        Tab tab = new Tab();
        tab.setText(titulo);
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
    
    private class ItemSucursalMenu extends MenuItem{
         private Sucursal sucursal;
         
         public ItemSucursalMenu(Sucursal sucursal){
             this.sucursal = sucursal;
             this.setText(sucursal.getNombre());
             
         }
         
         public Sucursal getSucursal(){
             return sucursal;
         }
         
    }
    
    
}
