package com.alissonpedrina.cli.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddRecipeDialog extends JDialog implements ActionListener {
    private static AddRecipeDialog dialog;
    private static String value = "";
    private JList list;

    public static String showDialog(Component frameComp, String title) {
        dialog = new AddRecipeDialog(null, title);
        dialog.setVisible(true);
        return value;
    }

    private AddRecipeDialog(Frame frame, String title) {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        final JButton setButton = new JButton("Create");
        setButton.setActionCommand("Create");

        JPanel listPane = new JPanel();
        listPane.setLayout(new BorderLayout());
        listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Name");
        JTextField nameTxt = new JTextField();
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
        buttonPane.add(setButton);

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
        AddRecipeDialog.dialog.setVisible(false);
    }
}

