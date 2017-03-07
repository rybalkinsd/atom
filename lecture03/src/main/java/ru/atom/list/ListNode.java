package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private ListNode next = null;
    private ListNode prev = null;
    private E value = null;

    public void setNext(ListNode node) {
        this.next = node;
    }

    public void setPrev(ListNode node) {
        this.prev = node;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public ListNode getNext() {
        return next;
    }

    public ListNode getPrev() {
        return prev;
    }

    public E getValue() {
        return value;
    }
}

