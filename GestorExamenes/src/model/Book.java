package model;

/**
 * Clase que representa un libro en la base de datos del Gestor de Exámenes.
 * Contiene información básica como título, autor, género, año de publicación,
 * stock disponible y descripción.
 */
public class Book {
    private int id;             // Identificador único del libro
    private String title;       // Título del libro
    private String author;      // Autor del libro
    private String genre;       // Género o categoría del libro
    private int yearPub;        // Año de publicación
    private int stock;          // Número de ejemplares disponibles
    private String description; // Descripción breve del libro

    // Constructor vacío (necesario para frameworks o inicialización sin datos)
    public Book() {}

    // Constructor completo con todos los atributos
    public Book(int id, String title, String author, String genre, int yearPub, int stock, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.yearPub = yearPub;
        this.stock = stock;
        this.description = description;
    }

    // -------------------- Getters y setters --------------------

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getYearPub() { return yearPub; }
    public void setYearPub(int yearPub) { this.yearPub = yearPub; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // -------------------- Métodos adicionales --------------------

    /**
     * Convierte el libro en una representación CSV para enviar por socket.
     * 
     * Se reemplazan las comas por punto y coma en los campos para evitar problemas
     * de parsing cuando se envían al cliente.
     * 
     * Formato devuelto:
     * id,title,author,genre,yearPub,stock,description
     */
    public String toCsv() {
        String safeTitle = title == null ? "" : title.replace(",", ";");
        String safeAuthor = author == null ? "" : author.replace(",", ";");
        String safeGenre = genre == null ? "" : genre.replace(",", ";");
        String safeDesc = description == null ? "" : description.replace(",", ";");
        return id + "," + safeTitle + "," + safeAuthor + "," + safeGenre + "," + yearPub + "," + stock + "," + safeDesc;
    }
}
