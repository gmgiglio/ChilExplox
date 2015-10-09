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
    
    public void agregarEnc(Pedido p, int peso, int volumen, Sucursal sucOrigen,Sucursal sucDestino,
            String dirDestino){
        p.agregarEnc(peso, volumen, sucOrigen, sucDestino, dirDestino);
    }
    
    public void agregarEnc(Pedido p,int peso, int volumen, int prioridad, Sucursal sucOrigen,
            Sucursal sucDestino, String dirDestino){
        p.agregarEnc(peso, volumen, prioridad, sucOrigen, sucDestino, dirDestino);
    }
    
    //Envia todos los pedidos pendientes posibles segun disponibilidad de camiones
    public void enviarPedidos(Sucursal s){
        
    }
    
    
}
