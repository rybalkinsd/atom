package servlets;

import accounts.AccountService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ilnur on 29.03.17.
 */
public class GetUsers extends HttpServlet{

    private final AccountService accountService;
    public GetUsers(AccountService accountService) { this.accountService = accountService; }
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response, AccountService accountService) throws ServletException, IOException {

        String json = new Gson().toJson(accountService.getusers());

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(json);
        response.setStatus(HttpServletResponse.SC_OK);

    }
}
