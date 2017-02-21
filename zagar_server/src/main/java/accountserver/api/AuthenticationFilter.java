package accountserver.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
  private static final Logger log = LogManager.getLogger(AuthenticationFilter.class);
  @Override
  public void filter(@NotNull ContainerRequestContext requestContext) throws IOException {

    // Get the HTTP Authorization header from the request
    String authorizationHeader =
        requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

    // Check if the HTTP Authorization header is present and formatted correctly
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      throw new NotAuthorizedException("Authorization header must be provided");
    }

    // Extract the token from the HTTP Authorization header
    Token token = new Token(authorizationHeader);

    try {
      // Validate the token
      validateToken(token);
      log.info("Validate token passed");
    } catch (Exception e) {
      requestContext.abortWith(
              Response.status(Response.Status.UNAUTHORIZED).build());
      log.info("Not valid token" );
    }
  }

  private void validateToken(Token token) throws Exception {
    TokenContainer.validateToken(token);
  }
}