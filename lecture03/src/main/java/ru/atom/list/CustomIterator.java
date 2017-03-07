package ru.atom.list;

import java.util.Iterator;

public class CustomIterator<E> implements Iterator<E> {
    private ListNode<E> node;

    public CustomIterator(ListNode<E> node) {
        this.node = node;
    }

    @Override
    public boolean hasNext() {
        return node != null;
    }

    @Override
    public E next() {
        E result = node.getValue();
        node = node.getNext();
        return result;
    }

}