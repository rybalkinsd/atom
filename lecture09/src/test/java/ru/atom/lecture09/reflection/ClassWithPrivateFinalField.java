package ru.atom.lecture09.reflection;

public class ClassWithPrivateFinalField {
    private final String privateFinalField// = "This is my final state";
            ;

    /**
     * Reflection collides with optimizations
     * According to JLS 17.5.3. Subsequent Modification of final Fields
     * we can not observe changes of final fields via ru.atom.lecture09.reflection
     * if they replaced at compile time with the value of the constant expression
     * https://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html
     * So we use initializing Constructor as a workaround
     * That is, ru.atom.lecture09.reflection is a dirty technique in most cases
     */
    public ClassWithPrivateFinalField() {
        privateFinalField = "This is my final state";
    }

    public String getPrivateFinalField() {
        return privateFinalField;
    }
}
