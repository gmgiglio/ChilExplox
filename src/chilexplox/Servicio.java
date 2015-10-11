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
    protected double prioridad;
    
    protected Servicio(){}
    
    protected Servicio(int peso, int volumen, double prioridad){
        this.peso=peso;
        this.volumen = volumen;
        this.prioridad = prioridad;
    }
    
    protected int getVol(){
        return this.volumen;
    }
    
    protected int getCostoEnvio(){
        return this.costoEnvio;
    }
    
    
    
}
