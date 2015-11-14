/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import java.util.List;

/**
 *
 * @author carlossalame
 */
public class Cliente extends Usuario implements java.io.Serializable {
    
    private String nombre;
    private String direccion;
    private String telefono;
    
    public Cliente(String nombre, String[] apellidos, String direccion, String telefono, String nombreUsuario, String contrasena){
        super( nombreUsuario, contrasena);
        this.nombre = nombre;
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
    
    
}
