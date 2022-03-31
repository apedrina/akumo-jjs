package com.alissonpedrina.cli.ui;

import com.alissonpedrina.cli.domain.Project;
import com.alissonpedrina.cli.domain.Recipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class NewProjectDialog extends JDialog implements ActionListener {
    private static NewProjectDialog dialog;
    private final JTextField nameTxt;
    private final JTextField locationTxt;

    public static void showDialog(Component frameComp, String title) {
        dialog = new NewProjectDialog(null, title);
        dialog.setVisible(true);

    }

    private NewProjectDialog(Frame frame, String title) {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        final JButton createBtn = new JButton("Create");
        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AkumoEditor.projectPath = locationTxt.getText();
                String filePath = locationTxt.getText() + File.separator + nameTxt.getText() + ".json";
                File fileToSave = new File(filePath);
                try {
                    fileToSave.createNewFile();
                    AkumoEditor.changeRootName(nameTxt.getText());
                    AkumoEditor.isProjectActive = true;
                    var project = new Project();
                    project.setName(nameTxt.getText());
                    project.setLocation(locationTxt.getText());
                    var r = new Recipe("helloWorld");
                    r.setBody("//not implemented yet");
                    r.setCode("//not implemented yet");
                    project.setRecipes(Arrays.asList(r));
                    AkumoEditor.project = project;
                    dialog.setVisible(false);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

        JPanel listPane = new JPanel();
        listPane.setLayout(new BorderLayout());
        listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel locationLabel = new JLabel("Location");
        locationTxt = new JTextField();
        locationLabel.setLabelFor(locationTxt);
        JPanel locationPanel = new JPanel(new BorderLayout());
        locationPanel.add(locationLabel, BorderLayout.NORTH);
        JButton chooserBtn = new JButton();
        chooserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showOpenDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    locationTxt.setText(fileChooser.getSelectedFile().getAbsolutePath());
                } else {
                }
            }
        });
        locationPanel.add(chooserBtn, BorderLayout.EAST);
        locationPanel.add(locationTxt, BorderLayout.CENTER);
        listPane.add(locationPanel, BorderLayout.NORTH);
        this.setPreferredSize(new Dimension(200, 200));

        JLabel nameLabel = new JLabel("Name");
        nameTxt = new JTextField();
        nameLabel.setLabelFor(nameTxt);
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.add(nameLabel, BorderLayout.NORTH);
        namePanel.add(nameTxt, BorderLayout.CENTER);
        listPane.add(namePanel, BorderLayout.CENTER);
        nameTxt.setPreferredSize(new Dimension(50,20));

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


