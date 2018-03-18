package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private E data;
    private ListNode<E> next;
    private ListNode<E> prev;

    public ListNode() {
        data = null;
        next = this;
        prev = this;
    }

    public ListNode(E data, ListNode<E> next, ListNode<E> prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    public E getData() {
        return data;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public ListNode<E> getPrev() {
        return prev;
    }

    public void setData(E data) {
        this.data = data;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }

    public void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }
}
