package Analizador;
import static Analizador.Tokens.*;

/* Sección de declaraciones de JFlex */
%%
%class Alexico
%type Tokens
%line
%column

/* Inicio de Expresiones regulares */
L=[a-zA-Z_]+
D=[0-9]+
Digito = [0-9]
Numero = {Digito} {Digito}*
Letra = [A-Za-z]
Palabra = {Letra} {Letra}*
Guion = "_"
Espacio = [ \t\r\n]+
Operador = "+"|"-"|"*"|"/"|"%"|"<"|"<="|">"|">="|"="|"=="|"!="|"&&"|"||"
Puntuacion = "!"|";"|","|"."
SignosA = "[]"|"()"|"{}"
Simbolos = "@"|"#"|"##"
SignosApertura = "["|"{"|"("
SignosCierre = "]"|"}"|")"

/* Finaliza expresiones regulares */

%{
    public String lexeme;
    public int line;
    public int initialcolumn;
    public int finalcolumn;
%}

%%
/* Finaliza la sección de declaraciones de JFlex */

/* Inicia sección de reglas */

/*PALABRAS RESERVADAS*/
ADD|EXTERNAL|PROCEDURE|ALL|FETCH|PUBLIC|ALTER|FILE|RAISERROR|AND|FILLFACTOR|READ|ANY|FOR|READTEXT|AS|FOREIGN|RECONFIGURE|
ASC|FREETEXT|REFERENCES|AUTHORIZATION|FREETEXTTABLE|REPLICATION|BACKUP|FROM|RESTORE|BEGIN|FULL|RESTRICT|BETWEEN|FUNCTION|
RETURN|BREAK|GOTO|REVERT|BROWSE|GRANT|REVOKE|BULK|GROUP|RIGHT|BY|HAVING|ROLLBACK|CASCADE|HOLDLOCK|ROWCOUNT|CASE|IDENTITY|
ROWGUIDCOL|CHECK|IDENTITY_INSERT|RULE|CHECKPOINT|IDENTITYCOL|SAVE|CLOSE|IF|SCHEMA|CLUSTERED|IN|SECURITYAUDIT|COALESCE|INDEX|
SELECT|COLLATE|INNER|SEMANTICKEYPHRASETABLE|COLUMN|INSERT|SEMANTICSIMILARITYDETAILSTABLE|COMMIT|INTERSECT|SEMANTICSIMILARITYTABLE|
COMPUTE|INTO|SESSION_USER|CONSTRAINT|IS|SET|CONTAINS|JOIN|SETUSER|CONTAINSTABLE|KEY|SHUTDOWN|CONTINUE|KILL|SOME|CONVERT|LEFT|
STATISTICS|CREATE|LIKE|SYSTEM_USER|CROSS|LINENO|TABLE|CURRENT|LOAD|TABLESAMPLE|CURRENT_DATE|MERGE|TEXTSIZE|CURRENT_TIME|NATIONAL|
THEN|CURRENT_TIMESTAMP|NOCHECK|TO|CURRENT_USER|NONCLUSTERED|TOP|CURSOR|NOT|TRAN|DATABASE|NULL|TRANSACTION|DBCC|NULLIF|TRIGGER|
DEALLOCATE|OF|TRUNCATE|DECLARE|OFF|TRY_CONVERT|DEFAULT|OFFSETS|TSEQUAL|DELETE|ON|UNION|DENY|OPEN|UNIQUE|DESC|OPENDATASOURCE|
UNPIVOT|DISK|OPENQUERY|UPDATE|DISTINCT|OPENROWSET|UPDATETEXT|DISTRIBUTED|OPENXML|USE|DOUBLE|OPTION|USER|DROP|OR|VALUES|DUMP|
ORDER|VARYING|ELSE|OUTER|VIEW|END|OVER|WAITFOR|ERRLVL|PERCENT|WHEN|ESCAPE|PIVOT|WHERE|EXCEPT|PLAN|WHILE|EXEC|PRECISION|WITH|
EXECUTE|PRIMARY|WITHIN GROUP|EXISTS|PRINT|WRITETEXT|EXIT|PROC|ABSOLUTE|OVERLAPS|ACTION|PAD|ADA|PARTIAL|PASCAL|EXTRACT|POSITION|
ALLOCATE|FALSE|PREPARE|FIRST|PRESERVE|FLOAT|ARE|PRIOR|PRIVILEGES|FORTRAN|ASSERTION|FOUND|AT|REAL|AVG|GET|GLOBAL|RELATIVE|GO|BIT|
BIT_LENGTH|BOTH|ROWS|HOUR|CASCADED|SCROLL|IMMEDIATE|SECOND|CAST|SECTION|CATALOG|INCLUDE|CHAR|SESSION|CHAR_LENGTH|INDICATOR|
CHARACTER|INITIALLY|CHARACTER_LENGTH|SIZE|INPUT|SMALLINT|INSENSITIVE|SPACE|INT|SQL|COLLATION|INTEGER|SQLCA|SQLCODE|INTERVAL|
SQLERROR|CONNECT|SQLSTATE|CONNECTION|SQLWARNING|ISOLATION|SUBSTRING|CONSTRAINTS|SUM|LANGUAGE|CORRESPONDING|LAST|TEMPORARY|COUNT|
LEADING|TIME|CROSS|LEVEL|TIMESTAMP|TIMEZONE_HOUR|LOCAL|TIMEZONE_MINUTE|LOWER|MATCH|TRAILING|MAX|MIN|TRANSLATE|DATE|MINUTE|
TRANSLATION|DAY|MODULE|TRIM|MONTH|TRUE|DEC|NAMES|DECIMAL|NATURAL|UNKNOWN|NCHAR|DEFERRABLE|NEXT|UPPER|DEFERRED|NO|USAGE|NONE|USING|
DESCRIBE|VALUE|DESCRIPTOR|DIAGNOSTICS|NUMERIC|VARCHAR|DISCONNECT|OCTET_LENGTH|DOMAIN|ONLY|WHENEVER|WORK|END-EXEC|WRITE|YEAR|
OUTPUT|ZONE|EXCEPTION {lexeme = yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Reservadas;}

