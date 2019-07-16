package com.example.mymessenger.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mymessenger.models.Config;
import com.example.mymessenger.tools.GlobalApplication;
import com.example.mymessenger.views.MainActivity;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnectionHelper extends AsyncTask<Config,Void,Connection> {
    private WeakReference<Context> contextRef;
    private Config config;
    private ServerResponse serverResponse;

    public void setViewContext(Context viewContext){
        this.contextRef = new WeakReference<Context>(viewContext);
    }
    public void setServerResponse(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }

    /**Устанавливаем связь с сервером**/
    @Override
    protected Connection doInBackground(Config... configs) {
        if(configs.length!= 1)
            return null;

        config = configs[0];
        Connection connection;
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(config.getIp(), config.getPort()),config.getTimeoutConnection());
            connection = new Connection(socket);
        }catch (IOException ex){
            Log.e(GlobalApplication.LOG_TAG,ex.getMessage());
            return null;
        }
        return connection;
    }

    /**Результат**/
    @Override
    protected void onPostExecute(Connection connection) {
        if(contextRef!=null) {
            Context viewContext = contextRef.get();
            if(viewContext!=null) {
                if(connection==null)
                {
                    if(serverResponse!=null)
                        serverResponse.connectionError(viewContext);
                    return;
                }
                GlobalApplication globalApplication = (GlobalApplication) viewContext.getApplicationContext();
                globalApplication.setConnection(connection);
                globalApplication.setConfig(config);
                viewContext.startActivity(MainActivity.getIntent(viewContext,true));
            }

        }
    }
}
