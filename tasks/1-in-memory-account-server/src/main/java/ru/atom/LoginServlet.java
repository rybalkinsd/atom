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

public class LoginServlet extends HttpServlet {
    private static final String USER_PARAM = "user";
    private static final String PASSWORD_PARAM = "password";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter(USER_PARAM);
        String password = req.getParameter(PASSWORD_PARAM);
        if (!users.getStorage().containsValue(userName)) {
            doSetResultIfNotRegistered(resp);
        } else if (!users.validatePassword(password, userName)) {
            doSetResultIfIncorrectPassword(resp);
        } else {
            if (tokens.getStorage().containsValue(userName)) {
                doSetResultIfLogined(resp, tokens.getTokenByName(userName));
            } else if (users.getStorage().containsValue(userName)) {
                long token = random.nextLong();
                tokens.getStorage().put(token, userName);
                doSetResultIfNotLogined(resp, token);
            }
        }
    }

    protected void doSetResultIfLogined(HttpServletResponse response, Long token) throws IOException {
        response.setContentType("text/plain;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("You are already logined" + token.toString());
    }

    protected void doSetResultIfNotLogined(HttpServletResponse response, Long token) throws IOException {
        response.setContentType("text/plain;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("You are logined" + token.toString());
    }

    protected void doSetResultIfIncorrectPassword(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("Incorrect password");
    }

    protected void doSetResultIfNotRegistered(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("Not registred");
    }
}
