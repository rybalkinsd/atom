package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private ListNode<E> next;
    private ListNode<E> prev;
    private E value;


    public void setValue(E value) {
        this.value = value;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }

    public void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }

    public E getValue() {
        return this.value;
    }

    public ListNode<E> getNext() {
        return this.next;
    }

    public ListNode<E> getPrev() {
        return this.prev;
    }
}
