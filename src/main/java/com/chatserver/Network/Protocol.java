package com.chatserver.Network;

public class Protocol {
    // Comandos del Cliente
    public static final String CMD_ENTER = "ENTRAR";
    public static final String CMD_MESSAGE = "MENSAJE";
    public static final String CMD_ALL = "TODOS";
    public static final String CMD_USERS = "USUARIOS";
    public static final String CMD_EXIT = "SALIR";

    // Respuestas del Servidor
    public static final String RESP_OK_ENTER = "OK_ENTRAR";
    public static final String RESP_ERROR_ENTER = "ERROR_ENTRAR";
    public static final String RESP_MENU = "MENU";
    public static final String RESP_CHOICE = "PEDIR_ELECCION";
    public static final String RESP_ERROR_COMMAND = "ERROR_COMANDO";
    public static final String RESP_PAUSE = "PAUSAR";
    public static final String RESP_DE = "DE";
    public static final String RESP_INFO_ENTER = "INFO_ENTRA";
    public static final String RESP_INFO_SALE = "INFO_SALE";
    public static final String RESP_USERS = "USUARIOS";
    public static final String RESP_OK_EXIT = "OK_SALIR";
}
