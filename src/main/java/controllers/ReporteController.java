package controllers;

import io.javalin.http.Context;
import models.Calificacion;
import utils.DataStore;
import java.util.List;
import java.util.Map;

public class ReporteController {
    private static final DataStore db = DataStore.get();

    public static void getBoletin(Context ctx) {
        String estId = ctx.pathParam("id");
        var misInscripciones = db.inscripciones.values().stream().filter(i -> i.estudianteId().equals(estId)).toList();

        var reporte = misInscripciones.stream().map(ins -> {
            var curso = db.cursos.get(ins.cursoId());
            if (curso == null)
                return null;

            var notas = db.calificaciones.values().stream().filter(c -> c.inscripcionId().equals(ins.id())).toList();
            double n1 = promedioCorte(notas, 1);
            double n2 = promedioCorte(notas, 2);
            double n3 = promedioCorte(notas, 3);
            double def = (n1 * curso.p1() + n2 * curso.p2() + n3 * curso.p3()) / 100;

            return Map.of(
                    "curso", curso.nombre(),
                    "p1", curso.p1(), "nota1", round(n1),
                    "p2", curso.p2(), "nota2", round(n2),
                    "p3", curso.p3(), "nota3", round(n3),
                    "definitiva", round(def));
        }).toList();
        ctx.json(reporte);
    }

    public static void resumen(Context ctx) {
        ctx.json(Map.of(
                "totalEstudiantes", db.estudiantes.size(),
                "totalProfesores", db.profesores.size(),
                "totalCursos", db.cursos.size(),
                "totalInscripciones", db.inscripciones.size()));
    }

    private static double promedioCorte(List<Calificacion> notas, int corte) {
        var notasCorte = notas.stream().filter(n -> n.corte() == corte).toList();
        if (notasCorte.isEmpty())
            return 0.0;
        return notasCorte.stream().mapToDouble(Calificacion::nota).average().orElse(0.0);
    }

    private static double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
