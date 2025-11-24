package controllers;

import io.javalin.http.Context;
import models.Curso;
import utils.DataStore;
import utils.Validador;
import java.util.UUID;

public class CursoController {
    private static final DataStore db = DataStore.get();

    public static void getAll(Context ctx) {
        ctx.json(db.cursos.values());
    }

    public static void create(Context ctx) {
        try {
            Curso b = ctx.bodyAsClass(Curso.class);
            Validador.require(Validador.texto(b.nombre()) && Validador.texto(b.profesorId()), "Datos incompletos");
            Validador.require(b.p1() + b.p2() + b.p3() == 100, "Los porcentajes deben sumar 100%");
            Curso n = new Curso(UUID.randomUUID().toString(), b.nombre(), b.profesorId(), b.p1(), b.p2(), b.p3());
            db.cursos.put(n.id(), n);
            ctx.status(201).json(n);
            db.save();
            Controlador.broadcast("UPDATE");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        }
    }

    public static void update(Context ctx) {
        try {
            String id = ctx.pathParam("id");
            Curso b = ctx.bodyAsClass(Curso.class);
            if (!db.cursos.containsKey(id))
                throw new IllegalArgumentException("Curso no encontrado");

            Curso updated = new Curso(id, b.nombre(), b.profesorId(), b.p1(), b.p2(), b.p3());
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
