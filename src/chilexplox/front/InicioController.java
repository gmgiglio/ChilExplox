
package chilexplox.front;

import chilexplox.*;
import controllers.AgregarEncomiendaController;
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
    private Button agregarCliente,botonAgregarEncomienda,crearPedido,cerrarPedido,modificar,botonNuevoMensaje,
               botonBuzonEntrada,botonMensajesEnviados;
       @FXML
    private TabPane tabs;
       @FXML
    private SplitPane split;
       @FXML
    private AnchorPane pantallaNuevoMensaje,anchorPaneMensajes,anchorEjemplo,agregarPane;
       @FXML
    private HBox pantallaBuzonEntrada,pantallaMensajesEnviados;
       @FXML
    private ListView pedidosPendientes, pedidosCargados, camionesDisponibles, camionesPorDescargar;
       @FXML
    private ComboBox comboBoxClientes, comboBoxSucursales, comboBoxEncomiendas;
       @FXML
    private Text patenteCamAct, capacidadCamAct, espDispCamAct, estadoCamAct,advertencia,presupuesto;
       @FXML
    private TreeView<String> treeEjemplo;
      
    
    private Menu menuSucursal;
    private AgregarEncomiendaController agregarEncomiendaCon;
        @FXML
    private AnchorPane anchorPedPend, anchorPedCar;
      
   private TreeView<String> pedidosPend = new TreeView<String>(), pedidosCar = new TreeView<String>();
   private TreeItem aMover;

   private EventHandler<MouseEvent> dragDetected = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //ListView<String> list = (ListView) event.getSource();
                TreeView<String> tree = (TreeView) event.getSource();
                //Dragboard db = list.startDragAndDrop(TransferMode.MOVE);
                Dragboard db = tree.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                //content.putString(list.getSelectionModel().getSelectedItem());
                content.putString(tree.getSelectionModel().getSelectedItem().getValue());
                aMover = tree.getSelectionModel().getSelectedItem();
                db.setContent(content);

                event.consume();
            }
        };
    private EventHandler<DragEvent> dragOver = new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != event.getTarget() && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        };
    private EventHandler<DragEvent> dragDropped = new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //ListView<String> list = (ListView) event.getGestureTarget();
                TreeView<String> tree = (TreeView) event.getGestureTarget();

                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    //list.getItems().add(db.getString());
                    tree.getRoot().getChildren().add(aMover);
                    success = true;
                }

                event.setDropCompleted(success);
                event.consume();
            }
        };
    private EventHandler<DragEvent> dragDone = new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getTransferMode() == TransferMode.MOVE) {
                    //ListView<String> list = (ListView) event.getGestureSource();
                    TreeView<String> tree = (TreeView) event.getGestureSource();
                    //list.getItems().remove(event.getDragboard().getString());
                    tree.getRoot().getChildren().remove(aMover);
                    String idString =(String)aMover.getValue();
                    int id = Integer.parseInt(idString.substring(8));
                    Main.getUsuarioActual().getSucursalActual().cargarPedido(id);
                    Sucursal origen = Main.getUsuarioActual().getSucursalActual();
                    Sucursal destino = Empresa.getSucursal(aMover.getChildren().get(2).toString().substring(8));
                    Cliente cliente = Empresa.getCliente(aMover.getChildren().get(3).toString().substring(9));
                    Pedido p = new Pedido(origen, destino, cliente);
                    Main.getUsuarioActual().getSucursalActual().getCamion(patenteCamAct.getText()).cargarPedido(p);
                }
                event.consume();
            }
        };
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
   
        LinkedList<Sucursal> sucursales = Empresa.getSucursales();
        
        ////////////////////////Inicializar Menú\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
       
        Menu menuUsuario = new Menu(Main.getUsuarioActual().getNombreUsuario());
        MenuItem itemCerrarSesion = new MenuItem("Cerrar sesión");
        itemCerrarSesion.setOnAction((ActionEvent e) -> {
            Main.cerrarSesion();
        });
        menuUsuario.getItems().add(itemCerrarSesion);
        Main.getUsuarioActual().setSucursalActual(Empresa.getSucursales().get(0));
        ItemSucursalMenu i = new ItemSucursalMenu(Main.getUsuarioActual().getSucursalActual());
        menuSucursal = new Menu(Main.getUsuarioActual().getSucursalActual().getNombre());
        //Main.getUsuarioActual().getSucursalActual().revisarTiempoPedidos();
        
        //agregar sucursales al menu de sucursales
        LinkedList<Sucursal> sucEnLista = new LinkedList(sucursales);
        sucEnLista.remove(Main.getUsuarioActual().getSucursalActual());
        for(Sucursal s : sucEnLista){
                ItemSucursalMenu item = new ItemSucursalMenu(s);
                menuSucursal.getItems().add(item); 
                item.setOnAction(eventoSucusal);  
                
        }
        menuBar.getMenus().addAll(menuUsuario,menuSucursal);
        
      cargarNombresClientes();
        
        split.setDividerPositions(1);

        
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
           
            botonAgregarEncomienda.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                    
                       try{        
                            agregarEncomiendaCon = new AgregarEncomiendaController();
                            agregarEncomiendaCon.setHandlerEncomienda((Event e2) -> {
                                agregarEncomienda(agregarEncomiendaCon.getPeso(),agregarEncomiendaCon.getVolumen(),
                                agregarEncomiendaCon.getPrioridad(),agregarEncomiendaCon.getDirDestino(),agregarEncomiendaCon.getDescr());
                            });
                            agregarPane.getChildren().setAll(agregarEncomiendaCon);
                            split.setDividerPositions(0.4684014869888476);
                       
                       }
                       catch (Exception exc)
                      {
                          System.out.println("InicioController: No se pudo cargar AgregarEncomiendaController");
                          throw new RuntimeException(exc);
                               }
                     
                
                }
            });
            
            
           cerrarPedido.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                    String nombreCliente = (String)comboBoxClientes.getItems().get(0);
                       try{
                            if(nombreCliente != null){
                                Scene scene = split.getScene();
                                Text idPedido = (Text)scene.lookup("#idPedido");
                                Main.getUsuarioActual().getSucursalActual().getPedidoAbierto().setCliente(nombreCliente);
                                Main.getUsuarioActual().cerrarPed();
                                limpiarAtender();
                           }
                            else advertencia.setText("Debe seleccionar un cliente");
                       }
                       catch (Exception exc)
                      {
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
                                        
                                        if(camionSelec.getPedidos() != null){
                                        pedidosCar = listarPedidos(camionSelec.getPedidos());
                                        anchorPedCar.getChildren().add(pedidosCar);
                                        pedidosCar.setPrefWidth(anchorPedCar.getPrefWidth());}
                                        
//                                        for(Pedido p : camionSelec.getPedidos()){
//                                            idsPedCamion.add("id: "+p.getIdPedido()+", prioridad: "+p.getPrioridad());
//                                        }
//                                        pedidosCargados.setItems(idsPedCamion);
                                        patenteCamAct.setText(camionSelec.getPatente());
                                        capacidadCamAct.setText(Integer.toString(camionSelec.getCapacidad()));
                                        espDispCamAct.setText(Integer.toString(camionSelec.getEspDisp()));
                                        estadoCamAct.setText("DISPONIBLE");
                                        
                                    }
                                });
                                
                                camionesPorDescargar.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        Camion camionSelec = null;
                                        String patenteCamionSelec = (String)camionesPorDescargar.getSelectionModel().getSelectedItem();
                                        ObservableList idsPedCamion = FXCollections.observableArrayList();
                                        Sucursal sucActual = Main.getUsuarioActual().getSucursalActual();
                                        for(Camion c : sucActual.getCamionesPend()){
                                            if(c.getPatente()==patenteCamionSelec) camionSelec = c;
                                        }
                                        for(Pedido p : camionSelec.getPedidos()){
                                            idsPedCamion.add("id: "+p.getIdPedido()+", prioridad: "+p.getPrioridad());
                                        }
                                        pedidosCargados.setItems(idsPedCamion);
                                        
                                        TreeView<String> pedCar = listarPedidos(camionSelec.getPedidos());
                                        anchorPedCar.getChildren().add(pedCar);
                                        pedCar.setPrefWidth(anchorPedCar.getPrefWidth());
                                        
                                        patenteCamAct.setText(camionSelec.getPatente());
                                        capacidadCamAct.setText(Integer.toString(camionSelec.getCapacidad()));
                                        espDispCamAct.setText(Integer.toString(camionSelec.getEspDisp()));
                                        estadoCamAct.setText("POR DESCARGAR");
                                        
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
        
        
        

