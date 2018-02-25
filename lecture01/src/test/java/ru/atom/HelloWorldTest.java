package ru.atom;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class HelloWorldTest {
    @Test
    public void getHelloWorld() throws Exception {
        assertEquals("Hello, World!", HelloWorld.getHelloWorld());
        assertFalse("Some strange string".equals(HelloWorld.getHelloWorld()));
    }
}