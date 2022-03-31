package com.alissonpedrina.cli.ui;

import com.alissonpedrina.cli.domain.Recipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class NewRecipeDialog extends JDialog implements ActionListener {
    private static NewRecipeDialog dialog;
    private final JTextField nameTxt;

    public static void showDialog(Component frameComp, String title) {
        dialog = new NewRecipeDialog(null, title);
        dialog.setVisible(true);

    }


    private NewRecipeDialog(Frame frame, String title) {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        final JButton createBtn = new JButton("Create");
        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = AkumoEditor.projectPath + File.separator + nameTxt.getText() + ".json";
                File fileToSave = new File(filePath);
                try {
                    fileToSave.createNewFile();
                    AkumoEditor.treeView.add(new Recipe(nameTxt.getText()));
                    dialog.setVisible(false);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel listPane = new JPanel();
        listPane.setLayout(new BorderLayout());
        listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setPreferredSize(new Dimension(200, 200));

        JLabel nameLabel = new JLabel("Name");
        nameTxt = new JTextField();
        nameLabel.setLabelFor(nameTxt);
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.add(nameLabel, BorderLayout.NORTH);
        namePanel.add(nameTxt, BorderLayout.CENTER);
        listPane.add(namePanel, BorderLayout.CENTER);

        // Lay out the buttons from left to right.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        //buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(cancelButton);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(createBtn);

        this.add(listPane, BorderLayout.CENTER);
        this.add(buttonPane, BorderLayout.PAGE_END);
        this.setVisible(true);

        Container contentPane = getContentPane();
        contentPane.add(listPane, BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.PAGE_END);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dialog.setVisible(false);
    }
}


