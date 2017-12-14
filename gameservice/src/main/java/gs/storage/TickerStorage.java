package gs.storage;

import gs.model.GameSession;
import gs.ticker.Action;
import gs.ticker.Ticker;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TickerStorage {
    private static ConcurrentHashMap<GameSession, Ticker> tickers = new ConcurrentHashMap<GameSession, Ticker>();

    public static void putTicker(Ticker ticker, GameSession session) {
        tickers.put(session, ticker);
    }

    public static void putAction(GameSession session, Action action) {
        tickers.get(session).putAction(action);
    }

    public static Ticker getTickerByGameSession(GameSession session) {
        return tickers.get(session);
    }
}
