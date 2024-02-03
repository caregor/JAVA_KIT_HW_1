package ru.gb.hw.server;

import ru.gb.hw.client.ClientWindow;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class ServerWindow extends JFrame {
    private final List<ClientWindow> clientWindows = new ArrayList<>();
    private static final String pathLogs = "logs.txt";
    private static final int POS_X = 500;
    private static final int POS_Y = 550;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static String message;

    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");
    private static final JTextArea log = new JTextArea();
    private static boolean isServerWorking;
    private final JPanel btnPanel = new JPanel(new FlowLayout());

    public boolean isServerWorking() {
        return isServerWorking;
    }
    public ServerWindow(){
        isServerWorking = false;
        btnStop.addActionListener(actionEvent -> {
            isServerWorking = false;
            btnStart.setEnabled(true);
            log.append("Server is working " + isServerWorking + "\n");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathLogs))){
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
        JScrollPane scrollLog = new JScrollPane(log);
        add(scrollLog, BorderLayout.CENTER);
        btnPanel.add(btnStart);
        btnPanel.add(btnStop);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void createFileIfNotExists() {
        File file = new File(pathLogs);

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
        try (BufferedReader reader = new BufferedReader(new FileReader(pathLogs))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        log.setText(content.toString());
    }

    public void addClient(ClientWindow clientWindow) {
        clientWindows.add(clientWindow);


    public void sendMessage(String message, ClientWindow sender) {
        System.out.println("Sender: " + sender);
        for (ClientWindow clientWindow : clientWindows) {
            System.out.println("client: " + clientWindow);
            if (clientWindow != sender) {
                System.out.println("Found: " + clientWindow);
                clientWindow.log.append(message + "\n");
            }
        }
    }

}
