package controllers;

import io.javalin.http.Context;
import models.Profesor;
import utils.DataStore;
import utils.Validador;
import java.util.UUID;

public class ProfesorController {
    private static final DataStore db = DataStore.get();

    public static void getAll(Context ctx) {
        ctx.json(db.profesores.values());
    }

    public static void create(Context ctx) {
        try {
            Profesor b = ctx.bodyAsClass(Profesor.class);
            Validador.require(Validador.texto(b.nombre()) && Validador.texto(b.especialidad()), "Datos incompletos");
            Profesor n = new Profesor(UUID.randomUUID().toString(), b.nombre(), b.especialidad());
            db.profesores.put(n.id(), n);
            ctx.status(201).json(n);
            db.save();
            Controlador.broadcast("UPDATE");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        }
    }

    public static void delete(Context ctx) {
        if (db.profesores.remove(ctx.pathParam("id")) != null) {
            db.save();
            Controlador.broadcast("UPDATE");
            ctx.status(204);
        } else {
            ctx.status(404);
        }
    }
}
