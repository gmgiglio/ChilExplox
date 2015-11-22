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
public class Cliente extends Usuario implements java.io.Serializable {
    
    private String nombre;
    private String[] apellidos;
    private String direccion;
    private String telefono;
    private final LinkedList<Pedido> pedidos = new LinkedList();
    
    public Cliente(String nombre, String[] apellidos, String direccion, String telefono, String nombreUsuario, String contrasena){
        super( nombreUsuario, contrasena);
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
    }
    
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }
    
    public String getNombreCompleto(){
        String result = new String(nombre);
        for(String a : apellidos){
            result+= " " + a;
        }
        return result;
    }
    
    public void agregarPedido(Pedido pedido){
        pedidos.add(pedido);
    }
    
    public List<Pedido> getPedidos(){
        return pedidos;
    }
    
    
}