//        pedidosPendientes.setOnDragDetected(dragDetected);
//        pedidosPendientes.setOnDragOver(dragOver);
//        pedidosPendientes.setOnDragDropped(dragDropped);
//        pedidosPendientes.setOnDragDone(dragDone);
//
//        pedidosCargados.setOnDragDetected(dragDetected);
//        pedidosCargados.setOnDragOver(dragOver);
//        pedidosCargados.setOnDragDropped(dragDropped);
//        pedidosCargados.setOnDragDone(dragDone);
        
        

        
   
        
        /////////////////////////////Mensajes\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        
        botonNuevoMensaje.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                    
                       try{
                        anchorPaneMensajes.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass().getResource("/resources/NuevoMensaje.fxml")));
                        
                       }
                       catch (Exception exc){
                           System.out.println("InicioController: No se cargó NuevoMensaje.fxml ");
                      }
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
    
    private EventHandler eventoSucusal = new EventHandler() {

         public void handle(Event e) {
             ItemSucursalMenu item1 = (ItemSucursalMenu)e.getSource();
             Sucursal suc = item1.getSucursal();
             menuSucursal.setText(suc.getNombre());
             ItemSucursalMenu item2 = new ItemSucursalMenu(Main.getUsuarioActual().getSucursalActual());
             item2.setOnAction(eventoSucusal);
             Main.getUsuarioActual().setSucursalActual(suc);
             menuSucursal.getItems().remove(item1);
             menuSucursal.getItems().add(item2);
             //suc.revisarTiempoPedidos();
             actualizarPestanaAdm();
         }
     };   
    
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
         //ObservableList idsPedPend = FXCollections.observableArrayList();
         ObservableList patentesCamDisp = FXCollections.observableArrayList();
         ObservableList patentesCamADesc = FXCollections.observableArrayList();
         
         pedidosPend = listarPedidos(sucActual.getPedidosPend());
         
         anchorPedPend.getChildren().add(pedidosPend);
         pedidosPend.setPrefWidth(anchorPedPend.getPrefWidth());
         
        
        //pedidosPendientes.setItems(idsPedPend);
         
         for(Camion c : sucActual.getCamionesDisp()){
             patentesCamDisp.add(c.getPatente());
         }
         camionesDisponibles.setItems(patentesCamDisp);
         
         for(Camion c : sucActual.getCamionesPend()){
             patentesCamADesc.add(c.getPatente());
         }
         camionesPorDescargar.setItems(patentesCamADesc);
     }
     
     public TreeView<String> listarPedidos(LinkedList<Pedido> pedidos){
         TreeItem<String> dummyRoot = new TreeItem<>("root");
         TreeItem<String> encomiendas = new TreeItem<>("Encomiendas");
         
         for(Pedido p : pedidos){
             //idsPedPend.add("id: "+p.getIdPedido()+", prioridad: "+p.getPrioridad());
             TreeItem<String> child = new TreeItem<>("Pedido #" + Integer.toString(p.getIdPedido()));
//             child.getChildren().add(new TreeItem<>("Prioridad: " + p.getPrioridad()));
//             child.getChildren().add(new TreeItem<>("Costo de Envío: $" + p.getCostoEnvio()));
//             child.getChildren().add(new TreeItem<>("Sucursal de Destino: " + p.getSucDestino().getNombre()));
//             child.getChildren().add(new TreeItem<>("Cliente: " + p.getCliente().getNombre()));
//             child.getChildren().add(new TreeItem<>("Peso: " + p.getPeso() + " g"));
//             child.getChildren().add(new TreeItem<>("Volumen aprox.: " + p.getVol()+ " cm^3"));
//             child.getChildren().add(encomiendas);
//             for(Encomienda e : p.getEncomiendas()){
//                 TreeItem<String> encomienda = new TreeItem<>(e.getDescripcion());
//                 encomienda.getChildren().add(new TreeItem<>("Prioridad: " + e.getPrioridad()));
//                 encomienda.getChildren().add(new TreeItem<>("Costo de Envío: $" + e.getCostoEnvio()));
//                 encomienda.getChildren().add(new TreeItem<>("Peso: " + e.getPeso() + " g"));
//                 encomienda.getChildren().add(new TreeItem<>("Volumen aprox.: " + e.getVol()+ " cm^3"));
//                 encomienda.getChildren().add(new TreeItem<>("Dirección de Destino: " + e.getDireccionDestino()));
//                 encomiendas.getChildren().add(encomienda);
//             }
             dummyRoot.getChildren().add(child);
         }
         
         TreeView<String> pedidosTree = new TreeView<>(dummyRoot);
         
        pedidosTree.setShowRoot(false);
        
        pedidosTree.setOnDragDetected(dragDetected);
        pedidosTree.setOnDragOver(dragOver);
        pedidosTree.setOnDragDropped(dragDropped);
        pedidosTree.setOnDragDone(dragDone);
        
         return pedidosTree;
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
     
     private void agregarEncomienda(int peso, int volumen,int prioridad, String dirDestino,String descr){
         Main.getUsuarioActual().getSucursalActual().getPedidoAbierto().agregarEnc(peso, volumen,prioridad, dirDestino,descr);
         comboBoxEncomiendas.getItems().add(descr);
         comboBoxEncomiendas.setPromptText(descr);
         presupuesto.setText(""+Main.getUsuarioActual().getSucursalActual().getPedidoAbierto().getCostoEnvio());
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

