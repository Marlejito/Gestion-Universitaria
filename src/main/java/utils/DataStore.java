package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import models.*;

public class DataStore {
    private static final DataStore INSTANCE = new DataStore();
    private final ObjectMapper mapper = new ObjectMapper();
    private final File file = new File("data.json");

    public Map<String, Estudiante> estudiantes = new ConcurrentHashMap<>();
    public Map<String, Profesor> profesores = new ConcurrentHashMap<>();
    public Map<String, Curso> cursos = new ConcurrentHashMap<>();
    public Map<String, Inscripcion> inscripciones = new ConcurrentHashMap<>();
    public Map<String, Calificacion> calificaciones = new ConcurrentHashMap<>();
    public Map<String, Usuario> usuarios = new ConcurrentHashMap<>();

    private DataStore() {
        load();
        seed();
    }

    public static DataStore get() {
        return INSTANCE;
    }

    private void seed() {
        if (usuarios.isEmpty()) {
            usuarios.put("admin", new Usuario("admin", "admin123", "ADMIN"));
            save();
        }
    }

    public void load() {
        if (!file.exists())
            return;
        try {
            // Cargar como estructura temporal para manejar el formato JSON
            Map<String, Object> temp = mapper.readValue(file, new TypeReference<Map<String, Object>>() {
            });

            // Ayudante para convertir lista a mapa
            loadMap(temp, "estudiantes", Estudiante.class, estudiantes);
            loadMap(temp, "profesores", Profesor.class, profesores);
            loadMap(temp, "cursos", Curso.class, cursos);
            loadMap(temp, "inscripciones", Inscripcion.class, inscripciones);
            loadMap(temp, "calificaciones", Calificacion.class, calificaciones);
            loadMap(temp, "usuarios", Usuario.class, usuarios);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <T> void loadMap(Map<String, Object> source, String key, Class<T> clazz, Map<String, T> target) {
        if (source.containsKey(key)) {
            List<T> list = mapper.convertValue(source.get(key),
                    mapper.getTypeFactory().constructCollectionType(List.class, clazz));
            for (T item : list) {
                try {
                    // Asumimos que todos los modelos tienen un método id() o identificador único.
                    // Para Usuario es username, para otros es id.
                    String id = getId(item);
                    target.put(id, item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getId(Object item) {
        if (item instanceof Usuario)
            return ((Usuario) item).username();
        if (item instanceof Estudiante)
            return ((Estudiante) item).id();
        if (item instanceof Profesor)
            return ((Profesor) item).id();
        if (item instanceof Curso)
            return ((Curso) item).id();
        if (item instanceof Inscripcion)
            return ((Inscripcion) item).id();
        if (item instanceof Calificacion)
            return ((Calificacion) item).id();
        return UUID.randomUUID().toString();
    }

    public void save() {
        try {
            // Guardar como listas para mantener compatibilidad JSON
            Map<String, Object> data = new HashMap<>();
            data.put("estudiantes", estudiantes.values());
            data.put("profesores", profesores.values());
            data.put("cursos", cursos.values());
            data.put("inscripciones", inscripciones.values());
            data.put("calificaciones", calificaciones.values());
            data.put("usuarios", usuarios.values());

            mapper.writeValue(file, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
