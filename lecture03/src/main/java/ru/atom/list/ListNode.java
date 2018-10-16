package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
class ListNode<E> {
    ListNode<E> next;
    ListNode<E> prev;
    E value;

    ListNode(ListNode<E> next, ListNode<E> prev, E value) {
        this.next = next;
        this.prev = prev;
        this.value = value;
    }
}
