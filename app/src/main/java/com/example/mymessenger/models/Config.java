package com.example.mymessenger.models;

public class Config {
    private String ip;
    private int port;
    private String name;
    private int timeoutConnection;

    public Config(){}

    public Config(String ip, int port, String name, int timeoutConnection) {
        this.ip = ip;
        this.port = port;
        this.name = name;
        this.timeoutConnection = timeoutConnection;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeoutConnection() {
        return timeoutConnection;
    }

    public void setTimeoutConnection(int timeoutConnection) {
        this.timeoutConnection = timeoutConnection;
    }
}
