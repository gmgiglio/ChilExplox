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
    
    private String nombreUsuario;
    private String contrasena;
    private Sucursal sucActual;
    
    public Usuario(String nombreUsuario, String contrasena){
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        sucActual = null;
    }
    
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
    }
    
    
    //Envia el pedido de mayor urgencia en el primer camion disponible en la sucursal.
    //Si existen pedidos pendientes que se dirigen a la misma sucursal y caben en el camion, estos tambien
    //son despachados.
    
    //true si se logro enviar pedido (siempre que el pedido estubiera abierto y el hay camiones disponibles)
    public boolean enviarPed(Pedido p){
        Camion c = getSucActual().getCamionesDisponibles().get(0);
        if(!p.getAbierto() && !sucActual.getCamionesDisponibles().isEmpty()){
            c.cargarPedido(p);
            return true;
        }
        else {
            return false;
        }
    }
    
    public void enviarPedMayorPrioridad(){
        if(getSucActual().getPedidosPendientes() != null){
            Camion camionACargar = getSucActual().getCamionesDisponibles().get(0);
            Pedido primerPed = getSucActual().getPedidosPendientes().get(0);
            Sucursal sucDestino = primerPed.getSucDestino();
            for(Pedido p : getSucActual().getPedidosPendientes()){
                if(camionACargar.getPedidos() == null){
                    p.setEstado(Estado.En_transito);
                    camionACargar.cargarPedido(p);
                    getSucActual().getPedidosPendientes().remove(p);
                    sucDestino = p.getSucDestino();
                }
            else{
                if(primerPed.getSucDestino() == p.getSucDestino()
                        && p.getVol() <= camionACargar.getEspDisp()){
                    p.setEstado(Estado.En_transito);
                    camionACargar.cargarPedido(p);
                        getSucActual().getPedidosPendientes().remove(p);
                }
            }
        }
        sucDestino.recibirCamionCargado(camionACargar);
        }
        else{
            //Mostrar mensaje notificando que no hay pedidos pendientes
        }
    }
    
    public void recibirPed(){
        if(getSucActual().getCamionesPend() != null){
            //Pedidos dentro del camion a descargar
            LinkedList<Pedido> pedidosADesc = getSucActual().getCamionesPend().get(0).getPedidos();
            Sucursal sucOrigen = pedidosADesc.get(0).getSucOrigen();
            for(Pedido p : pedidosADesc){
                getSucActual().bajarPedido(p);
            }
            Camion camionDescargado = getSucActual().getCamionesPend().get(0);
            getSucActual().despacharCamion();
            sucOrigen.recibirCamionDescargado(camionDescargado);
            
        }
    }
    
    public void confirmarPed(int idPedido, boolean correcto){
        Pedido p = getSucActual().getPedidoEnDestino(idPedido);
        if(idPedido == p.getIdPedido()){
            if(correcto)
                getSucActual().pedidoEntregado(p);
            else
                getSucActual().pedidoEquivocado(p);
        }
        
    }
    
//    public void enviarMens(String titulo, String texto, Sucursal sucursal){
//        Mensaje mensaje = new Mensaje(titulo, texto, this);
//        registroMensajesEnviados.add(mensaje.enviar(sucursal));
//    }
//    
//    public LinkedList<RegistroMensaje> getRegistroMensEnviados(){
//        return new LinkedList(registroMensajesEnviados);
//    }
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

    /**
     * @return the sucActual
     */
    public Sucursal getSucActual() {
        return sucActual;
    }
}
