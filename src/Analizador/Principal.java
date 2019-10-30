/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

/**
 *
 * @author josue
 */
public class Principal {

    public static void main(String[] args) throws Exception {
        //Ruta donde se encuentra el archivo .Jflex ya anteriormente parametrizado
        String ruta1 = "C:/Users/josue/Documents/NetBeansProjects/Proyecto_Compiladores/Proyecto_Compiladores/src/Analizador/Alexico.flex";
        String ruta2 = "C:/Users/josue/Documents/NetBeansProjects/Proyecto_Compiladores/Proyecto_Compiladores/src/Analizador/LexerCup.flex";
        String[] rutaS = {"-parser", "Sintax", "C:/Users/josue/Documents/NetBeansProjects/Proyecto_Compiladores/Proyecto_Compiladores/src/Analizador/Sintax.cup"};
        generarLexer(ruta1, ruta2, rutaS);
    }

    public static void generarLexer(String ruta1, String ruta2, String[] rutaS) throws IOException, Exception {
        File archivo;
        archivo = new File(ruta1);
        //Se genera el archivo java a partir del archivo .Jflex
        JFlex.Main.generate(archivo);
        archivo = new File(ruta2);
        //Se genera el archivo java a partir del archivo .Jflex
        JFlex.Main.generate(archivo);
        java_cup.Main.main(rutaS);

        Path rutaSym = Paths.get("C:/Users/josue/Documents/NetBeansProjects/Proyecto_Compiladores/Proyecto_Compiladores/src/Analizador/sym.java");
        if (Files.exists(rutaSym)) {
            Files.delete(rutaSym);
        }
        Files.move(
                Paths.get("C:/Users/josue/Documents/NetBeansProjects/Proyecto_Compiladores/Proyecto_Compiladores/sym.java"),
                Paths.get("C:/Users/josue/Documents/NetBeansProjects/Proyecto_Compiladores/Proyecto_Compiladores/src/Analizador/sym.java")
        );

        Path rutaSin = Paths.get("C:/Users/josue/Documents/NetBeansProjects/Proyecto_Compiladores/Proyecto_Compiladores/src/Analizador/Sintax.java");
        if (Files.exists(rutaSin)) {
            Files.delete(rutaSin);
        }
        Files.move(
                Paths.get("C:/Users/josue/Documents/NetBeansProjects/Proyecto_Compiladores/Proyecto_Compiladores/Sintax.java"),
                Paths.get("C:/Users/josue/Documents/NetBeansProjects/Proyecto_Compiladores/Proyecto_Compiladores/src/Analizador/Sintax.java")
        );
    }

}
