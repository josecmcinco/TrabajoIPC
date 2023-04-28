/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lanavegacion;

import DBAccess.NavegacionDAOException;
import java.time.LocalDateTime;
import model.Navegacion;

/**
 *
 * @author JACALMAN
 */
public class Lanavegacion {
    public static int aciertos;
    public static int errores;
    public static LocalDateTime fechaEntrada;
    public static Navegacion navegacion;
    
    
    public static void setNavegacion() throws NavegacionDAOException{
         navegacion = Navegacion.getSingletonNavegacion();
    }
    
    public static Navegacion getNavegacion (){
        return navegacion;
    }
    
    public static void iniciarValores(){
        aciertos = 0;
        errores = 0;
        fechaEntrada = LocalDateTime.now();
        
    }
    
    public static void añadirError(){
        errores++;
    }
    
    public static void añadirAcierto(){
        aciertos++;
    }
    
    public static int getError(){
        return errores;
    }
    
    public static int getAciertos(){
        return aciertos;
    }
            
    public static LocalDateTime getFechaEntrada(){
        return fechaEntrada;
    }
}
