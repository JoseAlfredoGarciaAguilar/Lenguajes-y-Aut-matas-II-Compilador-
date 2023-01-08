package codigo;
import java_cup.runtime.Symbol;
%%
%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%line
%char
L=[a-zA-Z_]+
D=[0-9]+
espacio=[ ,\t,\r,\n]+
%{
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
%}
%%

/* Espacios en blanco */
{espacio} {/*Ignore*/}

/* Comentarios */
( "//"(.)* ) {/*Ignore*/}

/* Comillas */
( "\"" ) {return new Symbol(sym.Comillas, yychar, yyline, yytext());}

/* Tipos de datos */
( int ) {return new Symbol(sym.Dato_Entero, yychar, yyline, yytext());}
( double ) {return new Symbol(sym.Dato_Double, yychar, yyline, yytext());}
( float ) {return new Symbol(sym.Dato_Float, yychar, yyline, yytext());}
( char ) {return new Symbol(sym.Dato_Char, yychar, yyline, yytext());}

( String ) {return new Symbol(sym.Cadena, yychar, yyline, yytext());}

( if ) {return new Symbol(sym.If, yychar, yyline, yytext());}
( else ) {return new Symbol(sym.Else, yychar, yyline, yytext());}
( break ) {return new Symbol(sym.Break, yychar, yyline, yytext());}
( switch ) {return new Symbol(sym.Switch, yychar, yyline, yytext());}
( case ) {return new Symbol(sym.Case, yychar, yyline, yytext());}
( do ) {return new Symbol(sym.Do, yychar, yyline, yytext());}
( while ) {return new Symbol(sym.While, yychar, yyline, yytext());}

( "=" ) {return new Symbol(sym.Igual, yychar, yyline, yytext());}
( "+" ) {return new Symbol(sym.Suma, yychar, yyline, yytext());}
( "-" ) {return new Symbol(sym.Resta, yychar, yyline, yytext());}
( "*" ) {return new Symbol(sym.Multiplicacion, yychar, yyline, yytext());}
( "/" ) {return new Symbol(sym.Division, yychar, yyline, yytext());}

/* Caracteres especiales */
( "¡" ) {return new Symbol(sym.Exclamacion_Inicial, yychar, yyline, yytext());}
( "!" ) {return new Symbol(sym.Exclamacion_Final, yychar, yyline, yytext());}
( "#" ) {return new Symbol(sym.Gato, yychar, yyline, yytext());}
( "%" ) {return new Symbol(sym.Porcentaje, yychar, yyline, yytext());}
( "&" ) {return new Symbol(sym.Y, yychar, yyline, yytext());}
( "." ) {return new Symbol(sym.Punto, yychar, yyline, yytext());}
( ":" ) {return new Symbol(sym.Dos_Puntos, yychar, yyline, yytext());}
( ";" ) {return new Symbol(sym.Punto_Y_Coma, yychar, yyline, yytext());}
( "?" ) {return new Symbol(sym.Interrogacion_Inicial, yychar, yyline, yytext());}
( "¿" ) {return new Symbol(sym.Interrogacion_Final, yychar, yyline, yytext());}
( "@" ) {return new Symbol(sym.Arroba, yychar, yyline, yytext());}
( "{" ) {return new Symbol(sym.Llave_Inicial, yychar, yyline, yytext());}
( "}" ) {return new Symbol(sym.Llave_Final, yychar, yyline, yytext());}
( "(" ) {return new Symbol(sym.Parentesis_Inicial, yychar, yyline, yytext());}
( ")" ) {return new Symbol(sym.Parentesis_Final, yychar, yyline, yytext());}

/* Marcador de inicio de algoritmo */
( "main" ) {return new Symbol(sym.Main, yychar, yyline, yytext());}

/* Identificador */
{L}({L}|{D})* {return new Symbol(sym.Identificador, yychar, yyline, yytext());}

/* Numero */
("(-"{D}+")")|{D}+ {return new Symbol(sym.Numero, yychar, yyline, yytext());}

/* Error de analisis */
 . {return new Symbol(sym.ERROR, yychar, yyline, yytext());}

//JOSÉ ALFREDO GARCÍA AGUILAR
//LENGUAJES Y AUTÓMATAS II - 12-1PM