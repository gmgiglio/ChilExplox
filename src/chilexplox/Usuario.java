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
public class Usuario {
    
    private String nombreUsuario;
    private String contrasena;
    
    public Usuario(String nombreUsuario, String contrasena){
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }
    
    public void crearPedido(Sucursal sucOrigen, Sucursal sucDestino, Cliente cliente){
        Pedido p = new Pedido(sucOrigen, sucDestino, cliente);
        sucOrigen.agregarPedido(p);
    }
    
    public Pedido crearPedido(Sucursal sucOrigen, Sucursal sucDestino){
        Pedido p = new Pedido(sucOrigen, sucDestino, null);
        sucOrigen.agregarPedido(p);
        return p;
    }
    
    public void agregarEnc(Pedido p, int peso, int volumen, String dirDestino){
        p.agregarEnc(peso, volumen, dirDestino);
    }
    
    public void agregarEnc(Pedido p,int peso, int volumen, int prioridad, String dirDestino){
        p.agregarEnc(peso, volumen, prioridad, dirDestino);
    }
    
    //Envia el pedido de mayor urgencia en el primer camion disponible en la sucursal.
    //Si existen pedidos pendientes que se dirigen a la misma sucursal y caben en el camion, estos tambien
    //son despachados.
    
    //true si se logro enviar pedido (siempre que el pedido estubiera abierto y el hay camiones disponibles)
    public boolean enviarPedido(Pedido p){
        Sucursal origen = p.getSucOrigen();
        Camion c = origen.getCamionesDisp().get(0);
        if(!p.getAbierto() && !origen.getCamionesDisp().isEmpty()){
            c.cargarPedido(p);
            return true;
        }
        else {
            return false;
        }
    }
    
    public void enviarPedidoMayorPrioridad(Sucursal s){
        if(s.getPedidosPend() != null){
        Camion camionACargar = s.getCamionesDisp().get(0);
        Pedido primerPed = s.getPedidosPend().get(0);
        Sucursal sucDestino = primerPed.getSucDestino();
        for(Pedido p : s.getPedidosPend()){
            if(camionACargar.getPedidos() == null){
                p.setEstado(Estado.En_transito);
                camionACargar.cargarPedido(p);
                s.getPedidosPend().remove(p);
                sucDestino = p.getSucDestino();
            }
            else{
                if(primerPed.getSucDestino() == p.getSucDestino()
                        && p.getVol() <= camionACargar.getEspDisp()){
                    p.setEstado(Estado.En_transito);
                    camionACargar.cargarPedido(p);
                    s.getPedidosPend().remove(p);
                }
            }
        }
        sucDestino.recibirCamionCargado(camionACargar);
        }
        else{
            //Mostrar mensaje notificando que no hay pedidos pendientes
        }
    }
    
    public void recibirPedido(Sucursal sucDestino){
        if(sucDestino.getCamionesPend() != null){
            //Pedidos dentro del camion a descargar
            List<Pedido> pedidosADesc = sucDestino.getCamionesPend().get(0).getPedidos();
            Sucursal sucOrigen = pedidosADesc.get(0).getSucOrigen();
            for(Pedido p : pedidosADesc){
                sucDestino.bajarPedido(p);
            }
            Camion camionDescargado = sucDestino.getCamionesPend().get(0);
            sucDestino.despacharCamion();
            sucOrigen.recibirCamionDescargado(camionDescargado);
            
        }
    }
    
    public void confirmarPedido(Sucursal s, int idPedido, boolean correcto){
        List<Pedido> pedidosEnDest = s.getPedidosEnDest();
        for(Pedido p : pedidosEnDest){
            if(idPedido == p.getIdPedido()){
                if(correcto){
                    s.pedidoEntregado(p);
                }
                else{
                    s.pedidoEquivocado(p);
                }
            }
        }
    }
    
    public void enviarMensaje(String titulo, String texto, Sucursal sucursal){
        Mensaje mensaje = new Mensaje(titulo, texto, this);
        mensaje.enviar(sucursal);
    }
}
