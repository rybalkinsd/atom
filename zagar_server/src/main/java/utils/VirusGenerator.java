package utils;

import model.Virus;
import ticker.Tickable;

/**
 * @author apomosov
 */
public interface VirusGenerator extends Tickable {
    Virus generate();
}
