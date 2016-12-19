package accountserver.model.response.ApiErrors;

import accountserver.model.response.ApiRequestError;

/**
 * Created by eugene on 11/5/16.
 */
public class WrongFieldError extends ApiRequestError {
    public WrongFieldError(String field) {
        super(String.format("Field '%s' doesn't exists", field), 15);
    }
}
