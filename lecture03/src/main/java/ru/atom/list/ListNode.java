package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    public ListNode<E> next;
    public ListNode<E> prev;
    public E value;

    ListNode(){
        next = null;
        prev = null;
        value = null;
    }
}
