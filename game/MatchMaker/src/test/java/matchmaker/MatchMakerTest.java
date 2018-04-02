package matchmaker;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

public class MatchMakerTest {


    @Test
    public void matchMakerTest() throws InterruptedException{
        Collection<Thread> list = new LinkedList<>();
        for (int i = 0;i < 12;i++) {
            list.add(new Thread(new TestClient()));
        }
        for (Thread thread:list)
            thread.start();
        for (Thread thread:list)
            thread.join();
    }

    @Test
    @Ignore
    public void matchMakerIncompleteSessionTest() throws InterruptedException{
        Collection<Thread> list = new LinkedList<>();
        for (int i = 0;i < 7;i++) {
            list.add(new Thread(new TestClient()));
        }
        for (Thread thread:list)
            thread.start();
        for (Thread thread:list)
            thread.join();
    }


}
