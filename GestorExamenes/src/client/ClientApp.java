package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Cliente simple por consola. Conecta al servidor, envía búsquedas y muestra respuestas.
 *
 * Uso:
 * - Escribe: SEARCH:title:Solo Leveling
 * - Escribe: SEARCH:author:Koyoharu Gotouge
 * - Escribe: SEARCH:genre:Seinen
 * - Escribe: QUIT o EXIT para desconectar
 */
public class ClientApp {
    private static final String HOST = "localhost"; // Dirección del servidor 
    private static final int PORT = 5050; // Puerto de conexión 

    public static void main(String[] args) {
        System.out.println("Cliente de Gestor de Exámenes - Conectando a " + HOST + ":" + PORT);
        try (
            // Crear socket y flujos de entrada/salida
            Socket socket = new Socket(HOST, PORT);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in)
        ) {

            System.out.println("Conectado. Escribe comandos (SEARCH:title:term) o QUIT para salir.");
            
            // Bucle principal de comunicación con el servidor
            while (true) {
                System.out.print("> ");
                String userInput = scanner.nextLine(); // Leer entrada del usuario

                if (userInput == null) continue;
                userInput = userInput.trim(); // Eliminar espacios sobrantes
                if (userInput.isEmpty()) continue; // Ignorar entradas vacías

                // Enviar comando al servidor seguido de salto de línea
                out.write(userInput + "\n");
                out.flush(); // Asegura que los datos se envíen inmediatamente

                // Leer la respuesta del servidor línea a línea
                String respLine;
                while ((respLine = in.readLine()) != null) {
                    if (respLine.equals("END")) break; // Fin de la respuesta
                    System.out.println(respLine); // Mostrar la línea recibida
                    if (respLine.equals("BYE")) break; // Mensaje de cierre
                }

                // Si el usuario escribe "QUIT" o "EXIT", cerrar conexión
                if (userInput.equalsIgnoreCase("QUIT") || userInput.equalsIgnoreCase("EXIT")) {
                    System.out.println("Desconectando...");
                    break;
                }
            }

        } catch (IOException e) {
            // Captura errores de conexión o comunicación
            System.err.println("Error en cliente: " + e.getMessage());
        }
    }
}
