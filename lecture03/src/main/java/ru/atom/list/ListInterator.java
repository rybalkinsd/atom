
package ru.atom.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListInterator<E> implements Iterator<E> {

    ListNode<E> next;

    ListInterator(ListNode first) {
        next = new ListNode<E>(null, null, first);
    }

    @Override
    public boolean hasNext() {
        return (next.next != null);
    }

    @Override
    public E next() {
        if (!hasNext()) throw new NoSuchElementException();

        next = next.next;
        return next.value;
    }

}

