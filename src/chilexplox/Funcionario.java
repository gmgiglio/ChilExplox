/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import static javafx.scene.input.KeyCode.I;

/**
 *
 * @author carlossalame
 */
public class Funcionario extends Usuario implements java.io.Serializable {
    
    
   
    private final LinkedList<RegistroMensaje> registroMensajesEnviados = new LinkedList();
    
    public Funcionario(String nombreUsuario, String contrasena){
        super( nombreUsuario, contrasena);
    }
    
    
    
    public void cargarPed(Camion c, int idPed){
        Pedido p = sucActual.getPedidoPendiente(idPed);
        sucActual.cargarPedido(p);
        p.setEstado(Estado.En_transito);
        c.cargarPedido(p);
    }
    
    public void descargarPed(Camion c, int idPed){
        Pedido p = c.getPedidoCargado(idPed);
        sucActual.agregarPedido(p);
        p.setEstado(Estado.En_origen);
        c.descargarPedido(p);
    }
    
    
    //Envia el pedido de mayor urgencia en el primer camion disponible en la sucursal.
    //Si existen pedidos pendientes que se dirigen a la misma sucursal y caben en el camion, estos tambien
    //son despachados.
    
    //true si se logro enviar pedido (siempre que el pedido estubiera abierto y el hay camiones disponibles)
    public boolean enviarPed(Pedido p){
        Camion c = getSucActual().getCamionesDisp().get(0);
        if(!p.getAbierto() && !sucActual.getCamionesDisp().isEmpty()){
            c.cargarPedido(p);
            return true;
        }
        else {
            return false;
        }
    }
    
    public void enviarPedMayorPrioridad(){
        if(getSucActual().getPedidosPend() != null){
            Camion camionACargar = getSucActual().getCamionesDisp().get(0);
            Pedido primerPed = getSucActual().getPedidosPend().get(0);
            Sucursal sucDestino = primerPed.getSucDestino();
            for(Pedido p : getSucActual().getPedidosPend()){
                if(camionACargar.getPedidos() == null){
                    p.setEstado(Estado.En_transito);
                    camionACargar.cargarPedido(p);
                    getSucActual().getPedidosPend().remove(p);
                    sucDestino = p.getSucDestino();
                }
            else{
                if(primerPed.getSucDestino() == p.getSucDestino()
                        && p.getVol() <= camionACargar.getEspDisp()){
                    p.setEstado(Estado.En_transito);
                    camionACargar.cargarPedido(p);
                        getSucActual().getPedidosPend().remove(p);
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
        if(sucActual.getCamionesPend() != null){
            //Pedidos dentro del camion a descargar
            LinkedList<Pedido> pedidosADesc = sucActual.getCamionesPend().get(0).getPedidos();
            Sucursal sucOrigen = pedidosADesc.get(0).getSucOrigen();
            for(Pedido p : pedidosADesc){
                sucActual.bajarPedido(p);
            }
            Camion camionDescargado = getSucActual().getCamionesPend().get(0);
            getSucActual().despacharCamion();
            sucOrigen.recibirCamionDescargado(camionDescargado);
            
        }
    }
    
    public void confirmarPed(int idPedido, boolean correcto){
        LinkedList<Pedido> pedidosEnDest = getSucActual().getPedidosEnDest();
        for(Pedido p : pedidosEnDest){
            if(idPedido == p.getIdPedido()){
                if(correcto){
                    getSucActual().pedidoEntregado(p);
                }
                else{
                    getSucActual().pedidoEquivocado(p);
                }
            }
        }
    }
    
    public void enviarMens(String titulo, String texto, Sucursal sucursal){
        Mensaje mensaje = new Mensaje(titulo, texto, this);
        registroMensajesEnviados.add(mensaje.enviar(sucursal));
    }
    
    public LinkedList<RegistroMensaje> getRegistroMensEnviados(){
        return new LinkedList(registroMensajesEnviados);
    }
    
    public RegistroCliente crearCliente(String nombre, String[] apellidos, String direccion, String telefono){
        return Empresa.agregarCliente(nombre, apellidos, direccion, telefono);
    }
    
    
    
}
