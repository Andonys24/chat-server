package com.chatserver.Client;

import java.io.IOException;
import java.util.Arrays;

import com.chatserver.Network.Connection;
import com.chatserver.Network.Protocol;
import com.chatserver.Utils.ProtocolManager;
import com.chatserver.Utils.UI;

public class ClientProtocolHandler extends ProtocolManager {
    private Console console;

    public ClientProtocolHandler(Connection connection) {
        super(connection);
        this.console = new Console();
    }

    @Override
    public boolean processResponse() throws IOException {
        String[] response = getParts();

        switch (response[0]) { // indice 0 contiene el encabezado
            case Protocol.RESP_OK_ENTER:

                break;
            case Protocol.RESP_USERS:
                System.out.println("Servidor: " + response[1]);
                break;
            case Protocol.RESP_MENU:
                var title = response[1];
                String[] options = Arrays.copyOfRange(response, 2, response.length);
                UI.generateMenu(title, options);
                break;
            case Protocol.RESP_PAUSE:
                console.pause(response[1]);
                break;
            case Protocol.RESP_CHOICE:
                String[] commands = getParts();
                int option = console.validateOption(response[1], commands.length - 1);
                sendReply(commands[option], null);
                break;
            case Protocol.RESP_ERROR_COMMAND:
                System.out.println("Comando No Valido");
                break;
            case Protocol.RESP_OK_EXIT:
                console.close();
                System.out.println("Cerrando Conexion con el servidor");
                return true;
            default:
                System.out.println("Respuesta del servidor no reconocida: " + response[0]);
                break;
        }

        return false;
    }

}
