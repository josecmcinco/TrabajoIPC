/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verEstadisticas;

import Lanavegacion.Lanavegacion;
import PaginaPrincipal.PaginaPrincipalController;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Session;
import model.Answer;
import model.Navegacion;
import model.Problem;
import model.User;

/**
 * FXML Controller class
 *
 * @author JACALMAN
 */
public class VerEstadisticasController implements Initializable {
    
    @FXML
    private Button botonSalir;
    //////////////////////////////////////
    @FXML
    private ListView<Session> listaView;
    
    private ObservableList<Session> misSesiones = null;
    private ObservableList<LocalDateTime> misFechas = null;
    private ObservableList<String> misFechasString;
    
     public static List<Session> listaSesiones;
    List<LocalDateTime> listaFechas;
    List<String> listaFechasString;
    
    Navegacion navegado;
    Session laSes;
    User usuario;
    @FXML
    private BorderPane elTodo;
    @FXML
    private Label textoAciertos;
    @FXML
    private Label textoErrores;
    @FXML
    private PieChart tarta;
    
    int aciertos = 3;
    int fallos = 5;
    int sumaAcierto = 0;
    int sumaErrores = 0;
    
    private ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label aciertoDia;
    @FXML
    private Label falloDia;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        navegado = Lanavegacion.getNavegacion();
        datePicker.setEditable(false);
       ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        
    }

public void loQue (User usu){
        usuario = usu;
        usuario.getSessions();
        listaSesiones = usuario.getSessions();
        misSesiones = FXCollections.observableList(listaSesiones);
        if(listaSesiones.isEmpty()){
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("No hay sesiones");
        alerta.setHeaderText("Aún no se ha realizado ningún ejercicio.");
        alerta.setContentText("La base de datos está vacía.");
         alerta.showAndWait();
            } else {  
              LocalDate ultimo = misSesiones.get(misSesiones.size() - 1).getLocalDate();
        datePicker.setDayCellFactory(param -> new DateCell() {
        @Override
        public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            setDisable(empty || (date.compareTo(ultimo)) > 0 );
        }
        });
        //misFechasString = FXCollections.observableList(listaFechasString);
        listaView.setItems(misSesiones);
        listaView.setCellFactory( c -> new FechasListCell());
       
        listaView.getSelectionModel().selectedIndexProperty().addListener(( ov, oldValue, newValue) -> {
            if(newValue.intValue() == -1) {
                aciertoDia.setText("");
                falloDia.setText("");
            }else {
                aciertoDia.setText("Aciertos de la sesión indicada: "  + listaSesiones.get(newValue.intValue()).getHits());
                falloDia.setText("Fallos de la sesión indicada: " + listaSesiones.get(newValue.intValue()).getFaults());
                
            }
        });
        //System.out.println(listaSesiones.get(3).toString());
        
        int i = listaSesiones.size() - 1;
         
        
        while( i >= 0){
                sumaAcierto += listaSesiones.get(i).getHits();
                sumaErrores += listaSesiones.get(i).getFaults();
                i--;
            }
           
            data.add(new PieChart.Data("Aciertos", sumaAcierto));
            data.add(new PieChart.Data("Fallos", sumaErrores));
         }
           
        
        datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            sumaAcierto = 0;
            sumaErrores = 0;
            int f = listaSesiones.size() - 1;
            boolean d = true;
            LocalDate comprobar = datePicker.getValue();
            
            while( f >= 0 && d){
               LocalDate today = listaSesiones.get(f).getLocalDate(); 
                    if (comprobar != null){
                        if(comprobar.getYear() < today.getYear()){
                            sumaAcierto += listaSesiones.get(f).getHits();
                            sumaErrores += listaSesiones.get(f).getFaults();}
                            else if(comprobar.getYear() == today.getYear()){
                                if(comprobar.getDayOfYear() <= today.getDayOfYear()){
                                    sumaAcierto += listaSesiones.get(f).getHits();
                                    sumaErrores += listaSesiones.get(f).getFaults();}}
                            else {d = false;}}
                        else{
                d = false;} 
                f--;
            }
            
            textoAciertos.textProperty().setValue("Acierto: " + (sumaAcierto ));
            textoErrores.textProperty().setValue("Fallos: " + (sumaErrores ));
            
            System.out.println(sumaAcierto + " " + sumaErrores);
           
            data.clear();
            data.add(new PieChart.Data("Aciertos", sumaAcierto));
            data.add(new PieChart.Data("Fallos", sumaErrores));

       });
            tarta.setData(data);
            
}


    @FXML
    private void salirVent(ActionEvent event) throws IOException {
        //((Node)(event.getSource())).getScene().getWindow().hide();
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
        stage.setTitle("Página inicial: "+ usuario.getNickName());
        stage.show();
        datePicker.getScene().getWindow().hide();
    }

        class FechasListCell extends ListCell<Session> {
        @Override
        protected void updateItem(Session item, boolean empty) {
             super.updateItem(item, empty);
             if(item == null){
                 setText(null);
             }else {
                 setText(item.getLocalDate().toString());
             }
        }
    }
}

    