package ru.gb.hw.server;

public interface Repository {
    void addMessage(String message);
    void readLog(String pathFile);
    void createStorage(String pathLogs);
    void saveMessages(String pathFile);
}
