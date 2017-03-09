package ru.atom.list;

import java.util.Iterator;

/**
 * Created by mac on 07.03.17.
 */

public class CustomLinkedListIterator<E> implements Iterator<E> {

    private ListNode<E> node;

    public CustomLinkedListIterator(ListNode<E> node) {
        this.node = node;
    }

    @Override
    public boolean hasNext() {
        return node != null;
    }

    @Override
    public E next() {
        E value = node.getValue();
        node = node.getNext();
        return value;
    }
}