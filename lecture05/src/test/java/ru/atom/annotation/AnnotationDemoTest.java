package ru.atom.annotation;

import org.junit.Test;
import org.springframework.web.bind.annotation.ResponseStatus;

import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;


public class AnnotationDemoTest {

    @Test
    public void countOverride() throws Exception {
        assertEquals(0, AnnotationDemo.getNumberOfAnnotatedMethods(
                ru.atom.boot.mm.ConnectionController.class, Override.class));
    }

    @Test
    public void countResponseStatus() throws Exception {
        assertEquals(1, AnnotationDemo.getNumberOfAnnotatedMethods(
                ru.atom.boot.mm.ConnectionController.class, ResponseStatus.class));
    }
}
