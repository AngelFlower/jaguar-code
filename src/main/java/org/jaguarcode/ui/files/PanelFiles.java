package org.jaguarcode.ui.files;

import org.jaguarcode.ui.editor.Editor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class PanelFiles extends JPanel {

    private DefaultMutableTreeNode root;

    private DefaultTreeModel treeModel;

    private JTree tree;


    public PanelFiles () {

        panelConfig();
        files();

    }

    public void panelConfig () {
        //this.setPreferredSize(new Dimension(250,2800));
        this.setLayout(new GridLayout());
        this.setBorder(new EmptyBorder(10, 1, 1, 1));
    }

    public void files () {
        File fileRoot = new File("E:/");
        root = new DefaultMutableTreeNode(new FileNode(fileRoot));

        treeModel = new DefaultTreeModel(root);

        tree = new JTree(treeModel);
        tree.setShowsRootHandles(true);

        tree.setCellRenderer(new MyTreeCellRenderer());
        MouseListener ml = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TreePath path = tree.getPathForLocation(e.getX(), e.getY());

                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    System.out.println("Double Click");
                    String filepath = tree.getSelectionPath().toString().replaceAll("[\\[\\]]", "").replace(", ", "\\");
                    File file = new File(filepath);
                    if (!file.isDirectory()) {
                        Editor.loadFile(file);
                    }
                } else {
                    if (tree.isCollapsed(path)) {
                        tree.expandPath(path);
                    } else {
                        tree.collapsePath(tree.getSelectionPath());
                    }
                }

            }

        };

        tree.addMouseListener(ml);

        JScrollPane scrollPane = new JScrollPane(tree);

        this.add(scrollPane);

        CreateChildNodes ccn =
                new CreateChildNodes(fileRoot, root);
        new Thread(ccn).start();

    }

    public class CreateChildNodes implements Runnable {

        private DefaultMutableTreeNode root;

        private File fileRoot;

        public CreateChildNodes(File fileRoot,
                                DefaultMutableTreeNode root) {
            this.fileRoot = fileRoot;
            this.root = root;
        }

        @Override
        public void run() {
            createChildren(fileRoot, root);
        }

        private void createChildren(File fileRoot,
                                    DefaultMutableTreeNode node) {
            File[] files = fileRoot.listFiles();
            if (files == null) return;

            for (File file : files) {
                DefaultMutableTreeNode childNode =
                        new DefaultMutableTreeNode(new FileNode(file));
                node.add(childNode);
                if (file.isDirectory()) {
                    createChildren(file, childNode);
                }
            }
        }

    }

    public class FileNode {

        private File file;

        public FileNode(File file) {
            this.file = file;
        }

        @Override
        public String toString() {
            String name = file.getName();
            if (name.equals("")) {
                return file.getAbsolutePath();
            } else {
                return name;
            }
        }
    }

}


class MyTreeCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel, boolean exp, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, hasFocus);

        // Assuming you have a tree of Strings
        String node;
        node = (String) ((DefaultMutableTreeNode) value).getUserObject().toString();

        // If the node is a leaf and ends with "xxx"
        if (leaf && node.endsWith("xxx")) {
            // Paint the node in blue
            setForeground(new Color(180, 185, 193));
        }

        return this;
    }
}