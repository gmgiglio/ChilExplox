/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;
import java.util.LinkedList;
import java.util.List;
import java.io.*;

/**
 *
 * @author carlossalame
 */
public class Empresa implements java.io.Serializable {
    
    
    
    private String nombre;
    private String rut;
    private List<Sucursal> sucursales = new LinkedList<>();
    //Indica el numero de pedidos que se han ingresado a la empresa. Este número será asignado a cada
    //pedido como su idPedido, único para cada uno de ellos.
    private int nroPedidos = 0;
    //contador que se inicia en 0 y aumentará en una unidad cada vez que se ingrese una encomienda al
    //sistema. Este contador corresponde a la prioridad de cada encomienda por defecto.
    private int nroEncomiendas = 0;
    
    private List<Cliente> clientes = new LinkedList<>();
    private List<Usuario> usuarios = new LinkedList<>();

    //tiempo limite para mandar para pedidos alta prioridad, 3 dias default
    private long tiempoLimite = 3*24*60*60*1000; 

    
    private Empresa() {
    }
    
    // true si logra deserializar
    public static boolean deserializar(String path){
        try
      {
         FileInputStream fileIn = new FileInputStream(path);
         ObjectInputStream in = new ObjectInputStream(fileIn);
         EmpresaHolder.INSTANCE = (Empresa) in.readObject();
         in.close();
         fileIn.close();
         return true;
      }catch(IOException i)
      {
         i.printStackTrace();
         return false;
      }catch(ClassNotFoundException c)
      {
         c.printStackTrace();
         return false;
      }
    }
    
    // true si logra serializar
    public static boolean serializar(String Path){
        try
      {
         FileOutputStream fileOut = new FileOutputStream("data/empresa.ser");
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(Empresa.getInstance());
         out.close();
         fileOut.close();
         return true;
      }catch(IOException i)
      {
          i.printStackTrace();
          return false;
      }
        
    }
    
    public void setTiempoLimite(long milisegundos){
        tiempoLimite = milisegundos;
    }
    
    public long getTiempoLimite(){
        return tiempoLimite;
    }
    
    //basado en tutorial http://www.tutorialspoint.com/java/java_serialization.htm
    
    public static Empresa getInstance() {
        return EmpresaHolder.INSTANCE;
    }

    /**
     * @return the sucursales
     */
    public List<Sucursal> getSucursales() {
        return new LinkedList<>(sucursales);
    }

    /**
     * @return the usuarios
     */
    public List<Usuario> getUsuarios() {
        return new LinkedList<>(usuarios);
    }

    /**
     * @return the clientes
     */
    public List<Cliente> getClientes() {
        return new LinkedList<>(clientes);
    }
    
    private static class EmpresaHolder {
        private static Empresa INSTANCE = new Empresa();
    }
    
    public void setProperties(String nombre, String rut){
        this.nombre = nombre;
        this.rut = rut;
    }
    
    //retorna true si se pudo agregar ( si no existía otra sucursal con el mismo nombre)
    public boolean agregarSucursal(String nombre, String ciudad, String direccion){
        for(int i = 0; i < sucursales.size(); i++){
            if(sucursales.get(i).getNombre().equals(nombre)){
                return false;
            }
        }
        sucursales.add(new Sucursal(nombre, ciudad, direccion));
        return true;
    }
    
    public boolean agregarUsuario(String nombreUsuario, String contrasena){
        for(int i = 0; i < usuarios.size(); i++){
            if(usuarios.get(i).getNombreUsuario().equals(nombreUsuario)){
                return false;
            }
        }
        usuarios.add(new Usuario(nombreUsuario, contrasena));
        return true;
    }
    public boolean agregarCliente(String nombreCliente, String direccion, String telefono){
        for(int i = 0; i < clientes.size(); i++){
            if(clientes.get(i).getNombre().equals(nombreCliente)){
                return false;
            }
        }
        clientes.add(new Cliente(nombreCliente, direccion, telefono));
        return true;
    }
    public void nuevaEncomienda(){
        nroEncomiendas++;
    }
    
    public void nuevoPedido(){
        this.nroPedidos++;
    }
    
    public int getNroEncomiendas(){
        return nroEncomiendas;
    }
    
    public int getNroPedidos(){
        return this.nroPedidos;
    }
    
    public List<Sucursal> sucursalesEnCiudad(String nombreCiudad){
        List<Sucursal> result = new LinkedList<>();
        for(int i = 0; i < this.getSucursales().size(); i++){
            if(getSucursales().get(i).getCiudad().equals(nombreCiudad)){
                result.add(this.getSucursales().get(i));
            }
        }
        
        return result;
    }
    
    public Sucursal getSucursal(String nombre){
        for(int i = 0; i < this.getSucursales().size(); i++){
            if(getSucursales().get(i).getNombre().equals(nombre)){
                return getSucursales().get(i);
            }
        }
        return null;
    }
}