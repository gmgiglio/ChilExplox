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
public class Sucursal{
    
    private String nombre;
    private String ciudad;
    private String direccion;
    //Camiones disponibles para asignar pedidos
    private List<Camion> camionesDisp = new LinkedList<>();
    //Camiones pendientes que esperan ser descargados
    private List<Camion> camionesPend = new LinkedList<>();
    //Pedidos pendientes asignados a ningun camion
    private List<Pedido> pedidosPend = new LinkedList<>();
    
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
    
    public void recibirCamion(Camion c){
        camionesPend.add(c);
    }

    public void descargarCamion(){
        camionesPend.remove(0);
    }
}
