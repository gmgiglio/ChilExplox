/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;
import java.util.List;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Date;
/**
 *
 * @author carlossalame
 */
public class Sucursal implements java.io.Serializable{
    
    private String nombre;
    private String ciudad;
    private String direccion;
    //Camiones disponibles para asignar pedidos
    private final List<Camion> camionesDisp = new LinkedList<>();
    //Camiones pendientes que esperan ser descargados
    private final List<Camion> camionesPend = new LinkedList<>();
    //Pedidos pendientes asignados a ningun camion
    private final List<Pedido> pedidosPend = new LinkedList<>();
    //Pedidos que ya se descargaron del camion y que esperan ser "revisados manualmente"
    private final List<Pedido> pedidosEnDest = new LinkedList<>();
    //Pedidos confirmados con informacion correcta
    private final List<Pedido> pedidosEntregados = new LinkedList<>();
    //Pedidos confirmados con informacion incorrecta
    private final List<Pedido> pedidosEquivocados = new LinkedList<>();
    
    private final Stack<Mensaje> buzonMensajes = new Stack<>();
    
    public Sucursal(String nombre, String ciudad, String direccion){
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.direccion = direccion;
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
    
    public List<Camion> getCamionesDisp(){
        return this.camionesDisp;
    }
    
    public List<Pedido> getPedidosPend(){
        return this.pedidosPend;
    }
    
    public Pedido getPedido(Integer id){
        for(int i=0; i<pedidosPend.size();i++)
        {
            if(pedidosPend.get(i).getIdPedido()==id)
                return pedidosPend.get(i);
        }
        
        return null;
        
    }
    
    
    public void despacharPedido(){
        this.pedidosPend.remove(0);
    }

    /**
     * @return the camionesPend
     */
    public List<Camion> getCamionesPend() {
        return camionesPend;
    }
    
    public void recibirCamionCargado(Camion c){
        camionesPend.add(c);
    }
    
    public void recibirCamionDescargado(Camion c){
        camionesPend.add(c);
    }

    public void despacharCamion(){
        camionesPend.remove(0);
    }
    
    //Bajar pedido del camion y pasarlo a la lista de pedidos en destino
    public void bajarPedido(Pedido p){
        getPedidosEnDest().add(p);
        p.setEstado(Estado.En_destino);
    }

    /**
     * @return the pedidosEnDest
     */
    public List<Pedido> getPedidosEnDest() {
        return pedidosEnDest;
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
        if (this.camionesPend.contains(camion)){
            this.pedidosEnDest.addAll(camion.descargarPedios());
            return true;
        }
        else return false;
    }
    
    public void revisarPedido(){
        for(Pedido p : this.pedidosPend){
            if(p.getTiempoTranscurrido() > Empresa.getTiempoLimite()){
                
            }
        }
    }
    
    public void agregarCamion(String patente, int capacidad){
        camionesDisp.add(new Camion(patente, capacidad));
    }
    
    public void agregarCamionPend(String patente, int capacidad){
        camionesPend.add(new Camion(patente, capacidad));
    }
    
    public List<Mensaje> getMensajesEnBuzon(){
        return new LinkedList<>(this.buzonMensajes);
    }   
}
