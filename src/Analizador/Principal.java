/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizador;

import java.io.File;

/**
 *
 * @author josue
 */
public class Principal 
{
    public static void main(String[] args) 
    {
        //Ruta donde se encuentra el archivo .Jflex ya anteriormente parametrizado
         String ruta = "C:/Users/josue/Documents/NetBeansProjects/Proyecto_Compiladores/Proyecto_Compiladores/src/Analizador/Alexico.flex";
         generarLexer(ruta);
    }
    
    public static void generarLexer(String ruta)
    {
        File archivo = new File(ruta);
        //Se genera el archivo java a partir del archivo .Jflex
        JFlex.Main.generate(archivo);
    }
    
}
