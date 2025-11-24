package controllers;

import io.javalin.http.Context;
import utils.DataStore;
import java.util.HashMap;
import java.util.Map;

public class ReporteController {
    private static final DataStore db = DataStore.get();

    public static void getBoletin(Context ctx) {
        String id = ctx.pathParam("id");
        Map<String, Object> boletin = new HashMap<>();
        boletin.put("estudianteId", id);
        boletin.put("mensaje", "Funcionalidad de boletín en desarrollo");
        ctx.json(boletin);
    }

    public static void resumen(Context ctx) {
        Map<String, Object> resumen = new HashMap<>();
        resumen.put("estudiantes", db.estudiantes.size());
        resumen.put("profesores", db.profesores.size());
        resumen.put("cursos", db.cursos.size());
        resumen.put("inscripciones", db.inscripciones.size());
        ctx.json(resumen);
    }
}
