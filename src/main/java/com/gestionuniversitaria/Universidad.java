package com.gestionuniversitaria;

import java.util.ArrayList;

import com.gestionuniversitaria.model.Estudiante;
import com.gestionuniversitaria.model.Materia;

/**
 * Clase auxiliar Universidad colocada en el paquete raíz del proyecto.
 * Usa las entidades JPA en `com.gestionuniversitaria.model`.
 */
public class Universidad {
    private final String nombre;
    private ArrayList<Estudiante> estudiantes;
    private ArrayList<Materia> materias;

    // Constructor principal
    public Universidad(String nombre) {
        this.nombre = nombre;
        this.estudiantes = new ArrayList<>();
        this.materias = new ArrayList<>();
    }

    // Constructor alternativo (opcional)
    public Universidad(ArrayList<Estudiante> estudiantes, ArrayList<Materia> materias, String nombre) {
        this(nombre);
        if (estudiantes != null) {
            this.estudiantes = estudiantes;
        }
        if (materias != null) {
            this.materias = materias;
        }
    }

    public void agregarEstudiante(Estudiante est) {
        estudiantes.add(est);
    }

    public void agregarMateria(Materia mat) {
        materias.add(mat);
    }

    public ArrayList<Estudiante> getEstudiantes() { return estudiantes; }
    public ArrayList<Materia> getMaterias() { return materias; }
    public String getNombre() { return nombre; }

    public String listarEstudiantes() {
        StringBuilder sb = new StringBuilder("Listado de estudiantes:\n");
        for (Estudiante e : estudiantes) {
            sb.append(e.toString()).append("\n");
        }
        return sb.toString();
    }

    public String listarMaterias() {
        StringBuilder sb = new StringBuilder("Listado de materias:\n");
        for (Materia m : materias) {
            // Adapt to the JPA entity getters
            sb.append("Materia: ")
              .append(m.getNombre() != null ? m.getNombre() : "<sin nombre>")
              .append(", Código: ")
              .append(m.getCodigo() != null ? m.getCodigo() : "<sin código>")
              .append(", Créditos: ")
              .append(m.getCreditos())
              .append("\n");
        }
        return sb.toString();
    }

    public void setEstudiantes(ArrayList<Estudiante> estudiantes) {
        this.estudiantes = (estudiantes != null) ? estudiantes : new ArrayList<>();
    }

    public void setMaterias(ArrayList<Materia> materias) {
        this.materias = (materias != null) ? materias : new ArrayList<>();
    }
}
