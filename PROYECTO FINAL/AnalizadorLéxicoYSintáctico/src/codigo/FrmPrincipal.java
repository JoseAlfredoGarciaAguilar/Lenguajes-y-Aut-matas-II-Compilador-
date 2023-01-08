/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package codigo;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.Symbol;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author ALFREDO GARCIA
 */
public class FrmPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form FrmPrincipal
     */
    public FrmPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Analizador Léxico (Scanner) y Sintáctico (Parser)");
        this.getContentPane().setBackground(Color.gray);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void analizarLexico() throws IOException {
        int cont = 1;

        String expr = (String) txtEntrada.getText();
        Lexer lexer = new Lexer(new StringReader(expr));
        String resultado = "LINEA " + "\tQUÉ ES\n";
        while (true) {
            Tokens token = lexer.yylex();
            if (token == null) {
                txtResultadoLex.setText(resultado);
                return;
            }
            switch (token) {
                case ERROR:
                    resultado += "\t Simbolo no definido\n";
                    break;
                case Dato_Entero:
                case Dato_Double:
                case Dato_Float:
                case Dato_Char:
                    resultado += lexer.lexeme + " \t" + "Tipo Dato (" + token + ")\n";
                    break;
                case If:
                case Else:
                case Break:
                case Switch:
                case Case:
                case Do:
                case While:
                    resultado += lexer.lexeme + " \t" + "Reservada\n";
                    break;
                case Igual:
                case Suma:
                case Resta:
                case Multiplicacion:
                case Division:
                    resultado += lexer.lexeme + " \t" + "Símbolo Matemático (" + token + ")\n";
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
                    break;
                case Main:
                    resultado += lexer.lexeme + " \t" + "Main\n";
                    break;
                case Identificador:
                    resultado += lexer.lexeme + " \t" + "Identificador\n";
                    break;
                case Numero:
                    resultado += lexer.lexeme + " \t" + token + "\n";
                    break;
                default:
                    resultado += lexer.lexeme + " \t" + token + "\n";
                    break;
            }
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
        btnLimpiarSint = new javax.swing.JButton();
        btnArchivo = new javax.swing.JButton();
        btnLimpiarLex = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtEntrada = new javax.swing.JTextArea();

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

        btnLimpiarSint.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        btnLimpiarSint.setText("Limpiar Sintáctico");
        btnLimpiarSint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarSintActionPerformed(evt);
            }
        });

        btnArchivo.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        btnArchivo.setText("Abrir Archivo");
        btnArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchivoActionPerformed(evt);
            }
        });

        btnLimpiarLex.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        btnLimpiarLex.setText("Limpiar Léxico");
        btnLimpiarLex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarLexActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtResultadoSint)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAnalizarLex)
                        .addGap(55, 55, 55)
                        .addComponent(btnArchivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpiarLex))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAnalizarSint)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpiarSint)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnalizarLex)
                    .addComponent(btnArchivo)
                    .addComponent(btnLimpiarLex))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnalizarSint)
                    .addComponent(btnLimpiarSint))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtResultadoSint, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

    private void btnLimpiarSintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarSintActionPerformed
        // TODO add your handling code here:
        txtResultadoSint.setText(null);
    }//GEN-LAST:event_btnLimpiarSintActionPerformed

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

    private void btnLimpiarLexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarLexActionPerformed
        // TODO add your handling code here:
        txtResultadoLex.setText(null); //LIMPIA EL BOTON
        txtResultadoLex.setEditable(false); //SIRVE PARA QUE NO SE PUEDA EDITAR EL CONTENIDO DEL JTextArea
    }//GEN-LAST:event_btnLimpiarLexActionPerformed

    private void txtEntradaAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtEntradaAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEntradaAncestorAdded

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
    private javax.swing.JButton btnAnalizarSint;
    private javax.swing.JButton btnArchivo;
    private javax.swing.JButton btnLimpiarLex;
    private javax.swing.JButton btnLimpiarSint;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txtEntrada;
    private javax.swing.JTextArea txtResultadoLex;
    private javax.swing.JTextField txtResultadoSint;
    // End of variables declaration//GEN-END:variables

    private void Tabla_Simbolos(Tokens tokens, Lexer lexer) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
//JOSÉ ALFREDO GARCÍA AGUILAR
//LENGUAJES Y AUTÓMATAS II - 12-1PM
