package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private E element;
    private ListNode next;
    private ListNode prev;

    public ListNode getNext(){
        return next;
    }

    public ListNode getPrev(){
        return prev;
    }

    public E getElement(){
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

    ListNode(){
        element = null;
        next = this;
        prev = this;
    }
}
