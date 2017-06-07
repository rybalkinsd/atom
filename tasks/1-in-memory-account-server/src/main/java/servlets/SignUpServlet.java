package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ilnur on 10.03.17.
 */

public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;
    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        UserProfile userProfile = new UserProfile(request.getParameter("login"),
                request.getParameter("password"));

        if ((!userProfile.getLogin().equals("{user}")
                && accountService.getUserByLogin(userProfile.getLogin()) == null)) {
            accountService.addNewUser(userProfile);

            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Пользователь под логином ///" + userProfile.getLogin() +
                    " /// зарегестрирован");
            response.setStatus(HttpServletResponse.SC_OK);
            log("Пользователь под логином ///" + userProfile.getLogin() +
                    " /// зарегестрирован");

        } else {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Пользователь ..." + userProfile.getLogin() + " ...уже существует");
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }
    }
}
