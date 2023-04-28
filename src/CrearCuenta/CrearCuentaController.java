/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CrearCuenta;


import DBAccess.NavegacionDAOException;
import EscogerAvatares.EscogerAvataresController;
import Lanavegacion.Lanavegacion;
import PaginaInicial.PaginaInicialController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Navegacion;
import model.User;


/**
 * FXML Controller class
 *
 * @author JACALMAN
 */
public class CrearCuentaController implements Initializable {

    @FXML
    private Button botonCancel;
    @FXML
    private Button botonAcept;
    @FXML
    private TextField espacioUsuario;
    @FXML
    private Label errorUsuario;
    @FXML
    private TextField espacioCorreo;
    @FXML
    private Label errorEmail;
    @FXML
    private PasswordField espacioContrasenya;
    @FXML
    private Label errorContrasenya;
    @FXML
    private PasswordField espacioContrasenya2;
    @FXML
    private Label errorContrasenya2;
    @FXML
    private DatePicker espacioFecha;
    @FXML
    private Label errorFecha;
    @FXML
    private Button botonCambiarAvatar;
    //Para saber si se puede crear la cuenta o hay un error en algun campo
    public int contador;
    
    //Para saber el nº de imagen y direcc de esta
    public int imagenSeleccionada;
    public String direccionFoto;
    //Saber si se ha buscado una foto en el equipo
    public boolean buscadoFoto;
    //
    public Image avatarFinal;
    @FXML
    private ImageView imagenCancelar;
    
