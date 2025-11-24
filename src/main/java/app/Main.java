package app;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import controllers.*;
import utils.DataStore;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        DataStore.get(); // Inicializar Base de Datos

        Javalin app = Javalin.create(c -> {
            c.staticFiles.add("/public", Location.CLASSPATH);
            c.plugins.enableCors(cors -> cors.add(it -> it.anyHost()));
        }).start(7000);

        // --- WebSocket ---
        app.ws("/api/ws", ws -> {
            ws.onConnect(ctx -> Controlador.wsClients.add(ctx));
            ws.onClose(ctx -> Controlador.wsClients.remove(ctx));
        });

        // --- Autenticación ---
        app.post("/api/login", AuthController::login);

        // --- Estudiantes ---
        app.get("/api/estudiantes", EstudianteController::getAll);
        app.post("/api/estudiantes", EstudianteController::create);
        app.put("/api/estudiantes/{id}", EstudianteController::update);
        app.delete("/api/estudiantes/{id}", EstudianteController::delete);

        // --- Profesores ---
        app.get("/api/profesores", ProfesorController::getAll);
        app.post("/api/profesores", ProfesorController::create);
        app.delete("/api/profesores/{id}", ProfesorController::delete);

        // --- Cursos ---
        app.get("/api/cursos", CursoController::getAll);
        app.post("/api/cursos", CursoController::create);
        app.put("/api/cursos/{id}", CursoController::update);
        app.delete("/api/cursos/{id}", CursoController::delete);

        // --- Otros ---
        app.get("/api/inscripciones", InscripcionController::getAll);
        app.post("/api/inscripciones", InscripcionController::create);
        app.delete("/api/inscripciones/{id}", InscripcionController::delete);

        app.get("/api/calificaciones", CalificacionController::getAll);
        app.post("/api/calificaciones", CalificacionController::create);
        app.delete("/api/calificaciones/{id}", CalificacionController::delete);

        app.get("/api/boletin/{id}", ReporteController::getBoletin);
        app.get("/api/reporte/resumen", ReporteController::resumen);

        // --- Versión ---
        app.get("/api/version", ctx -> {
            Properties props = new Properties();
            props.load(Main.class.getResourceAsStream("/version.properties"));
            ctx.result(props.getProperty("version"));
        });

        System.out.println("Sistema listo en http://localhost:7000");
    }
}
