package ru.atom.message.example2;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class MessageTest {
    @Test
    public void saveTo() throws Exception {
        Storable smthToSave = new Message("Perfect content");
        smthToSave.saveTo(new File("path to file"));

        assertThat(smthToSave, is(instanceOf(Message.class))); // <-- OK
        assertThat(smthToSave, is(instanceOf(Storable.class))); // <-- OK
    }

}