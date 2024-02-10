package ru.gb.hw.server;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ServerWindow extends JFrame implements Repository{
    private final Server server;
    private static final String pathLogs = "logs.txt";
    private static final int POS_X = 500;
    private static final int POS_Y = 550;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static String message;

    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");
    private static final JTextArea log = new JTextArea();
    private final JPanel btnPanel = new JPanel(new FlowLayout());

    public ServerWindow(){
        this.server = new Server(this);

        btnStop.addActionListener(actionEvent -> {
            Server.setServerWorking(false);
            btnStart.setEnabled(true);
            addMessage("Server is working " + Server.isServerWorking());
            saveMessages(pathLogs);
        });

        btnStart.addActionListener(actionEvent -> {
            Server.setServerWorking(true);
            createStorage(pathLogs);
            readLog(pathLogs);
            addMessage("----------------------");
            addMessage("Server is working " + Server.isServerWorking());
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

    public Server getConnection() {
        return this.server;
    }

    @Override
    public void addMessage(String message) {
        log.append(message + "\n");
        log.updateUI();
    }

    @Override
    public void readLog(String pathFile) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(pathFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.setText(content.toString());
    }

    @Override
    public void createStorage(String pathLogs) {
        File file = new File(pathLogs);

        try {
            if (file.createNewFile()) {
                addMessage("Файл " + "logs.txt" + " создан успешно.");
            }
        } catch (IOException e) {
            addMessage("Ошибка при создании файла: " + e.getMessage());
        }
    }

    @Override
    public void saveMessages(String pathFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile))){
            writer.write(log.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
