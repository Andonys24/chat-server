package com.chatserver.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {
    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final Object sendLock;
    private final Object receiveLock;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(this.socket.getInputStream());
        this.output = new DataOutputStream(this.socket.getOutputStream());
        this.sendLock = new Object();
        this.receiveLock = new Object();
    }

    // Metodos de Informacion

    public String getRemoteAddress() {
        if (socket != null && socket.getInetAddress() != null) {
            return socket.getInetAddress().getHostAddress();
        }
        return "Unknown";
    }

    public int getRemotePort() {
        if (socket != null) {
            return socket.getPort();
        }
        return -1;
    }

    public boolean isConnected() {
        return socket != null &&
                socket.isConnected() &&
                !socket.isClosed() &&
                !socket.isInputShutdown() &&
                !socket.isOutputShutdown();
    }

    // Metodos de comunicacion
    public void sendMessage(String message) throws IOException {
        synchronized (sendLock) {
            if (!isConnected()) {
                throw new IOException("Conexion Cerrada.");
            }
            output.writeUTF(message);
            output.flush();
        }
    }

    public String receiveMessage() throws IOException {
        synchronized (receiveLock) {
            if (!isConnected()) {
                throw new IOException("Conexion cerrada.");
            }
            return input.readUTF();
        }
    }

    // Metodos de Limpieza
    public void close() throws IOException {
        try {
            if (input != null)
                input.close();
        } catch (IOException e) {
            // Log
        }

        try {
            if (output != null)
                output.close();
        } catch (IOException e) {
            // Log
        }

        if (socket != null && !socket.isClosed())
            socket.close();

    }

    // Metodos de Utilidad
    public String getConnectionInfo() {
        return String.format("Connection[%s:%d, conneced: %s]", getRemoteAddress(), getRemotePort(), isConnected());
    }

    @Override
    public String toString() {
        return getConnectionInfo();
    }

}
