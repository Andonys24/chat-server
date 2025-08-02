package com.chatserver.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.chatserver.Utils.Config;
import com.chatserver.Utils.FileManager;
import com.chatserver.Utils.UI;

public class ServerApp {
    private static volatile boolean running = false;
    private static ServerSocket server;
    private static final Object logLock = new Object();

    public static void main(String[] args) {
        Scanner input;
        var fm = new FileManager(FileManager.Context.SERVER);
        startServer();

        if (!isRunning())
            return;

        // Hilo para monitorear comandos por consola
        input = new Scanner(System.in);

        var consoleThread = new Thread(() -> {
            System.out.println("Hilo de monitore Iniciado.");

            while (isRunning()) {
                System.out.println("Ingrese 'exit' para apagar el servidor.");
                var command = input.nextLine();

                if (!"exit".equals(command.trim().toLowerCase())) {
                    if (!command.trim().isEmpty()) {
                        System.out.println("Comando Invalido: '" + command.trim() + "'");
                    }
                    continue; // continuar esperando entrada
                }

                setRunning(false);

                try {
                    server.close();
                    System.out.println("ServerSocket cerrado exitosamente");
                } catch (IOException e) {
                    System.err.println("Error al cerrar el servidor.");
                }
            }

            System.out.println("Hilo de monitoreo de consola Terminado.");
        });

        consoleThread.setDaemon(true);
        consoleThread.start();

        // Logica para manejar a los clientes
        while (isRunning()) {
            try {
                var client = server.accept();
                var clientInfo = client.getInetAddress().toString() + ":" + client.getPort();
                System.out.println("Cliente Conectado: " + clientInfo);

                // Crear hilo virtual por cada cliente
                Thread.ofVirtual().start(new ConnectionHandler(client, fm));

            } catch (IOException e) {
                // Si el servidor se está cerrando, no mostrar el error
                if (isRunning()) {
                    System.err.println("Error al aceptar el cliente: " + e.getMessage());
                }
                // Si el socket está cerrado, salir del bucle
                break;
            } catch (Exception e) {
                System.err.println("Error inesperado: " + e.getMessage());
            }
        }

        input.close();
    }

    private static void startServer() {
        final int PORT = Integer.parseInt(Config.get("PORT"));

        try {
            server = new ServerSocket(PORT);
            UI.cleanConsole();
            System.out.println("Servidor Corriendo en: " + serverInfo());
            setRunning(true);
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
            setRunning(false);
        }
    }

    private static String serverInfo() throws UnknownHostException {
        var host = InetAddress.getLocalHost().getHostAddress();
        var port = server.getLocalPort();
        var info = new StringBuilder();
        info.append(host).append(":").append(port);
        return info.toString();

    }

    public static boolean isRunning() {
        synchronized (logLock) {
            return ServerApp.running;
        }
    }

    public static void setRunning(boolean running) {
        synchronized (logLock) {
            ServerApp.running = running;
        }
    }

}
