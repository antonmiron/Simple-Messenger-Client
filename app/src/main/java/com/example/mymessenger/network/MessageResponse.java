package com.example.mymessenger.network;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.mymessenger.models.Config;
import com.example.mymessenger.models.Message;
import com.example.mymessenger.tools.GlobalApplication;
import com.example.mymessenger.tools.MessageType;

import java.io.IOException;

public class MessageResponse implements Runnable{

    public static final String BUNDLE_MESSAGE = "message";
    private Connection connection;
    private Handler handler;
    private Config config;

    public MessageResponse(Connection connection,Handler handler, Config config){
        this.connection=connection;
        this.handler=handler;
        this.config =config;
    }
    @Override
    public void run() {
        try {
            clientHandshake(config.getName());
            clientMainLoop();
        }catch (IOException ex){ Log.e(GlobalApplication.LOG_TAG,ex.getMessage()); }
        catch (ClassNotFoundException ex){ Log.e(GlobalApplication.LOG_TAG,ex.getMessage()); }
    }

    /**"Знакомимся" с сервером**/
    protected void clientHandshake(String name) throws IOException, ClassNotFoundException {
        int nameIncrementer = 0; //если в чате уже есть участник с таким же именем мы добавляем к нашему нику порядковый номер
        while (true) {
            Message message = connection.receive();

            switch (message.getType()) {
                case NAME_REQUEST: {
                    connection.send(new Message(MessageType.USER_NAME, name));
                    break;
                }
                case NAME_ACCEPTED: {
                    return;
                }
                case NAME_NOT_ACCEPTED:{
                    nameIncrementer++;
                    connection.send(new Message(MessageType.USER_NAME,name+" ("+nameIncrementer+")")); //вот здесь -> "Петр (1)" и т.д.
                    break;
                }
                default: {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }
    }
    /**Основной цикл**/
    protected void clientMainLoop() throws IOException, ClassNotFoundException {

        while (true) {
            Message message = connection.receive();
            android.os.Message msg = android.os.Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_MESSAGE,message);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    }
}
