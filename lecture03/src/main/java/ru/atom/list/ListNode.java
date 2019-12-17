package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    E value;
    ListNode<E> prev;
    ListNode<E> next;

    public ListNode(E value, ListNode<E> prev, ListNode<E> next) {
        this.value = value;
        this.prev = prev;
        this.next = next;
    }
}
