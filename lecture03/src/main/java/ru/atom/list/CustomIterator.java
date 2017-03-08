package ru.atom.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

class CustomIterator<E> implements Iterator<E> {
    ListNode<E> next;

    CustomIterator(ListNode first) {
        next = new ListNode<E>(null, null, first);
    }

    @Override
    public boolean hasNext() {
        return next.next != null;
    }

    @Override
    public E next() {
        if (!hasNext())
            throw new NoSuchElementException();

        next = next.next;
        return next.item;
    }
}
