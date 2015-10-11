/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author carlossalame
 */
public class Empresa {
    
    
    
    private String nombre;
    private String rut;
    protected List<Sucursal> sucursales = new LinkedList<>();
    //Indica el numero de pedidos que se han ingresado a la empresa. Este número será asignado a cada
    //pedido como su idPedido, único para cada uno de ellos.
    private int nroPedidos = 0;
    //contador que se inicia en 0 y aumentará en una unidad cada vez que se ingrese una encomienda al
    //sistema. Este contador corresponde a la prioridad de cada encomienda por defecto.
    private int nroEncomiendas = 0;
    
    private Empresa() {
    }
    
    public static Empresa getInstance() {
        return EmpresaHolder.INSTANCE;
    }
    
    private static class EmpresaHolder {
        private static final Empresa INSTANCE = new Empresa();
    }
    
    public void setProperties(String nombre, String rut){
        this.nombre = nombre;
        this.rut = rut;
    }
    public void agregarSucursal(String nombre, String ciudad, String direccion){
        this.sucursales.add(new Sucursal(nombre, ciudad, direccion));
    }
    
    public void nuevaEncomienda(){
        nroEncomiendas++;
    }
    
    public void nuevoPedido(){
        this.nroPedidos++;
    }
    
    public int getNroEncomiendas(){
        return nroEncomiendas;
    }
    
    public int getNroPedidos(){
        return this.nroPedidos;
    }
}