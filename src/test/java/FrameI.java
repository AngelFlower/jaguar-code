import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.components.tabframe.JTabFrame;
import com.github.weisj.darklaf.components.tabframe.TabbedPopup;
import com.github.weisj.darklaf.components.text.NonWrappingTextPane;
import com.github.weisj.darklaf.components.text.NumberedTextComponent;
import com.github.weisj.darklaf.components.text.NumberingPane;
import com.github.weisj.darklaf.theme.DarculaTheme;
import com.github.weisj.darklaf.util.Alignment;
import com.github.weisj.darklaf.util.StringUtil;
import mdlaf.utils.MaterialColors;
import org.fife.rsta.ac.LanguageSupport;
import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.rsta.ac.java.JavaLanguageSupport;
import org.fife.ui.rsyntaxtextarea.ErrorStrip;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jaguarcode.ui.files.PanelFiles;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;

import static org.fife.ui.rsyntaxtextarea.SyntaxConstants.SYNTAX_STYLE_JAVA;

public class FrameI extends JFrame {


    public static void main(String[] args) {
        new FrameI();
    }

    public FrameI () {
        ConfigFrame();
    }

    public JComponent createComponent() {
        JTabFrame tabFrame = new JTabFrame();


        JPanel pcc = new JPanel();
        pcc.setOpaque(true);
        pcc.add(new JLabel( "_ sdf" + " Popup"));

        JPanel pc = new JPanel();
        pc.setOpaque(true);
        pc.add(new JLabel( "tab primero"));
        pc.setPreferredSize(new Dimension(250,1000));
        tabFrame.addTab(pc, "Files", Alignment.NORTH);

        tabFrame.addTab(new PanelFiles(), "Tab 1", Alignment.NORTH_WEST);

        TabbedPopup tabbedPopup = new TabbedPopup("Tabbed Popup:");
        tabbedPopup.getTabbedPane().addTab("Tab 1", pcc);
        tabbedPopup.getTabbedPane().addTab("Tab 2", pcc);
        //tabFrame.setTabAt(tabbedPopup, "NORTH (Tabbed Pane Tab)", null, Alignment.NORTH_WEST, 0);

        tabFrame.setTabEnabled(Alignment.NORTH_WEST, 0, true);
        //TabbedPopup tabbedPopup = new TabbedPopup("Tabbed Popup:");
        //tabFrame.setTabAt(tabbedPopup, "NORTH (Tabbed Pane Tab)", null, Alignment.NORTH, 0);
        //JPanel panel = new JPanel();
        //JLabel label = new JLabel("Tab Number ");
        //panel.add(label);
        //tabbedPopup.getTabbedPane().addTab("Tab " + i, panel);
        /*
         * Activate for a custom tab.
         * tabFrame.setUserTabComponentAt(new JLabel("NORTH (custom tab)") {{
         * setBorder(new EmptyBorder(0, 5, 0, 5));
         * setOpaque(false);
         * setForeground(Color.RED);
         * setFont(new Font(Font.SERIF, Font.ITALIC, 12));
         * }}, Alignment.NORTH, 1);
         */
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(tabFrame, BorderLayout.CENTER);
        ;
        tabFrame.setAcceleratorAt(1, Alignment.NORTH, 0);
        tabFrame.setContent(createTextArea());
        return tabFrame;
    }


    private static Component createTextArea() {

        RSyntaxTextArea textPane = new RSyntaxTextArea(20,60);
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
        textPane.setLineWrap(true);
        ToolTipManager.sharedInstance().registerComponent(textPane);
        ErrorStrip errorStrip = new ErrorStrip(textPane);

        LanguageSupportFactory lsf = LanguageSupportFactory.get();
        LanguageSupport support = lsf.getSupportFor(SYNTAX_STYLE_JAVA);
        JavaLanguageSupport jls = (JavaLanguageSupport)support;
        try {
            jls.getJarManager().addCurrentJreClassFileSource();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        LanguageSupportFactory.get().register(textPane);


        RTextScrollPane code = new RTextScrollPane(textPane);

        code.setFoldIndicatorEnabled(true);
        code.setLineNumbersEnabled(true);

        JPanel cp = new JPanel(new BorderLayout());
        cp.add(code);
        cp.add(errorStrip, BorderLayout.LINE_END);
        return cp;
    }


    private void ConfigFrame () {
        LafManager.setTheme(new DarculaTheme());
        LafManager.install();
        LafManager.enabledPreferenceChangeReporting(false);
        LafManager.setDecorationsEnabled(true);
        LafManager.setLogLevel(Level.FINE);
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(createComponent());
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dim = new Dimension(screenSize.width / 2,
        screenSize.height / 2);
        Dimension targetSize = frame.getSize();
        targetSize.width = Math.min(targetSize.width, dim.width);
        targetSize.height = Math.min(targetSize.height, dim.height);
        frame.setSize(targetSize);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
