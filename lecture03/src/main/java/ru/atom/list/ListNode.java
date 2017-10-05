package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    E object;
    ListNode<E> next;
    ListNode<E> prev;

    public ListNode(E object) {
        this.object = object;
        this.next = this;
        this.prev = this;
    }

    public ListNode(E object, ListNode<E> next, ListNode<E> prev) {
        this.object = object;
        this.next = next;
        this.prev = prev;
    }
}
