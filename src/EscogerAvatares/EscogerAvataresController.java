/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EscogerAvatares;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import CrearCuenta.CrearCuentaController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;

/**
 * FXML Controller class
 *
 * @author JACALMAN
 */
public class EscogerAvataresController implements Initializable {

    @FXML
    private Button botonSubirFoto;
    @FXML
    private Button botonCancel;
    @FXML
    private Button botonSeleccionarAvatar;
    
    public boolean pulsadoAceptar;
    @FXML
    private ImageView avatarDefault;
    @FXML
    private ImageView avatar1;
    @FXML
    private ImageView avatar2;
    @FXML
    private ImageView avatar3;
    @FXML
    private ImageView avatar4;
    @FXML
    private ImageView avatarExtra;
    
    public boolean buscFoto;
    
    public String direccFoto;
    
    public int imagSelecc;
    
    public String direccFotoEsc;
    
    public Image avatarBuscado;
    
    File selectedFile;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        pulsadoAceptar = false;
        
        

    }    
    public void initImagenSeleccionada(int imagenSeleccionada,String direccionFoto, boolean buscadaFoto ) throws FileNotFoundException{
            imagSelecc = imagenSeleccionada;
            direccFoto = direccionFoto;
            buscFoto = buscadaFoto;
            if(imagSelecc == 0){
            avatarDefault.getParent().styleProperty().setValue("-fx-border-color: #000000");}
            else if(imagSelecc == 1){
            avatar1.getParent().styleProperty().setValue("-fx-border-color: #000000");}
            else if(imagSelecc == 2){
            avatar2.getParent().styleProperty().setValue("-fx-border-color: #000000");}
            else if(imagSelecc == 3){
            avatar3.getParent().styleProperty().setValue("-fx-border-color: #000000");}
            else if(imagSelecc == 4){
            avatar4.getParent().styleProperty().setValue("-fx-border-color: #000000");}
            else {avatarExtra.getParent().styleProperty().setValue("-fx-border-color: #000000");
                avatarBuscado = new Image(new FileInputStream(direccFoto));
                avatarExtra.imageProperty().setValue(avatarBuscado);
                }
            
            
    
    }
    public void initImagenEdicion(int imagenSeleccionada,String direccionFoto, boolean buscadaFoto ) throws FileNotFoundException{
            imagSelecc = imagenSeleccionada;
            direccFoto = direccionFoto;
            buscFoto = buscadaFoto;
            if(imagSelecc == 0){
            avatarDefault.getParent().styleProperty().setValue("-fx-border-color: #000000");}
            else if(imagSelecc == 1){
            avatar1.getParent().styleProperty().setValue("-fx-border-color: #000000");}
            else if(imagSelecc == 2){
            avatar2.getParent().styleProperty().setValue("-fx-border-color: #000000");}
            else if(imagSelecc == 3){
            avatar3.getParent().styleProperty().setValue("-fx-border-color: #000000");}
            else if(imagSelecc == 4){
            avatar4.getParent().styleProperty().setValue("-fx-border-color: #000000");}
            else {avatarExtra.getParent().styleProperty().setValue("-fx-border-color: #000000");
                avatarBuscado = new Image(new FileInputStream(direccFoto));
                avatarExtra.imageProperty().setValue(avatarBuscado);
                }
            
            
    
    }
    
    
     public boolean getAceptar(){ return pulsadoAceptar;}
     
     public boolean getbuscadoFoto(){ return buscFoto;}
     
     
     public int getImagenSeleccionada(){return imagSelecc;}
     
     public String getDireccFoto(){return direccFoto ;}
    
    @FXML
    private void clickSubirFoto(MouseEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Abrir fichero");
            fileChooser.getExtensionFilters().addAll(
            //new ExtensionFilter("Ficheros de texto", "*.txt"),
            //new ExtensionFilter("Sonidos", "*.wav", "*.mp3", "*.aac"),
            //new ExtensionFilter("Todos", "*.*")
            new ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
            selectedFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
            if (selectedFile != null) {
                direccFotoEsc = selectedFile.getAbsolutePath();
                direccFoto = direccFotoEsc;
                imagSelecc = 5;
                buscFoto = true;
                Image avatarBuscado = new Image(new FileInputStream(direccFoto));
                avatarExtra.imageProperty().setValue(avatarBuscado);
            }
        
    }

    @FXML
    private void clickCancel(MouseEvent event) {
         botonCancel.getScene().getWindow().hide();
    }

     
     
     
    @FXML
    private void clickSeleccionarAvatar(MouseEvent event) {
        pulsadoAceptar = true;
        botonCancel.getScene().getWindow().hide();
    }

    @FXML
    private void clickAvatarBase(MouseEvent event) {
        avatarDefault.getParent().styleProperty().setValue("-fx-border-color: #000000");
        avatar1.getParent().styleProperty().setValue("");
        avatar2.getParent().styleProperty().setValue("");
        avatar3.getParent().styleProperty().setValue("");
        avatar4.getParent().styleProperty().setValue("");
        avatarExtra.getParent().styleProperty().setValue("");
        direccFoto = System.getProperty("user.dir")+File.separator+"src"+File.separator+"avatars"+File.separator+"default.png";
        imagSelecc =0;
        buscFoto = false;
    }

    @FXML
    private void clickAvatar1(MouseEvent event) {
        avatarDefault.getParent().styleProperty().setValue("");
        avatar1.getParent().styleProperty().setValue("-fx-border-color: #000000");
        avatar2.getParent().styleProperty().setValue("");
        avatar3.getParent().styleProperty().setValue("");
        avatar4.getParent().styleProperty().setValue("");
        avatarExtra.getParent().styleProperty().setValue("");
        direccFoto = System.getProperty("user.dir")+File.separator+"src"+File.separator+"avatars"+File.separator+"avatar1.png";
        imagSelecc =1;
        buscFoto = false;
    }

    @FXML
    private void clickAvatar2(MouseEvent event) {
        avatarDefault.getParent().styleProperty().setValue("");
        avatar1.getParent().styleProperty().setValue("");
        avatar2.getParent().styleProperty().setValue("-fx-border-color: #000000");
        avatar3.getParent().styleProperty().setValue("");
        avatar4.getParent().styleProperty().setValue("");
        avatarExtra.getParent().styleProperty().setValue("");
        direccFoto = System.getProperty("user.dir")+File.separator+"src"+File.separator+"avatars"+File.separator+"avatar2.png";
        imagSelecc =2;
        buscFoto = false;
    }

    @FXML
    private void clickAvatar3(MouseEvent event) {
        avatarDefault.getParent().styleProperty().setValue("");
        avatar1.getParent().styleProperty().setValue("");
        avatar2.getParent().styleProperty().setValue("");
        avatar3.getParent().styleProperty().setValue("-fx-border-color: #000000");
        avatar4.getParent().styleProperty().setValue("");
        avatarExtra.getParent().styleProperty().setValue("");
        direccFoto = System.getProperty("user.dir")+File.separator+"src"+File.separator+"avatars"+File.separator+"avatar3.png";
        imagSelecc =3;
        buscFoto = false;
    }

    @FXML
    private void clickAvatar4(MouseEvent event) {
        avatarDefault.getParent().styleProperty().setValue("");
        avatar1.getParent().styleProperty().setValue("");
        avatar2.getParent().styleProperty().setValue("");
        avatar3.getParent().styleProperty().setValue("");
        avatar4.getParent().styleProperty().setValue("-fx-border-color: #000000");
        avatarExtra.getParent().styleProperty().setValue("");
        direccFoto = System.getProperty("user.dir")+File.separator+"src"+File.separator+"avatars"+File.separator+"avatar4.png";
        imagSelecc =4;
        buscFoto = false;
    }

    @FXML
    private void clickAvatarExtra(MouseEvent event) throws FileNotFoundException {
        if(buscFoto){
            avatarDefault.getParent().styleProperty().setValue("");
            avatar1.getParent().styleProperty().setValue("");
            avatar2.getParent().styleProperty().setValue("");
            avatar3.getParent().styleProperty().setValue("");
            avatar4.getParent().styleProperty().setValue("");
            avatarExtra.getParent().styleProperty().setValue("-fx-border-color: #000000");
            direccFoto = direccFotoEsc;
            imagSelecc = 5;
        }//If
        else{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Abrir fichero");
            fileChooser.getExtensionFilters().addAll(
            //new ExtensionFilter("Ficheros de texto", "*.txt"),
            //new ExtensionFilter("Sonidos", "*.wav", "*.mp3", "*.aac"),
            //new ExtensionFilter("Todos", "*.*")
            new ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
            selectedFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
            if (selectedFile != null) {
                direccFotoEsc = selectedFile.getAbsolutePath();
                buscFoto = true;
                //avatarExtra;
                avatarBuscado = new Image(new FileInputStream(direccFotoEsc));
                avatarExtra.imageProperty().setValue(avatarBuscado);
                avatarDefault.getParent().styleProperty().setValue("");
                avatar1.getParent().styleProperty().setValue("");
                avatar2.getParent().styleProperty().setValue("");
                avatar3.getParent().styleProperty().setValue("");
                avatar4.getParent().styleProperty().setValue("");
                avatarExtra.getParent().styleProperty().setValue("-fx-border-color: #000000");
                direccFoto = direccFotoEsc;
                imagSelecc = 5;
            }
            //botonCancel.getScene().getWindow().hide(); 
            
        }

    }
    

        

    
}
