/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio;

import DBAccess.NavegacionDAOException;
import Lanavegacion.Lanavegacion;
import PaginaPrincipal.PaginaPrincipalController;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Answer;
import model.Navegacion;
import model.Problem;
import model.Session;
import model.User;
import seleccEjercicio.SeleccEjercicioController;

/**
 * FXML Controller class
 *
 * @author JACALMAN
 */
public class EjercicioController implements Initializable {

    @FXML
    private Button botonAcept;
    @FXML
    private Button botonCancel;
    @FXML
    private Label enunciado;
    @FXML
    private RadioButton opcion2;
    @FXML
    private RadioButton opcion1;
    @FXML
    private RadioButton opcion3;
    @FXML
    private RadioButton opcion4;
    
    Navegacion navegacion;
    
    int ejercicio;
    @FXML
    private Text titulo;
    
    boolean sinEscoger;
    List<Problem> problemas;
    @FXML
    private Button verMapa;
    User usuario;
    Answer respuesta1;
    Answer respuesta2;
    Answer respuesta3;
    Answer respuesta4;
    
    Answer respuestaEscogida;
    boolean origenLista;
    boolean seguirPracticando;
    Stage stageAct;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        seguirPracticando = true;
        
        // TODO
    }  
    
    public void obtenerProblema(int ej, User usu, boolean ori){
        botonAcept.setDisable(true);
        sinEscoger = true;
        
        
        opcion1.setDisable(false);
        opcion2.setDisable(false);
        opcion3.setDisable(false);
        opcion4.setDisable(false);
        
        opcion1.setSelected(false);
        opcion2.setSelected(false);
        opcion3.setSelected(false);
        opcion4.setSelected(false);
        
        
        navegacion = Lanavegacion.getNavegacion();
        origenLista = ori;
        usuario = usu;
        ejercicio = ej;
        titulo.textProperty().setValue("Ejercicio: " + (ejercicio + 1));
        int aleatorio = (int) (Math.random() * 4);
        //
        problemas = navegacion.getProblems();
        Problem a = problemas.get(ejercicio);
        List<Answer> respuestas = a.getAnswers();
        enunciado.setText(a.getText());
        ///
      
        
        int cont = 0;
        opcion1.setText(respuestas.get((aleatorio + cont) % 4).getText());
        respuesta1 = respuestas.get((aleatorio + cont) % 4);
        cont++;
        opcion2.setText(respuestas.get((aleatorio + cont) % 4).getText());
        respuesta2 = respuestas.get((aleatorio + cont) % 4);
        cont++;
        opcion3.setText(respuestas.get((aleatorio + cont) % 4).getText());
        respuesta3 = respuestas.get((aleatorio + cont) % 4);
        cont++;
        opcion4.setText(respuestas.get((aleatorio + cont) % 4).getText());
        respuesta4 = respuestas.get((aleatorio + cont) % 4);
        cont++;

    }

    @FXML
    private void clickAcept(MouseEvent event) throws IOException {
        
        seguirPracticando = true;
        
        opcion1.setDisable(true);
        opcion2.setDisable(true);
        opcion3.setDisable(true);
        opcion4.setDisable(true);
        


        
        boolean resultado = respuestaEscogida.getValidity();
        //System.out.println("Es correcto : " + resultado );
        //problema1 respuesta = 36º 02,6'N L =005 17,0'W
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        if(resultado){
        Lanavegacion.añadirAcierto();
        alerta.setTitle("Respuesta correcta");
        alerta.setHeaderText("La respuesta que has introducdo es la correcta");
        
        }//if resultado
        else{
        Lanavegacion.añadirError();
        Answer respuestaCorrecta;
        //Answer respuestaCorrecta = respuesta1;
        
        
        resultado = respuesta1.getValidity();
        if(resultado){respuestaCorrecta = respuesta1;}
        else{   resultado = respuesta2.getValidity();
                if(resultado){respuestaCorrecta = respuesta2;}
                else{   resultado = respuesta3.getValidity();
                        if(resultado){respuestaCorrecta = respuesta3;}
                        else{   respuestaCorrecta = respuesta4;
                        }//else opc3 = op4
                }//else opc2 op3?
            }//else opc1 = op2?
        
        alerta.setTitle("Respuesta incorrecta");
        alerta.setHeaderText("Respuesta incorrecta. \n La respuesta correcta era: "+respuestaCorrecta.getText());
            
        }
        if(origenLista){
            alerta.setContentText("¿Que quieres hacer a continuación?");
            ButtonType buttonTypeOne = new ButtonType("Volver al selector de ejercicios");
            ButtonType buttonTypeTwo = new ButtonType("Volver al menú principal");
            alerta.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
            //alerta.setHeaderText("Se creará una cuenta a continuación");
            //alerta.setContentText("(Tenga en cuenta que podrá cambiar los datos más adelante).");
            Optional<ButtonType> result = alerta.showAndWait();
        
            if (result.isPresent() && result.get() == buttonTypeOne){
                 //======================================================================
                    // 1- creación del grafo de escena a partir del fichero FXML
                    FXMLLoader loader= new  FXMLLoader(getClass().getResource("/seleccEjercicio/seleccEjercicio.fxml"));
                    Parent root = loader.load();
                    SeleccEjercicioController controladorPersona = loader.<SeleccEjercicioController>getController();
                    controladorPersona.obtenerDeLogIn(usuario);
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
                    controladorPersona.getEscenario(stage);
                    stage.setScene(scene);
                    stage.setTitle("Selección de Ejercicio");
                    stage.show();
                    opcion1.getScene().getWindow().hide();
                }
            else if(result.isPresent() && result.get() == buttonTypeTwo){
                //seguirPracticando = true;
                //titulo.getScene().getWindow().hide();
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
                opcion1.getScene().getWindow().hide();
            }
            }//if origenLista
        else{
            alerta.setContentText("¿Que quieres hacer a continuación?");
            ButtonType buttonTypeOne = new ButtonType("Hacer otro ejercicio aleatorio");
            ButtonType buttonTypeTwo = new ButtonType("Volver al menú principal");
            alerta.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
            //alerta.setHeaderText("Se creará una cuenta a continuación");
            //alerta.setContentText("(Tenga en cuenta que podrá cambiar los datos más adelante).");
            Optional<ButtonType> result = alerta.showAndWait();
        
            if (result.isPresent() && result.get() == buttonTypeTwo){
                //titulo.getScene().getWindow().hide();
                //seguirPracticando = true;
                //titulo.getScene().getWindow().hide();
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
                opcion1.getScene().getWindow().hide();
            }
            else if(result.isPresent() && result.get() == buttonTypeOne){
                int random = ejercicio; 
                while(random == ejercicio){
                random = (int) (Math.random() * problemas.size());
                //System.out.println(random);
                }
                obtenerProblema(random, usuario, origenLista);
                //titulo.getScene().getWindow().hide();
            }
            }//else origenLista
        
    }
    
    public boolean getSeguirPracticando(){return seguirPracticando;}


    @FXML
    private void clickCancel(MouseEvent event) throws IOException {
        if(!sinEscoger){
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("¿Cerrar problema a medias?");
            alerta.setHeaderText("Se va a cerrar un problema ya empezado. ¿Estas seguro?");
            alerta.setContentText("Recuerda que puedes buscar el ejercicio para reintentarlo");
            ButtonType buttonTypeOne = new ButtonType("Estoy seguro");
            ButtonType buttonTypeCancel = new ButtonType("No, volver a atrás");
            alerta.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
            //alerta.setHeaderText("Se creará una cuenta a continuación");
            //alerta.setContentText("(Tenga en cuenta que podrá cambiar los datos más adelante).");
            Optional<ButtonType> result = alerta.showAndWait();
            
            if (result.isPresent() && result.get() == buttonTypeOne){
                if(origenLista){
                    //======================================================================
                    // 1- creación del grafo de escena a partir del fichero FXML
                    FXMLLoader loader= new  FXMLLoader(getClass().getResource("/seleccEjercicio/seleccEjercicio.fxml"));
                    Parent root = loader.load();
                    SeleccEjercicioController controladorPersona = loader.<SeleccEjercicioController>getController();
                    controladorPersona.obtenerDeLogIn(usuario);
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
                    controladorPersona.getEscenario(stage);
                    stage.setScene(scene);
                    stage.setTitle("Selección de Ejercicio");
                    stage.show();
                    opcion1.getScene().getWindow().hide();
                    }
            else{
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
                opcion1.getScene().getWindow().hide();
                    
                    }
                //titulo.getScene().getWindow().hide();}
        
        
        
        
        }
        
        //else{titulo.getScene().getWindow().hide();}
    }
        else{
            if(origenLista){
                    //======================================================================
                    // 1- creación del grafo de escena a partir del fichero FXML
                    FXMLLoader loader= new  FXMLLoader(getClass().getResource("/seleccEjercicio/seleccEjercicio.fxml"));
                    Parent root = loader.load();
                    SeleccEjercicioController controladorPersona = loader.<SeleccEjercicioController>getController();
                    controladorPersona.obtenerDeLogIn(usuario);
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
                    controladorPersona.getEscenario(stage);
                    stage.setScene(scene);
                    stage.setTitle("Selección de Ejercicio");
                    stage.show();
                    opcion1.getScene().getWindow().hide();
                    }
            else{
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
                opcion1.getScene().getWindow().hide();
                    
                }
        }
    
    }

    @FXML
    private void click2(ActionEvent event) {
        opcion1.setSelected(false);
        opcion2.setSelected(true);
        opcion3.setSelected(false);
        opcion4.setSelected(false);
        sinEscoger = false;
        botonAcept.setDisable(false);
        respuestaEscogida = respuesta2;
    }

    @FXML
    private void click1(ActionEvent event) {
        opcion1.setSelected(true);
        opcion2.setSelected(false);
        opcion3.setSelected(false);
        opcion4.setSelected(false);
        sinEscoger = false;
        botonAcept.setDisable(false);
        respuestaEscogida = respuesta1;
    }

    @FXML
    private void click3(ActionEvent event) {
        opcion1.setSelected(false);
        opcion2.setSelected(false);
        opcion3.setSelected(true);
        opcion4.setSelected(false);
        sinEscoger = false;
        botonAcept.setDisable(false);
        respuestaEscogida = respuesta3;
    }

    @FXML
    private void click4(ActionEvent event) {
        opcion1.setSelected(false);
        opcion2.setSelected(false);
        opcion3.setSelected(false);
        opcion4.setSelected(true);
        sinEscoger = false;
        botonAcept.setDisable(false);
        respuestaEscogida = respuesta4;
    }

    @FXML
    private void clickVerMapa(MouseEvent event) throws IOException {
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("/mapaUPV/FXMLDocument.fxml"));
        Parent root = loader.load();

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
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Mapa de ayuda");
        stage.show();
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
