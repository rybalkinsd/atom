package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {

    private final E tmp;

    private ListNode<E> next;
    private ListNode<E> prev;

    public E getTmp() {
        return tmp;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public ListNode<E> getPrev() {
        return prev;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }

    public void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }

    public ListNode(E tmp) {

        this.tmp = tmp;
        next = null;
        prev = null;
    }
}
