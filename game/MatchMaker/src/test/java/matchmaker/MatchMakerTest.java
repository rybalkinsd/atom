package matchmaker;

import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

public class MatchMakerTest {


    @Test
    public void matchMakerTest(){
        Collection<Thread> list = new LinkedList<>();
        for (int i = 0;i < 4;i++) {
            list.add(new Thread(new TestClient()));
        }
        for (Thread thread:list)
            thread.start();
    }

}
