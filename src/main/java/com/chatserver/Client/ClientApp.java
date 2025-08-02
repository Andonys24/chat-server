package com.chatserver.Client;

import java.io.IOException;
import java.net.Socket;

import com.chatserver.Network.Connection;
import com.chatserver.Utils.Config;
import com.chatserver.Utils.UI;

public class ClientApp {
    private static boolean connected = false;
    private static Socket socket;
    private static Connection connection;
    private static ClientProtocolHandler clientProtocol;

    public static void main(String[] args) {

        UI.cleanConsole();

        if (!establishConnection()) {
            return;
        }

        try {
            connection = new Connection(socket);
            clientProtocol = new ClientProtocolHandler(connection);

            while (connected) {
                if (clientProtocol.processResponse()) {
                    connected = false;
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            cleanUp();
        }
    }

    private static boolean establishConnection() {
        final String HOST = Config.get("HOST");
        final int PORT = Integer.parseInt(Config.get("PORT"));
        try {
            socket = new Socket(HOST, PORT);
            System.out.print("Conexion establecida con el servidor: ");
            System.out.println(socket.getInetAddress() + ":" + socket.getPort());
            connected = true;
            return true;
        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor");
            return false;
        }
    }

    private static void cleanUp() {
        try {
            if (clientProtocol != null)
                clientProtocol.close();

        } catch (IOException e) {
            System.err.println("Error al cerrar los recursos: " + e.getMessage());
        }
    }

}
