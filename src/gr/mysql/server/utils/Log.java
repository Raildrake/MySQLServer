package gr.mysql.server.utils;

import gr.mysql.server.Config;

public class Log {

    public static void Critical(Object err) {
        if (Config.getVerbose()==0) return;
        if (err instanceof Exception) ((Exception)err).printStackTrace();
        else System.out.println(err);
    }
    public static void Print(Object m) {
        if (Config.getVerbose()==0) return;
        System.out.println(m);
    }

    public static void Warning(Object err) {
        if (Config.getVerbose()<2) return;
        if (err instanceof Exception) ((Exception)err).printStackTrace();
        else System.out.println(err);
    }
    public static void Debug(Object d) {
        if (Config.getVerbose()<3) return;
        System.out.println(d);
    }

    public static void PrintBuffer(byte[] buffer) {
        if (Config.getVerbose()<3) return;

        for(byte b : buffer) {
            System.out.print((int)b+" ");
        }
    }

}