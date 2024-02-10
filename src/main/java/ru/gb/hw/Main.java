package ru.gb.hw;


import ru.gb.hw.client.ClientWindow;
import ru.gb.hw.server.ServerWindow;

public class Main {
    public static void main(String[] args) {
        ServerWindow serverWindow = new ServerWindow();
        new ClientWindow(serverWindow);
        new ClientWindow(serverWindow);
    }
}