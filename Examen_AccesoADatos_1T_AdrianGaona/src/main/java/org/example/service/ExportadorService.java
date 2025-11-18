package org.example.service;

import org.example.model.Venta;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class ExportadorService {

    private final VentaService ventaService;

    public ExportadorService(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    public void exportar() {
        List<Venta> ventas = ventaService.listarVentas();
        double totalRecaudado = 0;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Ventas.txt"))) {
            for (Venta v : ventas) {
                double importe = v.getCantidad() * v.getPrecio();
                totalRecaudado += importe;
                bw.write(v + ", Importe=" + importe);
                bw.newLine();
            }
            bw.write("Recaudación total: " + totalRecaudado);
            System.out.println("Exportación completada en Ventas.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
