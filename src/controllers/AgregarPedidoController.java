/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.*;
import chilexplox.front.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author alberto
 */
public class AgregarPedidoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button crearPedido;
     
      @FXML
    private ComboBox comboBoxSucursales;
    
    private Text sucursalText;
    private Button crearP;
    private Text idPedido;
    private SplitPane splitPane;
//    
    @FXML
    private void handlePedido(ActionEvent event) {

            Scene scene = crearPedido.getScene();
            
            sucursalText = (Text)scene.lookup("#stext");
            crearP = (Button)scene.lookup("#crearP");
            idPedido= (Text)scene.lookup("#idPedido");
            splitPane = (SplitPane)scene.lookup("#splitPane");
            try{  
            Sucursal s = Empresa.getSucursal((String)(comboBoxSucursales.getSelectionModel().getSelectedItem()));
            Pedido p = Main.getUsuarioActual().crearPedido(s);
            sucursalText.setText((String)(comboBoxSucursales.getSelectionModel().getSelectedItem())) ;
            idPedido.setText(""+p.getIdPedido());
            crearP.setVisible(false);
            splitPane.setDividerPositions(1);
            }
            catch (Exception exc)
            {
                int i = 0;
            }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           cargarNombresSucursales();
        
    }    
    public void cargarNombresSucursales(){

            ObservableList nombreSucursales = FXCollections.observableArrayList();
            for (int i=0; i<  Empresa.getSucursales().size();i++)
            {
                if(!Empresa.getSucursales().get(i).getNombre().equals(Main.getUsuarioActual().getSucursalActual().getNombre()))
                nombreSucursales.add(Empresa.getSucursales().get(i).getNombre());
            }
            
            comboBoxSucursales.getItems().setAll(nombreSucursales);
            comboBoxSucursales.setPromptText("seleccione una sucursal");
    }
    
}
