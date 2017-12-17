/*
package bomber.connectionhandler;

import bomber.connectionhandler.json.Json;
import bomber.connectionhandler.json.Replica;
import bomber.games.gameobject.Wall;
import bomber.games.geometry.Point;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.*;

public class EventHandlerTest {
    @Test
    public void possessTest() {
        String json = Json.possesToJson(123);
        Assert.assertEquals(json, "{\"topic\":\"POSSESS\",\"data\":123}");
    }

    @Test
    public void replicaTest() {
        Replica replica = new Replica();
        Map<Integer, Wall> map = new HashMap<>(20);
        map.put(10, new Wall(10, new Point(10,10)));
        map.put(11, new Wall(11, new Point(20,20)));
        String json = Json.replicaToJson(map);
        System.out.println(json);
    }

    @Test
    public void MoveTest() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        FileReader fin = new FileReader("C:/Users/Arif/Desktop/Texnoatom/bomberman/Move.txt");
        int c;
        String json = "";
        while ((c = fin.read()) != -1) {
            json += (char) c;
        }
        PlayerAction playerAction = Json.jsonToPlayerAction(1, json);
        Assert.assertEquals(playerAction.getType(), PlayerAction.EventType.DOWN);

    }

    @Test
    public void PlantBombTest() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        FileReader fin = new FileReader("C:/Users/Arif/Desktop/Texnoatom/bomberman/PLANT_BOMB.txt");
        int c;
        String json = "";
        while ((c = fin.read()) != -1) {
            json += (char) c;
        }
        PlayerAction playerAction = Json.jsonToPlayerAction(1, json);
        Assert.assertEquals(playerAction.getType(), PlayerAction.EventType.BOMB);
    }


}*/
