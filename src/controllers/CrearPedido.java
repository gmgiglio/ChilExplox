/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chilexplox.Cliente;
import chilexplox.Encomienda;
import chilexplox.Funcionario;
import chilexplox.Pedido;
import chilexplox.Sucursal;
import chilexplox.front.Main;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author gianfrancogiglio
 */
public class CrearPedido extends AnchorPane {

    @FXML
    private Button botonCrearPedido,botonAgregarEncomienda,botonCerrarPedido,botonCancelar,botonModificar;
    @FXML
    private AgregarEncomiendaController agregarEncomiendaCon;
    @FXML
    private SplitPane split;
    @FXML
    private AnchorPane agregarPane;
    @FXML
    private ListView listEncomiendas;
    @FXML
    private Text textPresupuesto,textSucursal,textIdPedido;

    
    public CrearPedido(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/CrearPedido.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
         try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
         
         
         
         
         botonCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                limpiar();

                       
                
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
            
            
           botonCerrarPedido.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Cliente cl = (Cliente)Main.getUsuarioActual();
                cl.getSucActual().getPedidoAbierto().setCliente(cl.getNombre());
                ((Cliente)Main.getUsuarioActual()).cerrarPed();
                limpiar();

                }
            });
    }
    
    public void limpiar(){
         botonCrearPedido.setVisible(true);
         Scene scene = botonCrearPedido.getScene();
         Text idPedido = (Text) scene.lookup("#idPedido");
         idPedido.setText("Haga clic en crear pedido");
         botonModificar.setVisible(false);
         Text sucursalText = (Text) scene.lookup("#stext");
         sucursalText.setText("");
         listEncomiendas.getItems().clear();    

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
         textPresupuesto.setText(""+Main.getUsuarioActual().getSucActual().getPedidoAbierto().getCostoEnvio());
     }
    
    private AgregarPedidoController apc;
    
    public void crearPedido(ActionEvent event){
         
        apc = new AgregarPedidoController();
        agregarPane.getChildren().setAll(apc);
        split.setDividerPositions(0.4684014869888476);
        
        apc.sethandlerPedido((Event e) -> {
            Sucursal s = apc.getSucursal();
            Pedido p = Main.getUsuarioActual().crearPed(s);
            textSucursal.setText(s.getNombre()) ;
            textIdPedido.setText(""+p.getIdPedido());
            botonCrearPedido.setVisible(false);
            split.setDividerPositions(1);
            botonModificar.setVisible(true);
        });    
            
     }
    
}
