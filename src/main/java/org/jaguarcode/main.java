package org.jaguarcode;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import com.github.weisj.darklaf.theme.IntelliJTheme;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;
import mdlaf.themes.MaterialOceanicTheme;
import mdlaf.themes.MaterialTheme;
import org.jaguarcode.ui.FrameStart;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class main {

    public static Font monoCode;

    public static void main (String[] args) {
        LafManager.setTheme(new DarculaTheme());
        LafManager.install();
        /*
        try {
            UIManager.setLookAndFeel (new MaterialLookAndFeel());

        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace ();
        }


        if (UIManager.getLookAndFeel() instanceof MaterialLookAndFeel){
            MaterialLookAndFeel.changeTheme(new Material());
        }
        */

        try {
            //create the font to use. Specify the size!
            monoCode = Font.createFont(Font.TRUETYPE_FONT, new File("src\\main\\resources\\monoCode.ttf")).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(monoCode);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }

        new FrameStart();

        /*
        JFrame frame = new JFrame ("Material Design UI for Swing by atharva washimkar");
        frame.setMinimumSize (new Dimension (600, 400));

        JButton button = new JButton ("PRESS ME");
        button.setMaximumSize (new Dimension (200, 200));

        JPanel content = new JPanel ();
        content.add (button);
        frame.add (content, BorderLayout.CENTER);

        // on hover, button will change to a light gray
        MaterialUIMovement.add (button, MaterialColors.GRAY_100);

        frame.pack ();
        frame.setVisible (true);*/

    }
}
