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
import ru.atom.chat.socket.message.request.messagedata.RegisterUser;
import ru.atom.chat.socket.message.response.Mail;
import ru.atom.chat.socket.message.response.ResponseData;
import ru.atom.chat.socket.topics.ResponseTopic;
import ru.atom.chat.socket.util.JsonHelper;
import ru.atom.chat.socket.util.SessionsList;

import java.io.IOException;

@Service
public class SockEventHandler extends TextWebSocketHandler {
    private Logger log = LoggerFactory.getLogger(SockEventHandler.class);

    @Autowired
    private GameService gameService;

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
                case REGISTER:
                    RegisterUser registerUser = JsonHelper.fromJson(socketMessage.getData(), RegisterUser.class);
                    response = gameService.register(registerUser);
                    if (response.getStatus() == ResponseTopic.OK)
                        SessionsList.matchSessionWithName(session, registerUser.getSender());
                    handleResponse(session, response);
                    break;
                case PLAY:
                    gameService.play(session, SessionsList.finBySession(session));
                    break;
                default:
                    log.warn("Unexpected message type: " + socketMessage.getTopic());
                    break;
            }
        } catch (IllegalArgumentException exception) {
            log.error("Message was not properly prepared, watch logs for more information.");
            response = new ResponseData(socketMessage.getTopic());
            response.addSenderError(
                    "Message was not properly created, please contact the developers");
            handleResponse(session, response);
        }
    }

    private void handleResponse(WebSocketSession session, ResponseData response)
            throws IOException {
        for (Mail mail : response) {
            sendMail(session, mail);
        }
    }

    private void sendMail(WebSocketSession sender, Mail mail)
            throws IOException {
        switch (mail.getType()) {
            case TO_ALL:
                SessionsList.sendAll(mail.getData());
                break;
            case BACK_TO_SENDER:
                sender.sendMessage(new TextMessage(mail.getData()));
                break;
            case TO_USER:
                // TODO
                break;
            case TO_GROUP:
                // TODO
                break;
            default:
                log.error("Unexpected mail type, pls add it.");
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        SessionsList.deleteSession(session);
        super.afterConnectionClosed(session, closeStatus);
    }
}
