/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapaUPV;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import mapaUPV.Poi;

/**
 *
 * @author jsoler
 */
public class FXMLDocumentController implements Initializable {

    //=======================================
    // hashmap para guardar los puntos de interes POI
    private final HashMap<String, Poi> hm = new HashMap<>();
    // ======================================
    // la variable zoomGroup se utiliza para dar soporte al zoom
    // el escalado se realiza sobre este nodo, al escalar el Group no mueve sus nodos
    private Group zoomGroup;
    
    private Group paraBorrar;
    
    private ListView<Poi> map_listview;
    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private Slider zoom_slider;
    @FXML
    private MenuButton map_pin;
    @FXML
    private MenuItem pin_info;
    @FXML
    private Label posicion;
    @FXML
    private Pane panelPrueb;
    Line linePainting;
    Color colores;
    Line linX;
    Line linY;
    private BooleanProperty lineaApretado;
    
    private BooleanProperty arcoApretado;
    
    private BooleanProperty imagenApretado;
    Circle circlePainting;
    Rectangle regPainting;
    
    Line ejeX;
    Line ejeY;
    
    
    private BooleanProperty puntoApretado;
    
    private BooleanProperty textoApretado;
    
    private BooleanProperty transpApretado;
    
    private BooleanProperty coordApretado;
    
    private double inicioXArc;
    @FXML
    private ImageView fotoTransp;
    
    private double inicioXTrans;
    
    private double inicioYTrans;
    
    private double baseX;
    
