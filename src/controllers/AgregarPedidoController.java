/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.*;
import chilexplox.front.Main;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author alberto
 */
public class AgregarPedidoController extends VBox {

    /**
     * Initializes the controller class.
     */
        @FXML
    private Button crearPedido;
     
<<<<<<< HEAD
        @FXML
    private ComboBox comboBoxSucursales, comboBoxTipos;
        @FXML
    private Text textoAlertaSucursal, textoAlertaTipo;
=======
      @FXML
    private ComboBox comboBoxSucursales;
       @FXML
    private Text textoAlertaSucursal;
       @FXML
    private Button botonCrearPedido;
>>>>>>> origin/master
    
    private Sucursal sucursal;
    
<<<<<<< HEAD
//    
    @FXML
    private void handlePedido(ActionEvent event) {
            Scene scene = crearPedido.getScene();
            sucursalText = (Text)scene.lookup("#stext");
            crearP = (Button)scene.lookup("#crearP");
            idPedido= (Text)scene.lookup("#idPedido");
            splitPane = (SplitPane)scene.lookup("#splitPane");
            mod =(Button)scene.lookup("#modificar");
            if(comboBoxSucursales.getSelectionModel().getSelectedItem() == null){
                textoAlertaSucursal.setText("Por favor seleccione una sucursal");
                textoAlertaSucursal.setVisible(true);
                textoAlertaTipo.setVisible(false);

            }
            else if(comboBoxTipos.getSelectionModel().getSelectedItem() == null){
                textoAlertaTipo.setText("Por favor seleccione una sucursal");
                textoAlertaSucursal.setVisible(false);
                textoAlertaTipo.setVisible(true);

            }
            else{
                Sucursal s = Empresa.getSucursal((String)(comboBoxSucursales.getSelectionModel().getSelectedItem()));
                Tipo t = (Tipo)comboBoxTipos.getSelectionModel().getSelectedItem();
                ///////////////////IMPLEMENTAR SELECCIONAR TIPO EN FRONTEND!!!!\\\\\\\\\\\\\\\\\\\\\\\\\\
                Pedido p = ((Funcionario)Main.getUsuarioActual()).crearPed(s, t);
                sucursalText.setText((String)(comboBoxSucursales.getSelectionModel().getSelectedItem())) ;
                idPedido.setText(""+p.getIdPedido());
                crearP.setVisible(false);
                splitPane.setDividerPositions(1);
                mod.setVisible(true);
            }
=======
    
    private EventHandler handlerPedido;
    
    public void sethandlerPedido(EventHandler eh){
        handlerPedido = eh;
    }
    
    public void crearPedido(ActionEvent event) {
            
     
            if(!(comboBoxSucursales.getSelectionModel().getSelectedItem()==null)){
                sucursal = Empresa.getSucursal((String)comboBoxSucursales.getSelectionModel().getSelectedItem());
                handlerPedido.handle(event);
            } 
            else { textoAlertaSucursal.setText("Por favor seleccione una sucursal"); }
>>>>>>> origin/master

    }
    
    public Sucursal getSucursal(){
        return sucursal;
    }
    
    
<<<<<<< HEAD
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarNombresSucursales();
        cargarTipos();
        textoAlertaSucursal.setVisible(false);
        textoAlertaTipo.setVisible(false);

    }    
=======
    public AgregarPedidoController(){
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/AgregarPedido.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        cargarNombresSucursales();
    }
  
>>>>>>> origin/master
    public void cargarNombresSucursales(){

            ObservableList nombreSucursales = FXCollections.observableArrayList();
            for (int i=0; i<  Empresa.getSucursales().size();i++)
            {
                if(!Empresa.getSucursales().get(i).getNombre().equals(Main.getUsuarioActual().getSucActual().getNombre()))
                nombreSucursales.add(Empresa.getSucursales().get(i).getNombre());
            }
            
            comboBoxSucursales.getItems().setAll(nombreSucursales);
            comboBoxSucursales.setPromptText("seleccione una sucursal");
    }
    
    public void cargarTipos(){
            ObservableList tipos = FXCollections.observableArrayList();
            tipos.add(Tipo.Normal);
            tipos.add(Tipo.Animales);
            tipos.add(Tipo.Blindado);
            tipos.add(Tipo.Fragil);
            tipos.add(Tipo.Radioactivo);
            tipos.add(Tipo.Refrigerado);
            comboBoxTipos.getItems().setAll(tipos);
            comboBoxTipos.setPromptText("seleccione el tipo de pedido");
    }
    
}
