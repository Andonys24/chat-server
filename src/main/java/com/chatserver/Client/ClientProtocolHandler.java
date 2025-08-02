package com.chatserver.Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import com.chatserver.Utils.ProtocolManager;
import com.chatserver.Utils.UI;

public class ClientProtocolHandler extends ProtocolManager {
    private Console console;

    public ClientProtocolHandler(InputStream input, OutputStream output) {
        super(input, output);
        this.console = new Console();
    }

    public void sendReply(ClientCommand command, final String content) throws IOException {
        super.sendReply(command.name(), content);
    }

    @Override
    public boolean processResponse() throws IOException {
        String[] response = getParts();
        var command = ServerResponse.valueOf(response[0]);

        switch (command) {
            case OK_ENTRAR:

                break;
            case MENU:
                // Obtener Menu en el formato: MENU|TITLE|OPTIONS
                var title = response[1];
                String[] options = Arrays.copyOfRange(response, 2, response.length);
                UI.generateMenu(title, options);
                break;
            case PEDIR_ELECCION:
                // Obtener lista de comandos para enviar como respuesta
                String[] commands = getParts();
                // Pedir entrada al usuario y validar rango
                int option = console.validateOption(response[1], commands.length - 1);
                // Enviar Mensaje
                sendReply(commands[option], null);
                break;
            case ERROR_COMANDO:
                System.out.println("Comando No Valido");
                break;
            case OK_SALIR:
                // Al recibir esta respuesta cerrar la conexion
                console.close();
                System.out.println("Cerrando Conexion con el servidor");
                return true;

            default:
                break;
        }

        return false;
    }

}
