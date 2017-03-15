package ru.atom.servlet.mm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.thread.mm.Connection;
import ru.atom.thread.mm.ThreadSafeQueue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sergey on 3/15/17.
 */
public class ConnectionHandler extends HttpServlet {
    private static final Logger log = LogManager.getLogger(ConnectionHandler.class);
    private static final String NAME_PARAM = "name";
    private static final String ID_PARAM = "id";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter(NAME_PARAM);
        Long id = Long.parseLong(req.getParameter(ID_PARAM));
        log.info("New Connection from {} {}", id, name);

        ThreadSafeQueue.getInstance().offer(new Connection(id, name));
        resp.getWriter().append("OK");
    }
}
