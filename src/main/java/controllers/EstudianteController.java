package controllers;

import io.javalin.http.Context;
import models.Estudiante;
import utils.DataStore;
import utils.Validador;
import java.util.UUID;

public class EstudianteController {
    private static final DataStore db = DataStore.get();

    public static void getAll(Context ctx) {
        ctx.json(db.estudiantes.values());
    }

    public static void create(Context ctx) {
        try {
            Estudiante b = ctx.bodyAsClass(Estudiante.class);
            Validador.require(Validador.texto(b.nombre()), "Nombre requerido");
            Validador.require(Validador.email(b.email()), "Email inválido");
            Estudiante nuevo = new Estudiante(UUID.randomUUID().toString(), b.nombre(), b.email());
            db.estudiantes.put(nuevo.id(), nuevo);
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
            Estudiante b = ctx.bodyAsClass(Estudiante.class);
            if (!db.estudiantes.containsKey(id))
                throw new IllegalArgumentException("Estudiante no encontrado");

            Estudiante updated = new Estudiante(id, b.nombre(), b.email());
            db.estudiantes.put(id, updated);
            ctx.json(updated);
            db.save();
            Controlador.broadcast("UPDATE");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        }
    }

    public static void delete(Context ctx) {
        if (db.estudiantes.remove(ctx.pathParam("id")) != null) {
            db.save();
            Controlador.broadcast("UPDATE");
            ctx.status(204);
        } else {
            ctx.status(404);
        }
    }
}
