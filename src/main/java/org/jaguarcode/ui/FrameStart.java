package org.jaguarcode.ui;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import org.jaguarcode.ui.editor.Editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Level;

import static org.jaguarcode.ui.editor.Editor.*;

public class FrameStart extends JFrame implements ActionListener {

    private JMenuBar menu;
    private JMenuItem copy, paste, cut;
    public static ImageIcon openFolder;

    public FrameStart () {
        setIcons();
        buildFrame();
    }

    private void setIcons () {
        openFolder = new ImageIcon("src\\main\\resources\\open.png");
    }

    private JMenuBar buildMenu() {
        menu = new JMenuBar();
        buildFileMenu();
        buildEditMenu();

        return menu;
    }

    private void buildFileMenu() {
        JMenu file = new JMenu("File");
        file.setMnemonic('F');
        menu.add(file);
        JMenuItem n = new JMenuItem("New");
        n.setMnemonic('N');
        n.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        n.addActionListener( e -> {
            newFile();
        });
        n.setIcon(new ImageIcon("src\\main\\resources\\file.png"));
        file.add(n);
        JMenuItem open = new JMenuItem("Open");
        open.setIcon(openFolder);
        file.add(open);
        open.addActionListener( e -> {
            loadFileMenu();
        });
        open.setMnemonic('O');
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        JMenuItem save = new JMenuItem("Save");
        save.setIcon(new ImageIcon("src\\main\\resources\\save.png"));
        file.add(save);
        save.setMnemonic('S');
        save.addActionListener(e -> {
            Editor.saveFile();
        });
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        JMenuItem saveas = new JMenuItem("Save as...");
        saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
        file.add(saveas);
        saveas.addActionListener(this);
        JMenuItem quit = new JMenuItem("Quit");
        file.add(quit);
        quit.addActionListener(this);
        quit.setMnemonic('Q');
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
    }

    private void buildEditMenu() {
        JMenu edit = new JMenu("Edit");
        menu.add(edit);
        edit.setMnemonic('E');
        // cut
        cut = new JMenuItem("Cut");

        cut.addActionListener(e -> {
            textPane.cut();
        });
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        cut.setMnemonic('T');
        cut.setIcon(new ImageIcon("src\\main\\resources\\cut.png"));
        edit.add(cut);
        // copy
        copy = new JMenuItem("Copy");
        copy.addActionListener(this);
        copy.setMnemonic('C');
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        copy.setIcon(new ImageIcon("src\\main\\resources\\copy.png"));
        edit.add(copy);
        // paste
        paste = new JMenuItem("Paste");
        paste.setMnemonic('P');
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        paste.setIcon(new ImageIcon("src\\main\\resources\\paste.png"));
        edit.add(paste);
        paste.addActionListener(this);
        // find
        JMenuItem find = new JMenuItem("Find");
        find.setMnemonic('F');
        find.addActionListener(this);
        edit.add(find);
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        // select all
        JMenuItem sall = new JMenuItem("Select All");
        sall.setMnemonic('A');
        sall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        sall.addActionListener(this);
        edit.add(sall);
    }

    private void buildFrame () {
        LafManager.setTheme(new DarculaTheme());
        LafManager.install();
        LafManager.enabledPreferenceChangeReporting(false);
        LafManager.setDecorationsEnabled(true);
        LafManager.setLogLevel(Level.FINE);
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(PanelManager.getContent());
        frame.setJMenuBar(buildMenu());
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dim = new Dimension(screenSize.width /2,
                screenSize.height / 2);
        frame.setMinimumSize(dim);
        frame.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
