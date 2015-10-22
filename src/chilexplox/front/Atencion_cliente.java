/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox.front;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import static javafx.application.Application.launch;


/**
 *
 * @author gianfrancogiglio
 */

public class Atencion_cliente extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        Parent root = FXMLLoader.load(getClass().getResource("/resources/Inicio.fxml"));
        Scene scene = new Scene(root,1080,980);
        Text nombreText = (Text) scene.lookup("#nombreUsuario");
        nombreText.setText("Carlos Salamé");
        Text sucursalText = (Text) scene.lookup("#nombreSucursal");
        sucursalText.setText("Alcántara") ;
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
    
}