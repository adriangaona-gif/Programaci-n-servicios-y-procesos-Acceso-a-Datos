package pack;

import java.io.*;
import java.util.Scanner;

public class Principal {
    private static final String FILE_NAME = "alumno.dat";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Alumno alumno = null;

        // Intentar cargar el archivo si existe
        File archivo = new File(FILE_NAME);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                alumno = (Alumno) ois.readObject();
                System.out.println("Alumno cargado desde archivo: " + alumno.getNombre());
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
        } else {
            // Crear nuevo alumno si no existe archivo
            System.out.print("Introduce el nombre del alumno: ");
            String nombre = sc.nextLine();
            System.out.print("Introduce la edad del alumno: ");
            int edad = sc.nextInt();
            sc.nextLine(); // limpiar buffer
            alumno = new Alumno(nombre, edad);
            System.out.println("Alumno creado correctamente.");
        }

        // Menú principal
        int opcion;
        do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Añadir nueva calificación");
            System.out.println("2. Mostrar listado de calificaciones");
            System.out.println("3. Mostrar media de calificaciones");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Introduce asignatura: ");
                    String asignatura = sc.nextLine();
                    System.out.print("Introduce nota (0-100): ");
                    int nota = sc.nextInt();
                    sc.nextLine(); // limpiar buffer
                    alumno.calificar(asignatura, nota);
                    System.out.println("Calificación añadida.");
                    break;

                case 2:
                    System.out.println("\n--- LISTADO DE CALIFICACIONES ---");
                    if (alumno.getCalificaciones().isEmpty()) {
                        System.out.println("No hay calificaciones registradas.");
                    } else {
                        for (Calificacion c : alumno.getCalificaciones()) {
                            System.out.println(c);
                        }
                    }
                    break;

                case 3:
                    if (alumno.getCalificaciones().isEmpty()) {
                        System.out.println("No hay calificaciones para calcular la media.");
                    } else {
                        double suma = 0;
                        for (Calificacion c : alumno.getCalificaciones()) {
                            suma += c.getNota();
                        }
                        double media = suma / alumno.getCalificaciones().size();
                        System.out.println("La media de calificaciones es: " + media);
                    }
                    break;

                case 4:
                    System.out.println("Saliendo y guardando datos...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 4);

        // Guardar el alumno al salir
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(alumno);
            System.out.println("Alumno guardado correctamente en " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }

        sc.close();
    }
}

