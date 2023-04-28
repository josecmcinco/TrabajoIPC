/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PaginaPrincipal;

import DBAccess.NavegacionDAOException;
import Lanavegacion.Lanavegacion;
import PaginaInicial.PaginaInicialController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import Utils.Utils;
import editarDatos.EditarDatosController;
import ejercicio.EjercicioController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import model.Navegacion;
import model.Problem;
import model.Session;
import model.User;
import seleccEjercicio.SeleccEjercicioController;
import verEstadisticas.VerEstadisticasController;

/**
 * FXML Controller class
 *
 * @author JACALMAN
 */
public class PaginaPrincipalController implements Initializable {

    private Button botonCancel;
    
    User usuario;
    @FXML
    private Text titulo;
    @FXML
    private ImageView imagenUsuario;
    @FXML
    private Button botonCerrarSesion;
    @FXML
    private Button botonEjercicioAleatorio;
    @FXML
    private Button botonEjercicioConcreto;
    
    boolean pulsadoCerrarSesion;
    @FXML
    private MenuItem despVerEvolucion;
    @FXML
    private MenuItem despModifDatos;
    @FXML
    private MenuItem despCerrarSesion;
    @FXML
    private ImageView imagenOla;
    @FXML
    private VBox margenIzquierdo;
    @FXML
    private BorderPane elTodo;
    Navegacion navegacion;
    @FXML
    private MenuItem despSalirDeLaApp;
    Stage stageActual;
    Window ventanaActual;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        navegacion = Lanavegacion.getNavegacion();

