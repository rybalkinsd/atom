package servlets;


import accounts.AccountService;

import accounts.UserProfile;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ilnur on 29.03.17.
 */

public class LogOutServlet extends HttpServlet{
    private final AccountService accountService;

    public LogOutServlet(AccountService accountService) { this.accountService = accountService; }

    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        UserProfile thisUP = accountService.getUserByLogin(request.getParameter("login"));

        if (thisUP != null && thisUP.getPass().equals(request.getParameter("password"))
                && accountService.isActive(thisUP)) {
            accountService.deleteSession(thisUP.getUtoken());
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(" Досвидания, " + thisUP.getLogin() + "\n");
            response.setStatus(HttpServletResponse.SC_OK);
            log("Пользователь " + thisUP.getLogin() + " вышел из сайта");

        } else {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(" Вам не удалось выйти отсюда");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
}
