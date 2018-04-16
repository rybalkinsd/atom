package matchmaker.monitoring;

/**
 * Created by imakarycheva on 08.04.18.
 */
public class QueueState {
    private final int upperRange;
    private final int playersInQueue;

    public QueueState(int upperRange, int playersInQueue) {
        this.upperRange = upperRange;
        this.playersInQueue = playersInQueue;
    }

    public int getUpperRange() {
        return upperRange;
    }

    public int getPlayersInQueue() {
        return playersInQueue;
    }
}
