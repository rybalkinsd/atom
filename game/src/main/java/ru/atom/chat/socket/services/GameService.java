package ru.atom.chat.socket.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.util.HtmlUtils;
import ru.atom.chat.dao.PlayerDao;
import ru.atom.chat.socket.message.request.messagedata.RegisterUser;
import ru.atom.chat.socket.message.response.ResponseData;
import ru.atom.chat.socket.message.response.ResponseMessage;
import ru.atom.chat.socket.message.response.messagedata.OutgoingUser;
import ru.atom.chat.socket.services.lobby.Lobby;
import ru.atom.chat.socket.services.lobby.LobbyDealer;
import ru.atom.chat.socket.topics.IncomingTopic;
import ru.atom.chat.socket.topics.MailingType;
import ru.atom.chat.socket.topics.OutgoingTopic;
import ru.atom.chat.socket.util.JsonHelper;
import ru.atom.chat.socket.util.SessionsList;
import ru.atom.chat.user.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GameService {
    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private LobbyDealer lobbyDealer;

    public ResponseData register(RegisterUser registerUser) {
        ResponseData response = new ResponseData(IncomingTopic.REGISTER);

        String name = registerUser.getSender();

        if (name.length() < 1) {
            response.addSenderError("Too short name, sorry :(");
            return response;
        }
        if (name.length() > 20) {
            response.addSenderError("Too long name, sorry :(");
            return response;
        }

        name = HtmlUtils.htmlEscape(name);

        Player player = playerDao.getByLogin(name);
        if (player != null) {
            response.addSenderError("Already registered :(");
            return response;
        }

        Player newPlayer = new Player(name);
        playerDao.save(newPlayer);


        response.addSenderOkStatus();
        return response;
    }

    public ResponseData play(WebSocketSession session, String username) {
        ResponseData response = new ResponseData(IncomingTopic.PLAY);

        Player player = playerDao.getByLogin(username);
        Lobby lobby = lobbyDealer.connectToLobby(session, player);
        response.addSenderOkStatus();
        try {
            session.sendMessage(new TextMessage(response.get(0).getData()));
            ResponseMessage toPlayer = new ResponseMessage(
                    OutgoingTopic.LOBBY_PLAYERS,
                    JsonHelper.toJson(lobby.getPlayers())
            );
            session.sendMessage(new TextMessage(JsonHelper.toJson(toPlayer)));

            ResponseMessage toPlayers = new ResponseMessage(
                    OutgoingTopic.NEW_USER,
                    JsonHelper.toJson(SessionsList.finBySession(session))
            );
            lobby.broadcast(JsonHelper.toJson(toPlayers));
        } catch (IOException e) {
            log.error("cant sand message");
        }

        response.clear();
        return response;
    }

    public String allUsers() {
        List<Player> players = playerDao.findAll();
        List<OutgoingUser> outgoingUsers = new ArrayList<>();
        for (Player player : players) {
            outgoingUsers.add(new OutgoingUser(player));
        }
        return JsonHelper.toJson(outgoingUsers);
    }

    public boolean isLoggedIn(Player player) {
        return SessionsList.finByUsername(player.getLogin()) != null;
    }
}
