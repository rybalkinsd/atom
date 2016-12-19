package accountserver.api;

import accountserver.TokenService;
import accountserver.model.data.UserProfile;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by eugene on 10/14/16.
 */
@SuppressWarnings("DefaultFileTemplate")
@Provider
@Authorized
public class AuthorizationFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String authHeader = containerRequestContext.getHeaderString("Authorization");
        UserProfile user;

        if(
                        null != authHeader &&
                        authHeader.matches("^Bearer -?[0-9]+$") &&
                                (user = new TokenService().getUserByTokenString(authHeader.split(" ")[1])) != null
                ){

            containerRequestContext.getHeaders().add("token",authHeader.split(" ")[1]);
            containerRequestContext.getHeaders().add("userId",user.getId().toString());
            return;
        }

        containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}
