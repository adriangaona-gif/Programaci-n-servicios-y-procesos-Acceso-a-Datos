package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor principal del proyecto "Gestor de Exámenes".
 * 
 * Escucha peticiones en un puerto determinado y crea un hilo independiente
 * (ClientHandler) para atender a cada cliente que se conecta.
 */
public class Server {
    public static final int PORT = 5050; // Puerto donde el servidor escuchará las conexiones
    private boolean running = true; // Controla si el servidor sigue activo

    /**
     * Inicia el servidor y gestiona las conexiones entrantes.
     */
    public void startServer() {
        System.out.println("Iniciando servidor en puerto " + PORT + " ...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) { // Crea el socket del servidor
            System.out.println("Servidor iniciado. Esperando clientes...\n");

            // Bucle principal que acepta conexiones de clientes
            while (running) {
                // Espera hasta que un cliente se conecte
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());

                // Crear un manejador (hilo) para atender al cliente de forma independiente
                ClientHandler handler = new ClientHandler(clientSocket);
                Thread t = new Thread(handler); // Cada cliente se ejecuta en su propio hilo
                t.start(); // Inicia el hilo de comunicación con ese cliente
            }

        } catch (IOException e) {
            // Captura cualquier error en la creación del socket o la conexión
            System.err.println("Error en servidor: " + e.getMessage());
        }
    }

    /**
     * Método principal. Punto de entrada de la aplicación del servidor.
     */
    public static void main(String[] args) {
        Server server = new Server(); // Crea una instancia del servidor
        server.startServer(); // Llama al método que inicia la escucha
    }
}
