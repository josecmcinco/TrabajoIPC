/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejecutable;

import PaginaInicial.PaginaInicialController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Inicializador extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("/PaginaInicial/PaginaInicial.fxml"));
        Parent root = loader.load();
        PaginaInicialController controladorPersona = loader.<PaginaInicialController>getController();
        
        controladorPersona.getStage(stage);
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
        Scene scene = new Scene(root, 700, 400);
        //2.5 Poner CSS
        String css = this.getClass().getResource("/baseCSS/baseCSS.css").toExternalForm();
        scene.getStylesheets().add(css);
        //======================================================================
        // 3- asiganación de la escena al Stage que recibe el metodo 
        //     - configuracion del stage
        //     - se muestra el stage de manera no modal mediante el metodo show()
        stage.setScene(scene);
        stage.setTitle("Bienvenido a Navegati");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
        
        
    public static void main(String[] args) {
        launch(args);
        
    }


    
}
