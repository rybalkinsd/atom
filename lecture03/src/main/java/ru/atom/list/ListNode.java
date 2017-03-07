package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
//public class ListNode<E> extends CustomLinkedList<E> {
public class ListNode<E> {

    public E item;
    public ListNode<E> prev;
    public ListNode<E> next;

    public ListNode(ListNode<E> prev, E item, ListNode<E> next) {
        this.item = item;
        this.prev = prev;
        this.next = next;
    }
}
