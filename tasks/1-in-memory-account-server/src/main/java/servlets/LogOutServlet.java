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
        if(thisUP!=null&&thisUP.getPass().toString().equals(request.getParameter("password"))){
            String st = request.getSession().getId();
            accountService.deleteSession(request.getSession().getId());
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println (" Authorization: Bearer: " + st);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(" Authorization: not Bearer");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
}
