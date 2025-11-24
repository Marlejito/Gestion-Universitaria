package controllers;

import io.javalin.http.Context;
import models.Inscripcion;
import utils.DataStore;
import java.util.UUID;

public class InscripcionController {
    private static final DataStore db = DataStore.get();

    public static void getAll(Context ctx) {
        ctx.json(db.inscripciones.values());
    }

    public static void create(Context ctx) {
        try {
            Inscripcion b = ctx.bodyAsClass(Inscripcion.class);

            Inscripcion nuevo = new Inscripcion(
                    UUID.randomUUID().toString(),
                    b.estudianteId(),
                    b.cursoId(),
                    b.semestre(),
                    b.status() != null ? b.status() : "inscrito");

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
            Inscripcion b = ctx.bodyAsClass(Inscripcion.class);
            if (!db.inscripciones.containsKey(id))
                throw new IllegalArgumentException("Inscripción no encontrada");

            Inscripcion updated = new Inscripcion(
                    id,
                    b.estudianteId(),
                    b.cursoId(),
                    b.semestre(),
                    b.status());

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
