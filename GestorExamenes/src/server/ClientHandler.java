package server;

import dao.BookDAO;
import model.Book;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Clase que maneja la comunicación con un cliente conectado al servidor.
 * 
 * Cada cliente se atiende en un hilo independiente (implementa Runnable),
 * permitiendo que varios usuarios se conecten simultáneamente.
 */
public class ClientHandler implements Runnable {
    private Socket clientSocket;  // Conexión con el cliente
    private BookDAO bookDao;      // Objeto para realizar búsquedas en los datos

    /**
     * Constructor: inicializa el socket del cliente y el acceso a datos.
     */
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        this.bookDao = new BookDAO();
    }

    /**
     * Método principal del hilo. Gestiona las peticiones del cliente.
     */
    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            String line;
            // Bucle que escucha comandos del cliente mientras la conexión esté activa
            while ((line = in.readLine()) != null) {
                line = line.trim();

                // Comando para cerrar la conexión
                if (line.equalsIgnoreCase("QUIT") || line.equalsIgnoreCase("EXIT")) {
                    out.write("BYE\n");
                    out.flush();
                    break;
                }

                // Si el comando comienza con "SEARCH:", procesar búsqueda
                if (line.startsWith("SEARCH:")) {
                    handleSearch(line, out);
                } else {
                    // Si el comando no es reconocido, enviar mensaje de error
                    out.write("ERROR:Comando desconocido\n");
                    out.write("END\n");
                    out.flush();
                }
            }

        } catch (IOException e) {
            System.err.println("Cliente desconectado abruptamente: " + e.getMessage());
        } finally {
            // Cierra el socket al finalizar la comunicación
            try {
                clientSocket.close();
            } catch (IOException ignored) {}
        }
    }

    /**
     * Procesa las búsquedas enviadas por el cliente en formato:
     * SEARCH:campo:termino
     */
    private void handleSearch(String line, BufferedWriter out) throws IOException {
        try {
            // Divide el comando en partes: SEARCH, campo, término
            String[] parts = line.split(":", 3);
            if (parts.length < 3) {
                out.write("ERROR:Formato inválido. Uso SEARCH:field:term\n");
                out.write("END\n");
                out.flush();
                return;
            }

            String field = parts[1].toLowerCase(); // Campo de búsqueda (title, author, genre)
            String term = parts[2];                // Término a buscar

            List<Book> results;

            // Selecciona el tipo de búsqueda según el campo recibido
            switch (field) {
                case "title":
                    results = bookDao.findByTitle(term);
                    break;
                case "author":
                    results = bookDao.findByAuthor(term);
                    break;
                case "genre":
                    results = bookDao.findByGenre(term);
                    break;
                default:
                    out.write("ERROR:Campo de búsqueda no soportado. Usa title, author o genre\n");
                    out.write("END\n");
                    out.flush();
                    return;
            }

            // Enviar los resultados al cliente en formato CSV (una línea por libro)
            if (results.isEmpty()) {
                out.write("INFO:No se encontraron resultados\n");
                out.write("END\n");
                out.flush();
                return;
            }

            for (Book b : results) {
                out.write(b.toCsv() + "\n");
            }

            // Marca el fin del mensaje
            out.write("END\n");
            out.flush();

        } catch (Exception e) {
            // Maneja errores internos y notifica al cliente
            out.write("ERROR:Excepción en servidor: " + e.getMessage() + "\n");
            out.write("END\n");
            out.flush();
        }
    }
}
