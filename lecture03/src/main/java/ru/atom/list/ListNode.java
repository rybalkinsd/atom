package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private E object;
    private ListNode<E> next;
    private ListNode<E> prev;

    ListNode(ListNode<E> prev, E object, ListNode<E> next) {
        this.object = object;
        this.prev = prev;
        this.next = next;
    }

    public E getObject() {
        return object;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public ListNode<E> getPrev() {
        return prev;
    }

    public void setObject(E object) {
        this.object = object;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }

    public void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }
}
