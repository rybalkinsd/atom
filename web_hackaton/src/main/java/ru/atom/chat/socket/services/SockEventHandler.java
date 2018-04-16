package ru.atom.chat.socket.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.atom.chat.socket.message.request.SocketMessage;
import ru.atom.chat.socket.message.response.OutcomingMessage;
import ru.atom.chat.socket.message.response.messagedata.ResponseData;
import ru.atom.chat.socket.topics.IncomingTopic;
import ru.atom.chat.socket.topics.OutgoingTopic;
import ru.atom.chat.socket.topics.ResponseTopic;
import ru.atom.chat.socket.util.JsonHelper;
import ru.atom.chat.socket.message.request.messagedata.IncomingMessage;
import ru.atom.chat.socket.util.SessionsList;

import java.io.IOException;

@Service
public class SockEventHandler extends TextWebSocketHandler {
    private Logger log = LoggerFactory.getLogger(SockEventHandler.class);

    @Autowired
    private ChatService chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        SessionsList.addSession(session);
        log.info("Socket Connected: " + session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String json = message.getPayload();
        SocketMessage socketMessage = JsonHelper.fromJson(json, SocketMessage.class);
        ResponseData response;

        log.info("Received message with type: " + socketMessage.getTopic());

        try {
            switch (socketMessage.getTopic()) {
                case MESSAGE:
                    IncomingMessage messageBody = JsonHelper.fromJson(socketMessage.getData(), IncomingMessage.class);
                    response = chatService.say(messageBody);
                    handleResponse(session, IncomingTopic.MESSAGE, response);
                    break;
                default:
                    log.warn("Unexpected message type: " + socketMessage.getTopic());
                    break;
            }
        } catch (IllegalArgumentException exception) {
            log.error("Message was not properly prepared, watch logs for more information.");
        }
    }

    private void handleResponse(WebSocketSession session, IncomingTopic topic, ResponseData response)
            throws IOException {
        SocketMessage responseMessage;
        if (response.getTopic() == ResponseTopic.OK) {
            ResponseData responseOnTopic;
            OutcomingMessage messageToAll;

            responseOnTopic = new ResponseData(ResponseTopic.OK, "");
            responseMessage = new SocketMessage(topic, JsonHelper.toJson(responseOnTopic));

            session.sendMessage(new TextMessage(JsonHelper.toJson(responseMessage)));
            messageToAll = new OutcomingMessage(OutgoingTopic.NEW_MESSAGE, response.getData());
            SessionsList.sendAll(JsonHelper.toJson(messageToAll));
        } else {
            responseMessage = new SocketMessage(topic, JsonHelper.toJson(response));
            session.sendMessage(new TextMessage(JsonHelper.toJson(responseMessage)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        SessionsList.deleteSession(session);
        super.afterConnectionClosed(session, closeStatus);
    }
}
