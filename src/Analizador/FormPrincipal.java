/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author josue
 */
public class FormPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form FormPrincipal
     */
    public FormPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtruta = new javax.swing.JTextField();
        btnAnalizar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtResultado = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Analizador Léxico");

        txtruta.setEnabled(false);

        btnAnalizar.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnAnalizar.setText("Analizar");
        btnAnalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizarActionPerformed(evt);
            }
        });

        txtResultado.setColumns(20);
        txtResultado.setRows(5);
        jScrollPane1.setViewportView(txtResultado);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Josué David Higueros Calderón - 1169317 - Ingeniería Informática y de Sistemas");
        jLabel2.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtruta, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAnalizar, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtruta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnalizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizarActionPerformed
       
        //Se crea el cuadro de dialogo donde se obtendra el archivo Mini-SQL
        JFileChooser chooser = new JFileChooser();
        //Se aplica un filtro para que solo se reconozcan los archivos .MiniSQL
        FileNameExtensionFilter MiniSQLFilter = new FileNameExtensionFilter("Archivos con Extensión MiniSQL", "minisql"); 
        chooser.setFileFilter(MiniSQLFilter);
        chooser.showOpenDialog(null);


        try {
            //Se crea un lector con el cual se leéra el archivo seleccionado
            Reader lector = new BufferedReader(new FileReader(chooser.getSelectedFile()));
            //Se fija la ruta en el cuadro de texto destinado
            txtruta.setText(chooser.getSelectedFile().getAbsolutePath());
            //Se crea una instancia de la clase y se le envia la lectura al constructor
            Alexico lexer = new Alexico(lector);
            //se crea una variable para el resultado
            String resultado = "";
            //Contador de Tokens
            int Contador = 0;

            while (true) 
            {
                //se crea una instancia de la clase tokens
                Tokens tokens = lexer.yylex();
                if (tokens == null) 
                {
                    //Se concatena la palabra FIN cuando ya se finalizó el proceso
                    resultado += "FIN";
                    //Se coloca el texto en el JTextPanel
                    txtResultado.setText(resultado);
                    //Se manda a escribir la salida en un archivo de texto
                    EscribirSalida(chooser.getSelectedFile().getName().replace(".minisql"," "), txtruta.getText(), resultado);
                    return;
                }
                switch (tokens) {
                    
                    case ERROR:
                        resultado += " Simbolo no definido en el lenguaje" + lexer.lexeme + "\n";
                        break;
                        
                    case Identificador: 
                        
                        if(lexer.lexeme.length() > 31)
                        {
                            //Si el identificador es mayor a 31 caracteres
                            resultado += "TOKEN "+ Contador + ":"+" El elemento: "+lexer.lexeme.substring(0, 31) + " Es un " + tokens + "\n" + "ERROR, Identificador Truncado, Excedio el límite de caracteres permitidos"+"\n"+ "Linea: "+lexer.line+"   "+" PI: "+lexer.initialcolumn+"  "+"PF: "+lexer.finalcolumn + "\n" + "\n";
                            Contador++;
                        }
                        else
                        {
                            //Si el identificador cumple con las reglas
                            resultado += "TOKEN "+ Contador + ":"+" El elemento: "+lexer.lexeme+ " Es un " + tokens + "\n" + "Linea: "+lexer.line +"   "+"Posición Inicial: "+lexer.initialcolumn+"  "+"Posición Final: "+lexer.finalcolumn + "\n" + "\n";
                            Contador++;
                        }
                        break;
                        
                    case Reservadas:
                        //Si es una palabra reservada
                        resultado += "TOKEN "+ Contador + ":"+" El elemento: "+lexer.lexeme + " pertenece a las palabras " + tokens + "\n" + "Linea: "+lexer.line+"  "+"Posición Inicial: "+lexer.initialcolumn+"  "+"Posición Final: "+lexer.finalcolumn + "\n" + "\n";
                        Contador++;
                        break;
                        
                    case StringE:
                        resultado += "ERROR, el string excede la cantidad de líneas permitidas \n";
                        break;
                 
                    case Int: case String: case Float: case Bit:
                         resultado += "TOKEN "+ Contador + ":"+" El elemento: "+lexer.lexeme + " Es un " + tokens + "\n" + "Linea: "+lexer.line+"   "+"Posición Inicial: "+lexer.initialcolumn+"  "+"Posición Final: "+lexer.finalcolumn + "\n" + "\n";
                         Contador++;
                        break;
                        
                    case Comentario: 
                          resultado += "Comentario multilinea o simple\n";
                        break;
                        
                    case ComentarioE:
                        resultado += "ERROR, el comentario no posee el operador de cierre\n";
                    break;     
                        
                    default:
                        if(tokens.toString().contains("_"))
                        {
                            String temporal = tokens.toString().replace("_", " ");
                            resultado += "TOKEN " + Contador + ":"+" El elemento: "+lexer.lexeme + " Es " + temporal + "\n" + "Linea: "+lexer.line+"    "+"Posición Inicial: "+lexer.initialcolumn+"  "+"Posición Final: "+lexer.finalcolumn + "\n" + "\n";
                            Contador++;
                        }
                        else
                        {
                            resultado += "TOKEN " + Contador + ":"+" El elemento: "+lexer.lexeme + " Es  " + tokens + "\n" + "Linea: "+lexer.line+"    "+"Posición Inicial: "+lexer.initialcolumn+"  "+"Posición Final: "+lexer.finalcolumn + "\n" + "\n";
                            Contador++;
                        }
                        break;
                }
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FormPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FormPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAnalizarActionPerformed

    
    //Método que escribe la salida del archivo .out
    public void EscribirSalida(String Nombre, String ruta, String Contenido) throws IOException
    {
        //Se reemplaza el salto de línea por un salto de Línea que reconozca el BufferedWriter
        Contenido = Contenido.replace("\n", "\r\n");
        //Se reemplaza la extension del archivo
        ruta = ruta.replace(".minisql", ".out");
        File archivo = new File(ruta);
        BufferedWriter bw;
        
        if(archivo.exists()) 
        {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(Contenido);
        } else {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(Contenido);
        }
        bw.close();   
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnalizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtResultado;
    private javax.swing.JTextField txtruta;
    // End of variables declaration//GEN-END:variables
}
