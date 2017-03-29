package servlets;
import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ilnur on 10.03.17.
 */

public class SignInServlet extends HttpServlet {

    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        UserProfile thisUP = accountService.getUserByLogin(request.getParameter("login"));
        if(thisUP!=null&&thisUP.getPass().toString().equals(request.getParameter("password"))){
            accountService.addSession(request.getSession().getId(), thisUP);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Authorized: " + thisUP.getLogin()+ " \n token:" + request.getSession().getId());
            response.setStatus(HttpServletResponse.SC_OK);
            log("Authorized: " + thisUP.getLogin()+ "   token: " + request.getSession().getId());
        } else {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Unauthorized");
            log("не авторизован : ");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

}
