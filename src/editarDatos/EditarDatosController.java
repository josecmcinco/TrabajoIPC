/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editarDatos;

import DBAccess.NavegacionDAOException;
import EscogerAvatares.EscogerAvataresController;
import Lanavegacion.Lanavegacion;
import PaginaInicial.PaginaInicialController;
import PaginaPrincipal.PaginaPrincipalController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import model.Session;
import model.User;

/**
 * FXML Controller class
 *
 * @author JACALMAN
 */
public class EditarDatosController implements Initializable {

    private Button botonAcept;
    @FXML
    private Button botonCancel;
    @FXML
    private ImageView imagenCancelar;
    @FXML
    private Label errorUsuario;
    @FXML
    private TextField espacioCorreo;
    @FXML
    private Button botonCambiarEmail;
    @FXML
    private PasswordField espacioContrasenya;
    @FXML
    private Button botonCambiarContraseña;
    @FXML
    private DatePicker espacioFecha;
    @FXML
    private ImageView imagenUsuario;
    @FXML
    private Button botonCambiarAvatar;
    @FXML
    private Label nombreUsuario;
    @FXML
    private Label errorContrasenya;
    @FXML
    private Label fechaNacimiento;
    @FXML
    private Button botonCambiarCumple;
    @FXML
    private PasswordField espacioContrasenya2;
    @FXML
    private Label emailActual;
    @FXML
    private Label errorEmail;
    @FXML
    private Label errorFecha;
    
