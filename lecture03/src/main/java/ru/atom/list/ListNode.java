package ru.atom.list;

import java.util.LinkedList;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
//    private E object;
//    private ListNode<E> next;
//    private ListNode<E> prev;

    E item;
    ListNode<E> next;
    ListNode<E> prev;

    ListNode(ListNode<E> prev, E element, ListNode<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
