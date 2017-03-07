package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {

    private E element ;
    private ListNode next ;
    private ListNode prev ;


    ListNode() {
        this.element = null;
        this.next = this;
        this.prev = this ;
    }


    ListNode(E element, ListNode next, ListNode prev) {
        this.element = element;
        this.next = next;
        this.prev = prev;
    }



    public E getElement() {
        return element;
    }

    public ListNode getNext() {
        return next;
    }

    public ListNode getPrev() {
        return prev;
    }




    public void setElement(E element) {
        this.element = element;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    public void setPrev(ListNode prev) {
        this.prev = prev;
    }


}
