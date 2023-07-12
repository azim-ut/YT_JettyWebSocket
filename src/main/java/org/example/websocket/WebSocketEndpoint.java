package org.example.websocket;


import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebSocket
public class WebSocketEndpoint {

    private static final Set<Session> sessionsList = Collections.synchronizedSet(new HashSet<>());


    @OnWebSocketConnect
    public void clientConnected(Session session){
        sessionsList.add(session);
        System.out.println("New client connected: " + session.getRemoteAddress().toString());
    }

    @OnWebSocketClose
    public void clientClose(Session session){
        sessionsList.remove(session);
        System.out.println("Client disconnected: " + session.getRemoteAddress().toString());
    }

    @OnWebSocketError
    public void clientError(Throwable err){
        System.out.println("Client error: " + err.toString());
    }

    @OnWebSocketMessage
    public void clientMessage(Session session, String message) throws IOException {
        session.getRemote().sendString(message + ": OK");
        System.out.println("Client message: " + message);
    }
}
