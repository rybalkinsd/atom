package test.java;

import main.MasterServer;
import messageSystem.messages.ReplicateMsg;
import network.packets.PacketLeaderBoard;
import network.packets.PacketReplicate;
import org.junit.Test;
import protocol.CommandReplicate;
import protocol.model.Cell;
import protocol.model.Food;
import replication.TestJsonStateReplicator;
import utils.JSONHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Created by venik on 27.11.16.
 */
public class ReplicationTest {
    @Test
    public void createReplicationJsonFile() throws Exception {
        Cell[] cells = new Cell[11];
        cells[0] = new Cell(1,2,false,150,-800,-400);
        cells[1] = new Cell(3,4,true,150,800,-400);

        cells[2] = new Cell(9,10,true,50,400,-150);
        cells[3] = new Cell(9,10,true,50,300,-100);
        cells[4] = new Cell(9,10,true,50,200,-50);
        cells[5] = new Cell(9,10,true,50,100,0);

        cells[6] = new Cell(9,10,true,50,0,0);

        cells[7] = new Cell(9,10,true,50,-100,0);
        cells[8] = new Cell(9,10,true,50,-200,-50);
        cells[9] = new Cell(9,10,true,50,-300,-100);
        cells[10] = new Cell(9,10,true,50,-400,-150);

        Food[] food = new Food[2];
        food[0] = new Food(0,200);
        food[1] = new Food(100,300);
        PacketReplicate packetReplicate = new PacketReplicate(cells, food);

        try (PrintWriter writer = new PrintWriter("src/main/resources/tmp/replJson.json", "UTF-8")) {
            writer.print(JSONHelper.toJSON(packetReplicate));
            writer.close();
        }
    }

    @Test
    public void replicationLeaderBoardTest() throws Exception
    {
        MasterServer masterServer = new MasterServer();
        masterServer.start();
    }
}
