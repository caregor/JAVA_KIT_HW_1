package ru.gb.hw.server;

import ru.gb.hw.client.Client;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final String LOG_PATH = "logs.txt";
    private Repository repository;

    private static boolean serverWorking;
    private List<Client> clients = new ArrayList<>();

    public Server(ServerWindow serverWindow) {
        this.repository = serverWindow;
        serverWorking = false;
    }
    public boolean connectUser(Client client) {
        if (!serverWorking) {
            return false;
        }
        clients.add(client);
        return true;
    }

    public static void setServerWorking(boolean serverWorking) {
        Server.serverWorking = serverWorking;
    }
    public static boolean isServerWorking() {
        return serverWorking;
    }

    public String getHistory() {
        return readLog();
    }

    public void disconnectUser(Client client) {
        clients.remove(client);
    }

    public void sendMessage(String message) {
        if (!serverWorking){
            return;
        }
        repository.addMessage(message);
        answerAll(message);

    }

    private void answerAll(String text){
        for (Client client: clients){
            client.answerFromServer(text);
        }
    }

    private String readLog(){
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(LOG_PATH);){
            int c;
            while ((c = reader.read()) != -1){
                stringBuilder.append((char) c);
            }
            stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            return stringBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
