/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package codigo;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.Symbol;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import java.util.Scanner;

/**
 *
 * @author ALFREDO GARCIA
 */
public class FrmPrincipal extends javax.swing.JFrame {

    private ArrayList<String[]> arreglo = new ArrayList<String[]>();
    private ArrayList<String[]> a = new ArrayList<String[]>();
    private ArrayList<String[]> arreglo_tabla = new ArrayList<String[]>();

    /**
     * Creates new form FrmPrincipal
     */
    public FrmPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Analizador Léxico (Scanner), Sintáctico (Párser), Semántico, Tabla de Símbolos, Código Intermedio y Código Objeto");
        this.getContentPane().setBackground(Color.gray);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void analizarLexico() throws IOException {
        arreglo.clear();
        int cont = 1;

        String expr = (String) txtEntrada.getText();
        Lexer lexer = new Lexer(new StringReader(expr));
        String resultado = "LINEA " + "\tQUÉ ES\n";
        while (true) {
            Tokens token = lexer.yylex();
            if (token == null) {
                txtResultadoLex.setText(resultado);
                for (int i = 0; i < arreglo.size(); i++) {
                    System.out.println(arreglo.get(i)[0] + " | " + arreglo.get(i)[1]);

                }
                return;
            }
            switch (token) {
                case ERROR:
                    resultado += "\t Simbolo no definido\n";
                    arreglo.add(new String[]{lexer.lexeme, "ERROR (" + token + ")"});
                    break;
                case Dato_Entero:
                case Dato_Double:
                case Dato_Float:
                case Dato_Char:
                    //resultado += lexer.lexeme + " \t" + "Tipo Dato (" + token + ")\n";
                    resultado += lexer.lexeme + " \t" + "Tipo Dato (" + token + ")\n";
                    arreglo.add(new String[]{lexer.lexeme, "Tipo Dato (" + token + ")"});
                    break;
                case If:
                case Else:
                case Break:
                case Switch:
                case Case:
                case Do:
                case While:
                    resultado += lexer.lexeme + " \t" + "Reservada\n";
                    arreglo.add(new String[]{lexer.lexeme, "Reservada (" + token + ")"});
                    break;
                case Igual:
                case Suma:
                case Resta:
                case Multiplicacion:
                case Division:
                    resultado += lexer.lexeme + " \t" + "Símbolo Matemático (" + token + ")\n";
                    arreglo.add(new String[]{lexer.lexeme, "Símbolo Matemático (" + token + ")"});
                    break;
                case Exclamacion_Inicial:
                case Exclamacion_Final:
                case Gato:
                case Porcentaje:
                case Y:
                case Punto:
                case Dos_Puntos:
                case Punto_Y_Coma:
                case Interrogacion_Inicial:
                case Interrogacion_Final:
                case Arroba:
                case Llave_Inicial:
                case Llave_Final:
                case Parentesis_Inicial:
                case Parentesis_Final:
                    resultado += lexer.lexeme + " \t" + "Carácter Especial (" + token + ")\n";
                    arreglo.add(new String[]{lexer.lexeme, "Carácter Especial (" + token + ")"});
                    break;
                case Main:
                    resultado += lexer.lexeme + " \t" + "Main\n";
                    arreglo.add(new String[]{lexer.lexeme, "Main (" + token + ")"});
                    break;
                case Identificador:
                    resultado += lexer.lexeme + " \t" + "Identificador\n";
                    arreglo.add(new String[]{lexer.lexeme, "Identificador (" + token + ")"});
                    break;
                case Numero:
                    resultado += lexer.lexeme + " \t" + token + "\n";
                    arreglo.add(new String[]{lexer.lexeme, "Número (" + token + ")"});
                    break;
                default:
                    resultado += lexer.lexeme + " \t" + token + "\n";
                    arreglo.add(new String[]{lexer.lexeme, "Default (" + token + ")"});
                    break;
            }
        }
    }

