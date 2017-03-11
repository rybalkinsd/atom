package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {

    E elem;
    ListNode next;
    ListNode prev;

    public ListNode() {
        elem = null;
        next = this;
        prev = this;
    }

    public ListNode(E elem) {
        this.elem = elem;
        next = this;
        prev = this;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    public void setPrev(ListNode prev) {
        this.prev = prev;
    }

    public E getElem() {
        return elem;
    }

    public ListNode getPrev() {

        return prev;
    }

    public ListNode getNext() {
        return next;
    }
}
