package gr.mysql.server.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import gr.mysql.server.Config;
import gr.mysql.server.utils.Log;

public class Listener {

    private ServerSocket listenSocket;
    private LinkedList<Thread> liveConnectionHandlers=new LinkedList<>();

    public boolean init() {
        Log.Debug("Initializing listen socket..");
        try {
            listenSocket = new ServerSocket(Config.getPort());
            Log.Debug("Listen socket initialized.");
        } catch (Exception e) {
            Log.Critical(e);
            return false;
        }
        return true;
    }

    public void listen() {
        Log.Debug("Now listening.");
        try {
            while (true) {
                Socket conn = listenSocket.accept();
                Log.Debug("Connection attempt from "+conn.getInetAddress()+".");

                handleConnection(conn);
            }
        } catch (Exception e) {
            Log.Critical(e);
        }
    }

    private void handleConnection(Socket c) {
        Handler h=new Handler(c);
        Thread t=new Thread(h);
        t.run();
        liveConnectionHandlers.add(t); //TODO: clean&remove closed connections
    }
}