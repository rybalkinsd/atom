package ru.atom.servlets.examples;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DummyServlet extends HttpServlet {
  @NotNull
  private static final Logger log = LogManager.getLogger(DummyServlet.class);

  @Override
  public void doGet(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response)
      throws IOException, ServletException {
    log.info("GET request from " + request.getRemoteAddr());
    // Set the response message's MIME type
    response.setContentType("text/html;charset=UTF-8");
    // Allocate a output writer to write the response message into the network socket
    PrintWriter out = response.getWriter();
    request.getRequestURI();

    // Write the response message, in an HTML page
    try {
      out.println("<!DOCTYPE html>");
      out.println("<html><head>");
      out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
      out.println("<title>Hello, World</title></head>");
      out.println("<body>");
      out.println("<h1>Echo server</h1>");  // says Hello
      // Echo client's request information
      out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
      out.println("<p>Protocol: " + request.getProtocol() + "</p>");
      out.println("<p>PathInfo: " + request.getPathInfo() + "</p>");
      out.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>");
      // Generate a random number upon each request
      out.println("<p>A Random Number: <strong>" + Math.random() + "</strong></p>");
      out.println("</body>");
      out.println("</html>");
    } finally {
      out.close();  // Always close the output writer
    }
  }
}