package edu.school21.sockets.app;

import edu.school21.sockets.server.Server;

public class Program {
    public static void main(String[] args) {
        try {
            Server.makeServer(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
