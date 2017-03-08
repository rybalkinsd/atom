package ru.atom.list;


/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    E element = null;
    ListNode<E> prev = null;
    ListNode<E> next = null;

    ListNode(E e, ListNode prev, ListNode next) {
        element = e;
        this.prev = prev;
        this.next = next;
    }
}
