/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import java.util.Date;

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
    
    public RegistroMensaje enviar(Sucursal destino){   
        destino.recibeMensaje(this);
        return new RegistroMensaje(this,destino,new Date());
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
    
    public void agregarParrafo(String parrafo){
        if (mensaje == null) mensaje = "";
        mensaje = mensaje + System.lineSeparator() + System.lineSeparator() + parrafo;
    }
}