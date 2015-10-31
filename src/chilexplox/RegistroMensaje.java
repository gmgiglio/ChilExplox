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


public class RegistroMensaje {
    private Mensaje mensaje;
    private Sucursal destino;
    private Date tiempoEnvio;
    
    public RegistroMensaje(Mensaje mensaje, Sucursal destino, Date tiempoEnvio) {
        this.destino = destino;
        this.mensaje = mensaje;
        this.tiempoEnvio = tiempoEnvio;
}

    /**
     * @return the mensaje
     */
    public Mensaje getMensaje() {
        return mensaje;
    }

    /**
     * @return the destino
     */
    public Sucursal getDestino() {
        return destino;
    }

    /**
     * @return the tiempoEnvio
     */
    public Date getTiempoEnvio() {
        return tiempoEnvio;
    }
    
}
