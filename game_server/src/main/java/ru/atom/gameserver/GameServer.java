package ru.atom.gameserver;

import com.fasterxml.jackson.databind.JsonNode;
import ru.atom.gameserver.geometry.Point;
import ru.atom.gameserver.model.Pawn;
import ru.atom.gameserver.util.JsonHelper;

/**
 * Created by Alexandr on 05.12.2017.
 */
public class GameServer {

    public static void main(String[] args) {
        Pawn pawn = new Pawn(1, new Point(1.0f, 1.0f), 1.0f, 1);
        JsonNode jsonNode = JsonHelper.getJsonNode(pawn);
        System.out.println(jsonNode.toString());
    }
}
