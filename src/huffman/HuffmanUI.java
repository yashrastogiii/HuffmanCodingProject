package huffman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;

public class HuffmanUI extends JFrame {

    private JTextArea outputArea;
    private HuffmanCode huffmanCode;

    public HuffmanUI() {
        setTitle("Huffman Coding Compression");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Setting up the UI components
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JButton encodeButton = new JButton("Compress File");
        JButton decodeButton = new JButton("Decompress File");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(encodeButton);
        buttonPanel.add(decodeButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        add(panel);

        // Adding ActionListeners
        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        String content = new String(Files.readAllBytes(selectedFile.toPath()));
                        huffmanCode = new HuffmanCode(content);
                        String encodedText = huffmanCode.encode(content);

                        // Save encoded text to file
                        File compressedFile = new File(selectedFile.getParent(), "compressed.huff");
                        try (FileWriter writer = new FileWriter(compressedFile)) {
                            writer.write(encodedText);
                        }

                        outputArea.setText("File compressed successfully. Compressed file: " + compressedFile.getAbsolutePath());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        String encodedContent = new String(Files.readAllBytes(selectedFile.toPath()));
                        String decodedText = huffmanCode.decode(encodedContent);

                        // Save decoded text to file
                        File decompressedFile = new File(selectedFile.getParent(), "decompressed.txt");
                        try (FileWriter writer = new FileWriter(decompressedFile)) {
                            writer.write(decodedText);
                        }

                        outputArea.setText("File decompressed successfully. Decompressed file: " + decompressedFile.getAbsolutePath());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HuffmanUI().setVisible(true);
            }
        });
    }
}
