package controllers;

import io.javalin.http.Context;
import models.Curso;
import utils.DataStore;
import utils.Validador;
import java.util.UUID;
import java.util.ArrayList;

public class CursoController {
    private static final DataStore db = DataStore.get();

    public static void getAll(Context ctx) {
        ctx.json(db.cursos.values());
    }

    public static void create(Context ctx) {
        try {
            Curso b = ctx.bodyAsClass(Curso.class);
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
            Curso b = ctx.bodyAsClass(Curso.class);
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
