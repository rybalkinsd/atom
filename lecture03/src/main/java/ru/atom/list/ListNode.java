package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    ListNode<E> next;
    ListNode<E> prev;
    E value;

    public ListNode(ListNode<E> next, ListNode<E> prev, E value) {
        this.next = next;
        this.prev = prev;
        this.value = value;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public ListNode<E> getPrev() {
        return prev;
    }

    public E getValue() {
        return value;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }

    public void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }

    public void setValue(E value) {
        this.value = value;
    }
}
