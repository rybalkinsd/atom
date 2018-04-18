package matchmaker;


import java.util.UUID;

class StringGenerator {
    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}