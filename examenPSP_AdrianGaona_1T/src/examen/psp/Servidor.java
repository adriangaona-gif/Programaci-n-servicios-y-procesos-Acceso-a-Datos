package examen.psp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        Canciones canciones = new Canciones();
        System.out.println("APLICACIÓN DE SERVIDOR PROVEEDORA DE CANCIONES");
        System.out.println("-------------------------------------------------");

        try {
            ServerSocket servidor = new ServerSocket();
            InetSocketAddress direccion = new InetSocketAddress("localhost", 2000);
            servidor.bind(direccion);
            System.out.println("Servidor escuchando en: " + direccion.getAddress());

            while (true) {
                Socket enchufeAlCliente = servidor.accept();
                System.out.println("Comunicación entrante desde " + enchufeAlCliente.getInetAddress());
                
                HiloEscuchador hilo = new HiloEscuchador(enchufeAlCliente, canciones);
                hilo.start(); // Inicia un hilo independiente por cliente
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
