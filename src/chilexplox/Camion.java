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
public class Camion {
    
    private String patente;
    //Espacio disponible para cargar pedidos [m^3]
    private int espacioDisp;
    private int estado;
    private List<Pedido> pedidosCargados = new LinkedList<>();
    
    public Camion(String patente, int capacidad, int estado){
        this.patente = patente;
        this.espacioDisp = capacidad;
        this.estado = estado;
    }
    
    public List<Pedido> getPedidos(){
        return this.pedidosCargados;
    }
    
    public int getEspDisp(){
        return this.espacioDisp;
    }
    
    public void cargarPedido(Pedido p){
        this.pedidosCargados.add(p);
        this.espacioDisp -= p.getVol();
    }
    
    
    
}
