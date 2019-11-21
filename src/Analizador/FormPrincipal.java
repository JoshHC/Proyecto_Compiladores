/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizador;

import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author josue
 */
public class FormPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form FormPrincipal
     */
    public FormPrincipal() throws Exception {
        initComponents();
        generarTodo();
        this.setLocationRelativeTo(null);
    }

    public TOKEN Token = new TOKEN();
  
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
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Analizador Léxico - MiniSQL");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 13, 610, 94));

        txtruta.setEnabled(false);
        txtruta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtrutaActionPerformed(evt);
            }
        });
        getContentPane().add(txtruta, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 410, -1));

        btnAnalizar.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnAnalizar.setText("Analizar");
        btnAnalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAnalizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(437, 110, 150, -1));

        txtResultado.setColumns(20);
        txtResultado.setRows(5);
        jScrollPane1.setViewportView(txtResultado);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 570, 289));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Josué David Higueros Calderón - 1169317 - Ingeniería Informática y de Sistemas");
        jLabel2.setToolTipText("");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 448, 570, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/tapiz-sql_1280x800_11668.jpg"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 480));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizarActionPerformed
       
        //Se crea el cuadro de dialogo donde se obtendra el archivo Mini-SQL
        JFileChooser chooser = new JFileChooser();
        //Se aplica un filtro para que solo se reconozcan los archivos .MiniSQL
        FileNameExtensionFilter MiniSQLFilter = new FileNameExtensionFilter("Archivos con Extensión MiniSQL", "minisql"); 
        chooser.setFileFilter(MiniSQLFilter);
        chooser.showOpenDialog(null);
        TokenAnalisis Analizador = new TokenAnalisis();
        //se crea una lista para pasarla libre de errores y con formato al analizador sintactio
        LinkedList ListaLexica = new LinkedList();

        try {
            //Se crea un lector con el cual se leéra el archivo seleccionado
            Reader lector;
            lector = new BufferedReader(new FileReader(chooser.getSelectedFile()));
            //Se fija la ruta en el cuadro de texto destinado
            txtruta.setText(chooser.getSelectedFile().getAbsolutePath());
            //Se crea una instancia de la clase y se le envia la lectura al constructor
            Alexico lexer = new Alexico(lector);
            //se crea una variable para el resultado
            String resultado = "";

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
                    //Se manda a escribir la salida en un archivo de text
                    //EscribirSalida(chooser.getSelectedFile().getName().replace(".minisql", " "), txtruta.getText(), resultado);
                    //Se envía al analizador sintáctico.
                    String Datos = ArchivoPrevioAnalisisLexico(AnalizarLista(ListaLexica));
                    //Datos = LeerArchivo(chooser.getSelectedFile().getAbsolutePath());
                    AnalizadorSintactico(Datos);
                    return;
                }
                switch (tokens) {
                    
                    case ERROR:
                        resultado += "ERROR, Simbolo: "+lexer.lexeme+" no definido en el lenguaje" +"\n" + "Linea: "+lexer.line+"   "+"Posición Inicial: "+lexer.initialcolumn+"  "+"Posición Final: "+lexer.finalcolumn + "\n" + "\n";
                        Analizador = new TokenAnalisis(tokens,lexer.lexeme,lexer.line+1,lexer.initialcolumn, lexer.finalcolumn);
                        ListaLexica.offer(Analizador);
                        break;
                        
                    case Identificador: 
                        
                        if(lexer.lexeme.length() > 31)
                        {
                            //Si el identificador es mayor a 31 caracteres
                            resultado += "TOKEN " + ":" +" El elemento: "+lexer.lexeme.substring(0, 31) + " Es un " + tokens + "\n" + "ERROR, Identificador Truncado, Excedio el límite de caracteres permitidos"+"\n"+ "Linea: "+lexer.line+"   "+"Posición Inicial: "+lexer.initialcolumn+"  "+"Posición Final: "+lexer.finalcolumn + "\n" + "\n";
                            Analizador = new TokenAnalisis(tokens,lexer.lexeme,lexer.line+1,lexer.initialcolumn, lexer.finalcolumn);
                            ListaLexica.offer(Analizador);
                        }
                        else
                        {
                            //Si el identificador cumple con las reglas
                            resultado += "TOKEN "+ ":"+" El elemento: "+lexer.lexeme+ " Es un " + tokens + "\n" + "Linea: "+lexer.line +"   "+"Posición Inicial: "+lexer.initialcolumn+"  "+"Posición Final: "+lexer.finalcolumn + "\n" + "\n";
                            Analizador = new TokenAnalisis(tokens,lexer.lexeme,lexer.line+1,lexer.initialcolumn, lexer.finalcolumn);
                            ListaLexica.offer(Analizador);
                        }
                        break;

                    case ADD:
                    case AND:
                    case OR:
                    case EXTERNAL:
                    case FETCH:
                    case PUBLIC:
                    case ALTER:
                    case FILE:
                    case RAISERROR:
                    case FILLFACTOR:
                    case READ:
                    case ANY:
                    case FOR:
                    case READTEXT:
                    case AS:
                    case FOREIGN:
                    case RECONFIGURE:
                    case FREETEXT:
                    case REFERENCES:
                    case AUTHORIZATION:
                    case FREETEXTTABLE:
                    case REPLICATION:
                    case BACKUP:
                    case FROM:
                    case RESTORE:
                    case BEGIN:
                    case FULL:
                    case RESTRICT:
                    case BETWEEN:
                    case FUNCTION:
                    case RETURN:
                    case BREAK:
                    case GOTO:
                    case REVERT:
                    case BROWSE:
                    case GRANT:
                    case REVOKE:
                    case BULK:
                    case GROUP:
                    case RIGHT:
                    case BY:
                    case HAVING:
                    case ROLLBACK:
                    case CASCADE:
                    case HOLDLOCK:
                    case ROWCOUNT:
                    case CASE:
                    case IDENTITY:
                    case ROWGUIDCOL:
                    case CHECK:
                    case IDENTITY_INSERT:
                    case RULE:
                    case CHECKPOINT:
                    case IDENTITYCOL:
                    case SAVE:
                    case CLOSE:
                    case IF:
                    case SCHEMA:
                    case CLUSTERED:
                    case IN:
                    case SECURITYAUDIT:
                    case COALESCE:
                    case INDEX:
                    case SELECT:
                    case COLLATE:
                    case INNER:
                    case SEMANTICKEYPHRASETABLE:
                    case COLUMN:
                    case INSERT:
                    case SEMANTICSIMILARITYDETAILSTABLE:
                    case COMMIT:
                    case INTERSECT:
                    case SEMANTICSIMILARITYTABLE:
                    case COMPUTE:
                    case INTO:
                    case SESSION_USER:
                    case CONSTRAINT:
                    case IS:
                    case SET:
                    case CONTAINS:
                    case JOIN:
                    case SETUSER:
                    case CONTAINSTABLE:
                    case KEY:
                    case SHUTDOWN:
                    case CONTINUE:
                    case KILL:
                    case SOME:
                    case CONVERT:
                    case LEFT:
                    case STATISTICS:
                    case CREATE:
                    case LIKE:
                    case SYSTEM_USER:
                    case CROSS:
                    case LINENO:
                    case TABLE:
                    case CURRENT:
                    case LOAD:
                    case TABLESAMPLE:
                    case CURRENT_DATE:
                    case MERGE:
                    case TEXTSIZE:
                    case CURRENT_TIME:
                    case NATIONAL:
                    case THEN:
                    case CURRENT_TIMESTAMP:
                    case NOCHECK:
                    case TO:
                    case CURRENT_USER:
                    case NONCLUSTERED:
                    case TOP:
                    case CURSOR:
                    case NOT:
                    case TRAN:
                    case DATABASE:
                    case NULL:
                    case TRANSACTION:
                    case DBCC:
                    case NULLIF:
                    case TRIGGER:
                    case DEALLOCATE:
                    case OF:
                    case TRUNCATE:
                    case DECLARE:
                    case OFF:
                    case TRY_CONVERT:
                    case DEFAULT:
                    case OFFSETS:
                    case TSEQUAL:
                    case DELETE:
                    case UNION:
                    case DENY:
                    case OPEN:
                    case UNIQUE:
                    case DESC:
                    case OPENDATASOURCE:
                    case UNPIVOT:
                    case DISK:
                    case OPENQUERY:
                    case UPDATE:
                    case DISTINCT:
                    case OPENROWSET:
                    case UPDATETEXT:
                    case DISTRIBUTED:
                    case OPENXML:
                    case USE:
                    case DOUBLE:
                    case OPTION:
                    case USER:
                    case DROP:
                    case VALUES:
                    case DUMP:
                    case ORDER:
                    case ARYING:
                    case ELSE:
                    case OUTER:
                    case VIEW:
                    case END:
                    case OVER:
                    case WAITFOR:
                    case ERRLVL:
                    case PERCENT:
                    case WHEN:
                    case ESCAPE:
                    case PIVOT:
                    case WHERE:
                    case EXCEPT:
                    case PLAN:
                    case WHILE:
                    case EXEC:
                    case PRECISION:
                    case WITH:
                    case EXECUTE:
                    case PRIMARY:
                    case WITHIN_GROUP:
                    case EXISTS:
                    case PRINT:
                    case WRITETEXT:
                    case EXIT:
                    case PROC:
                    case ABSOLUTE:
                    case OVERLAPS:
                    case ACTION:
                    case PAD:
                    case ADA:
                    case PARTIAL:
                    case PASCAL:
                    case ALL:
                    case EXTRACT:
                    case POSITION:
                    case ALLOCATE:
                    case FALSE:
                    case PREPARE:
                    case FIRST:
                    case PRESERVE:
                    case FLOAT:
                    case ARE:
                    case PRIOR:
                    case PRIVILEGES:
                    case ASC:
                    case FORTRAN:
                    case PROCEDURE:
                    case ASSERTION:
                    case FOUND:
                    case AT:
                    case REAL:
                    case AVG:
                    case GET:
                    case GLOBAL:
                    case RELATIVE:
                    case GO:
                    case BIT:
                    case BIT_LENGTH:
                    case BOTH:
                    case ROWS:
                    case HOUR:
                    case CASCADED:
                    case SCROLL:
                    case IMMEDIATE:
                    case SECOND:
                    case CAST:
                    case SECTION:
                    case CATALOG:
                    case INCLUDE:
                    case CHAR:
                    case SESSION:
                    case CHAR_LENGTH:
                    case INDICATOR:
                    case CHARACTER:
                    case INITIALLY:
                    case CHARACTER_LENGTH:
                    case SIZE:
                    case INPUT:
                    case SMALLINT:
                    case INSENSITIVE:
                    case SPACE:
                    case INT:
                    case SQL:
                    case COLLATION:
                    case INTEGER:
                    case SQLCA:
                    case SQLCODE:
                    case INTERVAL:
                    case SQLERROR:
                    case CONNECT:
                    case SQLSTATE:
                    case CONNECTION:
                    case SQLWARNING:
                    case ISOLATION:
                    case SUBSTRING:
                    case CONSTRAINTS:
                    case SUM:
                    case LANGUAGE:
                    case CORRESPONDING:
                    case LAST:
                    case TEMPORARY:
                    case COUNT:
                    case LEADING:
                    case TIME:
                    case LEVEL:
                    case TIMESTAMP:
                    case TIMEZONE_HOUR:
                    case LOCAL:
                    case TIMEZONE_MINUTE:
                    case LOWER:
                    case MATCH:
                    case TRAILING:
                    case MAX:
                    case MIN:
                    case TRANSLATE:
                    case DATE:
                    case MINUTE:
                    case TRANSLATION:
                    case DAY:
                    case MODULE:
                    case TRIM:
                    case MONTH:
                    case TRUE:
                    case DEC:
                    case NAMES:
                    case DECIMAL:
                    case NATURAL:
                    case UNKNOWN:
                    case NCHAR:
                    case DEFERRABLE:
                    case NEXT:
                    case UPPER:
                    case DEFERRED:
                    case NO:
                    case USAGE:
                    case NONE:
                    case USING:
                    case DESCRIBE:
                    case VALUE:
                    case DESCRIPTOR:
                    case DIAGNOSTICS:
                    case NUMERIC:
                    case VARCHAR:
                    case DISCONNECT:
                    case OCTET_LENGTH:
                    case VARYING:
                    case DOMAIN:
                    case ON:
                    case ONLY:
                    case WHENEVER:
                    case WORK:
                    case END_EXEC:
                    case WRITE:
                    case YEAR:
                    case OUTPUT:
                    case ZONE:
                    case EXCEPTION:
                    case OUT:
                    case READONLY:
                    case ENCRYPTION:
                    case RETURNS:
                    case SCHEMABINDING:
                    case CALLED:
                    case INLINE:
                    case CALLER:
                    case SELF:
                    case OWNER:
                    case RECOMPILE:
                    case MARK:
                    case FORWARD_ONLY:
                    case STATIC:
                    case KEYSET:
                    case DYNAMIC:
                    case FAST_FORWARD:
                    case READ_ONLY:
                    case SCROLL_LOCKS:
                    case OPTIMISTIC:
                    case TYPE_WARNING:
                        //Si es una palabra reservada
                        resultado += "TOKEN " + ":" + " El elemento: " + lexer.lexeme + " pertenece a las palabras " + tokens + "\n" + "Linea: " + lexer.line + "  " + "Posición Inicial: " + lexer.initialcolumn + "  " + "Posición Final: " + lexer.finalcolumn + "\n" + "\n";
                        Analizador = new TokenAnalisis(tokens,lexer.lexeme,lexer.line+1,lexer.initialcolumn, lexer.finalcolumn);
                        ListaLexica.offer(Analizador);
                        break;
                        
                    case StringE:
                        resultado += "ERROR, el string excede la cantidad de líneas permitidas \n" + "Linea: "+lexer.line+"   "+"Posición Inicial: "+lexer.initialcolumn+"  "+"Posición Final: "+lexer.finalcolumn + "\n" + "\n";
                        Analizador = new TokenAnalisis(tokens,lexer.lexeme,lexer.line+1,lexer.initialcolumn, lexer.finalcolumn);
                        ListaLexica.offer(Analizador);
                        break;

                    case Int:
                    case String:
                    case Float:
                    case Bit:
                        resultado += "TOKEN " + ":" + " El elemento: " + lexer.lexeme + " Es un " + tokens + "\n" + "Linea: " + lexer.line + "   " + "Posición Inicial: " + lexer.initialcolumn + "  " + "Posición Final: " + lexer.finalcolumn + "\n" + "\n";
                        Analizador = new TokenAnalisis(tokens,lexer.lexeme,lexer.line+1,lexer.initialcolumn, lexer.finalcolumn);
                        ListaLexica.offer(Analizador);
                        break;
                        
                    case Comentario: 
                          //quitar comentario
                         // resultado += "Comentario multilinea o simple" + "\n" + "Linea: "+lexer.line+"   "+"Posición Inicial: "+lexer.initialcolumn+"  "+"Posición Final: "+lexer.finalcolumn + "\n" + "\n";
                        break;
                        
                    case ComentarioE:
                        //quitar comentario
                        resultado += "ERROR, el comentario no posee el operador de cierre"+ "\n" + "Linea: "+lexer.line+"   "+"Posición Inicial: "+lexer.initialcolumn+"  "+"Posición Final: "+lexer.finalcolumn + "\n" + "\n"; 
                        Analizador = new TokenAnalisis(tokens,lexer.lexeme,lexer.line+1,lexer.initialcolumn, lexer.finalcolumn);
                        ListaLexica.offer(Analizador);
                        break;     

                    default:
                        if(tokens.toString().contains("_"))
                        {
                            String temporal = tokens.toString().replace("_", " ");
                            resultado += "TOKEN " + ":"+" El elemento: "+lexer.lexeme + " Es " + temporal + "\n" + "Linea: "+lexer.line+"    "+"Posición Inicial: "+lexer.initialcolumn+"  "+"Posición Final: "+lexer.finalcolumn + "\n" + "\n";
                            Analizador = new TokenAnalisis(tokens,lexer.lexeme,lexer.line+1,lexer.initialcolumn, lexer.finalcolumn);
                            ListaLexica.offer(Analizador);
                        }
                        else
                        {
                            resultado += "TOKEN " +":"+" El elemento: "+lexer.lexeme + " Es  " + tokens + "\n" + "Linea: "+lexer.line+"    "+"Posición Inicial: "+lexer.initialcolumn+"  "+"Posición Final: "+lexer.finalcolumn + "\n" + "\n";
                            Analizador = new TokenAnalisis(tokens,lexer.lexeme,lexer.line+1,lexer.initialcolumn, lexer.finalcolumn);
                            ListaLexica.offer(Analizador);
                        } 
                        break;
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FormPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FormPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FormPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAnalizarActionPerformed

    
    public LinkedList<TokenAnalisis> AnalizarLista(LinkedList ListaLexica) {
        Queue<TokenAnalisis> ListaAux = new LinkedList<>();
        Queue<TokenAnalisis> Lista = new LinkedList<>();
        Queue<TokenAnalisis> ListaFinal = new LinkedList<>();
        boolean error = false;
        int contador;
            
        TokenAnalisis TokenEx = new TokenAnalisis();
        TokenAnalisis TokenAux = new TokenAnalisis();
        TokenAux = (TokenAnalisis) ListaLexica.remove();
        ListaAux.add(TokenAux);
        
        while(ListaAux.isEmpty() == false && ListaLexica.size() != 0)  
        {
            TokenAux = (TokenAnalisis) ListaLexica.poll();
            ListaAux.add(TokenAux);
            
            if(TokenAux.produccion.equals(";") || TokenAux.produccion.equals("GO"))
            {
                TokenAux = (TokenAnalisis) ListaLexica.poll();
                
                if(TokenAux != null)
                {
                    if(TokenAux.produccion.equals(";") || TokenAux.produccion.equals("GO"))
                    {
                        ListaAux.add(TokenAux);
                        TokenAux = (TokenAnalisis) ListaLexica.poll();
                    }
                }
                contador = ListaAux.size();
                for (int i = 0; i < contador; i++) {
                    
                    TokenEx = ListaAux.poll();
                    if(TokenEx.produccion.equals("ERROR") || TokenEx.produccion.equals("ComentarioE") || TokenEx.produccion.equals("StringE"))
                    {
                        error =  true;
                    }
                    else
                    {
                        Lista.add(TokenEx);
                    }
                }
                
                if(error == false)
                {
                    if(Lista.isEmpty() == false)
                    {
                        while(Lista.isEmpty() == false)
                        {
                            ListaFinal.add(Lista.remove());
                        }
                        ListaAux.add(TokenAux);
                    }
                }
                else
                {
                    Lista.clear();
                    if(TokenAux != null)
                    {
                        ListaAux.add(TokenAux);
                        error = false;
                    }
                }                
            }  
        }
        if(ListaAux.size() != 0)
        {
            while(ListaAux.isEmpty() == false)
            {
              if(ListaAux.peek() != null)
              {
              ListaFinal.add(ListaAux.remove());
              }
              else
              {
                  ListaAux.remove();
              }
            }
        }
        
        ListaLexica = (LinkedList) ListaFinal;
        return ListaLexica;
    }
    
    private void txtrutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrutaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrutaActionPerformed
  
    public void AnalizadorSintactico(String data) throws Exception {

        LinkedList<String> ErroresSintacticos = null;
        Sintax s = new Sintax(new Analizador.LexerCup(new StringReader(data)));
        String Contenido = "";
        
        txtResultado.setText("");        
        //Se parsea el archivo
        //PROBAR CON debug_parse();
        s.parse();
        //Se agrega la lista de errores sintacticos
        ErroresSintacticos = s.SyntacticErrors;
        //Se concatenan los errores en una variable para luego asignarlos al cuadro de texto
        for(String element: ErroresSintacticos){
                if(Contenido.contains(element) == false)
                {
                Contenido += element + "\r\n";
                }
        }
        
        if (ErroresSintacticos.isEmpty()) {
            
            txtResultado.setText("**** ANALISIS SINTACTICO SIN ERRORES ****");
            JOptionPane.showMessageDialog(null, "Análisis Sintáctico Completado Exitosamente Sin Errores", "Análisis Sintáctico", JOptionPane.OK_OPTION);
        }
        else{
            txtResultado.setText(Contenido);
            JOptionPane.showMessageDialog(null, "Análisis Sintáctico Completado", "Análisis Sintáctico", JOptionPane.OK_OPTION);
        }
        
    }
    
    public static String LeerArchivo(String filename)throws Exception { 
        String data = ""; 
        data = new String(Files.readAllBytes(Paths.get(filename))); 
        return data; 
    }
    
    private String ArchivoPrevioAnalisisLexico(LinkedList ColaAnalisis) throws IOException {
        
        StringBuilder pw = new StringBuilder();

        int linea = 1;
        int columnai = 0;
        int columnaf = 0;
        String Linea = "";
                
        TokenAnalisis Tok;

        while (ColaAnalisis.peek() != null) 
        {
            Tok = (TokenAnalisis) ColaAnalisis.poll();
            columnai = Tok.columnai;
            
            if (Tok.linea == linea)
            {
                while (columnai - columnaf > 1)
                {
                    Linea += ' ';
                    columnaf++;
                }
                Linea += Tok.produccion;
                
                columnaf = Tok.columnaf;
            }
            else
            {
                Linea += "\n";
                pw.append(Linea);
                linea++;
                
                while(Tok.linea != linea)
                {
                    Linea = "";
                    Linea += "\n";
                    pw.append(Linea);
                    linea++;
                }
                
                Linea = "";
                Linea += Tok.produccion;
                
                columnaf = Tok.columnaf;
            }
        }
        
        pw.append(Linea);
        return pw.toString();
    }
    
    //Método que escribe la salida del archivo .out
    public void EscribirSalida(String Nombre, String ruta, String Contenido) throws IOException {
        //Se reemplaza el salto de línea por un salto de Línea que reconozca el BufferedWriter
        Contenido = Contenido.replace("\n", "\r\n");
        //Se reemplaza la extension del archivo
        ruta = ruta.replace(".minisql", ".out");
        File archivo = new File(ruta);
        BufferedWriter bw;

        if (archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(Contenido);
        } else {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(Contenido);
        }
        bw.close();

    }
    
    public void generarTodo() throws IOException, Exception{
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
                try {
                    new FormPrincipal().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(FormPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnalizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtResultado;
    private javax.swing.JTextField txtruta;
    // End of variables declaration//GEN-END:variables
}
