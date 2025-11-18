package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para la gestión de la conexión JDBC con la base de datos MySQL.
 * 
 * Proporciona un método estático para obtener la conexión y simplifica
 * el acceso a la base de datos desde la capa DAO.
 */
public class DBConnection {
    // URL de conexión JDBC a la base de datos "exam_manager"
    // Ajusta el host, puerto, nombre de la base de datos y zona horaria según tu entorno
    private static final String URL = "jdbc:mysql://localhost:3306/exam_manager?useSSL=false&serverTimezone=UTC";

    // Usuario y contraseña de MySQL (ajustar según la instalación)
    private static final String USER = "root";
    private static final String PASS = "curso";

    static {
        try {
            // Cargar el driver JDBC de MySQL
            // No estrictamente necesario en versiones recientes de JDBC,
            // pero se incluye para mayor claridad y compatibilidad.
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC no encontrado: " + e.getMessage());
        }
    }

    /**
     * Devuelve una conexión a la base de datos MySQL.
     * 
     * @return Connection activa
     * @throws SQLException si ocurre un error al conectar
     */
    public static Connection getConnection() throws SQLException {
        // Obtiene y devuelve la conexión usando DriverManager
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
