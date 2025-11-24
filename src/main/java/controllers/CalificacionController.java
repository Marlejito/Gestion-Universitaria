package controllers;

import io.javalin.http.Context;
import models.Calificacion;
import utils.DataStore;
import utils.Validador;
import java.util.UUID;

public class CalificacionController {
    private static final DataStore db = DataStore.get();

    public static void getAll(Context ctx) {
        ctx.json(db.calificaciones.values());
    }

    public static void create(Context ctx) {
        try {
            Calificacion b = ctx.bodyAsClass(Calificacion.class);
            Validador.require(Validador.texto(b.inscripcionId()), "Inscripcion requerida");
            Validador.require(Validador.nota(b.nota()), "Nota debe ser 0.0 - 5.0");
            Validador.require(b.corte() >= 1 && b.corte() <= 3, "Corte debe ser 1, 2 o 3");

            Calificacion n = new Calificacion(UUID.randomUUID().toString(), b.inscripcionId(), b.nota(), b.corte());
            db.calificaciones.put(n.id(), n);
            ctx.status(201).json(n);
            db.save();
            Controlador.broadcast("UPDATE");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        }
    }

    public static void delete(Context ctx) {
        if (db.calificaciones.remove(ctx.pathParam("id")) != null) {
            db.save();
            Controlador.broadcast("UPDATE");
            ctx.status(204);
        } else {
            ctx.status(404);
        }
    }
}
