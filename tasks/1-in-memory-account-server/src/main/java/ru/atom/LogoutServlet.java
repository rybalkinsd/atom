package ru.atom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static ru.atom.AuthServer.random;
import static ru.atom.AuthServer.tokens;
import static ru.atom.AuthServer.users;

public class LogoutServlet extends HttpServlet {
    private static final String USER_PARAM = "user";
    private static final String PASSWORD_PARAM = "password";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String header = req.getHeader("Authorization");
        String stringToken = header.split(" ")[1];
        stringToken = stringToken.substring(1, stringToken.length() - 1);
        System.out.print(stringToken);
        Long token = Long.valueOf(stringToken).longValue();
        if (tokens.validateToken(token)) {
            tokens.getStorage().remove(token);
            doSetResult(resp);
        } else {
            doSetError(resp);
        }
    }

    protected void doSetResult(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("You are signed out");
    }

    protected void doSetError(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("You are not logined");
    }
}
