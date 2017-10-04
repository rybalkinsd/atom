package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    public ListNode() {
        this.value = null;
        this.next  = null;
        this.prev  = null;
    }

    public ListNode(E value, ListNode<E> prev, ListNode<E> next) {
        this.value = value;
        this.next  = next;
        this.prev  = prev;
    }

    public E getValue() {
        return value;
    }

    public ListNode<E> next() {
        return next;
    }

    public ListNode<E> prev() {
        return prev;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public void setNext(ListNode<E> node) {
        next = node;
    }

    public void setPrev(ListNode<E> node) {
        prev = node;
    }

    private ListNode<E> next;
    private ListNode<E> prev;
    private E           value;
}
