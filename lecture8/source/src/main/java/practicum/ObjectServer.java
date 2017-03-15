package practicum;

import java.io.*;
import java.net.*;

public class ObjectServer {
  /**
   * This server accepts objects of type Packet
   * TODO: Implement client for this server and send Packet with YOUR_NAME_AND_SURNAME as payload to
   * TODO: 0.0.0.0:12345
   */
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    int portNum = 12345;
    ServerSocket listener = new ServerSocket(portNum);
    System.out.println("ObjectServer is now running at port: " + portNum);

    //Run server forever
    while (true) {
      try (
          //Block until mm established
          Socket clientSocket = listener.accept();
          //Open InputStream to read Objects from socket
           ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
      ) {

        //Block until object received
        Packet inputData = (Packet) in.readObject();

        System.out.println("[received] " + inputData);
      }
    }
  }
}
