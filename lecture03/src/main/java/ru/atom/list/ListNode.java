package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {

    private E content;
    private ListNode<E> prev;
    private ListNode<E> next;

    public ListNode(E content, ListNode<E> prev) {
        this.content = content;
        this.prev = prev;
    }

    public E getContent() {
        return content;
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
