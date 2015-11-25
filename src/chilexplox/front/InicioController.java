package chilexplox.front;

import chilexplox.*;
import controllers.AdministrarSucursales;
import controllers.AgregarClienteController;
import controllers.AgregarEncomiendaController;
import controllers.AgregarPedidoController;
import controllers.BuzonEntradaController;
import controllers.CajaEncomienda;
import controllers.EditarEncomiendaController;
import controllers.FichaCliente;
import controllers.Indicador;
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
import javafx.scene.Node;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InicioController implements Initializable {

        @FXML
    private MenuBar menuBar;
       @FXML
    private Button agregarCliente, botonAgregarEncomienda,crearPedido, botonCancelar, cerrarPedido, modificar,
               botonNuevoMensaje, botonBuzonEntrada, botonMensajesEnviados;
       @FXML
    private ImageView imagenTipo;
       @FXML
    private Pane indicadorPane;
       @FXML
    private Text textoTipo, errorDeCarga;
        @FXML
    private Button accionCamion, retornarCamion;
        @FXML
    private TabPane tabs;
        @FXML
    private SplitPane split;
        @FXML
    private AnchorPane anchorPaneMensajes, agregarPane;
        @FXML
    private ComboBox comboBoxClientes;
        @FXML
    private VBox vBoxConfPed;
        @FXML
    private Text patenteCamAct, origenCamAct, destinoCamAct, capacidadCamAct, espDispCamAct, estadoCamAct;
        @FXML
    private Text advertencia, presupuesto,textSucursal,textIdPedido;
        @FXML
    private AnchorPane anchorPedPend, anchorPedCar, anchorPedDest, anchorPedConf, anchorPedEq;
        @FXML
    private AnchorPane anchorCamDisp, anchorCamDesc;
       @FXML
    private AnchorPane enviadoPane, entradaPane,nuevoMensajePane; 
        @FXML
    private ListView listEncomiendas;
        
    private Sucursal sucActual;
    private Menu menuSucursal;
    private AgregarEncomiendaController agregarEncomiendaCon;
    private EditarEncomiendaController editarEncomiendaCon;

    private AgregarClienteController agregarClienteCon;

    private TreeView<String> treeOrigen = new TreeView<>(), treeDestino=new TreeView<>();
    private TreeView<String> pedidosPend = new TreeView<>(), pedidosCar = new TreeView<>(),
            pedidosDest = new TreeView<>(), pedidosEq = new TreeView<>(),
            pedidosConf = new TreeView<>();;
    private TreeView<String> camionesDisp = new TreeView<>(), camionesDesc = new TreeView<>();
       
    private Camion camionActual;
    
    private final EventHandler<MouseEvent> dragDetected;
    
    private final EventHandler<DragEvent> dragOver;
    
    private final EventHandler<DragEvent> dragDropped;
    
    private final EventHandler<MouseEvent> mostrarCamionDisponible;
    
    private final EventHandler<MouseEvent> mostrarCamionPendiente;

    private EventHandler cambiarSucursalActual;

    public InicioController() {
        cambiarSucursalActual = (EventHandler) (Event e) -> {
            ItemSucursalMenu itemNuevo = (ItemSucursalMenu)e.getSource();
            Sucursal suc = itemNuevo.getSucursal();
            menuSucursal.setText(suc.getNombre());
            if(getSucActual() != null){
                ItemSucursalMenu itemActual = new ItemSucursalMenu(getSucActual());
                itemActual.setOnAction(cambiarSucursalActual);
                if (Empresa.getSucursales().size() > 1) {menuSucursal.getItems().add(itemActual); }
            }
            Main.getUsuarioActual().setSucActual(suc);
            sucActual = suc;
            menuSucursal.getItems().remove(itemNuevo);
            
                actualizarPestanaAdm();
                actualizarPestanaMens();
        };
        
        dragDetected = (MouseEvent event) -> {
            //obtiene el treeview de origen
            treeOrigen = (TreeView) event.getSource();
            //Solo los hijos de root pueden ser drageados
            if(treeOrigen.getSelectionModel().getSelectedItem().getParent().equals(treeOrigen.getRoot())){
                Dragboard dragBoard = treeOrigen.startDragAndDrop(TransferMode.MOVE);
                Image img = new Image(Main.class.getResourceAsStream("/resources/images/pedidoIcon.png"));
                dragBoard.setDragView(img);
                ClipboardContent content = new ClipboardContent();
                content.put(DataFormat.PLAIN_TEXT, treeOrigen.getSelectionModel().getSelectedItem().toString());
                dragBoard.setContent(content);
            }
            event.consume();
        };
        dragOver = (DragEvent event) -> {
            String valueToMove = event.getDragboard().getString();
            TreeItem<String> itemToMove = search(treeOrigen.getRoot(), valueToMove);
            String[] idPedido = itemToMove.getValue().split("#");
            boolean cabePedido = false;
            if(treeOrigen.getParent() == anchorPedPend){
                if(camionActual != null){
                    cabePedido = camionActual.verificaEspacioDestino(sucActual, Integer.parseInt(idPedido[1]));
                }
            }
            event.acceptTransferModes(TransferMode.MOVE);
            
            event.consume();
        };
        dragDropped = (DragEvent event) -> {
            String valueToMove = event.getDragboard().getString();
            TreeItem<String> itemToMove = search(treeOrigen.getRoot(), valueToMove);
            String[] idPedido = itemToMove.getValue().split("#");
            Pedido pedidoACargar = sucActual.getPedidoPendiente(Integer.parseInt(idPedido[1]));
            treeDestino = (TreeView) event.getGestureTarget();
            boolean mismoDestino = false;
            boolean sePuedeCargar = false;
            if(treeOrigen.getParent() == anchorPedPend){
                if(camionActual != null){
                    mismoDestino = camionActual.mismoDestino(sucActual, Integer.parseInt(idPedido[1]));
                    sePuedeCargar = camionActual.verificaEspacioDestino(sucActual, Integer.parseInt(idPedido[1]));
                }
            }
            if((treeOrigen.getParent() == anchorPedPend && treeDestino.getParent() == anchorPedCar &&
                    pedidoACargar.getTipo() == camionActual.getTipo() && sePuedeCargar) ||
                    (treeOrigen.getParent() == anchorPedCar && treeDestino.getParent() == anchorPedPend) ||
                    (treeOrigen.getParent() == anchorPedDest &&
                    (treeDestino.getParent() == anchorPedConf || treeDestino.getParent() == anchorPedEq))){
                // Remove from former parent.
                treeOrigen.getRoot().getChildren().remove(itemToMove);
                // Add to new parent.
                treeDestino.getRoot().getChildren().add(itemToMove);
                
                if(treeDestino.getParent() == anchorPedCar){
                    ((Funcionario)Main.getUsuarioActual()).cargarPed(camionActual, Integer.parseInt(idPedido[1]));
                    espDispCamAct.setText(""+camionActual.getEspDisp());
                    camionesDisp = new TreeView<>(listarCamiones(sucActual.getCamionesDisponibles()));
                    amononarTreeView(anchorCamDisp, camionesDisp);
                }
                
                else if(treeDestino.getParent() == anchorPedPend){
                    ((Funcionario)Main.getUsuarioActual()).descargarPed(camionActual, Integer.parseInt(idPedido[1]));
                    espDispCamAct.setText(Integer.toString(camionActual.getEspDisp()));
                }
                else if(treeDestino.getParent() == anchorPedConf)
                    ((Funcionario)Main.getUsuarioActual()).confirmarPed(Integer.parseInt(idPedido[1]), true);
                
                else if(treeDestino.getParent() == anchorPedEq){
                    ((Funcionario)Main.getUsuarioActual()).confirmarPed(Integer.parseInt(idPedido[1]), false);
                    Sucursal origen = camionActual.getPedidos().get(0).getSucOrigen();
                    ((Funcionario)Main.getUsuarioActual()).enviarMens("Retorno del Camion " + camionActual.getPatente(),
                            "Estimado:\n\t Le escribimos desde la sucursal " + sucActual.getNombre() + " para informarle "
                                    + "que hemos recibido el pedido de id número "+ pedidoACargar.getIdPedido() + ". "
                                    + "Desafortunadamente, este pedido no debía llegar a nuestra suscursal, por lo que lo "
                                    + "tenemos retenido junto con el resto de los pedidos equivocados.\n" 
                                    + "Quedamos atentos a sus comentarios.\nSe despide,\n\n" 
                                    + ((Funcionario)Main.getUsuarioActual()).getNombreUsuario() + "\nSucursal " 
                                    + sucActual.getNombre(), origen);
                camionActual.setEstado(EstadoCamion.Con_Errores);
                }
                
                event.consume();
            }
            else if(treeOrigen.getParent() == anchorPedPend && treeDestino.getParent() == anchorPedCar){
                if(pedidoACargar.getTipo() != camionActual.getTipo()){
                    Alert alert = new Alert(AlertType.ERROR, "Un pedido del tipo " + pedidoACargar.getTipo() +
                            " no puede cargarse en un " + "camion de tipo " + camionActual.getTipo() + ".", ButtonType.CLOSE);
                    alert.showAndWait();
                }
                else if(!mismoDestino){
                    Alert alert = new Alert(AlertType.ERROR, "Este camión no se dirije a la sucursal de destino del "
                            + "pedido seleccionado.", ButtonType.CLOSE);
                    alert.showAndWait();
                }
                else if(!sePuedeCargar){
                    Alert alert = new Alert(AlertType.ERROR, "No hay suficiente espacio en este camión.", ButtonType.CLOSE);
                    alert.showAndWait();
                }
            }
        };
        
        mostrarCamionDisponible = (MouseEvent event) -> {
            vBoxConfPed.setVisible(false);
            pedidosCar.setVisible(true);
            accionCamion.setVisible(true);
            retornarCamion.setVisible(false);
            accionCamion.setText("Enviar Camión");
            camionActual = null;
            String patenteCamionActual = (String)camionesDisp.getSelectionModel().getSelectedItem().getValue();
            for (Camion c : sucActual.getCamionesDisponibles()) {
                if(c.getPatente() == null ? patenteCamionActual == null : c.getPatente().equals(patenteCamionActual))
                    camionActual = c;
            }
            pedidosCar = new TreeView<>(listarPedidos(camionActual.getPedidos()));
            amononarTreeView(anchorPedCar, pedidosCar);
            pedidosCar.setOnDragDetected(dragDetected);
            pedidosCar.setOnDragOver(dragOver);
            pedidosCar.setOnDragDropped(dragDropped);
            actualizarDatosCamion();
            estadoCamAct.setText("DISPONIBLE");
            if (camionActual.getPedidos().size()>0) {
                accionCamion.setOnMouseClicked((MouseEvent event1) -> {
                    Sucursal destino = camionActual.getPedidos().get(0).getSucDestino();
                    sucActual.enviarCamion(camionActual);
                    destino.recibirCamionCargado(camionActual);
                    
                    actualizarPestanaAdm();
                });
            }
        };
        
        mostrarCamionPendiente = (MouseEvent event) -> {
            vBoxConfPed.setVisible(false);
            pedidosCar.setVisible(true);
            accionCamion.setVisible(true);
            accionCamion.setText("Descargar Camión");
            camionActual = null;
            String patenteCamionSelec = (String)camionesDesc.getSelectionModel().getSelectedItem().getValue();
            for (Camion c : sucActual.getCamionesPend()) {
                if(c.getPatente() == null ? patenteCamionSelec == null : c.getPatente().equals(patenteCamionSelec))
                    camionActual = c;
            }
            if(camionActual.getPedidos() != null){
                pedidosCar = new TreeView<>(listarPedidos(camionActual.getPedidos()));
                amononarTreeView(anchorPedCar, pedidosCar);
            }
            
            if(camionActual.getEstado() == EstadoCamion.Sin_Errores)
                retornarCamion.setVisible(true);
            else
                retornarCamion.setVisible(false);
            
            actualizarDatosCamion();
            estadoCamAct.setText("PENDIENTE");
            accionCamion.setOnMouseClicked((MouseEvent event1) -> {
                if(camionActual.getEstado() == EstadoCamion.Sin_Errores)
                    sucActual.descargarCamion(camionActual);
                else
                    sucActual.descargarCamionEquivocado(camionActual);
                camionActual.setEstado(EstadoCamion.Sin_Errores);
                actualizarPestanaAdm();
            });
            retornarCamion.setOnMouseClicked((MouseEvent event1) -> {
                Sucursal origen = camionActual.getPedidos().get(0).getSucOrigen();
                ((Funcionario)Main.getUsuarioActual()).enviarMens("Retorno del Camion " + camionActual.getPatente(), "Estimado:\n"
                        + "\t Le escribimos desde la sucursal " + sucActual.getNombre() + " para informarle que hemos recibido al camión de patente " + camionActual.getPatente() + ". Desafortunadamente, el contenido de este presenta errores, por lo que " + "decidimos enviarlo de vuelta a sus instalaciones.\n" + "Quedamos atentos a sus comentarios.\nSe despide,\n\n" + ((Funcionario)Main.getUsuarioActual()).getNombreUsuario() + "\nSucursal " + sucActual.getNombre(), origen);
                camionActual.setEstado(EstadoCamion.Con_Errores);
                origen.recibirCamionCargado(camionActual);
                sucActual.retornarCamion(camionActual);
                actualizarPestanaAdm();
            });
        };
    }
    
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
        String nombreMenuSuc;
        if(sucActual == null){ nombreMenuSuc = "Sucursal";}
        else{ nombreMenuSuc = sucActual.getNombre();} 
        
        menuSucursal = new Menu(nombreMenuSuc);
        
        //agregar sucursales al menu de sucursales
        LinkedList<Sucursal> sucEnLista = new LinkedList(sucursales);
        sucEnLista.remove(sucActual);
            for (Sucursal s : sucEnLista) {
                ItemSucursalMenu item = new ItemSucursalMenu(s);
                menuSucursal.getItems().add(item);
                item.setOnAction(cambiarSucursalActual);
            }
        menuBar.getMenus().addAll(menuUsuario,menuSucursal);
        
      cargarNombresClientes();
        
        split.setDividerPositions(1);

        
///////////////////////////////////////////////////ATENDER\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\     
         
        
        botonCancelar.setOnAction((ActionEvent e) -> {
            limpiarAtender();
        });
        
        agregarCliente.setOnAction((ActionEvent e) -> {
            try{
                agregarClienteCon = new AgregarClienteController();
                agregarClienteCon.setHandlerCliente((Event e2) -> {
                    
                    RegistroCliente registro = ((Funcionario)Main.getUsuarioActual()).crearCliente(
                            agregarClienteCon.getNombre(),agregarClienteCon.getApellidos(),
                            agregarClienteCon.getCalle()+" "+agregarClienteCon.getNumero()+", "+agregarClienteCon.getComuna()+", "
                                    +agregarClienteCon.getCiudad(),agregarClienteCon.getTelefono());
                    
                    Cliente cliente =registro.cliente;
                    String enComboBox = cliente.getNombreCompleto() +" " + cliente.getNombreUsuario();
                    comboBoxClientes.getItems().add(enComboBox);
                    comboBoxClientes.setPromptText(enComboBox);
                    
                    //mostrar FichaCliente
                    FichaCliente ficha = new FichaCliente(registro);
                    agregarPane.getChildren().setAll(ficha);
                    
                });
                agregarPane.getChildren().setAll(agregarClienteCon);
                split.setDividerPositions(0.4684014869888476);
                
            }
            catch (Exception exc)
            {
                System.out.println("InicioController: No se pudo cargar AgregarClienteController");
                throw new RuntimeException(exc);
            }
        });
        
           
            botonAgregarEncomienda.setOnAction((ActionEvent e) -> {
                try{
                    if(sucActual.getPedidoAbierto() != null){
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
        });
            
            
            cerrarPedido.setOnAction((ActionEvent e) -> {
                String nombreCliente = (String)comboBoxClientes.getValue();
                if(nombreCliente != null && sucActual.getPedidoAbierto().getEncomiendas().size()>0){
                    advertencia.setText("");
                    Scene scene = split.getScene();
                    Text idPedido = (Text)scene.lookup("#idPedido");
                    sucActual.getPedidoAbierto().setCliente(nombreCliente);
                    ((Funcionario)Main.getUsuarioActual()).cerrarPed();
                    limpiarAtender();
                }
                else if(nombreCliente == null){
                    advertencia.setText("Debe seleccionar un cliente");
                } else {
                    advertencia.setText("Debe haber al menos una encomienda");
                }
               
        });
           
           
        
//////////////////////////////////////////////TABS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        for(int j=0;j<tabs.getTabs().size();j++)
        {
        tabs.getTabs().get(j).setOnSelectionChanged((Event e) -> {
            Tab t = (Tab)(e.getSource());
            if(((Tab)(e.getSource())).isSelected()){
                switch(t.getText()){
                    
                    case "Atender":
                        break;
                        
                    case "Administrar":
                        actualizarPestanaAdm();
                        break;
                        
                    case "Mensajes":
                        actualizarPestanaMens();
                        break;
                    default:;
                    break;
                }
            }
        }); 
        }
           
           
           
///////////////////////////////////////////////////ADMINISTRAR\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        
        
/////////////////////////////////////////////////////MENSAJES\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        
        botonNuevoMensaje.setOnAction((ActionEvent e) -> {
            try{
                anchorPaneMensajes.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass().getResource("/resources/NuevoMensaje.fxml")));
                nuevoMensajePane.setStyle("-fx-background-color: #E2E2E2;");
                entradaPane.setStyle("-fx-background-color: #F2F2F2;");
                enviadoPane.setStyle("-fx-background-color: #F2F2F2;");
            }
            catch (Exception exc){
                System.out.println("InicioController: No se cargó NuevoMensaje.fxml ");
            }
        });
        
        botonBuzonEntrada.setOnAction((ActionEvent e) -> {
            
            BuzonEntradaController bec = new BuzonEntradaController();
            anchorPaneMensajes.getChildren().setAll(bec); 
 
            nuevoMensajePane.setStyle("-fx-background-color: #F2F2F2;");
            entradaPane.setStyle("-fx-background-color: #E2E2E2;");
            enviadoPane.setStyle("-fx-background-color: #F2F2F2;");
                
            
        });
        
        botonMensajesEnviados.setOnAction((ActionEvent e) -> {
            try{
                anchorPaneMensajes.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass()
                        .getResource("/resources/MensajesEnviados.fxml"))); 
                nuevoMensajePane.setStyle("-fx-background-color: #F2F2F2;");
                entradaPane.setStyle("-fx-background-color: #F2F2F2;");
                enviadoPane.setStyle("-fx-background-color: #E2E2E2;");
            }
            catch (Exception exc){}
        });
        
        
