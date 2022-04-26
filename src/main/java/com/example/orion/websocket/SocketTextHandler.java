package com.example.orion.websocket;


import com.example.orion.core.i18n.Translator;
import com.example.orion.entities.Phone;
import com.example.orion.entities.User;
import com.example.orion.repositories.MissedCallRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SocketTextHandler extends AbstractWebSocketHandler {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM HH:mm");

    @Autowired
    private MissedCallRepository missedCallRepository;

    private final Map<Long, WebSocketSession> sessionsMap = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        User user = (User) session.getAttributes().get("user");
        sessionsMap.putIfAbsent(user.getId(), session);
        session.sendMessage(new TextMessage(getMissedCallMessage(missedCallRepository.findMissedCalls(user.getId()), user)));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        User user = (User) session.getAttributes().get("user");
        sessionsMap.remove(user.getId());
    }

    private String getMissedCallMessage(List<Object[]> missedCallList, User user) throws IOException {
        if (missedCallList.isEmpty()) {
            return StringUtils.EMPTY;
        }
        Locale locale = Locale.forLanguageTag(user.getLang().getLanguage());
        String config = user.getConfig();
        StringBuilder builder = new StringBuilder();
        for (Object[] missedCall : missedCallList) {
            String formattedDate = ((LocalDateTime) missedCall[3]).format(FORMATTER);
            Phone phone = (Phone) missedCall[1];
            builder.append(config.replace("{{phone}}", phone.getPhoneNumber())
                            .replace("{{number}}", missedCall[2].toString())
                            .replace("{{date}}", formattedDate))
                    .append("\n");
            sendAvailableMessage(phone, user.getPhone().getPhoneNumber(), formattedDate, (Boolean) missedCall[4]);
        }
        return Translator.getMessage("missed-calls", locale, builder.toString());
    }

    private void sendAvailableMessage(Phone from, String to, String dateTime, Boolean recieved) throws IOException {
        if (Boolean.FALSE.equals(recieved) && sessionsMap.containsKey(from.getUser().getId())) {
            WebSocketSession socketSession = sessionsMap.get(from.getUser().getId());
            User user = (User) socketSession.getAttributes().get("user");
            missedCallRepository.updateRecieved(from);
            socketSession.sendMessage(new TextMessage(Translator.getMessage("now.available",
                    Locale.forLanguageTag(user.getLang().getLanguage()), to, dateTime)));
        }
    }

}
