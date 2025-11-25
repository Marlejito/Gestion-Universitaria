package controllers;

import io.javalin.http.Context;
import models.Inscripcion;
import utils.DataStore;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InscripcionController {
    private static final DataStore db = DataStore.get();

    public static void getAll(Context ctx) {
        ctx.json(db.inscripciones.values());
    }

    public record InscripcionRequest(
            @JsonProperty("studentId") String estudianteId,
            @JsonProperty("courseId") String cursoId,
            @JsonProperty("semester") String semestre,
            String status) {
    }

    public static void create(Context ctx) {
        try {
            InscripcionRequest b = ctx.bodyAsClass(InscripcionRequest.class);

            Inscripcion nuevo = new Inscripcion(
                    UUID.randomUUID().toString(),
                    b.estudianteId(),
                    b.cursoId(),
                    b.semestre(),
                    b.status() != null ? b.status() : "inscrito",
                    java.time.Instant.now().toString());

            db.inscripciones.put(nuevo.id(), nuevo);
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
            InscripcionRequest b = ctx.bodyAsClass(InscripcionRequest.class);
            if (!db.inscripciones.containsKey(id))
                throw new IllegalArgumentException("Inscripción no encontrada");

            Inscripcion existing = db.inscripciones.get(id);
            Inscripcion updated = new Inscripcion(
                    id,
                    b.estudianteId(),
                    b.cursoId(),
                    b.semestre(),
                    b.status(),
                    existing.fechaInscripcion()); // Keep existing date

            db.inscripciones.put(id, updated);
            ctx.json(updated);
            db.save();
            Controlador.broadcast("UPDATE");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        }
    }

    public static void delete(Context ctx) {
        if (db.inscripciones.remove(ctx.pathParam("id")) != null) {
            db.save();
            Controlador.broadcast("UPDATE");
            ctx.status(204);
        } else {
            ctx.status(404);
        }
    }
}
