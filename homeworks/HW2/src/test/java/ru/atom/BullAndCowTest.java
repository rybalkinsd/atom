package ru.atom;

;
import org.junit.Assert;
import org.junit.Test;



public class BullAndCowTest {
    @Test
    public void bullTest1(){
        Assert.assertEquals(BullAndCaw.getBulls("java", "atom"), 0);
    }

    @Test
    public void bullTest2(){
        Assert.assertEquals(BullAndCaw.getBulls("java", "lava"), 3);
    }

    @Test
    public void cowTest1(){
        Assert.assertEquals(BullAndCaw.getCows("java", "atom"), 1);
    }

    @Test
    public void cowTest2(){
        Assert.assertEquals(BullAndCaw.getCows("java", "lava"), 0);
    }
}
