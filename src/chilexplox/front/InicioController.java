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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

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
    private AnchorPane agregar;
      @FXML
    private Button enviarMensaje;
      @FXML
    private Button buzonEntrada;
      @FXML
    private Button mensajesEnviados;
      @FXML
    private HBox pantallaEnviados;
      @FXML
    private HBox pantallaEntrada;
      @FXML
    private AnchorPane pantallaNuevo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
        Empresa.deserializar("data/empresa.ser");
        List<Sucursal> sucursales = Empresa.getInstance().getSucursales();
        List<Usuario> usuarios = Empresa.getInstance().getUsuarios();
        List<Cliente> clientes = Empresa.getInstance().getClientes();
        
        // TODO
        Menu menuUsuario = new Menu("Carlos Salamé");
        menuUsuario.getItems().add(new MenuItem("Cerrar sesión"));
        menuBar.getMenus().add(menuUsuario);
        Menu menuSucursal = new Menu(sucursales.get(0).getNombre());
        
        try{
        
        agregar.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass().getResource("/resources/AgregarCliente.fxml")));
        }
        catch(Exception e) {
        }
        
        
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
          
        
    }    
    
}
