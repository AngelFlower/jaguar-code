package org.jaguarcode.ui;
import com.github.weisj.darklaf.components.tabframe.JTabFrame;
import com.github.weisj.darklaf.components.tabframe.TabbedPopup;
import com.github.weisj.darklaf.util.Alignment;
import org.jaguarcode.ui.editor.Editor;
import org.jaguarcode.ui.files.PanelFiles;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;

public class PanelManager{

    public static JComponent getContent(){
        return new PanelManager().createComponents();
    }

    public JComponent createComponents() {
        JTabFrame tabFrame = new JTabFrame();

        JPanel pcc = new JPanel();
        pcc.setOpaque(true);
        pcc.add(new JLabel( "_ sdf" + " Popup"));

        JPanel pc = new JPanel();
        pc.setOpaque(true);
        pc.add(new JLabel( "tab primero"));
        pc.setPreferredSize(new Dimension(250,1000));
        tabFrame.addTab(pc, "Files", Alignment.NORTH);

        tabFrame.addTab(new PanelFiles(), "Files", FrameStart.openFolder, Alignment.NORTH_WEST);

        TabbedPopup tabbedPopup = new TabbedPopup("Tabbed Popup:");
        tabbedPopup.getTabbedPane().addTab("Tab 1", pcc);
        tabbedPopup.getTabbedPane().addTab("Tab 2", pcc);
        //tabFrame.setTabAt(tabbedPopup, "NORTH (Tabbed Pane Tab)", null, Alignment.NORTH_WEST, 0);

        tabFrame.setTabEnabled(Alignment.NORTH_WEST, 0, true);
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(tabFrame, BorderLayout.CENTER);
        ;
        tabFrame.setAcceleratorAt(1, Alignment.NORTH, 0);
        tabFrame.setContent(Editor.getTextEditor());
        return tabFrame;
    }


    public static BasicSplitPaneUI flattenJSplitPane(JSplitPane splitPane) {
        splitPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        BasicSplitPaneUI flatDividerSplitPaneUI = new BasicSplitPaneUI() {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    @Override
                    public void setBorder(Border b) {
                    }

                };
            }
        };
        return flatDividerSplitPaneUI;
    }


}
