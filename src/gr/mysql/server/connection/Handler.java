package gr.mysql.server.connection;

import gr.mysql.server.utils.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Handler implements Runnable {

    private Socket connectionSocket;
    private BufferedReader in;

    public Handler(Socket conn) {
        connectionSocket=conn;
    }

    @Override
    public void run() {
        try {

            in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            while (true) {
                Log.Print(in.readLine());
            }

        } catch (Exception e) {
            Log.Warning(e);
        }
    }

}
