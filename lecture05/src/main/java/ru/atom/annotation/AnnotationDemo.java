package ru.atom.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by sergey on 3/15/17.
 */
public class AnnotationDemo {
    public static int getNumberOfAnnotatedMethods(Class clazz, Class<? extends Annotation> annotation) {
        Method[] methods = clazz.getMethods();
        int sum = 0;
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation))
                sum++;
        }
        return sum;
    }
}
