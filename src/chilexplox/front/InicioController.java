/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chilexplox.front;

import chilexplox.Cliente;
import chilexplox.Empresa;
import chilexplox.Sucursal;
import chilexplox.Usuario;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author alberto
 */
public class InicioController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private MenuBar menuBar;
      @FXML
    private AnchorPane agregarPane;
       @FXML
    private Button agregarCliente;
       @FXML
    private Button agregarEncomienda;
       @FXML
    private TabPane tabs;

         @FXML
    private SplitPane split;

       @FXML
    private AnchorPane pantallaNuevoMensaje;
       @FXML
    private HBox pantallaBuzonEntrada;
       @FXML
    private HBox pantallaMensajesEnviados;
       @FXML
    private Button botonNuevoMensaje;
       @FXML
    private Button botonBuzonEntrada;
       @FXML
    private Button botonMensajesEnviados;
       @FXML
    private AnchorPane anchorPaneMensajes;
       
       
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
        Empresa.deserializar("data/empresa.ser");
        List<Sucursal> sucursales = Empresa.getInstance().getSucursales();
        
        List<Usuario> usuarios = Empresa.getInstance().getUsuarios();
       
        List<Cliente> clientes = Empresa.getInstance().getClientes();
        
        //Inicializar Menú
        Menu menuUsuario = new Menu("Carlos Salamé");
        menuUsuario.getItems().add(new MenuItem("Cerrar sesión"));
        menuBar.getMenus().add(menuUsuario);
        Menu menuSucursal = new Menu(sucursales.get(0).getNombre());
        
        for(int j=0;j<sucursales.size();j++)
        {
                if(j!=0)
                menuSucursal.getItems().add(new MenuItem(sucursales.get(j).getNombre()));
          
        }
        
   
        menuBar.getMenus().add(menuSucursal);
        
        for(int i=0;i<menuSucursal.getItems().size();i++)
        {
            menuSucursal.getItems().get(i).setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
               MenuItem item = (MenuItem)e.getSource();
               String presionado = item.textProperty().get();
               String actual = menuSucursal.getText();
               item.setText(actual);
               menuSucursal.setText(presionado);
                 
            }
        });      
            
        }
        
        split.setDividerPositions(1);

        //Tabs
        /*for(int i=0;i<tabs.getTabs().size();i++)
        {
        tabs.getTabs().get(i).setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle (Event e) {
                    Tab t = (Tab)(e.getSource());
                    if(((Tab)(e.getSource())).isSelected()){
                    }
                }
        
            }); 
          }*/

        //Atender
        agregarCliente.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                    
                       try{        

                       agregarPane.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass().getResource("/resources/AgregarCliente.fxml")));
                       split.setDividerPositions(0.4684014869888476);
                       
                       }
                       catch (Exception exc)
                      {
                               }
                     
                
                }
            });
        
           agregarEncomienda.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                    
                       try{        

                       agregarPane.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass().getResource("/resources/AgregarEncomienda.fxml")));
                       split.setDividerPositions(0.4684014869888476);
                       
                       }
                       catch (Exception exc)
                      {
                               }
                     
                
                }
            });
        
         //Tabs
       /* for(int i=0;i<tabs.getTabs().size();i++)
        {
        tabs.getTabs().get(i).setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle (Event e) {
                    Tab t = (Tab)(e.getSource());
                    if(((Tab)(e.getSource())).isSelected()){
                   
                    }
                }
        
            }); 
          }*/

        
   
        
        //Mensajes
        
        botonNuevoMensaje.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                    
                       try{
                        anchorPaneMensajes.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass().getResource("/resources/Nuevo Mensaje.fxml")));
                        
                       }
                       catch (Exception exc)
                      {
                               }
                     
                
                }
            });
        
       
        
          
        
    }
}
