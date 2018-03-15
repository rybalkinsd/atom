package ru.atom.files;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.atom.exception.ExceptionHandler;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class ResourceReadTest {
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    @Test
    public void testReadResource() throws Exception {
        List<String> lines = ResourceReader.readFromResource("FileToRead.txt");
        lines.stream()
                .filter(s -> !s.isEmpty())
                .map(String::trim)
                .forEach(System.out::println);
        assertEquals(2, lines.size());
    }
}
