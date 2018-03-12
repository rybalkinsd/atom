package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private ListNode next;
    private ListNode prev;
    private E element;

    public ListNode getNext() {
        return next;
    }

    public ListNode getPrev() {
        return prev;
    }

    public E getElement() {
        return element;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public void setPrev(ListNode prev) {
        this.prev = prev;
    }

    public ListNode(ListNode toCopy) {
        this.setPrev(toCopy.getPrev());
        this.setNext(toCopy.getNext());
        this.element = null;
    }

    public ListNode(E newelem, ListNode newNext, ListNode newPrev) {
        setElement(newelem);
        setNext(newNext);
        setPrev(newPrev);
    }

}
