package accountserver.model.response.ApiErrors;

import accountserver.model.response.ApiRequestError;

/**
 * Created by eugene on 10/25/16.
 */
public class PolicyViolationError extends ApiRequestError {
    public PolicyViolationError() {
        super("Credentials policy violation", 3);
    }
}
