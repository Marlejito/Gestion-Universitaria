package app;

import io.javalin.Javalin;
import controllers.*;
import utils.DataStore;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        DataStore.get(); // Inicializar Base de Datos

        Javalin app = Javalin.create(c -> {
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
        app.get("/api/students", EstudianteController::getAll);
        app.post("/api/students", EstudianteController::create);
        app.patch("/api/students/{id}", EstudianteController::update);
        app.delete("/api/students/{id}", EstudianteController::delete);

        // --- Profesores ---
        app.get("/api/professors", ProfesorController::getAll);
        app.post("/api/professors", ProfesorController::create);
        app.patch("/api/professors/{id}", ProfesorController::update);
        app.delete("/api/professors/{id}", ProfesorController::delete);

        // --- Cursos ---
        app.get("/api/courses", CursoController::getAll);
        app.post("/api/courses", CursoController::create);
        app.patch("/api/courses/{id}", CursoController::update);
        app.delete("/api/courses/{id}", CursoController::delete);

        // --- Inscripciones ---
        app.get("/api/enrollments", InscripcionController::getAll);
        app.post("/api/enrollments", InscripcionController::create);
        app.patch("/api/enrollments/{id}", InscripcionController::update);
        app.delete("/api/enrollments/{id}", InscripcionController::delete);

        // --- Calificaciones ---
        app.get("/api/grades", CalificacionController::getAll);
        app.post("/api/grades", CalificacionController::create);
        app.patch("/api/grades/{id}", CalificacionController::update);
        app.delete("/api/grades/{id}", CalificacionController::delete);

        // --- Reportes ---
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
