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
            Validador.require(Validador.texto(b.nombre()), "Nombre requerido");
            Validador.require(Validador.texto(b.apellido()), "Apellido requerido");
            Validador.require(Validador.email(b.email()), "Email inválido");

            Profesor nuevo = new Profesor(
                    UUID.randomUUID().toString(),
                    b.codigoProfesor(),
                    b.nombre(),
                    b.apellido(),
                    b.email(),
                    b.telefono(),
                    b.departamento(),
                    b.especializacion(),
                    b.status() != null ? b.status() : "activo");

            db.profesores.put(nuevo.id(), nuevo);
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
            Profesor b = ctx.bodyAsClass(Profesor.class);
            if (!db.profesores.containsKey(id))
                throw new IllegalArgumentException("Profesor no encontrado");

            Profesor updated = new Profesor(
                    id,
                    b.codigoProfesor(),
                    b.nombre(),
                    b.apellido(),
                    b.email(),
                    b.telefono(),
                    b.departamento(),
                    b.especializacion(),
                    b.status());

            db.profesores.put(id, updated);
            ctx.json(updated);
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
