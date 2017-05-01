package ru.atom.lecture09.serialization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by ikozin on 22.04.17.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Logger log = LogManager.getLogger(Client.class);

        Socket socket = new Socket("wtfis.ru", 12345);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        Packet output = new Packet("Ivan Kozin");
        oos.writeObject(output);
        log.info("[sent]" + output);
        oos.close();
        socket.close();
    }
}
