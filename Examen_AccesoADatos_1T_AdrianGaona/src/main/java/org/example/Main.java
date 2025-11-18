package org.example;

import org.example.model.Venta;
import org.example.service.VentaService;
import org.example.service.ExportadorService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        VentaService ventaService = context.getBean(VentaService.class);
        ExportadorService exportadorService = context.getBean(ExportadorService.class);

        // Mostrar todas las ventas
        System.out.println("Todas las ventas:");
        List<Venta> ventas = ventaService.listarVentas();
        ventas.forEach(System.out::println);

        // Insertar una nueva venta con un ID que no exista aún
        Venta nuevaVenta = new Venta(16, "Gorra negra", 2, 15.00);
        ventaService.insertarVenta(nuevaVenta);

        // Mostrar ventas de un producto específico
        System.out.println("\nVentas del producto 'Zapatillas negras':");
        List<Venta> ventasProducto = ventaService.listarPorProducto("Zapatillas negras");
        ventasProducto.forEach(System.out::println);

        // Exportar ventas a fichero
        exportadorService.exportar();
    }
}
