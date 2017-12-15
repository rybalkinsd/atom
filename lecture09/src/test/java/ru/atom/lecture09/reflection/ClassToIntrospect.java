package ru.atom.lecture09.reflection;

@CompileTimeAnnotation
@RuntimeAnnotation
public class ClassToIntrospect<T> {
    private static final String STATIC_FIELD = "STATIC_FIELD value";

    private final String stringField = "stringField value";
    int intField = 42;

    public ClassToIntrospect() {
    }

    public ClassToIntrospect(int intField) {
        this.intField = intField;
    }

    private void privateMethod() {
    }

    public T genericMethod() {
        return null;
    }

    public void methodWithException() throws Exception {
    }

    public static final class StaticInnerClass {
    }

    public final class InnerClass {
    }
}
