package ru.atom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.google.gson.Gson;

import static ru.atom.AuthServer.random;
import static ru.atom.AuthServer.tokens;
import static ru.atom.AuthServer.users;

public class DataServlet extends HttpServlet {
    private static final String USER_PARAM = "user";
    private static final String PASSWORD_PARAM = "password";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, ArrayList<String>> allUsers = new HashMap<>();
        allUsers.put("users", users.getAll());
        Gson gson = new Gson();
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(gson.toJson(allUsers));
    }
}
