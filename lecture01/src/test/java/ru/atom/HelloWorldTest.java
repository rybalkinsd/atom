package ru.atom;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

public class HelloWorldTest {
    @Test
    public void getHelloWorld() throws Exception {
        assertThat(HelloWorld.getHelloWorld(), is(equalTo("Hello, World!")));
        assertThat(HelloWorld.getHelloWorld(), is(not(equalTo("Some strange string"))));
    }
}