package com.alissonpedrina.cli.ui.editor;

import com.alissonpedrina.cli.domain.Project;
import com.alissonpedrina.cli.domain.Recipe;
import com.alissonpedrina.cli.engine.Compiler;
import com.alissonpedrina.cli.ui.JSelectedTree;
import com.alissonpedrina.cli.ui.NewProjectDialog;
import com.google.gson.Gson;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class AkumoEditor extends JFrame {
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
    public static Project project = new Project();
    public static JButton runBtn;
    private static JToolBar tollBar;
    public static Recipe activeRecipe;

    public static void load(){
        editor.setText(activeRecipe.getBody() == null? "//nothing implemented here yet": activeRecipe.getBody());
        languageEditor.setText(activeRecipe.getCode() == null ? "//nothing implemented here yet": activeRecipe.getCode());
    }

    public AkumoEditor() {
        try {
            Compiler.main(new String[]{});
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.init();
    }

    public static void console(String line) {
        console.setText(console.getText() + "\n" + line);
        console.paint(AkumoEditor.console.getGraphics());

    }

    public static void enableRun() {
        runBtn.enable(true);
        tollBar.updateUI();
    }

    public void init() {
        treeViewPanel = new JPanel();
        JPanel mainPanel = new JPanel(new BorderLayout());
        var ide = createIDE();
         tollBar = new JToolBar();
        ImageIcon saveIcon = new ImageIcon(AkumoEditor.class.getResource("/save.png"));
        ImageIcon newIcon = new ImageIcon(AkumoEditor.class.getResource("/new.png"));
        ImageIcon openIcon = new ImageIcon(AkumoEditor.class.getResource("/open.png"));
        ImageIcon runIcon = new ImageIcon(AkumoEditor.class.getResource("/run.png"));
        var saveBtn = saveBtnBuider();
        saveBtn.setIcon(saveIcon);
        var newBtn = newBtnBuilder();
        newBtn.setIcon(newIcon);
        runBtn = runBtnBuilder();
        runBtn.setIcon(runIcon);
        var openBtn = openBtnBuilder();
        openBtn.setIcon(openIcon);

        tollBar.add(newBtn);
        tollBar.addSeparator();
        tollBar.add(saveBtn);
        tollBar.addSeparator();
        tollBar.add(openBtn);
        tollBar.addSeparator();
        tollBar.add(runBtn);

        mainPanel.add(tollBar, BorderLayout.NORTH);
        mainPanel.add(ide, BorderLayout.CENTER);

        JButton toggleBtn = new JButton();
        ImageIcon previousIcon = new ImageIcon(AkumoEditor.class.getResource("/previous.png"));
        toggleBtn.setIcon(previousIcon);
        toggleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (treeView.isVisible()) {
                    ImageIcon next = new ImageIcon(AkumoEditor.class.getResource("/next.png"));
                    treeView.setVisible(false);
                    toggleBtn.setIcon(next);

                } else {
                    ImageIcon previous = new ImageIcon(AkumoEditor.class.getResource("/previous.png"));
                    treeView.setVisible(true);
                    toggleBtn.setIcon(previous);
                }
            }
        });
        var project = new Project();
        var r = new Recipe();
        project.setRecipes(Arrays.asList(r));
        treeView = new JSelectedTree(project);

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

    public JButton openBtnBuilder() {
        var openBtn = new JButton();
        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    if (selectedFile.isDirectory()) {
                        projectPath = selectedFile.getAbsolutePath();
                        AkumoEditor.changeRootName(selectedFile.getName());
                        try (Stream<Path> paths = Files.walk(Paths.get(selectedFile.getAbsolutePath()))) {
                            paths.filter(f -> f.endsWith(".json")).forEach(f -> AkumoEditor.treeView.add(new Recipe(f.toFile().getName())));

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }

                }

            }

        });
        return openBtn;
    }

    public JButton runBtnBuilder() {
        var runBtn = new JButton();
        runBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AkumoEditor.console.setText("");
                    Gson gson = new Gson();
                    ProcessBuilder builder = new ProcessBuilder("node", "app.js", "-c", gson.toJson(project));
                    builder.redirectErrorStream(true);
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
                } catch (Exception ex) {
                    ex.printStackTrace();

                }


            }

        });
        return runBtn;

    }

    public JButton saveBtnBuider() {
        var saveBtn = new JButton();
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        return saveBtn;
    }

    public static void save() {
        if (AkumoEditor.codePath != null) {
            try {
                Gson gson = new Gson();
                Reader reader = Files.newBufferedReader(Paths.get(AkumoEditor.codePath));
                var recipe = gson.fromJson(reader, Recipe.class);
                recipe.setBody(AkumoEditor.editor.getText());
                recipe.setCode(AkumoEditor.languageEditor.getText());
                Writer writer = new FileWriter(AkumoEditor.codePath);
                gson.toJson(recipe, writer);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    public static void changeRootName(String name) {
        treeView.removeAll();
        var prj = new Project();
        prj.setName(name);
        prj.getRecipes().add(new Recipe("hello"));
        treeView.init(prj);
        treeView.updateUI();

    }

    private JPanel createContent() {
        JButton cancelButton = new JButton("Cancel");
        final JButton setButton = new JButton("Create");
        setButton.setActionCommand("Create");

        JPanel mainPanel = new JPanel();
        mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.setLayout(new BorderLayout());
        comboLanguage = new JComboBox<>();
        comboLanguage.addItem("Java");
        comboLanguage.addItem("Javascript");
        comboLanguage.addItem("Json");

        var comboPanel = new JPanel();
        comboPanel.setLayout(new BorderLayout());
        mainPanel.add(comboLanguage, BorderLayout.NORTH);

        comboLanguage.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == 2) {
                    getLanguage();

                }
            }
        });

        languageEditor = new RSyntaxTextArea(20, 60);
        languageEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);

        languageEditor.setCodeFoldingEnabled(true);
        var ide = new RTextScrollPane(languageEditor);
        RTextScrollPane sp = new RTextScrollPane(languageEditor);
        sp.setLineNumbersEnabled(true);
        mainPanel.add(sp, BorderLayout.CENTER);

        return mainPanel;

    }

    private void getLanguage() {
        switch (comboLanguage.getSelectedItem().toString()) {
            case "Json":
                languageEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
                languageEditor.paint(languageEditor.getGraphics());

            case "Javascript":
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        languageEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
                        languageEditor.paint(languageEditor.getGraphics());
                    }
                });

            default:
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        languageEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
                        languageEditor.paint(languageEditor.getGraphics());
                    }
                });


        }


    }

    private JSplitPane createIDE() {
        editor = new RSyntaxTextArea(20, 60);
        editor.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                save();
            }
        });
        editor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
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

    public static void callOpen(){

    }

    public static void main(String[] x) {
        //String[] xx = new String[]{"projectPath?/Users/alissonpedrina/Documents/gitprojects/commander-options"};
        //if (xx.length == 1 && xx[0].startsWith("projectPath?")) {
          //  SwingUtilities.invokeLater(() -> new ProjectViewer(xx[0]).setVisible(true));
        //} else {
            SwingUtilities.invokeLater(() -> new AkumoEditor().setVisible(true));

        //}
    }

}






