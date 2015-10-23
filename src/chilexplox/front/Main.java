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
    
   

    private static Usuario usuarioActual = Empresa.getInstance().getUsuarios().get(0);

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
    
    @Override
    public void stop(){
       Empresa.serializar("data/empresa.ser");
    }


    
}