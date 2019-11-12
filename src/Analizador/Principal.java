/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizador;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

/**
 *
 * @author josue
 */
public class Principal {

    public static void main(String[] args) throws Exception {
        String syntaxRoute = "", aux = "", SyntaxLexer = "";
        String absolutePath = new File(".").getAbsolutePath();
        absolutePath = absolutePath.substring(0, absolutePath.length() - 1);
        syntaxRoute = absolutePath;
        aux = absolutePath;
        SyntaxLexer = absolutePath;
        SyntaxLexer += "src\\Analizador\\Alexico.flex";
        absolutePath += "src\\Analizador\\LexerCup.flex";
        syntaxRoute += "src\\Analizador\\Sintax.cup";
        String[] routesS = {"-parser", "Sintax", syntaxRoute};

        File file = new File(absolutePath);
        JFlex.Main.generate(file);
        java_cup.Main.main(routesS);

        Path symRoute_aux = Paths.get(aux + "/src/Analizador/sym.java");
        Path symRoute = Paths.get(aux + "/sym.java");
        Files.deleteIfExists(symRoute_aux);
        Files.move(symRoute, symRoute_aux);

        Path sinRoute_aux = Paths.get(aux + "/src/Analizador/Sintax.java");
        Path sinRoute = Paths.get(aux + "/Sintax.java");
        Files.deleteIfExists(sinRoute_aux);
        Files.move(sinRoute, sinRoute_aux);
    }
}
