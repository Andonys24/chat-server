package com.chatserver.Client;

import java.util.Scanner;

public class Console {
    private Scanner input;

    public Console() {
        input = new Scanner(System.in);
    }

    public void pause(String message) {
        if (message == null)
            message = "continuar";

        System.out.print("\nPresione una tecla para " + message + "... ");
        input.nextLine();
    }

    public int validateInt(final String mensaje) {
        int num = 0;
        boolean validado = false;

        while (!validado) {
            System.out.print(mensaje + ": ");
            String entrada = input.nextLine();
            try {
                num = Integer.parseInt(entrada.trim());
                validado = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: '" + entrada + "' no es un número entero válido.");
            }
        }

        return num;
    }

    public int validateOption(final String mensaje, final int max) {
        while (true) {
            int option = validateInt(mensaje);

            if (option < 1) {
                System.err.println("La opcion no puede ser menor que 1.");
                continue;
            }

            if (option > max) {
                System.err.println("La opcion no puede ser mayor que " + max);
                continue;
            }

            return option;
        }
    }

    public void close() {
        if (input != null) {
            input.close();
        }
    }
}
