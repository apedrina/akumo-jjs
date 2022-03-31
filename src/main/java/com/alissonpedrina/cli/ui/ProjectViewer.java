package com.alissonpedrina.cli.ui;

import com.alissonpedrina.cli.domain.Project;
import com.alissonpedrina.cli.domain.Recipe;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ProjectViewer extends JFrame {
    public static RSyntaxTextArea editor;
    public static JScrollPane scrollableTextArea;
    public static JPanel treeViewPanel;
    public static JTextArea console;
    private JComboBox<Object> comboLanguage;
    public static RSyntaxTextArea languageEditor;
    public static boolean isProjectActive;
    public static String projectPath;
    public static boolean isCodeSave = false;
    public static String codePath;
    public static JSelectedTree treeView;

    public ProjectViewer() {
        this.init();
    }

    public ProjectViewer(String projectPath) {
        this.projectPath = projectPath;
        this.init();

    }

    public static void console(String line) {
        console.setText(console.getText() + "\n" + line);
        console.paint(ProjectViewer.console.getGraphics());

    }

    public void init() {
        treeViewPanel = new JPanel();
        JPanel mainPanel = new JPanel(new BorderLayout());
        var ide = createIDE();
        var tollBar = new JToolBar();
        ImageIcon nextIcon = new ImageIcon(ProjectViewer.class.getResource("/nextGreen.png"));
        ImageIcon previousIcon = new ImageIcon(ProjectViewer.class.getResource("/previousGreen.png"));
        var nextBtn = new JButton();
        nextBtn.setIcon(nextIcon);

        var previousBtn = newBtnBuilder();
        previousBtn.setIcon(previousIcon);

        tollBar.add(previousBtn);
        tollBar.addSeparator();
        tollBar.add(nextBtn);

        mainPanel.add(tollBar, BorderLayout.NORTH);
        mainPanel.add(ide, BorderLayout.CENTER);

        JButton toggleBtn = new JButton();
        ImageIcon previousTreeIcon = new ImageIcon(ProjectViewer.class.getResource("/previous.png"));
        toggleBtn.setIcon(previousTreeIcon);
        toggleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (treeView.isVisible()) {
                    ImageIcon next = new ImageIcon(ProjectViewer.class.getResource("/next.png"));
                    treeView.setVisible(false);
                    toggleBtn.setIcon(next);

                } else {
                    ImageIcon previous = new ImageIcon(ProjectViewer.class.getResource("/previous.png"));
                    treeView.setVisible(true);
                    toggleBtn.setIcon(previous);
                }
            }
        });
        var project = new Project();
        var r = new Recipe();
        project.setRecipes(Arrays.asList(r));
        treeView = new JSelectedTree(true, project);

        treeViewPanel.setLayout(new BorderLayout());
        treeViewPanel.add(treeView, BorderLayout.CENTER);
        treeViewPanel.add(toggleBtn, BorderLayout.NORTH);

        mainPanel.add(treeViewPanel, BorderLayout.WEST);

        setContentPane(mainPanel);

        setTitle("Akumo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationByPlatform(true);

    }

    private JPanel createContent() {
        JPanel mainPanel = new JPanel();
        mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(HtmlEditorKitTest.get(), BorderLayout.CENTER);

        return mainPanel;

    }

    private JSplitPane createIDE() {
        editor = new RSyntaxTextArea(20, 60);
        editor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        editor.setCodeFoldingEnabled(true);
        var ide = new RTextScrollPane(editor);
        console = new JTextArea(20, 20);
        scrollableTextArea = new JScrollPane(console);

        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        var content = new JSplitPane(JSplitPane.VERTICAL_SPLIT, ide, scrollableTextArea);
        content.setDividerLocation(400);
        content.setDividerSize(3);
        var main = new JSplitPane();//(JSplitPane.HORIZONTAL_SPLIT, content, createContent());
        main.setLeftComponent(content);
        main.setRightComponent(createContent());
        main.setAlignmentX(0);
        main.setDividerSize(10);
        main.setDividerLocation(300);

        return main;
    }

    public JButton newBtnBuilder() {
        var newBtn = new JButton();
        newBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewProjectDialog.showDialog(null, "");
            }
        });
        return newBtn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProjectViewer().setVisible(true));
    }

}






