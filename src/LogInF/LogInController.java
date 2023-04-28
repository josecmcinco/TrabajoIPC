/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogInF;

import DBAccess.NavegacionDAOException;
import EscogerAvatares.EscogerAvataresController;
import Lanavegacion.Lanavegacion;
import PaginaInicial.PaginaInicialController;
import PaginaPrincipal.PaginaPrincipalController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Navegacion;
import model.User;

/**
 * FXML Controller class
 *
 * @author JACALMAN
 */
public class LogInController implements Initializable {

    @FXML
    private Button botonCrearCuenta;
    @FXML
    private Button botonAcept;
    @FXML
    private Button botonCancel;
    @FXML
    private TextField espacioNickName;
    @FXML
    private Label errorNickName;
    @FXML
    private PasswordField espacioContrasenya;
    @FXML
    private Label errorContrasenya;
    Navegacion navegacion;
    @FXML
    private BorderPane elTodo;
    @FXML
    private VBox cajaFaro;
    @FXML
    private ImageView imagenFaro;
    
    //Vriables creadas:
    public int contador;
    Stage stageAct;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        navegacion = Lanavegacion.getNavegacion();
        // Desactivar errores
        errorNickName.visibleProperty().set(false);
        errorContrasenya.visibleProperty().set(false);
        //Inicialización Imagen
        imagenFaro.setPreserveRatio(true);
        imagenFaro.setSmooth(true);
        imagenFaro.setCache(true);
        //Stage stage = (Stage) imagenFaro.getScene().getWindow();
        
        //CONTROLADORES FOTO
        elTodo.widthProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            EdicionFoto();}});//width

        elTodo.heightProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            EdicionFoto();}});//height
    
    }//Initilaize
    
    
    public void getStage(Stage st){
        stageAct = st;
        stageAct.maximizedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
            EdicionFoto();}});
    }    
        
    public void EdicionFoto(){
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
        Scene scene = new Scene(root,700, 715);
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
        botonCancel.getScene().getWindow().hide();
    }

    @FXML
    private void clickAcept(MouseEvent event) throws IOException, NavegacionDAOException {
        loQueHaceTodo();
    }
        
    private void loQueHaceTodo() throws IOException{
       contador = 0;
       
        if(!navegacion.exitsNickName(espacioNickName.textProperty().getValueSafe())){
            errorNickName.textProperty().setValue("El usuario introducido no existe");
            errorNickName.visibleProperty().set(true);
            errorContrasenya.visibleProperty().set(false);
            espacioNickName.styleProperty().setValue("-fx-background-color: #FCE5E0");
            espacioNickName.textProperty().setValue("");
            espacioContrasenya.styleProperty().setValue("-fx-background-color: #FCE5E0");
            espacioContrasenya.textProperty().setValue("");
            contador++;
        }

        if(contador == 0){
            errorNickName.visibleProperty().set(false);
            espacioNickName.styleProperty().setValue("");
            
            String espUsuario = espacioNickName.textProperty().getValueSafe();
            String espCont = espacioContrasenya.textProperty().getValueSafe();
            
            if( navegacion.loginUser(espUsuario, espCont)== null){
                errorContrasenya.textProperty().setValue("El usuario y la contraseña no coinciden");
                errorContrasenya.visibleProperty().set(true);
                espacioContrasenya.styleProperty().setValue("-fx-background-color: #FCE5E0");
                espacioContrasenya.textProperty().setValue("");
            }
            
            else{
                errorContrasenya.visibleProperty().set(false);
                espacioContrasenya.styleProperty().setValue("");
                espacioContrasenya.textProperty().setValue("");
                espacioNickName.textProperty().setValue("");
                //contador++;
                User usuario = navegacion.loginUser(espUsuario, espCont);
                

        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("/PaginaPrincipal/PaginaPrincipal.fxml"));
        Parent root = loader.load();
        PaginaPrincipalController controladorPersona = loader.<PaginaPrincipalController>getController();
        controladorPersona.obtenerDeLogIn(usuario);
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
        Scene scene = new Scene(root, 700, 400);
        Lanavegacion.iniciarValores();
        
        //2.5 Poner CSS
        String css = this.getClass().getResource("/baseCSS/baseCSS.css").toExternalForm();
        scene.getStylesheets().add(css);
        //======================================================================
        // 3- asiganación de la escena al Stage que recibe el metodo 
        //     - configuracion del stage
        //     - se muestra el stage de manera no modal mediante el metodo show()
        Stage stage = new Stage();
        controladorPersona.getEscenario(stage);
        stage.setScene(scene);
        stage.setTitle("Página inicial: "+ espUsuario);
        stage.show();
        imagenFaro.getScene().getWindow().hide();
        //if(controladorPersona.getCerradoSesion()){botonCancel.getScene().getWindow().hide();}
                }//Segundo if
            }//Primer if
        }//Pulsar loQueHaceTodo

    @FXML
    private void clickCancel(MouseEvent event) throws IOException {
        //botonCancel.getScene().getWindow().hide();
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("/PaginaInicial/PaginaInicial.fxml"));
        Parent root = loader.load();
        PaginaInicialController controladorPersona = loader.<PaginaInicialController>getController();
        Stage stage = new Stage();
        
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
        stage.setTitle("Iniciar Sesión");
        stage.show();
        botonCancel.getScene().getWindow().hide();
    }

    @FXML
    private void clickAcept2(KeyEvent event) throws IOException, NavegacionDAOException {
        if(event.getCode().toString().equals("ENTER")){loQueHaceTodo();}
    }
    
}
