
package chilexplox.front;

import chilexplox.*;
import controllers.AgregarClienteController;
import controllers.AgregarEncomiendaController;
import controllers.CajaEncomienda;
import java.net.URL;
import java.util.LinkedList;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Button agregarCliente,botonAgregarEncomienda,crearPedido,botonCancelar,cerrarPedido,modificar,
               botonNuevoMensaje, botonBuzonEntrada,botonMensajesEnviados, accionCamion;
        @FXML
    private TabPane tabs;
        @FXML
    private SplitPane split;
        @FXML
    private AnchorPane pantallaNuevoMensaje,anchorPaneMensajes,anchorEjemplo,agregarPane;
        @FXML
    private HBox pantallaBuzonEntrada,pantallaMensajesEnviados;
        @FXML
    private ListView pedidosPendientes, pedidosCargados, camionesDisponibles, camionesPorDescargar, listEncomiendas;
        @FXML
    private ComboBox comboBoxClientes, comboBoxSucursales;
        @FXML
    private VBox vBoxConfPed;
        @FXML
    private Text patenteCamAct, capacidadCamAct, espDispCamAct, estadoCamAct,advertencia,presupuesto;
        @FXML
    private AnchorPane anchorPedPend, anchorPedCar, anchorCamDisp, anchorCamDesc, anchorPedDest;
    
    private Menu menuSucursal;
    private AgregarEncomiendaController agregarEncomiendaCon;

    private AgregarClienteController agregarClienteCon;

    private TreeView<String> treeOrigen = new TreeView<String>(), pedidosPend = new TreeView<String>(), 
           pedidosCar = new TreeView<String>(), camionesDisp = new TreeView<String>(),
           camionesDesc = new TreeView<String>(), treeDestino=new TreeView<String>(),
           pedidosDest = new TreeView<String>();

    private TreeItem aMover;
    
    private Camion camionActual;
  
    private EventHandler<MouseEvent> dragDetected = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                
                 treeOrigen = (TreeView) event.getSource();
                 if(treeOrigen.getSelectionModel().getSelectedItem().getParent().equals(treeOrigen.getRoot())){
                Dragboard dragBoard = treeOrigen.startDragAndDrop(TransferMode.MOVE);
                Image img = new Image(Main.class.getResourceAsStream("/resources/images/pedidoIcon.png"));
                        dragBoard.setDragView(img);
          
                    ClipboardContent content = new ClipboardContent();
                    content.put(DataFormat.PLAIN_TEXT, treeOrigen.getSelectionModel().getSelectedItem().toString());
                    dragBoard.setContent(content);
                 }
                    event.consume();
            }
        };
   
     private EventHandler<DragEvent> dragOver = new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                String valueToMove = event.getDragboard().getString();
                TreeItem<String> itemToMove = search(treeOrigen.getRoot(), valueToMove);
                String[] idPedido = itemToMove.getValue().split("#");
                if(treeOrigen.getSelectionModel().getSelectedItem().getParent().equals(treeOrigen.getRoot()))
                {
                    if(treeOrigen != pedidosPend || camionActual.verificaEspacioDestino(Main.getUsuarioActual().getSucActual(), Integer.parseInt(idPedido[1])))
                 event.acceptTransferModes(TransferMode.MOVE);
                }
                    event.consume();
            }
        };
     private EventHandler<DragEvent> dragDropped = new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                    String valueToMove = event.getDragboard().getString();
                    TreeItem<String> itemToMove = search(treeOrigen.getRoot(), valueToMove);
                    String[] idPedido = itemToMove.getValue().split("#");
                        treeDestino = (TreeView) event.getGestureTarget();
                        // Remove from former parent.
                        treeOrigen.getRoot().getChildren().remove(itemToMove);
                        // Add to new parent.
                        treeDestino.getRoot().getChildren().add(itemToMove);

                        if(treeOrigen.getParent() == anchorPedPend)
                            Main.getUsuarioActual().cargarPed(camionActual, Integer.parseInt(idPedido[1]));
                        else 
                            Main.getUsuarioActual().descargarPed(camionActual, Integer.parseInt(idPedido[1]));
                        espDispCamAct.setText(Integer.toString(camionActual.getEspDisp()));

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
        
////////////////////////////////////////////Inicializar Menú\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
       
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
        
      cargarNombresClientes();
        
        split.setDividerPositions(1);

        
