package ru.atom.list;

import java.util.List;

/**
 * Contains ref to next node, prev node and value
 */


public class ListNode<E> {
    private E item;
    private ListNode<E> next;
    private ListNode<E> prev;

    public E getItem() {
        return item;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public ListNode<E> getPrev() {
        return prev;
    }

    public void setItem(E item) {
        this.item = item;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }

    public void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }

    ListNode(ListNode<E> prev, E element, ListNode<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
