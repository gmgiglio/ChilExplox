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
    private String nombre;
    String mensaje;
    private Usuario remitente;
    
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

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the remitente
     */
    public Usuario getRemitente() {
        return remitente;
    }
    
    
}