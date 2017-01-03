package ru.atom;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by sergei-r on 03.01.17.
 */
public class HWTest {
    @Test
    public void get() throws Exception {
        assertEquals("get", HW.get());
    }

}