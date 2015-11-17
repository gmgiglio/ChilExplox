/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;
import java.util.EventObject;
import java.util.List;
import java.util.LinkedList;
import java.util.Stack;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
/**
 *
 * @author carlossalame
 */
public class Sucursal implements java.io.Serializable{
    
    private String nombre;
    private String ciudad;
    private String direccion;
    //Camiones disponibles para asignar pedidos
    private final LinkedList<Camion> camionesDisponibles = new LinkedList<>();
    //Camiones pendientes que esperan ser descargados
    private final LinkedList<Camion> camionesPendientes = new LinkedList<>();
    //Pedidos pendientes asignados a ningun camion
    private final LinkedList<Pedido> pedidosPendientes = new LinkedList<>();
    //Pedidos que ya se descargaron del camion y que esperan ser "revisados manualmente"
    private final LinkedList<Pedido> pedidosEnDestino = new LinkedList<>();
    //Pedidos confirmados con informacion correcta
    private final LinkedList<Pedido> pedidosConfirmados = new LinkedList<>();
    //Pedidos confirmados con informacion incorrecta
    private final LinkedList<Pedido> pedidosEquivocados = new LinkedList<>();
    private Pedido pedidoAbierto = null;
    private final Stack<Mensaje> buzonMensajes = new Stack<>();
    
    private final Usuario autoRobot;
    
    public Sucursal(String nombre, String ciudad, String direccion){
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.autoRobot = new Usuario("AutoRobot sucursal " + nombre, nombre);
    }
    
    public void agregarPedido(Pedido p){
        if(pedidosPendientes.isEmpty()) pedidosPendientes.add(p);
        else{   
            for(int i = 0; i < pedidosPendientes.size(); i++){

                if(p.getPrioridad() >= pedidosPendientes.get(i).getPrioridad()) {
                    pedidosPendientes.add(i, p);
                    break;
                }
                
            }
        }
    }
    
    public LinkedList<Camion> getCamionesDisponibles(){
        return this.camionesDisponibles;
    }
    
    public LinkedList<Pedido> getPedidosPendientes(){
        return new LinkedList(pedidosPendientes);
    }
    
    public LinkedList<Pedido> getPedidosEntregados(){
        return new LinkedList(pedidosConfirmados);
    }
    
    public LinkedList<Pedido> getPedidosEquivocados(){
        return new LinkedList(pedidosEquivocados);
    }
    
    public Pedido getPedidoPendiente(Integer id){
        for(int i=0; i<pedidosPendientes.size();i++)
        {
            if(pedidosPendientes.get(i).getIdPedido()==id)
                return pedidosPendientes.get(i);
        }
        
        return null;
        
    }
    
    
    public void cargarPedido(Pedido p){
        pedidosPendientes.remove(p);
    }
    
    public void cargarPedido(int id){
        for(Pedido p : pedidosPendientes){
            if(p.getIdPedido() == id ){
                p.setEstado(Estado.En_transito);
                pedidosPendientes.remove(p);
            }
        }
    }

    /**
     * @return the camionesPendientes
     */
    public LinkedList<Camion> getCamionesPend() {
        return camionesPendientes;
    }
    
    public void recibirCamionCargado(Camion c){
        camionesPendientes.add(c);
        for(Pedido p : c.getPedidos()){
            p.setEstado(Estado.En_destino);
        }
    }
    
    public void recibirCamionDescargado(Camion c){
        camionesDisponibles.add(c);
    }

    public void despacharCamion(){
        camionesPendientes.remove(0);
    }
    
    public void enviarCamion(Camion c){
        camionesDisponibles.remove(c);
    }
    
    //Bajar pedido del camion y pasarlo a la lista de pedidos en destino
    public void bajarPedido(Pedido p){
        getPedidosEnDest().add(p);
        p.setEstado(Estado.En_destino);
    }

    /**
     * @return the pedidosEnDestino
     */
    public LinkedList<Pedido> getPedidosEnDest() {
        return new LinkedList(pedidosEnDestino);
    }
    
    public void pedidoEntregado(Pedido p){
        getPedidosConfirmados().add(p);
        p.setEstado(Estado.Entregado);
    }
    
    public void pedidoEquivocado(Pedido p){
        getPedidosEquivocados().add(p);
        p.setEstado(Estado.Equivocado);
    }
    
    public void recibeMensaje(Mensaje mensaje){
        buzonMensajes.add(mensaje);
    }
    
    public String getCiudad(){
        return this.ciudad;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    //retorna true si encontró el camion entre sus camiones pendientes y false de lo contrario
    public boolean descargarCamion (Camion camion){
        if (camionesPendientes.contains(camion)){
            pedidosEnDestino.addAll(camion.descargarPedidos());
            camionesPendientes.remove(camion);
            camionesDisponibles.add(camion);
            return true;
        }
        else return false;
    }
    
    public boolean cerrarPedido(){
        if(pedidoAbierto.cerrarPedido()){
            agregarPedido(pedidoAbierto);
            pedidoAbierto = null;
            return true;
        }
        else return false;
    }
    
    
    private EventHandler handlerPedidoAtrasado;
    
    public void setHandlerPedidoAtrasado(EventHandler e){
        handlerPedidoAtrasado = e;
    }
            
    public void revisarTiempoPedidos(){
        if(pedidosPendientes.size()>0){
            for(Pedido p : this.pedidosPendientes){
                if(p.getPrioridad() >= Empresa.getAltaPrioridad() && p.getTiempoTranscurrido() >= Empresa.getTiempoLimite()){
                    if(handlerPedidoAtrasado != null) handlerPedidoAtrasado.handle(new ActionEvent(p,null));
                    else{
                        String texto = "Se notifica que se ha detectado un atrazo en pedido id: " + p.getIdPedido() + " de alta prioridad. ";
                        autoRobot.enviarMens("Notificación pedido atrasado",texto, this);
                    }
                }
            }
        }
    } 
    
    public void agregarCamion(String patente, int capacidad){
        camionesDisponibles.add(new Camion(patente, capacidad));
    }
    
    public void agregarCamionPend(String patente, int capacidad){
        camionesPendientes.add(new Camion(patente, capacidad));
    }
    
    public LinkedList<Mensaje> getMensajesEnBuzon(){
        return new LinkedList<>(this.buzonMensajes);
    }   

    /**
     * @return the pedidoAbierto
     */
    public Pedido getPedidoAbierto() {
        return pedidoAbierto;
    }

    /**
     * @param pedidoAbierto the pedidoAbierto to set
     */
    public void setPedidoAbierto(Pedido pedidoAbierto) {
        this.pedidoAbierto = pedidoAbierto;
    }
    
    public Camion getCamion(String patente){
        for(Camion c : camionesDisponibles){
            if(c.getPatente() == patente){
                return c;
            }
        }
        for(Camion c : camionesPendientes){
            if(c.getPatente() == patente){
                return c;
            }
        }
        return null;
    }

    /**
     * @return the pedidosConfirmados
     */
    public LinkedList<Pedido> getPedidosConfirmados() {
        return pedidosConfirmados;
    }
}
