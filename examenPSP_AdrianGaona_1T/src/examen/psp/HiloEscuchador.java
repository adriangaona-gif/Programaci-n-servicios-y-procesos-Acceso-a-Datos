package examen.psp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class HiloEscuchador extends Thread {

    private Socket enchufeAlCliente;
    private Canciones canciones;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;

    public HiloEscuchador(Socket cliente, Canciones canciones) {
        this.enchufeAlCliente = cliente;
        this.canciones = canciones;
    }

    @Override
    public void run() {
        System.out.println("Estableciendo comunicación con " + Thread.currentThread().getName());
        try {
            salida = new ObjectOutputStream(enchufeAlCliente.getOutputStream());
            entrada = new ObjectInputStream(enchufeAlCliente.getInputStream());
            String peticion = "";

            do {
                peticion = (String) entrada.readObject();

                switch (peticion) {
                    case "1": // Una canción al azar
                        salida.writeObject(canciones.cancionAzar().toString());
                        break;

                    case "2": // Canciones de un grupo
                        salida.writeObject("Escribe el nombre del grupo:");
                        String grupo = (String) entrada.readObject();
                        ArrayList<Cancion> cancionesGrupo = canciones.getCancionesGrupo(grupo);
                        ArrayList<String> cancionesGrupoStr = new ArrayList<>();
                        for (Cancion c : cancionesGrupo) {
                            cancionesGrupoStr.add(c.toString());
                        }
                        salida.writeObject(cancionesGrupoStr);
                        break;

                    case "3": // Canciones por título
                        salida.writeObject("Escribe el título a buscar:");
                        String titulo = (String) entrada.readObject();
                        ArrayList<Cancion> cancionesTitulo = canciones.getCancionesTitulo(titulo);
                        ArrayList<String> cancionesTituloStr = new ArrayList<>();
                        for (Cancion c : cancionesTitulo) {
                            cancionesTituloStr.add(c.toString());
                        }
                        salida.writeObject(cancionesTituloStr);
                        break;

                    case "4": // Todas las canciones
                        ArrayList<Cancion> todas = canciones.getListaDistribucion();
                        ArrayList<String> todasStr = new ArrayList<>();
                        for (Cancion c : todas) {
                            todasStr.add(c.toString());
                        }
                        salida.writeObject(todasStr);
                        break;

                    case "5": // Desconectar
                    case "FIN":
                        salida.writeObject("Hasta pronto, gracias por establecer conexión");
                        System.out.println(Thread.currentThread().getName() + " ha cerrado la comunicación");
                        break;

                    default:
                        salida.writeObject("Opción no válida");
                }

            } while (!peticion.equals("5") && !peticion.equals("FIN"));

            entrada.close();
            salida.close();
            enchufeAlCliente.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error en hilo " + Thread.currentThread().getName() + ": " + e.getMessage());
        }
    }
}
