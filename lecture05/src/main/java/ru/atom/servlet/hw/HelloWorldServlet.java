package ru.atom.servlet.hw;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by sergey on 3/15/17.
 */
public class HelloWorldServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(HelloWorldServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Processing GET request");
        PrintWriter writer = resp.getWriter();
        writer.append("<html>")
                .append("<body>")
                .append("<h1>" + "GET " + getClass().getSimpleName() + "</h1>")
                .append("</body>")
                .append("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Processing POST request");
        PrintWriter writer = resp.getWriter();
        writer.append("<html>")
                .append("<body>")
                .append("<h1>" + "POST " + getClass().getSimpleName() + "</h1>")
                .append("</body>")
                .append("</html>");
    }

}
