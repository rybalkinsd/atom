package ru.atom.lecture11.sharedmutablestate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alpi
 * @since 22.11.16
 */
public class FindAllSharedMutableState {
    private static int privateStaticField = 1;
    public static int publicStaticField = 2;
    public int publicField = 3;
    private int privateField = 4;
    private NestedClass ref = new NestedClass();
    private List<NestedClass> list = new ArrayList<>();

    public String method() {
        FindAllSharedMutableState var = new FindAllSharedMutableState();
        return var.toString();
    }

    public int getPrivateField() {
        return privateField;
    }

    public static void main(String[] args) {
        new FindAllSharedMutableState();
    }

    public List<NestedClass> getList() {
        return list;
    }

    public static class NestedClass {
        private int privateField = 4;

        public int getPrivateField() {
            return privateField;
        }
    }
}
