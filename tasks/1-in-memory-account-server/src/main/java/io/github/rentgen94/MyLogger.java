package io.github.rentgen94;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Western-Co on 27.03.2017.
 */
public class MyLogger {
    private static final Logger log = LogManager.getLogger(MyLogger.class);

    public static Logger getLog() {
        return log;
    }
}
