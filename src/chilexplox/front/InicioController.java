
package chilexplox.front;

import chilexplox.*;
import controllers.AgregarClienteController;
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
    private AnchorPane anchorPedPend, anchorPedCar;
    
    private Menu menuSucursal;
    private AgregarEncomiendaController agregarEncomiendaCon;

    private AgregarClienteController agregarClienteCon;

        
      
   private TreeView<String> treeOrigen = new TreeView<String>(), pedidosPend = new TreeView<String>(),
           pedidosCar = new TreeView<String>(), camionesDisp = new TreeView<String>(),
           camionesDesc = new TreeView<String>();

   private TreeItem aMover;
   
   private EventHandler<MouseEvent> dragDetected = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                 treeOrigen = (TreeView) event.getSource();
                //TreeView<String> tree = (TreeView) event.getSource();
          
                Dragboard dragBoard = treeOrigen.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.put(DataFormat.PLAIN_TEXT, treeOrigen.getSelectionModel().getSelectedItem().toString());
                    dragBoard.setContent(content);
                    event.consume();
            }
        };
   
     private EventHandler<DragEvent> dragOver = new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /*TreeView<String> tree = (TreeView) event.getSource();
                 if (event.getDragboard().hasString()) {
                        String valueToMove = event.getDragboard().getString();
                        if (valueToMove.equals(tree.getSelectionModel().getSelectedItem().toString())) {
                            // We accept the transfer!!!!!
                            event.acceptTransferModes(TransferMode.MOVE);
                        }
                    }*/
                 event.acceptTransferModes(TransferMode.MOVE);
                    event.consume();
            }
        };
     private EventHandler<DragEvent> dragDropped = new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                   
                    TreeView<String> treeDestino = (TreeView) event.getGestureTarget();

                   String valueToMove = event.getDragboard().getString();
                    TreeItem<String> itemToMove = search(treeOrigen.getRoot(), valueToMove);
                    // Remove from former parent.
                    treeOrigen.getRoot().getChildren().remove(itemToMove);
                    // Add to new parent.
                    treeDestino.getRoot().getChildren().add(itemToMove);
                    treeDestino.getRoot().setExpanded(true);
                    event.consume();
            }
        };
     private TreeItem<String> search(final TreeItem<String> currentNode, final String valueToSearch) {
            TreeItem<String> result = null;
            if (currentNode.toString().equals(valueToSearch) ){
                result = currentNode;
            } else if (!currentNode.isLeaf()) {
                for (TreeItem<String> child : currentNode.getChildren()) {
                    result = search(child, valueToSearch);
                    if (result != null) {
                        break;
                    }
                }
            }
            return result;
        }
     
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
                            agregarClienteCon = new AgregarClienteController();
                            agregarClienteCon.setHandlerEncomienda((Event e2) -> {
                                Empresa.agregarCliente(agregarClienteCon.getNombre()+" "+agregarClienteCon.getApellidos(),agregarClienteCon.getCalle()+" "+agregarClienteCon.getNumero()+", "+agregarClienteCon.getComuna()+", "+agregarClienteCon.getCiudad(),agregarClienteCon.getTelefono());
                                comboBoxClientes.getItems().add(agregarClienteCon.getNombre()+" "+agregarClienteCon.getApellidos());
                                comboBoxClientes.setPromptText(agregarClienteCon.getNombre()+" "+agregarClienteCon.getApellidos());
                                split.setDividerPositions(1);
                            });
                            agregarPane.getChildren().setAll(agregarClienteCon);
                            split.setDividerPositions(0.4684014869888476);
                       
                       }
                       catch (Exception exc)
                      {
                          System.out.println("InicioController: No se pudo cargar AgregarClienteController");
                          throw new RuntimeException(exc);
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
                                        pedidosCar = new TreeView<>(listarPedidos(camionSelec.getPedidos()));
                                            amononarTreeView(anchorPedCar, pedidosCar);
//                                        pedidosCar.setShowRoot(false);
//                                        pedidosCar.setOnDragDetected(dragDetected);
//                                        pedidosCar.setOnDragOver(dragOver);
//                                        pedidosCar.setOnDragDropped(dragDropped);
//                                        anchorPedCar.getChildren().add(pedidosCar);
//                                        pedidosCar.setPrefWidth(anchorPedCar.getPrefWidth());
                                        }
                                        
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
                                        
                                        TreeView<String> pedCar = new TreeView<>(listarPedidos(camionSelec.getPedidos()));
                                        amononarTreeView(anchorPedCar, pedCar);