    private void analizarSemantico() throws IOException {
        arreglo_tabla.clear();
        String expr = (String) txtEntrada.getText();
        Lexer lexer = new Lexer(new StringReader(expr));
        while (true) {

            Tokens token = lexer.yylex();
            if (token == null) {
                for (int i = 0; i < arreglo.size(); i++) {
                    //System.out.println(arreglo.get(i)[0] + " | " + arreglo.get(i)[1]);
                    //System.out.println(arreglo.get(i)[0]);

                    if (arreglo.get(i)[0].equals("int")) {

                        //System.out.println("Es un int");
                        if (arreglo.get(i + 1)[1].equals("Identificador (Identificador)")) {
                            if (arreglo.get(i + 2)[1].equals("Carácter Especial (Punto_Y_Coma)")) {
                                arreglo_tabla.add(new String[]{arreglo.get(i + 1)[0], "0"});
                                i += 2;
                            } else if (arreglo.get(i + 2)[1].equals("Número (Numero)")) {
                                System.out.println(arreglo.get(i + 1)[0]);
                                arreglo_tabla.add(new String[]{arreglo.get(i + 1)[0], arreglo.get(i + 2)[0]});
                                i += 3;
                            }
                        }
                    }

                    if (arreglo.get(i)[1].equals("Identificador (Identificador)")) {
                        if (arreglo.get(i + 1)[1].equals("Número (Numero)")) {
                            if (arreglo.get(i + 2)[1].equals("Símbolo Matemático (Multiplicacion)")) {
                                int temp = Integer.valueOf(arreglo.get(i + 1)[0]) * Integer.valueOf(arreglo.get(i + 3)[0]);
                                int posicion = ArregloIndex(arreglo.get(i)[0]);
                                arreglo_tabla.set(posicion, new String[]{arreglo.get(i)[0], temp + ""});
                                i += 4;
                            }
                        } else {
                            //Corregir
                            arreglo_tabla.add(new String[]{arreglo.get(i)[0], arreglo.get(i + 1)[0]});
                            i += 2;
                        }
                    }

                    if (arreglo.get(i)[0].equals("double")) {
                        if (arreglo.get(i + 1)[1].equals("Identificador (Identificador)")) {
                            if (arreglo.get(i + 2)[1].equals("Carácter Especial (Punto_Y_Coma)")) {
                                arreglo_tabla.add(new String[]{arreglo.get(i + 1)[0], "0"});
                                i += 2;
                            } else if (arreglo.get(i + 2)[1].equals("Número (Numero)")) {
                                System.out.println(arreglo.get(i + 1)[0]);
                                arreglo_tabla.add(new String[]{arreglo.get(i + 1)[0], arreglo.get(i + 2)[0]});
                                i += 3;
                            }
                        }
                    }
                    if (arreglo.get(i)[0].equals("float")) {
                        //System.out.println("Es un float");
                        if (arreglo.get(i + 1)[1].equals("Identificador (Identificador)")) {
                            if (arreglo.get(i + 2)[1].equals("Carácter Especial (Punto_Y_Coma)")) {
                                arreglo_tabla.add(new String[]{arreglo.get(i + 1)[0], "0"});
                                i += 2;
                            }
                            if (arreglo.get(i + 2)[1].equals("Símbolo Matemático (Igual)")) {
                                arreglo_tabla.add(new String[]{arreglo.get(i + 1)[0], arreglo.get(i + 3)[0]});
                                i += 4;
                            }
                        }
                    }
                    if (arreglo.get(i)[0].equals("char")) {
                        //System.out.println("Es un char");
                        if (arreglo.get(i + 1)[1].equals("Identificador (Identificador)")) {
                            if (arreglo.get(i + 2)[1].equals("Carácter Especial (Punto_Y_Coma)")) {
                                arreglo_tabla.add(new String[]{arreglo.get(i + 1)[0], "0"});
                                i += 2;
                            }
                            if (arreglo.get(i + 2)[1].equals("Símbolo Matemático (Igual)")) {
                                arreglo_tabla.add(new String[]{arreglo.get(i + 1)[0], arreglo.get(i + 3)[0]});
                                i += 4;
                            }
                        }
                    }
                }
                return;
            }
        }
    }

