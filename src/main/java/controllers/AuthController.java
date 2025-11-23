package controllers;

import io.javalin.http.Context;
import models.*;
import utils.DataStore;
import java.util.Map;

public class AuthController {
    private static final DataStore db = DataStore.get();

    public static void login(Context ctx) {
        Usuario u = ctx.bodyAsClass(Usuario.class);
        var found = db.usuarios.stream()
            .filter(user -> user.username().equals(u.username()) && user.password().equals(u.password()))
            .findFirst();
        
        if (found.isPresent()) {
            ctx.json(Map.of("token", "valid_token", "rol", found.get().rol()));
        } else {
            ctx.status(401).result("Credenciales inválidas");
        }
    }
}
