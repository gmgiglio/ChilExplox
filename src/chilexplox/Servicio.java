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
public abstract class Servicio {
    
    private int peso;
    private int volumen;
    private int costoEnvio;
    private int prioridad;
    private Sucursal sucOrigen;
    private Sucursal sucDestino;
    
    
    public Servicio(int peso, int volumen, int costoEnvio, int prioridad, Sucursal sucOrigen, Sucursal sucDestino){
        this.peso=peso;
        this.volumen = volumen;
        this.costoEnvio = costoEnvio;
        this.prioridad = prioridad;
        this.sucOrigen = sucOrigen;
        this.sucDestino = sucDestino;
    }
    
}
