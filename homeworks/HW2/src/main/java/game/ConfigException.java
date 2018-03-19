package atom.game;

class ConfigException extends BullsAndCowsException {
    public ConfigException() {
        
    }

    public ConfigException(String message) {
        super("ConfigException: " + message);
    }

    public ConfigException(Throwable e) {
        initCause(e);
    }
}
