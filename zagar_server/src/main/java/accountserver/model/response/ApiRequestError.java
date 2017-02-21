package accountserver.model.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by eugene on 10/13/16.
 */

@SuppressWarnings("DefaultFileTemplate")
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE
)
public class ApiRequestError extends Exception{
    private Integer code;

    @JsonProperty("code")
    public Integer getCode() {
        return code;
    }

    @JsonProperty("reason")
    public String getReason(){
        return getMessage();
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @JsonCreator
    public ApiRequestError(
            @JsonProperty("reason") String message,
            @JsonProperty("code") Integer code
    ) {
        super(message);
        this.code = code;
    }

    public ApiRequestError(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public ApiRequestError(Throwable cause, Integer code) {
        super(cause);
        this.code = code;
    }
}
