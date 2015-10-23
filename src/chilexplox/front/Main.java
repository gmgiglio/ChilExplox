/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox.front;
import chilexplox.*;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.WindowEvent;


/**
 *
 * @author gianfrancogiglio
 */

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{
       
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
    
<<<<<<< HEAD
    public static Sucursal getSucursalActual(String nombreSucursal){
            Sucursal sucActual = null;
            for(Sucursal s : Empresa.getInstance().getSucursales()){
                if(nombreSucursal == s.getNombre()){
                    sucActual = s;
                }
            }
            return sucActual;
    }
=======
    @Override
    public void stop(){
       Empresa.serializar("data/empresa.ser");
    }

>>>>>>> 23f0605c76ee99c955652d2c571645034c254a4a
    
}