package com.chatserver.Server;

import java.io.IOException;
import java.net.Socket;

import com.chatserver.Utils.FileManager;

public class ConnectionHandler implements Runnable {
    private final Socket client;
    private final FileManager fm;
    private ServerProtocolHandler sh;

    public ConnectionHandler(Socket socket, FileManager fileManager) {
        this.client = socket;
        this.fm = fileManager;
    }

    @Override
    public void run() {

        try {
            sh = new ServerProtocolHandler(fm, client.getInputStream(), client.getOutputStream());

            while (true) {

                if (sh.processResponse())
                    continue;

                break;
            }
        } catch (IOException e) {
            System.err.println("Error en manejo de el cliente: " + e.getMessage());
        } finally {
            cleanUp();
        }

    }

    private void cleanUp() {
        if (client == null) {
            System.out.println("Error: El cliente no esta inicializado");
            return;
        }

        if (client.isClosed()) {
            System.out.println("Error: La conexion con el cliente ya fue cerrada");
            return;
        }

        try {
            sh.close();
            client.close();
        } catch (IOException e) {
            System.err.println("Error al cerrar la conexion con el cliente: " + e.getMessage());
        }
    }

}
