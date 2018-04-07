public class Player {
    private final long rating;
    private final String name;

    public Player(String name) {
        this.rating = 1000;
        this.name = name;
    }

    public long getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

}
