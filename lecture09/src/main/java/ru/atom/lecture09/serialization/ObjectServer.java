package ru.atom.lecture09.serialization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ObjectServer {
    private static final Logger log = LogManager.getLogger(ObjectServer.class);

    /**
     * This server accepts objects of type Packet
     * TODO: Implement client for this server and send Packet with YOUR_NAME_AND_SURNAME as payload to
     * TODO: wtfis.ru:12345
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int portNum = 12345;
        ServerSocket listener = new ServerSocket(portNum);
        System.out.println("ObjectServer is now running at port: " + portNum);

        //Run server forever
        while (true) {
            try (//Block until connection established
                 Socket clientSocket = listener.accept();
                 //Open InputStream to read Objects from socket
                 ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
            ) {
                //Block until object received
                Packet inputData = (Packet) in.readObject();
                log.info("[received] " + inputData);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }
}
