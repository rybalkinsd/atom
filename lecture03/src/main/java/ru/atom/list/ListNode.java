package ru.atom.list;


/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private E element = null;
    ListNode<E> prev = null;
    ListNode<E> next = null;

    ListNode(E e, ListNode prev, ListNode next) {
        element = e;
        this.prev = prev;
        this.next = next;
    }

    public E getElement() {
        return element;
    }

    public ListNode<E> getPrev() {
        return prev;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public void setElement(E e) {
        element = e;
    }

    public void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }
}
