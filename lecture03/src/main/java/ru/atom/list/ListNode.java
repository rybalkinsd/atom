package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    E object;
    ListNode<E> next = null;
    ListNode<E> prev = null;

    ListNode(E object) {
        this.object = object;
    }
}
