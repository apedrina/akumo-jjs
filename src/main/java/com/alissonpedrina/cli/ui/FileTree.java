package com.alissonpedrina.cli.ui;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.util.Collections;
import java.util.Vector;

public class FileTree extends JPanel {
    /**
     * Construct a FileTree
     */
    public FileTree(File dir) {
        setLayout(new BorderLayout());

        JTree tree = new JTree(addNodes(null, dir));
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) e
                        .getPath().getLastPathComponent();
                JOptionPane.showMessageDialog(null, "You selected " + node);
            }
        });
        JScrollPane scrollpane = new JScrollPane();
        scrollpane.getViewport().add(tree);
        add(BorderLayout.CENTER, scrollpane);
    }

    /**
     * Add nodes from under "dir" into curTop. Highly recursive.
     */
    DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
        String curPath = dir.getPath();
        DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
        if (curTop != null) { // should only be null at root
            curTop.add(curDir);
        }
        Vector ol = new Vector();
        String[] tmp = dir.list();
        for (int i = 0; i < tmp.length; i++)
            ol.addElement(tmp[i]);
        Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
        File f;
        Vector files = new Vector();
        for (int i = 0; i < ol.size(); i++) {
            String thisObject = (String) ol.elementAt(i);
            String newPath;
            if (curPath.equals("."))
                newPath = thisObject;
            else
                newPath = curPath + File.separator + thisObject;
            if ((f = new File(newPath)).isDirectory())
                addNodes(curDir, f);
            else
                files.addElement(thisObject);
        }
        for (int fnum = 0; fnum < files.size(); fnum++)
            curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
        return curDir;
    }

    public void changeProjectName(){

    }

    public Dimension getMinimumSize() {
        return new Dimension(200, 400);
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 500);
    }

    /**
     * Main: make a Frame, add a FileTree
     */
    public static void main(String[] av) {

        JFrame frame = new JFrame("FileTree");
        frame.setForeground(Color.black);
        frame.setBackground(Color.lightGray);
        Container cp = frame.getContentPane();

        if (av.length == 0) {
            cp.add(new FileTree(new File(".")));
        } else {
            cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
            for (int i = 0; i < av.length; i++)
                cp.add(new FileTree(new File(av[i])));
        }

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