/*ESPACIOS*/
{Espacio}+ {/*Ignore*/}

/*COMENTARIOS*/
"--".* {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Comentario;}
("/*" [^*] ~"*/") {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Comentario;}
("/*" "*"+ "/") {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Comentario;}
("/*"[^\r\n/]*) {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return ComentarioE;}
//("/*"([^*/])*) {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return ComentarioE;}

/*VARIABLE TIPO BIT*/
(0|1|NULL) {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Bit;}

/*VARIABLE TIPO INT*/
({Numero}) {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Int;}
("-"{Numero})|("+"{Numero}) {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Int;}

/*VARIABLE TIPO STRING*/
("'"([^'\r\n])*"'") {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return String;}
("'") {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return ERROR;}
("'"([^'\r\n]*)) {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return StringE;}

/*VARIABLE TIPO FLOAT*/
({Numero}+"."{Numero}*) {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Float;}
({Numero}+"."{Numero}*(E|e)?("+"|"-")?{Numero}?) {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Float;}

/*SIMBOLO IGUAL*/
"=" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Igual;}

/*SUMA*/
("+") {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Suma;}

/*RESTA*/
"-" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Resta;}

/*MULTIPLICACION*/
"*" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Multiplicacion;}

/*DIVISION*/
"/" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Division;}

/*SIMBOLO PORCENTAJE*/
"%" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Porcentaje;}

/*SIMBOLO MENOR QUE*/
"<" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Menor_que;}

/*SIMBOLO MENOR O IGUAL QUE*/
"<=" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Menor_o_igual_que;}

/*SIMBOLO MAYOR QUE*/
">" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Mayor_que;}

/*SIMBOLO MAYOR O IGUAL QUE*/
">=" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Mayor_o_igual_que;}

/*DOBLE ASIGNACION*/
"==" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Operador_de_Igualdad;}

/*SIMBOLO NO ES IGUAL*/
"!=" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Operador_no_igual;}

/*AND*/
"&&" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Operador_AND;}

/*OR*/
"||" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Operador_OR;}

/*SIGNO DE EXCLAMACIÓN*/
"!" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Signo_de_Exclamación;}

/*SIGNO DE INTERROGACION*/
"?" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Cierre_signo_de_Interrogacion;}

/*SIGNO DE INTERROGACION*/
"¿" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Apertura_signo_de_Interrogacion;}

/*PUNTO Y COMA*/
";" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Punto_y_coma;}

/*COMA*/
(,) {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Coma;}

/*PUNTO*/
"." {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Punto;}

/*ARROBA*/
"@" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Arroba;}

/*NUMERAL*/
"#" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Numeral;}

/*DOBLE NUMERAL*/
"##" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Doble_Numeral;}

/*CORCHETES*/
"[]" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Corchetes;}

/*LLAVES*/
"{}" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Llaves;}

/*PARENTESIS*/
"()" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Paréntesis;}

/*APERTURA DE CORCHETES*/
"[" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Apertura_de_corchetes;}

/*APERTURA DE LLAVES*/
"{" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Apertura_de_llaves;}

/*APERTURA DE PARÉNTESIS*/
"(" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Apertura_de_paréntesis;}

/*CIERRE DE CORCHETES*/
"]" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Cierre_de_corchetes;}

/*CIERRE DE LLAVES*/
"}" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Cierre_de_llaves;}

/*CIERRE DE PARÉNTESIS*/
")" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Cierre_de_paréntesis;}

/*SIMBOLO GUION BAJO*/
"_" {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Guión_bajo;}

/*IDENTIFICADOR*/
{L}({L}|{D}|{Guion})* {lexeme=yytext(); line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return Identificador;}

. {line = yyline; initialcolumn = yycolumn; finalcolumn = yycolumn + yylength()-1; return ERROR;}

/* Finaliza Sección de Reglas */
