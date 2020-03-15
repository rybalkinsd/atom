package ru.atom;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class HelloWorldTest {
    @Test
    public void getHelloWorld() {
        assertEquals("Hello, World!", HelloWorld.getHelloWorld());
    }
}