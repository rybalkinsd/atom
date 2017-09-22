package ru.atom.servlet.mm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.thread.mm.ThreadSafeStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by sergey on 3/15/17.
 */
public class GamesView extends HttpServlet {
    private static final Logger log = LogManager.getLogger(GamesView.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("View request");
        PrintWriter writer = resp.getWriter();
        writer.append(
                ThreadSafeStorage.getAll().toString()
        );
    }
}