    Navegacion navegacion;
    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        navegacion = Lanavegacion.getNavegacion();
        // TODO                                   
        errorUsuario.visibleProperty().set(false);
        errorContrasenya.visibleProperty().set(false);
        errorContrasenya2.visibleProperty().set(false);
        errorEmail.visibleProperty().set(false);
        errorFecha.visibleProperty().set(false);
        espacioFecha.setEditable(false);
        //SelecciónImagenBase
        direccionFoto = System.getProperty("user.dir")+File.separator+"src"+File.separator+"avatars"+File.separator+"default.png";
        //direccionFoto = "..\\avatars\\default.png";
        imagenSeleccionada =0;
        buscadoFoto = false;
        espacioFecha.setDayCellFactory(param -> new DateCell() {
        @Override
        public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
        }
    });
        
        
                
    }  
    //Bloquear parte de los dias del calendario




    @FXML
    private void clickCancel(MouseEvent event) throws IOException {
        if(
                   ((espacioUsuario.getText().isEmpty()) || (espacioUsuario.getText().trim().length()==0))
            && ((espacioCorreo.getText().isEmpty()) || (espacioCorreo.getText().trim().length() ==0))
            && ((espacioContrasenya.getText().isEmpty()) || (espacioContrasenya.getText().trim().length() ==0))
            && ((espacioContrasenya2.getText().isEmpty()) || (espacioContrasenya2.getText().trim().length() ==0))
            && (espacioFecha.getValue() == null)
                )
        {//botonAcept.getScene().getWindow().hide();
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
            //System.getProperty("user.dir")+File.separator+"src"+
            stage.setScene(scene);
            stage.setTitle("Bienvenido a Navegati");
            stage.show();
            botonAcept.getScene().getWindow().hide();
        }
        
        else{
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("¿Cerrar formulario ya empezado?");
            alerta.setHeaderText("");
            alerta.setContentText("Se va a cerrar un formulario ya empezado. ¿Está seguro? Si acepta, todos sus avances se perderán.");
            //alerta.setHeaderText("Se creará una cuenta a continuación");
            //alerta.setContentText("(Tenga en cuenta que podrá cambiar los datos más adelante).");
            Optional<ButtonType> result = alerta.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
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
            botonAcept.getScene().getWindow().hide();
            }        
        }//else     
    }

    @FXML
    private void clickAcept(MouseEvent event) throws IOException, NavegacionDAOException {
        contador = 0;
        navegacion = Lanavegacion.getNavegacion();
        //Navegacion navegacion = Navegacion.getSingletonNavegacion();
        //IMPORTANTE
        //Permite reiniciar la base de datos
        //navegacion.removeAllData();
        //comprobar campo usuario
        if(!User.checkNickName(espacioUsuario.textProperty().getValueSafe())){
            //Cambiar texto
            errorUsuario.textProperty().setValue("El usuario introducido no cumple con los requisitos");
            errorUsuario.visibleProperty().set(true);
            espacioUsuario.styleProperty().setValue("-fx-background-color: #FCE5E0");
            espacioUsuario.textProperty().setValue("");
        }
        
        else if(navegacion.exitsNickName(espacioUsuario.textProperty().getValueSafe())){
            errorUsuario.textProperty().setValue("Un usuario con este nombre ya existe. Escoja otro nombre");
            errorUsuario.visibleProperty().set(true);
            espacioUsuario.styleProperty().setValue("-fx-background-color: #FCE5E0");
            espacioUsuario.textProperty().setValue("");
        }
        
        else{
        errorUsuario.visibleProperty().set(false);
        espacioUsuario.styleProperty().setValue("");
        contador++;
    }
        
        //Comprobar correo
        if(!User.checkEmail(espacioCorreo.textProperty().getValueSafe())){
        errorEmail.visibleProperty().set(true);
        espacioCorreo.styleProperty().setValue("-fx-background-color: #FCE5E0");
        espacioCorreo.textProperty().setValue("");
        }
        else{
        errorEmail.visibleProperty().set(false);
        espacioCorreo.styleProperty().setValue("");
        contador++;
    }
        //Comprobar contraseña1 & not equals
        if(!User.checkPassword(espacioContrasenya.textProperty().getValueSafe())){
            errorContrasenya2.visibleProperty().set(false);
            errorContrasenya.visibleProperty().set(true);
            espacioContrasenya.styleProperty().setValue("-fx-background-color: #FCE5E0");
            espacioContrasenya.textProperty().setValue("");
            espacioContrasenya2.styleProperty().setValue("-fx-background-color: #FCE5E0");
            espacioContrasenya2.textProperty().setValue("");}
        
        else if((espacioContrasenya.textProperty().getValueSafe().compareTo(
        espacioContrasenya2.textProperty().getValueSafe()) != 0)){
            errorContrasenya.visibleProperty().set(false);
            errorContrasenya2.visibleProperty().set(true);
            espacioContrasenya.styleProperty().setValue("-fx-background-color: #FCE5E0");
            espacioContrasenya.textProperty().setValue("");
            espacioContrasenya2.styleProperty().setValue("-fx-background-color: #FCE5E0");
            espacioContrasenya2.textProperty().setValue("");}
        
        else{
            errorContrasenya.visibleProperty().set(false);
            errorContrasenya2.visibleProperty().set(false);
            espacioContrasenya.styleProperty().setValue("");
            espacioContrasenya2.styleProperty().setValue("");
            contador++;
    }
        //Comprobar edad calendario
        LocalDate comprobar = espacioFecha.getValue();
        if (comprobar != null){
            LocalDate today = LocalDate.now();
            comprobar = comprobar.plusYears(16);
            if(comprobar.getYear() > today.getYear()){
                espacioFecha.setValue(null);
                errorFecha.visibleProperty().set(true);
                espacioFecha.styleProperty().setValue("-fx-background-color: #FCE5E0");}
            else if(comprobar.getYear() == today.getYear()){
                if(comprobar.getDayOfYear() > today.getDayOfYear()){
                    espacioFecha.setValue(null);
                    errorFecha.visibleProperty().set(true);
                    espacioFecha.styleProperty().setValue("-fx-background-color: #FCE5E0");}}
        
            else {
            errorFecha.visibleProperty().set(false);
            espacioFecha.styleProperty().setValue("");
            contador++;}
        }
        else{
        espacioFecha.setValue(null);
         errorFecha.visibleProperty().set(true);
        //espacioFecha.styleProperty().setValue(".date-picker > .date-picker-display-node {-fx-background-color: #FCE5E0}");}
        espacioFecha.styleProperty().setValue("-fx-background-color: #FCE5E0;");}        
        
        //Comprobar si lo puede crear, PIDE CONFIRMACIÓN y si acepta LO HACE
        if(contador == 4){

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("¿Crear Cuenta?");
        alerta.setHeaderText("Se va a crear una cuenta con los datos. ¿Proceder?");
        alerta.setContentText("Tenga en cuenta que podrá editar cualquier campo (menos el usuario) más adelante.");
        //alerta.setHeaderText("Se creará una cuenta a continuación");
        //alerta.setContentText("(Tenga en cuenta que podrá cambiar los datos más adelante).");
        Optional<ButtonType> result = alerta.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK){
            String nickName = espacioUsuario.textProperty().getValueSafe();
            String email = espacioCorreo.textProperty().getValueSafe();
            String password = espacioContrasenya.textProperty().getValueSafe();
            LocalDate birthdate = espacioFecha.getValue();
            //avatarFinal = new Image(new FileInputStream(direccionFoto));
            User resultado = navegacion.registerUser(nickName, email, password, crearImagen(direccionFoto), birthdate);
            //User result = navegacion.registerUser(nickName, email, password, birthdate);
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
            botonAcept.getScene().getWindow().hide();
            }
        }//FinalIf = X  
    }//FinalAaceptar
    
    public Image crearImagen(String URL) throws FileNotFoundException{
        return new Image(new FileInputStream(direccionFoto));
    }

    @FXML
    private void clickCanviarAvatar(MouseEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/EscogerAvatares/EscogerAvatares.fxml"));
        Parent root = miCargador.load();
//      acceso al controlador de datos persona
        EscogerAvataresController controladorPersona = miCargador.<EscogerAvataresController>getController();// fila seleccionada de la vista de tabla.
        controladorPersona.initImagenSeleccionada(imagenSeleccionada,direccionFoto,buscadoFoto);
        Scene scene = new Scene(root,550,450);
        //2.5 Poner CSS
        String css = this.getClass().getResource("/baseCSS/baseCSS.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Seleccionar avatar");
        stage.initModality(Modality.APPLICATION_MODAL); //la ventana se muestra modal
        stage.showAndWait(); // espera a que se cierre la segunda ventana.
        
        if (controladorPersona.getAceptar()){
            imagenSeleccionada = controladorPersona.getImagenSeleccionada();
            direccionFoto = controladorPersona.getDireccFoto();
            buscadoFoto = controladorPersona.getbuscadoFoto();
            //botonAcept.getScene().getWindow().hide();
            //espacioUsuario.textProperty().setValue("Valor:"+ imagenSeleccionada);
            //espacioUsuario.textProperty().setValue(direccionFoto);
        }
        
    }
    
}
