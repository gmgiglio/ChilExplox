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
    private List<Sucursal> sucursales = new LinkedList<>();
    private int nroPedidos = 0;
    
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
}