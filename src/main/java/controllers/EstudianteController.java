package controllers;

import io.javalin.http.Context;
import models.Estudiante;
import utils.DataStore;
import utils.Validador;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EstudianteController {
    private static final DataStore db = DataStore.get();

    public static void getAll(Context ctx) {
        ctx.json(db.estudiantes.values());
    }

    public record EstudianteRequest(
            @JsonProperty("studentId") String codigoEstudiante,
            @JsonProperty("firstName") String nombre,
            @JsonProperty("lastName") String apellido,
            String email,
            @JsonProperty("phone") String telefono,
            @JsonProperty("program") String programa,
            @JsonProperty("semester") int semestre,
            String status) {
    }

    public static void create(Context ctx) {
        try {
            EstudianteRequest b = ctx.bodyAsClass(EstudianteRequest.class);
            Validador.require(Validador.texto(b.nombre()), "Nombre requerido");
            Validador.require(Validador.texto(b.apellido()), "Apellido requerido");
            Validador.require(Validador.email(b.email()), "Email inválido");

            Estudiante nuevo = new Estudiante(
                    UUID.randomUUID().toString(),
                    b.codigoEstudiante(),
                    b.nombre(),
                    b.apellido(),
                    b.email(),
                    b.telefono(),
                    b.programa(),
                    b.semestre(),
                    b.status() != null ? b.status() : "activo");

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
            EstudianteRequest b = ctx.bodyAsClass(EstudianteRequest.class);
            if (!db.estudiantes.containsKey(id))
                throw new IllegalArgumentException("Estudiante no encontrado");

            Estudiante updated = new Estudiante(
                    id,
                    b.codigoEstudiante(),
                    b.nombre(),
                    b.apellido(),
                    b.email(),
                    b.telefono(),
                    b.programa(),
                    b.semestre(),
                    b.status());

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
