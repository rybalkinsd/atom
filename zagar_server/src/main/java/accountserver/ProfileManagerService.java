package accountserver;

import accountserver.dao.UserDAO;
import accountserver.dao.UserProfileHibernate;
import accountserver.dao.exceptions.DaoException;
import accountserver.misc.Sensitive;
import accountserver.misc.UserCanChange;
import accountserver.model.data.UserProfile;
import accountserver.model.response.ApiErrors.InternalError;
import accountserver.model.response.ApiErrors.WrongFieldError;
import accountserver.model.response.ApiRequestError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.TestOnly;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by eugene on 11/5/16.
 */
public class ProfileManagerService {
    private static List<String> mutable;
    private static List<String> sensitive;
    private static final Logger LOG = LogManager.getLogger("PROFILEMGR");

    private final UserDAO dao = new UserProfileHibernate();


    private static boolean checkField(Field field){
        return field.isAnnotationPresent(UserCanChange.class) && (
                        field.isAnnotationPresent(Basic.class) ||
                        field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).updatable()
        );
    }

    private static boolean checkSensitive(Field field){
        return field.isAnnotationPresent(Sensitive.class);
    }

    static {
        Class<UserProfile> clazz = UserProfile.class;
        mutable = Arrays.stream(clazz.getDeclaredFields())
                .filter(ProfileManagerService::checkField)
                .map(Field::getName)
                .collect(Collectors.toList());
        LOG.info("mutable fields " + mutable.toString());

        sensitive = mutable.stream().filter(s -> {
            try {
                return checkSensitive(clazz.getDeclaredField(s));
            } catch (NoSuchFieldException e) {
                return false;
            }
        }).collect(Collectors.toList());
    }

    private UserProfile profile;

    public ProfileManagerService(Long id) throws ApiRequestError {
        try {
            this.profile = dao.getById(id);
        } catch (DaoException daoException) {
            // TODO report this
            throw new InternalError();
        }

        if(null == profile){
            LOG.warn("ProfileManager initialized with invalid id:" + id.toString());
            throw new InternalError();
        }
    }

    public void update(String field, String value) throws ApiRequestError {
        if(!mutable.contains(field)){
            throw new WrongFieldError(field);
        }

        try {
            dao.update(profile.getId(), field, value);
            LOG.info(String.format("user '%d' change they '%s' to '%s'",
                    profile.getId(),
                    field,
                    sensitive.contains(field)? "****" : value)
            );
        } catch (DaoException daoException) {
            throw new InternalError();
        }
    }

    public UserProfile getProfile() {
        return profile;
    }

    @TestOnly
    public UserDAO getDao() {
        return dao;
    }
}
