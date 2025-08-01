package com.chatserver.Server;

import com.chatserver.Utils.Config;

public class ServerApp {
    public static void main(String[] args) {
        final int PORT = Integer.parseInt(Config.get("PORT"));

        System.out.println("El servidor estara escuchando por e puerto: " + PORT);
    }
}
