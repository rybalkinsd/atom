package leaderboard.model;

/**
 * Created by eugene on 12/16/16.
 */
public class Score {
    private int playerId;
    private int score;


    public Score(int playerId, int score) {
        this.playerId = playerId;
        this.score = score;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getScore() {
        return score;
    }
}
