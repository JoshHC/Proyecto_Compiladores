/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizador;

/**
 *
 * @author josue
 */
public class Simbolo {

    String Ambito, Ambito_Objeto, Tipo, Tipo_Dato, ID, Valor, Tipo_Retorno;

    public Simbolo(String Ambito, String Ambito_Objeto, String Tipo, String Tipo_Dato, String ID, String Valor, String Tipo_Retorno) {
        this.Ambito = Ambito;
        this.Ambito_Objeto = Ambito_Objeto;
        this.Tipo = Tipo;
        this.Tipo_Dato = Tipo_Dato;
        this.ID = ID;
        this.Valor = Valor;
        this.Tipo_Retorno = Tipo_Retorno;
    }

    public String getAmbito() {
        return Ambito;
    }

    public void setAmbito(String Ambito) {
        this.Ambito = Ambito;
    }

    public String getAmbito_Objeto() {
        return Ambito_Objeto;
    }

    public void setAmbito_Objeto(String Ambito_Objeto) {
        this.Ambito_Objeto = Ambito_Objeto;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getTipo_Dato() {
        return Tipo_Dato;
    }

    public void setTipo_Dato(String Tipo_Dato) {
        this.Tipo_Dato = Tipo_Dato;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String Valor) {
        this.Valor = Valor;
    }

    public String getTipo_Retorno() {
        return Tipo_Retorno;
    }

    public void setTipo_Retorno(String Tipo_Retorno) {
        this.Tipo_Retorno = Tipo_Retorno;
    }
}
