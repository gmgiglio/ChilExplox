/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chilexplox.front;

import chilexplox.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.Text;

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
       @FXML
    private ListView pedidosPendientes, pedidosCargados, camionesDisponibles, camionesPorDescargar;

       @FXML
    private ComboBox comboBoxClientes, comboBoxSucursales, comboBoxEncomiendas;
       @FXML
    private Text patenteCamAct, capacidadCamAct, espDispCamAct;
      
    Usuario actual;
       
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   

        Empresa.getInstance().agregarSucursal("Maipu", "Santiago", "Amapolas 1122");
        Empresa.getInstance().agregarSucursal("Las Condes", "Santiago", "Apoquindo 5000");
        Empresa.getInstance().agregarSucursal("Victoria", "Temuco", "Bernardo Ohiggins 4256");
        Empresa.getInstance().agregarCliente("Leo", "Las Raíces 1172", "7778899");
        Cliente leo = Empresa.getInstance().getClientes().get(0);
        Sucursal maipu = Empresa.getInstance().getSucursal("Maipu");
        Sucursal victoria = Empresa.getInstance().getSucursal("Victoria");
        Empresa.getInstance().agregarUsuario("Tulio Triviño", "31minutos");
        maipu.agregarCamion("BDGH34", 3000);
        maipu.agregarCamionPend("JUHK87", 2500);
        maipu.getCamionesDisp().get(0).cargarPedido(new Pedido(maipu, victoria, leo));
        Usuario tulio = Empresa.getInstance().getUsuarios().get(0);
        tulio.setSucursalActual(maipu);
        tulio.crearPedido(victoria);
        
        Empresa.serializar("data/empresa.ser");
        
        Empresa.deserializar("data/empresa.ser");
        List<Sucursal> sucursales = Empresa.getInstance().getSucursales();
        
        List<Usuario> usuarios = Empresa.getInstance().getUsuarios();
       
        List<Cliente> clientes = Empresa.getInstance().getClientes();
        
        ////////////////////////Inicializar Menú\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        Empresa.getInstance().agregarUsuario("Karl Saleam","112233445");
        actual= Empresa.getInstance().getUsuarios().get(0); ///POR MIENTRAS
        actual.setSucursalActual(sucursales.get(0));
        //Inicializar Menú
        Menu menuUsuario = new Menu(actual.getNombreUsuario());
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
               Main.getUsuarioActual().setSucursalActual(Empresa.getInstance().getSucursal(presionado));
               actualizarPestanaAdm();
            }
        });      

        }
        
     // cargarNombresClientes();
     // cargarNombresClientes();
        cargarNombresSucursales();
        
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
        ///////////////////////////////Atender\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        agregarCliente.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                    
                       try{        

                       agregarPane.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass().getResource("/resources/AgregarCliente.fxml")));
                       split.setDividerPositions(0.4684014869888476);
                       
                       }
                       catch (Exception exc)
                      {
                          int i=0;
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
        for(int i=0;i<tabs.getTabs().size();i++)
        {
        tabs.getTabs().get(i).setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle (Event e) {
                    Tab t = (Tab)(e.getSource());
                    if(((Tab)(e.getSource())).isSelected()){
                        switch(t.getText()){
                            
                            case "Atender":
                                break;
                            
                            case "Administrar":
                                actualizarPestanaAdm();
                                
                                camionesDisponibles.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        Camion camionSelec = null;
                                        String patenteCamionSelec = (String)camionesDisponibles.getSelectionModel().getSelectedItem();
                                        ObservableList idsPedCamion = FXCollections.observableArrayList();
                                        Sucursal sucActual = Main.getUsuarioActual().getSucursalActual();
                                        for(Camion c : sucActual.getCamionesDisp()){
                                            if(c.getPatente()==patenteCamionSelec) camionSelec = c;
                                        }
                                        for(Pedido p : camionSelec.getPedidos()){
                                            idsPedCamion.add("id: "+p.getIdPedido()+", prioridad: "+p.getPrioridad());
                                        }
                                        pedidosCargados.setItems(idsPedCamion);
                                        patenteCamAct.setText(camionSelec.getPatente());
                                        capacidadCamAct.setText(Integer.toString(camionSelec.getCapacidad()));
                                        espDispCamAct.setText(Integer.toString(camionSelec.getEspDisp()));
                                        
                                        
                                    }
                                });
                                
                                
                            break;
                                
                            case "Mensajes":
                                    break;
                            default:;
                                break;
                        }             
                    }
                }                 
            }); 
        }
           
           
           
        ///////////////////////////ADMINISTRAR\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        // set up Dnd in both directions
        
        EventHandler<MouseEvent> dragDetected = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                ListView<String> list = (ListView) event.getSource();
                Dragboard db = list.startDragAndDrop(TransferMode.MOVE);

                ClipboardContent content = new ClipboardContent();
                content.putString(list.getSelectionModel().getSelectedItem());
                db.setContent(content);

                event.consume();
            }
        };
        EventHandler<DragEvent> dragOver = new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != event.getTarget() && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        };
        EventHandler<DragEvent> dragDropped = new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                ListView<String> list = (ListView) event.getGestureTarget();

                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    list.getItems().add(db.getString());
                    success = true;
                }

                event.setDropCompleted(success);
                event.consume();
            }
        };
        EventHandler<DragEvent> dragDone = new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getTransferMode() == TransferMode.MOVE) {
                    ListView<String> list = (ListView) event.getGestureSource();
                    list.getItems().remove(event.getDragboard().getString());
                }
                event.consume();
            }
        };

        pedidosPendientes.setOnDragDetected(dragDetected);
        pedidosPendientes.setOnDragOver(dragOver);
        pedidosPendientes.setOnDragDropped(dragDropped);
        pedidosPendientes.setOnDragDone(dragDone);

        pedidosCargados.setOnDragDetected(dragDetected);
        pedidosCargados.setOnDragOver(dragOver);
        pedidosCargados.setOnDragDropped(dragDropped);
        pedidosCargados.setOnDragDone(dragDone);
        
        

        
   
        
        /////////////////////////////Mensajes\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        
        botonNuevoMensaje.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                    
                       try{
                        anchorPaneMensajes.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass().getResource("/resources/Nuevo Mensaje.fxml")));
                        
                       }
                       catch (Exception exc)
                      {}
            }
        });
        
        botonBuzonEntrada.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {                    
                try{
                 anchorPaneMensajes.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass().getResource("/resources/BuzonEntrada.fxml")));
                }
                catch (Exception exc){} 
            }
        });
        
        botonMensajesEnviados.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {                    
                try{
                 anchorPaneMensajes.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass().getResource("/resources/Mensajes Enviados.fxml")));
                }
                catch (Exception exc){} 
            }
        });
        
    }
    public void cargarNombresClientes(){

            ObservableList nombreClientes = FXCollections.observableArrayList();
            for (int i=0; i<  Empresa.getInstance().getClientes().size();i++)
            {
                nombreClientes.add(Empresa.getInstance().getClientes().get(i).getNombre());
            }
            
            comboBoxClientes.getItems().setAll(nombreClientes);
            comboBoxClientes.setPromptText(comboBoxClientes.getItems().get(0).toString());

    }
     public void cargarNombresSucursales(){

            ObservableList nombreSucursales = FXCollections.observableArrayList();
            for (int i=0; i<  Empresa.getInstance().getSucursales().size();i++)
            {
                if(!Empresa.getInstance().getSucursales().get(i).getNombre().equals(actual.getSucursalActual().getNombre()))
                nombreSucursales.add(Empresa.getInstance().getSucursales().get(i).getNombre());
            }
            
            comboBoxSucursales.getItems().setAll(nombreSucursales);
            comboBoxSucursales.setPromptText(comboBoxSucursales.getItems().get(0).toString());
    }
     
     public void actualizarPestanaAdm(){
         Sucursal sucActual = Main.getUsuarioActual().getSucursalActual();
         ObservableList idsPedPend = FXCollections.observableArrayList();
         ObservableList patentesCamDisp = FXCollections.observableArrayList();
         ObservableList patentesCamADesc = FXCollections.observableArrayList();
         
         for(Pedido p : sucActual.getPedidosPend()){
             idsPedPend.add("id: "+p.getIdPedido()+", prioridad: "+p.getPrioridad());
         }
         pedidosPendientes.setItems(idsPedPend);
         
         for(Camion c : sucActual.getCamionesDisp()){
             patentesCamDisp.add(c.getPatente());
         }
         camionesDisponibles.setItems(patentesCamDisp);
         
         for(Camion c : sucActual.getCamionesPend()){
             patentesCamADesc.add(c.getPatente());
         }
         camionesPorDescargar.setItems(patentesCamADesc);
     }
}

