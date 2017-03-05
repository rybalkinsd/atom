package ru.atom.list;

import java.util.Iterator;

/**
 * Created by Western-Co on 05.03.2017.
 */
public class CustomIterator<E> implements Iterator<E> {
    private ListNode<E> current;

    public CustomIterator(ListNode<E> current) {
        this.current = current;
    }

    @Override
    public boolean hasNext() {
        if (current != null) {
            return true;
        }
        return false;
    }

    @Override
    public E next() {
        E value = current.getElement();
        current = current.getNext();
        return value;
    }
}