//                                        pedCar.setShowRoot(false);
//                                        pedCar.setOnDragDetected(dragDetected);
//                                        pedCar.setOnDragOver(dragOver);
//                                        pedCar.setOnDragDropped(dragDropped);
//                                        anchorPedCar.getChildren().add(pedCar);
//                                        pedCar.setPrefWidth(anchorPedCar.getPrefWidth());
                                        
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
             //actualizarPestanaAdm();
         }
     };   
    
    public void cargarNombresClientes(){

            ObservableList nombreClientes = FXCollections.observableArrayList();
            for (int i=0; i<  Empresa.getClientes().size();i++)
            {
                nombreClientes.add(Empresa.getClientes().get(i).getNombre());
            }
            
            comboBoxClientes.getItems().setAll(nombreClientes);
            comboBoxClientes.setPromptText("");

    }


     public void actualizarPestanaAdm(){
        Sucursal sucActual = Main.getUsuarioActual().getSucursalActual();
        //ObservableList idsPedPend = FXCollections.observableArrayList();
        ObservableList patentesCamDisp = FXCollections.observableArrayList();
        ObservableList patentesCamADesc = FXCollections.observableArrayList();
        
        TreeView<String> pedidosPend = new TreeView<>(listarPedidos(sucActual.getPedidosPend()));
        amononarTreeView(anchorPedPend, pedidosPend);
        
        
//        pedidosPend.setShowRoot(false);
//        pedidosPend.setOnDragDetected(dragDetected);
//        pedidosPend.setOnDragOver(dragOver);
//        pedidosPend.setOnDragDropped(dragDropped);
//        anchorPedPend.getChildren().add(pedidosPend);
//        pedidosPend.setPrefWidth(anchorPedPend.getPrefWidth());
         
        
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
     
     public TreeItem<String> listarCamiones(LinkedList<Camion> camiones){
         TreeItem<String> dummyRoot = new TreeItem<>("root");
         TreeItem<String> pedidos = new TreeItem<>("Pedidos Cargados");
         for(Camion c : camiones){
             TreeItem<String> camionView = new TreeItem<>("Camión de Patente " + c.getPatente());
             camionView.getChildren().add(new TreeItem("Capacidad total: " + c.getCapacidad() + " cm^3"));
             camionView.getChildren().add(new TreeItem("Espacio Disponible: " + c.getEspDisp() + "cm^3"));
         }
         
         return dummyRoot;
     }
     
     public TreeItem<String> listarPedidos(LinkedList<Pedido> pedidos){
         TreeItem<String> dummyRoot = new TreeItem<>("root");
         TreeItem<String> encomiendas = new TreeItem<>("Encomiendas");
         
         for(Pedido p : pedidos){
             //idsPedPend.add("id: "+p.getIdPedido()+", prioridad: "+p.getPrioridad());
             TreeItem<String> child = new TreeItem<>("Pedido #" + Integer.toString(p.getIdPedido()));
             child.getChildren().add(new TreeItem<>("Prioridad: " + p.getPrioridad()));
             child.getChildren().add(new TreeItem<>("Costo de Envío: $" + p.getCostoEnvio()));
             child.getChildren().add(new TreeItem<>("Sucursal de Destino: " + p.getSucDestino().getNombre()));
             child.getChildren().add(new TreeItem<>("Cliente: " + p.getCliente().getNombre()));
             child.getChildren().add(new TreeItem<>("Peso: " + p.getPeso() + " g"));
             child.getChildren().add(new TreeItem<>("Volumen aprox.: " + p.getVol()+ " cm^3"));
             child.getChildren().add(encomiendas);
             for(Encomienda e : p.getEncomiendas()){
                 TreeItem<String> encomienda = new TreeItem<>(e.getDescripcion());
                 encomienda.getChildren().add(new TreeItem<>("Prioridad: " + e.getPrioridad()));
                 encomienda.getChildren().add(new TreeItem<>("Costo de Envío: $" + e.getCostoEnvio()));
                 encomienda.getChildren().add(new TreeItem<>("Peso: " + e.getPeso() + " g"));
                 encomienda.getChildren().add(new TreeItem<>("Volumen aprox.: " + e.getVol()+ " cm^3"));
                 encomienda.getChildren().add(new TreeItem<>("Dirección de Destino: " + e.getDireccionDestino()));
                 encomiendas.getChildren().add(encomienda);
             }
             dummyRoot.getChildren().add(child);
         }
         
        
        //pedidosTree.setOnDragDone(dragDone);
        
         return dummyRoot;
     }

     public void amononarTreeView (AnchorPane ap, TreeView<String> tv){
        tv.setShowRoot(false);
        tv.setOnDragDetected(dragDetected);
        tv.setOnDragOver(dragOver);
        tv.setOnDragDropped(dragDropped);
        ap.getChildren().add(tv);
        tv.setPrefWidth(ap.getPrefWidth());
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