    //Variables
    User usuario;
    int imagenSeleccionada;
    boolean buscadoFoto;
    String direccionFoto;
    Stage stageAct;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        espacioFecha.setEditable(false);
        buscadoFoto = false;
        espacioFecha.setDayCellFactory(param -> new DateCell() {
        @Override
        public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
        }
    });
    }
    public void obtenerDePagPrin(User usu ) throws FileNotFoundException, NavegacionDAOException{
            usuario = usu;
            Image avatarUsuario = usuario.getAvatar();
            imagenUsuario.imageProperty().setValue(avatarUsuario);
            
            
            fechaNacimiento.textProperty().setValue("Fecha de nacimiento: " + usuario.getBirthdate().toString());
            emailActual.textProperty().setValue("Email actual: " + usuario.getEmail());
            nombreUsuario.textProperty().setValue("Nombre del usuario: " + usuario.getNickName());
            emailActual.textProperty().setValue("Email actual: " +usuario.getEmail());
            
            errorContrasenya.visibleProperty().set(false);
            errorUsuario.visibleProperty().set(true);
            errorFecha.visibleProperty().set(false);
            errorEmail.visibleProperty().set(false);
            
            direccionFoto = null;       
    }
    
    public Image crearImagen(String URL) throws FileNotFoundException{
        return new Image(new FileInputStream(direccionFoto));
    }
    /*
    public void comprobacion(){
        contadorCambios = 0;
        if((espacioUsuario.getText().isEmpty()) ||(espacioCorreo.textProperty().getValueSafe()).equals(usuario.getEmail()){cambiarCorreo = false;}
        else{cambiarCorreo = true; contadorCambios++; }
        //if((espacioContrasenya.getText().isEmpty()) || (espacioContrasenya2.getText().isEmpty()) || (espacioContrasenya.textProperty().getValueSafe()).equals(usuario.getPassword())){cambiarCont = false;}
        //else{cambiarCont= true; contadorCambios++;}
        //if(espacioFecha.getValue().equals(usuario.getBirthdate())){cambiarCumple= false;}
        //else{cambiarCumple = true; contadorCambios++;}

    }
    */
    

    @FXML
    private void clickCancel(MouseEvent event) throws IOException {
        //Comprobación
        int contadorCambios = 0;
        boolean cambiarCorreo = false;
        boolean cambiarCont = false;
        boolean cambiarCumple= false;
        if((espacioCorreo.getText().isEmpty()) ||(espacioCorreo.textProperty().getValueSafe()).equals(usuario.getEmail())){cambiarCorreo = false;}
        else{cambiarCorreo = true; contadorCambios++; }
        if(((espacioContrasenya.getText().isEmpty()) && (espacioContrasenya2.getText().isEmpty())) || (espacioContrasenya.textProperty().getValueSafe()).equals(usuario.getPassword())){cambiarCont = false;}
        else{cambiarCont= true; contadorCambios++;}
        if((espacioFecha.getValue() == null)||espacioFecha.getValue().equals(usuario.getBirthdate())){cambiarCumple= false;}
        else{cambiarCumple = true; contadorCambios++;}
        //Fin comprobación
        if((cambiarCorreo || cambiarCont || cambiarCumple)){
        String cambios ="";
        
        if(cambiarCorreo){cambios = cambios + "correo"; contadorCambios--;
            if(contadorCambios == 1){cambios = cambios + " y "; }
            else if(contadorCambios > 1){cambios = cambios + ", ";}     
        }

        if(cambiarCont){cambios = cambios + "contraseña"; contadorCambios--;
            if(contadorCambios == 1){cambios = cambios + " y "; }
            else if(contadorCambios > 1){cambios = cambios + ", ";}
        }

            
        if(cambiarCumple){cambios = cambios + "fecha de nacimiento"; contadorCambios--;
            if(contadorCambios == 1){cambios = cambios + " y "; }
            else if(contadorCambios > 1){cambios = cambios + ", ";}
        }
        
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Salir sin guardar datos");
        alerta.setHeaderText("Hay uno o mas campos editados en los que no se ha guardado su contendio. ¿Estas seguro de cerrar?");
        alerta.setContentText("Datos : " + cambios);
        ButtonType buttonTypeOne = new ButtonType("Estoy seguro");
        ButtonType buttonTypeCancel = new ButtonType("No, volver a atrás");
        alerta.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        //alerta.setHeaderText("Se creará una cuenta a continuación");
        //alerta.setContentText("(Tenga en cuenta que podrá cambiar los datos más adelante).");
        Optional<ButtonType> result = alerta.showAndWait();
        
        if (result.isPresent() && result.get() == buttonTypeOne){
                //======================================================================
                // 1- creación del grafo de escena a partir del fichero FXML
                FXMLLoader loader= new  FXMLLoader(getClass().getResource("/PaginaPrincipal/PaginaPrincipal.fxml"));
                Parent root = loader.load();
                PaginaPrincipalController controladorPersona = loader.<PaginaPrincipalController>getController();
                controladorPersona.obtenerDeLogIn(usuario);
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
                controladorPersona.getEscenario(stage);
                stage.setScene(scene);
                stage.setTitle("Página inicial: "+ usuario.getNickName());
                stage.show();
                espacioCorreo.getScene().getWindow().hide();
            }
        }
        else{
            //botonCambiarCumple.getScene().getWindow().hide();
            //======================================================================
                // 1- creación del grafo de escena a partir del fichero FXML
                FXMLLoader loader= new  FXMLLoader(getClass().getResource("/PaginaPrincipal/PaginaPrincipal.fxml"));
                Parent root = loader.load();
                PaginaPrincipalController controladorPersona = loader.<PaginaPrincipalController>getController();
                controladorPersona.obtenerDeLogIn(usuario);
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
                controladorPersona.getEscenario(stage);
                stage.setScene(scene);
                stage.setTitle("Página inicial: "+ usuario.getNickName());
                stage.show();
                espacioCorreo.getScene().getWindow().hide();
        }

    
    }

    @FXML
    private void clickCanviarEmail(MouseEvent event) throws NavegacionDAOException {
        if(!User.checkEmail(espacioCorreo.textProperty().getValueSafe())){
        errorEmail.visibleProperty().set(true);
        espacioCorreo.styleProperty().setValue("-fx-background-color: #FCE5E0");
        espacioCorreo.textProperty().setValue("");
        }
        else{
        errorEmail.visibleProperty().set(false);
        espacioCorreo.styleProperty().setValue("");
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("¿Cambiar email?");
        alerta.setHeaderText("");
        alerta.setContentText("Se va a cambiar el correo adjunto a tu usuario. ¿Proceder?");
        //alerta.setHeaderText("Se creará una cuenta a continuación");
        //alerta.setContentText("(Tenga en cuenta que podrá cambiar los datos más adelante).");
        Optional<ButtonType> result = alerta.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK){
            usuario.setEmail(espacioCorreo.textProperty().getValueSafe());
            espacioCorreo.textProperty().setValue("");
            emailActual.textProperty().setValue("Email actual: " +usuario.getEmail());
        }
        }
    }

    @FXML
    private void clickCanviarContrase(MouseEvent event) throws NavegacionDAOException {
        if(!User.checkPassword(espacioContrasenya.textProperty().getValueSafe())){
            errorContrasenya.textProperty().setValue("La contraseña no cumple los requisitos");
            errorContrasenya.visibleProperty().set(true);
            espacioContrasenya.styleProperty().setValue("-fx-background-color: #FCE5E0");
            espacioContrasenya.textProperty().setValue("");
            espacioContrasenya2.styleProperty().setValue("-fx-background-color: #FCE5E0");
            espacioContrasenya2.textProperty().setValue("");}
        
        else if((espacioContrasenya.textProperty().getValueSafe().compareTo(
        espacioContrasenya2.textProperty().getValueSafe()) != 0)){
            errorContrasenya.textProperty().setValue("Las contraseñas no coinciden");
            errorContrasenya.visibleProperty().set(true);
            espacioContrasenya.styleProperty().setValue("-fx-background-color: #FCE5E0");
            espacioContrasenya.textProperty().setValue("");
            espacioContrasenya2.styleProperty().setValue("-fx-background-color: #FCE5E0");
            espacioContrasenya2.textProperty().setValue("");}
        
        else{
            errorContrasenya.visibleProperty().set(false);
            espacioContrasenya.styleProperty().setValue("");
            espacioContrasenya2.styleProperty().setValue("");
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("¿Cambiar contraseña?");
        alerta.setHeaderText("");
        alerta.setContentText("Se va a cambiar la contraseña tu usuario. ¿Proceder?");
        //alerta.setHeaderText("Se creará una cuenta a continuación");
        //alerta.setContentText("(Tenga en cuenta que podrá cambiar los datos más adelante).");
        Optional<ButtonType> result = alerta.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK){
            usuario.setPassword(espacioContrasenya.textProperty().getValueSafe());
            espacioContrasenya.textProperty().setValue("");
            espacioContrasenya2.textProperty().setValue("");
            
        }
        
            
    }
    }

    @FXML
    private void clickCanviarAvatar(MouseEvent event) throws IOException, NavegacionDAOException {FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/EscogerAvatares/EscogerAvatares.fxml"));
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
            Image imagenFinal = new Image(new FileInputStream(direccionFoto));
            usuario.setAvatar(imagenFinal);
            imagenUsuario.imageProperty().setValue(imagenFinal);
        }
        
    }

    @FXML
    private void clickCambiarCumple(MouseEvent event) throws NavegacionDAOException {
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
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("¿Cambiar cumpleaños?");
        alerta.setHeaderText("");
        alerta.setContentText("Se va a cambiar la fecha de cumpleaños del usuario. ¿Proceder?");
        //alerta.setHeaderText("Se creará una cuenta a continuación");
        //alerta.setContentText("(Tenga en cuenta que podrá cambiar los datos más adelante).");
        Optional<ButtonType> result = alerta.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK){
            usuario.setBirthdate(espacioFecha.getValue());
            espacioFecha.setValue(null);
            fechaNacimiento.textProperty().setValue("Fecha de nacimiento: " + usuario.getBirthdate().toString());
        }
        
            }
        }
        else{
        espacioFecha.setValue(null);
         errorFecha.visibleProperty().set(true);
        //espacioFecha.styleProperty().setValue(".date-picker > .date-picker-display-node {-fx-background-color: #FCE5E0}");}
        espacioFecha.styleProperty().setValue("-fx-background-color: #FCE5E0;");}
    }
    
     public void pasarDatos() throws NavegacionDAOException{
        int hits = Lanavegacion.getAciertos();
        int errors = Lanavegacion.getError();
        LocalDateTime fechaEntrada=Lanavegacion.getFechaEntrada();
        Session sesion = new Session(fechaEntrada,hits, errors);
        if (hits + errors > 0){usuario.addSession(sesion);}
    
    };
        public void getEscenario(Stage sc){
        stageAct = sc;
        stageAct.setOnCloseRequest(event -> {
        try {
            pasarDatos();}
        catch (NavegacionDAOException ex) {
            Logger.getLogger(PaginaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);}
        });
    }
    
    
    
    
    }
   
