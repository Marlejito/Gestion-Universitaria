package controllers;

import io.javalin.http.Context;
import models.Curso;
import utils.DataStore;
import utils.Validador;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CursoController {
    private static final DataStore db = DataStore.get();

    public static void getAll(Context ctx) {
        ctx.json(db.cursos.values());
    }

    public record CursoRequest(
            @JsonProperty("courseCode") String codigoCurso,
            @JsonProperty("name") String nombre,
            @JsonProperty("description") String descripcion,
            @JsonProperty("department") String departamento,
            @JsonProperty("credits") int creditos,
            @JsonProperty("capacity") int capacidad,
            @JsonProperty("professorId") String profesorId,
            @JsonProperty("semester") String semestre,
            @JsonProperty("schedule") String horario,
            @JsonProperty("room") String salon,
            @JsonProperty("prerequisites") List<String> prerequisitos,
            String status) {
    }

    public static void create(Context ctx) {
        try {
            CursoRequest b = ctx.bodyAsClass(CursoRequest.class);
            Validador.require(Validador.texto(b.nombre()), "Nombre requerido");

            Curso nuevo = new Curso(
                    UUID.randomUUID().toString(),
                    b.codigoCurso(),
                    b.nombre(),
                    b.descripcion(),
                    b.departamento(),
                    b.creditos(),
                    b.capacidad(),
                    b.profesorId(),
                    b.semestre(),
                    b.horario(),
                    b.salon(),
                    b.prerequisitos() != null ? b.prerequisitos() : new ArrayList<>(),
                    b.status() != null ? b.status() : "activo");

            db.cursos.put(nuevo.id(), nuevo);
            ctx.status(201).json(nuevo);
            db.save();
            Controlador.broadcast("UPDATE");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        }
    }

    public static void update(Context ctx) {
        try {
            String id = ctx.pathParam("id");
            CursoRequest b = ctx.bodyAsClass(CursoRequest.class);
            if (!db.cursos.containsKey(id))
                throw new IllegalArgumentException("Curso no encontrado");

            Curso updated = new Curso(
                    id,
                    b.codigoCurso(),
                    b.nombre(),
                    b.descripcion(),
                    b.departamento(),
                    b.creditos(),
                    b.capacidad(),
                    b.profesorId(),
                    b.semestre(),
                    b.horario(),
                    b.salon(),
                    b.prerequisitos(),
                    b.status());

            db.cursos.put(id, updated);
            ctx.json(updated);
            db.save();
            Controlador.broadcast("UPDATE");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        }
    }

    public static void delete(Context ctx) {
        if (db.cursos.remove(ctx.pathParam("id")) != null) {
            db.save();
            Controlador.broadcast("UPDATE");
            ctx.status(204);
        } else {
            ctx.status(404);
        }
    }
}
