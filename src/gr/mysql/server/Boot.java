package gr.mysql.server;

import gr.mysql.server.connection.Listener;
import gr.mysql.server.connection.packets.HandshakeV10;
import gr.mysql.server.utils.Log;

public class Boot {

    public static void main(String[] args) {

        try {
            HandshakeV10 h = new HandshakeV10();
            Log.PrintBuffer(h.encode());
        } catch (Exception e) {
            Log.Critical(e);
        }


        /*Listener l=new Listener();

        if (!l.init()) return;
        l.listen();*/
    }

}
