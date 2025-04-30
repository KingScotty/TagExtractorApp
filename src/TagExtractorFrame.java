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
    private JButton selectedFiledLabel;
    private JLabel sleectedFileLabel;
    private File textFile;
    private File stopWordFile;

    public TagExtractorFrame() {
        setTitle("Tag Extractor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new FlowLayout());

        //Top Panel
        JPanel topPanel = new JPanel(new GridLayout(3,1));
        JLabel selectedFileLabel = new JLabel("no file selected");

        selectTextFileButton = new JButton("Select Text File");
        selectTextFileButton.addActionListener(e -> chooseTextFile());

        selectStopWordFileButton = new JButton("Select Stop Word File");
        selectStopWordFileButton.addActionListener(e -> chooseStopWordFile());

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

        setVisible(true);

    }



}
