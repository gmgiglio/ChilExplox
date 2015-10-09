/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;
import java.util.List;
import java.util.LinkedList;
/**
 *
 * @author carlossalame
 */
public class Pedido extends Servicio{
    
    private List<Encomienda> encomiendas = new LinkedList<>();
    private Cliente cliente;
    //contador que se inicia en 0 y aumentará en una unidad cada vez que se ingrese una encomienda al
    //sistema. Este contador corresponde a la prioridad de cada encomienda por defecto.
    private int nroEncomienda = 0;
    private int idPedido;
    
    
    public Pedido(Sucursal sucOrigen, Sucursal sucDestino, Cliente cliente){
        super(sucOrigen, sucDestino);
        this.peso = 0;
        this.volumen = 0;
        this.costoEnvio = 0;
        this.prioridad = 0;
        this.cliente = cliente;
    }
    
    //La prioridad del pedido es igual al promedio de las prioridades de todas sus encomiendas
    private void setPrioridad(){
        int pr = 0;
        for(Encomienda e : this.encomiendas){
            pr += e.prioridad;
        }
        pr /= this.encomiendas.size();
        this.prioridad = pr;
    }
    
    //agregar una encomienda a un pedido con prioridad por orden de llegada
    public void agregarEnc(int peso, int volumen, Sucursal sucOrigen,Sucursal sucDestino,
            String dirDestino){
        this.nroEncomienda++;
        Encomienda e = new Encomienda(peso, volumen, this.nroEncomienda, sucOrigen,sucDestino, dirDestino);
        this.encomiendas.add(e);
        this.peso += peso;
        this.volumen += volumen;
        this.costoEnvio += e.costoEnvio;
        setPrioridad();
        }
  
    //asignar encomienda con prioridad indicada por el cliente
    public void agregarEnc(int peso, int volumen, int prioridad, Sucursal sucOrigen,
            Sucursal sucDestino, String dirDestino){
        int prioridadAsignada = this.nroEncomienda + 100*prioridad;
        Encomienda e = new Encomienda(peso, volumen, prioridadAsignada, sucOrigen,sucDestino, dirDestino);
        e.agregarCostoPrioridad(prioridad);
        this.encomiendas.add(e);
        this.peso += peso;
        this.volumen += volumen;
        this.costoEnvio += e.costoEnvio;
        setPrioridad();
    }
}
