package ru.atom.samples.generics;

import java.util.ArrayList;
import java.util.List;

public class StackImpl<T> implements Stack<T> {

    private List<T> elements = new ArrayList<>();

    @Override
    public void push(T elem) {
        elements.add(elem);
    }

    /**
     * if stack is empty throw NoSuchElementException.
     * */
    @Override
    public T pop() {
        // your code here
        return null;
    }

    /**
     * @return null if stack is empty
     * */
    @Override
    public T peek() {
        // your code here
        return null;
    }
}