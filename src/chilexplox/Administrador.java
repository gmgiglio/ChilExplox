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
public class Administrador extends Funcionario {
    
    public Administrador(String nombreUsuario, String contrasena){
        super( nombreUsuario, contrasena);   
    }
    
    public boolean agregarFuncionario(String nombreUsuario, String contrasena){
        return Empresa.agregarFuncionario(nombreUsuario, contrasena);
    }
    
    public boolean agregarSucursal(String nombre, String ciudad, String direccion){
        return Empresa.agregarSucursal( nombre,  ciudad,  direccion);
    }
    
    public boolean agregarAdministrador(String nombreUsuario, String contrasena){
        return Empresa.agregarFuncionario(new Administrador(nombreUsuario,contrasena));
    }
    
    public void eliminiarSucursal(Sucursal sucursal){
        Empresa.eliminarSucursal(sucursal);
    }
    
    public void eliminarUsuario(Usuario usuario){
        Empresa.eliminarUsuario(usuario);
    }
    
}
