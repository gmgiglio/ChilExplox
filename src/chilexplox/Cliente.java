/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

/**
 *
 * @author carlossalame
 */
public class Cliente implements java.io.Serializable {
    
    private String nombre;
    private String direccion;
    private String telefono;
    
    public Cliente(String nombre, String direccion, String telefono){
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
    
    
}
