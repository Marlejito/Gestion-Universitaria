package controllers;

import java.util.Map;
import java.util.UUID;

import io.javalin.http.Context;
import models.Calificacion;
import models.Curso;
import models.Estudiante;
import models.Inscripcion;
import models.Profesor;
import utils.DataStore;
import utils.Validador;

public class Controlador {
    private static final DataStore db = DataStore.get();

    // Helpers
    private static String uuid() { return UUID.randomUUID().toString(); }
    private static void ok(Context ctx, Object o) { ctx.status(201).json(o); db.save(); }
    private static <T> void tryOp(Context ctx, Runnable op) {
        try { op.run(); } catch (IllegalArgumentException e) { ctx.status(400).result(e.getMessage()); }
    }

    // Estudiantes
    public static void getEst(Context ctx) { ctx.json(db.estudiantes); }
    public static void addEst(Context ctx) {
        tryOp(ctx, () -> {
            Estudiante b = ctx.bodyAsClass(Estudiante.class);
            Validador.require(Validador.texto(b.nombre()), "Nombre requerido");
            Validador.require(Validador.email(b.email()), "Email inválido");
            Estudiante nuevo = new Estudiante(uuid(), b.nombre(), b.email());
            db.estudiantes.add(nuevo);
            ok(ctx, nuevo);
        });
    }
    public static void delEst(Context ctx) { db.estudiantes.removeIf(x -> x.id().equals(ctx.pathParam("id"))); db.save(); ctx.status(204); }

    // Profesores
    public static void getProf(Context ctx) { ctx.json(db.profesores); }
    public static void addProf(Context ctx) {
        tryOp(ctx, () -> {
            Profesor b = ctx.bodyAsClass(Profesor.class);
            Validador.require(Validador.texto(b.nombre()) && Validador.texto(b.especialidad()), "Datos incompletos");
            Profesor n = new Profesor(uuid(), b.nombre(), b.especialidad());
            db.profesores.add(n); ok(ctx, n);
        });
    }
    public static void delProf(Context ctx) { db.profesores.removeIf(x -> x.id().equals(ctx.pathParam("id"))); db.save(); ctx.status(204); }

    // Cursos
    public static void getCur(Context ctx) { ctx.json(db.cursos); }
    public static void addCur(Context ctx) {
        tryOp(ctx, () -> {
            Curso b = ctx.bodyAsClass(Curso.class);
            Validador.require(Validador.texto(b.nombre()) && Validador.texto(b.profesorId()), "Datos incompletos");
            Validador.require(b.p1() + b.p2() + b.p3() == 100, "Los porcentajes deben sumar 100%");
            Curso n = new Curso(uuid(), b.nombre(), b.profesorId(), b.p1(), b.p2(), b.p3());
            db.cursos.add(n); ok(ctx, n);
        });
    }
    public static void delCur(Context ctx) { db.cursos.removeIf(x -> x.id().equals(ctx.pathParam("id"))); db.save(); ctx.status(204); }

    // Inscripciones
    public static void getIns(Context ctx) { ctx.json(db.inscripciones); }
    public static void addIns(Context ctx) {
        tryOp(ctx, () -> {
            Inscripcion b = ctx.bodyAsClass(Inscripcion.class);
            Validador.require(Validador.texto(b.estudianteId()) && Validador.texto(b.cursoId()), "Datos requeridos");
            // Evitar duplicados
            boolean existe = db.inscripciones.stream().anyMatch(i -> i.estudianteId().equals(b.estudianteId()) && i.cursoId().equals(b.cursoId()));
            Validador.require(!existe, "El estudiante ya está inscrito en este curso");
            
            Inscripcion n = new Inscripcion(uuid(), b.estudianteId(), b.cursoId());
            db.inscripciones.add(n); ok(ctx, n);
        });
    }
    public static void delIns(Context ctx) { db.inscripciones.removeIf(x -> x.id().equals(ctx.pathParam("id"))); db.save(); ctx.status(204); }

    // Calificaciones
    public static void getCal(Context ctx) { ctx.json(db.calificaciones); }
    public static void addCal(Context ctx) {
        tryOp(ctx, () -> {
            Calificacion b = ctx.bodyAsClass(Calificacion.class);
            Validador.require(Validador.texto(b.inscripcionId()), "Inscripcion requerida");
            Validador.require(Validador.nota(b.nota()), "Nota debe ser 0.0 - 5.0");
            Validador.require(b.corte() >= 1 && b.corte() <= 3, "Corte debe ser 1, 2 o 3");
            
            Calificacion n = new Calificacion(uuid(), b.inscripcionId(), b.nota(), b.corte());
            db.calificaciones.add(n); ok(ctx, n);
        });
    }
    public static void delCal(Context ctx) { db.calificaciones.removeIf(x -> x.id().equals(ctx.pathParam("id"))); db.save(); ctx.status(204); }
    
    // Boletin por Estudiante
    public static void getBoletin(Context ctx) {
        String estId = ctx.pathParam("id");
        // Buscar inscripciones del estudiante
        var misInscripciones = db.inscripciones.stream()
                .filter(i -> i.estudianteId().equals(estId))
                .toList();
        
        // Construccion de reporte
        var reporte = misInscripciones.stream().map(ins -> {
            var curso = db.cursos.stream().filter(c -> c.id().equals(ins.cursoId())).findFirst().orElse(null);
            if (curso == null) return null;
            
            // Notas por corte
            var notas = db.calificaciones.stream().filter(c -> c.inscripcionId().equals(ins.id())).toList();
            
            double n1 = promedioCorte(notas, 1);
            double n2 = promedioCorte(notas, 2);
            double n3 = promedioCorte(notas, 3);
            
            double def = (n1 * curso.p1() + n2 * curso.p2() + n3 * curso.p3()) / 100;
            
            return Map.of(
                "curso", curso.nombre(),
                "p1", curso.p1(), "nota1", Math.round(n1 * 100.0) / 100.0,
                "p2", curso.p2(), "nota2", Math.round(n2 * 100.0) / 100.0,
                "p3", curso.p3(), "nota3", Math.round(n3 * 100.0) / 100.0,
                "definitiva", Math.round(def * 100.0) / 100.0
            );
        }).toList();
        
        ctx.json(reporte);
    }
    
    private static double promedioCorte(java.util.List<Calificacion> notas, int corte) {
        var notasCorte = notas.stream().filter(n -> n.corte() == corte).toList();
        if (notasCorte.isEmpty()) return 0.0;
        return notasCorte.stream().mapToDouble(Calificacion::nota).average().orElse(0.0);
    }

    // Reporte
    public static void resumen(Context ctx) {
        ctx.json(Map.of(
            "totalEstudiantes", db.estudiantes.size(),
            "totalProfesores", db.profesores.size(),
            "totalCursos", db.cursos.size(),
            "totalInscripciones", db.inscripciones.size()
        ));
    }
}
