package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {

    private final E value;

    private ListNode<E> prev;
    private ListNode<E> next;

    public void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }

    public ListNode<E> getPrev() {
        return prev;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public ListNode(E value) {
        this.value = value;
        prev = null;
        next = null;
    }

    public E getValue() {
        return value;
    }

    public boolean hasNext() {
        return next != null;
    }


    public boolean hasPrev() {
        return prev != null;
    }
}
