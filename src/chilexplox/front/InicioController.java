/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chilexplox.front;

import chilexplox.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
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
    private Button crearPedido;
      @FXML
    private Button cerrarPedido;
         @FXML
    private Button modificar;

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
      
    
   private Menu menuSucursal;
   
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
   
        List<Sucursal> sucursales = Empresa.getSucursales();
        
        ////////////////////////Inicializar Menú\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        
        //Inicializar Menú
        Menu menuUsuario = new Menu(Main.getUsuarioActual().getNombreUsuario());
        
        MenuItem itemCerrarSesion = new MenuItem("Cerrar sesión");
        itemCerrarSesion.setOnAction((ActionEvent e) -> {
            Main.cerrarSesion();
        });
        menuUsuario.getItems().add(itemCerrarSesion);
        menuBar.getMenus().add(menuUsuario);
        Main.getUsuarioActual().setSucursalActual(Empresa.getSucursales().get(0));
        ItemSucursalMenu i = new ItemSucursalMenu(Main.getUsuarioActual().getSucursalActual());
        menuSucursal = new Menu(Main.getUsuarioActual().getSucursalActual().getNombre());
        
        //agregar sucursales al menu de sucursales
        LinkedList<Sucursal> sucEnLista = new LinkedList(sucursales);
        sucEnLista.remove(Main.getUsuarioActual().getSucursalActual());
        for(Sucursal s : sucEnLista){
                ItemSucursalMenu item = new ItemSucursalMenu(s);
                menuSucursal.getItems().add(item); 
                
                item.setOnAction((ActionEvent e) -> {
                    ItemSucursalMenu item1 = (ItemSucursalMenu)e.getSource();
                    Sucursal suc = item1.getSucursal();
                    menuSucursal.setText(suc.getNombre());
                    ItemSucursalMenu item2 = new ItemSucursalMenu(Main.getUsuarioActual().getSucursalActual());
                    Main.getUsuarioActual().setSucursalActual(suc);
                    menuSucursal.getItems().remove(item1);
                    menuSucursal.getItems().add(item2);
                    actualizarPestanaAdm();
                });    
                
        }
        menuBar.getMenus().add(menuSucursal);
        
      cargarNombresClientes();
        
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
        
           crearPedido.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                    
                       try{        

                       agregarPane.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass().getResource("/resources/AgregarPedido.fxml")));
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
            
           cerrarPedido.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                    
                       try{     
                            Scene scene = split.getScene();
                            Text idPedido = (Text)scene.lookup("#idPedido");
                            Main.getUsuarioActual().confirmarPedido(Integer.parseInt(idPedido.getText()), true);
                            limpiarAtender();
                       }
                       catch (Exception exc)
                      {
                           int i=0;
                               }
                     
                
                }
            });
        
         //Tabs
        for(int j=0;j<tabs.getTabs().size();j++)
        {
        tabs.getTabs().get(j).setOnSelectionChanged(new EventHandler<Event>() {
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
        
        //Fuente: https://bugs.openjdk.java.net/browse/JDK-8094227
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
                        anchorPaneMensajes.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass().getResource("/resources/NuevoMensaje.fxml")));
                        
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
                 anchorPaneMensajes.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass().getResource("/resources/MensajesEnviados.fxml")));
                }
                catch (Exception exc){} 
            }
        });
        
    }
    public void cargarNombresClientes(){

            ObservableList nombreClientes = FXCollections.observableArrayList();
            for (int i=0; i<  Empresa.getClientes().size();i++)
            {
                nombreClientes.add(Empresa.getClientes().get(i).getNombre());
            }
            
            comboBoxClientes.getItems().setAll(nombreClientes);
            comboBoxClientes.setPromptText(comboBoxClientes.getItems().get(0).toString());

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

     public void limpiarAtender(){
     
         crearPedido.setVisible(true);
         Scene scene = crearPedido.getScene();
         Text idPedido = (Text) scene.lookup("#idPedido");
         idPedido.setText("Haga clic en crear pedido");
         modificar.setVisible(false);
         Text sucursalText = (Text) scene.lookup("#stext");
         sucursalText.setText("");
         comboBoxEncomiendas.getItems().clear();
         comboBoxEncomiendas.setPromptText("");
         Text presupuesto = (Text) scene.lookup("#presupuesto");
         presupuesto.setText("0");
         split.setDividerPositions(1);
        
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

