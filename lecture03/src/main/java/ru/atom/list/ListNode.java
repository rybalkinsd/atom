package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {

    E item;
    ListNode<E> next;
    ListNode<E> prev;

    ListNode(ListNode<E> prev, E element, ListNode<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
