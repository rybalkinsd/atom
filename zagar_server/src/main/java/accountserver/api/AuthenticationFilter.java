package accountserver.api;

import org.jetbrains.annotations.NotNull;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Authorized
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {
  @Override
  public void filter(@NotNull ContainerRequestContext requestContext) throws IOException {
    String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      throw new NotAuthorizedException("Authorization header must be provided");
    }
    String token = authorizationHeader.substring("Bearer".length()).trim();
    if (!validateToken(token)) {
      requestContext.abortWith(
          Response.status(Response.Status.UNAUTHORIZED).build());
    }
  }

  private boolean validateToken(@NotNull String token) {
    return AuthenticationServlet.validateToken(token);
  }
}