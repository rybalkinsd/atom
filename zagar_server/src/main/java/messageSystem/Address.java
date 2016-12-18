package messageSystem;

import org.jetbrains.annotations.NotNull;

/**
 * Created by User on 26.11.2016.
 */
public class Address {

    @NotNull private final String name;

    public Address(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return name;
    }
}
