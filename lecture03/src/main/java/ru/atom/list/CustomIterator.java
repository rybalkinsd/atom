package ru.atom.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomIterator<E> implements Iterator<E> {
    private ListNode<E> current;

    public CustomIterator(ListNode<E> current) {
        this.current = current;
    }

    @Override
    public boolean hasNext() {
        return current.getNext() != null;
    }

    @Override
    public E next() {
        current = current.getNext();
        if (current == null)
            throw new NoSuchElementException();
        return current.getObject();
    }

}
