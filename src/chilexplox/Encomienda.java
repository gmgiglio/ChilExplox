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
    
    public Encomienda(int peso, int volumen, int prioridad, String dirDestino){
        super(peso, volumen, prioridad);
        //El costo de envío se calcula sumando mil pesos por kilo y 20000 pesos por metro cubico
        //Podemos juntarnos a discutir el precio si quieren
        this.costoEnvio = 1000*peso + 20000*volumen;
        this.direccionDestino = dirDestino;
    }
    
    public void agregarCostoPrioridad(int prioridad){
        this.costoEnvio += 100*prioridad;
    }
    
}
