package ru.gb.hw.client;

import ru.gb.hw.server.ServerWindow;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Objects;

public class ClientWindow extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static String message = "";

    private final JTextArea log = new JTextArea();

    private final JPanel panelTop = new JPanel(new GridLayout(2,3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JTextField tfLogin = new JTextField("User");
    private final JPasswordField tfPassword = new JPasswordField("123456");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");
    private final ServerWindow serverWindow;

    public ClientWindow(ServerWindow serverWindow) {
        this.serverWindow = serverWindow;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setTitle("Chat Client");
        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);

        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);

        log.setEditable(false);
        btnSend.setEnabled(false);
        tfMessage.setEnabled(false);
        JScrollPane scrolling = new JScrollPane(log);
        add(scrolling);

        setVisible(true);

        btnSend.addActionListener(actionEvent -> {
            if (serverWindow.isServerWorking()) {
                message = tfLogin.getText() + ": " + tfMessage.getText() + "\n";
                log.append(message);
                tfMessage.setText("");
            } else {
                log.append("Server is offline\n");
            }
        });
        btnLogin.addActionListener(actionEvent -> {
            if (serverWindow.isServerWorking() && Objects.equals(btnLogin.getText(), "Login")) {
                log.append("Connection Successful!\n");
                btnLogin.setText("Logout");
                btnSend.setEnabled(true);
                tfMessage.setEnabled(true);
            } else if (serverWindow.isServerWorking() && Objects.equals(btnLogin.getText(), "Logout")) {
                btnLogin.setText("Login");
                btnSend.setEnabled(false);
                tfMessage.setEnabled(false);
            } else {
                log.append("Can't connect to the server...\n");
            }
        });

    }
}
