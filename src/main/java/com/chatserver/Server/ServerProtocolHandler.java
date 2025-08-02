package com.chatserver.Server;

import java.io.IOException;

import com.chatserver.Network.Connection;
import com.chatserver.Network.Protocol;
import com.chatserver.Utils.ProtocolManager;

public class ServerProtocolHandler extends ProtocolManager {

    public ServerProtocolHandler(Connection connection) {
        super(connection);
    }

    @Override
    public boolean processResponse() throws IOException {
        sendMainMenu();

        String[] responseClient = getParts();

        switch (responseClient[0]) {
            case Protocol.CMD_ENTER:

                break;
            case Protocol.CMD_USERS:
                viewConnectedClient();
                break;
            case Protocol.CMD_MESSAGE:
                System.out.println("Enviando Mensaje...");
                break;
            case Protocol.CMD_ALL:

                break;
            case Protocol.CMD_EXIT:
                sendReply(Protocol.RESP_OK_EXIT, null);
                System.out.println("Cerrando conexion con el cliente.");
                return false;
            default:
                System.out.println("Comando No valido: " + responseClient[0]);
                sendReply(Protocol.RESP_ERROR_COMMAND, null);
                break;
        }

        sendReply(Protocol.RESP_PAUSE, "continuar");

        return true;
    }

    public void sendMenu(final String title, String[] options) throws IOException {
        var content = new StringBuilder();

        content.append(title);
        for (var option : options)
            content.append("|").append(option);
        sendReply(Protocol.RESP_MENU, content.toString());
    }

    public void sendCommandList(String[] commads) throws IOException {
        StringBuilder commandList = new StringBuilder();

        for (int i = 0; i < commads.length; i++) {
            commandList.append(commads[i]);
            if (i < commads.length - 1)
                commandList.append("|");
        }
        sendReply(Protocol.RESP_INFO_ENTER, commandList.toString());
    }

    private void sendMainMenu() throws IOException {
        String[] options = { "Ver Usuarios Conectados", "Enviar Mensaje", "Salir" };
        String[] commandList = { Protocol.CMD_USERS, Protocol.CMD_MESSAGE, Protocol.CMD_EXIT };

        sendMenu("Menu Principal", options);
        sendReply(Protocol.RESP_CHOICE, "Ingrese una opcion");
        sendCommandList(commandList);
    }

    private void viewConnectedClient() throws IOException {
        System.out.println("Solicitando ver clientes disponibles.");
        sendReply(Protocol.RESP_USERS, "Listando Clientes");
    }

}
