package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private E element;
    private ListNode<E> prev;
    private ListNode<E> next;


    public E getElement(){
        return element;
    }
    public ListNode getPrev(){
        return prev;
    }
    public ListNode getNext(){
        return next;
    }

    public void setElement(E element) { this.element = element; }
    public void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }
    public void setNext(ListNode<E> next) {
        this.next = next;
    }
}
