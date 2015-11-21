/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import java.util.LinkedList;

/**
 *
 * @author gianfrancogiglio
 */
public abstract class Usuario implements java.io.Serializable {
    
    String nombreUsuario;
    private String contrasena;
    Sucursal sucActual;

    
    public Usuario(String nombreUsuario, String contrasena){
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        sucActual = null;
    }
    
    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public boolean contrasenaCorrecta(String clave){
        return contrasena.equals(clave);
    }
    
    public boolean cambiarContrasena(String contrasenaAnterior, String nuevaContrasena){
        if (contrasenaCorrecta(contrasenaAnterior)){
            contrasena = nuevaContrasena;
            return true;
        }
        else { return false; }
    }
    
    
    public Pedido crearPed(Sucursal sucDestino, Cliente cliente){
        sucActual.setPedidoAbierto(new Pedido(sucActual, sucDestino, cliente));
        return sucActual.getPedidoAbierto();
    }
    
    public Pedido crearPed(Sucursal sucDestino){
        sucActual.setPedidoAbierto(new Pedido(sucActual, sucDestino, null));
        return sucActual.getPedidoAbierto();
    }
    
    public void agregarEnc(Pedido p, int peso, int volumen, String dirDestino, String desc){
        p.agregarEnc(peso, volumen, dirDestino, desc);
    }
    public void agregarEnc(Pedido p,int peso, int volumen, int prioridad, String dirDestino, String desc){
        p.agregarEnc(peso, volumen, prioridad, dirDestino, desc);
    }
    
    public boolean cerrarPed(){
        return sucActual.cerrarPedido();
    }
    /**
     * @param sucursalActual the sucActual to set
     */
    public void setSucActual(Sucursal sucursalActual) {
        this.sucActual = sucursalActual;
    }
    

    public Sucursal getSucActual() {
        return sucActual;
    }

}
