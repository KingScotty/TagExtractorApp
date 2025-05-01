import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;
import java.util.*;

public class TagExtractorFrame extends JFrame {
    private JButton selectTextFileButton;
    private JButton selectStopWordFileButton;
    private JButton extractTagsButton;
    private JButton saveTagsButton;
    private JLabel selectedFileLabel;
    private JTextArea outputArea;

    private File textFile;
    private File stopWordsFile;

    public TagExtractorFrame() {
        setTitle("Tag Extractor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new FlowLayout());

        //Top Panel
        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        selectedFileLabel = new JLabel("No file selected");

        selectTextFileButton = new JButton("Select Text File");
        selectTextFileButton.addActionListener(e -> chooseTextFile());

        selectStopWordFileButton = new JButton("Select Stop Word File");
        selectStopWordFileButton.addActionListener(e -> chooseStopWordsFile());

        topPanel.add(selectTextFileButton);
        topPanel.add(selectStopWordFileButton);
        topPanel.add(selectedFileLabel);
        add(topPanel, BorderLayout.NORTH);

        //Center output panel
        JPanel bottomPanel = new JPanel();
        extractTagsButton = new JButton("Extract Tags");
        extractTagsButton.addActionListener(e -> extractTags());
        saveTagsButton = new JButton("Save Tags");
        saveTagsButton.addActionListener(e -> saveTags());

        bottomPanel.add(extractTagsButton);
        bottomPanel.add(saveTagsButton);
        add(bottomPanel, BorderLayout.SOUTH);

        outputArea = new JTextArea(20, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);

    }
// infinite constructors go brr
    private void chooseTextFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            textFile = chooser.getSelectedFile();
            selectedFileLabel.setText("Selected File: " + textFile.getName());
        }
    }

    private void chooseStopWordsFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            stopWordsFile = chooser.getSelectedFile();
        }
    }

    private void extractTags() {
        if (textFile == null || stopWordsFile == null) {
            JOptionPane.showMessageDialog(this, "Please select both text and stop word files.");
            return;
        }

        Set<String> stopWords = loadStopWords(stopWordsFile);
        Map<String, Integer> wordFreq = new TreeMap<>();

        try (Scanner scanner = new Scanner(textFile)) {
            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase().replaceAll("[^a-z]", "");
                if (!word.isEmpty() && !stopWords.contains(word)) {
                    wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading text file.");
        }

        outputArea.setText("");
        for (Map.Entry<String, Integer> entry : wordFreq.entrySet()) {
            outputArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
        }
    }

    private Set<String> loadStopWords(File file) {
        Set<String> stopWords = new HashSet<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                stopWords.add(scanner.nextLine().trim());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading stop words file.");
        }
        return stopWords;
    }

    private void saveTags() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = chooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(saveFile)) {
                writer.write(outputArea.getText());
                JOptionPane.showMessageDialog(this, "Tags saved successfully.");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving file.");
            }
        }
    }
}