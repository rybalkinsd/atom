package atom.game;

public class BullsAndCowsException extends Exception {
    public BullsAndCowsException() {
        
    }

    public BullsAndCowsException(String message) {
        super("BullsAndCowsException: " + message);
    }   

    public BullsAndCowsException(Throwable e) {
        initCause(e);
    }
}
