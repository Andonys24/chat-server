package com.chatserver.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class RequestManager {
    private final DataInputStream in;
    private final DataOutputStream out;
    // private final FileManager fm;

    public enum ClientCommand {
        ENTRAR, ELECCION, USUARIOS, MENSAJE, TODOS, SALIR
    }

    public enum ServerResponse {
        OK_ENTRAR, ERROR_ENTRAR, ERROR_COMANDO,
        MENU, PEDIR_ELECCION, INFO_ENTRADA,
        PAUSAR,
        USUARIOS, MENSAJE, DE,
        OK_SALIR
    }

    public RequestManager(InputStream input, OutputStream output) {
        // this.fm = fileManager;
        this.in = new DataInputStream(input);
        this.out = new DataOutputStream(output);
    }

    public void close() throws IOException {
        if (in == null)
            throw new IOException("Error: El Stream de entrada (in) no esta inicializado");

        if (out == null)
            throw new IOException("Error: El Stream de entrada (out) no esta inicializado");

        in.close();
        out.close();

    }

    // Leer mensaje de la forma Header|contenido y dividirlo en un arreglo
    public String[] getParts() throws IOException {
        var header = in.readUTF();
        return header.split("\\|");
    }

    // Enviar mensaje de la forma header|contenido
    public void sendReply(final String header, final String content) throws IOException {
        if (content == null) {
            out.writeUTF(header + "|");
        } else {
            out.writeUTF(header + "|" + content);
        }
        out.flush();
    }

    public abstract boolean processResponse() throws IOException;
}
