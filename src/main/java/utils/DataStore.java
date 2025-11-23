package utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.*;
import models.*;

public class DataStore {
    private static final DataStore INSTANCE = new DataStore();
    private final ObjectMapper mapper = new ObjectMapper();
    private final File file = new File("data.json");
    
    public List<Estudiante> estudiantes = new ArrayList<>();
    public List<Profesor> profesores = new ArrayList<>();
    public List<Curso> cursos = new ArrayList<>();
    public List<Inscripcion> inscripciones = new ArrayList<>();
    public List<Calificacion> calificaciones = new ArrayList<>();
    public List<Usuario> usuarios = new ArrayList<>();

    private DataStore() { load(); seed(); }
    public static DataStore get() { return INSTANCE; }

    private void seed() {
        if(usuarios.isEmpty()) {
            usuarios.add(new Usuario("admin", "admin123", "ADMIN"));
            save();
        }
    }

    public void load() {
        if (!file.exists()) return;
        try {
            DataStore temp = mapper.readValue(file, DataStore.class);
            estudiantes = temp.estudiantes != null ? temp.estudiantes : new ArrayList<>();
            profesores = temp.profesores != null ? temp.profesores : new ArrayList<>();
            cursos = temp.cursos != null ? temp.cursos : new ArrayList<>();
            inscripciones = temp.inscripciones != null ? temp.inscripciones : new ArrayList<>();
            calificaciones = temp.calificaciones != null ? temp.calificaciones : new ArrayList<>();
            usuarios = temp.usuarios != null ? temp.usuarios : new ArrayList<>();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void save() {
        try { mapper.writeValue(file, this); } catch (Exception e) { e.printStackTrace(); }
    }
}
