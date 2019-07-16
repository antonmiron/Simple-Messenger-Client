package com.example.mymessenger.network;

import android.util.Log;

import com.example.mymessenger.models.Message;
import com.example.mymessenger.tools.GlobalApplication;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class MessageRequest implements Runnable {
    private BlockingQueue<Message> messagesQueue;
    private Connection connection;

    public MessageRequest(Connection connection, BlockingQueue<Message> messagesQueue){
        this.connection=connection;
        this.messagesQueue=messagesQueue;
    }

    @Override
    public void run() {
        while(true){
            try {
                sendMessage(messagesQueue.take());
            }catch (InterruptedException ex){ Log.e(GlobalApplication.LOG_TAG,ex.getMessage());}
        }
    }
    /**Отправка сообщения**/
    private void sendMessage(Message message){
        try {
            connection.send(message);
        }catch (IOException ex){Log.e(GlobalApplication.LOG_TAG,ex.getMessage());}
    }
}
