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
    private final LinkedList<Camion> camionesDisp = new LinkedList<>();
    //Camiones pendientes que esperan ser descargados
    private final LinkedList<Camion> camionesPend = new LinkedList<>();
    //Pedidos pendientes asignados a ningun camion
    private final LinkedList<Pedido> pedidosPend = new LinkedList<>();
    //Pedidos que ya se descargaron del camion y que esperan ser "revisados manualmente"
    private final LinkedList<Pedido> pedidosEnDest = new LinkedList<>();
    //Pedidos confirmados con informacion correcta
    private final LinkedList<Pedido> pedidosEntregados = new LinkedList<>();
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
        if(pedidosPend.isEmpty()) pedidosPend.add(p);
        else{   
            for(int i = 0; i < pedidosPend.size(); i++){

                if(p.getPrioridad() >= pedidosPend.get(i).getPrioridad()) {
                    pedidosPend.add(i, p);
                    break;
                }
                
            }
        }
    }
    
    public LinkedList<Camion> getCamionesDisp(){
        return this.camionesDisp;
    }
    
    public LinkedList<Pedido> getPedidosPend(){
        return new LinkedList(pedidosPend);
    }
    
    public LinkedList<Pedido> getPedidosEntregados(){
        return new LinkedList(pedidosEntregados);
    }
    
    public LinkedList<Pedido> getPedidosEquivocados(){
        return new LinkedList(pedidosEquivocados);
    }
    
    public Pedido getPedidoPendiente(Integer id){
        for(int i=0; i<pedidosPend.size();i++)
        {
            if(pedidosPend.get(i).getIdPedido()==id)
                return pedidosPend.get(i);
        }
        
        return null;
        
    }
    
    
    public void cargarPedido(Pedido p){
        pedidosPend.remove(p);
    }
    
    public void cargarPedido(int id){
        for(Pedido p : pedidosPend){
            if(p.getIdPedido() == id ){
                p.setEstado(Estado.En_transito);
                pedidosPend.remove(p);
            }
        }
    }

    /**
     * @return the camionesPend
     */
    public LinkedList<Camion> getCamionesPend() {
        return camionesPend;
    }
    
    public void recibirCamionCargado(Camion c){
        camionesPend.add(c);
        for(Pedido p : c.getPedidos()){
            p.setEstado(Estado.En_destino);
        }
    }
    
    public void recibirCamionDescargado(Camion c){
        camionesDisp.add(c);
    }

    public void despacharCamion(){
        camionesPend.remove(0);
    }
    
    public void enviarCamion(Camion c){
        camionesDisp.remove(c);
    }
    
    //Bajar pedido del camion y pasarlo a la lista de pedidos en destino
    public void bajarPedido(Pedido p){
        getPedidosEnDest().add(p);
        p.setEstado(Estado.En_destino);
    }

    /**
     * @return the pedidosEnDest
     */
    public LinkedList<Pedido> getPedidosEnDest() {
        return new LinkedList(pedidosEnDest);
    }
    
    public void pedidoEntregado(Pedido p){
        pedidosEntregados.add(p);
        p.setEstado(Estado.Entregado);
    }
    
    public void pedidoEquivocado(Pedido p){
        pedidosEquivocados.add(p);
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
        if (camionesPend.contains(camion)){
            pedidosEnDest.addAll(camion.descargarPedidos());
            camionesPend.remove(camion);
            camionesDisp.add(camion);
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
        if(pedidosPend.size()>0){
            for(Pedido p : this.pedidosPend){
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
        camionesDisp.add(new Camion(patente, capacidad));
    }
    
    public void agregarCamionPend(String patente, int capacidad){
        camionesPend.add(new Camion(patente, capacidad));
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
        for(Camion c : camionesDisp){
            if(c.getPatente() == patente){
                return c;
            }
        }
        for(Camion c : camionesPend){
            if(c.getPatente() == patente){
                return c;
            }
        }
        return null;
    }
}
