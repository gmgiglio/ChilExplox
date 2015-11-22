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
    
    public void retornarCamion(Camion c){
        
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
