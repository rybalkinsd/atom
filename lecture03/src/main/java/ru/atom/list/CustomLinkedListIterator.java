package ru.atom.list;

import java.util.Iterator;

/**
 * Created by gammaker on 04.03.2017.
 */
public class CustomLinkedListIterator<E> implements Iterator<E> {
    private ListNode<E> node;

    public CustomLinkedListIterator(ListNode<E> node) {
        this.node = node;
    }

    @Override public boolean hasNext() {
        return node != null;
    }

    @Override public E next() {
        E result = node.element;
        node = node.next;
        return result;
    }
}
