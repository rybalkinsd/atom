package ru.atom.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListIterator<E> implements Iterator<E> {
    private ListNode<E> lastNode;

    public LinkedListIterator(ListNode<E> head) {
        this.lastNode = head;
    }

    @Override
    public boolean hasNext() {
        return lastNode != null;
    }

    @Override
    public E next() {
        if (!hasNext()) throw new NoSuchElementException();
        E value = lastNode.getItem();
        lastNode = lastNode.getNext();
        return value;
    }
}
