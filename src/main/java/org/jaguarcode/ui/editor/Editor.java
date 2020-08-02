package org.jaguarcode.ui.editor;

import mdlaf.utils.MaterialColors;
import org.fife.rsta.ac.LanguageSupport;
import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.rsta.ac.java.JavaLanguageSupport;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.RTextScrollPane;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import static org.fife.ui.rsyntaxtextarea.SyntaxConstants.SYNTAX_STYLE_JAVA;
import static org.jaguarcode.main.monoCode;

@SuppressWarnings("serial")
public class Editor extends JPanel implements DocumentListener {

    public static RSyntaxTextArea textPane;
    public static File file;
    public static boolean changed = false;

    public static Component getTextEditor () {
        return new Editor().CreateTextEditor();
    }

    public Component CreateTextEditor () {
        textPane = new RSyntaxTextArea(20,60);
        textPane.setSyntaxEditingStyle(SYNTAX_STYLE_JAVA);
        textPane.setCodeFoldingEnabled(true);
        textPane.setAntiAliasingEnabled(true);
        textPane.setBackground(MaterialColors.GRAY_800);
        LanguageSupportFactory.get().register(textPane);
        textPane.requestFocusInWindow();
        textPane.setMarkOccurrences(true);
        textPane.setCodeFoldingEnabled(true);
        textPane.setTabsEmulated(true);
        textPane.setTabSize(3);
        textPane.getDocument().addDocumentListener(this);
        ToolTipManager.sharedInstance().registerComponent(textPane);
        ErrorStrip errorStrip = new ErrorStrip(textPane);
        errorStrip.setCaretMarkerColor(MaterialColors.RED_800);

        LanguageSupportFactory lsf = LanguageSupportFactory.get();
        LanguageSupport support = lsf.getSupportFor(SYNTAX_STYLE_JAVA);
        JavaLanguageSupport jls = (JavaLanguageSupport)support;
        try {
            jls.getJarManager().addCurrentJreClassFileSource();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        changeStyleViaThemeXml();
        changeStyleProgrammatically();
        setFont(textPane,monoCode.deriveFont(14f));

        RTextScrollPane code = new RTextScrollPane(textPane);

        code.setFoldIndicatorEnabled(true);
        code.setLineNumbersEnabled(true);

        JPanel cp = new JPanel(new BorderLayout());
        cp.add(code);
        cp.add(errorStrip, BorderLayout.LINE_END);
        return cp;
    }

    private void changeStyleProgrammatically() {
        // Set the font for all token types.
        setFont(textPane, new Font("monoCode", Font.PLAIN, 16));
        textPane.revalidate();
    }

    public static void setFont(RSyntaxTextArea textArea, Font font) {
        if (font != null) {
            SyntaxScheme ss = textArea.getSyntaxScheme();
            ss = (SyntaxScheme) ss.clone();
            for (int i = 0; i < ss.getStyleCount(); i++) {
                if (ss.getStyle(i) != null) {
                    ss.getStyle(i).font = font;
                }
            }
            textArea.setSyntaxScheme(ss);
            textArea.setFont(font);
        }
    }

    private void changeStyleViaThemeXml() {
        try {
            Theme theme = Theme.load(getClass().getResourceAsStream(
                    "/org/fife/ui/rsyntaxtextarea/themes/monokai.xml"));
            theme.apply(textPane);
        } catch (IOException ioe) { // Never happens
            ioe.printStackTrace();
        }
    }

    public static void newFile() {
        if (changed)
            saveFile();
        file = null;
        textPane.setText("");
        changed = false;
    }

    public static void loadFile(File rfile) {
        textPane.setText(readFile(rfile));
        textPane.setCaretPosition(0);
        file = rfile;
    }

    public static void loadFileMenu() {
        JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
        dialog.setMultiSelectionEnabled(false);
        try {
            int result = dialog.showOpenDialog(null);
            if (result == JFileChooser.CANCEL_OPTION)
                return;
            if (result == JFileChooser.APPROVE_OPTION) {
                //if (changed)
                //    saveFile();
                file = dialog.getSelectedFile();
                textPane.setText(readFile(file));
                //changed = false;
                //setTitle("Editor - " + file.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String readFile(File rfile) {
        StringBuilder result = new StringBuilder();
        try (FileReader fr = new FileReader(rfile);
             BufferedReader reader = new BufferedReader(fr);) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot read file !", "Error !", JOptionPane.ERROR_MESSAGE);
        }
        return result.toString();
    }

    public static void saveFile() {

        String text = textPane.getText();
        System.out.println(text);
        try (PrintWriter writer = new PrintWriter(file);){
            if (!file.canWrite())
                throw new Exception("Cannot write file!");
            writer.write(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
         changed = true;
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        changed = true;
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        changed = true;
    }
}


