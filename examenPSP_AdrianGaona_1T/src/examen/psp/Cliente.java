package examen.psp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {
    private static ObjectInputStream entrada;
    private static ObjectOutputStream salida;

    public static void main(String[] args) {
        System.out.println("APLICACIÓN CLIENTE");
        System.out.println("-----------------------------------");
        Scanner lector = new Scanner(System.in);

        try {
            Socket cliente = new Socket();
            InetSocketAddress direccionServidor = new InetSocketAddress("localhost", 2000);
            System.out.println("Esperando a que el servidor acepte la conexión...");
            cliente.connect(direccionServidor);
            System.out.println("Comunicación establecida con el servidor");

            salida = new ObjectOutputStream(cliente.getOutputStream());
            entrada = new ObjectInputStream(cliente.getInputStream());

            String opcion = "";

            while (!opcion.equals("5")) {
                System.out.println("1. Una sola canción");
                System.out.println("2. Canciones de un grupo");
                System.out.println("3. Canciones por título");
                System.out.println("4. Todas las canciones de la lista");
                System.out.println("5. Desconectar");
                System.out.print("Elige opción: ");
                opcion = lector.nextLine();

                salida.writeObject(opcion);

                switch (opcion) {
                    case "1":
                        // Recibe una canción como String
                        System.out.println(entrada.readObject());
                        break;

                    case "2":
                    case "3":
                        // Recibe mensaje de "Escribe nombre del grupo/título"
                        System.out.println(entrada.readObject());
                        String dato = lector.nextLine();
                        salida.writeObject(dato);

                        // Recibe lista de canciones como Strings
                        Object respuestaLista = entrada.readObject();
                        if (respuestaLista instanceof ArrayList) {
                            ArrayList<?> lista = (ArrayList<?>) respuestaLista;
                            if (lista.isEmpty()) {
                                System.out.println("No se encontraron canciones.");
                            } else {
                                lista.forEach(System.out::println);
                            }
                        }
                        break;

                    case "4":
                        Object todas = entrada.readObject();
                        if (todas instanceof ArrayList) {
                            ArrayList<?> lista = (ArrayList<?>) todas;
                            lista.forEach(System.out::println);
                        }
                        break;

                    case "5":
                        System.out.println(entrada.readObject());
                        break;

                    default:
                        System.out.println("Opción no válida");
                }
            }

            entrada.close();
            salida.close();
            cliente.close();
            System.out.println("Comunicación cerrada");

        } catch (UnknownHostException e) {
            System.out.println("No se puede establecer comunicación con el servidor");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de E/S");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error de clase");
            System.out.println(e.getMessage());
        }

        lector.close();
    }
}
