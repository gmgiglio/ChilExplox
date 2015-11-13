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
    
    private static int precioPorGramo = 10;
    private static int precioPorCc = 10;
    private static int precioPorPrioridad = 1000;
    private static int largoMaximoDesc = 20;
    
    //peso y volumen definidos en gramos y cm cubicos
    public Encomienda(int peso, int volumen, double prioridad, String dirDestino, String d){
        super(peso, volumen, prioridad);
        //El costo de envío se calcula sumando mil pesos por kilo y 20000 pesos por metro cubico
        //Podemos juntarnos a discutir el precio si quieren
        costoEnvio = precioPorGramo*peso + precioPorCc*volumen;
        direccionDestino = dirDestino;
        if(d.length() > largoMaximoDesc) descripcion = d.substring(0, largoMaximoDesc);
        else descripcion = d;
    }
    
    public void agregarCostoPrioridad(int prioridadAgregada){
        costoEnvio += precioPorPrioridad*prioridadAgregada;
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
    
    public static int calcularPresupuesto(int peso, int volumen,int prioridad){
        return precioPorGramo*peso + precioPorCc*volumen + precioPorPrioridad*prioridad;
    }
    
}
