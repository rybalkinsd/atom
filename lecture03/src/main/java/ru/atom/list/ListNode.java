package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private E object;
    private ListNode<E> next;
    private ListNode<E> prev;

    public ListNode(E object) {
        this.object = object;
    }

    public ListNode(E object, ListNode<E> prev, ListNode<E> next) {
        this.object = object;
        this.prev = prev;
        this.next = next;
        this.next.prev = this;
        this.prev.next = this;
    }

    public ListNode<E> next() {
        return this.next;
    }

    public ListNode prev() {
        return this.prev;
    }

    public E getObject() {
        return this.object;
    }

    public void setObject(E object) {
        this.object = object;
    }

    public void setNext(ListNode<E> nextList) {
        this.next = nextList;
    }

    public void setPrev(ListNode<E> prevList) {
        this.prev = prevList;
    }
}
