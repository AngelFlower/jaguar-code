package org.jaguarcode;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import org.jaguarcode.ui.FrameStart;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class main {

    public static Font monoCode;

    public static void main (String[] args) {
        LafManager.setTheme(new DarculaTheme());
        LafManager.install();

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

    }
}
