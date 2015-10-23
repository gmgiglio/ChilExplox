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
    private List<Camion> camionesDisp = new LinkedList<>();
    //Camiones pendientes que esperan ser descargados
    private List<Camion> camionesPend = new LinkedList<>();
    //Pedidos pendientes asignados a ningun camion
    private List<Pedido> pedidosPend = new LinkedList<>();
    //Pedidos que ya se descargaron del camion y que esperan ser "revisados manualmente"
    private List<Pedido> pedidosEnDest = new LinkedList<>();
    //Pedidos confirmados con informacion correcta
    private List<Pedido> pedidosEntregados = new LinkedList<>();
    //Pedidos confirmados con informacion incorrecta
    private List<Pedido> pedidosEquivocados = new LinkedList<>();
    
    private Stack<Mensaje> buzonMensajes;
    
    public Sucursal(String nombre, String ciudad, String direccion){
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.direccion = direccion;
    }
    
    public void agregarPedido(Pedido p){
        
        for(int i = 0; i < this.pedidosPend.size(); i++){
            if(p.prioridad >= this.pedidosPend.get(i).prioridad) this.pedidosPend.add(i, p);
        }
    }
    
    public List<Camion> getCamionesDisp(){
        return this.camionesDisp;
    }
    
    public List<Pedido> getPedidosPend(){
        return this.pedidosPend;
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
            if(p.getTiempoTranscurrido() > Empresa.getInstance().){
                
            }
        }
    }
    
    
}
