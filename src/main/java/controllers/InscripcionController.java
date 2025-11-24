package controllers;

import io.javalin.http.Context;
import models.Inscripcion;
import utils.DataStore;
import utils.Validador;
import java.util.UUID;

public class InscripcionController {
    private static final DataStore db = DataStore.get();

    public static void getAll(Context ctx) {
        ctx.json(db.inscripciones.values());
    }

    public static void create(Context ctx) {
        try {
            Inscripcion b = ctx.bodyAsClass(Inscripcion.class);
            Validador.require(Validador.texto(b.estudianteId()) && Validador.texto(b.cursoId()), "Datos requeridos");
            boolean existe = db.inscripciones.values().stream()
                    .anyMatch(i -> i.estudianteId().equals(b.estudianteId()) && i.cursoId().equals(b.cursoId()));
            Validador.require(!existe, "El estudiante ya está inscrito en este curso");

            Inscripcion n = new Inscripcion(UUID.randomUUID().toString(), b.estudianteId(), b.cursoId());
            db.inscripciones.put(n.id(), n);
            ctx.status(201).json(n);
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
