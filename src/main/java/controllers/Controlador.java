package controllers;

import io.javalin.websocket.WsContext;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Controlador {
    public static final Set<WsContext> wsClients = ConcurrentHashMap.newKeySet();

    // Difusión WebSocket
    public static void broadcast(String msg) {
        wsClients.stream().filter(ctx -> ctx.session.isOpen()).forEach(session -> session.send(msg));
    }
}
