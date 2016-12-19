package accountserver.model.mixins;

import accountserver.model.data.UserProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by eugene on 10/18/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public interface TokenMixin {
    @JsonProperty("token") String toString();
    @JsonIgnore UserProfile getUser();
}
