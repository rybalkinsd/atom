package ru.atom.list;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
public class CustomLinkedListIterator<E> implements Iterator<E> {
    private ListNode<E> node;

    public CustomLinkedListIterator(ListNode<E> node) {
        this.node = node;
    }

    @Override
    public boolean hasNext() {
        return node.getNext() != null;
    }

    @Override
    public E next() {
        E nextElement = node.getElement();
        node = node.getNext();
        return nextElement;
    }
}
