package com.chatserver.Server;

import java.io.IOException;
import java.net.Socket;

import com.chatserver.Network.Connection;
import com.chatserver.Utils.FileManager;

public class ConnectionHandler implements Runnable {
    private final FileManager fm;
    private final Socket client;
    private final Connection connection;
    private ServerProtocolHandler serverProtocol;

    public ConnectionHandler(Socket socket, FileManager fileManager) throws IOException {
        this.client = socket;
        this.fm = fileManager;
        this.connection = new Connection(this.client);
    }

    @Override
    public void run() {

        try {
            serverProtocol = new ServerProtocolHandler(connection);

            while (true) {

                if (serverProtocol.processResponse())
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

        try {
            if (serverProtocol != null)
                serverProtocol.close();

        } catch (IOException e) {
            System.err.println("Error al cerrar la conexion con el cliente: " + e.getMessage());
        }
    }

}
