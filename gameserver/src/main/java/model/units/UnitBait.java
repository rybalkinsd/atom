package model.units;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/*
 * Created by Rail on 10.10.2016.
 */
/**
 * Class represents bait unit on game field (when player press 'W')
 *
 */
class UnitBait extends Unit{
    @NotNull
    protected static final Logger log = LogManager.getLogger(Unit.class);
    public static final int BAIT_SIZE=5;

    UnitBait(int x, int y){
        super(x, y, BAIT_SIZE);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "UnitBait{" +
                "x='" + x + '\'' +
                "y='" + y + '\'' +
                '}';
    }
}
