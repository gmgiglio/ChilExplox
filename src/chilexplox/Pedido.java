/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;
import java.util.List;
import java.util.LinkedList;
import java.util.Date;
/**
 *
 * @author carlossalame
 */
public class Pedido extends Servicio implements java.io.Serializable{
    
    
    private int idPedido;
    private Sucursal sucOrigen;
    private Sucursal sucDestino;
    private List<Encomienda> encomiendas = new LinkedList<>();
    private Cliente cliente;
    private Estado estado;
    private boolean abierto; //establece si el pedido esta abierto a seguir agregandole encomiendas
    private Date tiempoCierre; //el momento en el que se cierra el pedido y empieza a correr para temas de prioridad
            
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
        this.estado = Estado.En_origen;
        abierto = true;
        
        
    }
    
    public boolean getAbierto(){ return this.abierto;}
    
    //devuelve true si se pudo cerrar el pedido (si estan asignados los datos del cliente) y false de lo contrario
    public boolean cerrarPedido(){
        if (cliente != null){
            abierto = false;
            tiempoCierre = new Date();
            return true;
        }
        else {
            return false;
        }
    }
    
    public Sucursal getSucDestino(){
        return sucDestino;
    }
    
    //diferencia de tiempo en milisegundos
    public long getTiempoTranscurrido(){
        Date tpoActual = new Date();
        return tpoActual.getTime() - tiempoCierre.getTime();
    }
    
    //La prioridad del pedido es igual al promedio de las prioridades de todas sus encomiendas
    private void setPrioridad(){
        double pr = 0;
        for(Encomienda e : encomiendas){
            pr += e.getPrioridad();
        }
        pr /= encomiendas.size();
        prioridad = pr;
    }
    
    //agregar una encomienda a un pedido con prioridad por orden de llegada
    // true si se logra agregar (si esta aun abierto el pedido) y false de lo contrario)
    public boolean agregarEnc(int peso, int volumen, String dirDestino, String desc){
        if (abierto){
            Empresa.getInstance().nuevaEncomienda();
            double prioridad = 1/(Empresa.getInstance().getNroEncomiendas());
            Encomienda e = new Encomienda(peso, volumen, prioridad, dirDestino, desc);
            this.encomiendas.add(e);
            this.peso += peso;
            this.volumen += volumen;
            this.costoEnvio += e.costoEnvio;
            setPrioridad();
            return true;
            }
        else{
            return false;
        }
    }
  
    //asignar encomienda con prioridadAgregada indicada por el cliente
    // true si se logra agregar (si esta aun abierto el pedido) y false de lo contrario)
    public boolean agregarEnc(int peso, int volumen, int prioridadAgregada, String dirDestino, String desc){
        if (abierto){
            double prioridadPorDefecto = 1/(Empresa.getInstance().getNroEncomiendas());
            double prioridadAsignada = prioridadPorDefecto + prioridadAgregada;
            Encomienda e = new Encomienda(peso, volumen, prioridadAsignada, dirDestino, desc);
            e.agregarCostoPrioridad(prioridadAgregada);
            this.encomiendas.add(e);
            this.peso += peso;
            this.volumen += volumen;
            this.costoEnvio += e.costoEnvio;
            setPrioridad();
            return true;
        }
        else{
            return false;
        }
    }
    
    public void setEstado(Estado e){
        estado = e;
    }
    
    public Estado getEstado(){
        return estado;
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
