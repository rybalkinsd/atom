package protocol.commands;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.NotNull;

/**
 * Created by xakep666 on 16.11.16.
 * <p>
 * Command from client to server with client window size
 */
public final class CommandWindowSize extends Command {
    @NotNull
    public static final String NAME = "windowsize";
    @Expose
    private int width;
    @Expose
    private int height;

    public CommandWindowSize(int width, int height) {
        super(NAME);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
