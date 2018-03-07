package ru.atom.list;

import java.util.Iterator;

/**
 * Created by User on 06.03.2018.
 * sphere
 */
public class Iter<E> implements Iterator<E> {
    private ListNode<E> curr;
    private ListNode<E> head;
    private int index = -1;

    public Iter(ListNode<E> head) {
        this.head = head;
        this.curr = head.getPrev();
    }

    @Override
    public boolean hasNext() {
        if (curr == null) {
            return false;
        }
        if (curr.getNext() == head && index != -1) {
            return false;
        }
        return true;
    }

    @Override
    public E next() {
        if (curr == null || !hasNext()) {
            return null;
        }
        index += 1;
        //E res = curr.getValue();
        curr = curr.getNext();
        return curr.getValue();
    }

    public E getValue() {
        return curr.getValue();
    }
}
