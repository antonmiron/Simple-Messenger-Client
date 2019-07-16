package com.example.mymessenger.tools;

import android.app.Application;

import com.example.mymessenger.models.Config;
import com.example.mymessenger.network.Connection;

public class GlobalApplication extends Application {
    private Connection connection;
    private Config config;
    public static final String LOG_TAG="SimpleMessengerExample";

    public Connection getConnection() {
        return connection;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Config getConfig() {
        return config;
    }
    public void setConfig(Config config) {
        this.config = config;
    }
}
