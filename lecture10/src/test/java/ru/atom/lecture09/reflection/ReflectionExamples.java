package ru.atom.lecture09.reflection;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReflectionExamples {
    @Test
    public void introspection() {
        ClassToIntrospect classToIntrospect = new ClassToIntrospect();
        Class<? extends ClassToIntrospect> classObject = classToIntrospect.getClass();
        System.out.println("Class:\n" + classObject);
        System.out.println("ClassLoader:\n" + classObject.getClassLoader());
        System.out.println("Declared Constructors:\n" + Arrays.toString(classObject.getDeclaredConstructors()));
        System.out.println("Declared Fields:\n" + Arrays.toString(classObject.getDeclaredFields()));
        System.out.println("Declared Methods:\n" + Arrays.toString(classObject.getDeclaredMethods()));
        System.out.println("Declared Annotations:\n" + Arrays.toString(classObject.getDeclaredAnnotations()));
        //TODO where is CompileTimeAnnotation?
        //TODO are there any Generic information?
    }

    @Test
    public void changePrivateFinalField()
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        //Get Class by name
        ClassWithPrivateFinalField obj =
                (ClassWithPrivateFinalField) Class.forName("ru.atom.lecture09.reflection.ClassWithPrivateFinalField")
                //Instantiate ClassWithPrivateFinalField via ru.atom.lecture09.reflection
                .newInstance();

        assertNotNull(obj);
        assertEquals("This is my final state", obj.getPrivateFinalField());

        //Look at declared methods
        Field privateFinalField = obj.getClass().getDeclaredField("privateFinalField");

        //To access private final field we simply must call Field.setAccessible();
        privateFinalField.setAccessible(true);
        privateFinalField.set(obj, "Changed value!");

        assertEquals("Changed value!", obj.getPrivateFinalField());
    }
}
