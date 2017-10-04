package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    public void setNext(ListNode<E> next) {
        this.next = next;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }

    public ListNode(E element) {
        this.element = element;
        this.prev = this;
        this.next = this;
    }

    public ListNode(E element, ListNode<E> next, ListNode<E> prev) {
        this.element = element;
        this.next = next;
        this.prev = prev;
    }

    public E getElement() {
        return element;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public ListNode<E> getPrev() {
        return prev;
    }

    private E element;

    private ListNode<E> next;

    private ListNode<E> prev;
}
