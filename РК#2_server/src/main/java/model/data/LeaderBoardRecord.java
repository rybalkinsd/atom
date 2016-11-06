package model.data;

/**
 * Created by svuatoslav on 11/6/16.
 */
public class LeaderBoardRecord {
    private String user;
    int score;

    public LeaderBoardRecord(String user, int score) {
        this.user = user;
        this.score = score;
    }

    public String getUser() {
        return user;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "LeaderBoardRecord{" +
                "user=" + user +
                ", score=" + score +
                '}';
    }
}