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
    private LinkedList<Sucursal> sucursales = new LinkedList<>();
    //Indica el numero de pedidos que se han ingresado a la empresa. Este número será asignado a cada
    //pedido como su idPedido, único para cada uno de ellos.
    private int nroPedidos = 0;
    //contador que se inicia en 0 y aumentará en una unidad cada vez que se ingrese una encomienda al
    //sistema. Este contador corresponde a la prioridad de cada encomienda por defecto.
    private int nroEncomiendas = 0;
    
    private LinkedList<Cliente> clientes = new LinkedList<>();
    private LinkedList<Funcionario> usuarios = new LinkedList<>();

    //tiempo limite para mandar para pedidos alta prioridad, 3 dias default
    private long tiempoLimite = 0; //3*24*60*60*1000;
    private int altaPrioridad = 0; //5;
    
    

    
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
         System.out.println("Empresa: Deserializacion exitosa");
         return true;
      }catch(Exception i){
         
         System.out.println("Empresa: no se pudo deserializar");
         return false;
      }
    }
    
    // true si logra serializar
    public static boolean serializar(String Path){
        try
      {
         FileOutputStream fileOut = new FileOutputStream("data/empresa.ser");
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(getInstance());
         out.close();
         fileOut.close();
         System.out.println("Empresa: Serializacion exitosa");
         return true;
      }catch(IOException i)
      {
          System.out.println("Empresa: No se pudo serializar");
          return false;
      }
     
     //basado en tutorial http://www.tutorialspoint.com/java/java_serialization.htm  
    }
    
    public static void setTiempoLimite(long milisegundos){
        getInstance().tiempoLimite = milisegundos;
    }
    
    public static long getTiempoLimite(){
        return getInstance().getITiempoLimite();
    }
    
    private long getITiempoLimite(){
        return tiempoLimite;
    }
    
    
    
    private static Empresa getInstance() {
        return EmpresaHolder.INSTANCE;
    }

    /**
     * @return the sucursales
     */
    public static LinkedList<Sucursal> getSucursales() {
        return new LinkedList<>(getInstance().sucursales);
    }

    /**
     * @return the usuarios
     */
    public static LinkedList<Funcionario> getUsuarios() {
        return new LinkedList<>(getInstance().usuarios);
    }

    /**
     * @return the clientes
     */
    public static LinkedList<Cliente> getClientes() {
        return new LinkedList<>(getInstance().clientes);
    }

    /**
     * @return the altaPrioridad
     */
    public static int getAltaPrioridad() {
        Empresa e = getInstance();
        return getInstance().getIAltaPrioridad();
    }
    
    private int getIAltaPrioridad(){
        return altaPrioridad;
    }

    /**
     * @param altaPrioridad the altaPrioridad to set
     */
    public static void setAltaPrioridad(int altaPrioridad) {
        getInstance().altaPrioridad = altaPrioridad;
    }


    private String getINombre() {
        return nombre;
    }
    
    public static String getNombre(){
        return getInstance().getINombre();
    }


    private String getIRut() {
        return rut;
    }
    
    public static String getRut(){
        return getInstance().getIRut();
    }
    
    private static class EmpresaHolder {
        private static Empresa INSTANCE = new Empresa();
    }
    
    public static void setProperties(String nombre, String rut){
        getInstance().nombre = nombre;
        getInstance().rut = rut;
    }
    
    //retorna true si se pudo agregar ( si no existía otra sucursal con el mismo nombre)
    public static boolean agregarSucursal(String nombre, String ciudad, String direccion){
        for(int i = 0; i < getInstance().sucursales.size(); i++){
            if(getInstance().sucursales.get(i).getNombre().equals(nombre)){
                return false;
            }
        }
        getInstance().sucursales.add(new Sucursal(nombre, ciudad, direccion));
        return true;
    }
    
    public static boolean agregarUsuario(String nombreUsuario, String contrasena){
        for(int i = 0; i <  getInstance().usuarios.size(); i++){
            if( getInstance().usuarios.get(i).getNombreUsuario().equals(nombreUsuario)){
                return false;
            }
        }
         getInstance().usuarios.add(new Funcionario(nombreUsuario, contrasena));
        return true;
    }
    public static boolean agregarCliente(String nombreCliente, String direccion, String telefono){
        for(int i = 0; i <  getInstance().clientes.size(); i++){
            if( getInstance().clientes.get(i).getNombre().equals(nombreCliente)){
                return false;
            }
        }
         getInstance().clientes.add(new Cliente(nombreCliente, direccion, telefono));
        return true;
    }
    public static void nuevaEncomienda(){
         getInstance().nroEncomiendas++;
    }
    
    public static void nuevoPedido(){
         getInstance().nroPedidos++;
    }
    
    public static int getNroEncomiendas(){
        return  getInstance().nroEncomiendas;
    }
    
    public static int getNroPedidos(){
        return  getInstance().nroPedidos;
    }
    
    public static LinkedList<Sucursal> sucursalesEnCiudad(String nombreCiudad){
        LinkedList<Sucursal> result = new LinkedList<>();
        for(int i = 0; i <  getInstance().getSucursales().size(); i++){
            if(getSucursales().get(i).getCiudad().equals(nombreCiudad)){
                result.add( getInstance().getSucursales().get(i));
            }
        }
        
        return result;
    }
    
    public static Sucursal getSucursal(String nombre){
        for(int i = 0; i <  getInstance().sucursales.size(); i++){
            if( getInstance().sucursales.get(i).getNombre().equals(nombre)){
                return  getInstance().sucursales.get(i);
            }
        }
        return null;
    }
    
    public static Cliente getCliente(String nombre, String telefono){
        for(int i = 0; i <  getInstance().clientes.size(); i++){
            if( getInstance().clientes.get(i).getNombre().replace(" ", "").equals(nombre.replace(" ",""))&&getInstance().clientes.get(i).getTelefono().replace(" ","").equals(telefono.replace(" ", ""))){
                return  getInstance().clientes.get(i);
            }
        }
        return null;
    }
    
    public static Funcionario getUsuario(String nombre){
        for (Funcionario u :  getInstance().usuarios){
            if(nombre.equals(u.getNombreUsuario())) return u;
        }
        return null;
    }
}