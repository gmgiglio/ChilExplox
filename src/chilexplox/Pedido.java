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
    private LinkedList<Encomienda> encomiendas = new LinkedList<>();
    private Cliente cliente;
    private Estado estado;
    private boolean abierto; //establece si el pedido esta abierto a seguir agregandole encomiendas
    private Date tiempoCierre; //el momento en el que se cierra el pedido y empieza a correr para temas de prioridad
    private Tipo tipo;
    
    public Pedido(Sucursal sucOrigen, Sucursal sucDestino, Cliente cliente, Tipo tipo){
        super(0, 0, 0.0);
        this.idPedido = Empresa.getNroPedidos();
        this.sucOrigen = sucOrigen;
        this.sucDestino = sucDestino;
        this.costoEnvio = 0;
        this.volumen = 0;
        this.cliente = cliente;
        this.estado = Estado.En_origen;
        abierto = true;
        this.tipo = tipo;
        
    }
    
    public boolean getAbierto(){ return this.abierto;}
    
    //devuelve true si se pudo cerrar el pedido (si estan asignados los datos del cliente y hay al menos una encomienda) y false de lo contrario
    public boolean cerrarPedido(){
        if (getCliente() != null && !encomiendas.isEmpty()){
            abierto = false;
            tiempoCierre = new Date();
            Empresa.nuevoPedido();
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
        for(Encomienda e : getEncomiendas()){
            pr += e.getPrioridad();
        }
        pr /= getEncomiendas().size();
        prioridad = pr;
    }
    
    //agregar una encomienda a un pedido con prioridad por orden de llegada
    // true si se logra agregar (si esta aun abierto el pedido) y false de lo contrario)
    public boolean agregarEnc(int peso, int volumen, String dirDestino, String desc){
        if (abierto){
            Empresa.nuevaEncomienda();
            double prioridad = 1/(Empresa.getNroEncomiendas());
            Encomienda e = new Encomienda(peso, volumen, prioridad, dirDestino, desc);
            encomiendas.add(e);
            this.peso += peso;
            this.volumen += volumen;
            this.costoEnvio += e.getCostoEnvio();
            setPrioridad();
            return true;
            }
        else{
            return false;
        }
    }
    
    public boolean agregarEnc(Encomienda encomienda){
        if(abierto){
            encomiendas.add(encomienda);
            this.peso += encomienda.getPeso();
            this.volumen += encomienda.getVol();
            this.costoEnvio += encomienda.getCostoEnvio();
            setPrioridad();
            
            return true;
        }
        else return false;
        
    }
  
    //asignar encomienda con prioridadAgregada indicada por el cliente
    // true si se logra agregar (si esta aun abierto el pedido) y false de lo contrario)
    public boolean agregarEnc(int peso, int volumen, int prioridadAgregada, String dirDestino, String desc){
        if (abierto){
            double prioridadPorDefecto;
            if(Empresa.getNroEncomiendas() == 0) prioridadPorDefecto = 1;
            else prioridadPorDefecto = 1/(Empresa.getNroEncomiendas());
            double prioridadAsignada = prioridadPorDefecto + prioridadAgregada;
            Encomienda e = new Encomienda(peso, volumen, prioridadAsignada, dirDestino, desc);
            e.agregarCostoPrioridad(prioridadAgregada);
            encomiendas.add(e);
            this.peso += peso;
            this.volumen += volumen;
            this.costoEnvio += e.getCostoEnvio();
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

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(String nombre) {
        for(Cliente c : Empresa.getClientes()){
            if(c.getNombre() == nombre){
                cliente = c;
            }
        }
    }

    /**
     * @return the encomiendas
     */
    public LinkedList<Encomienda> getEncomiendas() {
        return new LinkedList(encomiendas);
    }
    
    public boolean eliminarEncomienda(Encomienda enc){
        if(abierto){
            encomiendas.remove(enc);
            this.peso -= enc.getPeso();
            this.volumen -= enc.getVol();
            this.costoEnvio -= enc.getCostoEnvio();
            setPrioridad();
            return true;
        }
        else return false;
    }
    
    
}
