package test.java;

import main.ApplicationContext;
import main.MasterServer;
import matchmaker.MatchMaker;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.EjectMassMsg;
import messageSystem.messages.MoveMsg;
import messageSystem.messages.SplitMsg;
import model.Player;
import network.ClientConnectionServer;
import network.ClientConnections;
import network.handlers.PacketHandlerEjectMass;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import protocol.CommandEjectMass;
import protocol.CommandMove;
import protocol.CommandSplit;

/**
 * Created by svuatoslav on 11/26/16.
 */
public class MechanicsPacketImmitator {

    @Test
    public void EjectMassImmitator() throws Exception
    {
        MasterServer masterServer = new MasterServer();
        Thread thread = new Thread(()->
        {
            try
            {
                masterServer.start();
            }
            catch (Exception ex)
            {}
        });
        thread.start();
        Thread.sleep(3000);
        while(!masterServer.isReady()){}
        @NotNull MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
        Message message = new EjectMassMsg(messageSystem.getService(ClientConnectionServer.class).getAddress(),new CommandEjectMass(),new Player(0,"Test player"));
        messageSystem.sendMessage(message);
        Thread.sleep(3000);
        thread.interrupt();
    }

    @Test
    public void SplitImmitator() throws Exception
    {
        MasterServer masterServer = new MasterServer();
        Thread thread = new Thread(()->
        {
            try
            {
                masterServer.start();
            }
            catch (Exception ex)
            {}
        });
        thread.start();
        while(!masterServer.isReady()){}
        Thread.sleep(3000);
        @NotNull MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
        Message message = new SplitMsg(messageSystem.getService(ClientConnectionServer.class).getAddress(),new CommandSplit(),new Player(0,"Test player"));
        messageSystem.sendMessage(message);
        Thread.sleep(3000);
        thread.interrupt();
    }

    @Test
    public void MoveImmitator() throws Exception
    {
        MasterServer masterServer = new MasterServer();
        Thread thread = new Thread(()->
        {
            try
            {
                masterServer.start();
            }
            catch (Exception ex)
            {}
        });
        thread.start();
        while(!masterServer.isReady()){}
        Thread.sleep(3000);
        @NotNull MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
        Message message = new MoveMsg(messageSystem.getService(ClientConnectionServer.class).getAddress(),new CommandMove(1,1),new Player(0,"Test player"));
        messageSystem.sendMessage(message);
        Thread.sleep(3000);
        thread.interrupt();
    }

    @Test
    public void FullImmitator() throws Exception
    {
        MasterServer masterServer = new MasterServer();
        Thread thread = new Thread(()->
        {
            try
            {
                masterServer.start();
            }
            catch (Exception ex)
            {}
        });
        thread.start();
        while(!masterServer.isReady()){}
        Thread.sleep(3000);
        @NotNull MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
        Message message = new MoveMsg(messageSystem.getService(ClientConnectionServer.class).getAddress(),new CommandMove(1,1),new Player(0,"Test player"));
        messageSystem.sendMessage(message);
        Thread.sleep(3000);
        message = new SplitMsg(messageSystem.getService(ClientConnectionServer.class).getAddress(),new CommandSplit(),new Player(0,"Test player"));
        messageSystem.sendMessage(message);
        Thread.sleep(3000);
        message = new EjectMassMsg(messageSystem.getService(ClientConnectionServer.class).getAddress(),new CommandEjectMass(),new Player(0,"Test player"));
        messageSystem.sendMessage(message);
        Thread.sleep(3000);
        thread.interrupt();
    }

}
