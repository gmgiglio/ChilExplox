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
    
    
    private int idPedido;
    private Sucursal sucOrigen;
    private Sucursal sucDestino;
    private List<Encomienda> encomiendas = new LinkedList<>();
    private Cliente cliente;
    int estado;
    
    public Pedido(Sucursal sucOrigen, Sucursal sucDestino, Cliente cliente){
        Empresa.getInstance().nuevoPedido();
        this.idPedido = Empresa.getInstance().getNroPedidos();
        this.sucOrigen = sucOrigen;
        this.sucDestino = sucDestino;
        this.peso = 0;
        this.volumen = 0;
        this.costoEnvio = 0;
        this.prioridad = 0.0;
        this.cliente = cliente;
        this.estado = 0;
    }
    
    public Sucursal getSucDestino(){
        return sucDestino;
    }
    
    //La prioridad del pedido es igual al promedio de las prioridades de todas sus encomiendas
    private void setPrioridad(){
        double pr = 0;
        for(Encomienda e : encomiendas){
            pr += e.prioridad;
        }
        pr /= encomiendas.size();
        prioridad = pr;
    }
    
    //agregar una encomienda a un pedido con prioridad por orden de llegada
    public void agregarEnc(int peso, int volumen, String dirDestino){
        Empresa.getInstance().nuevaEncomienda();
        double prioridad = 1/(Empresa.getInstance().getNroEncomiendas());
        Encomienda e = new Encomienda(peso, volumen, prioridad, dirDestino);
        this.encomiendas.add(e);
        this.peso += peso;
        this.volumen += volumen;
        this.costoEnvio += e.costoEnvio;
        setPrioridad();
        }
  
    //asignar encomienda con prioridadAgregada indicada por el cliente
    public void agregarEnc(int peso, int volumen, int prioridadAgregada, String dirDestino){
        double prioridadPorDefecto = 1/(Empresa.getInstance().getNroEncomiendas());
        double prioridadAsignada = prioridadPorDefecto + prioridadAgregada;
        Encomienda e = new Encomienda(peso, volumen, prioridadAsignada, dirDestino);
        e.agregarCostoPrioridad(prioridadAgregada);
        this.encomiendas.add(e);
        this.peso += peso;
        this.volumen += volumen;
        this.costoEnvio += e.costoEnvio;
        setPrioridad();
    }
    
    public void setEstado(int i){
        estado = i;
    }

    /**
     * @return the sucOrigen
     */
    public Sucursal getSucOrigen() {
        return sucOrigen;
    }

    /**
     * @return the idPedido
     */
    public int getIdPedido() {
        return idPedido;
    }
}
