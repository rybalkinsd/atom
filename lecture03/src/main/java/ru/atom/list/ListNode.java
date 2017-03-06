package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private E content;
    private ListNode next;
    private ListNode prev;


    public E getContent() {

        return content;
    }

    public ListNode getNext() {

        return next;
    }

    public ListNode getPrev() {

        return prev;
    }

    public void setContent(E content) {
        this.content = content;
    }

    public void setNext(ListNode next) {

        this.next = next;
    }

    public void setPrev(ListNode prev) {

        this.prev = prev;
    }


}
