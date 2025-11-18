package org.example.service;

import org.example.model.Venta;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    private final String URL = "jdbc:mysql://localhost:3306/VENTAS";
    private final String USER = "root";
    private final String PASS = "curso";

    // Insertar venta en la base de datos
    public void insertarVenta(Venta v) {
        String sql = "INSERT INTO VENTA (ID_VENTA, PRODUCTO, CANTIDAD, PRECIO) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, v.getIdVenta());
            ps.setString(2, v.getProducto());
            ps.setInt(3, v.getCantidad());
            ps.setDouble(4, v.getPrecio());
            ps.executeUpdate();
            System.out.println("Venta insertada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Listar todas las ventas
    public List<Venta> listarVentas() {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM VENTA";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                ventas.add(new Venta(
                        rs.getInt("ID_VENTA"),
                        rs.getString("PRODUCTO"),
                        rs.getInt("CANTIDAD"),
                        rs.getDouble("PRECIO")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventas;
    }

    // Listar ventas por producto
    public List<Venta> listarPorProducto(String nombreProducto) {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM VENTA WHERE PRODUCTO = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombreProducto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ventas.add(new Venta(
                            rs.getInt("ID_VENTA"),
                            rs.getString("PRODUCTO"),
                            rs.getInt("CANTIDAD"),
                            rs.getDouble("PRECIO")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventas;
    }
}
