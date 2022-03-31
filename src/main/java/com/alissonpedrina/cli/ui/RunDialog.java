package com.alissonpedrina.cli.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RunDialog extends JDialog implements ActionListener {
    private static RunDialog dialog;
    private static String value = "";
    private JList list;

    public static String showDialog(Component frameComp, String title) {
        Frame frame = JOptionPane.getFrameForComponent(frameComp);
        dialog = new RunDialog(frame, title);
        dialog.setVisible(true);
        return value;
    }

    private void setValue(String newValue) {
        value = newValue;
    }

    private RunDialog(Frame frame, String title) {
        super(frame, title, true);

        try {
            ProcessBuilder builder = new ProcessBuilder("node", "app.js", "-c", AkumoEditor.editor.getText());
            builder.directory(new File("/Users/alissonpedrina/Documents/gitprojects/commander-options"));
            Process proc = builder.start();
            String line;
            InputStream inputStream = proc.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while ((line = bufferedReader.readLine()) != null) {
                AkumoEditor.console(line); // it prints all at once after command has been executed.
            }
            proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();

        }

        // Create and initialize the buttons.
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);

        JPanel listPane = new JPanel();
        listPane.setLayout(new GridLayout(4, 2));
        listPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        //listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField combo = new JTextField();
        JButton searchBtn = new JButton("search");
        listPane.add(combo);
        listPane.add(searchBtn);

        // Data to be displayed in the JTable
        String[][] data = {
                {"Kundan Kumar Jha", "4031", "CSE"},
                {"Anand Jha", "6014", "IT"}
        };

        // Column Names
        String[] columnNames = {"Name", "Roll Number", "Department"};

        // Initializing the JTable
        var j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 300);

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(j);
        listPane.add(new JLabel("result"));
        listPane.add(sp);

        // Lay out the buttons from left to right.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        //buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(cancelButton);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));

        Container contentPane = getContentPane();
        contentPane.add(listPane, BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.PAGE_END);
        pack();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RunDialog.dialog.setVisible(false);
    }
}

