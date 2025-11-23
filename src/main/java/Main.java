import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import controllers.Controlador;
import utils.DataStore;

public class Main {
    public static void main(String[] args) {
        // Forzar carga de datos al inicio
        DataStore.get(); 
        
        Javalin app = Javalin.create(c -> {
            c.staticFiles.add("public", Location.EXTERNAL);
            c.plugins.enableCors(cors -> cors.add(it -> it.anyHost()));
        }).start(7000);

        // Rutas concisas
        app.get("/api/estudiantes", Controlador::getEst);
        app.post("/api/estudiantes", Controlador::addEst);
        app.delete("/api/estudiantes/{id}", Controlador::delEst);

        app.get("/api/profesores", Controlador::getProf);
        app.post("/api/profesores", Controlador::addProf);
        app.delete("/api/profesores/{id}", Controlador::delProf);

        app.get("/api/cursos", Controlador::getCur);
        app.post("/api/cursos", Controlador::addCur);
        app.delete("/api/cursos/{id}", Controlador::delCur);

        app.get("/api/inscripciones", Controlador::getIns);
        app.post("/api/inscripciones", Controlador::addIns);
        app.delete("/api/inscripciones/{id}", Controlador::delIns);

        app.get("/api/calificaciones", Controlador::getCal);
        app.post("/api/calificaciones", Controlador::addCal);
        app.delete("/api/calificaciones/{id}", Controlador::delCal);
        
        app.get("/api/boletin/{id}", Controlador::getBoletin);

        app.get("/api/reporte/resumen", Controlador::resumen);

        System.out.println("Sistema listo en http://localhost:7000");
    }
}
