package com.gestionuniversitaria;

import java.util.Scanner;

// Clase principal que muestra el menú de la aplicación de consola.
public class ConsoleMenu {
    /**
     * Método principal: muestra el menú y gestiona la interacción con el usuario.
     * Permite registrar estudiantes, materias o salir.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Menú de Gestión Universitaria ---");
            System.out.println("1. Registrar estudiante");
            System.out.println("2. Registrar materia");
            System.out.println("3. Listar estudiantes");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1" -> MenuActions.registrarEstudiante(scanner);
                case "2" -> MenuActions.registrarMateria(scanner);
                case "3" -> MenuActions.listarEstudiantes(scanner);
                case "4" -> {
                    System.out.println("Saliendo...");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }
}
