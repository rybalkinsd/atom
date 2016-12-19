package accountserver.database;

/**
 * Created by eugene on 10/22/16.
 */
class TransactionalError extends Exception {
    public TransactionalError(String message) {
        super(message);
    }

    public TransactionalError(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionalError(RuntimeException e) {
        super(e);
    }
}
