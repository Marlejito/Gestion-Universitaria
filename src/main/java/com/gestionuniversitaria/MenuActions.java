
// Clase que contiene las acciones del menú de consola.
// Permite registrar estudiantes y materias enviando datos al backend REST.
package com.gestionuniversitaria;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MenuActions {
    /**
     * Solicita los datos del estudiante por consola y los envía al backend para registrar un nuevo estudiante.
     */
    public static void registrarEstudiante(Scanner scanner) {
        try {
            System.out.print("Nombres del estudiante: ");
            String nombres = scanner.nextLine();
            System.out.print("Apellidos: ");
            String apellidos = scanner.nextLine();
            System.out.print("Correo: ");
            String email = scanner.nextLine();
            System.out.print("Celular: ");
            String celular = scanner.nextLine();
            System.out.print("Código de estudiante: ");
            String codigo = scanner.nextLine();
            System.out.print("Carrera: ");
            String carrera = scanner.nextLine();
            System.out.print("Promedio acumulado: ");
            String promedio = scanner.nextLine();
            System.out.print("Semestre actual: ");
            String semestre = scanner.nextLine();

            String json = String.format("{\"nombres\":\"%s\",\"apellidos\":\"%s\",\"email\":\"%s\",\"celular\":\"%s\",\"codigo\":\"%s\",\"carrera\":\"%s\",\"promedioAcumulado\":%s,\"semestreActual\":%s}",
                nombres, apellidos, email, celular, codigo, carrera, promedio, semestre);

            URI uri = URI.create("http://localhost:8080/api/estudiantes");
            URL url = uri.toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            int code = con.getResponseCode();
            if (code == 200 || code == 201) {
                System.out.println("Estudiante registrado correctamente.");
            } else {
                System.out.println("Error al registrar estudiante. Código: " + code);
            }
        } catch (java.io.IOException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Obtiene la lista de estudiantes desde el backend y la muestra en la consola.
     */
    public static void listarEstudiantes(Scanner scanner) {
        try {
            URI uri = URI.create("http://localhost:8080/api/estudiantes");
            URL url = uri.toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            int code = con.getResponseCode();
            if (code == 200) {
                try (java.io.InputStream is = con.getInputStream();
                     java.util.Scanner s = new java.util.Scanner(is, StandardCharsets.UTF_8.name())) {
                    String json = s.useDelimiter("\\A").hasNext() ? s.next() : "";
                    // Simple printing: show raw JSON or try a basic formatting
                    System.out.println("\n--- Estudiantes registrados ---");
                    if (json.isBlank() || json.equals("[]")) {
                        System.out.println("No hay estudiantes registrados.");
                    } else {
                        // Intento de formateo muy básico: separar objetos por '},{' para líneas
                        String compact = json.trim();
                        compact = compact.substring(1, compact.length() - 1); // quitar [ ]
                        String[] items = compact.split("},\\s*\\{");
                        for (String item : items) {
                            String obj = item;
                            if (!obj.startsWith("{")) obj = "{" + obj;
                            if (!obj.endsWith("}")) obj = obj + "}";
                            System.out.println(obj);
                            System.out.println("-----------------------------");
                        }
                    }
                }
            } else {
                System.out.println("Error al obtener estudiantes. Código: " + code);
            }
        } catch (java.io.IOException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }

    /**
     * Solicita los datos de la materia por consola y los envía al backend para registrar una nueva materia.
     */
    public static void registrarMateria(Scanner scanner) {
        try {
            System.out.print("Nombre de la materia: ");
            String nombre = scanner.nextLine();
            System.out.print("Créditos: ");
            String creditos = scanner.nextLine();
            System.out.print("Código de materia: ");
            String codigo = scanner.nextLine();

            String json = String.format("{\"nombre\":\"%s\",\"creditos\":%s,\"codigo\":\"%s\"}",
                nombre, creditos, codigo);

            URI uri = URI.create("http://localhost:8080/api/materias");
            URL url = uri.toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            int code = con.getResponseCode();
            if (code == 200 || code == 201) {
                System.out.println("Materia registrada correctamente.");
            } else {
                System.out.println("Error al registrar materia. Código: " + code);
            }
        } catch (java.io.IOException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
