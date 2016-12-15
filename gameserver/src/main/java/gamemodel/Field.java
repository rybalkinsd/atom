package gamemodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Field {

    public static final int BORDER_DOWN = 0;
    public static final int BORDER_TOP = 20000;
    public static final int BORDER_LEFT = 0;
    public static final int BORDER_RIGHT = 20000;

    private static final Logger LOG = LogManager.getLogger(Field.class);

    @NotNull
    private List<Food> foods;

    @NotNull
    private List<Virus> viruses;

    @NotNull
    private List<Blob> blobs = new ArrayList<>();

    public Field(@NotNull List<Food> foods, @NotNull List<Virus> viruses) {
        this.foods = foods;
        this.viruses = viruses;
        if (LOG.isInfoEnabled()) {
            LOG.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "Field{" +
                "foods=" + foods +
                ", viruses=" + viruses +
                ", blobs=" + blobs +
                '}';
    }
}
