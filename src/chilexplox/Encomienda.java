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
public class Encomienda extends Servicio{
    
    private String direccionDestino;
    
    public Encomienda(int peso, int volumen, int costoEnvio, int prioridad, Sucursal origen, Sucursal destino,
            String direccionDestino){
        super(peso, volumen, costoEnvio, prioridad, origen, destino);
        this.direccionDestino = direccionDestino;
    }
    
}
