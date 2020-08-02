package org.jaguarcode.ui;
import com.github.weisj.darklaf.components.tabframe.JTabFrame;
import com.github.weisj.darklaf.components.tabframe.TabFramePopup;
import com.github.weisj.darklaf.components.tabframe.TabbedPopup;
import com.github.weisj.darklaf.components.text.NonWrappingTextPane;
import com.github.weisj.darklaf.components.text.NumberedTextComponent;
import com.github.weisj.darklaf.components.text.NumberingPane;
import com.github.weisj.darklaf.util.Alignment;
import com.github.weisj.darklaf.util.DarkUIUtil;
import com.github.weisj.darklaf.util.StringUtil;
import org.jaguarcode.ui.editor.Editor;
import org.jaguarcode.ui.files.PanelFiles;
import org.jaguarcode.ui.tasks.PanelTask;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.text.BadLocationException;
import java.awt.*;




public class PanelManager extends JPanel {

    public PanelManager () {
        GridConfig();

        /*

        TabbedPopup popup = new TabbedPopup("Files");
        tabFrame.setTabAt(popup,"NORTH (Tabbed Pane Tab)", null, Alignment.NORTH, 0);
        popup.getTabbedPane().addTab("segundo",pcc);
*/

        /*
         * Activate for a custom tab.
         * tabFrame.setUserTabComponentAt(new JLabel("NORTH (custom tab)") {{
         * setBorder(new EmptyBorder(0, 5, 0, 5));
         * setOpaque(false);
         * setForeground(Color.RED);
         * setFont(new Font(Font.SERIF, Font.ITALIC, 12));
         * }}, Alignment.NORTH, 1);
         */


        this.add(new PanelTask(), BorderLayout.EAST);

        final JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new PanelFiles(), new Editor());

        sp.setContinuousLayout(true);
        sp.setDividerSize(6);
        sp.setUI(flattenJSplitPane(sp));
        sp.setBorder(null);

        sp.setOneTouchExpandable(true);

        sp.setPreferredSize(new Dimension(250,2000));

        JPanel cp = new JPanel(new BorderLayout());
        cp.add(sp);
        this.add(cp);

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

    public void GridConfig () {
        this.setLayout(new BorderLayout());
    }
}
