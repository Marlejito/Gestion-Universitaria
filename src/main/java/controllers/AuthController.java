package controllers;

import io.javalin.http.Context;
import models.*;
import utils.DataStore;
import java.util.Map;

public class AuthController {
    private static final DataStore db = DataStore.get();

    public static void login(Context ctx) {
        Usuario u = ctx.bodyAsClass(Usuario.class);
        Usuario found = db.usuarios.get(u.username());
        
        if (found != null && found.password().equals(u.password())) {
            ctx.json(Map.of("token", "valid_token", "rol", found.rol()));
        } else {
            ctx.status(401).result("Credenciales inválidas");
        }
    }
}
