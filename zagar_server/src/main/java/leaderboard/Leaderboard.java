package leaderboard;

import main.Service;

/**
 * Created by svuatoslav on 11/28/16.
 */
public abstract class Leaderboard extends Service{
    Leaderboard() {
        super("leaderboard");
        try {
            Class.forName("leaderboard.LeaderboardState");
        } catch (ClassNotFoundException ignored) {}
    }

    public abstract void update();

    @Override
    public Class<? extends Service> getServiceClass()
    {
        return Leaderboard.class;
    }
}
