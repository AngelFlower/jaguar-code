package org.jaguarcode.ui.tasks;

import mdlaf.utils.MaterialColors;

import javax.swing.*;
import java.awt.*;

public class PanelTask extends JPanel {
    public PanelTask () {
        panelConfig();
    }

    public void panelConfig () {
        this.setBackground(MaterialColors.BLUE_A400);
        this.setPreferredSize(new Dimension(300,2800));
        this.setBackground(MaterialColors.GRAY_700);
    }
}
