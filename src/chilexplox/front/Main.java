/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox.front;
import chilexplox.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;



/**
 *
 * @author gianfrancogiglio
 */

public class Main extends Application {
       

    private static Usuario usuarioActual; 

    /**
     * @return the usuarioActual
     */
    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * @param aUsuarioActual the usuarioActual to set
     */
    public static void setUsuarioActual(Usuario aUsuarioActual) {
        usuarioActual = aUsuarioActual;
    }
 

    public void start(Stage primaryStage) throws Exception{
        
        Empresa.getInstance().agregarUsuario("hugo", "asasasdasd");
        
        
        usuarioActual = Empresa.getInstance().getUsuarios().get(0);
       
        Parent root = FXMLLoader.load(getClass().getResource("/resources/Inicio.fxml"));
        Scene scene = new Scene(root,1080,615);
        primaryStage.setTitle("ChilExplox");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
       
    }
    

    @Override
    public void stop(){
       Empresa.serializar("data/empresa.ser");
    }
    
    public static void poblar(){
        Empresa.getInstance().agregarSucursal("Maipu", "Santiago", "Amapolas 1122");
        Empresa.getInstance().agregarSucursal("Las Condes", "Santiago", "Apoquindo 5000");
        Empresa.getInstance().agregarSucursal("Victoria", "Temuco", "Bernardo Ohiggins 4256");
        Empresa.getInstance().agregarCliente("Leo", "Las Raíces 1172", "7778899");
        Empresa.getInstance().agregarUsuario("Karl Saleam","112233445");
        
        Cliente leo = Empresa.getInstance().getClientes().get(0);
        Sucursal maipu = Empresa.getInstance().getSucursal("Maipu");
        Sucursal victoria = Empresa.getInstance().getSucursal("Victoria");
        Empresa.getInstance().agregarUsuario("Tulio Triviño", "31minutos");
        maipu.agregarCamion("BDGH34", 3000);
        maipu.agregarCamionPend("JUHK87", 2500);
        maipu.getCamionesDisp().get(0).cargarPedido(new Pedido(maipu, victoria, leo));
        Usuario tulio = Empresa.getInstance().getUsuarios().get(0);
        tulio.setSucursalActual(maipu);
        tulio.crearPedido(victoria);
        Main.getUsuarioActual().enviarMensaje("prueba", "esto es una prueba", victoria);
        Main.getUsuarioActual().enviarMensaje("hola", "te queria mandar saludos", victoria);
        Main.getUsuarioActual().enviarMensaje("Banana Split", "Esto es un banana split", victoria);
    }
    
    //true si se logro
    public static boolean deserializar(String path){
        
        try{
            Empresa.deserializar(path);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    
    public static void inicioPrueba(){
        if(!deserializar("data/empresa.ser")){
            poblar();
        }
    }
    
    //por implementar
    public static void inicio(){
        
    }

}