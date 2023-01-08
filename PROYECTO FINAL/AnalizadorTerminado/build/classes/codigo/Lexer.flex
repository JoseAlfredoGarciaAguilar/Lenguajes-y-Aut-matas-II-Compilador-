package codigo;
import static codigo.Tokens.*;
%%
%class Lexer
%type Tokens
L=[a-zA-Z_]+
D=[0-9]+
espacio=[ ,\t,\r]+
%{
    public String lexeme;
%}
%%

/* Espacios en blanco */
{espacio} {/*Ignore*/}

/* Comentarios */
( "//"(.)* ) {/*Ignore*/}

/* Salto de linea */
( "\n" ) {return Linea;}

/* Comillas */
( "\"" ) {lexeme=yytext(); return Comillas;}

/* Tipos de datos */
"int" {lexeme=yytext(); return Dato_Entero;}
"double" {lexeme=yytext(); return Dato_Double;}
"float" {lexeme=yytext(); return Dato_Float;}
"char" {lexeme=yytext(); return Dato_Char;}

/* Tipo de dato String */
( String ) {lexeme=yytext(); return Cadena;}

/* Palabras Reservadas */
( if ) {lexeme=yytext(); return If;}
( else ) {lexeme=yytext(); return Else;}
( break ) {lexeme=yytext(); return Break;}
( switch ) {lexeme=yytext(); return Switch;}
( case ) {lexeme=yytext(); return Case;}
( do ) {lexeme=yytext(); return Do;}
( while ) {lexeme=yytext(); return While;}

/* Simbolos Matematicos */
"=" {lexeme=yytext(); return Igual;}
"+" {lexeme=yytext(); return Suma;}
"-" {lexeme=yytext(); return Resta;}
"*" {lexeme=yytext(); return Multiplicacion;}
"/" {lexeme=yytext(); return Division;}

/* Caracteres especiales */
"¡" {lexeme=yytext(); return Exclamacion_Inicial;}
("!") {lexeme=yytext(); return Exclamacion_Final;}
"#" {lexeme=yytext(); return Gato;}
"%" {lexeme=yytext(); return Porcentaje;}
"&" {lexeme=yytext(); return Y;}
"." {lexeme=yytext(); return Punto;}
":" {lexeme=yytext(); return Dos_Puntos;}
";" {lexeme=yytext(); return Punto_Y_Coma;}
("?") {lexeme=yytext(); return Interrogacion_Inicial;}
"¿" {lexeme=yytext(); return Interrogacion_Final;}
"@" {lexeme=yytext(); return Arroba;}
("{") {lexeme=yytext(); return Llave_Inicial;}
"}" {lexeme=yytext(); return Llave_Final;}
"(" {lexeme=yytext(); return Parentesis_Inicial;}
")" {lexeme=yytext(); return Parentesis_Final;}

/* Marcador de inicio de algoritmo */
"main" {lexeme=yytext(); return Main;}

/* Identificador */
{L}({L}|{D})* {lexeme=yytext(); return Identificador;}

/* Numero */
("(-"{D}+")")|{D}+ {lexeme=yytext(); return Numero;}

/* Error de analisis */
 . {return ERROR;}

//JOSÉ ALFREDO GARCÍA AGUILAR
//LENGUAJES Y AUTÓMATAS II - 12-1PM