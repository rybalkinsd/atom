package accountserver.model.mixins;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by eugene on 10/18/16.
 */

@SuppressWarnings("DefaultFileTemplate")
public interface UserProfileMixin {

    @JsonIgnore String getPassword();
    @JsonIgnore Long getId();
    @JsonIgnore Long getVersion();
}
