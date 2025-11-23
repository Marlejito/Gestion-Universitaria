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

    private DataStore() { load(); }
    public static DataStore get() { return INSTANCE; }

    public void load() {
        if (!file.exists()) return;
        try {
            DataStore temp = mapper.readValue(file, DataStore.class);
            estudiantes = temp.estudiantes; profesores = temp.profesores;
            cursos = temp.cursos; inscripciones = temp.inscripciones;
            calificaciones = temp.calificaciones;
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void save() {
        try { mapper.writeValue(file, this); } catch (Exception e) { e.printStackTrace(); }
    }
}
