/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;
import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author carlossalame
 */
public class Usuario implements java.io.Serializable {
    
    private String nombreUsuario;
    private String contrasena;
    private Sucursal sucursalActual;
    
    public Usuario(String nombreUsuario, String contrasena){
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        sucursalActual = null;
    }
    
    public void crearPedido(Sucursal sucDestino, Cliente cliente){
        Pedido p = new Pedido(getSucursalActual(), sucDestino, cliente);
        getSucursalActual().agregarPedido(p);
    }
    
    public Pedido crearPedido(Sucursal sucDestino){
        Pedido p = new Pedido(sucursalActual, sucDestino, null);
        sucursalActual.agregarPedido(p);
        return p;
    }
    
    public void agregarEnc(Pedido p, int peso, int volumen, String dirDestino, String desc){
        p.agregarEnc(peso, volumen, dirDestino, desc);
    }
    
    public void agregarEnc(Pedido p,int peso, int volumen, int prioridad, String dirDestino, String desc){
        p.agregarEnc(peso, volumen, prioridad, dirDestino, desc);
    }
    
    //Envia el pedido de mayor urgencia en el primer camion disponible en la sucursal.
    //Si existen pedidos pendientes que se dirigen a la misma sucursal y caben en el camion, estos tambien
    //son despachados.
    
    //true si se logro enviar pedido (siempre que el pedido estubiera abierto y el hay camiones disponibles)
    public boolean enviarPedido(Pedido p){
        Camion c = getSucursalActual().getCamionesDisp().get(0);
        if(!p.getAbierto() && !sucursalActual.getCamionesDisp().isEmpty()){
            c.cargarPedido(p);
            return true;
        }
        else {
            return false;
        }
    }
    
    public void enviarPedidoMayorPrioridad(){
        if(getSucursalActual().getPedidosPend() != null){
            Camion camionACargar = getSucursalActual().getCamionesDisp().get(0);
            Pedido primerPed = getSucursalActual().getPedidosPend().get(0);
            Sucursal sucDestino = primerPed.getSucDestino();
            for(Pedido p : getSucursalActual().getPedidosPend()){
                if(camionACargar.getPedidos() == null){
                    p.setEstado(Estado.En_transito);
                    camionACargar.cargarPedido(p);
                    getSucursalActual().getPedidosPend().remove(p);
                    sucDestino = p.getSucDestino();
                }
            else{
                if(primerPed.getSucDestino() == p.getSucDestino()
                        && p.getVol() <= camionACargar.getEspDisp()){
                    p.setEstado(Estado.En_transito);
                    camionACargar.cargarPedido(p);
                        getSucursalActual().getPedidosPend().remove(p);
                }
            }
        }
        sucDestino.recibirCamionCargado(camionACargar);
        }
        else{
            //Mostrar mensaje notificando que no hay pedidos pendientes
        }
    }
    
    public void recibirPedido(){
        if(getSucursalActual().getCamionesPend() != null){
            //Pedidos dentro del camion a descargar
            List<Pedido> pedidosADesc = getSucursalActual().getCamionesPend().get(0).getPedidos();
            Sucursal sucOrigen = pedidosADesc.get(0).getSucOrigen();
            for(Pedido p : pedidosADesc){
                getSucursalActual().bajarPedido(p);
            }
            Camion camionDescargado = getSucursalActual().getCamionesPend().get(0);
            getSucursalActual().despacharCamion();
            sucOrigen.recibirCamionDescargado(camionDescargado);
            
        }
    }
    
    public void confirmarPedido(int idPedido, boolean correcto){
        List<Pedido> pedidosEnDest = getSucursalActual().getPedidosEnDest();
        for(Pedido p : pedidosEnDest){
            if(idPedido == p.getIdPedido()){
                if(correcto){
                    getSucursalActual().pedidoEntregado(p);
                }
                else{
                    getSucursalActual().pedidoEquivocado(p);
                }
            }
        }
    }
    
    public void enviarMensaje(String titulo, String texto, Sucursal sucursal){
        Mensaje mensaje = new Mensaje(titulo, texto, this);
        mensaje.enviar(sucursal);
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param sucursalActual the sucursalActual to set
     */
    public void setSucursalActual(Sucursal sucursalActual) {
        this.sucursalActual = sucursalActual;
    }

    /**
     * @return the sucursalActual
     */
    public Sucursal getSucursalActual() {
        return sucursalActual;
    }
}
