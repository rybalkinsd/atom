package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    private E value;
    private ListNode<E> prev;
    private ListNode<E> next;

    public ListNode() {
        this.value = null;
        this.prev = null;
        this.next = null;
    }

    public ListNode(E val) {
        this.value = val;
        this.prev = null;
        this.next = null;
    }

    public ListNode<E> getNext() {
        return this.next;
    }

    public ListNode<E> getPrev() {
        return this.prev;
    }

    public E getValue() {
        return this.value;
    }

    public void setNext(ListNode<E> o) {
        this.next = o;
    }

    public void setPrev(ListNode<E> o) {
        this.prev = o;
    }

    public void setNextPrev(ListNode<E> o) {
        this.next.prev = o;
    }

    public void setPrevNext(ListNode<E> o) {
        this.prev.next = o;
    }
}