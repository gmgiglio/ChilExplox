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
    
    protected int peso;
    protected int volumen;
    protected int costoEnvio;
    protected int prioridad;
    protected Sucursal sucOrigen;
    protected Sucursal sucDestino;
    
    
    public Servicio(int peso, int volumen, int prioridad, Sucursal sucOrigen, Sucursal sucDestino){
        this.peso=peso;
        this.volumen = volumen;
        this.prioridad = prioridad;
        this.sucOrigen = sucOrigen;
        this.sucDestino = sucDestino;
    }
    
    protected Servicio(Sucursal sucOrigen, Sucursal sucDestino){
        this.sucOrigen = sucOrigen;
        this.sucDestino = sucDestino;
    }
    
    protected int getCostoEnvio(){
        return this.costoEnvio;
    }
    
}
