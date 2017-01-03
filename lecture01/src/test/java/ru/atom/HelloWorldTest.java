package ru.atom;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by sergei-r on 03.01.17.
 */
public class HelloWorldTest {
    @Test
    public void getHelloWorld() throws Exception {
        assertEquals("Hello, World!", HelloWorld.getHelloWorld());
        assertNotEquals("Some strange string", HelloWorld.getHelloWorld());
    }



}