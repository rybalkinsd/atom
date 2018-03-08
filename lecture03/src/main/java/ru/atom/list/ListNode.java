package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private E element;
    private ListNode<E> next;
    private ListNode<E> prev;

    ListNode (E elem, ListNode<E> next, ListNode<E> prev) {
        this.element = elem;
        this.next = next;
        this.prev = prev;
    }

    public E getElement() {
        return this.element;
    }

    public ListNode<E> getNext() {
        return this.next;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }

    public void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }


    public ListNode<E> getPrev() {
        return this.prev;
    }
}
