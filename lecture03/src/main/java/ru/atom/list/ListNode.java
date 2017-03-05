package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private ListNode<E> prev;
    private E element;
    private ListNode<E> next;

    public ListNode(ListNode<E> prev, E element, ListNode<E> next) {
        this.prev = prev;
        this.element = element;
        this.next = next;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public ListNode<E> getPrev() {
        return prev;
    }

    public void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }
}
