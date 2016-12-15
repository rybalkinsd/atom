package leaderboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by svuatoslav on 12/11/16.
 */
public final class LeaderboardState {
    private static final ConcurrentHashMap<String,Integer> board = new ConcurrentHashMap<>();
    private static String[] best=null;

    public static void update(String name, int score)
    {
        board.put(name,score);
        synchronized (board) {
            best = null;
        }
    }

    public static String[] get()
    {
        synchronized (board) {
            if(best==null)
            {
                List<Map.Entry<String, Integer>> list = new ArrayList<>(board.entrySet());
                list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
                int size = Integer.min(10, list.size());
                best = new String[size];
                for (int i = 0; i < size; i++) {
                    Map.Entry<String, Integer> record = list.get(i);
                    best[i] = record.getKey();
                }
            }
        }
        return best;
    }
}