///////////////////////////////////////////////////ATENDER\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\     
         
        
        botonCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                       try{
                                limpiarAtender();

                          
                       }
                       catch (Exception exc)
                      {
                               }
                     
                
                }
            });
        
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
            modificar.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                    
                       try{        

                       agregarPane.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass().getResource("/resources/AgregarPedido.fxml")));
                       split.setDividerPositions(0.4684014869888476);
                       modificar.setVisible(false);
                       
                       }
                       catch (Exception exc)
                      {
                               }
                     
                
                }
            });
           
            botonAgregarEncomienda.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                    
                       try{       
                            if(Main.getUsuarioActual().getSucActual().getPedidoAbierto() != null){
                                agregarEncomiendaCon = new AgregarEncomiendaController();
                                agregarEncomiendaCon.setHandlerEncomienda((Event e2) -> {
                                    agregarEncomienda(new Encomienda(agregarEncomiendaCon.getPeso(),agregarEncomiendaCon.getVolumen(),
                                    agregarEncomiendaCon.getPrioridad(),agregarEncomiendaCon.getDirDestino(),agregarEncomiendaCon.getDescr()));
                                });
                                agregarPane.getChildren().setAll(agregarEncomiendaCon);
                                split.setDividerPositions(0.4684014869888476);
                            }
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
                                Main.getUsuarioActual().getSucActual().getPedidoAbierto().setCliente(nombreCliente);
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
           
           
        
//////////////////////////////////////////////TABS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
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
           
           
           
///////////////////////////////////////////////////ADMINISTRAR\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        
        
/////////////////////////////////////////////////////MENSAJES\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        
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
    
    private EventHandler eventoSucursal = new EventHandler() {

        public void handle(Event e) {
            ItemSucursalMenu item1 = (ItemSucursalMenu)e.getSource();
            Sucursal suc = item1.getSucursal();
            menuSucursal.setText(suc.getNombre());
            ItemSucursalMenu item2 = new ItemSucursalMenu(Main.getUsuarioActual().getSucActual());
            item2.setOnAction(eventoSucursal);
            Main.getUsuarioActual().setSucActual(suc);
            menuSucursal.getItems().remove(item1);
            menuSucursal.getItems().add(item2);
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
            comboBoxClientes.setPromptText("");

    }


     public void actualizarPestanaAdm(){
         
        camionActual = null;
        patenteCamAct.setText("");
        capacidadCamAct.setText("");
        espDispCamAct.setText("");
        estadoCamAct.setText("");
        pedidosCar = new TreeView<>(new TreeItem<>("empty"));
        pedidosCar.setShowRoot(false);
        amononarTreeView(anchorPedCar, pedidosCar);
         
        Sucursal sucActual = Main.getUsuarioActual().getSucActual();
        ObservableList patentesCamDisp = FXCollections.observableArrayList();
        ObservableList patentesCamADesc = FXCollections.observableArrayList();
        
        pedidosPend = new TreeView<>(listarPedidos(sucActual.getPedidosPend()));
        amononarTreeView(anchorPedPend, pedidosPend);
        pedidosPend.setOnDragDetected(dragDetected);
        pedidosPend.setOnDragOver(dragOver);
        pedidosPend.setOnDragDropped(dragDropped);
        
        pedidosDest = new TreeView<>(listarPedidos(sucActual.getPedidosEnDest()));
        amononarTreeView(anchorPedDest, pedidosDest);
        
        camionesDisp = new TreeView<>(listarCamiones(sucActual.getCamionesDisp()));
        amononarTreeView(anchorCamDisp, camionesDisp);
        
        camionesDesc = new TreeView<>(listarCamiones(sucActual.getCamionesPend()));
        amononarTreeView(anchorCamDesc, camionesDesc);
        
        camionesDisp.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            accionCamion.setVisible(true);
            accionCamion.setText("Enviar Camión");
            
            camionActual = null;
            String patenteCamionActual = (String)camionesDisp.getSelectionModel().getSelectedItem().getValue();
            
            Sucursal sucActual = Main.getUsuarioActual().getSucActual();
            
            
            for(Camion c : sucActual.getCamionesDisp()){
                if(c.getPatente()==patenteCamionActual) camionActual = c;
            }
            
            pedidosCar = new TreeView<>(listarPedidos(camionActual.getPedidos()));
            amononarTreeView(anchorPedCar, pedidosCar);
            pedidosCar.setOnDragDetected(dragDetected);
            pedidosCar.setOnDragOver(dragOver);
            pedidosCar.setOnDragDropped(dragDropped);
                
            patenteCamAct.setText(camionActual.getPatente());
            capacidadCamAct.setText(Integer.toString(camionActual.getCapacidad()));
            espDispCamAct.setText(Integer.toString(camionActual.getEspDisp()));
            estadoCamAct.setText("DISPONIBLE");
            if(camionActual.getPedidos().size()>0){
                accionCamion.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event){
                        Sucursal sucActual = Main.getUsuarioActual().getSucActual();
                        Sucursal destino = camionActual.getPedidos().get(0).getSucDestino();
                        destino.recibirCamionCargado(camionActual);
                        sucActual.enviarCamion(camionActual);
                        actualizarPestanaAdm();
                    }
                });
            }
            }
        });
    
    camionesDesc.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            accionCamion.setVisible(true);
            accionCamion.setText("Descargar Camión");
            camionActual = null;
            String patenteCamionSelec = (String)camionesDesc.getSelectionModel().getSelectedItem().getValue();
            Sucursal sucActual = Main.getUsuarioActual().getSucActual();
            
            for(Camion c : sucActual.getCamionesPend()){
                if(c.getPatente()==patenteCamionSelec) camionActual = c;
            }
            
            if(camionActual.getPedidos() != null){
                pedidosCar = new TreeView<>(listarPedidos(camionActual.getPedidos()));
                amononarTreeView(anchorPedCar, pedidosCar);
            }
            patenteCamAct.setText(camionActual.getPatente());
            capacidadCamAct.setText(Integer.toString(camionActual.getCapacidad()));
            espDispCamAct.setText(Integer.toString(camionActual.getEspDisp()));
            estadoCamAct.setText("DISPONIBLE");
            
            accionCamion.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event){
                    Sucursal sucActual = Main.getUsuarioActual().getSucActual();
                    sucActual.descargarCamion(camionActual);
                    actualizarPestanaAdm();
                }
            });
            }
        });
    
    anchorPedDest.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            vBoxConfPed.setVisible(true);
            }
        });
     }
     
     public TreeItem<String> listarCamiones(LinkedList<Camion> camiones){
         TreeItem<String> dummyRoot = new TreeItem<>("root");
         for(Camion c : camiones){
             TreeItem<String> camionView = new TreeItem<>(c.getPatente());
             camionView.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/resources/images/truckIcon.png"))));
             camionView.getChildren().add(new TreeItem("Capacidad total: " + c.getCapacidad() + " cm^3"));
             camionView.getChildren().add(new TreeItem("Espacio Disponible: " + c.getEspDisp() + "cm^3"));
             dummyRoot.getChildren().add(camionView);
         }
         
         return dummyRoot;
     }
     
     public TreeItem<String> listarPedidos(LinkedList<Pedido> pedidos){
         TreeItem<String> dummyRoot = new TreeItem<>("root");
         
         
         for(Pedido p : pedidos){
             TreeItem<String> child = new TreeItem<>("Pedido #" + Integer.toString(p.getIdPedido()));
             child.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/resources/images/pedidoIconView.png"))));
             child.getChildren().add(new TreeItem<>("Prioridad: " + p.getPrioridad()));
             child.getChildren().add(new TreeItem<>("Costo de Envío: $" + p.getCostoEnvio()));
             child.getChildren().add(new TreeItem<>("Sucursal de Destino: " + p.getSucDestino().getNombre()));
             child.getChildren().add(new TreeItem<>("Cliente: " + p.getCliente().getNombre()));
             child.getChildren().add(new TreeItem<>("Peso: " + p.getPeso() + " g"));
             child.getChildren().add(new TreeItem<>("Volumen aprox.: " + p.getVol()+ " cm^3"));
             TreeItem<String> encomiendas = new TreeItem<>("Encomiendas");
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
         return dummyRoot;
     }

     public void amononarTreeView (AnchorPane ap, TreeView<String> tv){
        tv.setShowRoot(false);
        ap.getChildren().add(tv);
        tv.setPrefWidth(ap.getPrefWidth());
        tv.setPrefHeight(ap.getPrefHeight());
     }
     
     public void limpiarAtender(){
         crearPedido.setVisible(true);
         Scene scene = crearPedido.getScene();
         Text idPedido = (Text) scene.lookup("#idPedido");
         idPedido.setText("Haga clic en crear pedido");
         modificar.setVisible(false);
         Text sucursalText = (Text) scene.lookup("#stext");
         sucursalText.setText("");
         listEncomiendas.getItems().clear();    
         comboBoxClientes.getItems().clear();
         cargarNombresClientes();

         Text presupuesto = (Text) scene.lookup("#presupuesto");
         presupuesto.setText("0");
         split.setDividerPositions(1);
     }
     
     private void agregarEncomienda(Encomienda encomienda){
         Main.getUsuarioActual().getSucActual().getPedidoAbierto().agregarEnc(encomienda);
         CajaEncomienda c = new CajaEncomienda(encomienda);
         c.setHandlerEliminar((Event e) -> {
             CajaEncomienda caja = (CajaEncomienda) e.getSource();
             Encomienda enc = caja.getEncomienda();
             Main.getUsuarioActual().getSucActual().getPedidoAbierto().eliminarEncomienda(enc);
             listEncomiendas.getItems().remove(caja);
         });
         listEncomiendas.getItems().add(c);
         //comboBoxEncomiendas.setPromptText(encomienda.getDescripcion());
         presupuesto.setText(""+Main.getUsuarioActual().getSucActual().getPedidoAbierto().getCostoEnvio());
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