    private void LlenarTabla() {
        DefaultTableModel dm = (DefaultTableModel) tabla_s.getModel();
        while (dm.getRowCount() > 0) {
            dm.removeRow(0);
        }

        for (int i = 0; i < arreglo_tabla.size(); i++) {
            //dm.addRow(arreglo.get(i+1)[0], arreglo_tabla.get(i)[0], arreglo_tabla.get(i)[0], arreglo_tabla.get(i)[1]);
            dm.addRow(new Object[]{arreglo_tabla.get(i)[0], arreglo_tabla.get(i)[1], i * 2});
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAnalizarLex = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtResultadoLex = new javax.swing.JTextArea();
        btnAnalizarSint = new javax.swing.JButton();
        txtResultadoSint = new javax.swing.JTextField();
        btnArchivo = new javax.swing.JButton();
        btnLimpiarTodo = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtEntrada = new javax.swing.JTextArea();
        btnCodigoIntermedio = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla_s = new javax.swing.JTable();
        btnAnalizarSeman = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtCodigoObjeto = new javax.swing.JTextArea();
        btnCodigoObjeto = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtCodigoIntermedio = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 102, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btnAnalizarLex.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        btnAnalizarLex.setText("Analizador Léxico");
        btnAnalizarLex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizarLexActionPerformed(evt);
            }
        });

        txtResultadoLex.setColumns(20);
        txtResultadoLex.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txtResultadoLex.setRows(5);
        txtResultadoLex.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                txtResultadoLexAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(txtResultadoLex);

        btnAnalizarSint.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        btnAnalizarSint.setText("Analizador Sintáctico");
        btnAnalizarSint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizarSintActionPerformed(evt);
            }
        });

        txtResultadoSint.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txtResultadoSint.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                txtResultadoSintAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        txtResultadoSint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtResultadoSintActionPerformed(evt);
            }
        });

        btnArchivo.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        btnArchivo.setText("Abrir Archivo");
        btnArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchivoActionPerformed(evt);
            }
        });

        btnLimpiarTodo.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        btnLimpiarTodo.setText("Limpiar Todo");
        btnLimpiarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarTodoActionPerformed(evt);
            }
        });

        txtEntrada.setColumns(20);
        txtEntrada.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txtEntrada.setRows(5);
        txtEntrada.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                txtEntradaAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane2.setViewportView(txtEntrada);

        btnCodigoIntermedio.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        btnCodigoIntermedio.setText("Código Intermedio");
        btnCodigoIntermedio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCodigoIntermedioActionPerformed(evt);
            }
        });

        tabla_s.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Variable", "Valor", "Memoria"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_s.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tabla_sAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane3.setViewportView(tabla_s);
        if (tabla_s.getColumnModel().getColumnCount() > 0) {
            tabla_s.getColumnModel().getColumn(0).setResizable(false);
            tabla_s.getColumnModel().getColumn(1).setResizable(false);
            tabla_s.getColumnModel().getColumn(2).setResizable(false);
        }

        btnAnalizarSeman.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        btnAnalizarSeman.setText("Analizador Semántico");
        btnAnalizarSeman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizarSemanActionPerformed(evt);
            }
        });

        txtCodigoObjeto.setColumns(20);
        txtCodigoObjeto.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txtCodigoObjeto.setRows(5);
        txtCodigoObjeto.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                txtCodigoObjetoAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane4.setViewportView(txtCodigoObjeto);

        btnCodigoObjeto.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        btnCodigoObjeto.setText("Código Objeto");
        btnCodigoObjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCodigoObjetoActionPerformed(evt);
            }
        });

        txtCodigoIntermedio.setColumns(20);
        txtCodigoIntermedio.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txtCodigoIntermedio.setRows(5);
        txtCodigoIntermedio.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                txtCodigoIntermedioAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane5.setViewportView(txtCodigoIntermedio);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addComponent(txtResultadoSint)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAnalizarLex)
                        .addGap(55, 55, 55)
                        .addComponent(btnArchivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                        .addComponent(btnLimpiarTodo))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAnalizarSeman)
                            .addComponent(btnAnalizarSint)
                            .addComponent(btnCodigoObjeto)
                            .addComponent(btnCodigoIntermedio))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnalizarLex)
                    .addComponent(btnArchivo)
                    .addComponent(btnLimpiarTodo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAnalizarSint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtResultadoSint, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAnalizarSeman, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCodigoIntermedio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCodigoObjeto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnalizarLexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizarLexActionPerformed
        try {
            analizarLexico();
        } catch (IOException ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAnalizarLexActionPerformed

    private void btnAnalizarSintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizarSintActionPerformed
        String ST = txtEntrada.getText();
        Sintax s = new Sintax(new codigo.LexerCup(new StringReader(ST)));

        try {
            s.parse();
            txtResultadoSint.setText("¡Análisis Correcto!");
        } catch (Exception ex) {
            Symbol sym = s.getS();
            txtResultadoSint.setText("Error de sintaxis en la Linea: " + (sym.right + 1) + ", Columna: " + (sym.left + 1) + ", Texto: \"" + sym.value + "\"");
            //txtResultadoSint.setForeground(Color.red);
        }
    }//GEN-LAST:event_btnAnalizarSintActionPerformed

    private void txtResultadoSintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtResultadoSintActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtResultadoSintActionPerformed

    private void btnArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchivoActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File archivo = new File(chooser.getSelectedFile().getAbsolutePath());

        try {
            String ST = new String(Files.readAllBytes(archivo.toPath()));
            txtEntrada.setText(ST);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnArchivoActionPerformed

    private void txtResultadoLexAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtResultadoLexAncestorAdded
        // TODO add your handling code here:
        txtResultadoLex.setEditable(false); //SIRVE PARA QUE NO SE PUEDA EDITAR EL CONTENIDO DEL JTextArea
    }//GEN-LAST:event_txtResultadoLexAncestorAdded

    private void txtResultadoSintAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtResultadoSintAncestorAdded
        // TODO add your handling code here:
        txtResultadoSint.setText(null); //LIMPIA EL BOTON
        txtResultadoSint.setEditable(false); //SIRVE PARA QUE NO SE PUEDA EDITAR EL CONTENIDO DEL JTextArea
    }//GEN-LAST:event_txtResultadoSintAncestorAdded

    private void btnLimpiarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarTodoActionPerformed
        // TODO add your handling code here:
        txtResultadoLex.setText(null); //LIMPIA EL BOTON

        txtResultadoSint.setText(null);

        DefaultTableModel dm = (DefaultTableModel) tabla_s.getModel();
        while (dm.getRowCount() > 0) {
            dm.removeRow(0);
        }

        txtCodigoIntermedio.setText(null);

        txtCodigoObjeto.setText(null);

        txtResultadoLex.setEditable(false); //SIRVE PARA QUE NO SE PUEDA EDITAR EL CONTENIDO DEL JTextArea
    }//GEN-LAST:event_btnLimpiarTodoActionPerformed

    private void txtEntradaAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtEntradaAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEntradaAncestorAdded

    private void btnCodigoIntermedioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCodigoIntermedioActionPerformed
        // TODO add your handling code here:
        String ST = txtEntrada.getText();
        Sintax s = new Sintax(new codigo.LexerCup(new StringReader(ST)));
        try {
            s.parse();
            analizarIntermedio(); //hacer version traductor
        } catch (Exception ex) {
            Symbol sym = s.getS();
        }
    }//GEN-LAST:event_btnCodigoIntermedioActionPerformed

    private void btnAnalizarSemanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizarSemanActionPerformed
        // TODO add your handling code here:
        String ST = txtEntrada.getText();
        Sintax s = new Sintax(new codigo.LexerCup(new StringReader(ST)));
        try {
            s.parse();
            analizarSemantico();
            LlenarTabla();
        } catch (Exception ex) {
            Symbol sym = s.getS();
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_btnAnalizarSemanActionPerformed

    private void tabla_sAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tabla_sAncestorAdded
        // TODO add your handling code here:
        String ST = txtEntrada.getText();
        Sintax s = new Sintax(new codigo.LexerCup(new StringReader(ST)));
        try {
            s.parse();
            analizarSemantico();
            LlenarTabla();
        } catch (Exception ex) {
            Symbol sym = s.getS();
            System.out.println("ENTRO AQUI");
        }
    }//GEN-LAST:event_tabla_sAncestorAdded

    private void txtCodigoObjetoAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtCodigoObjetoAncestorAdded
        // TODO add your handling code here:
        txtCodigoObjeto.setEditable(false);
    }//GEN-LAST:event_txtCodigoObjetoAncestorAdded

    private void btnCodigoObjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCodigoObjetoActionPerformed

        try {
            analizarObjeto(); //hacer version traductor
        } catch (Exception ex) {
            System.out.println("ERROR");
            ex.getMessage();

        }
    }//GEN-LAST:event_btnCodigoObjetoActionPerformed

    private void txtCodigoIntermedioAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtCodigoIntermedioAncestorAdded
        // TODO add your handling code here:
        txtCodigoIntermedio.setEditable(false);
    }//GEN-LAST:event_txtCodigoIntermedioAncestorAdded

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
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnalizarLex;
    private javax.swing.JButton btnAnalizarSeman;
    private javax.swing.JButton btnAnalizarSint;
    private javax.swing.JButton btnArchivo;
    private javax.swing.JButton btnCodigoIntermedio;
    private javax.swing.JButton btnCodigoObjeto;
    private javax.swing.JButton btnLimpiarTodo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable tabla_s;
    private javax.swing.JTextArea txtCodigoIntermedio;
    private javax.swing.JTextArea txtCodigoObjeto;
    private javax.swing.JTextArea txtEntrada;
    private javax.swing.JTextArea txtResultadoLex;
    private javax.swing.JTextField txtResultadoSint;
    // End of variables declaration//GEN-END:variables

    private void Tabla_Simbolos(Tokens tokens, Lexer lexer) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void analizarIntermedio() throws IOException, BadLocationException {
        int contadorv = 0;
        txtCodigoIntermedio.setText("\n\n");
        String expr = (String) txtEntrada.getText();
        Lexer lexer = new Lexer(new StringReader(expr));
        while (true) {

            Tokens token = lexer.yylex();
            if (token == null) {
                for (int i = 0; i < arreglo.size(); i++) {
                    //System.out.println(arreglo.get(i)[0] + " | " + arreglo.get(i)[1]);
                    //System.out.println(arreglo.get(i)[0]);
                    if (arreglo.get(i)[0].equals("int")) {
                        //System.out.println("Es un int");
                        if (arreglo.get(i + 1)[1].equals("Identificador (Identificador)")) {
                            // identificador dw ?
                            txtCodigoIntermedio.insert(arreglo.get(i + 1)[0] + " dw ?\n", txtCodigoIntermedio.getLineEndOffset(contadorv));
                            contadorv++;
                            if (arreglo.get(i + 2)[1].equals("Número (Numero)")) {
                                txtCodigoIntermedio.setText(txtCodigoIntermedio.getText() + "MOV " + arreglo.get(i + 1)[0] + ", " + arreglo.get(i + 2)[0] + "\n");
                                i += 3;
                                //Mov identificador, numero
                            } else {
                                i += 2;
                            }
                        }
                    } else if (arreglo.get(i)[1].equals("Identificador (Identificador)")) {
                        System.out.println("Entro : 1");
                        if (arreglo.get(i + 1)[1].equals("Número (Numero)")) {
                            System.out.println("Entro : 2");
                            if (arreglo.get(i + 2)[1].equals("Símbolo Matemático (Multiplicacion)")) {
                                System.out.println("Entro : 3");
                                txtCodigoIntermedio.setText(txtCodigoIntermedio.getText() + "MOV AX, " + arreglo.get(i + 1)[0] + "\n");
                                txtCodigoIntermedio.setText(txtCodigoIntermedio.getText() + "MUL AX, " + arreglo.get(i + 3)[0] + "\n");
                                txtCodigoIntermedio.setText(txtCodigoIntermedio.getText() + "MOV " + arreglo.get(i)[0] + ", EAX\n");
                                i += 4;
                            }
                        } else {
                            //Corregir
                            System.out.println(arreglo.get(i)[0]);
                            System.out.println(arreglo.get(i + 1)[0]);
                            arreglo_tabla.add(new String[]{arreglo.get(i)[0], arreglo.get(i + 1)[0]});
                            i += 2;
                        }
                    }

                    if (arreglo.get(i)[0].equals("double")) {
                        if (arreglo.get(i + 1)[1].equals("Identificador (Identificador)")) {
                            if (arreglo.get(i + 2)[1].equals("Carácter Especial (Punto_Y_Coma)")) {
                                arreglo_tabla.add(new String[]{arreglo.get(i + 1)[0], "0"});
                                i += 2;
                            } else if (arreglo.get(i + 2)[1].equals("Número (Numero)")) {
                                System.out.println(arreglo.get(i + 1)[0]);
                                arreglo_tabla.add(new String[]{arreglo.get(i + 1)[0], arreglo.get(i + 2)[0]});
                                i += 3;
                            }
                        }
                    }
                    if (arreglo.get(i)[0].equals("float")) {
                        //System.out.println("Es un float");
                        if (arreglo.get(i + 1)[1].equals("Identificador (Identificador)")) {
                            if (arreglo.get(i + 2)[1].equals("Carácter Especial (Punto_Y_Coma)")) {
                                arreglo_tabla.add(new String[]{arreglo.get(i + 1)[0], "0"});
                                i += 2;
                            }
                            if (arreglo.get(i + 2)[1].equals("Símbolo Matemático (Igual)")) {
                                arreglo_tabla.add(new String[]{arreglo.get(i + 1)[0], arreglo.get(i + 3)[0]});
                                i += 4;
                            }
                        }
                    }
                    if (arreglo.get(i)[0].equals("char")) {
                        //System.out.println("Es un char");
                        if (arreglo.get(i + 1)[1].equals("Identificador (Identificador)")) {
                            if (arreglo.get(i + 2)[1].equals("Carácter Especial (Punto_Y_Coma)")) {
                                arreglo_tabla.add(new String[]{arreglo.get(i + 1)[0], "0"});
                                i += 2;
                            }
                            if (arreglo.get(i + 2)[1].equals("Símbolo Matemático (Igual)")) {
                                arreglo_tabla.add(new String[]{arreglo.get(i + 1)[0], arreglo.get(i + 3)[0]});
                                i += 4;
                            }
                        }
                    }
                }
                return;
            }
        }
    }

    //te detecta si lo que estás ingresando es un número
    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void analizarObjeto() throws IOException {
        Scanner texto = new Scanner(txtCodigoIntermedio.getText());
        String resultado = "";
        ArrayList<String> arr = new ArrayList(); //arraylist de strings para guardar
        while (texto.hasNextLine()) { //booleano que pregunta si hay una siguiente linea
            String Line = texto.nextLine(); //el scanner va leyendo todo
            String[] linea = new String[3];
            if (Line.length() == 0) {
                continue;
            }                                                                          //opcode
            if (Line.split(" ").length == 3) { //splir separa Strings en varias partes Ejemplo MOV AX, 20
                //replace permite cambiar un caracter por otro dentro de una cadena
                linea[0] = Line.split(" ")[0]; //MOV
                linea[1] = Line.split(" ")[1].replace(",", ""); //AX (destino)
                linea[2] = Line.split(" ")[2].replace(",", ""); //20 (fuente)
            } else { // if(Line.split(" ").length==4)
                linea[0] = Line.split(" ")[0];
                linea[1] = Line.split(" ")[1];
                linea[2] = Line.split(" ")[3];
            }

            //codigo de operacion
            //cod. op, direccion
            //cod. op, direccion1, direccion2
            //cod. op, direccion1, direccion2, direccion3
            if (linea[0].equals("MOV")) {
                if (linea[1].equals("AX")) {
                    if (isNumeric(linea[2])) {
                        //MOV AX 20
                        String binario = "10111000"; //1011wrrr datos
                        String n = Integer.toBinaryString(Integer.valueOf(linea[2])); //acepta un int y regresa el string
                        //en binario
                        n = Agregar0(n, 8);
                        //2 = 11
                        binario += " " + n;
                        resultado += " " + binario + " \n";
                    }
                } else { // Es Variable
                    if (isNumeric(linea[2])) { // Es Inmediato.
                        //MOV VARIABLE 20
                        String binario = "1100101 00000110";
                        //Datos
                        String n = Integer.toBinaryString(Integer.valueOf(linea[2]));
                        n = Agregar0(n, 8);
                        //2 = 11
                        binario += " " + n;
                        //Desplacamiento
                        int direcion = arr.indexOf(linea[1]) * 2;
                        n = Integer.toBinaryString(direcion);
                        n = Agregar0(n, 8);
                        binario += " " + n;
                        resultado += " " + binario + " \n";
                    } else {// Es Registro
                        // MOV VARIABLE3 EAX
                        //MOV MEM REG
                        String binario = "10001001 00000110";
                        int direcion = arr.indexOf(linea[1]) * 2;
                        String n = Integer.toBinaryString(direcion);
                        n = Agregar0(n, 8);
                        binario += " " + n;
                        resultado += " " + binario + " \n";
                    }

                }
            } else if (linea[0].equals("MUL")) {
                if (linea[1].equals("AX")) {
                    if (isNumeric(linea[2])) {
                        //MUL 5
                        String binario = "11110111 00100110";

                        String n = Integer.toBinaryString(Integer.valueOf(linea[2]));
                        n = Agregar0(n, 8);
                        n = Agregar0(n, 8);
                        binario += " " + n;
                        resultado += " " + binario + " \n";

                    }
                }
            } else {
                int direcion = arr.size() * 2;
                String n = Integer.toBinaryString(direcion);
                n = Agregar0(n, 8);
                arr.add(linea[0]);
                resultado += n + " 00000000 00000000\n";

            }
        }
        txtCodigoObjeto.setText(resultado);
    }

    private int ArregloIndex(String string) {
        for (int i = 0; i < arreglo_tabla.size(); i++) {
            if (arreglo_tabla.get(i)[0].equals(string)) {
                return i;
            }
        }
        return 0;
    }

    private String Agregar0(String palabra, int tamaño) {
        int resta = tamaño - palabra.length();
        String p = "";

        for (int i = 0; i <= resta; i++) {
            p += "0";
        }

        return p + palabra;
    }
}
//JOSÉ ALFREDO GARCÍA AGUILAR
//LENGUAJES Y AUTÓMATAS II - 12-1PM