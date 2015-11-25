/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox.front;
import chilexplox.*;
import controllers.ElegirUsuario;
import java.awt.geom.Rectangle2D;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Screen;



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
    
    private static Stage escenarioPrincipal;
    private static Scene escenaElegirUsuario;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        inicioPrueba(); //CAMBIAR A INICIO
        
  
        ElegirUsuario pantallaElegirUsuario = new ElegirUsuario();
        
        pantallaElegirUsuario.setHandlerUsuarioElegido((EventHandler) (Event event) -> {
            usuarioActual = pantallaElegirUsuario.getUsuario();
            try{    
                if (usuarioActual instanceof Cliente){
                    primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/resources/MenuPrincipal.fxml"))));
                }
                else{
                    primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/resources/Inicio.fxml"))));


                    javafx.geometry.Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                    primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                    primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
                }
            }catch(Exception e){ 
                throw new RuntimeException(e);
                      }
            
        });
        
        Parent root = pantallaElegirUsuario;
        escenaElegirUsuario = new Scene(root,400,269);
        primaryStage.setTitle("ChilExplox");
        primaryStage.setScene(escenaElegirUsuario);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/resources/images/pedidoIcon.png")));
      
        primaryStage.setResizable(false);
        primaryStage.show();
        escenarioPrincipal = primaryStage;
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
       
    }
    

    @Override
    public void stop(){
       cerrarSesion();
       Empresa.serializar("data/empresa.ser");
    }
    
    public static void poblar(){
        Empresa.agregarSucursal("Maipu", "Santiago", "Amapolas 1122");
        Empresa.agregarSucursal("Las Condes", "Santiago", "Apoquindo 5000");
        Empresa.agregarSucursal("Victoria", "Temuco", "Bernardo Ohiggins 4256");
        String[] apellidos = {"Silva", "Lopez"};
        Empresa.agregarCliente("Leo",apellidos, "Las Raíces 1172", "7778899");
        Empresa.agregarFuncionario("Karl Saleam","112233445");
        
        Cliente leo = Empresa.getClientes().get(0);
        Sucursal maipu = Empresa.getSucursal("Maipu");
        Sucursal victoria = Empresa.getSucursal("Victoria");
        
        Empresa.agregarFuncionario("Tulio Triviño", "31minutos");
        Empresa.agregarFuncionario("a" , "a");
        Empresa.agregarFuncionario(new Administrador("b","b"));
        String[] ap = {"melo"};
        RegistroCliente rc = Empresa.agregarCliente("pablo", ap,"asda", "123");
        System.out.println(rc.contrasena);
        
        maipu.agregarCamion("BDGH30", 3000, Tipo.Normal);
        maipu.agregarCamion("BDGH31", 3000, Tipo.Normal);
        maipu.agregarCamion("BDGH32", 3000, Tipo.Fragil);
        maipu.agregarCamion("BDGH33", 3000, Tipo.Fragil);
        maipu.agregarCamion("BDGH34", 3000, Tipo.Animales);
        maipu.agregarCamion("BDGH35", 3000, Tipo.Animales);
        maipu.agregarCamion("BDGH36", 3000, Tipo.Blindado);
        maipu.agregarCamion("BDGH37", 3000, Tipo.Blindado);
        maipu.agregarCamion("BDGH38", 3000, Tipo.Radioactivo);
        maipu.agregarCamion("BDGH39", 3000, Tipo.Radioactivo);
        maipu.agregarCamion("BDGH40", 3000, Tipo.Refrigerado);
        maipu.agregarCamion("BDGH41", 3000, Tipo.Refrigerado);
        maipu.agregarCamionPend("JUHK87", 2500, Tipo.Normal);
        maipu.getCamionesDisponibles().get(0).cargarPedido(new Pedido(maipu, victoria, leo, Tipo.Normal));
        Funcionario tulio = Empresa.getFuncionarios().get(0);
        tulio.setSucActual(maipu);
        
        Funcionario a = (Funcionario) Empresa.getUsuario("a");
        a.enviarMens("prueba", "esto es una prueba", victoria);
        a.enviarMens("hola", "te queria mandar saludos", victoria);
        a.enviarMens("Banana Split", "Esto es un banana split", victoria);
        
        Empresa.setProperties("ChilExplox", "70.306.847-0");
        
    }
    
    
    public static void inicioPrueba(){
        if(!Empresa.deserializar("data/empresa.ser")){
            poblar();
        }
        revisarTpoPedidos();
        //enviarReporte();
    }
    
    public static void inicio(){
        Empresa.deserializar("data/empresa.ser");
        revisarTpoPedidos();
    }
    
    public static void cerrarSesion(){
        escenarioPrincipal.setScene(escenaElegirUsuario);
        //usuarioActual.getSucActual().setPedidoAbierto(null);
        usuarioActual = null;
    }
    
    //en todas las sucursales
    public static void revisarTpoPedidos(){
        for(Sucursal s : Empresa.getSucursales()){
            s.revisarTiempoPedidos();
        }
    }
    
    public static void enviarReporte(){
        Mensaje m = GeneradorReporte.generarReporte();
        m.enviar(usuarioActual.getSucActual());
    }
        

}