package ru.atom.annotation;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.assertj.core.api.Assertions.assertThat;


public class AnnotationDemoTest {

    @Test
    public void countOverride() throws Exception {
        assertThat(AnnotationDemo.getNumberOfAnnotatedMethods(
                ru.atom.boot.mm.ConnectionController.class, Override.class)).isEqualTo(0);
    }

    @Test
    public void countRequestMapping() throws Exception {
        assertThat(AnnotationDemo.getNumberOfAnnotatedMethods(
                ru.atom.boot.hw.HelloController.class, RequestMapping.class)).isEqualTo(1);
    }

    @Test
    public void countResponseStatus() throws Exception {
        assertThat(AnnotationDemo.getNumberOfAnnotatedMethods(
                ru.atom.boot.mm.ConnectionController.class, ResponseStatus.class)).isEqualTo(1);
    }
}
