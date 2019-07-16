package com.example.mymessenger.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymessenger.BR;
import com.example.mymessenger.models.Config;
import com.example.mymessenger.network.Connection;
import com.example.mymessenger.network.ConnectionHelper;
import com.example.mymessenger.network.MessageRequest;
import com.example.mymessenger.network.MessageResponse;
import com.example.mymessenger.R;
import com.example.mymessenger.models.Message;
import com.example.mymessenger.network.ServerResponse;
import com.example.mymessenger.tools.BindingViewModel;
import com.example.mymessenger.tools.GlobalApplication;
import com.example.mymessenger.tools.MessageType;
import com.example.mymessenger.tools.RecyclerBindingAdapter;
import com.example.mymessenger.tools.RecyclerConfiguration;
import com.example.mymessenger.views.LoginActivity;
import com.example.mymessenger.views.MainActivity;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageViewModel extends BindingViewModel implements ServerResponse {
    private static RecyclerBindingAdapter<Message> messageAdapter;
    private static RecyclerConfiguration messageRecyclerConfig = new RecyclerConfiguration();
    private static List<Message> messageList = new ArrayList<>();
    private BlockingQueue<Message> requestMessageQueue = new LinkedBlockingQueue<>();

    private ObservableBoolean isConnecting = new ObservableBoolean(false);
    private ObservableField<String> messageText = new ObservableField<>();
    private Connection connection;
    private Config config;

    /*Getters, Setters*/
    public RecyclerConfiguration getMessageRecyclerConfig() {
        return messageRecyclerConfig;
    }
    public void setMessageRecyclerConfig(RecyclerConfiguration messageRecyclerConfig) {
        this.messageRecyclerConfig = messageRecyclerConfig;
    }

    public ObservableBoolean getIsConnecting() {
        return isConnecting;
    }
    public void setIsConnecting(ObservableBoolean isConnecting) {
        this.isConnecting = isConnecting;
    }

    public ObservableField<String> getMessageText() {
        return messageText;
    }
    public void setMessageText(ObservableField<String> messageText) {
        this.messageText = messageText;
    }

    public Config getConfig() {
        return config;
    }
    public void setConfig(Config config) {
        this.config = config;
    }

    /*Buttons*/
    public void onClickConnect(View view){
        if(isStringEmpty(config.getIp()) || isStringEmpty(config.getName()))
        {
            Toast.makeText(view.getContext(),R.string.error_empty_fields,Toast.LENGTH_LONG).show();
            return;
        }

        if(!isIpCorrect(config.getIp()))
        {
            Toast.makeText(view.getContext(),R.string.error_wrong_ip_format,Toast.LENGTH_LONG).show();
            return;
        }

        if(!isPortCorrect(config.getPort()))
        {
            Toast.makeText(view.getContext(),R.string.error_wrong_port_number,Toast.LENGTH_LONG).show();
            return;
        }

        isConnecting.set(true);
        ConnectionHelper connectionHelper = new ConnectionHelper();
        connectionHelper.setViewContext(view.getContext());
        connectionHelper.setServerResponse(this);
        connectionHelper.execute(config);

    }
    public void onClickSendMessage(View view){
        if(messageText.get()==null || messageText.get().isEmpty())
            return;

        Message message = new Message(MessageType.TEXT,messageText.get());
        sendMessage(message);

        messageText.set("");
    }

    /*Settings*/
    private void setMainActivitySettings(final Activity activity){
        config = ((GlobalApplication)activity.getApplication()).getConfig();
        connection = ((GlobalApplication)activity.getApplication()).getConnection();
        if(connection == null || config == null) {
            activity.startActivity(LoginActivity.getIntent(activity, true));
            return;
        }

        messageAdapter = new RecyclerBindingAdapter<Message>(R.layout.activity_recycler_message_item, BR.message, messageList);
        messageRecyclerConfig.setAdapter(messageAdapter);
        messageRecyclerConfig.setLayoutManager(new LinearLayoutManager(activity));

        Handler handler = new MyHandler();

        MessageResponse messageResponse = new MessageResponse(connection,handler,config);
        MessageRequest messageRequest = new MessageRequest(connection,requestMessageQueue);

        Thread responseThread = new Thread(messageResponse);
        responseThread.setDaemon(true);
        responseThread.start();

        Thread requestThread = new Thread(messageRequest);
        requestThread.setDaemon(true);
        requestThread.start();
    }
    private void setLoginActivitySettings(Activity activity){
        config = new Config();
        config.setTimeoutConnection(5000); //5 секунд, на подключение
    }

    /*Additional functions*/
    public boolean isStringEmpty(String str){
        return str==null || str.isEmpty() || str.matches("^\\s+$");
    }
    public boolean isIpCorrect(String ip){
        String regex="^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$";
        if(!ip.matches(regex)) return false;

        String[] ipArray = ip.split("\\.");
        for(String element: ipArray){
            int ipPart = Integer.parseInt(element);
            if(ipPart>255)
                return false;
        }
        return true;
    }
    public boolean isPortCorrect(int port){
        return port>0 && port<65536;
    }

    @Override
    public void connectionError(Context context) {
        Toast.makeText(context,R.string.error_connection,Toast.LENGTH_LONG).show();
        isConnecting.set(false);
    }

    private void sendMessage(Message message){
        try {
            requestMessageQueue.put(message);
        }catch (InterruptedException ex){ Log.e(GlobalApplication.LOG_TAG,ex.getMessage());}
    }
    @Override
    public void setActivityType(Activity activity) {
        if(activity instanceof MainActivity)
            setMainActivitySettings(activity);
        if(activity instanceof LoginActivity)
            setLoginActivitySettings(activity);
    }
    private static class MyHandler extends Handler{
        @Override
        public void handleMessage(android.os.Message msg) {
            Bundle reply = msg.getData();
            Message message = (Message)reply.getSerializable(MessageResponse.BUNDLE_MESSAGE);

            messageList.add(message);
            messageAdapter.notifyDataSetChanged();
        }

    }
}
