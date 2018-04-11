package playerdb;

public class Player {
    private long rating;
    private final String name;

    public Player(String name) {
        this.rating = 1000;
        this.name = name;
    }

    public Player(String name, long num) {
        this.rating = num;
        this.name = name;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long num) {
        this.rating = num;
    }

    public String getName() {
        return name;
    }

}
