package model;

/**
 * @author apomosov
 */
public class Virus extends Cell {
    public Virus(Location location) {
        super(location, GameConstants.VIRUS_MASS);
    }
}
