package controllers;

import io.javalin.http.Context;
import models.Calificacion;
import utils.DataStore;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CalificacionController {
    private static final DataStore db = DataStore.get();

    public static void getAll(Context ctx) {
        ctx.json(db.calificaciones.values());
    }

    public record CalificacionRequest(
            @JsonProperty("enrollmentId") String inscripcionId,
            @JsonProperty("grade") Double nota,
            @JsonProperty("letterGrade") String notaLetra,
            String status) {
    }

    public static void create(Context ctx) {
        try {
            CalificacionRequest b = ctx.bodyAsClass(CalificacionRequest.class);

            // Validar rango de nota colombiana (1.0-5.0)
            if (b.nota() < 1.0 || b.nota() > 5.0) {
                throw new IllegalArgumentException("La nota debe estar entre 1.0 y 5.0");
            }

            // Generar nota letra automáticamente
            String notaLetra = Calificacion.obtenerNotaLetra(b.nota());

            Calificacion nueva = new Calificacion(
                    UUID.randomUUID().toString(),
                    b.inscripcionId(),
                    b.nota(),
                    notaLetra,
                    b.status() != null ? b.status() : "pendiente");

            db.calificaciones.put(nueva.id(), nueva);
            ctx.status(201).json(nueva);
            db.save();
            Controlador.broadcast("UPDATE");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        }
    }

    public static void update(Context ctx) {
        try {
            String id = ctx.pathParam("id");
            CalificacionRequest b = ctx.bodyAsClass(CalificacionRequest.class);
            if (!db.calificaciones.containsKey(id))
                throw new IllegalArgumentException("Calificación no encontrada");

            // Validar rango de nota colombiana (1.0-5.0)
            if (b.nota() < 1.0 || b.nota() > 5.0) {
                throw new IllegalArgumentException("La nota debe estar entre 1.0 y 5.0");
            }

            // Generar nota letra automáticamente
            String notaLetra = Calificacion.obtenerNotaLetra(b.nota());

            Calificacion updated = new Calificacion(
                    id,
                    b.inscripcionId(),
                    b.nota(),
                    notaLetra,
                    b.status());

            db.calificaciones.put(id, updated);
            ctx.json(updated);
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
