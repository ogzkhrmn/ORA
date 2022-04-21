package com.example.orion.websocket;


import com.example.orion.core.security.jwt.JWTService;
import com.example.orion.core.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SocketTextHandler extends AbstractWebSocketHandler {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private JWTUtil jwtUtil;

    private final Map<Long, WebSocketSession> sessionsMap = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage("Merhaba"));
//        addToOnline(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
//        removeFromList(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession webSocketSession, TextMessage message) throws Exception {
        String payload = message.getPayload();
        webSocketSession.sendMessage(new TextMessage("Merhaba" + payload + "bugün sana nasıl yardımcı olabilirim?"));

    }

    private void addToOnline(WebSocketSession session) {
        String token = session.getHandshakeHeaders().get(jwtUtil.getHeaderString()).get(0);
        Long id = jwtService.getIdFromToken(token);
        sessionsMap.put(id, session);
    }

}
