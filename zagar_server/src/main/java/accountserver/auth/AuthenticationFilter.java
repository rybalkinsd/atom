package accountserver.auth;

import model.TokensCollection;
import model.TokensCollectionImpl;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sl on 19.10.2016.
 */


@Authorized
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {
    TokensCollection data=new TokensCollectionImpl();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Get the HTTP Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        //want to logout
        int flag = 0;

        if (requestContext.getUriInfo().getPath().equals("/auth/logout"))
            flag=1;//want to logout
        String param=null;
        String path=requestContext.getUriInfo().getPath();
        if (path.matches("/profile/(name|password|email)")) {
            if (path.equals("/profile/name")) flag = 2;//want to change name
            if (path.equals("/profile/password")) flag = 3; //want to change password
            if (path.equals("/profile/email")) flag = 4; //want to change email
            Pattern p = Pattern.compile("^.+<<<(\\w+)=(\\w+)>>>.+$", Pattern.DOTALL);
            String req = requestContext.getEntityStream().toString();
            Matcher m = p.matcher(req);
            if (m.matches()) {
                if (!m.group(1).matches("(name|password|email)")){
                    throw new NotFoundException("param not found");
                }
                param = m.group(2);
            } else throw new NotFoundException("param not found");
        }
        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();
        try {
            // Validate the token
            data.validateToken(token, flag, param);
        }catch (IllegalArgumentException e){
            requestContext.abortWith(
                    Response.status(Response.Status.CONFLICT).build());
        }
        catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
