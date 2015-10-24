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
public class Mensaje implements java.io.Serializable {
    String nombre;
    String mensaje;
    Usuario remitente;
    
    public Mensaje(String nombre, String mensaje, Usuario remitente){
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.remitente = remitente;
        
    }
    
    public void enviar(Sucursal destino){
        destino.recibeMensaje(this);
    }
    
    public String getTexto(){
        return mensaje;
    }
}