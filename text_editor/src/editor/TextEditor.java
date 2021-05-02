package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {

    public List<MatchResult> matches = new ArrayList<>();
    public int currentPosition = 0;
    public boolean regex = false;

    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        setTitle("Text Editor");
        init();
        pack();
        setVisible(true);
    }

    private void init() {

        JFileChooser jfc = new JFileChooser("./src");
        jfc.setName("FileChooser");
        this.add(jfc);

        // NORTH

        FlowLayout northLayout = new FlowLayout(FlowLayout.LEFT, 7, 10);
        JPanel northPanel = new JPanel(northLayout);
        northPanel.setPreferredSize(new Dimension(530, 50));

        JButton openButton = new JButton(new ImageIcon("./src/openfolder.png"));
        openButton.setPreferredSize(new Dimension(35,35));
        openButton.setName("OpenButton");

        JButton saveButton = new JButton(new ImageIcon("./src/disk.png"));
        saveButton.setPreferredSize(new Dimension(35,35));
        saveButton.setName("SaveButton");

        JButton startSearchButton = new JButton(new ImageIcon("./src/loupe.png"));
        startSearchButton.setPreferredSize(new Dimension(35,35));
        startSearchButton.setName("StartSearchButton");

        JButton previousMatchButton = new JButton(new ImageIcon("./src/left-arrow.png"));
        previousMatchButton.setPreferredSize(new Dimension(35,35));
        previousMatchButton.setName("PreviousMatchButton");

        JButton nextMatchButton = new JButton(new ImageIcon("./src/right-arrow.png"));
        nextMatchButton.setPreferredSize(new Dimension(35,35));
        nextMatchButton.setName("NextMatchButton");

        JCheckBox useRegexCheckBox = new JCheckBox("Use regex");
        useRegexCheckBox.setPreferredSize(new Dimension(90,35));
        useRegexCheckBox.setName("UseRegExCheckbox");


        JTextField searchField = new JTextField(10);
        searchField.setName("SearchField");
        searchField.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        northPanel.add(openButton);
        northPanel.add(saveButton);
        northPanel.add(searchField);
        northPanel.add(startSearchButton);
        northPanel.add(previousMatchButton);
        northPanel.add(nextMatchButton);
        northPanel.add(useRegexCheckBox);

        // CENTER

        FlowLayout centerLayout = new FlowLayout(FlowLayout.LEFT);
        JPanel centerPanel = new JPanel(centerLayout);
        centerPanel.setPreferredSize(new Dimension(530, 300));

        JTextArea textArea = new JTextArea(12, 37);
        textArea.setName("TextArea");
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        JScrollPane spane = new JScrollPane(textArea);
        spane.setName("ScrollPane");

        centerPanel.add(spane);

        // MAIN PANEL

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // MENU BAR

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setName("MenuOpen");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");

        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");

        JMenuItem startSearchMenuItem = new JMenuItem("Start search");
        startSearchMenuItem.setName("MenuStartSearch");
        JMenuItem previousMatchMenuItem = new JMenuItem("Previous match");
        previousMatchMenuItem.setName("MenuPreviousMatch");
        JMenuItem nextMatchMenuItem = new JMenuItem("Next match");
        nextMatchMenuItem.setName("MenuNextMatch");
        JMenuItem useRegexMenuItem = new JMenuItem("Use regular expression");
        useRegexMenuItem.setName("MenuUseRegExp");

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        searchMenu.add(startSearchMenuItem);
        searchMenu.add(previousMatchMenuItem);
        searchMenu.add(nextMatchMenuItem);
        searchMenu.add(useRegexMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(searchMenu);
        setJMenuBar(menuBar);

        // ACTION LISTENERS

        saveButton.addActionListener(event -> saveFile(jfc, textArea));
        saveMenuItem.addActionListener(event -> saveFile(jfc, textArea));

        openButton.addActionListener(event -> openFile(jfc, textArea));
        openMenuItem.addActionListener(event -> openFile(jfc, textArea));

        useRegexCheckBox.addItemListener(event -> {
            int sel = event.getStateChange();
            if (sel == ItemEvent.SELECTED) {
                System.out.println(ItemEvent.SELECTED);
                regex = true;
            }
        });

        useRegexMenuItem.addActionListener(event -> useRegexCheckBox.setSelected(true));

        startSearchButton.addActionListener(event -> searchText(searchField, textArea));
        startSearchMenuItem.addActionListener(event -> searchText(searchField, textArea));

        nextMatchButton.addActionListener(event -> showNextMatch(textArea));
        nextMatchMenuItem.addActionListener(event -> showNextMatch(textArea));

        previousMatchButton.addActionListener(event -> showPreviousMatch(textArea));
        previousMatchMenuItem.addActionListener(event -> showPreviousMatch(textArea));



        exitMenuItem.addActionListener(event -> {
            dispose();
            System.exit(0);
        });
    }

    public void saveTextToFile(String fileName, String text) {

        System.out.println(fileName);

        try (OutputStream os = Files.newOutputStream(Path.of(fileName))) {

            os.write(text.getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String loadTextFromFile(File file) {

        String text = "";

        try (InputStream is = Files.newInputStream(file.toPath())) {

            byte[] allBytes = is.readAllBytes();

            text = new String(allBytes, StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return text;
    }

    public void saveFile(JFileChooser jfc, JTextArea textArea) {
        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File filePath = jfc.getSelectedFile();
            saveTextToFile(filePath.getAbsolutePath(), textArea.getText());
        }
    }

    public void openFile(JFileChooser jfc, JTextArea textArea) {
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            textArea.setText(loadTextFromFile(selectedFile));
        }
    }

    public void searchText(JTextField searchField, JTextArea textArea) {

        try {
            matches.clear();
            currentPosition = 0;
            String stringToFind = searchField.getText();
            Pattern pattern = Pattern.compile(stringToFind, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(textArea.getText());
            while (matcher.find()) {
                matches.add(matcher.toMatchResult());
            }
            if (!matches.isEmpty()) {
                int index = matches.get(currentPosition).start();
                int end = matches.get(currentPosition).end();
                textArea.setCaretPosition(end);
                textArea.select(index, end);
                textArea.grabFocus();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showNextMatch(JTextArea textArea) {
        if (!matches.isEmpty()) {

            if (currentPosition < matches.size() - 1) {
                currentPosition++;
            } else {
                currentPosition = 0;
            }
            int index = matches.get(currentPosition).start();
            int end = matches.get(currentPosition).end();
            textArea.setCaretPosition(index);
            textArea.select(index, end);
            textArea.grabFocus();
        }
    }

    public void showPreviousMatch(JTextArea textArea) {
        if (!matches.isEmpty() && currentPosition >= 0) {
            if (currentPosition > 0) {
                currentPosition--;
            } else {
                currentPosition = matches.size() - 1;
            }
            int index = matches.get(currentPosition).start();
            int end = matches.get(currentPosition).end();
            textArea.setCaretPosition(index);
            textArea.select(index, end);
            textArea.grabFocus();
        }
    }
}
