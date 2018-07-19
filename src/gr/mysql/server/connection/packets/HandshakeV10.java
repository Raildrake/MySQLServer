package gr.mysql.server.connection.packets;

public class HandshakeV10 extends Packet {

    @PacketInt(1)
    public int protocol=10;

}