package ru.gb.hw.client;

import ru.gb.hw.server.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class ClientWindow extends JFrame implements View{
    private Client client;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    public final JTextArea log = new JTextArea();

    private final JPanel panelTop = new JPanel(new GridLayout(2,3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JTextField tfLogin = new JTextField("User");
    private final JPasswordField tfPassword = new JPasswordField("123456");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    public ClientWindow(ServerWindow serverWindow) {

        client = new Client(this, serverWindow.getConnection());
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
        btnSend.addActionListener(e -> sendMessage());
        btnLogin.addActionListener(e -> {
            if (Objects.equals(btnLogin.getText(), "Login")) {
                connectedToServer();
            }else disconnectedFromServer();
            });
    }

    public void sendMessage(){
        client.sendMessage(tfMessage.getText());
        tfMessage.setText("");
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING){
            disconnectedFromServer();
        }
    }

    @Override
    public void sendMessage(String message) {
        log.append(message);
    }

    @Override
    public void connectedToServer() {
        if (client.connectToServer(tfLogin.getText())){
            btnLogin.setText("Logout");
            tfMessage.setEnabled(true);
            btnSend.setEnabled(true);

        }
    }

    @Override
    public void disconnectedFromServer() {
        client.disconnectFromServer();
        btnLogin.setText("Login");
        tfMessage.setEnabled(false);
        btnSend.setEnabled(false);
    }
}
