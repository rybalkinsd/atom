package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private E item;
    private ListNode<E> next;
    private ListNode<E> prev;

    public ListNode(ListNode<E> prev, E item, ListNode<E> next) {
        this.item = item;
        this.prev = prev;
        this.next = next;
    }

    public E getItem() {
        return item;
    }

    public void setItem(E item) {
        this.item = item;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }

    public ListNode<E> getPrev() {
        return prev;
    }

    public void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }
}