        //stageActual = Lanavegacion.getStagePP();
        
        
        pulsadoCerrarSesion = false;
        elTodo.widthProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            EdicionFoto();}});
        
        elTodo.heightProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            EdicionFoto();}});
        
        
        //ventanaActual.fireEvent(new WindowEvent(ventanaActual, WindowEvent.WINDOW_CLOSE_REQUEST));
        //botonCancel.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        // TODO
    } 
    
    public void alertaCruz(){
    stageActual.setOnCloseRequest(event -> {
        try {
            pasarDatos();}
        catch (NavegacionDAOException ex) {
            Logger.getLogger(PaginaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);}
        });
    
        
    }//alertaCruz
    
    public void getEscenario(Stage sc){
        stageActual = sc;
        stageActual.maximizedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
            EdicionFoto();}});
        alertaCruz();
    }
     
    public void obtenerDeLogIn(User usu ){
            usuario = usu;

            Image avatarUsuario = usuario.getAvatar();
            imagenUsuario.imageProperty().setValue(avatarUsuario);
            titulo.textProperty().setValue("Hola " + usuario.getNickName());
    }
    
    public void EdicionFoto(){
        margenIzquierdo.prefWidthProperty().bind(elTodo.widthProperty().multiply(0.12));
        double h = elTodo.heightProperty().intValue();
        imagenOla.setPreserveRatio(true);
        imagenOla.setFitHeight(h*0.23);
        //0.1975
        }
    
    //public boolean getCerradoSesion(){return pulsadoCerrarSesion;}

    @FXML
    private void clickEjercicioAleatorio(MouseEvent event) throws IOException {
        List<Problem> problemas = navegacion.getProblems();
        problemas = navegacion.getProblems();
        int aleatorio = (int) (Math.random() * problemas.size());
        //System.out.println(aleatorio);

        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("/ejercicio/ejercicio.fxml"));
        Parent root = loader.load();
        EjercicioController controladorPersona = loader.<EjercicioController>getController();
        
        controladorPersona.obtenerProblema(aleatorio, usuario, false);
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
        Scene scene = new Scene(root,700, 400);
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
        stage.setTitle("Ejercicio Aleatorio");
        stage.show();
        imagenUsuario.getScene().getWindow().hide();
    }

    @FXML
    private void clckEjercicioConcreto(MouseEvent event) throws IOException {
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("/seleccEjercicio/seleccEjercicio.fxml"));
        Parent root = loader.load();
        SeleccEjercicioController controladorPersona = loader.<SeleccEjercicioController>getController();
        controladorPersona.obtenerDeLogIn(usuario);
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
        Scene scene = new Scene(root,700, 500);
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
        stage.setTitle("Selección de Ejercicio");
        stage.show();
        imagenUsuario.getScene().getWindow().hide();
    }

    @FXML
    private void clickVerEvolucion(ActionEvent event) throws IOException, NavegacionDAOException {
        pasarDatos();
        List<Session>listaSesiones = usuario.getSessions();
        if(listaSesiones.isEmpty()){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("No se puede ver la evolución");
            alerta.setHeaderText("");
            alerta.setContentText("Para poder ver la evolución es necesario haber hecho al menos un ejercicio");
            ButtonType buttonTypeOne = new ButtonType("Entendido");
            alerta.getButtonTypes().setAll(buttonTypeOne);
            //alerta.setHeaderText("Se creará una cuenta a continuación");
            //alerta.setContentText("(Tenga en cuenta que podrá cambiar los datos más adelante).");
            Optional<ButtonType> result = alerta.showAndWait();
        }
        
        else{
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("/verEstadisticas/verEstadisticas.fxml"));
        Parent root = loader.load();
        VerEstadisticasController controladorPersona = loader.<VerEstadisticasController>getController();
        controladorPersona.loQue(usuario);
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
        stage.setTitle("Evolucion de " + usuario.getNickName());
        stage.show();
        imagenUsuario.getScene().getWindow().hide();}
    }

    @FXML
    private void clickModifDatos(ActionEvent event) throws IOException, FileNotFoundException, NavegacionDAOException {

        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("/editarDatos/editarDatos.fxml"));
        Parent root = loader.load();
        EditarDatosController controladorPersona = loader.<EditarDatosController>getController();
        controladorPersona.obtenerDePagPrin(usuario);
        
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
        Scene scene = new Scene(root,700, 700);
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
        stage.setTitle("Editar datos de " + usuario.getNickName());
        stage.show();
        //Image avatarUsuario = usuario.getAvatar();
        //imagenUsuario.imageProperty().setValue(avatarUsuario);
        imagenUsuario.getScene().getWindow().hide();
        }
    
    public void pasarDatos() throws NavegacionDAOException{
        int hits = Lanavegacion.getAciertos();
        int errors = Lanavegacion.getError();
        LocalDateTime fechaEntrada=Lanavegacion.getFechaEntrada();
        Session sesion = new Session(fechaEntrada,hits, errors);
        //System.out.println("aciertos" + sesion.getHits());
        //System.out.println( " errores" +sesion.getFaults());
        //System.out.println(" fecha" +sesion.getLocalDate().toString());
        if (hits + errors > 0){usuario.addSession(sesion);}
        //System.out.println("Por ahora finciona");
    
    };
    
    public void cerrarSesion() throws NavegacionDAOException, IOException{
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("¿Cerrar sesión?");
            alerta.setHeaderText("");
            alerta.setContentText("Se va a cerrar la sesion. ¿Proceder?");
            Optional<ButtonType> result = alerta.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                pulsadoCerrarSesion = true;
                pasarDatos();
                //botonCerrarSesion.getScene().getWindow().hide();
                //botonAcept.getScene().getWindow().hide();
            //======================================================================
            // 1- creación del grafo de escena a partir del fichero FXML
            FXMLLoader loader= new  FXMLLoader(getClass().getResource("/PaginaInicial/PaginaInicial.fxml"));
            Parent root = loader.load();
            PaginaInicialController controladorPersona = loader.<PaginaInicialController>getController();
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
            stage.setTitle("Bienvenido a Navegati");
            stage.show();
            imagenUsuario.getScene().getWindow().hide();
            }
    }
    
    @FXML
    private void clickCerrarSesion(MouseEvent event) throws NavegacionDAOException, IOException {cerrarSesion();}

    @FXML
    private void clickCerrarSesion2(ActionEvent event) throws NavegacionDAOException, IOException{cerrarSesion();}

    @FXML
    private void clickSalirDeLaApp(ActionEvent event) throws NavegacionDAOException {
        pasarDatos();
        System.exit(0);
   
    }
    
    
    
}

    

