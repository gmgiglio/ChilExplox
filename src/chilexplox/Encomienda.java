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
public class Encomienda extends Servicio implements java.io.Serializable{
    
    private String direccionDestino;
    private String descripcion;
    
    //peso y volumen definidos en gramos y cm cubicos
    public Encomienda(int peso, int volumen, double prioridad, String dirDestino, String d){
        super(peso, volumen, prioridad);
        //El costo de envío se calcula sumando mil pesos por kilo y 20000 pesos por metro cubico
        //Podemos juntarnos a discutir el precio si quieren
        costoEnvio = 1000*peso + 20000*volumen;
        direccionDestino = dirDestino;
        descripcion = d;
    }
    
    public void agregarCostoPrioridad(int prioridadAgregada){
        costoEnvio += 1000*prioridadAgregada;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the direccionDestino
     */
    public String getDireccionDestino() {
        return direccionDestino;
    }
    
}