/////////////////////////////////////////////////////TAB ADMINISTRADOR\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        
        if(Main.getUsuarioActual() instanceof Administrador){
            AdministrarSucursales admSuc = new AdministrarSucursales();
            admSuc.setHandlerCambiosSucursal((EventHandler) (Event e)->{
                recargarMenuSucursal();
            });
            addTab(admSuc, "Sucursales");
        }
        
        
    }
    
    public void cargarNombresClientes(){

            ObservableList nombreClientes = FXCollections.observableArrayList();
            for (int i=0; i<  Empresa.getClientes().size();i++)
            {
                nombreClientes.add(Empresa.getClientes().get(i).getNombre());
            }
            
            comboBoxClientes.getItems().setAll(nombreClientes);
            comboBoxClientes.setPromptText("");

    }
    
    private void actualizarPestanaAdm(){
         
        accionCamion.setVisible(false);
        retornarCamion.setVisible(false);
        camionActual = null;
        patenteCamAct.setText("");
        origenCamAct.setText("");
        destinoCamAct.setText("");
        capacidadCamAct.setText("");
        espDispCamAct.setText("");
        estadoCamAct.setText("");
        
        pedidosCar = new TreeView<>(new TreeItem<>("empty"));
        pedidosCar.setShowRoot(false);
        amononarTreeView(anchorPedCar, pedidosCar);
        
        pedidosPend = new TreeView<>(listarPedidos(sucActual.getPedidosPendientes()));
        amononarTreeView(anchorPedPend, pedidosPend);
        
        pedidosDest = new TreeView<>(listarPedidos(sucActual.getPedidosEnDest()));
        amononarTreeView(anchorPedDest, pedidosDest);
        
        pedidosConf = new TreeView<>(listarPedidos(sucActual.getPedidosConfirmados()));
        amononarTreeView(anchorPedConf, pedidosConf);
        
        pedidosEq = new TreeView<>(listarPedidos(sucActual.getPedidosEquivocados()));
        amononarTreeView(anchorPedEq, pedidosEq);

        camionesDisp = new TreeView<>(listarCamiones(sucActual.getCamionesDisponibles()));
        amononarTreeView(anchorCamDisp, camionesDisp);
        camionesDisp.setPrefHeight(240);
        
        camionesDesc = new TreeView<>(listarCamiones(sucActual.getCamionesPend()));
        amononarTreeView(anchorCamDesc, camionesDesc);
        
        pedidosDest.setOnMouseClicked((MouseEvent event) -> {
            vBoxConfPed.setVisible(true);
            pedidosCar.setVisible(false);
        });
    }
     
    private TreeItem<String> listarCamiones(LinkedList<Camion> camiones){
        TreeItem<String> dummyRoot = new TreeItem<>("root");
        for(Camion c : camiones){
            TreeItem<String> camionView = new TreeItem<>(c.getPatente());
            if(c.getEstado() == EstadoCamion.Con_Errores)
                camionView.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/resources/images/ErrorTruckIcon.png"))));
            else if(c.getTipo() == Tipo.Normal)
                camionView.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/resources/images/TruckIcon.png"))));
            else if(c.getTipo() == Tipo.Animales)
                camionView.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/resources/images/AnimalTruckIcon.png"))));
            else if(c.getTipo() == Tipo.Blindado)
                camionView.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/resources/images/RubyTruckIcon.png"))));
            else if(c.getTipo() == Tipo.Radioactivo)
                camionView.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/resources/images/RadioactiveTruckIcon.png"))));
            else if(c.getTipo() == Tipo.Fragil)
                camionView.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/resources/images/FragileTruckIcon.png"))));
            else if(c.getTipo() == Tipo.Refrigerado)
                camionView.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/resources/images/ColdTruckIcon.png"))));
            
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
            child.getChildren().add(new TreeItem<>("Tipo: " + p.getTipo()));
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
    
    private void amononarTreeView (AnchorPane ap, TreeView<String> tv){
        tv.setShowRoot(false);
        ap.getChildren().add(tv);
        tv.setPrefWidth(ap.getPrefWidth());
        tv.setPrefHeight(ap.getPrefHeight());
        if (tv == pedidosPend){
            tv.setOnDragDetected(dragDetected);
            tv.setOnDragOver(dragOver);
            tv.setOnDragDropped(dragDropped);
        }
        else if(tv == pedidosDest)
            tv.setOnDragDetected(dragDetected);
        else if(tv == pedidosConf || tv == pedidosEq){
            tv.setOnDragOver(dragOver);
            tv.setOnDragDropped(dragDropped);
        }   
        else if(tv == camionesDisp)
            tv.setOnMouseClicked(mostrarCamionDisponible);
        else if(tv == camionesDesc)
            tv.setOnMouseClicked(mostrarCamionPendiente);

    }
    
    public void limpiarAtender(){
        advertencia.setText("");   
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
        presupuesto.setText("0");
        split.setDividerPositions(1);
    }
    
    private void agregarEncomienda(Encomienda encomienda){
        sucActual.getPedidoAbierto().agregarEnc(encomienda);
        CajaEncomienda c = new CajaEncomienda(encomienda);
        c.setHandlerEliminar((Event e) -> {
            CajaEncomienda caja = (CajaEncomienda) e.getSource();
            Encomienda enc = caja.getEncomienda();
            sucActual.getPedidoAbierto().eliminarEncomienda(enc);
           
            listEncomiendas.getItems().remove(caja);
            presupuesto.setText(""+sucActual.getPedidoAbierto().getCostoEnvio());

        });
        c.setHandlerEditarEncomienda((Event e) -> {
            CajaEncomienda caja = (CajaEncomienda) e.getSource();
            Encomienda enc = caja.getEncomienda();
            editarEncomiendaCon = new EditarEncomiendaController(enc);
            editarEncomiendaCon.setHandlerAceptarEncomienda((Event ev) -> {
                eliminarEncomienda(editarEncomiendaCon.getEnc());
                agregarEncomienda(editarEncomiendaCon.getEncomiendaMod());
           });
                               agregarPane.getChildren().setAll(editarEncomiendaCon);
                               split.setDividerPositions(0.4684014869888476);
        });
        listEncomiendas.getItems().add(c);
        //comboBoxEncomiendas.setPromptText(encomienda.getDescripcion());
        presupuesto.setText(""+ sucActual.getPedidoAbierto().getCostoEnvio());
    }

    private void eliminarEncomienda(Encomienda encomienda){
        sucActual.getPedidoAbierto().eliminarEncomienda(encomienda);
        List<CajaEncomienda> listcajas = listEncomiendas.getItems(); 
        
        for(CajaEncomienda c:listcajas)
        {
            if(c.getEncomienda()==encomienda){
             
                listEncomiendas.getItems().remove(c);
                break;
            }
        
        }
       
       presupuesto.setText(""+sucActual.getPedidoAbierto().getCostoEnvio());

     
    }
    
    private AgregarPedidoController apc;
    public void crearPedido(ActionEvent event){
        apc = new AgregarPedidoController();
        agregarPane.getChildren().setAll(apc);
        split.setDividerPositions(0.4684014869888476);
        
        apc.sethandlerPedido((Event e) -> {
            Sucursal s = apc.getSucursal();
            Tipo t = apc.getTipo();
            Pedido p = ((Funcionario)Main.getUsuarioActual()).crearPed(s,t);
            textSucursal.setText(s.getNombre()) ;
            textIdPedido.setText(""+p.getIdPedido());
            crearPedido.setVisible(false);
            split.setDividerPositions(1);
            modificar.setVisible(true);
        });
    }
           
    private class ItemSucursalMenu extends MenuItem{
        private final Sucursal sucursal;
        
        public ItemSucursalMenu(Sucursal sucursal){
            this.sucursal = sucursal;
            this.setText(sucursal.getNombre());
            
        }
        
        public Sucursal getSucursal(){
            return sucursal;
        } 
    }
    
    private void actualizarDatosCamion(){
        patenteCamAct.setText(camionActual.getPatente());
        origenCamAct.setText(camionActual.getOrigen().getNombre());
        if(camionActual.getDestino() != null)
            destinoCamAct.setText(camionActual.getDestino().getNombre());
        capacidadCamAct.setText(Integer.toString(camionActual.getCapacidad()));
        espDispCamAct.setText(Integer.toString(camionActual.getEspDisp()));
        textoTipo.setText("Tipo: " + camionActual.getTipo());
        

        if(camionActual.getTipo() == Tipo.Animales){
            Image img = new Image(Main.class.getResourceAsStream("/resources/images/AnimalBigIcon.png"));
            imagenTipo.setImage(img);
        }
        else if(camionActual.getTipo() == Tipo.Blindado){
            Image img = new Image(Main.class.getResourceAsStream("/resources/images/RubyBigIcon.png"));
            imagenTipo.setImage(img);
        }
        else if(camionActual.getTipo() == Tipo.Fragil){
            Image img = new Image(Main.class.getResourceAsStream("/resources/images/FragilBigIcon.png"));
            imagenTipo.setImage(img);
        }
        else if(camionActual.getTipo() == Tipo.Radioactivo){
            Image img = new Image(Main.class.getResourceAsStream("/resources/images/RadioActiveBigIcon.png"));
            imagenTipo.setImage(img);
        }
        else if(camionActual.getTipo() == Tipo.Refrigerado){
            Image img = new Image(Main.class.getResourceAsStream("/resources/images/CopoBigIcon.png"));
            imagenTipo.setImage(img);
        }
        else{
            imagenTipo.setImage(null);
        }
    }
    
    private void actualizarPestanaMens(){
        try{
            //Mensajes enviados
            anchorPaneMensajes.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass()
                    .getResource("/resources/MensajesEnviados.fxml"))); 
            nuevoMensajePane.setStyle("-fx-background-color: #F2F2F2;");
            entradaPane.setStyle("-fx-background-color: #F2F2F2;");
            enviadoPane.setStyle("-fx-background-color: #E2E2E2;");
            
            //Buzon de entrada
            BuzonEntradaController bec = new BuzonEntradaController();
            anchorPaneMensajes.getChildren().setAll(bec); 
            nuevoMensajePane.setStyle("-fx-background-color: #F2F2F2;");
            entradaPane.setStyle("-fx-background-color: #E2E2E2;");
            enviadoPane.setStyle("-fx-background-color: #F2F2F2;");
            indicadorPane.getChildren().setAll(new Indicador(0));
            bec.actualizarIndicador();
            
            
            
            
        }
        catch (Exception exc){}
        
    }
    
    public void addTab(Node contenido,String titulo){
        Tab tab = new Tab();
        tab.setText(titulo);
        tab.setContent(contenido);
        List<Tab> t = tabs.getTabs();
        t.add(tab);
    }
    
    public List<Tab> getTabs(){
        return tabs.getTabs();
    }
    
    public void borrarTab(Tab tab){
        tabs.getTabs().remove(tab);
    }
    
    private void recargarMenuSucursal(){
        
        menuSucursal.getItems().clear();
        
        LinkedList<Sucursal> sucursales = Empresa.getSucursales();
        
        LinkedList<Sucursal> sucEnLista = new LinkedList(sucursales);
        if(getSucActual() != null) { sucEnLista.remove(Main.getUsuarioActual().getSucActual()); }
        
        String nombreMenuSuc;
        if(sucActual == null){ nombreMenuSuc = "Sucursal";}
        else{ nombreMenuSuc = sucActual.getNombre();}
        menuSucursal.setText(nombreMenuSuc);
        
        for(Sucursal s : sucEnLista){
                ItemSucursalMenu item = new ItemSucursalMenu(s);
                menuSucursal.getItems().add(item); 
                item.setOnAction(cambiarSucursalActual);  
                
        } 
    }
    
    private Sucursal getSucActual(){
        return Main.getUsuarioActual().getSucActual();
    }
}