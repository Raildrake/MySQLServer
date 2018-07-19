package gr.mysql.server.connection.packets;

import gr.mysql.server.utils.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

abstract class Packet {

    public enum STR_TYPE { FIXED,NULL,VAR,ENC,REST }

    //No need for annotations because these are hard coded and needed for all packets.
    private int sequenceId;
    private byte[] payload=new byte[0];

    @Retention(RetentionPolicy.RUNTIME)
    public @interface PacketInt {
      int value();
    }
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PacketString {
        STR_TYPE type();
        int fixed();
        String var_name();
    }

    public int getPayloadLength() {
        return payload.length;
    }
    public int getSequenceID() {
        return sequenceId;
    }
    public byte[] getPayload() {
        return payload;
    }
    public void setSequenceID(int s) {
        sequenceId=s;
    }
    private void setPayload(byte[] p) {
        payload=p;
    } //no need for this to be public, payload is set through encode only

    private void encodePayload() throws Exception {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

        for (Field f: this.getClass().getFields()) {
            PacketInt pInt = f.getAnnotation(PacketInt.class);
            if (pInt != null) byteOut.write(encodeInt(f.getInt(this),pInt.value()));
        }

        setPayload(byteOut.toByteArray());
    }
    public byte[] encode() throws Exception {
        encodePayload();

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

        byteOut.write(encodeInt(getPayloadLength(),3));
        byteOut.write(encodeInt(getSequenceID(),1));
        byteOut.write(getPayload());

        return byteOut.toByteArray();
    }

    private byte[] encodeInt(int val, int size) throws Exception {
        byte[] buffer;
        switch (size) {
            case 1: return new byte[]{(byte)(val&0xFF)};
            case 2: return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short)val).array();
            case 3:
                buffer=ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(val).array();
                return new byte[]{buffer[0],buffer[1],buffer[2]};
            case 4: return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(val).array();
        }
        throw new Exception("Invalid size.");
    }

}