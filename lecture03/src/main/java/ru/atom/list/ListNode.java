package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    ListNode<E> next;
    ListNode<E> prev;
    E value;

    ListNode() {
        next = null;
        prev = null;
        value = null;
    }
}
