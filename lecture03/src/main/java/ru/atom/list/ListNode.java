package ru.atom.list;

import java.util.List;
import java.util.RandomAccess;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private ListNode<E> next = null;
    private ListNode<E> prev = null;
    E value;

    ListNode(E value)  {
        this.value = value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }

    public void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }

    public E getValue() {
        return value;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public ListNode<E> getPrev() {
        return prev;
    }
}
