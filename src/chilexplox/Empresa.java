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
    
    public Empresa(String nombre, String rut){
        this.nombre = nombre;
        this.rut = rut;
    }
    
}
