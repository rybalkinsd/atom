package ru.atom.exception;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ExceptionHandlerTest {

    @Test
    public void testSimpleHandle() throws Exception {
        ExceptionHandler.simpleHandle();
    }

    @Test
    public void testExceptionThrower() throws Exception {
        try {
            ExceptionHandler.exceptionThrower();
        } catch (Exception ex) {
            assertEquals("BaseException: SubException: Handled NPE", ex.getMessage());
        }
    }

    @Test
    public void testMultipleCatch() throws Exception {
        assertEquals("BaseException: SubException: testMultipleCatch",
                ExceptionHandler.multipleCatch(new SubException("testMultipleCatch")));

        assertEquals("BaseException: testMultipleCatch",
                ExceptionHandler.multipleCatch(new BaseException("testMultipleCatch")));

        // FileNotFoundException extends Exception
        assertEquals("File not found: testMultipleCatch",
                ExceptionHandler.multipleCatch(new FileNotFoundException("File not found: testMultipleCatch")));
        // OutOfMemoryError !extends Exception
        assertEquals("Out of memory: testMultipleCatch",
                ExceptionHandler.multipleCatch(new OutOfMemoryError("Out of memory: testMultipleCatch")));
    }

    @Test
    public void testReadOneLineFromFile() throws Exception {
        {
            String line = ExceptionHandler.readOneLineFromFile("/FileToRead");
            // File not found
            assertNull(line);
        }

        {
            String line = ExceptionHandler.readOneLineFromFile(
                    this.getClass().getResource("/FileToRead.txt").getPath());
            assertEquals("First line in FileToRead.txt", line);
        }
    }

    @Test
    public void testReadOneLineFromFile_TheNewWay() throws Exception {
        {
            String line = ExceptionHandler.readOneLineFromFile_TheNewWay("/FileToRead");
            // File not found
            assertNull(line);
        }

        {
            String line = ExceptionHandler.readOneLineFromFile_TheNewWay(
                    this.getClass().getResource("/FileToRead.txt").getPath());
            assertEquals("First line in FileToRead.txt", line);
        }
    }
}