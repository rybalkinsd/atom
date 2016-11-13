package practicum;

import java.io.*;
import java.net.*;

/**
 * @author darren
 */
public class ObjectClient {
  public static void main(String arg[]) throws IOException, ClassNotFoundException {
    Socket socket = new Socket("localhost", 12345);

    Packet packet = new Packet("Hello, ObjectServer!");

    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

    out.writeObject(packet);
  }
}
