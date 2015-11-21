/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

/**
 *
 * @author gianfrancogiglio
 */
public class RegistroCliente {
    
    public Cliente cliente;
    public String contrasena;
    
    
    public RegistroCliente(Cliente cliente, String contrasena){
        this.cliente =cliente;
        this.contrasena=contrasena;
    }
}
