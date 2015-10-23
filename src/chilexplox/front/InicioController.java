/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chilexplox.front;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Menu menuUsuario = new Menu("Carlos Salamé");
        menuUsuario.getItems().add(new MenuItem("Cerrar sesión"));
        menuBar.getMenus().add(menuUsuario);
        Menu menuSucursal = new Menu("Alcántara");
        menuSucursal.getItems().add(new MenuItem("Vitacura"));
        menuBar.getMenus().add(menuSucursal);
    }    
    
}
