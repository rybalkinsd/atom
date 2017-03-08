package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private E element;
    private ListNode<E> next;
    private ListNode<E> prev;

    public ListNode() {
        this.element = null;
        this.next = null;
        this.prev = null;
    }

    public void setNext(ListNode<E> e) {
        this.next = e;
    }

    public  void  setPrev(ListNode e) {
        this.prev = e;
    }

    public void setElement(E v) {
        this.element = v;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public ListNode<E> getPrev() {
        return prev;
    }

    public E getElement() {
        return element;
    }
}
