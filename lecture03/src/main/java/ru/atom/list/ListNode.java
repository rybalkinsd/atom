package ru.atom.list;

import java.util.LinkedList;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    E element;
    ListNode<E> next;
    ListNode<E> prev;

    public ListNode(E element, ListNode<E> next, ListNode<E> prev) {
        this.next = next;
        this.prev = prev;
        this.element = element;
    }






}
