package accountserver.database;

/**
 * Created by eugene on 11/7/16.
 */
class DbException extends RuntimeException {
    DbException(Throwable cause) {
        super(cause);
    }
}
