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
public abstract class Servicio implements java.io.Serializable {
    
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
    
    public int getVol(){
        return this.volumen;
    }
    
    public int getCostoEnvio(){
        return this.costoEnvio;
    }

    /**
     * @return the prioridad
     */
    public double getPrioridad() {
        return prioridad;
    }

    /**
     * @return the peso
     */
    public int getPeso() {
        return peso;
    }
    
    
    
}
