package ru.atom.list;

import java.util.Iterator;

/**
 * Created by Vlad on 05.03.2017.
 */
public class CustomIterator<E> implements Iterator<E>{
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
        E value = node.getValue();

        node = node.getNext();

        return value;
    }
}
