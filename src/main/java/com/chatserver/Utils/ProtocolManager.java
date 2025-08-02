package com.chatserver.Utils;

import java.io.IOException;

import com.chatserver.Network.Connection;

public abstract class ProtocolManager {
    protected final Connection connection;

    public ProtocolManager(Connection connection) {
        this.connection = connection;
    }

    public void close() throws IOException {
        if (connection == null) {
            throw new IOException("Error: La conexion no esta inicializada");
        }
        connection.close();
    }

    // Leer mensaje de la forma Header|contenido y dividirlo en un arreglo
    public String[] getParts() throws IOException {
        var rawMessage = connection.receiveMessage();
        return rawMessage.split("\\|");
    }

    // Enviar mensaje de la forma header|contenido
    public void sendReply(final String header, final String content) throws IOException {
        String formatterdMessage;
        if (content == null) {
            formatterdMessage = header + "|";
        } else {
            formatterdMessage = header + "|" + content;
        }
        connection.sendMessage(formatterdMessage);
    }

    public abstract boolean processResponse() throws IOException;
}
