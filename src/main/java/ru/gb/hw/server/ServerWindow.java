package ru.gb.hw.server;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ServerWindow extends JFrame {
    private static final int POS_X = 500;
    private static final int POS_Y = 550;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");
    private static final JTextArea log = new JTextArea();
    private final JScrollPane scrollLog = new JScrollPane(log);
    private boolean isServerWorking;
    private final JPanel btnPanel = new JPanel(new FlowLayout());

    public static void main(String[] args) {
        new ServerWindow();
    }

    private ServerWindow(){
        isServerWorking = false;
        btnStop.addActionListener(actionEvent -> {
            isServerWorking = false;
            btnStart.setEnabled(true);
            log.append("Server is working " + isServerWorking + "\n");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("logs.txt"))){
                writer.write(log.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnStart.addActionListener(actionEvent -> {
            isServerWorking = true;
            createFileIfNotExists();
            try {
                readLogFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            log.append("-------------------------\n");
            log.append("Server is working " + isServerWorking + "\n");
            btnStart.setEnabled(false);
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat Server");
        setAlwaysOnTop(true);
        log.setEnabled(false);
        add(scrollLog, BorderLayout.CENTER);
        btnPanel.add(btnStart);
        btnPanel.add(btnStop);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void createFileIfNotExists() {
        File file = new File("logs.txt");

        try {
            if (file.createNewFile()) {
                log.append("Файл " + "logs.txt" + " создан успешно.");
            }
        } catch (IOException e) {
            log.append("Ошибка при создании файла: " + e.getMessage());
        }
    }

    private static void readLogFile() throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("logs.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        log.setText(content.toString());
    }
}
