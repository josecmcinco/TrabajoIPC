/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PaginaInicial;

import DBAccess.NavegacionDAOException;
import Lanavegacion.Lanavegacion;
import LogInF.LogInController;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Answer;
import model.Navegacion;
import model.Problem;

/**
 * FXML Controller class
 *
 * @author JACALMAN
 */
public class PaginaInicialController implements Initializable {

    @FXML
    private Button botonCrearCuenta;
    @FXML
    private Button botonIniciarSesion;
    @FXML
    private Label errorContrasenya;
    @FXML
    private Button botonSalida;
    @FXML
    private ImageView gifBrujula;
    @FXML
    private Label errorContrasenya1;
    @FXML
    private ImageView imagenFaro;
    @FXML
    private BorderPane elTodo;
    @FXML
    private VBox cajaFaro;
    @FXML
    private BorderPane zonaBotones;
    @FXML
    private VBox margenDerecho;
    Navegacion navegador;
    
    Stage stageAct;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
        
            Lanavegacion.setNavegacion();
        } catch (NavegacionDAOException ex) {
            Logger.getLogger(PaginaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
              
        navegador = Lanavegacion.getNavegacion();
        String direccionGif = System.getProperty("user.dir")+File.separator+"src"+File.separator+"icons"+File.separator+"brujula.gif";
        Image i = new Image(new File(direccionGif).toURI().toString());
        gifBrujula.setImage(i);
        imagenFaro.setPreserveRatio(true);
        imagenFaro.setSmooth(true);
        imagenFaro.setCache(true);
        
        elTodo.widthProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            EdicionFoto();}});//width
        
        
        elTodo.heightProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            EdicionFoto();}});
    }//Initialize
    
    public void getStage(Stage st){
        stageAct = st;
        st.maximizedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
            EdicionFoto();
            }});
        
    }
    
    public void EdicionFoto(){
        //EXTRA: EDICIÓN MARGEN IZQUIERDO
        margenDerecho.prefWidthProperty().bind(elTodo.widthProperty().multiply(0.08333));
        
        imagenFaro.setPreserveRatio(false);
        int h = elTodo.heightProperty().intValue();
        int w = elTodo.widthProperty().intValue();
        double he = imagenFaro.getFitHeight();
        double wi = imagenFaro.getFitWidth();
            
            //0.658 es APROX. la proporción WFaro/HFaro
            //0.278 es la proporción WFaro/WTodo
            if((imagenFaro.getFitWidth()  / cajaFaro.heightProperty().doubleValue() < 0.658)){
                imagenFaro.setFitWidth(w * 0.278);
                imagenFaro.setFitHeight((he*imagenFaro.getFitWidth())/wi);
                        }
            // -100 por la cabecera de la escena
            else{
            imagenFaro.setFitHeight(h - 100);
            imagenFaro.setFitWidth((wi*imagenFaro.getFitHeight())/he);
            
           }
    }
    
    @FXML
    private void clickCrearCuenta(MouseEvent event) throws IOException {
        
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("/CrearCuenta/CrearCuenta.fxml"));
        Parent root = loader.load();
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
        Scene scene = new Scene(root, 700, 715);
        //========================================
        //2.5 Poner CSS
        String css = this.getClass().getResource("/baseCSS/baseCSS.css").toExternalForm();
        scene.getStylesheets().add(css);
        //======================================================================
        // 3- asiganación de la escena al Stage que recibe el metodo 
        //     - configuracion del stage
        //     - se muestra el stage de manera no modal mediante el metodo show()
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Crear Cuenta");
        stage.show();
        imagenFaro.getScene().getWindow().hide();
    }


    
    @FXML
    private void salirApp(ActionEvent event) throws NavegacionDAOException {       
       
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Salir de la aplicación");
        alerta.setHeaderText("");
        alerta.setContentText("Vas a salir de la aplicación ¿Seguro que quieres continuar?");
        
        Optional<ButtonType> result = alerta.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            //System.out.println("OK");
            //alerta.close();
            //miescena.getWindow().hide();
            Platform.exit();
        } else{
            //System.out.println("CANCEL");
        }
    }

    @FXML
    private void iniciarSesion(ActionEvent event) throws IOException {
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("/LogInF/LogIn.fxml"));
        Parent root = loader.load();
        LogInController controladorPersona = loader.<LogInController>getController();
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
        Stage stage = new Stage();
        controladorPersona.getStage(stage);
        stage.setScene(scene);
        stage.setTitle("Crear Cuenta");
        stage.show();
        imagenFaro.getScene().getWindow().hide();
    }

    
}