    private double baseY;
    @FXML
    private ImageView cartaNautica;
    @FXML
    private ToggleButton botonImagen1;
    @FXML
    private ToggleButton botonLinea1;
    @FXML
    private ToggleButton botonPunto1;
    @FXML
    private ToggleButton botonArco1;
    @FXML
    private ToggleButton botonTexto1;
    @FXML
    private ToggleButton botonTranspor1;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Button botonLim;
    @FXML
    private Button botonCerrar;
    @FXML
    private Button botonAyuda;
    

    
    @FXML
    void zoomIn(ActionEvent event) {
        //================================================
        // el incremento del zoom dependerá de los parametros del 
        // slider y del resultado esperado
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal += 0.1);
    }

    @FXML
    void zoomOut(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        //System.out.println("Valor: " + sliderVal);
        zoom_slider.setValue(sliderVal + -0.1);
    }
    
    // esta funcion es invocada al cambiar el value del slider zoom_slider
    private void zoom(double scaleValue) {
        //===================================================
        //guardamos los valores del scroll antes del escalado
        double scrollH = map_scrollpane.getHvalue();
        double scrollV = map_scrollpane.getVvalue();
        //===================================================
        // escalamos el zoomGroup en X e Y con el valor de entrada
        zoomGroup.setScaleX(scaleValue);
        zoomGroup.setScaleY(scaleValue);
        //===================================================
        // recuperamos el valor del scroll antes del escalado
        map_scrollpane.setHvalue(scrollH);
        map_scrollpane.setVvalue(scrollV);
    }

    private List<Line> todasLineas = null; 
    
    void listClicked(MouseEvent event) {
        /*
        Poi itemSelected = map_listview.getSelectionModel().getSelectedItem();

        // Animación del scroll hasta la posicion del item seleccionado
        double mapWidth = zoomGroup.getBoundsInLocal().getWidth();
        double mapHeight = zoomGroup.getBoundsInLocal().getHeight();
        double scrollH = itemSelected.getPosition().getX() / mapWidth;
        double scrollV = itemSelected.getPosition().getY() / mapHeight;
        final Timeline timeline = new Timeline();
        final KeyValue kv1 = new KeyValue(map_scrollpane.hvalueProperty(), scrollH);
        final KeyValue kv2 = new KeyValue(map_scrollpane.vvalueProperty(), scrollV);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        // movemos el objto map_pin hasta la posicion del POI
        double pinW = map_pin.getBoundsInLocal().getWidth();
        double pinH = map_pin.getBoundsInLocal().getHeight();
        map_pin.setLayoutX(itemSelected.getPosition().getX());
        map_pin.setLayoutY(itemSelected.getPosition().getY());
        pin_info.setText(itemSelected.getDescription());
        map_pin.setVisible(true);
    */
    }

    private void initData() {
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initData();
        //==========================================================
        // inicializamos el slider y enlazamos con el zoom
        
        
        
        
        fotoTransp.setPreserveRatio(true);
        
        zoom_slider.setMin(0.2);
        zoom_slider.setMax(1);
        zoom_slider.setValue(0.5);
        zoom_slider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));
        
        //=========================================================================
        //Envuelva el contenido de scrollpane en un grupo para que 
        //ScrollPane vuelva a calcular las barras de desplazamiento tras el escalado
        Group contentGroup = new Group();
        
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(map_scrollpane.getContent());
        
        paraBorrar = new Group();
        zoomGroup.getChildren().add(paraBorrar);
        
        map_scrollpane.setContent(contentGroup);
        lineaApretado = new SimpleBooleanProperty();
        arcoApretado = new SimpleBooleanProperty();   
        imagenApretado = new SimpleBooleanProperty();
        puntoApretado = new SimpleBooleanProperty();
        textoApretado = new SimpleBooleanProperty();
        transpApretado = new SimpleBooleanProperty();
        coordApretado = new SimpleBooleanProperty();
        
        lineaApretado.setValue(Boolean.FALSE);
        arcoApretado.setValue(Boolean.FALSE);
        imagenApretado.setValue(Boolean.FALSE);
        puntoApretado.setValue(Boolean.FALSE);
        textoApretado.setValue(Boolean.FALSE);
        transpApretado.setValue(Boolean.FALSE);
        coordApretado.setValue(Boolean.FALSE);
        zoom_slider.setValue(0.2);
        
        botonImagen1.setDisable(false);
        botonLinea1.setDisable(false);
        botonArco1.setDisable(false);
        botonTexto1.setDisable(false);
        botonPunto1.setDisable(false);
        botonTranspor1.setDisable(false);
        
        colorPicker.setValue(Color.BLACK);
        
    }

    @FXML
    private void muestraPosicion(MouseEvent event) {
        posicion.setText("sceneX: " + (int) event.getSceneX() + ", sceneY: " + (int) event.getSceneY() + "\n"
                + "         X: " + (int) event.getX() + ",          Y: " + (int) event.getY());
    }

    @FXML
    private void cerrarAplicacion(ActionEvent event) {
        ((Stage)zoom_slider.getScene().getWindow()).close();
    }


    @FXML
    private void handleMoussedPressed(MouseEvent event) {
        
    }

    @FXML
    private void hacerLinea(MouseEvent event) {
        
        if(arcoApretado.getValue() != FALSE){
       double radio = Math.abs(event.getX() - inicioXArc);
       circlePainting.setRadius(radio);
       event.consume();
        }
        
        if(lineaApretado.getValue() != FALSE){
       linePainting.setEndX(event.getX());
        linePainting.setEndY(event.getY());
        linX.setEndX(event.getX());
        linY.setEndY(event.getY());
        event.consume();
        }
        
    }

    @FXML
    private void crearLinea(MouseEvent event) {
        //texto
       if(textoApretado.getValue() != FALSE ) {
           
        TextField texto = new TextField();
        
        paraBorrar.getChildren().add(texto);
        texto.setLayoutX(event.getX());
        texto.setLayoutY(event.getY());
        texto.requestFocus();
        
        texto.setOnAction(e -> {
            Text textoT = new Text(texto.getText());
            textoT.setX(texto.getLayoutX());
            textoT.setY(texto.getLayoutY());
            textoT.setStyle("-fx-font-family: Gafata; -fx-font-size: 80;");
            textoT.setFill(colorPicker.getValue());
            zoomGroup.getChildren().add(textoT);
            paraBorrar.getChildren().add(textoT);
            paraBorrar.getChildren().remove(texto);
            e.consume();
        });
       
       }
        //arco
        if(arcoApretado.getValue() != FALSE ) {
           
                
            
        circlePainting = new Circle(1);
        circlePainting.setStroke(colorPicker.getValue());
        circlePainting.setFill(Color.TRANSPARENT);
        circlePainting.setStrokeWidth(4);
        
        
        paraBorrar.getChildren().add(circlePainting);
        
        
        circlePainting.setCenterX(event.getX());
        circlePainting.setCenterY(event.getY());
        inicioXArc = event.getX();
        
        circlePainting.setOnContextMenuRequested(e -> {
        ContextMenu menuContext = new ContextMenu();
        MenuItem borrarItem = new MenuItem("eliminar");
        menuContext.getItems().add(borrarItem);
        borrarItem.setOnAction(ev -> {
        paraBorrar.getChildren().remove((Node)e.getSource());
        ev.consume();
        });
        menuContext.show(circlePainting, e.getSceneX(), e.getSceneY());
        e.consume();
        });
        }
        
        //lineas
        if(lineaApretado.getValue() != FALSE) {    
        linePainting = new Line(event.getX(), event.getY(),event.getX(), event.getY());
        linePainting.setStrokeWidth(5);
        linePainting.setFill(colorPicker.getValue());
        linePainting.setStroke(colorPicker.getValue());
        paraBorrar.getChildren().add(linePainting);
        
        linePainting.setOnContextMenuRequested(e -> {
        ContextMenu menuContext = new ContextMenu();
        MenuItem borrarItem = new MenuItem("eliminar");
        MenuItem cambiarColor = new MenuItem("Cambiar Color");
        menuContext.getItems().add(cambiarColor);
        cambiarColor.setOnAction(ev -> {
           
            linePainting.setFill(colorPicker.getValue());
           linePainting.setStroke(colorPicker.getValue());
            ev.consume();
            
        });
        menuContext.getItems().add(borrarItem);
        borrarItem.setOnAction(ev -> {
        paraBorrar.getChildren().remove((Node)e.getSource());
        
        ev.consume();
        });
        menuContext.show(linePainting, e.getSceneX(), e.getSceneY());
        e.consume();
        });
        }
        
        //PUNTO
        if(puntoApretado.getValue() != FALSE) {
            
             
            int l = 20;
            circlePainting = new Circle(l);
        circlePainting.setStroke(colorPicker.getValue());
        circlePainting.setFill(colorPicker.getValue());
        paraBorrar.getChildren().add(circlePainting);
        
        circlePainting.setCenterX(event.getX());
        circlePainting.setCenterY(event.getY());
        inicioXArc = event.getX();
        circlePainting.setOnContextMenuRequested(e -> {
        ContextMenu menuContext = new ContextMenu();
        MenuItem borrarItem = new MenuItem("Eliminar");
        MenuItem cambiarColor = new MenuItem("Cambiar color");
        MenuItem cambiarTam = new MenuItem("Hacer más grande");
        MenuItem masPeq = new MenuItem("Hacer más pequeño");
        MenuItem igual = new MenuItem("Devolver tamaño original");
        menuContext.getItems().add(igual);
        igual.setOnAction(ev -> {
            circlePainting.setStrokeWidth(l);
        });
        menuContext.getItems().add(masPeq);
        masPeq.setOnAction(ev -> {
            circlePainting.setStrokeWidth(10);
        });
        menuContext.getItems().add(cambiarTam);
        cambiarTam.setOnAction(ev -> {
          
            circlePainting.setStrokeWidth(40);
            ev.consume();
        });
        menuContext.getItems().add(cambiarColor);
        cambiarColor.setOnAction(ev -> {
           colorPicker.requestFocus();
            circlePainting.setFill(colorPicker.getValue());
           circlePainting.setStroke(colorPicker.getValue());
            ev.consume();
            
        });
        
        menuContext.getItems().add(borrarItem);
        borrarItem.setOnAction(ev -> {
        paraBorrar.getChildren().remove((Node)e.getSource());
        ev.consume();
        });
        menuContext.show(circlePainting, e.getSceneX(), e.getSceneY());
        e.consume();
        });
        }
        
        if(coordApretado.getValue() != FALSE ){
           panelPrueb = (Pane) event.getSource();
           linX = new Line(0,event.getY(),panelPrueb.getBoundsInLocal().getMaxX(),event.getY());
           linY = new Line(event.getX(),0,event.getX(),panelPrueb.getBoundsInLocal().getMaxY());
           linX.setFill(colorPicker.getValue());
           linX.setStroke(colorPicker.getValue());
           linX.setStrokeWidth(5);
           linY.setFill(colorPicker.getValue());
           linY.setStroke(colorPicker.getValue());
           linY.setStrokeWidth(5);
           paraBorrar.getChildren().add(linX);
           paraBorrar.getChildren().add(linY);
           
           linX.setOnContextMenuRequested(e -> {
           ContextMenu menuContext = new ContextMenu();
        MenuItem borrarItem = new MenuItem("eliminar");
        menuContext.getItems().add(borrarItem);
        borrarItem.setOnAction(ev -> {
        paraBorrar.getChildren().remove((Node)e.getSource());
        
        ev.consume();
        });
        menuContext.show(linX, e.getSceneX(), e.getSceneY());
        e.consume();
        });
        
        
        linY.setOnContextMenuRequested(e -> {
           ContextMenu menuContext = new ContextMenu();
        MenuItem borrarItem = new MenuItem("eliminar");
        menuContext.getItems().add(borrarItem);
        borrarItem.setOnAction(ev -> {
        paraBorrar.getChildren().remove((Node)e.getSource());
        
        ev.consume();
        });
        menuContext.show(linY, e.getSceneX(), e.getSceneY());
        e.consume();
        });
        }
    
        }
    
    

    @FXML
    private void ponerImagen(ActionEvent event) {
        //lineas
        if(coordApretado.getValue() != TRUE){
        botonLinea1.setDisable(true);
        botonTexto1.setDisable(true);
        botonPunto1.setDisable(true);
        botonTranspor1.setDisable(true);
        botonArco1.setDisable(true);
            coordApretado.setValue(Boolean.TRUE);
        }else if(coordApretado.getValue() != FALSE) {
        botonLinea1.setDisable(false);
        botonTexto1.setDisable(false);
        botonPunto1.setDisable(false);
        botonTranspor1.setDisable(false);
        botonArco1.setDisable(false);
            coordApretado.setValue(Boolean.FALSE);
        }
    }

    @FXML
    private void ponerArco(ActionEvent event) {
        
        if(arcoApretado.getValue() != TRUE){
            botonImagen1.setDisable(true);
        botonLinea1.setDisable(true);
        botonTexto1.setDisable(true);
        botonPunto1.setDisable(true);
        botonTranspor1.setDisable(true);
            arcoApretado.setValue(Boolean.TRUE);
        }else if(arcoApretado.getValue() != FALSE) {
            botonImagen1.setDisable(false);
        botonLinea1.setDisable(false);
        botonTexto1.setDisable(false);
        botonPunto1.setDisable(false);
        botonTranspor1.setDisable(false);
            arcoApretado.setValue(Boolean.FALSE);
        }
    }

    @FXML
    private void ponerLinea(ActionEvent event) {
        if(lineaApretado.getValue() != TRUE){
            botonImagen1.setDisable(true);
        botonArco1.setDisable(true);
        botonTexto1.setDisable(true);
        botonPunto1.setDisable(true);
        botonTranspor1.setDisable(true);
            lineaApretado.setValue(Boolean.TRUE);
        }else if(lineaApretado.getValue() != FALSE) {
            botonImagen1.setDisable(false);
        botonArco1.setDisable(false);
        botonTexto1.setDisable(false);
        botonPunto1.setDisable(false);
        botonTranspor1.setDisable(false);
            lineaApretado.setValue(Boolean.FALSE);
        }
    }

    @FXML
    private void finalTranslado(MouseEvent event) {
    }

    @FXML
    private void inicioTranslado(MouseEvent event) {
        if(transpApretado.getValue() != FALSE){
        inicioXTrans = event.getSceneX();
        inicioYTrans = event.getSceneY();
        baseX = fotoTransp.getTranslateX();
        baseY = fotoTransp.getTranslateY();
         fotoTransp.setOnContextMenuRequested(e -> {
        ContextMenu menuContext = new ContextMenu();
        MenuItem hacerPeq = new MenuItem("Hacer más pequeño");
        MenuItem borrarItem = new MenuItem("Hacer más grande");
        MenuItem dev = new MenuItem("Devolver tamaño");
        menuContext.getItems().add(dev);
        dev.setOnAction(ev -> {
            fotoTransp.setFitHeight(474);
            fotoTransp.setFitWidth(472);
            ev.consume();
        });
        menuContext.getItems().add(hacerPeq);
        hacerPeq.setOnAction(ev -> {
            fotoTransp.setFitHeight(274);
            fotoTransp.setFitWidth(272);
            ev.consume();
        });
        menuContext.getItems().add(borrarItem);
        borrarItem.setOnAction(ev -> {
        fotoTransp.setFitHeight(874);
        fotoTransp.setFitWidth(872);
        
        ev.consume();
        });
        menuContext.show(fotoTransp, e.getSceneX(), e.getSceneY());
        e.consume();
        });
        event.consume();
        }

    }

    @FXML
    private void translacion(MouseEvent event) {
        if(transpApretado.getValue() != FALSE) {
        double despX = event.getSceneX() - inicioXTrans;
        double despY = event.getSceneY() - inicioYTrans;
        fotoTransp.setTranslateX(baseX + despX);
        fotoTransp.setTranslateY(baseY + despY);
        event.consume();
        }
        
    }

    @FXML
    private void ponerPunto(MouseEvent event) {
        
        
    
        
    }

    @FXML
    private void marcarPunto(ActionEvent event) {
        if(puntoApretado.getValue() != TRUE){
            botonImagen1.setDisable(true);
        botonLinea1.setDisable(true);
        botonArco1.setDisable(true);
        botonTexto1.setDisable(true);
        botonTranspor1.setDisable(true);
            puntoApretado.setValue(Boolean.TRUE);
        }else if(puntoApretado.getValue() != FALSE) {
            botonImagen1.setDisable(false);
        botonLinea1.setDisable(false);
        botonArco1.setDisable(false);
        botonTexto1.setDisable(false);
        botonTranspor1.setDisable(false);
            puntoApretado.setValue(Boolean.FALSE);
        }
    }

    @FXML
    private void ponerTexto(ActionEvent event) {
        if(textoApretado.getValue() != TRUE){
            botonImagen1.setDisable(true);
        botonLinea1.setDisable(true);
        botonArco1.setDisable(true);
        botonPunto1.setDisable(true);
        botonTranspor1.setDisable(true);
            textoApretado.setValue(Boolean.TRUE);
        }else if(textoApretado.getValue() != FALSE) {
            botonImagen1.setDisable(false);
        botonLinea1.setDisable(false);
        botonArco1.setDisable(false);
        botonPunto1.setDisable(false);
        botonTranspor1.setDisable(false);
            textoApretado.setValue(Boolean.FALSE);
        }
    }

    @FXML
    private void usarTransp(ActionEvent event) {
        if(transpApretado.getValue() != TRUE){
            botonImagen1.setDisable(true);
        botonLinea1.setDisable(true);
        botonArco1.setDisable(true);
        botonTexto1.setDisable(true);
        botonPunto1.setDisable(true);
        
            fotoTransp.setVisible(true);
            transpApretado.setValue(Boolean.TRUE);
        }else if(transpApretado.getValue() != FALSE) {
            botonImagen1.setDisable(false);
        botonLinea1.setDisable(false);
        botonArco1.setDisable(false);
        botonTexto1.setDisable(false);
        botonPunto1.setDisable(false);
        
            transpApretado.setValue(Boolean.FALSE);
            //istansperable no se que pa bajar lineas
        }
    }

    @FXML
    private void borrarTodo(ActionEvent event) {
       if(paraBorrar.getChildren().size() > 0){
           
           paraBorrar.getChildren().clear();
           fotoTransp.setVisible(false);
       }
       
    }

    @FXML
    private void ponerLineasAqui(MouseEvent event) {
        
    
    }

    @FXML
    private void mostrarAyuda(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Ayudas");
        alerta.setHeaderText("¿Cómo funciona?");
        alerta.setContentText("Cada botón realiza una acción: \n" + "Línea : Marca una línea \n" + 
                            "Circunferencia: Marca una circunferencia en pantalla \n" +
                            "Texto: Añade un texto en pantalla \n" +
                            "Coordenada: Marca en las coordenadas deseadas \n" +
                            "Punto: Marca un punto en el mapa \n" +
                            "Transportador: Aparece un transportador en pantalla \n" +
                           "\n" + "CLICK DERECHO \n"  +
                            "Al realizar click derecho sobre los elementos dibujados podrás \n" +
                            "realizar diferentes opciones."
                );
        alerta.showAndWait();
    }
}
    



