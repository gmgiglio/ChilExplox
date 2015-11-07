/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;
import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author carlossalame
 */
public class Camion implements java.io.Serializable {
    
    private String patente;
    //Espacio disponible para cargar pedidos [m^3]
    private int espacioDisp;
    private LinkedList<Pedido> pedidosCargados = new LinkedList<>();
    private int capacidad;
    
    public Camion(String patente, int capacidad){
        this.patente = patente;
        this.capacidad = capacidad;
        this.espacioDisp = capacidad;
    }
    
    public LinkedList<Pedido> getPedidos(){
        return this.pedidosCargados;
    }
    
    public int getEspDisp(){
        return this.espacioDisp;
    }
    
    public int getCapacidad(){
        return this.capacidad;
    }
    
    public void cargarPedido(Pedido p){
        p.setEstado(Estado.En_transito);
        pedidosCargados.add(p);
        espacioDisp -= p.getVol();
    }
    
    public void cargarPedidos(LinkedList<Pedido> pedidos){
        for(Pedido p : pedidos){
            cargarPedido(p);
        }
    }
    
    public LinkedList<Pedido> descargarPedios(){
        LinkedList<Pedido> result = new LinkedList<Pedido>(this.pedidosCargados);
        this.pedidosCargados.clear();
        return result;
    }

    /**
     * @return the patente
     */
    public String getPatente() {
        return patente;
    }
    
    
    
}
