package com.chatserver.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.chatserver.Utils.FileManager;
import com.chatserver.Utils.RequestManager;

public class ServerRequestHandler extends RequestManager {

    public ServerRequestHandler(FileManager fileManager, InputStream input, OutputStream out) {
        super(input, out);
    }

    public void sendReply(ServerResponse response, final String content) throws IOException {
        super.sendReply(response.name(), content);
    }

    @Override
    public boolean processResponse() throws IOException {
        sendMainMenu();

        String[] responseClient = getParts();
        var command = ClientCommand.valueOf(responseClient[0]);

        switch (command) {
            case ENTRAR:

                break;
            case USUARIOS:
                viewConnectedClient();
                break;
            case MENSAJE:
                System.out.println("Enviando Mensaje...");
                break;
            case TODOS:

                break;

            case SALIR:
                sendReply(ServerResponse.OK_SALIR, null);
                System.out.println("Cerrando conexion con el cliente.");
                return false;
            default:
                System.out.println("Comando No valido: " + command);
                sendReply(ServerResponse.ERROR_COMANDO, null);
                break;
        }

        return true;
    }

    public void sendMenu(final String title, String[] options) throws IOException {
        var content = new StringBuilder();

        content.append(title);
        for (var option : options)
            content.append("|").append(option);

        sendReply(ServerResponse.MENU, content.toString());
    }

    public void sendCommandList(String[] commads) throws IOException {
        StringBuilder commandList = new StringBuilder();

        for (int i = 0; i < commads.length; i++) {
            commandList.append(commads[i]);
            if (i < commads.length - 1)
                commandList.append("|");
        }

        sendReply(ServerResponse.INFO_ENTRADA, commandList.toString());
    }

    private void sendMainMenu() throws IOException {
        String[] options = { "Ver Usuarios Conectados", "Enviar Mensaje", "Salir" };
        String[] commandList = { ClientCommand.USUARIOS.name(), ClientCommand.MENSAJE.name(),
                ClientCommand.SALIR.name() };

        sendMenu("Menu Principal", options);
        sendReply(ServerResponse.PEDIR_ELECCION, "Ingrese una opcion");
        sendCommandList(commandList);
    }

    private void viewConnectedClient() {
        System.out.println("Solicitando ver clientes disponibles.");
    }

}
