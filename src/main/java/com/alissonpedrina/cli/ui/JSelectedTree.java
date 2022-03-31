package com.alissonpedrina.cli.ui;

import com.alissonpedrina.cli.domain.Project;
import com.alissonpedrina.cli.domain.Recipe;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;

public class JSelectedTree extends JPanel implements ActionListener {
    DefaultMutableTreeNode root, parent, child, node;
    JTree tree;
    JButton add, remove;
    JPanel buttonsPanel;
    TreePath treePath;
    int index;
    private boolean isView;

    JSelectedTree(Project project) {
        init(project);
    }

    public JSelectedTree(boolean isView, Project project) {
        this.isView = isView;
        init(project);

    }

    public void init(Project project) {
        root = new DefaultMutableTreeNode(project.getName() == null ? "project" : project.getName());
        project.getRecipes().forEach(r -> {
            child = new DefaultMutableTreeNode(r.getLabel());
            child.setUserObject(r);
            root.add(child);

        });
        tree = new JTree(root);

        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int selRow = tree.getRowForLocation(e.getX(), e.getY());
                    TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                    tree.setSelectionPath(selPath);
                    if (selRow > -1) {
                        tree.setSelectionRow(selRow);
                    }
                }
            }
        };
        tree.addMouseListener(ml);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent tse) {
                DefaultMutableTreeNode node =
                        (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                var x = node.getUserObject();
                if (x instanceof Recipe) {
                    var r = ((Recipe) x);
                    AkumoEditor.activeRecipe = r;
                    AkumoEditor.load();

                }
            }
        });


        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(tree), BorderLayout.NORTH);

        add = new JButton("Add");
        add.addActionListener(this);
        remove = new JButton("Remove");
        remove.addActionListener(this);

        buttonsPanel = new JPanel();
        buttonsPanel.add(add);
        ImageIcon addIcon = new ImageIcon(AkumoEditor.class.getResource("/addRecipe.png"));
        add.setIcon(addIcon);
        ImageIcon removeIcon = new ImageIcon(AkumoEditor.class.getResource("/deleteRecipe.png"));
        remove.setIcon(removeIcon);
        buttonsPanel.add(remove);
        if (!isView) {
            this.add(buttonsPanel, BorderLayout.CENTER);

        }

        setSize(400, 600);
        setVisible(true);
    }

    public void add(Recipe recipe) {
        DefaultMutableTreeNode SelectedNode;

        treePath = tree.getPathForRow(0);
        SelectedNode = (DefaultMutableTreeNode) treePath
                .getLastPathComponent();

        index = SelectedNode.getIndex(SelectedNode) + 1;

        node = new DefaultMutableTreeNode(recipe.getLabel());
        node.setUserObject(recipe);

        SelectedNode.insert(node, index);
        tree.updateUI();

    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            NewRecipeDialog.showDialog(null, "New Recipe");
            tree.updateUI();
        } else if (ae.getSource() == remove) {
            int val = JOptionPane.showConfirmDialog(buttonsPanel,
                    "Please confirm to delete ?");
            if (val == 0) {
                DefaultMutableTreeNode SelectedNode;

                treePath = tree.getSelectionPath();
                SelectedNode = (DefaultMutableTreeNode) treePath
                        .getLastPathComponent();
                if (SelectedNode.isLeaf()) {
                    parent = (DefaultMutableTreeNode) SelectedNode.getParent();
                    parent.remove(SelectedNode);
                    tree.updateUI();
                } else {
                    JOptionPane.showMessageDialog(this, "Unable to Remove");
                }
            }
        }

    }

    public void remove() {
        int val = JOptionPane.showConfirmDialog(buttonsPanel,
                "Please confirm to delete ?");
        if (val == 0) {
            DefaultMutableTreeNode SelectedNode;

            treePath = tree.getSelectionPath();
            SelectedNode = (DefaultMutableTreeNode) treePath
                    .getLastPathComponent();
            if (SelectedNode.isLeaf()) {
                parent = (DefaultMutableTreeNode) SelectedNode.getParent();
                parent.remove(SelectedNode);
                tree.updateUI();
            } else {
                JOptionPane.showMessageDialog(this, "Unable to Remove");
            }
        }
    }
}

