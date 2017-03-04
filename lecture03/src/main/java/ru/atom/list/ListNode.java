package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    public ListNode<E> next;
    public ListNode<E> prev;
    public E element;

    public ListNode() {
        this.next = null;
        this.prev = null;
    }
}
