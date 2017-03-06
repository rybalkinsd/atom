package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {

    private E value;
    private ListNode<E> prev = null;
    private ListNode<E> next = null;

    public ListNode(E value) {
        this.value = value;
    }

    public ListNode(E value, ListNode<E> prev) {
        this.value = value;
        this.prev = prev;
    }

    public E getValue() {
        return value;
    }

    public ListNode<E> getPrev() {
        return prev;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }
}
