package ru.atom.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomIterator<E> implements Iterator<E> {
    private ListNode<E> element;

    public CustomIterator(ListNode<E> element) {
        this.element = new ListNode<E>(null, null, element);
    }

    @Override
    public boolean hasNext() {
        return element.getNext() != null;
    }

    @Override
    public E next() {
        if (element.getNext() == null) {
            throw new NoSuchElementException();
        }
        element = element.getNext();
        return element.getValue();
    }
}
