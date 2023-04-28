/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seleccEjercicio;

import DBAccess.NavegacionDAOException;
import Lanavegacion.Lanavegacion;
import PaginaPrincipal.PaginaPrincipalController;
import ejercicio.EjercicioController;
import java.io.IOException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Navegacion;
import model.Problem;
import model.Session;
import model.User;

/**
 * FXML Controller class
 *User usuario;

 * @author JACALMAN
 */



public class SeleccEjercicioController implements Initializable {

    @FXML
    private BorderPane elTodo;
    User usuario;
    @FXML
    private Button botonSalir;
    @FXML
    private ListView<Problem> listaView;

    private ObservableList<Problem> probs = null;
    
    Navegacion navegado;
    @FXML
    private TextArea textArea;
    @FXML
    private Button botonHacer;
    @FXML
    private Text titulo;
    
    Stage stageAct;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        navegado = Lanavegacion.getNavegacion();
        
        probs = FXCollections.observableList(navegado.getProblems());
        listaView.setItems(probs);
        
        botonHacer.setDisable(true);
        
        listaView.setCellFactory( c -> new ProblemasListCell());
        
        listaView.getSelectionModel().selectedIndexProperty().addListener(( ov, oldValue, newValue) -> {
            if(newValue.intValue() == -1) {textArea.setText("");
            }else {
                botonHacer.setDisable(false);
                ensenar(newValue.intValue());
            }
        });
        // TODO
    } 
    
    public void obtenerDeLogIn(User usu){
        usuario = usu;
    }

    @FXML
    private void salirAqui(ActionEvent event) throws IOException {
        //((Node)(event.getSource())).getScene().getWindow().hide();
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
        listaView.getScene().getWindow().hide();
    }

    @FXML
    private void hacerEste(ActionEvent event) throws IOException {
        /*
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/mofifPersona.fxml"));
        Parent root = miCargador.load();
//      acceso al controlador de datos persona
        MofifPersonaController controladorPersona = miCargador.<MofifPersonaController>getController();// fila seleccionada de la vista de tabla.
        Persona persona = vistadeListafxID.getSelectionModel().getSelectedItem();
        controladorPersona.initPersona(persona);
        Scene scene = new Scene(root,500,300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Cambiar datos persona");
        stage.initModality(Modality.APPLICATION_MODAL); //la ventana se muestra modal
        stage.showAndWait(); // espera a que se cierre la segunda ventana.
        
       
        */
        List<Problem> problemas = navegado.getProblems();
        problemas = navegado.getProblems();
        
        

        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("/ejercicio/ejercicio.fxml"));
        Parent root = loader.load();
        EjercicioController controladorPersona = loader.<EjercicioController>getController();
        Problem per= listaView.getSelectionModel().getSelectedItem();
       //controladorPersona.obtenerProblema(probs.indexOf());
        int i = listaView.getSelectionModel().getSelectedIndex();
        //System.out.println(i);
        controladorPersona.obtenerProblema(i, usuario, true);
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
        stage.setTitle("Ejercicio escogido");
        stage.show();
        //if(!controladorPersona.getSeguirPracticando()){botonHacer.getScene().getWindow().hide();}
        botonHacer.getScene().getWindow().hide();
    }
    
    private void ensenar(int n) {
        //revisa esto
        Problem problemas = navegado.getProblems().get(n);
        String enun = problemas.getText() + "\n";
        textArea.setText(enun);
    }
    
    class ProblemasListCell extends ListCell<Problem> {
        @Override
        protected void updateItem(Problem item, boolean empty) {
             super.updateItem(item, empty);
             if(item == null){
                 setText(null);
             }else {
                 setText("Ejercicio " +(probs.indexOf(item)+ 1) + ": " + item.getText().substring(0,60) + "...");
             }
        }
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
