package utils.json;

import com.google.gson.JsonSyntaxException;
import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 *
 * Throws if json deserialization was unsuccessful
 */
public class JSONDeserializationException extends Exception {
    JSONDeserializationException(@NotNull JsonSyntaxException cause) {
        super(cause);
    }
}
