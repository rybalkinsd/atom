package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {

    public
        E value;
    ListNode<E> next;
    ListNode<E> prev;


    ListNode() {
        value = null;
        next = null;
        prev = null;
    }


    ListNode(E val, ListNode<E> nxt, ListNode<E> prv) {
        value = val;
        next = nxt;
        prev = prv;
    }
}
