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
    
    public Pedido(int peso, int volumen, int costoEnvio, int prioridad, Sucursal sucOrigen,
            Sucursal sucDestino, Cliente cliente){
        super(peso, volumen, costoEnvio, prioridad, sucOrigen, sucDestino);
        this.cliente = cliente;
    }
    
    //agregar una encomienda a un pedido con prioridad por orden de llegada
    private void agregarEnc(int peso, int volumen, int costoEnvio, Sucursal sucOrigen,
            Sucursal sucDestino, String dirDestino){
        this.nroEncomienda += 1;
        this.encomiendas.add(new Encomienda(peso, volumen, costoEnvio, this.nroEncomienda, sucOrigen,
                sucDestino, dirDestino));
    }
    
    //asignar encomienda con prioridad indicada por el cliente
    private void agregarEnc(int peso, int volumen, int costoEnvio, int prioridad, Sucursal sucOrigen,
            Sucursal sucDestino, String dirDestino){
        this.encomiendas.add(new Encomienda(peso, volumen, costoEnvio, this.nroEncomienda, sucOrigen,
                sucDestino, dirDestino));
    }
}
