package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by valentin on 10.10.16.
 */
public class BackGround {
    private static final Logger log = LogManager.getLogger(Player.class);
    private String someBackgroundFileName;
    /**
     * Create Background with Filename
     *
     * @param bkg          fileName
     */
    public BackGround(String bkg){
        this.someBackgroundFileName = bkg;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }
    @Override
    public String toString() {
        return "Background{ " +
                someBackgroundFileName +
                " }";
    }
}
