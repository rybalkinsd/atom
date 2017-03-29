package ru.atom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static ru.atom.AuthServer.users;

public class RegisterServlet extends HttpServlet {
    private static final String USER_PARAM = "user";
    private static final String PASSWORD_PARAM = "password";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter(USER_PARAM);
        String password = (req.getParameter(PASSWORD_PARAM));
        if (users.getStorage().containsValue(userName)) {
            doSetError(resp);
        } else {
            users.getStorage().put(password, userName);
            doSetResult(resp);
        }
    }

    protected void doSetResult(HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        String reply = "You are signed up!";
        response.getOutputStream().write(reply.getBytes("UTF-8"));
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected void doSetError(HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        String reply = "This user is already signed up";
        response.getOutputStream().write(reply.getBytes("UTF-8"));
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
