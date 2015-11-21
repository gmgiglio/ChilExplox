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
    private EstadoCamion estado;
    
    public Camion(String patente, int capacidad){
        this.patente = patente;
        this.capacidad = capacidad;
        this.espacioDisp = capacidad;
        estado = EstadoCamion.Sin_Errores;
    }
    
    public boolean verificaEspacioDestino(Sucursal s,int idPedido){
        Pedido p = s.getPedidoPendiente(idPedido);
        if(pedidosCargados.size()>0){
            if(p.getSucDestino() != pedidosCargados.get(0).getSucDestino() ||
                    espacioDisp < p.getVol()){
                return false;
            }
        }
        return true;
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
        pedidosCargados.add(p);
        espacioDisp -= p.getVol();
    }
    
    public void descargarPedido(Pedido p){
        pedidosCargados.remove(p);
        espacioDisp += p.getVol();
    }
    
    public void cargarPedidos(LinkedList<Pedido> pedidos){
        for(Pedido p : pedidos){
            cargarPedido(p);
        }
    }
    
    public LinkedList<Pedido> descargarPedidos(){
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
    
    public Pedido getPedidoCargado(Integer id){
        for(int i=0; i<pedidosCargados.size();i++)
        {
            if(pedidosCargados.get(i).getIdPedido()==id)
                return pedidosCargados.get(i);
        }
        
        return null;
        
    }

    /**
     * @return the estado
     */
    public EstadoCamion getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoCamion estado) {
        this.estado = estado;
    }
    
}
