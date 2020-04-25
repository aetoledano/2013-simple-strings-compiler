package run;

import AST.ASTProgram;
import Errors.ErrorReporter;
import OUT.FileOps;
import Stream.StringSourceStream;
import SymbolTable.SymbolTable;

import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.Buffer;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import Lexer.Lexer;
import Parser.Parser;
import Checker.Checker;
import Coder.Encoder;
import OUT.Interpreter;
import Errors.CompilerError;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class GUI extends javax.swing.JFrame {

    public void BuildAndRun(boolean only_compile) {
        jTextArea1.setText("");
        paint(getGraphics());
        Lexer l = null;
        Parser p = null;
        ASTProgram astp = null;
        Checker ch = null;
        ErrorReporter err = null;
        Stream.StringSourceStream st = new StringSourceStream(jTextPane1.getText());
        try {
            l = new Lexer(st, new SymbolTable(), (err = new Errors.ErrorReporter()));
            if (err.isEmpty()) {
                p = new Parser(l, err);
                astp = p.Parse();
                if (err.isEmpty()) {
                    ch = new Checker(p.getInput().getSymbolsTable(), err);
                    ch.Visit(astp);
                    if (err.isEmpty()) {
                        jTextArea1.append("Compiled OK\n");
                        if (!only_compile) {
                            Coder.Encoder e = new Encoder(l.getSymbolsTable());
                            e.Visit(astp);
                            Interpreter i = new Interpreter(e.getCode());
                            i.setOut(jTextArea1);
                            try {
                                i.Execute();
                            } catch (Exception ex) {
                                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
            for (CompilerError e : err) {
                jTextArea1.append(e.toString());
            }
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Runnable run = new Runnable() {

        public void run() {
            jProgressBar1.setIndeterminate(true);
            BuildAndRun(false);
            jProgressBar1.setIndeterminate(false);
        }

    };

    public GUI() throws IOException {
        initComponents();

        final StyleContext cont = StyleContext.getDefaultStyleContext();
        final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
        final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        final AttributeSet attrGreen = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);

        String ReservedWordsRegex = "(\\W)*(main|int|string|intercept|substring|concat|difference|print)";
        String CharsRegex = "(\\W)*(0|1|2|3|4|5|6|7|8|9)*";

        DefaultStyledDocument doc = new DefaultStyledDocument() {
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offset);
                if (before < 0) {
                    before = 0;
                }
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                        if (text.substring(wordL, wordR).matches(ReservedWordsRegex)) {
                            setCharacterAttributes(wordL, wordR - wordL, attr, false);
                        } else if (text.substring(wordL, wordR).matches(CharsRegex)) {
                            setCharacterAttributes(wordL, wordR - wordL, attrGreen, false);
                        } else {
                            setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                        }
                        wordL = wordR;
                    }
                    wordR++;
                }
            }

            public void remove(int offs, int len) throws BadLocationException {
                super.remove(offs, len);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offs);
                if (before < 0) {
                    before = 0;
                }
                int after = findFirstNonWordChar(text, offs);
                if (text.substring(before, after).matches(ReservedWordsRegex)) {
                    setCharacterAttributes(before, after - before, attr, false);
                } else if (text.substring(before, after).matches(CharsRegex)) {
                    setCharacterAttributes(before, after - before, attrGreen, false);
                } else {
                    setCharacterAttributes(before, after - before, attrBlack, false);
                }
            }
        };
        TextLineNumber tln = new TextLineNumber(jTextPane1);
        jScrollPane1.setRowHeaderView(tln);

        jTextPane1.setStyledDocument(doc);
        setLocationRelativeTo(null);
        setTitle("Strings Compiler");
        File default_code_sample = new File("default_code_sample.str");
        BufferedReader reader = new BufferedReader(new FileReader(default_code_sample));
        StringBuilder buff = new StringBuilder();
        reader.lines().forEach(line -> {
            buff.append(line + "\n");
        });
        reader.close();
        jTextPane1.setText(buff.toString());
        jTextPane1.setCaretPosition(16);
    }

    private int findLastNonWordChar(String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar(String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonNEW = new javax.swing.JButton();
        open = new javax.swing.JButton();
        save = new javax.swing.JButton();
        jButtonCompile = new javax.swing.JButton();
        jButtonRun = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(150, 0), new java.awt.Dimension(150, 0), new java.awt.Dimension(150, 32767));
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(150, 0), new java.awt.Dimension(150, 0), new java.awt.Dimension(150, 32767));
        jProgressBar1 = new javax.swing.JProgressBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuCompile = new javax.swing.JMenuItem();
        jMenuRun = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Press F6 to Compile&Run", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP));
        jTextPane1.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jTextPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextPane1KeyPressed(evt);
            }

            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextPane1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTextPane1);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Console Output", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));
        jScrollPane2.setViewportView(jTextArea1);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButtonNEW.setText("New");
        jButtonNEW.setFocusable(false);
        jButtonNEW.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonNEW.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonNEW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNEWActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonNEW);

        open.setText("Open");
        open.setFocusable(false);
        open.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        open.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openActionPerformed(evt);
            }
        });
        jToolBar1.add(open);

        save.setText("Save");
        save.setFocusable(false);
        save.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        save.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        jToolBar1.add(save);

        jButtonCompile.setText("Compile");
        jButtonCompile.setFocusable(false);
        jButtonCompile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonCompile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonCompile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompileActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonCompile);

        jButtonRun.setText("Run");
        jButtonRun.setFocusable(false);
        jButtonRun.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonRun.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonRun);
        jToolBar1.add(filler4);
        jToolBar1.add(filler1);
        jToolBar1.add(jProgressBar1);

        jMenu1.setText("File");

        jMenuItem1.setText("New");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Open");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Save");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Exit");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Build");

        jMenuCompile.setText("Compile");
        jMenuCompile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuCompileActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuCompile);

        jMenuRun.setText("Run");
        jMenuRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuRunActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuRun);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane2))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextPane1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPane1KeyReleased


    }//GEN-LAST:event_jTextPane1KeyReleased

    private void jTextPane1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPane1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F6) {
            evt.consume();
            jButtonRunActionPerformed(null);
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }//GEN-LAST:event_jTextPane1KeyPressed

    private void jMenuRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuRunActionPerformed
        jButtonRunActionPerformed(evt);
    }//GEN-LAST:event_jMenuRunActionPerformed

    private void jMenuCompileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuCompileActionPerformed
        jButtonCompileActionPerformed(evt);
    }//GEN-LAST:event_jMenuCompileActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        FileOps.SaveAs(jTextPane1.getText());
    }//GEN-LAST:event_saveActionPerformed

    private void openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openActionPerformed
        int opt = JOptionPane.showConfirmDialog(null, "Do you want to save current project?");
        if (opt == JOptionPane.YES_OPTION) {
            FileOps.SaveAs(jTextPane1.getText());
            jTextPane1.setText("");
            String str = FileOps.Load();
            jTextPane1.setText(str);
        }
        if (opt == JOptionPane.NO_OPTION) {
            jTextPane1.setText("");
            String str = FileOps.Load();
            jTextPane1.setText(str);
        }
    }//GEN-LAST:event_openActionPerformed

    private void jButtonNEWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNEWActionPerformed
        int opt = JOptionPane.showConfirmDialog(null, "Do you want to save current project?");
        if (opt == JOptionPane.YES_OPTION) {
            FileOps.SaveAs(jTextPane1.getText());
            String str = "main(){\n        \n}";
            jTextPane1.setText(str);
            jTextPane1.setCaretPosition(17);
        }
        if (opt == JOptionPane.NO_OPTION) {
            String str = "main(){\n        \n}";
            jTextPane1.setText(str);
            jTextPane1.setCaretPosition(17);
        }
    }//GEN-LAST:event_jButtonNEWActionPerformed

    private void jButtonCompileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCompileActionPerformed
        BuildAndRun(true);
    }//GEN-LAST:event_jButtonCompileActionPerformed

    private void jButtonRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                run.run();
            }
        });
    }//GEN-LAST:event_jButtonRunActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        jButtonNEWActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        openActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        saveActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JButton jButtonCompile;
    private javax.swing.JButton jButtonNEW;
    private javax.swing.JButton jButtonRun;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuCompile;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuRun;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton open;
    private javax.swing.JButton save;
    // End of variables declaration//GEN-END:variables
}
