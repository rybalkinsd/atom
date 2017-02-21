package model.units;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/*
 * Created by Rail on 10.10.2016.
 */
/**
 * Class represents bush unit on game field
 *
 */
public class UnitBush extends Unit{
    public static final int BUSH_SIZE = 40;
    @NotNull
    protected static final Logger log = LogManager.getLogger(UnitBush.class);

    UnitBush(int x, int y){
        super(x, y, BUSH_SIZE);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "UnitBush{" +
                    "x='" + x + '\'' +
                    "y='" + y + '\'' +
                    "size='" + BUSH_SIZE + '\'' +
                '}';
    }
}
