/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;
import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
/**
 *
 * @author carlossalame
 */
public class Sucursal{
    
    private String nombre;
    private String ciudad;
    private String direccion;
    private List<Camion> camionesDisp = new LinkedList<>();
    private List<Pedido> pedidos = new LinkedList<>();
    
    public Sucursal(String nombre, String ciudad, String direccion){
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.direccion = direccion;
    }
    
    public void agregarPedido(Pedido p){
        
        for(int i = 0; i < this.pedidos.size(); i++){
            if(p.prioridad <= this.pedidos.get(i).prioridad) this.pedidos.add(i, p);
        }
    }
    
    
}
