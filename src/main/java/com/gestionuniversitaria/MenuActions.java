
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

    // Extra helpers for simple JSON parsing and safe truncation
    private static String extractJsonField(String jsonObj, String fieldName) {
        try {
            String key = '"' + fieldName + '"' + ':';
            int idx = jsonObj.indexOf(key);
            if (idx == -1) return "";
            int start = idx + key.length();
            // skip whitespace
            while (start < jsonObj.length() && Character.isWhitespace(jsonObj.charAt(start))) start++;
            char c = jsonObj.charAt(start);
            if (c == '"') {
                start++;
                int end = jsonObj.indexOf('"', start);
                if (end == -1) end = jsonObj.length();
                return jsonObj.substring(start, end);
            } else {
                // number or boolean
                int end = start;
                while (end < jsonObj.length() && ",} ".indexOf(jsonObj.charAt(end))==-1) end++;
                return jsonObj.substring(start, end).trim();
            }
        } catch (Exception e) {
            return "";
        }
    }

    private static String safeTruncate(String s, int len) {
        if (s == null) return "";
        String out = s.replaceAll("\n", " ").replaceAll("\r", " ");
        if (out.length() <= len) return out;
        return out.substring(0, len-3) + "...";
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
                    System.out.println("\n--- Estudiantes registrados ---\n");
                    if (json.isBlank() || json.equals("[]")) {
                        System.out.println("No hay estudiantes registrados.");
                    } else {
                        // Parseo muy simple del JSON para extraer campos clave sin dependencias
                        String compact = json.trim();
                        compact = compact.substring(1, compact.length() - 1); // quitar [ ]
                        String[] items = compact.split("},\\s*\\{");

                        // Encabezado de tabla
                        String fmt = "%-10s | %-15s | %-15s | %-25s | %-15s | %-8s | %-8s";
                        System.out.println(String.format(fmt, "Código", "Nombres", "Apellidos", "Email", "Carrera", "Promedio", "Semestre"));
                        System.out.println(new String(new char[110]).replace('\0','-'));

                        for (String item : items) {
                            String obj = item;
                            if (!obj.startsWith("{")) obj = "{" + obj;
                            if (!obj.endsWith("}")) obj = obj + "}";

                            String codigo = extractJsonField(obj, "codigo");
                            String nombres = extractJsonField(obj, "nombres");
                            String apellidos = extractJsonField(obj, "apellidos");
                            String email = extractJsonField(obj, "email");
                            String carrera = extractJsonField(obj, "carrera");
                            String promedio = extractJsonField(obj, "promedioAcumulado");
                            String semestre = extractJsonField(obj, "semestreActual");

                            System.out.println(String.format(fmt,
                                safeTruncate(codigo,10), safeTruncate(nombres,15), safeTruncate(apellidos,15), safeTruncate(email,25), safeTruncate(carrera,15), safeTruncate(promedio,8), safeTruncate(semestre,8)));
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
