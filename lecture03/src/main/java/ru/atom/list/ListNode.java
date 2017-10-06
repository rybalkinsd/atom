package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    public final E element;
    ListNode<E> next; //default
    ListNode<E> prev; //default

    ListNode(E element) {
        this.element = element;
        next = prev = null;
    }
}
