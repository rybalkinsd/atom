package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private ListNode<E> next = null;
    private ListNode<E> prev = null;
    private E object;

    public void setObject(E object) {
        this.object = object;
    }

    public E getObject() {
        return object;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    public ListNode getNext() {
        return next;
    }

    public void setPrev(ListNode prev) {
        this.prev = prev;
    }

    public ListNode getPrev() {
        return prev;
    }
}
