package gr.mysql.server;

public class Config {

    private final static int PORT=3306;
    private final static int VERBOSE=3;

    public static int getPort() {
        return PORT;
    }
    public static int getVerbose() {
        return VERBOSE;
    }
}