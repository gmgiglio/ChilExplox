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
    
<<<<<<< HEAD
    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
=======
    public void cargarPed(Camion c, int idPed){
        Pedido p = sucActual.getPedidoPendiente(idPed);
        sucActual.cargarPedido(p);
        p.setEstado(Estado.En_transito);
        c.cargarPedido(p);
    }
    
    public void descargarPed(Camion c, int idPed){
        Pedido p = c.getPedidoCargado(idPed);
        getSucActual().agregarPedido(p);
        p.setEstado(Estado.En_origen);
        c.descargarPedido(p);
>>>>>>> origin/master
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
    
<<<<<<< HEAD
    public void agregarEnc(Pedido p,int peso, int volumen, int prioridad, String dirDestino, String desc){
        p.agregarEnc(peso, volumen, prioridad, dirDestino, desc);
    }
    
    public boolean cerrarPed(){
        return sucActual.cerrarPedido();
    }
    
=======
//    public void enviarMens(String titulo, String texto, Sucursal sucursal){
//        Mensaje mensaje = new Mensaje(titulo, texto, this);
//        registroMensajesEnviados.add(mensaje.enviar(sucursal));
//    }
//    
//    public LinkedList<RegistroMensaje> getRegistroMensEnviados(){
//        return new LinkedList(registroMensajesEnviados);
//    }
>>>>>>> origin/master
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
