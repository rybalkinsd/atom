package ru.atom.list;

import java.util.Iterator;

public class CustomIterator<E> implements Iterator<E> {
    private ListNode<E> currentElement;

    public CustomIterator(ListNode<E> e) {
        this.currentElement = e;
    }

    @Override
    public boolean hasNext() {
        return (currentElement.getNext() != null);
    }

    @Override
    public E next() {
        currentElement = currentElement.getNext();
        return currentElement.getElement();
    }

}
