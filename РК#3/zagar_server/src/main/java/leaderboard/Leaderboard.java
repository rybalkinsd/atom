package leaderboard;

import main.Service;
import ticker.Tickable;

/**
 * Created by svuatoslav on 11/28/16.
 */
public abstract class Leaderboard extends Service{
    Leaderboard() {super("leaderboard");}
    public abstract void update();

    @Override
    public Class<? extends Service> getServiceClass()
    {
        return Leaderboard.class;
    }
}
