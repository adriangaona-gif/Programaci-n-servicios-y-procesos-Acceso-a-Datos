package dao;

import db.DBConnection;
import model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones de consulta sobre libros.
 * Solo operaciones de lectura (es suficiente para el enunciado).
 */
public class BookDAO {

    /**
     * Busca libros por título (LIKE %term%).
     */
    public List<Book> findByTitle(String term) {
        String sql = "SELECT * FROM books WHERE title LIKE ?";
        return executeSearch(sql, "%" + term + "%");
    }

    /**
     * Busca libros por autor.
     */
    public List<Book> findByAuthor(String author) {
        String sql = "SELECT * FROM books WHERE author LIKE ?";
        return executeSearch(sql, "%" + author + "%");
    }

    /**
     * Busca libros por género.
     */
    public List<Book> findByGenre(String genre) {
        String sql = "SELECT * FROM books WHERE genre LIKE ?";
        return executeSearch(sql, "%" + genre + "%");
    }

    /**
     * Método helper que ejecuta la consulta y construye la lista de Book.
     */
    private List<Book> executeSearch(String sql, String param) {
        List<Book> results = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getInt("year_pub"),
                        rs.getInt("stock"),
                        rs.getString("description")
                    );
                    results.add(book);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error DAO: " + e.getMessage());
            // En producción registrar correctamente (log).
        }
        return results;
    }
}

